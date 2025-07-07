package com.lutu.prodColorList.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lutu.prodColorList.model.ProdColorListVO.CompositeDetail;


public interface ProdColorListRepository extends JpaRepository<ProdColorListVO, CompositeDetail>{

	 List<ProdColorListVO> findByProdId(Integer prodId);
	 
	 List<ProdColorListVO> findByProdIdAndProdColorStatus(Integer prodId, Byte status);

}
