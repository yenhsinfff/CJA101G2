package com.lutu.reply.controller;

import com.lutu.ApiResponse;
import com.lutu.reply.model.ReplyService;
import com.lutu.reply.model.ReplyVO;
import com.lutu.member.model.MemberRepository;
import com.lutu.article.model.ArticlesRepository;
import com.lutu.member.model.MemberVO;
import com.lutu.article.model.ArticlesVO;
import com.lutu.reply_image.model.ReplyImageService;
import com.lutu.reply_image.model.ReplyImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Base64;

@RestController
//@CrossOrigin(origins = "*")
public class ReplyApiController {
    @Autowired
    ReplyService replyService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ArticlesRepository articlesRepository;
    @Autowired
    ReplyImageService replyImageService;

    // 取得所有留言
    @GetMapping("/api/replies")
    public ApiResponse<List<ReplyDTO>> getAllReplies() {
        List<ReplyVO> replies = replyService.getAll();
        List<ReplyDTO> replyDTOs = replies.stream()
                .map(ReplyDTO::new)
                .collect(Collectors.toList());
        return new ApiResponse<>("success", replyDTOs, "查詢成功");
    }

    // 取得所有留言數量
    @GetMapping("/api/replies/count")
    public ApiResponse<Long> getRepliesCount() {
        List<ReplyVO> replies = replyService.getAll();
        Long count = (long) replies.size();
        return new ApiResponse<>("success", count, "查詢成功");
    }

    // 取得單一留言
    @GetMapping("/api/replies/{replyId}")
    public ApiResponse<ReplyDTO> getOneReply(@PathVariable Integer replyId) {
        ReplyVO reply = replyService.getOneReply(replyId);
        if (reply != null) {
            ReplyDTO replyDTO = new ReplyDTO(reply);
            return new ApiResponse<>("success", replyDTO, "查詢成功");
        } else {
            return new ApiResponse<>("fail", null, "查無此留言");
        }
    }

    // 根據文章ID取得該文章的所有留言
    @GetMapping("/api/replies/article/{acId}")
    public ApiResponse<List<ReplyDTO>> getRepliesByArticle(@PathVariable Integer acId) {
        try {
            // 先確認文章是否存在
            ArticlesVO article = articlesRepository.findById(acId).orElse(null);
            if (article == null) {
                return new ApiResponse<>("fail", null, "查無此文章");
            }

            // 使用優化的查詢方法，直接取得該文章的留言並載入會員資料
            List<ReplyVO> articleReplies = replyService.getRepliesByArticleId(acId);

            List<ReplyDTO> replyDTOs = articleReplies.stream()
                    .map(ReplyDTO::new)
                    .collect(Collectors.toList());

            return new ApiResponse<>("success", replyDTOs, "查詢成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "查詢失敗: " + e.getMessage());
        }
    }

    // 根據文章ID取得該文章的留言數量
    @GetMapping("/api/replies/article/{acId}/count")
    public ApiResponse<Long> getReplyCountByArticle(@PathVariable Integer acId) {
        try {
            // 先確認文章是否存在
            ArticlesVO article = articlesRepository.findById(acId).orElse(null);
            if (article == null) {
                return new ApiResponse<>("fail", null, "查無此文章");
            }

            // 取得所有留言並篩選出屬於該文章的留言
            List<ReplyVO> allReplies = replyService.getAll();
            Long count = allReplies.stream()
                    .filter(reply -> reply.getArticlesVO() != null &&
                            reply.getArticlesVO().getAcId().equals(acId))
                    .count();

            return new ApiResponse<>("success", count, "查詢成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", 0L, "查詢失敗: " + e.getMessage());
        }
    }

    // 新增留言
    @PostMapping("/api/replies")
    public ApiResponse<ReplyDTO> createReply(@Valid @RequestBody ReplyDTO dto) {
        try {
            // 將 DTO 轉為 VO
            ReplyVO vo = new ReplyVO();
            vo.setReplyContext(dto.getReplyContext());

            // 查詢並設置 MemberVO
            MemberVO member = null;
            if (dto.getMemId() != null) {
                member = memberRepository.findById(dto.getMemId()).orElse(null);
                if (member == null) {
                    return new ApiResponse<>("fail", null, "查無此會員");
                }
            }
            vo.setMemberVO(member);

            // 查詢並設置 ArticlesVO
            ArticlesVO article = null;
            if (dto.getAcId() != null) {
                article = articlesRepository.findById(dto.getAcId()).orElse(null);
                if (article == null) {
                    return new ApiResponse<>("fail", null, "查無此文章");
                }
            }
            vo.setArticlesVO(article);

            // 設定回覆時間為當前時間
            if (vo.getReplyTime() == null) {
                vo.setReplyTime(java.time.LocalDateTime.now());
            }

            // 設定預設狀態為顯示 (0)
            if (vo.getReplyStatus() == null) {
                vo.setReplyStatus((byte) 0);
            }

            replyService.addReply(vo);

            // 回傳包含完整資料的 DTO
            ReplyDTO replyDTO = new ReplyDTO(vo);
            return new ApiResponse<>("success", replyDTO, "新增成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "新增失敗: " + e.getMessage());
        }
    }

    // 新增帶圖片的留言
    @PostMapping("/api/replies/with-images")
    public ApiResponse<ReplyDTO> createReplyWithImages(@Valid @RequestBody Map<String, Object> requestData) {
        try {
            // 解析請求資料
            ReplyDTO dto = extractReplyDTOFromMap(requestData);
            @SuppressWarnings("unchecked")
            List<String> base64Images = (List<String>) requestData.get("images");

            // 驗證圖片數量（更新為5張）
            if (base64Images != null && base64Images.size() > 5) {
                return new ApiResponse<>("fail", null, "留言最多只能上傳5張圖片");
            }

            // 創建留言
            ReplyVO vo = new ReplyVO();
            vo.setReplyContext(dto.getReplyContext());

            // 設置會員和文章
            MemberVO member = memberRepository.findById(dto.getMemId()).orElse(null);
            if (member == null) {
                return new ApiResponse<>("fail", null, "查無此會員");
            }
            vo.setMemberVO(member);

            ArticlesVO article = articlesRepository.findById(dto.getAcId()).orElse(null);
            if (article == null) {
                return new ApiResponse<>("fail", null, "查無此文章");
            }
            vo.setArticlesVO(article);

            vo.setReplyTime(java.time.LocalDateTime.now());
            vo.setReplyStatus((byte) 0);

            // 保存留言
            replyService.addReply(vo);

            // 處理圖片（支援更大的檔案）
            if (base64Images != null && !base64Images.isEmpty()) {
                int successCount = 0;
                long totalSize = 0;

                for (String base64Image : base64Images) {
                    try {
                        // 移除 data:image/xxx;base64, 前綴
                        String cleanBase64 = base64Image;
                        if (base64Image.contains(",")) {
                            cleanBase64 = base64Image.split(",")[1];
                        }

                        byte[] imageBytes = Base64.getDecoder().decode(cleanBase64);

                        // 更新限制為10MB
                        if (imageBytes.length > 10 * 1024 * 1024) {
                            System.err.println("圖片大小超過10MB，跳過處理");
                            continue;
                        }

                        totalSize += imageBytes.length;

                        // 檢查總大小限制（50MB）
                        if (totalSize > 50 * 1024 * 1024) {
                            System.err.println("圖片總大小超過50MB，停止處理後續圖片");
                            break;
                        }

                        // 如果圖片大於2MB，進行壓縮優化
                        if (imageBytes.length > 2 * 1024 * 1024) {
                            imageBytes = optimizeImageForReply(imageBytes);
                        }

                        ReplyImageVO imageVO = new ReplyImageVO();
                        imageVO.setReplyImg(imageBytes);
                        imageVO.setReplyVO(vo);
                        replyImageService.addReplyImage(imageVO);
                        successCount++;
                    } catch (Exception e) {
                        // 如果某張圖片處理失敗，記錄但不影響留言創建
                        System.err.println("圖片處理失敗: " + e.getMessage());
                    }
                }

                System.out.println("成功處理 " + successCount + " 張圖片，總大小: " +
                        String.format("%.2f", totalSize / (1024.0 * 1024.0)) + " MB");
            }

            // 重新查詢留言以獲取完整的圖片資訊
            ReplyVO savedReply = replyService.getOneReply(vo.getReplyId());
            ReplyDTO replyDTO = new ReplyDTO(savedReply);
            return new ApiResponse<>("success", replyDTO, "新增成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "新增失敗: " + e.getMessage());
        }
    }

    // 圖片優化方法（簡化版）
    private byte[] optimizeImageForReply(byte[] imageData) {
        try {
            // 如果圖片已經小於1MB，不需要處理
            if (imageData.length <= 1024 * 1024) {
                return imageData;
            }

            java.io.ByteArrayInputStream inputStream = new java.io.ByteArrayInputStream(imageData);
            java.awt.image.BufferedImage originalImage = javax.imageio.ImageIO.read(inputStream);

            if (originalImage == null) {
                return imageData; // 無法處理則返回原圖
            }

            // 計算新尺寸（留言圖片建議最大800px）
            int maxDimension = 800;
            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();

            double scale = Math.min(
                    (double) maxDimension / originalWidth,
                    (double) maxDimension / originalHeight);

            // 如果圖片已經夠小，不需要縮放
            if (scale >= 1.0) {
                return imageData;
            }

            int newWidth = (int) (originalWidth * scale);
            int newHeight = (int) (originalHeight * scale);

            // 創建縮放後的圖片
            java.awt.image.BufferedImage resizedImage = new java.awt.image.BufferedImage(
                    newWidth, newHeight, java.awt.image.BufferedImage.TYPE_INT_RGB);
            java.awt.Graphics2D g2d = resizedImage.createGraphics();

            // 設置高品質縮放
            g2d.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                    java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING,
                    java.awt.RenderingHints.VALUE_RENDER_QUALITY);

            g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
            g2d.dispose();

            // 輸出為JPEG格式
            java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
            javax.imageio.ImageIO.write(resizedImage, "jpg", outputStream);

            return outputStream.toByteArray();

        } catch (Exception e) {
            // 如果壓縮失敗，返回原圖
            System.err.println("圖片優化失敗: " + e.getMessage());
            return imageData;
        }
    }

    // 從 Map 中提取 ReplyDTO
    private ReplyDTO extractReplyDTOFromMap(Map<String, Object> requestData) {
        ReplyDTO dto = new ReplyDTO();
        dto.setMemId((Integer) requestData.get("memId"));
        dto.setAcId((Integer) requestData.get("acId"));
        dto.setReplyContext((String) requestData.get("replyContext"));
        return dto;
    }

    // 更新留言
    @PutMapping("/api/replies/{replyId}")
    public ApiResponse<ReplyDTO> updateReply(@PathVariable Integer replyId, @Valid @RequestBody ReplyDTO dto) {
        try {
            // 先檢查留言是否存在
            ReplyVO existingReply = replyService.getOneReply(replyId);
            if (existingReply == null) {
                return new ApiResponse<>("fail", null, "查無此留言");
            }

            // 更新留言內容
            existingReply.setReplyContext(dto.getReplyContext());

            // 如果提供了新的狀態，則更新狀態
            if (dto.getReplyStatus() != null) {
                existingReply.setReplyStatus(dto.getReplyStatus());
            }

            // 如果提供了新的回覆時間，則更新時間
            if (dto.getReplyTime() != null) {
                existingReply.setReplyTime(dto.getReplyTime());
            }

            replyService.updateReply(existingReply);

            ReplyDTO replyDTO = new ReplyDTO(existingReply);
            return new ApiResponse<>("success", replyDTO, "更新成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "更新失敗: " + e.getMessage());
        }
    }

    // 刪除留言（包含圖片）
    @DeleteMapping("/api/replies/{replyId}")
    public ApiResponse<Void> deleteReply(@PathVariable Integer replyId) {
        try {
            // 先檢查留言是否存在
            ReplyVO existingReply = replyService.getOneReply(replyId);
            if (existingReply == null) {
                return new ApiResponse<>("fail", null, "查無此留言");
            }

            // 先刪除關聯的圖片
            replyImageService.deleteImagesByReplyId(replyId);

            // 再刪除留言
            replyService.deleteReply(replyId);
            return new ApiResponse<>("success", null, "刪除成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "刪除失敗: " + e.getMessage());
        }
    }

    // 取得留言的完整資訊（包含圖片）
    @GetMapping("/api/replies/{replyId}/with-images")
    public ApiResponse<Map<String, Object>> getReplyWithImages(@PathVariable Integer replyId) {
        try {
            ReplyVO reply = replyService.getOneReply(replyId);
            if (reply == null) {
                return new ApiResponse<>("fail", null, "查無此留言");
            }

            // 取得留言圖片
            List<ReplyImageVO> images = replyImageService.getReplyImagesByReplyId(replyId);

            Map<String, Object> result = new HashMap<>();
            result.put("reply", new ReplyDTO(reply));
            result.put("images", images);
            result.put("imageCount", images.size());

            return new ApiResponse<>("success", result, "查詢成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "查詢失敗: " + e.getMessage());
        }
    }

    // 根據會員ID取得該會員的所有留言
    @GetMapping("/api/replies/member/{memId}")
    public ApiResponse<List<ReplyDTO>> getRepliesByMember(@PathVariable Integer memId) {
        try {
            // 先確認會員是否存在
            MemberVO member = memberRepository.findById(memId).orElse(null);
            if (member == null) {
                return new ApiResponse<>("fail", null, "查無此會員");
            }

            // 取得所有留言並篩選出屬於該會員的留言
            List<ReplyVO> allReplies = replyService.getAll();
            List<ReplyVO> memberReplies = allReplies.stream()
                    .filter(reply -> reply.getMemberVO() != null &&
                            reply.getMemberVO().getMemId().equals(memId))
                    .collect(Collectors.toList());

            List<ReplyDTO> replyDTOs = memberReplies.stream()
                    .map(ReplyDTO::new)
                    .collect(Collectors.toList());

            return new ApiResponse<>("success", replyDTOs, "查詢成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "查詢失敗: " + e.getMessage());
        }
    }

    // 根據會員ID取得該會員的留言數量
    @GetMapping("/api/replies/member/{memId}/count")
    public ApiResponse<Long> getReplyCountByMember(@PathVariable Integer memId) {
        try {
            // 先確認會員是否存在
            MemberVO member = memberRepository.findById(memId).orElse(null);
            if (member == null) {
                return new ApiResponse<>("fail", null, "查無此會員");
            }

            // 取得所有留言並篩選出屬於該會員的留言
            List<ReplyVO> allReplies = replyService.getAll();
            Long count = allReplies.stream()
                    .filter(reply -> reply.getMemberVO() != null &&
                            reply.getMemberVO().getMemId().equals(memId))
                    .count();

            return new ApiResponse<>("success", count, "查詢成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", 0L, "查詢失敗: " + e.getMessage());
        }
    }
}
