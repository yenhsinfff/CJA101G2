package com.lutu.reply_image.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.util.*;

@Service
public class HybridReplyImageUploadService {

    // 檔案大小常數 (bytes)
    private static final long SIZE_2MB = 2 * 1024 * 1024; // 2MB
    private static final long SIZE_10MB = 10 * 1024 * 1024; // 10MB
    private static final long SIZE_50MB = 50 * 1024 * 1024; // 50MB 總限制
    private static final int MAX_IMAGES_PER_REPLY = 5; // 每則留言最多5張圖片

    @Autowired
    private ReplyImageService replyImageService;

    /**
     * 判斷檔案上傳策略
     */
    public UploadStrategy determineStrategy(long fileSize) {
        if (fileSize <= SIZE_2MB) {
            return UploadStrategy.BASE64;
        } else if (fileSize <= SIZE_10MB) {
            return UploadStrategy.DIRECT_UPLOAD;
        } else {
            return UploadStrategy.COMPRESS_AND_UPLOAD;
        }
    }

    /**
     * 驗證留言圖片上傳條件
     */
    public ValidationResult validateReplyImageUpload(Integer replyId, List<MultipartFile> files) {
        ValidationResult result = new ValidationResult();

        if (files == null || files.isEmpty()) {
            result.setValid(false);
            result.setMessage("沒有檔案上傳");
            return result;
        }

        // 檢查數量限制
        if (files.size() > MAX_IMAGES_PER_REPLY) {
            result.setValid(false);
            result.setMessage("留言最多只能上傳" + MAX_IMAGES_PER_REPLY + "張圖片");
            return result;
        }

        // 檢查現有圖片數量
        if (replyId != null) {
            List<ReplyImageVO> existingImages = replyImageService.getReplyImagesByReplyId(replyId);
            if (existingImages.size() + files.size() > MAX_IMAGES_PER_REPLY) {
                result.setValid(false);
                result.setMessage("加上現有圖片，總數不能超過" + MAX_IMAGES_PER_REPLY + "張");
                return result;
            }
        }

        // 檢查總檔案大小
        long totalSize = files.stream().mapToLong(MultipartFile::getSize).sum();
        if (totalSize > SIZE_50MB) {
            result.setValid(false);
            result.setMessage("所有圖片總大小不能超過50MB");
            return result;
        }

        // 檢查單個檔案
        for (MultipartFile file : files) {
            if (file.getSize() > SIZE_10MB) {
                result.setValid(false);
                result.setMessage("單張圖片不能超過10MB，建議壓縮後上傳");
                return result;
            }

            if (!isValidImageFile(file)) {
                result.setValid(false);
                result.setMessage("檔案 " + file.getOriginalFilename() + " 不是有效的圖片格式");
                return result;
            }
        }

        result.setValid(true);
        result.setMessage("驗證通過");
        return result;
    }

    /**
     * 處理 Base64 圖片上傳（小檔案 ≤ 2MB）
     */
    public ReplyImageVO processBase64Image(String base64Data, Integer replyId) throws IOException {
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

            return createReplyImage(imageData, replyId);

        } catch (Exception e) {
            throw new IOException("Base64 圖片處理失敗: " + e.getMessage(), e);
        }
    }

    /**
     * 處理直接上傳（中等檔案 2-10MB）
     */
    public ReplyImageVO processDirectUpload(MultipartFile file, Integer replyId) throws IOException {
        validateImageFile(file);

        if (file.getSize() > SIZE_10MB) {
            throw new IllegalArgumentException("直接上傳檔案不能超過 10MB");
        }

        byte[] originalData = file.getBytes();
        byte[] processedData = optimizeImageForReply(originalData);

        return createReplyImage(processedData, replyId);
    }

    /**
     * 批量處理圖片上傳
     */
    public List<ReplyImageVO> processBatchUpload(List<MultipartFile> files, Integer replyId) throws IOException {
        ValidationResult validation = validateReplyImageUpload(replyId, files);
        if (!validation.isValid()) {
            throw new IllegalArgumentException(validation.getMessage());
        }

        List<ReplyImageVO> uploadedImages = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    UploadStrategy strategy = determineStrategy(file.getSize());
                    ReplyImageVO imageVO;

                    switch (strategy) {
                        case BASE64:
                            // 對於小檔案，直接處理
                            imageVO = createReplyImage(file.getBytes(), replyId);
                            break;
                        case DIRECT_UPLOAD:
                            imageVO = processDirectUpload(file, replyId);
                            break;
                        case COMPRESS_AND_UPLOAD:
                            // 強制壓縮大檔案
                            byte[] compressedData = compressImageForReply(file.getBytes());
                            imageVO = createReplyImage(compressedData, replyId);
                            break;
                        default:
                            throw new IllegalArgumentException("不支援的上傳策略");
                    }

                    uploadedImages.add(imageVO);
                } catch (Exception e) {
                    // 記錄錯誤但繼續處理其他檔案
                    System.err.println("處理檔案 " + file.getOriginalFilename() + " 失敗: " + e.getMessage());
                }
            }
        }

        return uploadedImages;
    }

    /**
     * 針對留言優化圖片
     */
    private byte[] optimizeImageForReply(byte[] imageData) {
        try {
            // 如果圖片已經小於1MB，不需要處理
            if (imageData.length <= 1024 * 1024) {
                return imageData;
            }

            return compressImageForReply(imageData);
        } catch (Exception e) {
            // 如果壓縮失敗，返回原圖
            System.err.println("圖片優化失敗: " + e.getMessage());
            return imageData;
        }
    }

    /**
     * 壓縮圖片（專為留言設計）
     */
    private byte[] compressImageForReply(byte[] imageData) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
            BufferedImage originalImage = ImageIO.read(inputStream);

            if (originalImage == null) {
                throw new IOException("無法讀取圖片資料");
            }

            // 計算新尺寸（留言圖片建議最大800px）
            int maxDimension = 800;
            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();

            double scale = Math.min(
                    (double) maxDimension / originalWidth,
                    (double) maxDimension / originalHeight);

            // 如果圖片已經夠小，且檔案大小合理，不需要縮放
            if (scale >= 1.0 && imageData.length <= SIZE_2MB) {
                return imageData;
            }

            int newWidth = (int) (originalWidth * scale);
            int newHeight = (int) (originalHeight * scale);

            // 創建縮放後的圖片
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = resizedImage.createGraphics();

            // 設置高品質縮放
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
            g2d.dispose();

            // 輸出為JPEG格式
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "jpg", outputStream);

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new IOException("圖片壓縮失敗: " + e.getMessage(), e);
        }
    }

    /**
     * 創建留言圖片
     */
    private ReplyImageVO createReplyImage(byte[] imageData, Integer replyId) {
        ReplyImageVO imageVO = new ReplyImageVO();
        imageVO.setReplyImg(imageData);

        if (replyId != null) {
            com.lutu.reply.model.ReplyVO replyVO = new com.lutu.reply.model.ReplyVO();
            replyVO.setReplyId(replyId);
            imageVO.setReplyVO(replyVO);
        }

        replyImageService.addReplyImage(imageVO);
        return imageVO;
    }

    /**
     * 驗證圖片檔案
     */
    private void validateImageFile(MultipartFile file) throws IOException {
        if (!isValidImageFile(file)) {
            throw new IOException("檔案不是有效的圖片格式");
        }
    }

    /**
     * 檢查是否為有效的圖片檔案
     */
    private boolean isValidImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }

        String contentType = file.getContentType();
        if (contentType == null) {
            return false;
        }

        return contentType.startsWith("image/") &&
                (contentType.equals("image/jpeg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("image/gif") ||
                        contentType.equals("image/webp"));
    }

    /**
     * 取得上傳建議
     */
    public Map<String, Object> getUploadRecommendations() {
        Map<String, Object> recommendations = new HashMap<>();
        recommendations.put("maxImagesPerReply", MAX_IMAGES_PER_REPLY);
        recommendations.put("maxSingleImageSize", "10MB");
        recommendations.put("maxTotalSize", "50MB");
        recommendations.put("recommendedSingleSize", "2MB以下獲得最佳體驗");
        recommendations.put("supportedFormats", Arrays.asList("JPEG", "PNG", "GIF", "WebP"));
        recommendations.put("autoCompress", "超過2MB的圖片會自動壓縮");
        recommendations.put("recommendedDimensions", "800x600以下獲得最佳載入速度");

        return recommendations;
    }

    /**
     * 上傳策略枚舉
     */
    public enum UploadStrategy {
        BASE64, // ≤ 2MB: 使用 Base64
        DIRECT_UPLOAD, // 2-10MB: 直接上傳
        COMPRESS_AND_UPLOAD // > 2MB: 壓縮後上傳
    }

    /**
     * 驗證結果類
     */
    public static class ValidationResult {
        private boolean valid;
        private String message;

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}