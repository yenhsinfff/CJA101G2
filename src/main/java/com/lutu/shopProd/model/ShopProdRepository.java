// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.lutu.shopProd.model;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ShopProdRepository extends JpaRepository<ShopProdVO, Integer> { //(VO,PK型別)

	/*
	//查詢全部商品 JPQL
	@Query("""
			SELECT p FROM ShopProdVO p
			 LEFT JOIN FETCH p.prodTypeVO 
			""")
	List<ShopProdVO> selectAllProducts();
	
	//用ID查詢
	@Query("""
			SELECT p FROM ShopProdVO p
			 LEFT JOIN FETCH p.prodTypeVO 
			 WHERE p.prodId = ?1
			""")
	Optional<ShopProdVO> selectProdById(Integer prodId);
	
	@Query("SELECT p FROM ShopProdVO p WHERE p.prodName LIKE %:name%")
	List<ShopProdVO> selectProductsByKeyword(@Param("name") String name);
	
	@Query("SELECT p FROM ShopProdVO p JOIN FETCH p.prodTypeVO")
	List<ShopProdVO> selectProductsByType();
	*/
	
	// 關鍵字查詢（名稱或介紹）
	@Query("SELECT p FROM ShopProdVO p WHERE p.prodName LIKE %:keyword% OR p.prodIntro LIKE %:keyword%")
	List<ShopProdVO> findByKeyword(@Param("keyword") String keyword);

	// 類別查詢
	List<ShopProdVO> findProdByProdType(Integer prodTypeId);

	// 最新上架（依上架日期倒序）
	List<ShopProdVO> findByReleaseDateDesc();

	// 折扣查詢（折扣不為空且大於 0）
	@Query("SELECT p FROM ShopProdVO p WHERE p.prodDiscount IS NOT NULL AND p.prodDiscount > 0")
	List<ShopProdVO> findByDiscounted();

	// 隨機推薦（隨機取 N 筆，這個用 Native SQL 效率較佳）
	@Query(value = "SELECT * FROM shop_prod ORDER BY RAND() LIMIT :limit", nativeQuery = true)
	List<ShopProdVO> findRandom(@Param("limit") int limit);

//	@Modifying
//	@Transactional
//	@Query("""
//			 UPDATE ShopProdVO p SET
//			    p.prodName = :name, 
//			    p.prodIntro = :intro, 
//			    p.prodDiscount = :discount,
//			    p.prodDiscountStart = :start,
//			    p.prodDiscountEnd = :end,
//			    p.prodTypeVO = :type,
//			    p.prodStatus = :status,
//			    p.prodColorOrNot = :color
//			    WHERE p.prodId = :id
//			""")
//	void updateProdById(@Param("id") Integer id,
//				@Param("name") String name,
//				@Param("intro") String intro,
//				@Param("discount") BigDecimal discount,
//				@Param("start") Timestamp start,
//				@Param("end") Timestamp end,
//				@Param("type") ProdTypeVO type,
//				@Param("status") Byte status,
//				@Param("color") Byte color);

//	@Transactional（確保操作在交易中進行）
//	@Modifying（告訴 Spring 這是資料操作語句）
//	@Query(value = "delete from shop_prod where prod_id =?1", nativeQuery = true)
//	void deleteByProdId(int prodId);

	//● (自訂)條件查詢
	//@Query(value = "from ShopProdVO where prodId=?1 and prodName like?2 and prodReleaseDate=?3 order by prodId")
	//List<ShopProdVO> findByOthers(Integer prodId , String prodName , Timestamp prodReleaseDate);


	
	
	
}