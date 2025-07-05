package com.lutu.prodFavList.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.prodFavList.model.ProdFavListService;
import com.lutu.shopProd.model.ShopProdDTO;
import com.lutu.shopProd.model.ShopProdService;

@RestController
@RequestMapping("/api/prodfavorites")
public class ProdFavListController {

    @Autowired
    private ProdFavListService prodFavListService;

    @Autowired
    private ShopProdService shopProdService;

    /**
     * 加入收藏
     * POST /api/favorites/{memId}/{prodId}
     */
    @PostMapping("/{memId}/{prodId}")
    public ApiResponse<String> addFavorite(@PathVariable Integer memId,
                                           @PathVariable Integer prodId) {
        prodFavListService.addFavorite(memId, prodId);
        return new ApiResponse<>("success", "已加入收藏", "操作成功");
    }


    /**
     * 查詢會員所有收藏商品（含商品詳細資料）
     * GET /api/favorites/member/{memId}
     */
    @GetMapping("/member/{memId}")
    public ApiResponse<List<ShopProdDTO>> getMemberFavorites(@PathVariable Integer memId) {
        List<ShopProdDTO> favorites = prodFavListService.getFavoriteProducts(memId);
        return new ApiResponse<>("success", favorites, "查詢成功");
    }

    /**
     * 是否已收藏（給前端判斷愛心狀態）
     * GET /api/favorites/{memId}/{prodId}
     */
    @GetMapping("/isFavoriteOrNot/{memId}/{prodId}")
    public ApiResponse<Boolean> isFavorite(@PathVariable Integer memId,
                                           @PathVariable Integer prodId) {
        boolean isFav = prodFavListService.isFavorite(memId, prodId);
        return new ApiResponse<>("success", isFav, "查詢成功");
    }
    
    /**
     * 取消收藏
     * DELETE /api/favorites/{memId}/{prodId}
     */
    @DeleteMapping("/{memId}/{prodId}")
    public ApiResponse<String> removeFavorite(@PathVariable Integer memId,
                                              @PathVariable Integer prodId) {
        prodFavListService.removeFavorite(memId, prodId);
        return new ApiResponse<>("success", "已取消收藏", "操作成功");
    }
}
