package com.lutu.article_image.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lutu.ApiResponse;
import com.lutu.article_image.model.ArticleImageService;
import com.lutu.article_image.model.ArticleImageVO;
import com.lutu.article_image.model.HybridImageUploadService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "*")
public class ArticleImageApiController {

    @Autowired
    ArticleImageService articleImageService;

    @Autowired
    HybridImageUploadService hybridImageUploadService;

    // 取得所有文章圖片
    @GetMapping("/api/article-images")
    public ApiResponse<List<ArticleImageVO>> getAllArticleImages() {
        List<ArticleImageVO> articleImages = articleImageService.getAll();
        return new ApiResponse<>("success", articleImages, "查詢成功");
    }

    // 取得單一文章圖片
    @GetMapping("/api/article-images/{acImgId}")
    public ApiResponse<ArticleImageVO> getOneArticleImage(@PathVariable Integer acImgId) {
        ArticleImageVO articleImage = articleImageService.getOneArticleImage(acImgId);
        if (articleImage != null) {
            return new ApiResponse<>("success", articleImage, "查詢成功");
        } else {
            return new ApiResponse<>("fail", null, "查無此文章圖片");
        }
    }

    // 根據文章ID取得該文章的所有圖片
    @GetMapping("/api/article-images/article/{acId}")
    public ApiResponse<List<ArticleImageVO>> getArticleImagesByArticleId(@PathVariable Integer acId) {
        try {
            List<ArticleImageVO> articleImages = articleImageService.getArticleImagesByArticleId(acId);
            if (articleImages != null && !articleImages.isEmpty()) {
                return new ApiResponse<>("success", articleImages, "查詢成功");
            } else {
                return new ApiResponse<>("success", articleImages, "該文章暫無圖片");
            }
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "查詢失敗: " + e.getMessage());
        }
    }

    // 新增文章圖片
    @PostMapping("/api/article-images")
    public ApiResponse<ArticleImageVO> createArticleImage(@RequestBody ArticleImageVO articleImageVO) {
        try {
            articleImageService.addArticleImage(articleImageVO);
            return new ApiResponse<>("success", articleImageVO, "新增成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "新增失敗");
        }
    }

    // 上傳文章圖片檔案
    @PostMapping("/api/article-images/upload")
    public ApiResponse<ArticleImageVO> uploadArticleImage(
            @RequestParam("acImg") MultipartFile acImg,
            @RequestParam("acId") Integer acId) {
        try {
            ArticleImageVO articleImageVO = new ArticleImageVO();
            articleImageVO.setAcImg(acImg.getBytes());

            // 建立 ArticlesVO 物件並設定 acId
            com.lutu.article.model.ArticlesVO articlesVO = new com.lutu.article.model.ArticlesVO();
            articlesVO.setAcId(acId);
            articleImageVO.setArticlesVO(articlesVO);

            articleImageService.addArticleImage(articleImageVO);
            return new ApiResponse<>("success", articleImageVO, "上傳成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "上傳失敗: " + e.getMessage());
        }
    }

    // 更新文章圖片
    @PutMapping("/api/article-images/{acImgId}")
    public ApiResponse<ArticleImageVO> updateArticleImage(@PathVariable Integer acImgId,
            @RequestBody ArticleImageVO articleImageVO) {
        try {
            articleImageVO.setAcImgId(acImgId);
            articleImageService.updateArticleImage(articleImageVO);
            return new ApiResponse<>("success", articleImageVO, "更新成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "更新失敗");
        }
    }

    // 刪除文章圖片
    @DeleteMapping("/api/article-images/{acImgId}")
    public ApiResponse<Void> deleteArticleImage(@PathVariable Integer acImgId) {
        try {
            articleImageService.deleteArticleImage(acImgId);
            return new ApiResponse<>("success", null, "刪除成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "刪除失敗");
        }
    }

    // 取得文章圖片檔案
    @GetMapping("/api/article-images/{acImgId}/image")
    public void getArticleImage(@PathVariable Integer acImgId, HttpServletResponse response) throws IOException {
        try {
            ArticleImageVO articleImage = articleImageService.getOneArticleImage(acImgId);
            if (articleImage != null && articleImage.getAcImg() != null) {
                response.setContentType("image/jpeg");
                response.getOutputStream().write(articleImage.getAcImg());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // === 混合策略上傳端點 ===

    // 檢查上傳策略
    @GetMapping("/api/article-images/upload-strategy")
    public ApiResponse<Map<String, Object>> getUploadStrategy(@RequestParam long fileSize) {
        try {
            HybridImageUploadService.UploadStrategy strategy = hybridImageUploadService.determineStrategy(fileSize);

            Map<String, Object> result = Map.of(
                    "strategy", strategy.name(),
                    "fileSize", fileSize,
                    "maxBase64Size", 2 * 1024 * 1024,
                    "maxDirectSize", 10 * 1024 * 1024,
                    "chunkSize", 1024 * 1024);

            return new ApiResponse<>("success", result, "策略獲取成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "策略獲取失敗: " + e.getMessage());
        }
    }

    // 處理 Base64 圖片上傳（≤ 2MB）
    @PostMapping("/api/article-images/upload-base64")
    public ApiResponse<ArticleImageVO> uploadBase64Image(
            @RequestParam("base64Data") String base64Data,
            @RequestParam(value = "articleId", required = false) Integer articleId) {
        try {
            ArticleImageVO result = hybridImageUploadService.processBase64Image(base64Data, articleId);
            return new ApiResponse<>("success", result, "Base64 圖片上傳成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "Base64 圖片上傳失敗: " + e.getMessage());
        }
    }

    // 處理直接圖片上傳（2-10MB）
    @PostMapping("/api/article-images/upload-direct")
    public ApiResponse<ArticleImageVO> uploadDirectImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "articleId", required = false) Integer articleId) {
        try {
            ArticleImageVO result = hybridImageUploadService.processDirectUpload(file, articleId);
            return new ApiResponse<>("success", result, "直接圖片上傳成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "直接圖片上傳失敗: " + e.getMessage());
        }
    }

    // 初始化分塊上傳（> 10MB）
    @PostMapping("/api/article-images/init-chunked-upload")
    public ApiResponse<Map<String, Object>> initChunkedUpload(
            @RequestParam("fileName") String fileName,
            @RequestParam("contentType") String contentType,
            @RequestParam("totalSize") long totalSize,
            @RequestParam(value = "articleId", required = false) Integer articleId) {
        try {
            Map<String, Object> result = hybridImageUploadService.initializeChunkedUpload(
                    fileName, contentType, totalSize, articleId);
            return new ApiResponse<>("success", result, "分塊上傳初始化成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "分塊上傳初始化失敗: " + e.getMessage());
        }
    }

    // 上傳分塊
    @PostMapping("/api/article-images/upload-chunk")
    public ApiResponse<Map<String, Object>> uploadChunk(
            @RequestParam("uploadId") String uploadId,
            @RequestParam("chunkIndex") int chunkIndex,
            @RequestParam("chunk") MultipartFile chunk) {
        try {
            Map<String, Object> result = hybridImageUploadService.processChunkUpload(uploadId, chunkIndex, chunk);
            return new ApiResponse<>("success", result, "分塊上傳成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "分塊上傳失敗: " + e.getMessage());
        }
    }

    // 完成分塊上傳
    @PostMapping("/api/article-images/complete-chunked-upload")
    public ApiResponse<ArticleImageVO> completeChunkedUpload(@RequestParam("uploadId") String uploadId) {
        try {
            ArticleImageVO result = hybridImageUploadService.completeChunkedUpload(uploadId);
            return new ApiResponse<>("success", result, "分塊上傳完成");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "分塊上傳完成失敗: " + e.getMessage());
        }
    }

    // 獲取上傳進度
    @GetMapping("/api/article-images/upload-progress/{uploadId}")
    public ApiResponse<Map<String, Object>> getUploadProgress(@PathVariable String uploadId) {
        try {
            Map<String, Object> progress = hybridImageUploadService.getUploadProgress(uploadId);
            if (progress != null) {
                return new ApiResponse<>("success", progress, "進度獲取成功");
            } else {
                return new ApiResponse<>("fail", null, "找不到上傳會話");
            }
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "進度獲取失敗: " + e.getMessage());
        }
    }

    // 智能上傳端點 - 根據檔案大小自動選擇策略
    @PostMapping("/api/article-images/smart-upload")
    public ApiResponse<Object> smartUpload(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "base64Data", required = false) String base64Data,
            @RequestParam(value = "articleId", required = false) Integer articleId) {
        try {
            // 優先處理 MultipartFile
            if (file != null && !file.isEmpty()) {
                long fileSize = file.getSize();
                HybridImageUploadService.UploadStrategy strategy = hybridImageUploadService.determineStrategy(fileSize);

                switch (strategy) {
                    case DIRECT_UPLOAD:
                        ArticleImageVO directResult = hybridImageUploadService.processDirectUpload(file, articleId);
                        return new ApiResponse<>("success", directResult, "直接上傳成功");

                    case CHUNKED_UPLOAD:
                        Map<String, Object> chunkedResult = hybridImageUploadService.initializeChunkedUpload(
                                file.getOriginalFilename(), file.getContentType(), fileSize, articleId);
                        return new ApiResponse<>("success", chunkedResult, "請使用分塊上傳");

                    default:
                        return new ApiResponse<>("fail", null, "MultipartFile 不適用於小檔案，請使用 Base64");
                }
            }

            // 處理 Base64
            if (base64Data != null && !base64Data.trim().isEmpty()) {
                ArticleImageVO base64Result = hybridImageUploadService.processBase64Image(base64Data, articleId);
                return new ApiResponse<>("success", base64Result, "Base64 上傳成功");
            }

            return new ApiResponse<>("fail", null, "請提供有效的檔案或 Base64 資料");

        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "智能上傳失敗: " + e.getMessage());
        }
    }

    // === 編輯專用圖片管理 API ===

    /**
     * 批量刪除文章的舊圖片（編輯時清理不再使用的圖片）
     */
    @DeleteMapping("/api/article-images/article/{acId}/cleanup")
    public ApiResponse<Map<String, Object>> cleanupUnusedImages(
            @PathVariable Integer acId,
            @RequestBody List<Integer> keepImageIds) {
        try {
            // 獲取文章所有圖片
            List<ArticleImageVO> allImages = articleImageService.getArticleImagesByArticleId(acId);

            // 找出要刪除的圖片（不在保留列表中的）
            List<ArticleImageVO> imagesToDelete = allImages.stream()
                    .filter(img -> !keepImageIds.contains(img.getAcImgId()))
                    .collect(Collectors.toList());

            int deletedCount = 0;
            for (ArticleImageVO image : imagesToDelete) {
                try {
                    articleImageService.deleteArticleImage(image.getAcImgId());
                    deletedCount++;
                } catch (Exception e) {
                    System.err.println("刪除圖片失敗: " + image.getAcImgId() + ", 錯誤: " + e.getMessage());
                }
            }

            Map<String, Object> result = new HashMap<>();
            result.put("articleId", acId);
            result.put("totalImages", allImages.size());
            result.put("keptImages", keepImageIds.size());
            result.put("deletedImages", deletedCount);
            result.put("deletedImageIds", imagesToDelete.stream()
                    .map(ArticleImageVO::getAcImgId)
                    .collect(Collectors.toList()));

            return new ApiResponse<>("success", result, "圖片清理完成");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "圖片清理失敗: " + e.getMessage());
        }
    }

    /**
     * 批量上傳編輯時的新圖片
     */
    @PostMapping("/api/article-images/article/{acId}/batch-upload")
    public ApiResponse<List<ArticleImageVO>> batchUploadForEdit(
            @PathVariable Integer acId,
            @RequestParam("files") MultipartFile[] files) {
        try {
            List<ArticleImageVO> uploadedImages = new ArrayList<>();

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    ArticleImageVO imageVO = new ArticleImageVO();
                    imageVO.setAcImg(file.getBytes());

                    // 設置關聯的文章
                    com.lutu.article.model.ArticlesVO articlesVO = new com.lutu.article.model.ArticlesVO();
                    articlesVO.setAcId(acId);
                    imageVO.setArticlesVO(articlesVO);

                    articleImageService.addArticleImage(imageVO);
                    uploadedImages.add(imageVO);
                }
            }

            return new ApiResponse<>("success", uploadedImages,
                    String.format("成功上傳 %d 張圖片", uploadedImages.size()));
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "批量上傳失敗: " + e.getMessage());
        }
    }
}
