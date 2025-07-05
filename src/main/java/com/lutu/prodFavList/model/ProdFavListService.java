package com.lutu.prodFavList.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lutu.prodFavList.model.ProdFavListVO.CompositeDetail;
import com.lutu.shopProd.model.ShopProdDTO;
import com.lutu.shopProd.model.ShopProdService;

@Service
public class ProdFavListService {

    @Autowired
    private ProdFavListRepository repository;
    
    @Autowired
    private ShopProdService shopProdService;

    // 加入收藏
    public void addFavorite(Integer memId, Integer prodId) {
        CompositeDetail key = new CompositeDetail(memId, prodId);
        if (!repository.existsById(key)) {
            ProdFavListVO fav = new ProdFavListVO();
            fav.setCompositeKey(key);
            repository.save(fav);
        }
    }

    // 依照會員查看收藏商品
    public List<ShopProdDTO> getFavoriteProducts(Integer memId) {
        List<ProdFavListVO> list = repository.findByCompositeKeyMemId(memId);
        List<ShopProdDTO> dtoList = new ArrayList<>();

        for (ProdFavListVO vo : list) {
            Integer prodId = vo.getCompositeKey().getProdId();
            ShopProdDTO dto = shopProdService.getProdById(prodId);
            // 過濾已下架的商品
            if (dto != null && dto.getProdStatus() == 1) {
                dtoList.add(dto);
            }
        }

        return dtoList;
    }

    // 是否有收藏
    public boolean isFavorite(Integer memId, Integer prodId) {
        return repository.existsByCompositeKeyMemIdAndCompositeKeyProdId(memId, prodId);
    }
    
    // 移除收藏
    public void removeFavorite(Integer memId, Integer prodId) {
        CompositeDetail key = new CompositeDetail(memId, prodId);
        repository.deleteById(key);
    }

}
