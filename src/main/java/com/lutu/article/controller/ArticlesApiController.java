package com.lutu.article.controller;

import com.lutu.ApiResponse;
import com.lutu.article.model.ArticlesService;
import com.lutu.article.model.ArticlesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class ArticlesApiController {
    @Autowired
    ArticlesService articlesService;

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

    // 根據文章編號查詢文章數量
    @GetMapping("/api/articles/{acId}/count")
    public ApiResponse<Long> getArticleCountById(@PathVariable Integer acId) {
        ArticlesVO article = articlesService.getOneArticles(acId);
        if (article != null) {
            return new ApiResponse<>("success", 1L, "查詢成功");
        } else {
            return new ApiResponse<>("success", 0L, "查詢成功");
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
    public ApiResponse<ArticlesDTO> createArticle(@RequestBody ArticlesVO articlesVO) {
        try {
            // 設定發布時間為當前時間
            if (articlesVO.getAcTime() == null) {
                articlesVO.setAcTime(java.time.LocalDateTime.now());
            }

            // 設定預設狀態為顯示 (0)
            if (articlesVO.getAcStatus() == null) {
                articlesVO.setAcStatus((byte) 0);
            }

            articlesService.addArticles(articlesVO);

            // 回傳包含完整資料的 DTO
            ArticlesDTO articleDTO = new ArticlesDTO(articlesVO);
            return new ApiResponse<>("success", articleDTO, "新增成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "新增失敗: " + e.getMessage());
        }
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
}
