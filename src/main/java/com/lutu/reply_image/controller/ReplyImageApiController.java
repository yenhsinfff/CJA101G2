package com.lutu.reply_image.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lutu.ApiResponse;
import com.lutu.reply_image.model.ReplyImageService;
import com.lutu.reply_image.model.ReplyImageVO;
import com.lutu.reply_image.model.HybridReplyImageUploadService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "*")
public class ReplyImageApiController {

    @Autowired
    ReplyImageService replyImageService;

    @Autowired
    HybridReplyImageUploadService hybridReplyImageUploadService;

    // 取得所有留言圖片
    @GetMapping("/api/reply-images")
    public ApiResponse<List<ReplyImageVO>> getAllReplyImages() {
        List<ReplyImageVO> replyImages = replyImageService.getAll();
        return new ApiResponse<>("success", replyImages, "查詢成功");
    }

    // 取得單一留言圖片
    @GetMapping("/api/reply-images/{replyImgId}")
    public ApiResponse<ReplyImageVO> getOneReplyImage(@PathVariable Integer replyImgId) {
        ReplyImageVO replyImage = replyImageService.getOneReplyImage(replyImgId);
        if (replyImage != null) {
            return new ApiResponse<>("success", replyImage, "查詢成功");
        } else {
            return new ApiResponse<>("fail", null, "查無此留言圖片");
        }
    }

    // 根據留言ID取得該留言的所有圖片
    @GetMapping("/api/reply-images/reply/{replyId}")
    public ApiResponse<List<ReplyImageVO>> getReplyImagesByReplyId(@PathVariable Integer replyId) {
        try {
            List<ReplyImageVO> replyImages = replyImageService.getReplyImagesByReplyId(replyId);
            if (replyImages != null && !replyImages.isEmpty()) {
                return new ApiResponse<>("success", replyImages, "查詢成功");
            } else {
                return new ApiResponse<>("success", replyImages, "該留言暫無圖片");
            }
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "查詢失敗: " + e.getMessage());
        }
    }

    // 新增留言圖片
    @PostMapping("/api/reply-images")
    public ApiResponse<ReplyImageVO> createReplyImage(@RequestBody ReplyImageVO replyImageVO) {
        try {
            replyImageService.addReplyImage(replyImageVO);
            return new ApiResponse<>("success", replyImageVO, "新增成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "新增失敗: " + e.getMessage());
        }
    }

    // 智能上傳留言圖片（自動選擇策略）
    @PostMapping("/api/reply-images/smart-upload")
    public ApiResponse<ReplyImageVO> smartUploadReplyImage(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "base64Data", required = false) String base64Data,
            @RequestParam("replyId") Integer replyId) {
        try {
            ReplyImageVO result;

            if (file != null && !file.isEmpty()) {
                // 檔案上傳
                HybridReplyImageUploadService.UploadStrategy strategy = hybridReplyImageUploadService
                        .determineStrategy(file.getSize());

                switch (strategy) {
                    case BASE64:
                    case DIRECT_UPLOAD:
                        result = hybridReplyImageUploadService.processDirectUpload(file, replyId);
                        break;
                    case COMPRESS_AND_UPLOAD:
                        // 對於大檔案，自動壓縮
                        result = hybridReplyImageUploadService.processDirectUpload(file, replyId);
                        break;
                    default:
                        throw new IllegalArgumentException("不支援的上傳策略");
                }

                return new ApiResponse<>("success", result,
                        "上傳成功 (檔案大小: " + file.getSize() + " bytes, 策略: " + strategy + ")");

            } else if (base64Data != null && !base64Data.trim().isEmpty()) {
                // Base64 上傳
                result = hybridReplyImageUploadService.processBase64Image(base64Data, replyId);
                return new ApiResponse<>("success", result, "Base64 上傳成功");

            } else {
                return new ApiResponse<>("fail", null, "請提供檔案或 Base64 資料");
            }

        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "上傳失敗: " + e.getMessage());
        }
    }

    // 上傳留言圖片檔案（傳統方式，保持向後相容）
    @PostMapping("/api/reply-images/upload")
    public ApiResponse<ReplyImageVO> uploadReplyImage(
            @RequestParam("replyImg") MultipartFile replyImg,
            @RequestParam("replyId") Integer replyId) {
        try {
            // 使用智能上傳處理
            return smartUploadReplyImage(replyImg, null, replyId);
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "上傳失敗: " + e.getMessage());
        }
    }

    // 批量上傳留言圖片（增強版）
    @PostMapping("/api/reply-images/reply/{replyId}/batch-upload")
    public ApiResponse<List<ReplyImageVO>> batchUploadForReply(
            @PathVariable Integer replyId,
            @RequestParam("files") MultipartFile[] files) {
        try {
            // 轉換為 List
            List<MultipartFile> fileList = java.util.Arrays.asList(files);

            // 使用混合上傳服務處理
            List<ReplyImageVO> uploadedImages = hybridReplyImageUploadService.processBatchUpload(fileList, replyId);

            if (uploadedImages.isEmpty()) {
                return new ApiResponse<>("fail", null, "沒有成功上傳任何圖片");
            }

            return new ApiResponse<>("success", uploadedImages,
                    "批量上傳完成，成功上傳 " + uploadedImages.size() + " 張圖片");

        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "批量上傳失敗: " + e.getMessage());
        }
    }

    // 檢查上傳策略和限制
    @GetMapping("/api/reply-images/upload-strategy")
    public ApiResponse<Map<String, Object>> getUploadStrategy(@RequestParam long fileSize) {
        try {
            HybridReplyImageUploadService.UploadStrategy strategy = hybridReplyImageUploadService
                    .determineStrategy(fileSize);

            Map<String, Object> result = Map.of(
                    "strategy", strategy.name(),
                    "fileSize", fileSize,
                    "maxBase64Size", "2MB",
                    "maxDirectSize", "10MB",
                    "autoCompressAbove", "2MB",
                    "recommendation", getRecommendationForSize(fileSize));

            return new ApiResponse<>("success", result, "策略獲取成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "策略獲取失敗: " + e.getMessage());
        }
    }

    // 取得上傳建議
    @GetMapping("/api/reply-images/upload-recommendations")
    public ApiResponse<Map<String, Object>> getUploadRecommendations() {
        try {
            Map<String, Object> recommendations = hybridReplyImageUploadService.getUploadRecommendations();
            return new ApiResponse<>("success", recommendations, "建議獲取成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "建議獲取失敗: " + e.getMessage());
        }
    }

    // 驗證批量上傳
    @PostMapping("/api/reply-images/validate-batch")
    public ApiResponse<Map<String, Object>> validateBatchUpload(
            @RequestParam("replyId") Integer replyId,
            @RequestParam("files") MultipartFile[] files) {
        try {
            List<MultipartFile> fileList = java.util.Arrays.asList(files);
            HybridReplyImageUploadService.ValidationResult validation = hybridReplyImageUploadService
                    .validateReplyImageUpload(replyId, fileList);

            Map<String, Object> result = Map.of(
                    "valid", validation.isValid(),
                    "message", validation.getMessage(),
                    "fileCount", files.length,
                    "totalSize", fileList.stream().mapToLong(MultipartFile::getSize).sum());

            return new ApiResponse<>("success", result, "驗證完成");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "驗證失敗: " + e.getMessage());
        }
    }

    // 更新留言圖片
    @PutMapping("/api/reply-images/{replyImgId}")
    public ApiResponse<ReplyImageVO> updateReplyImage(@PathVariable Integer replyImgId,
            @RequestBody ReplyImageVO replyImageVO) {
        try {
            replyImageVO.setReplyImgId(replyImgId);
            replyImageService.updateReplyImage(replyImageVO);
            return new ApiResponse<>("success", replyImageVO, "更新成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "更新失敗: " + e.getMessage());
        }
    }

    // 刪除留言圖片
    @DeleteMapping("/api/reply-images/{replyImgId}")
    public ApiResponse<Void> deleteReplyImage(@PathVariable Integer replyImgId) {
        try {
            replyImageService.deleteReplyImage(replyImgId);
            return new ApiResponse<>("success", null, "刪除成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "刪除失敗: " + e.getMessage());
        }
    }

    // 取得留言圖片檔案
    @GetMapping("/api/reply-images/{replyImgId}/image")
    public void getReplyImage(@PathVariable Integer replyImgId, HttpServletResponse response) throws IOException {
        try {
            ReplyImageVO replyImage = replyImageService.getOneReplyImage(replyImgId);
            if (replyImage != null && replyImage.getReplyImg() != null) {
                response.setContentType("image/jpeg");
                response.getOutputStream().write(replyImage.getReplyImg());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // 刪除留言的所有圖片
    @DeleteMapping("/api/reply-images/reply/{replyId}/cleanup")
    public ApiResponse<Map<String, Object>> cleanupReplyImages(@PathVariable Integer replyId) {
        try {
            List<ReplyImageVO> images = replyImageService.getReplyImagesByReplyId(replyId);
            int deletedCount = images.size();

            replyImageService.deleteImagesByReplyId(replyId);

            Map<String, Object> result = Map.of(
                    "deletedCount", deletedCount,
                    "replyId", replyId);

            return new ApiResponse<>("success", result, "清理完成，已刪除 " + deletedCount + " 張圖片");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "清理失敗: " + e.getMessage());
        }
    }

    // 獲取留言圖片統計
    @GetMapping("/api/reply-images/reply/{replyId}/stats")
    public ApiResponse<Map<String, Object>> getReplyImageStats(@PathVariable Integer replyId) {
        try {
            List<ReplyImageVO> images = replyImageService.getReplyImagesByReplyId(replyId);

            long totalSize = images.stream()
                    .mapToLong(img -> img.getReplyImg() != null ? img.getReplyImg().length : 0)
                    .sum();

            Map<String, Object> stats = Map.of(
                    "imageCount", images.size(),
                    "totalSizeBytes", totalSize,
                    "totalSizeMB", String.format("%.2f", totalSize / (1024.0 * 1024.0)),
                    "maxImagesAllowed", 5,
                    "remainingSlots", Math.max(0, 5 - images.size()));

            return new ApiResponse<>("success", stats, "統計獲取成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "統計獲取失敗: " + e.getMessage());
        }
    }

    // 根據檔案大小提供建議
    private String getRecommendationForSize(long fileSize) {
        if (fileSize <= 2 * 1024 * 1024) {
            return "最佳大小，快速上傳";
        } else if (fileSize <= 10 * 1024 * 1024) {
            return "適中大小，會自動優化";
        } else {
            return "檔案較大，建議壓縮後上傳";
        }
    }
}