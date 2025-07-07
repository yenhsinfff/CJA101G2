package com.lutu.prodSpecList.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lutu.prodSpecList.model.ProdSpecListVO.CompositeDetail2;

public interface ProdSpecListRepository extends JpaRepository<ProdSpecListVO, CompositeDetail2>{


	//依商品編號查詢所有商品規格 
//    List<ProdSpecListVO> findByProdId(Integer prodId);
    
	@Query("SELECT vo FROM ProdSpecListVO vo WHERE vo.prodId = :prodId")
    List<ProdSpecListVO> findAllByProdId(@Param("prodId") Integer prodId);

	@Query("SELECT vo FROM ProdSpecListVO vo WHERE vo.prodId = :prodId AND vo.prodSpecId = :specId")
    ProdSpecListVO findByCompositeKey(@Param("prodId") Integer prodId, @Param("specId") Integer specId);

    // 尋找該商品的已上架規格
	@Query("SELECT vo FROM ProdSpecListVO vo WHERE vo.prodId = :prodId AND vo.prodSpecStatus = 1")
    List<ProdSpecListVO> findActiveSpecsByProdId(@Param("prodId") Integer prodId);
}

