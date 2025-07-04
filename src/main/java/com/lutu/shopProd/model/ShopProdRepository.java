// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.lutu.shopProd.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShopProdRepository extends JpaRepository<ShopProdVO, Integer> { // (VO,PK型別)

	//查詢上架商品
	@Query("SELECT p FROM ShopProdVO p WHERE p.prodStatus = :status")
	List<ShopProdVO> findByStatus(@Param("status") int status);

	// 關鍵字查詢（名稱或介紹）
	@Query("SELECT p FROM ShopProdVO p WHERE p.prodStatus = 1 AND (p.prodName LIKE %:keyword% OR p.prodIntro LIKE %:keyword%)")
	List<ShopProdVO> findByKeyword(@Param("keyword") String keyword);

	// 類別查詢
	@Query("SELECT p FROM ShopProdVO p WHERE p.prodStatus = 1 AND p.prodTypeVO.prodTypeId = :prodTypeId")
	List<ShopProdVO> findByProdType(Integer prodTypeId);

	// 最新上架（依上架日期倒序）
	@Query(value = "SELECT * FROM shop_prod WHERE prod_status = 1 ORDER BY prod_release_date DESC LIMIT :limit", nativeQuery = true)
	List<ShopProdVO> findByReleaseDateDesc(@Param("limit") int limit);

	// 折扣查詢（折扣小於1 即為有折扣商品）
	@Query("SELECT p FROM ShopProdVO p WHERE p.prodStatus = 1 AND p.prodDiscount < 1")
	List<ShopProdVO> findByDiscounted();

	// 隨機推薦（隨機取 N 筆，這個用 Native SQL 效率較佳）
	@Query(value = "SELECT * FROM shop_prod WHERE prod_status = 1 ORDER BY RAND() LIMIT :limit", nativeQuery = true) // nativeQuery = true 使用原生 SQL
	List<ShopProdVO> findRandom(@Param("limit") int limit); // @Param("limit") 指定 Java 方法參數 int limit 對應到查詢中的 :limit 參數

	// 價格由低到高排序
	@Query("SELECT DISTINCT p FROM ShopProdVO p LEFT JOIN p.prodSpecList s WHERE p.prodStatus = 1 ORDER BY s.prodSpecPrice ASC")
	List<ShopProdVO> findAllByPriceAsc();

	// 價格由高到低排序
	@Query("SELECT DISTINCT p FROM ShopProdVO p LEFT JOIN p.prodSpecList s WHERE p.prodStatus = 1 ORDER BY s.prodSpecPrice DESC")
	List<ShopProdVO> findAllByPriceDesc();

	// 價格區間
	@Query("""
	    SELECT DISTINCT p FROM ShopProdVO p
	    JOIN p.prodSpecList s
	    WHERE p.prodStatus = 1 AND s.prodSpecPrice BETWEEN :min AND :max
			""")
	List<ShopProdVO> findByPriceBetween(@Param("min") int min, @Param("max") int max);

	// 價格大於某值
	@Query("""
	    SELECT DISTINCT p FROM ShopProdVO p
	    JOIN p.prodSpecList s
	    WHERE p.prodStatus = 1 AND s.prodSpecPrice >= :min
			""")
	List<ShopProdVO> findByPriceMoreThan(@Param("min") int min);

}