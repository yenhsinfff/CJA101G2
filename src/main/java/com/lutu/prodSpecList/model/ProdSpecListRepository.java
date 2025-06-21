package com.lutu.prodSpecList.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lutu.prodSpecList.model.ProdSpecListVO.CompositeDetail2;

public interface ProdSpecListRepository extends JpaRepository<ProdSpecListVO, CompositeDetail2>{

	//依商品編號查詢所有規格 
    List<ProdSpecListVO> findByProdId(Integer prodId);
    
}
