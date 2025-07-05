package com.lutu.prodFavList.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lutu.prodFavList.model.ProdFavListVO.CompositeDetail;

public interface ProdFavListRepository extends JpaRepository<ProdFavListVO, CompositeDetail> {

    List<ProdFavListVO> findByCompositeKeyMemId(Integer memId);

    boolean existsByCompositeKeyMemIdAndCompositeKeyProdId(Integer memId, Integer prodId);

    void deleteByCompositeKeyMemIdAndCompositeKeyProdId(Integer memId, Integer prodId);
}

