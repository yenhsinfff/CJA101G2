package com.lutu.nice_article.controller;

import com.lutu.ApiResponse;
import com.lutu.nice_article.model.NiceArticleService;
import com.lutu.nice_article.model.NiceArticleVO;
import com.lutu.nice_article.model.NiceArticleId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/CJA101G02")
public class NiceArticleApiController {
    @Autowired
    private NiceArticleService niceArticleService;

    // http://localhost:8081/CJA101G02
    // 新增按讚
    @PostMapping("/api/likes")
    public ApiResponse<NiceArticleVO> addLike(@Valid @RequestBody NiceArticleVO vo) {
        if (niceArticleService.isLiked(vo.getAcId(), vo.getMemId())) {
            return new ApiResponse<>("fail", null, "已經按過讚");
        }
        niceArticleService.addNiceArticle(vo);
        return new ApiResponse<>("success", vo, "按讚成功");
    }

    // 取消按讚
    @DeleteMapping("/api/likes/article/{acId}/member/{memId}")
    public ApiResponse<Void> removeLike(@PathVariable Integer acId, @PathVariable Integer memId) {
        if (!niceArticleService.isLiked(acId, memId)) {
            return new ApiResponse<>("fail", null, "尚未按讚");
        }
        niceArticleService.deleteNiceArticle(acId, memId);
        return new ApiResponse<>("success", null, "取消按讚成功");
    }

    // 查詢某文章按讚數
    @GetMapping("/api/likes/article/{acId}/count")
    public ApiResponse<Long> getLikeCountByArticle(@PathVariable Integer acId) {
        long count = niceArticleService.getLikeCount(acId);
        return new ApiResponse<>("success", count, "查詢成功");
    }

    // 查詢會員是否已按讚
    @GetMapping("/api/likes/article/{acId}/member/{memId}/check")
    public ApiResponse<Boolean> checkLike(@PathVariable Integer acId, @PathVariable Integer memId) {
        boolean liked = niceArticleService.isLiked(acId, memId);
        return new ApiResponse<>("success", liked, "查詢成功");
    }

    // 查詢某文章所有按讚會員ID
    @GetMapping("/api/likes/article/{acId}/members")
    public ApiResponse<List<Integer>> getLikedMembersByArticle(@PathVariable Integer acId) {
        List<NiceArticleVO> likes = niceArticleService.getByAcId(acId);
        List<Integer> memberIds = likes.stream().map(NiceArticleVO::getMemId).toList();
        return new ApiResponse<>("success", memberIds, "查詢成功");
    }

    // 查詢所有按讚紀錄
    @GetMapping("/api/likes")
    public ApiResponse<List<NiceArticleVO>> getAllLikes() {
        List<NiceArticleVO> likes = niceArticleService.getAll();
        return new ApiResponse<>("success", likes, "查詢成功");
    }
}