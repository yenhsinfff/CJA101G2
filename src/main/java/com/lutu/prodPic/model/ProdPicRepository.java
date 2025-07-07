package com.lutu.prodPic.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdPicRepository extends JpaRepository<ProdPicVO, Integer> {
	
	List<ProdPicVO> findByProdIdOrderByProdPicIdAsc(Integer prodId);
    
}
