package com.lutu.article_image.controller;

import java.io.IOException;
import java.util.List;

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

import jakarta.servlet.http.HttpServletResponse;

@RestController
//@CrossOrigin(origins = "*")
public class ArticleImageApiController {

    @Autowired
    ArticleImageService articleImageService;

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
}
