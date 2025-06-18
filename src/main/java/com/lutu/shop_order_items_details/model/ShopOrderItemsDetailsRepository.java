package com.lutu.shop_order_items_details.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShopOrderItemsDetailsRepository extends JpaRepository<ShopOrderItemsDetailsVO, Integer>{
	
	// 依商品訂單查詢
	@Query(value = "SELECT soid FROM ShopOrderItemsDetailsVO soid WHERE soid.shopOrderId = :shopOrderId")
	List<ShopOrderItemsDetailsVO> findByShopOrderId(Integer shopOrderId);

}
