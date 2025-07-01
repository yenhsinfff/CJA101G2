package com.lutu.ac_fav_record.controller;

import com.lutu.ApiResponse;
import com.lutu.ac_fav_record.model.AcFavRecordService;
import com.lutu.ac_fav_record.model.AcFavRecordVO;
import com.lutu.member.model.MemberRepository;
import com.lutu.article.model.ArticlesRepository;
import com.lutu.member.model.MemberVO;
import com.lutu.article.model.ArticlesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class AcFavRecordApiController {
    @Autowired
    AcFavRecordService acFavRecordService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ArticlesRepository articlesRepository;

    // 取得所有收藏記錄
    @GetMapping("/api/favorites")
    public ApiResponse<List<AcFavRecordDTO>> getAllFavorites() {
        List<AcFavRecordVO> favorites = acFavRecordService.getAll();
        List<AcFavRecordDTO> favoriteDTOs = favorites.stream()
                .map(AcFavRecordDTO::new)
                .collect(Collectors.toList());
        return new ApiResponse<>("success", favoriteDTOs, "查詢成功");
    }

    // 取得所有收藏記錄數量
    @GetMapping("/api/favorites/count")
    public ApiResponse<Long> getFavoritesCount() {
        List<AcFavRecordVO> favorites = acFavRecordService.getAll();
        Long count = (long) favorites.size();
        return new ApiResponse<>("success", count, "查詢成功");
    }

    // 根據會員ID取得該會員的所有收藏記錄
    @GetMapping("/api/favorites/member/{memId}")
    public ApiResponse<List<AcFavRecordDTO>> getFavoritesByMember(@PathVariable Integer memId) {
        try {
            // 先確認會員是否存在
            MemberVO member = memberRepository.findById(memId).orElse(null);
            if (member == null) {
                return new ApiResponse<>("fail", null, "查無此會員");
            }

            List<AcFavRecordVO> favorites = acFavRecordService.getByMemId(memId);
            List<AcFavRecordDTO> favoriteDTOs = favorites.stream()
                    .map(AcFavRecordDTO::new)
                    .collect(Collectors.toList());

            return new ApiResponse<>("success", favoriteDTOs, "查詢成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "查詢失敗: " + e.getMessage());
        }
    }

    // 根據會員ID取得該會員的收藏數量
    @GetMapping("/api/favorites/member/{memId}/count")
    public ApiResponse<Long> getFavoriteCountByMember(@PathVariable Integer memId) {
        try {
            // 先確認會員是否存在
            MemberVO member = memberRepository.findById(memId).orElse(null);
            if (member == null) {
                return new ApiResponse<>("fail", null, "查無此會員");
            }

            List<AcFavRecordVO> favorites = acFavRecordService.getByMemId(memId);
            Long count = (long) favorites.size();

            return new ApiResponse<>("success", count, "查詢成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", 0L, "查詢失敗: " + e.getMessage());
        }
    }

    // 根據文章ID取得該文章的所有收藏記錄
    @GetMapping("/api/favorites/article/{acId}")
    public ApiResponse<List<AcFavRecordDTO>> getFavoritesByArticle(@PathVariable Integer acId) {
        try {
            // 先確認文章是否存在
            ArticlesVO article = articlesRepository.findById(acId).orElse(null);
            if (article == null) {
                return new ApiResponse<>("fail", null, "查無此文章");
            }

            List<AcFavRecordVO> favorites = acFavRecordService.getByAcId(acId);
            List<AcFavRecordDTO> favoriteDTOs = favorites.stream()
                    .map(AcFavRecordDTO::new)
                    .collect(Collectors.toList());

            return new ApiResponse<>("success", favoriteDTOs, "查詢成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "查詢失敗: " + e.getMessage());
        }
    }

    // 根據文章ID取得該文章的收藏數量
    @GetMapping("/api/favorites/article/{acId}/count")
    public ApiResponse<Long> getFavoriteCountByArticle(@PathVariable Integer acId) {
        try {
            // 先確認文章是否存在
            ArticlesVO article = articlesRepository.findById(acId).orElse(null);
            if (article == null) {
                return new ApiResponse<>("fail", null, "查無此文章");
            }

            List<AcFavRecordVO> favorites = acFavRecordService.getByAcId(acId);
            Long count = (long) favorites.size();

            return new ApiResponse<>("success", count, "查詢成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", 0L, "查詢失敗: " + e.getMessage());
        }
    }

    // 檢查特定會員是否已收藏特定文章
    @GetMapping("/api/favorites/check")
    public ApiResponse<Boolean> checkFavorite(
            @RequestParam("acId") Integer acId,
            @RequestParam("memId") Integer memId) {
        try {
            // 先確認會員和文章是否存在
            MemberVO member = memberRepository.findById(memId).orElse(null);
            if (member == null) {
                return new ApiResponse<>("fail", null, "查無此會員");
            }

            ArticlesVO article = articlesRepository.findById(acId).orElse(null);
            if (article == null) {
                return new ApiResponse<>("fail", null, "查無此文章");
            }

            boolean isFavorited = acFavRecordService.isFavorited(acId, memId);
            return new ApiResponse<>("success", isFavorited, "查詢成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", false, "查詢失敗: " + e.getMessage());
        }
    }

    // 新增收藏
    @PostMapping("/api/favorites")
    public ApiResponse<AcFavRecordDTO> addFavorite(@Valid @RequestBody AcFavRecordDTO dto) {
        // 記錄請求資料，方便除錯
        System.out.println("收到新增收藏請求: " + dto.toString());
        try {
            // 先檢查是否已經收藏
            boolean alreadyFavorited = acFavRecordService.isFavorited(dto.getAcId(), dto.getMemId());
            if (alreadyFavorited) {
                return new ApiResponse<>("fail", null, "已經收藏過此文章");
            }

            // 查詢並確認會員存在
            MemberVO member = memberRepository.findById(dto.getMemId()).orElse(null);
            if (member == null) {
                return new ApiResponse<>("fail", null, "查無此會員");
            }

            // 查詢並確認文章存在
            ArticlesVO article = articlesRepository.findById(dto.getAcId()).orElse(null);
            if (article == null) {
                return new ApiResponse<>("fail", null, "查無此文章");
            }

            // 建立收藏記錄
            AcFavRecordVO vo = new AcFavRecordVO();
            vo.setAcId(dto.getAcId());
            vo.setMemId(dto.getMemId());
            // 不設定 articlesVO，避免關聯映射問題

            // 設定收藏時間為當前時間
            if (dto.getAcFavTime() == null) {
                vo.setAcFavTime(java.time.LocalDateTime.now());
            } else {
                vo.setAcFavTime(dto.getAcFavTime());
            }

            acFavRecordService.addAcFavRecord(vo);

            // 回傳包含完整資料的 DTO
            AcFavRecordDTO favoriteDTO = new AcFavRecordDTO(vo);
            return new ApiResponse<>("success", favoriteDTO, "收藏成功");
        } catch (Exception e) {
            // 記錄詳細錯誤訊息，方便除錯
            System.err.println("新增收藏失敗: " + e.getMessage());
            e.printStackTrace();
            return new ApiResponse<>("fail", null, "收藏失敗: " + e.getMessage());
        }
    }

    // 取消收藏 - 支援兩種格式
    @DeleteMapping("/api/favorites")
    public ApiResponse<Void> removeFavorite(
            @RequestParam("acId") Integer acId,
            @RequestParam("memId") Integer memId) {
        try {
            // 先檢查是否已經收藏
            boolean isFavorited = acFavRecordService.isFavorited(acId, memId);
            if (!isFavorited) {
                return new ApiResponse<>("fail", null, "尚未收藏此文章");
            }

            // 確認會員存在
            MemberVO member = memberRepository.findById(memId).orElse(null);
            if (member == null) {
                return new ApiResponse<>("fail", null, "查無此會員");
            }

            // 確認文章存在
            ArticlesVO article = articlesRepository.findById(acId).orElse(null);
            if (article == null) {
                return new ApiResponse<>("fail", null, "查無此文章");
            }

            acFavRecordService.deleteAcFavRecord(acId, memId);
            return new ApiResponse<>("success", null, "取消收藏成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "取消收藏失敗: " + e.getMessage());
        }
    }

    // 取消收藏 - 路徑參數格式 (支援前端呼叫)
    @DeleteMapping("/api/favorites/{memId}/{acId}")
    public ApiResponse<Void> removeFavoriteByPath(
            @PathVariable Integer memId,
            @PathVariable Integer acId) {
        try {
            // 先檢查是否已經收藏
            boolean isFavorited = acFavRecordService.isFavorited(acId, memId);
            if (!isFavorited) {
                return new ApiResponse<>("fail", null, "尚未收藏此文章");
            }

            // 確認會員存在
            MemberVO member = memberRepository.findById(memId).orElse(null);
            if (member == null) {
                return new ApiResponse<>("fail", null, "查無此會員");
            }

            // 確認文章存在
            ArticlesVO article = articlesRepository.findById(acId).orElse(null);
            if (article == null) {
                return new ApiResponse<>("fail", null, "查無此文章");
            }

            acFavRecordService.deleteAcFavRecord(acId, memId);
            return new ApiResponse<>("success", null, "取消收藏成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "取消收藏失敗: " + e.getMessage());
        }
    }

    // 切換收藏狀態（如果已收藏則取消，如果未收藏則新增）
    @PostMapping("/api/favorites/toggle")
    public ApiResponse<AcFavRecordDTO> toggleFavorite(@Valid @RequestBody AcFavRecordDTO dto) {
        try {
            // 確認會員存在
            MemberVO member = memberRepository.findById(dto.getMemId()).orElse(null);
            if (member == null) {
                return new ApiResponse<>("fail", null, "查無此會員");
            }

            // 確認文章存在
            ArticlesVO article = articlesRepository.findById(dto.getAcId()).orElse(null);
            if (article == null) {
                return new ApiResponse<>("fail", null, "查無此文章");
            }

            boolean isFavorited = acFavRecordService.isFavorited(dto.getAcId(), dto.getMemId());

            if (isFavorited) {
                // 如果已收藏，則取消收藏
                acFavRecordService.deleteAcFavRecord(dto.getAcId(), dto.getMemId());
                return new ApiResponse<>("success", null, "取消收藏成功");
            } else {
                // 如果未收藏，則新增收藏
                AcFavRecordVO vo = new AcFavRecordVO();
                vo.setAcId(dto.getAcId());
                vo.setMemId(dto.getMemId());
                // 不設定 articlesVO，避免關聯映射問題
                vo.setAcFavTime(java.time.LocalDateTime.now());

                acFavRecordService.addAcFavRecord(vo);

                AcFavRecordDTO favoriteDTO = new AcFavRecordDTO(vo);
                return new ApiResponse<>("success", favoriteDTO, "收藏成功");
            }
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "操作失敗: " + e.getMessage());
        }
    }

    // 取得會員收藏的文章ID列表
    @GetMapping("/api/favorites/member/{memId}/article-ids")
    public ApiResponse<List<Integer>> getFavoriteArticleIds(@PathVariable Integer memId) {
        try {
            // 先確認會員是否存在
            MemberVO member = memberRepository.findById(memId).orElse(null);
            if (member == null) {
                return new ApiResponse<>("fail", null, "查無此會員");
            }

            List<AcFavRecordVO> favorites = acFavRecordService.getByMemId(memId);
            List<Integer> articleIds = favorites.stream()
                    .map(AcFavRecordVO::getAcId)
                    .collect(Collectors.toList());

            return new ApiResponse<>("success", articleIds, "查詢成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "查詢失敗: " + e.getMessage());
        }
    }
}
