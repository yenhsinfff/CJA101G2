// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.lutu.shopProd.model;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShopProdRepository extends JpaRepository<ShopProdVO, Integer> { //(VO,PK型別)

		// 關鍵字查詢（名稱或介紹）
		@Query("SELECT p FROM ShopProdVO p WHERE p.prodName LIKE %:keyword% OR p.prodIntro LIKE %:keyword%")
		List<ShopProdVO> findByKeyword(@Param("keyword") String keyword);

		// 類別查詢
		@Query("SELECT p FROM ShopProdVO p WHERE p.prodTypeVO.prodTypeId = :prodTypeId")
		List<ShopProdVO> findByProdType(Integer prodTypeId);

		// 最新上架（依上架日期倒序）
		@Query("SELECT p FROM ShopProdVO p ORDER BY p.prodReleaseDate DESC")
		List<ShopProdVO> findByReleaseDateDesc();

		// 折扣查詢（折扣不為空且大於 0）
		@Query("SELECT p FROM ShopProdVO p WHERE p.prodDiscount IS NOT NULL AND p.prodDiscount > 0")
		List<ShopProdVO> findByDiscounted();

		// 隨機推薦（隨機取 N 筆，這個用 Native SQL 效率較佳）
		@Query(value = "SELECT * FROM shop_prod ORDER BY RAND() LIMIT :limit", nativeQuery = true)
		List<ShopProdVO> findRandom(@Param("limit") int limit); //@Param("limit") 指定 Java 方法參數 int limit 對應到查詢中的 :limit 參數
	
	
}