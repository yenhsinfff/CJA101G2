package com.lutu.article_image.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class HybridImageUploadService {

    // 檔案大小常數 (bytes)
    private static final long SIZE_2MB = 2 * 1024 * 1024; // 2MB
    private static final long SIZE_10MB = 10 * 1024 * 1024; // 10MB
    private static final long DEFAULT_CHUNK_SIZE = 1024 * 1024; // 1MB 分塊

    @Autowired
    private ArticleImageService articleImageService;

    @Autowired
    private UploadSessionRepository uploadSessionRepository;

    @Autowired
    private ArticleImageRepository articleImageRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 內存中的分塊資料暫存 (uploadId -> (chunkIndex -> chunkData))
    private final Map<String, Map<Integer, byte[]>> chunkStorage = new ConcurrentHashMap<>();

    /**
     * 判斷檔案上傳策略
     */
    public UploadStrategy determineStrategy(long fileSize) {
        if (fileSize <= SIZE_2MB) {
            return UploadStrategy.BASE64;
        } else if (fileSize <= SIZE_10MB) {
            return UploadStrategy.DIRECT_UPLOAD;
        } else {
            return UploadStrategy.CHUNKED_UPLOAD;
        }
    }

    /**
     * 處理 Base64 圖片上傳（小檔案 ≤ 2MB）
     */
    public ArticleImageVO processBase64Image(String base64Data, Integer articleId) throws IOException {
        try {
            // 移除 data:image/xxx;base64, 前綴
            String[] parts = base64Data.split(",");
            if (parts.length != 2) {
                throw new IllegalArgumentException("無效的 Base64 圖片格式");
            }

            String actualBase64 = parts[1];
            byte[] imageData = Base64.getDecoder().decode(actualBase64);

            // 檢查檔案大小
            if (imageData.length > SIZE_2MB) {
                throw new IllegalArgumentException("Base64 圖片不能超過 2MB");
            }

            return createArticleImage(imageData, articleId);

        } catch (Exception e) {
            throw new IOException("Base64 圖片處理失敗: " + e.getMessage(), e);
        }
    }

    /**
     * 處理直接上傳（中等檔案 2-10MB）
     */
    public ArticleImageVO processDirectUpload(MultipartFile file, Integer articleId) throws IOException {
        validateImageFile(file);

        if (file.getSize() > SIZE_10MB) {
            throw new IllegalArgumentException("直接上傳檔案不能超過 10MB");
        }

        byte[] compressedData = compressImageIfNeeded(file.getBytes());
        return createArticleImage(compressedData, articleId);
    }

    /**
     * 初始化分塊上傳（大檔案 > 10MB）
     */
    public Map<String, Object> initializeChunkedUpload(String fileName, String contentType,
            long totalSize, Integer articleId) {
        if (totalSize <= SIZE_10MB) {
            throw new IllegalArgumentException("分塊上傳僅適用於大於 10MB 的檔案");
        }

        // 生成唯一的上傳會話ID
        String uploadId = generateUploadId();

        // 計算分塊數量
        int totalChunks = (int) Math.ceil((double) totalSize / DEFAULT_CHUNK_SIZE);

        // 創建上傳會話
        UploadSessionVO session = new UploadSessionVO(
                uploadId, totalChunks, DEFAULT_CHUNK_SIZE, totalSize, fileName, contentType);
        session.setArticleId(articleId);

        uploadSessionRepository.save(session);

        // 初始化分塊存儲
        chunkStorage.put(uploadId, new ConcurrentHashMap<>());

        Map<String, Object> result = new HashMap<>();
        result.put("uploadId", uploadId);
        result.put("totalChunks", totalChunks);
        result.put("chunkSize", DEFAULT_CHUNK_SIZE);
        result.put("strategy", "CHUNKED_UPLOAD");

        return result;
    }

    /**
     * 處理分塊上傳
     */
    public Map<String, Object> processChunkUpload(String uploadId, int chunkIndex,
            MultipartFile chunk) throws IOException {
        // 驗證上傳會話
        UploadSessionVO session = uploadSessionRepository.findById(uploadId)
                .orElseThrow(() -> new IllegalArgumentException("無效的上傳會話ID"));

        if (!"UPLOADING".equals(session.getStatus())) {
            throw new IllegalStateException("上傳會話狀態無效: " + session.getStatus());
        }

        // 驗證分塊索引
        if (chunkIndex < 0 || chunkIndex >= session.getTotalChunks()) {
            throw new IllegalArgumentException("無效的分塊索引: " + chunkIndex);
        }

        // 存儲分塊資料
        Map<Integer, byte[]> chunks = chunkStorage.get(uploadId);
        if (chunks == null) {
            chunks = new ConcurrentHashMap<>();
            chunkStorage.put(uploadId, chunks);
        }

        chunks.put(chunkIndex, chunk.getBytes());

        // 更新已上傳分塊列表
        updateUploadedChunks(session, chunkIndex);

        Map<String, Object> result = new HashMap<>();
        result.put("chunkIndex", chunkIndex);
        result.put("uploadedChunks", chunks.size());
        result.put("totalChunks", session.getTotalChunks());
        result.put("isComplete", chunks.size() == session.getTotalChunks());

        return result;
    }

    /**
     * 完成分塊上傳，合併檔案
     */
    public ArticleImageVO completeChunkedUpload(String uploadId) throws IOException {
        // 驗證上傳會話
        UploadSessionVO session = uploadSessionRepository.findById(uploadId)
                .orElseThrow(() -> new IllegalArgumentException("無效的上傳會話ID"));

        Map<Integer, byte[]> chunks = chunkStorage.get(uploadId);
        if (chunks == null || chunks.size() != session.getTotalChunks()) {
            throw new IllegalStateException("分塊上傳未完成");
        }

        try {
            // 合併分塊
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            for (int i = 0; i < session.getTotalChunks(); i++) {
                byte[] chunkData = chunks.get(i);
                if (chunkData == null) {
                    throw new IllegalStateException("缺少分塊: " + i);
                }
                outputStream.write(chunkData);
            }

            byte[] completeImageData = outputStream.toByteArray();

            // 壓縮圖片（如果需要）
            byte[] finalImageData = compressImageIfNeeded(completeImageData);

            // 創建文章圖片
            ArticleImageVO articleImage = createArticleImage(finalImageData, session.getArticleId());

            // 更新會話狀態
            session.setStatus("COMPLETED");
            uploadSessionRepository.save(session);

            // 清理暫存資料
            cleanupChunkData(uploadId);

            return articleImage;

        } catch (Exception e) {
            session.setStatus("FAILED");
            uploadSessionRepository.save(session);
            cleanupChunkData(uploadId);
            throw new IOException("分塊上傳完成失敗: " + e.getMessage(), e);
        }
    }

    /**
     * 獲取上傳進度
     */
    public Map<String, Object> getUploadProgress(String uploadId) {
        UploadSessionVO session = uploadSessionRepository.findById(uploadId).orElse(null);
        if (session == null) {
            return null;
        }

        Map<Integer, byte[]> chunks = chunkStorage.get(uploadId);
        int uploadedChunks = chunks != null ? chunks.size() : 0;

        Map<String, Object> progress = new HashMap<>();
        progress.put("uploadId", uploadId);
        progress.put("status", session.getStatus());
        progress.put("uploadedChunks", uploadedChunks);
        progress.put("totalChunks", session.getTotalChunks());
        progress.put("progressPercentage", (double) uploadedChunks / session.getTotalChunks() * 100);
        progress.put("totalSize", session.getTotalSize());
        progress.put("fileName", session.getFileName());

        return progress;
    }

    // === 私有方法 ===

    private ArticleImageVO createArticleImage(byte[] imageData, Integer articleId) {
        ArticleImageVO articleImage = new ArticleImageVO();
        articleImage.setAcImg(imageData);

        if (articleId != null) {
            com.lutu.article.model.ArticlesVO article = new com.lutu.article.model.ArticlesVO();
            article.setAcId(articleId);
            articleImage.setArticlesVO(article);
        }

        articleImageService.addArticleImage(articleImage);
        return articleImage;
    }

    private void validateImageFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("圖片檔案不能為空");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("請上傳有效的圖片檔案");
        }

        // 檢查支援的圖片格式
        String[] allowedTypes = { "image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp" };
        boolean isValidType = Arrays.stream(allowedTypes)
                .anyMatch(type -> type.equals(contentType.toLowerCase()));

        if (!isValidType) {
            throw new IllegalArgumentException("不支援的圖片格式，請上傳 JPG、PNG、GIF 或 WebP 格式");
        }
    }

    private byte[] compressImageIfNeeded(byte[] imageData) {
        // 簡單的壓縮邏輯，實際可以使用圖片處理庫
        // 這裡暫時直接返回原始資料，實際專案中可以加入壓縮邏輯
        return imageData;
    }

    private String generateUploadId() {
        return "upload_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);
    }

    private void updateUploadedChunks(UploadSessionVO session, int chunkIndex) {
        try {
            List<Integer> uploadedChunks = objectMapper.readValue(
                    session.getUploadedChunks(),
                    new TypeReference<List<Integer>>() {
                    });

            if (!uploadedChunks.contains(chunkIndex)) {
                uploadedChunks.add(chunkIndex);
                session.setUploadedChunks(objectMapper.writeValueAsString(uploadedChunks));
                uploadSessionRepository.save(session);
            }
        } catch (Exception e) {
            // 如果 JSON 解析失敗，重新創建列表
            List<Integer> newList = new ArrayList<>();
            newList.add(chunkIndex);
            try {
                session.setUploadedChunks(objectMapper.writeValueAsString(newList));
                uploadSessionRepository.save(session);
            } catch (Exception ex) {
                // 記錄錯誤但不拋出異常
                System.err.println("更新上傳分塊列表失敗: " + ex.getMessage());
            }
        }
    }

    private void cleanupChunkData(String uploadId) {
        chunkStorage.remove(uploadId);
    }

    /**
     * 定期清理過期的上傳會話（每小時執行一次）
     */
    @Scheduled(fixedRate = 3600000) // 1小時
    public void cleanupExpiredSessions() {
        LocalDateTime expireTime = LocalDateTime.now().minusHours(24); // 24小時過期

        // 清理資料庫中的過期會話
        List<UploadSessionVO> expiredSessions = uploadSessionRepository.findExpiredSessions(expireTime);
        for (UploadSessionVO session : expiredSessions) {
            cleanupChunkData(session.getUploadId());
        }

        int deletedCount = uploadSessionRepository.deleteExpiredSessions(expireTime);
        if (deletedCount > 0) {
            System.out.println("清理了 " + deletedCount + " 個過期的上傳會話");
        }
    }

    /**
     * 上傳策略枚舉
     */
    public enum UploadStrategy {
        BASE64, // ≤ 2MB: 使用 Base64
        DIRECT_UPLOAD, // 2-10MB: 直接上傳
        CHUNKED_UPLOAD // > 10MB: 分塊上傳
    }
}