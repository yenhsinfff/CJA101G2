package com.lutu.article_type.controller;

import com.lutu.ApiResponse;
import com.lutu.article_type.model.ArticleTypeRepository;
import com.lutu.article_type.model.ArticleTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ArticleTypeApiController {

    @Autowired
    ArticleTypeRepository articleTypeRepository;

    // 取得所有文章類別
    @GetMapping("/api/article-types")
    public ApiResponse<List<ArticleTypeVO>> getAllArticleTypes() {
        List<ArticleTypeVO> articleTypes = articleTypeRepository.findAll();
        return new ApiResponse<>("success", articleTypes, "查詢成功");
    }

    // 根據ID取得文章類別
    @GetMapping("/api/article-types/{acTypeId}")
    public ApiResponse<ArticleTypeVO> getArticleTypeById(@PathVariable Integer acTypeId) {
        ArticleTypeVO articleType = articleTypeRepository.findById(acTypeId).orElse(null);
        if (articleType != null) {
            return new ApiResponse<>("success", articleType, "查詢成功");
        } else {
            return new ApiResponse<>("fail", null, "查無此文章類別");
        }
    }
}