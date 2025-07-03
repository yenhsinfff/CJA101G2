package com.lutu.nice_article.controller;

import com.lutu.ApiResponse;
import com.lutu.nice_article.model.NiceArticleService;
import com.lutu.nice_article.model.NiceArticleVO;
import com.lutu.nice_article.model.NiceArticleId;
import com.lutu.member.model.MemberRepository;
import com.lutu.article.model.ArticlesRepository;
import com.lutu.member.model.MemberVO;
import com.lutu.article.model.ArticlesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class NiceArticleApiController {
    @Autowired
    NiceArticleService niceArticleService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ArticlesRepository articlesRepository;

    // 取得所有按讚記錄
    @GetMapping("/api/nice-articles")
    public ApiResponse<List<NiceArticleVO>> getAllLikes() {
        List<NiceArticleVO> likes = niceArticleService.getAll();
        return new ApiResponse<>("success", likes, "查詢成功");
    }

    // 取得所有按讚記錄數量
    @GetMapping("/api/nice-articles/count")
    public ApiResponse<Long> getLikesCount() {
        long count = niceArticleService.getAll().size();
        return new ApiResponse<>("success", count, "查詢成功");
    }

    // 根據會員ID取得該會員的所有按讚記錄
    @GetMapping("/api/nice-articles/member/{memId}")
    public ApiResponse<List<NiceArticleVO>> getLikesByMember(@PathVariable Integer memId) {
        MemberVO member = memberRepository.findById(memId).orElse(null);
        if (member == null) {
            return new ApiResponse<>("fail", null, "查無此會員");
        }
        List<NiceArticleVO> likes = niceArticleService.getByMemId(memId);
        return new ApiResponse<>("success", likes, "查詢成功");
    }

    // 根據會員ID取得該會員的按讚數量
    @GetMapping("/api/nice-articles/member/{memId}/count")
    public ApiResponse<Long> getLikeCountByMember(@PathVariable Integer memId) {
        MemberVO member = memberRepository.findById(memId).orElse(null);
        if (member == null) {
            return new ApiResponse<>("fail", null, "查無此會員");
        }
        long count = niceArticleService.getMemberLikeCount(memId);
        return new ApiResponse<>("success", count, "查詢成功");
    }

    // 根據文章ID取得該文章的所有按讚記錄
    @GetMapping("/api/nice-articles/article/{acId}")
    public ApiResponse<List<NiceArticleVO>> getLikesByArticle(@PathVariable Integer acId) {
        ArticlesVO article = articlesRepository.findById(acId).orElse(null);
        if (article == null) {
            return new ApiResponse<>("fail", null, "查無此文章");
        }
        List<NiceArticleVO> likes = niceArticleService.getByAcId(acId);
        return new ApiResponse<>("success", likes, "查詢成功");
    }

    // 根據文章ID取得該文章的按讚數量
    @GetMapping("/api/nice-articles/article/{acId}/count")
    public ApiResponse<Long> getLikeCountByArticle(@PathVariable Integer acId) {
        ArticlesVO article = articlesRepository.findById(acId).orElse(null);
        if (article == null) {
            return new ApiResponse<>("fail", null, "查無此文章");
        }
        long count = niceArticleService.getLikeCount(acId);
        return new ApiResponse<>("success", count, "查詢成功");
    }

    // 檢查特定會員是否已按讚特定文章
    @GetMapping("/api/nice-articles/check")
    public ApiResponse<Boolean> checkLike(@RequestParam("acId") Integer acId, @RequestParam("memId") Integer memId) {
        MemberVO member = memberRepository.findById(memId).orElse(null);
        if (member == null) {
            return new ApiResponse<>("fail", null, "查無此會員");
        }
        ArticlesVO article = articlesRepository.findById(acId).orElse(null);
        if (article == null) {
            return new ApiResponse<>("fail", null, "查無此文章");
        }
        boolean isLiked = niceArticleService.isLiked(acId, memId);
        return new ApiResponse<>("success", isLiked, "查詢成功");
    }

    // 新增按讚
    @PostMapping("/api/nice-articles")
    public ApiResponse<NiceArticleVO> addLike(@Valid @RequestBody NiceArticleVO vo) {
        // 檢查是否已按讚
        boolean alreadyLiked = niceArticleService.isLiked(vo.getAcId(), vo.getMemId());
        if (alreadyLiked) {
            return new ApiResponse<>("fail", null, "已經按讚過此文章");
        }
        // 驗證會員與文章存在
        MemberVO member = memberRepository.findById(vo.getMemId()).orElse(null);
        if (member == null) {
            return new ApiResponse<>("fail", null, "查無此會員: " + vo.getMemId());
        }
        ArticlesVO article = articlesRepository.findById(vo.getAcId()).orElse(null);
        if (article == null) {
            return new ApiResponse<>("fail", null, "查無此文章: " + vo.getAcId());
        }
        // 設定按讚時間
        if (vo.getLikeTime() == null) {
            vo.setLikeTime(LocalDateTime.now());
        }
        niceArticleService.addNiceArticle(vo);
        return new ApiResponse<>("success", vo, "按讚成功");
    }

    // 取消按讚（支援兩種格式）
    @DeleteMapping("/api/nice-articles")
    public ApiResponse<Void> removeLike(@RequestParam("acId") Integer acId, @RequestParam("memId") Integer memId) {
        if (!niceArticleService.isLiked(acId, memId)) {
            return new ApiResponse<>("fail", null, "尚未按讚");
        }
        niceArticleService.deleteNiceArticle(acId, memId);
        return new ApiResponse<>("success", null, "取消按讚成功");
    }

    @DeleteMapping("/api/nice-articles/{memId}/{acId}")
    public ApiResponse<Void> removeLikeByPath(@PathVariable Integer memId, @PathVariable Integer acId) {
        if (!niceArticleService.isLiked(acId, memId)) {
            return new ApiResponse<>("fail", null, "尚未按讚");
        }
        niceArticleService.deleteNiceArticle(acId, memId);
        return new ApiResponse<>("success", null, "取消按讚成功");
    }

    // 熱門文章（按讚數最多）
    @GetMapping("/api/nice-articles/popular")
    public ApiResponse<List<Object[]>> getPopularArticles() {
        List<Object[]> popular = niceArticleService.getPopularArticles();
        return new ApiResponse<>("success", popular, "查詢成功");
    }

    // 取得會員按讚過的所有文章（含文章資訊）
    @GetMapping("/api/nice-articles/member/{memId}/liked-articles")
    public ApiResponse<List<NiceArticleVO>> getLikedArticlesByMember(@PathVariable Integer memId) {
        MemberVO member = memberRepository.findById(memId).orElse(null);
        if (member == null) {
            return new ApiResponse<>("fail", null, "查無此會員");
        }
        List<NiceArticleVO> likedArticles = niceArticleService.getLikedArticlesByMember(memId);
        return new ApiResponse<>("success", likedArticles, "查詢成功");
    }
}
