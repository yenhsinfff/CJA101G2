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

@RestController
@CrossOrigin(origins = "*")
public class ArticlesApiController {
    @Autowired
    ArticlesService articlesService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ArticleTypeRepository articleTypeRepository;

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

    // 取得單一文章
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

            // 處理 Quill 編輯器內容，分離文字和圖片
            String processedContent = processQuillContent(dto.getAcContext());
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

    // 更新文章
    @PutMapping("/api/articles/{acId}")
    public ApiResponse<ArticlesDTO> updateArticle(@PathVariable Integer acId, @RequestBody ArticlesVO articlesVO) {
        try {
            articlesVO.setAcId(acId);
            articlesService.updateArticles(articlesVO);
            ArticlesDTO articleDTO = new ArticlesDTO(articlesVO);
            return new ApiResponse<>("success", articleDTO, "更新成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "更新失敗");
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
     * 新增包含圖片的文章
     */
    @PostMapping("/api/articles/with-images")
    public ApiResponse<ArticlesDTO> createArticleWithImages(@RequestBody ArticlesDTO dto) {
        try {
            // 先新增文章（純文字內容）
            ArticlesVO vo = new ArticlesVO();
            vo.setAcTitle(dto.getAcTitle());

            // 處理 Quill 編輯器內容，分離文字和圖片
            String processedContent = processQuillContent(dto.getAcContext());
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

            // 回傳包含完整資料的 DTO
            ArticlesDTO articleDTO = new ArticlesDTO(vo);
            return new ApiResponse<>("success", articleDTO, "文章新增成功，請使用 /api/article-images/upload 上傳圖片");

        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "新增失敗: " + e.getMessage());
        }
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
}
