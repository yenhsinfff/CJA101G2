package com.lutu.article.controller;

import com.lutu.ApiResponse;
import com.lutu.article.model.ArticlesService;
import com.lutu.article.model.ArticlesVO;
import com.lutu.member.model.MemberRepository;
import com.lutu.article_type.model.ArticleTypeRepository;
import com.lutu.member.model.MemberVO;
import com.lutu.article_type.model.ArticleTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import com.lutu.article_image.model.ArticleImageService;
import com.lutu.article_image.model.ArticleImageVO;
import com.lutu.article_image.model.HybridImageUploadService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.HashMap;

@RestController
//@CrossOrigin(origins = "*")
public class ArticlesApiController {
    @Autowired
    ArticlesService articlesService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ArticleTypeRepository articleTypeRepository;
    @Autowired
    ArticleImageService articleImageService;
    @Autowired
    HybridImageUploadService hybridImageUploadService;

    // http://localhost:8081/CJA101G02/
    // 取得所有文章
    @GetMapping("/api/articles")
    public ApiResponse<List<ArticlesDTO>> getAllArticles() {
        List<ArticlesVO> articles = articlesService.getAll();
        List<ArticlesDTO> articlesDTOs = articles.stream()
                .map(ArticlesDTO::new)
                .collect(Collectors.toList());
        return new ApiResponse<>("success", articlesDTOs, "查詢成功");
    }

    // 取得所有文章數量
    @GetMapping("/api/articles/count")
    public ApiResponse<Long> getArticlesCount() {
        List<ArticlesVO> articles = articlesService.getAll();
        Long count = (long) articles.size();
        return new ApiResponse<>("success", count, "查詢成功");
    }

    // 根據文章類別查詢文章數量
    @GetMapping("/api/articles/type/{acTypeId}/count")
    public ApiResponse<Long> getArticleCountByType(@PathVariable Integer acTypeId) {
        try {
            Long count = articlesService.getArticleCountByType(acTypeId);
            return new ApiResponse<>("success", count, "查詢成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", 0L, "查詢失敗: " + e.getMessage());
        }
    }

    // 根據會員ID查詢文章數量
    @GetMapping("/api/articles/member/{memId}/count")
    public ApiResponse<Long> getArticleCountByMember(@PathVariable Integer memId) {
        try {
            // 先確認會員是否存在
            MemberVO member = memberRepository.findById(memId).orElse(null);
            if (member == null) {
                return new ApiResponse<>("fail", null, "查無此會員");
            }

            Long count = articlesService.getArticleCountByMember(memId);
            return new ApiResponse<>("success", count, "查詢成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", 0L, "查詢失敗: " + e.getMessage());
        }
    }

    // 取得單一文章資訊
    @GetMapping("/api/articles/{acId}")
    public ApiResponse<ArticlesDTO> getOneArticle(@PathVariable Integer acId) {
        ArticlesVO article = articlesService.getOneArticles(acId);
        if (article != null) {
            ArticlesDTO articleDTO = new ArticlesDTO(article);
            return new ApiResponse<>("success", articleDTO, "查詢成功");
        } else {
            return new ApiResponse<>("fail", null, "查無此文章");
        }
    }

    // 新增文章
    @PostMapping("/api/articles")
    public ApiResponse<ArticlesDTO> createArticle(@RequestBody ArticlesDTO dto) {
        try {
            // 將 DTO 轉為 VO
            ArticlesVO vo = new ArticlesVO();
            vo.setAcTitle(dto.getAcTitle());

            // 直接存前端傳來的 HTML 內容
            vo.setAcContext(dto.getAcContext());

            // 查詢並設置 MemberVO
            MemberVO member = null;
            if (dto.getMemId() != null) {
                member = memberRepository.findById(dto.getMemId()).orElse(null);
            }
            vo.setMemberVO(member);
            // 查詢並設置 ArticleTypeVO
            ArticleTypeVO articleType = null;
            if (dto.getAcTypeId() != null) {
                articleType = articleTypeRepository.findById(dto.getAcTypeId()).orElse(null);
            }
            vo.setArticleTypeVO(articleType);
            // 設定發布時間為當前時間
            if (vo.getAcTime() == null) {
                vo.setAcTime(java.time.LocalDateTime.now());
            }
            // 設定預設狀態為顯示 (0)
            if (vo.getAcStatus() == null) {
                vo.setAcStatus((byte) 0);
            }
            articlesService.addArticles(vo);
            // 回傳包含完整資料的 DTO
            ArticlesDTO articleDTO = new ArticlesDTO(vo);
            return new ApiResponse<>("success", articleDTO, "新增成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "新增失敗: " + e.getMessage());
        }
    }

    /**
     * 處理 Quill 編輯器內容，移除圖片標籤並保留純文字
     * 
     * @param quillContent Quill 編輯器的 HTML 內容
     * @return 處理後的純文字內容
     */
    private String processQuillContent(String quillContent) {
        if (quillContent == null || quillContent.trim().isEmpty()) {
            return "";
        }

        // 移除所有 HTML 標籤，保留純文字內容
        String textContent = quillContent
                .replaceAll("<[^>]*>", "") // 移除所有 HTML 標籤
                .replaceAll("&nbsp;", " ") // 將 &nbsp; 轉為空格
                .replaceAll("&amp;", "&") // 將 &amp; 轉為 &
                .replaceAll("&lt;", "<") // 將 &lt; 轉為 <
                .replaceAll("&gt;", ">") // 將 &gt; 轉為 >
                .replaceAll("&quot;", "\"") // 將 &quot; 轉為 "
                .trim();

        // 如果內容超過 4000 字元，截斷並加上省略號
        if (textContent.length() > 4000) {
            textContent = textContent.substring(0, 3997) + "...";
        }

        return textContent;
    }

    // 更新文章（基本版本，修復版）
    @PutMapping("/api/articles/{acId}")
    public ApiResponse<ArticlesDTO> updateArticle(@PathVariable Integer acId, @RequestBody ArticlesDTO articleDTO) {
        try {
            // 使用安全的更新方法，避免 Hibernate 實體關聯問題
            ArticlesVO updatedArticle = articlesService.updateArticleBasicInfo(
                    acId,
                    articleDTO.getAcTitle(),
                    articleDTO.getAcContext(),
                    articleDTO.getAcTypeId());

            if (updatedArticle != null) {
                ArticlesDTO resultDTO = new ArticlesDTO(updatedArticle);
                return new ApiResponse<>("success", resultDTO, "更新成功");
            } else {
                return new ApiResponse<>("fail", null, "查無此文章");
            }
        } catch (Exception e) {
            e.printStackTrace(); // 開發階段用於除錯
            return new ApiResponse<>("fail", null, "更新失敗: " + e.getMessage());
        }
    }

    // 編輯文章（包含圖片處理的完整版本）
    @PutMapping("/api/articles/{acId}/edit")
    public ApiResponse<ArticlesDTO> editArticleWithImages(@PathVariable Integer acId, @RequestBody ArticlesDTO dto) {
        try {
            // 先檢查文章是否存在
            ArticlesVO existingArticle = articlesService.getOneArticles(acId);
            if (existingArticle == null) {
                return new ApiResponse<>("fail", null, "查無此文章");
            }

            // 處理新的內容中的圖片
            String processedContent = processHtmlContentWithHybridStrategy(dto.getAcContext(), acId);

            // 更新文章資料
            ArticlesVO vo = new ArticlesVO();
            vo.setAcId(acId);
            vo.setAcTitle(dto.getAcTitle());
            vo.setAcContext(processedContent);

            // 保留原有的會員和文章類型關聯（如果DTO中沒有提供）
            if (dto.getMemId() != null) {
                MemberVO member = memberRepository.findById(dto.getMemId()).orElse(null);
                vo.setMemberVO(member);
            } else {
                vo.setMemberVO(existingArticle.getMemberVO());
            }

            if (dto.getAcTypeId() != null) {
                ArticleTypeVO articleType = articleTypeRepository.findById(dto.getAcTypeId()).orElse(null);
                vo.setArticleTypeVO(articleType);
            } else {
                vo.setArticleTypeVO(existingArticle.getArticleTypeVO());
            }

            // 保留原有的發布時間和瀏覽次數
            vo.setAcTime(existingArticle.getAcTime());
            vo.setAcViewCount(existingArticle.getAcViewCount());

            // 設定狀態（如果DTO中有提供）
            if (dto.getAcStatus() != null) {
                vo.setAcStatus(dto.getAcStatus());
            } else {
                vo.setAcStatus(existingArticle.getAcStatus());
            }

            // 執行更新
            articlesService.updateArticles(vo);

            // 回傳更新後的資料
            ArticlesDTO articleDTO = new ArticlesDTO(vo);
            return new ApiResponse<>("success", articleDTO, "文章編輯成功，圖片已重新處理");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "編輯失敗: " + e.getMessage());
        }
    }

    // 刪除文章
    @DeleteMapping("/api/articles/{acId}")
    public ApiResponse<Void> deleteArticle(@PathVariable Integer acId) {
        try {
            articlesService.deleteArticles(acId);
            return new ApiResponse<>("success", null, "刪除成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "刪除失敗");
        }
    }

    // 依標題模糊查詢文章（可選擇文章類別）
    @GetMapping("/api/articles/search")
    public ApiResponse<List<ArticlesDTO>> searchArticlesByTitle(
            @RequestParam("title") String title,
            @RequestParam(value = "acTypeId", required = false) Integer acTypeId) {
        List<ArticlesVO> articles = articlesService.findByAcTitleAndOptionalType(title, acTypeId);
        List<ArticlesDTO> articlesDTOs = articles.stream()
                .map(ArticlesDTO::new)
                .collect(Collectors.toList());
        return new ApiResponse<>("success", articlesDTOs, "查詢成功");
    }

    // 依內容模糊查詢文章
    @GetMapping("/api/articles/search/content")
    public ApiResponse<List<ArticlesDTO>> searchArticlesByContent(@RequestParam("content") String content) {
        List<ArticlesVO> articles = articlesService.findByAcContextContaining(content);
        List<ArticlesDTO> articlesDTOs = articles.stream()
                .map(ArticlesDTO::new)
                .collect(Collectors.toList());
        return new ApiResponse<>("success", articlesDTOs, "查詢成功");
    }

    // 依標題或內容模糊查詢文章
    @GetMapping("/api/articles/search/title-or-content")
    public ApiResponse<List<ArticlesDTO>> searchArticlesByTitleOrContent(@RequestParam("keyword") String keyword) {
        List<ArticlesVO> articles = articlesService.findByAcTitleOrContextContaining(keyword);
        List<ArticlesDTO> articlesDTOs = articles.stream()
                .map(ArticlesDTO::new)
                .collect(Collectors.toList());
        return new ApiResponse<>("success", articlesDTOs, "查詢成功");
    }

    // 依標題或內容模糊查詢文章（可選擇文章類別）
    @GetMapping("/api/articles/search/advanced")
    public ApiResponse<List<ArticlesDTO>> searchArticlesAdvanced(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "acTypeId", required = false) Integer acTypeId) {
        List<ArticlesVO> articles = articlesService.findByAcTitleOrContextAndOptionalType(keyword, acTypeId);
        List<ArticlesDTO> articlesDTOs = articles.stream()
                .map(ArticlesDTO::new)
                .collect(Collectors.toList());
        return new ApiResponse<>("success", articlesDTOs, "查詢成功");
    }

    /**
     * 新增包含圖片的文章 - 增強版
     * 處理 Quill 編輯器的 HTML 內容，提取 base64 圖片並存儲到 article_image 表
     */
    @PostMapping("/api/articles/with-images")
    public ApiResponse<ArticlesDTO> createArticleWithImages(@RequestBody ArticlesDTO dto) {
        try {
            // 使用混合策略處理 HTML 內容，提取並保存圖片
            String processedContent = processHtmlContentWithHybridStrategy(dto.getAcContext(), null);

            // 先新增文章
            ArticlesVO vo = new ArticlesVO();
            vo.setAcTitle(dto.getAcTitle());
            vo.setAcContext(processedContent);

            // 查詢並設置 MemberVO
            MemberVO member = null;
            if (dto.getMemId() != null) {
                member = memberRepository.findById(dto.getMemId()).orElse(null);
            }
            vo.setMemberVO(member);

            // 查詢並設置 ArticleTypeVO
            ArticleTypeVO articleType = null;
            if (dto.getAcTypeId() != null) {
                articleType = articleTypeRepository.findById(dto.getAcTypeId()).orElse(null);
            }
            vo.setArticleTypeVO(articleType);

            // 設定發布時間為當前時間
            if (vo.getAcTime() == null) {
                vo.setAcTime(java.time.LocalDateTime.now());
            }

            // 設定預設狀態為顯示 (0)
            if (vo.getAcStatus() == null) {
                vo.setAcStatus((byte) 0);
            }

            articlesService.addArticles(vo);

            // 現在處理圖片並更新文章內容
            String finalContent = processHtmlContentWithHybridStrategy(dto.getAcContext(), vo.getAcId());
            if (!finalContent.equals(processedContent)) {
                vo.setAcContext(finalContent);
                articlesService.updateArticles(vo);
            }

            // 回傳包含完整資料的 DTO
            ArticlesDTO articleDTO = new ArticlesDTO(vo);
            return new ApiResponse<>("success", articleDTO, "文章新增成功，圖片已使用混合策略處理");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "新增失敗: " + e.getMessage());
        }
    }

    /**
     * 取得包含處理過圖片的文章內容
     */
    @GetMapping("/api/articles/{acId}/with-images")
    public ApiResponse<ArticlesDTO> getArticleWithImages(@PathVariable Integer acId) {
        try {
            ArticlesVO article = articlesService.getOneArticles(acId);
            if (article == null) {
                return new ApiResponse<>("fail", null, "查無此文章");
            }

            // 處理文章內容，將圖片引用替換為實際圖片 URL
            String contentWithImages = restoreImagesInContent(article.getAcContext(), acId);

            // 創建 DTO 並設置處理過的內容
            ArticlesDTO articleDTO = new ArticlesDTO(article);
            articleDTO.setAcContext(contentWithImages);

            return new ApiResponse<>("success", articleDTO, "查詢成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "查詢失敗: " + e.getMessage());
        }
    }

    /**
     * 使用混合策略處理 HTML 內容中的圖片
     * 
     * @param htmlContent 原始 HTML 內容
     * @param articleId   文章ID，如果為 null 則只做預處理
     * @return 處理後的 HTML 內容
     */
    private String processHtmlContentWithHybridStrategy(String htmlContent, Integer articleId) {
        if (htmlContent == null || htmlContent.trim().isEmpty()) {
            return htmlContent;
        }

        // 匹配 base64 圖片的正則表達式
        Pattern pattern = Pattern.compile("<img[^>]*src=\"data:image/([^;]+);base64,([^\"]+)\"[^>]*>");
        Matcher matcher = pattern.matcher(htmlContent);

        StringBuffer result = new StringBuffer();
        int imageIndex = 1;

        while (matcher.find()) {
            String imageFormat = matcher.group(1); // jpeg, png 等
            String base64Data = matcher.group(2);
            String fullBase64 = "data:image/" + imageFormat + ";base64," + base64Data;

            if (articleId != null) {
                try {
                    // 計算 Base64 解碼後的檔案大小
                    byte[] imageBytes = Base64.getDecoder().decode(base64Data);
                    long fileSize = imageBytes.length;

                    // 使用混合策略決定處理方式
                    HybridImageUploadService.UploadStrategy strategy = hybridImageUploadService
                            .determineStrategy(fileSize);

                    ArticleImageVO imageVO = null;

                    switch (strategy) {
                        case BASE64:
                            // 小檔案使用 Base64 策略
                            imageVO = hybridImageUploadService.processBase64Image(fullBase64, articleId);
                            break;

                        default:
                            // 對於較大的檔案，暫時還是使用原始方法，但記錄警告
                            System.out.println("警告：檔案大小 " + fileSize + " bytes 超過 Base64 策略限制，使用降級處理");
                            // 創建 ArticleImageVO 並保存到資料庫（降級處理）
                            imageVO = new ArticleImageVO();
                            imageVO.setAcImg(imageBytes);
                            ArticlesVO articlesVO = new ArticlesVO();
                            articlesVO.setAcId(articleId);
                            imageVO.setArticlesVO(articlesVO);
                            articleImageService.addArticleImage(imageVO);
                            break;
                    }

                    if (imageVO != null) {
                        // 替換為圖片引用（使用完整API前綴）
                        String replacement = String.format(
                                "<img src=\"%s/api/article-images/%d/image\" alt=\"文章圖片%d\" class=\"article-image\" data-image-id=\"%d\" data-strategy=\"%s\">",
                                getApiPrefix(), imageVO.getAcImgId(), imageIndex, imageVO.getAcImgId(),
                                strategy.name());
                        matcher.appendReplacement(result, replacement);
                        imageIndex++;
                    } else {
                        // 處理失敗的情況
                        String replacement = String.format("<img alt=\"圖片處理失敗\" class=\"article-image-error\">");
                        matcher.appendReplacement(result, replacement);
                    }

                } catch (Exception e) {
                    System.err.println("使用混合策略處理圖片時發生錯誤: " + e.getMessage());
                    // 如果處理失敗，保留原始標籤但移除 base64 數據以節省空間
                    String replacement = String.format("<img alt=\"圖片載入失敗\" class=\"article-image-error\">");
                    matcher.appendReplacement(result, replacement);
                }
            } else {
                // 預處理階段，暫時用占位符替換
                String replacement = String.format("[[IMAGE_PLACEHOLDER_%d]]", imageIndex);
                matcher.appendReplacement(result, replacement);
                imageIndex++;
            }
        }

        matcher.appendTail(result);
        return result.toString();
    }

    /**
     * 舊版處理 HTML 內容中的圖片（保留向後兼容性）
     * 
     * @param htmlContent 原始 HTML 內容
     * @param articleId   文章ID，如果為 null 則只做預處理
     * @return 處理後的 HTML 內容
     */
    private String processHtmlContentWithImages(String htmlContent, Integer articleId) {
        if (htmlContent == null || htmlContent.trim().isEmpty()) {
            return htmlContent;
        }

        // 匹配 base64 圖片的正則表達式
        Pattern pattern = Pattern.compile("<img[^>]*src=\"data:image/([^;]+);base64,([^\"]+)\"[^>]*>");
        Matcher matcher = pattern.matcher(htmlContent);

        StringBuffer result = new StringBuffer();
        int imageIndex = 1;

        while (matcher.find()) {
            String imageFormat = matcher.group(1); // jpeg, png 等
            String base64Data = matcher.group(2);

            if (articleId != null) {
                try {
                    // 解碼 base64 數據
                    byte[] imageBytes = Base64.getDecoder().decode(base64Data);

                    // 創建 ArticleImageVO 並保存到資料庫
                    ArticleImageVO imageVO = new ArticleImageVO();
                    imageVO.setAcImg(imageBytes);

                    // 設置關聯的文章
                    ArticlesVO articlesVO = new ArticlesVO();
                    articlesVO.setAcId(articleId);
                    imageVO.setArticlesVO(articlesVO);

                    // 保存圖片
                    articleImageService.addArticleImage(imageVO);

                    // 替換為圖片引用（使用完整API前綴）
                    String replacement = String.format(
                            "<img src=\"%s/api/article-images/%d/image\" alt=\"文章圖片%d\" class=\"article-image\" data-image-id=\"%d\">",
                            getApiPrefix(), imageVO.getAcImgId(), imageIndex, imageVO.getAcImgId());
                    matcher.appendReplacement(result, replacement);
                    imageIndex++;

                } catch (Exception e) {
                    System.err.println("處理圖片時發生錯誤: " + e.getMessage());
                    // 如果處理失敗，保留原始標籤但移除 base64 數據以節省空間
                    String replacement = String.format("<img alt=\"圖片載入失敗\" class=\"article-image-error\">");
                    matcher.appendReplacement(result, replacement);
                }
            } else {
                // 預處理階段，暫時用占位符替換
                String replacement = String.format("[[IMAGE_PLACEHOLDER_%d]]", imageIndex);
                matcher.appendReplacement(result, replacement);
                imageIndex++;
            }
        }

        matcher.appendTail(result);
        return result.toString();
    }

    /**
     * 在顯示文章時恢復圖片內容
     * 
     * @param htmlContent 存儲的 HTML 內容
     * @param articleId   文章ID
     * @return 包含完整圖片的 HTML 內容
     */
    private String restoreImagesInContent(String htmlContent, Integer articleId) {
        if (htmlContent == null || htmlContent.trim().isEmpty()) {
            return htmlContent;
        }

        // 確保圖片 URL 包含完整的 API 前綴
        String result = htmlContent.replaceAll(
                "src=\"/api/article-images/",
                String.format("src=\"%s/api/article-images/", getApiPrefix()));

        return result;
    }

    /**
     * 獲取 API 前綴，用於構建完整的圖片 URL
     */
    private String getApiPrefix() {
        // 根據主管規定使用完整的API前綴
        return "http://localhost:8081/CJA101G02";
    }

    /**
     * API: GET /api/articles/by-reply-member?memName={memName}
     * 根據留言者姓名查詢所有有該留言者留言的文章
     */
    @GetMapping("/api/articles/by-reply-member")
    public ApiResponse<List<ArticlesDTO>> getArticlesByReplyMemberName(@RequestParam("memName") String memName) {
        List<ArticlesVO> articles = articlesService.findArticlesByReplyMemberName(memName);
        List<ArticlesDTO> articlesDTOs = articles.stream()
                .map(ArticlesDTO::new)
                .collect(Collectors.toList());
        return new ApiResponse<>("success", articlesDTOs, "查詢成功");
    }

    // 根據露營者名稱查詢文章
    @GetMapping("/api/articles/search/by-member-name")
    public ApiResponse<List<ArticlesDTO>> searchArticlesByMemberName(@RequestParam("memName") String memName) {
        List<ArticlesVO> articles = articlesService.findByMemberName(memName);
        List<ArticlesDTO> articlesDTOs = articles.stream()
                .map(ArticlesDTO::new)
                .collect(Collectors.toList());
        return new ApiResponse<>("success", articlesDTOs, "查詢成功");
    }

    /**
     * 取得指定文章的瀏覽次數
     */
    @GetMapping("/api/articles/{acId}/view-count")
    public ApiResponse<Long> getArticleViewCount(@PathVariable Integer acId) {
        ArticlesVO article = articlesService.getOneArticles(acId);
        if (article != null) {
            return new ApiResponse<>("success", article.getAcViewCount(), "查詢成功");
        } else {
            return new ApiResponse<>("fail", null, "查無此文章");
        }
    }

    /**
     * 指定文章瀏覽次數+1，並回傳最新值
     */
    @PostMapping("/api/articles/{acId}/view")
    public ApiResponse<Long> increaseArticleViewCount(@PathVariable Integer acId) {
        Long newCount = articlesService.increaseViewCount(acId);
        if (newCount != null) {
            return new ApiResponse<>("success", newCount, "瀏覽次數已更新");
        } else {
            return new ApiResponse<>("fail", null, "查無此文章");
        }
    }

    // === 混合策略智能文章端點 ===

    /**
     * 智能創建文章，自動根據圖片大小選擇最佳處理策略
     */
    @PostMapping("/api/articles/smart-create")
    public ApiResponse<Map<String, Object>> smartCreateArticle(@RequestBody ArticlesDTO dto) {
        try {
            // 分析文章內容中的圖片
            Map<String, Object> analysis = performImageAnalysis(dto.getAcContext());
            List<Map<String, Object>> imageAnalysis = (List<Map<String, Object>>) analysis.get("images");

            // 檢查是否有大檔案需要特殊處理
            boolean hasLargeImages = imageAnalysis.stream()
                    .anyMatch(img -> {
                        HybridImageUploadService.UploadStrategy strategy = (HybridImageUploadService.UploadStrategy) img
                                .get("strategy");
                        return strategy == HybridImageUploadService.UploadStrategy.CHUNKED_UPLOAD;
                    });

            if (hasLargeImages) {
                // 如果有大檔案，返回分析結果，建議前端使用分塊上傳
                Map<String, Object> response = new HashMap<>();
                response.put("requiresChunkedUpload", true);
                response.put("analysis", analysis);
                response.put("message", "檢測到大檔案，請使用分塊上傳功能");
                return new ApiResponse<>("chunked_required", response, "需要分塊上傳");
            } else {
                // 正常處理
                ApiResponse<ArticlesDTO> result = createArticleWithImages(dto);
                Map<String, Object> response = new HashMap<>();
                response.put("article", result.getData());
                response.put("analysis", analysis);
                response.put("requiresChunkedUpload", false);
                return new ApiResponse<>(result.getStatus(), response, result.getMessage());
            }

        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "智能創建失敗: " + e.getMessage());
        }
    }

    /**
     * 分析文章內容中的圖片
     */
    @PostMapping("/api/articles/analyze-images")
    public ApiResponse<Map<String, Object>> analyzeArticleImagesAPI(@RequestBody String htmlContent) {
        try {
            Map<String, Object> analysis = performImageAnalysis(htmlContent);
            return new ApiResponse<>("success", analysis, "圖片分析完成");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "圖片分析失敗: " + e.getMessage());
        }
    }

    /**
     * 分析 HTML 內容中的圖片資訊
     */
    private Map<String, Object> performImageAnalysis(String htmlContent) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> images = new ArrayList<>();

        if (htmlContent == null || htmlContent.trim().isEmpty()) {
            result.put("totalImages", 0);
            result.put("images", images);
            result.put("totalSize", 0L);
            result.put("strategies", new HashMap<>());
            return result;
        }

        // 匹配 base64 圖片的正則表達式
        Pattern pattern = Pattern.compile("<img[^>]*src=\"data:image/([^;]+);base64,([^\"]+)\"[^>]*>");
        Matcher matcher = pattern.matcher(htmlContent);

        long totalSize = 0;
        Map<String, Integer> strategyCounts = new HashMap<>();
        strategyCounts.put("BASE64", 0);
        strategyCounts.put("DIRECT_UPLOAD", 0);
        strategyCounts.put("CHUNKED_UPLOAD", 0);

        int imageIndex = 0;
        while (matcher.find()) {
            String imageFormat = matcher.group(1);
            String base64Data = matcher.group(2);

            try {
                byte[] imageBytes = Base64.getDecoder().decode(base64Data);
                long fileSize = imageBytes.length;
                totalSize += fileSize;

                HybridImageUploadService.UploadStrategy strategy = hybridImageUploadService.determineStrategy(fileSize);

                Map<String, Object> imageInfo = new HashMap<>();
                imageInfo.put("index", imageIndex);
                imageInfo.put("format", imageFormat);
                imageInfo.put("size", fileSize);
                imageInfo.put("sizeMB", String.format("%.2f", fileSize / 1024.0 / 1024.0));
                imageInfo.put("strategy", strategy);
                imageInfo.put("strategyName", strategy.name());

                images.add(imageInfo);
                strategyCounts.put(strategy.name(), strategyCounts.get(strategy.name()) + 1);
                imageIndex++;

            } catch (Exception e) {
                System.err.println("分析圖片時發生錯誤: " + e.getMessage());
            }
        }

        result.put("totalImages", images.size());
        result.put("images", images);
        result.put("totalSize", totalSize);
        result.put("totalSizeMB", String.format("%.2f", totalSize / 1024.0 / 1024.0));
        result.put("strategies", strategyCounts);

        return result;
    }

    /**
     * 獲取圖片上傳建議
     */
    @GetMapping("/api/articles/upload-recommendations")
    public ApiResponse<Map<String, Object>> getUploadRecommendations() {
        Map<String, Object> recommendations = new HashMap<>();

        Map<String, Object> base64Rec = new HashMap<>();
        base64Rec.put("maxSize", "2MB");
        base64Rec.put("description", "小檔案，直接嵌入文章內容");
        base64Rec.put("advantages", List.of("處理快速", "操作簡單", "即時預覽"));
        base64Rec.put("limitations", List.of("檔案大小限制", "增加傳輸量"));

        Map<String, Object> directRec = new HashMap<>();
        directRec.put("maxSize", "10MB");
        directRec.put("description", "中等檔案，直接上傳處理");
        directRec.put("advantages", List.of("支援較大檔案", "壓縮優化", "效能良好"));
        directRec.put("limitations", List.of("需要檔案上傳介面"));

        Map<String, Object> chunkedRec = new HashMap<>();
        chunkedRec.put("maxSize", "50MB+");
        chunkedRec.put("description", "大檔案，分塊上傳支援斷點續傳");
        chunkedRec.put("advantages", List.of("支援超大檔案", "斷點續傳", "進度顯示"));
        chunkedRec.put("limitations", List.of("實作複雜", "需要前端配合"));

        recommendations.put("BASE64", base64Rec);
        recommendations.put("DIRECT_UPLOAD", directRec);
        recommendations.put("CHUNKED_UPLOAD", chunkedRec);

        Map<String, Object> response = new HashMap<>();
        response.put("strategies", recommendations);
        response.put("defaultChunkSize", "1MB");
        response.put("sessionTimeout", "24小時");

        return new ApiResponse<>("success", response, "上傳建議獲取成功");
    }

    // === 文章與圖片整合 API ===

    /**
     * 取得文章的所有圖片（整合文章圖片模組的功能）
     */
    @GetMapping("/api/articles/{acId}/images")
    public ApiResponse<List<ArticleImageVO>> getArticleImages(@PathVariable Integer acId) {
        try {
            // 先檢查文章是否存在
            ArticlesVO article = articlesService.getOneArticles(acId);
            if (article == null) {
                return new ApiResponse<>("fail", null, "查無此文章");
            }

            List<ArticleImageVO> images = articleImageService.getArticleImagesByArticleId(acId);
            return new ApiResponse<>("success", images, "查詢成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "查詢失敗: " + e.getMessage());
        }
    }

    /**
     * 刪除文章時同時刪除相關圖片（級聯刪除）
     */
    @DeleteMapping("/api/articles/{acId}/cascade")
    public ApiResponse<Map<String, Object>> deleteArticleWithImages(@PathVariable Integer acId) {
        try {
            // 先檢查文章是否存在
            ArticlesVO article = articlesService.getOneArticles(acId);
            if (article == null) {
                return new ApiResponse<>("fail", null, "查無此文章");
            }

            // 先獲取相關圖片列表
            List<ArticleImageVO> images = articleImageService.getArticleImagesByArticleId(acId);

            // 刪除相關圖片
            int deletedImageCount = 0;
            for (ArticleImageVO image : images) {
                try {
                    articleImageService.deleteArticleImage(image.getAcImgId());
                    deletedImageCount++;
                } catch (Exception e) {
                    System.err.println("刪除圖片失敗: " + image.getAcImgId() + ", 錯誤: " + e.getMessage());
                }
            }

            // 刪除文章
            articlesService.deleteArticles(acId);

            Map<String, Object> result = new HashMap<>();
            result.put("deletedArticleId", acId);
            result.put("deletedImageCount", deletedImageCount);
            result.put("totalImageCount", images.size());

            return new ApiResponse<>("success", result, "文章及相關圖片刪除成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "刪除失敗: " + e.getMessage());
        }
    }

    /**
     * 取得文章統計資訊（包含圖片數量）
     */
    @GetMapping("/api/articles/{acId}/stats")
    public ApiResponse<Map<String, Object>> getArticleStats(@PathVariable Integer acId) {
        try {
            ArticlesVO article = articlesService.getOneArticles(acId);
            if (article == null) {
                return new ApiResponse<>("fail", null, "查無此文章");
            }

            List<ArticleImageVO> images = articleImageService.getArticleImagesByArticleId(acId);

            Map<String, Object> stats = new HashMap<>();
            stats.put("acId", acId);
            stats.put("title", article.getAcTitle());
            stats.put("viewCount", article.getAcViewCount());
            stats.put("imageCount", images.size());
            stats.put("contentLength", article.getAcContext() != null ? article.getAcContext().length() : 0);
            stats.put("publishTime", article.getAcTime());
            stats.put("status", article.getAcStatus());

            // 計算圖片總大小
            long totalImageSize = images.stream()
                    .mapToLong(img -> img.getAcImg() != null ? img.getAcImg().length : 0)
                    .sum();
            stats.put("totalImageSize", totalImageSize);
            stats.put("totalImageSizeMB", String.format("%.2f", totalImageSize / 1024.0 / 1024.0));

            return new ApiResponse<>("success", stats, "統計資訊獲取成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "統計資訊獲取失敗: " + e.getMessage());
        }
    }
}
