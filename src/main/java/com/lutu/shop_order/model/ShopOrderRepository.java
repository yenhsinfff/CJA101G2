package com.lutu.shop_order.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lutu.member.model.MemberVO;

import io.lettuce.core.dynamic.annotation.Param;

public interface ShopOrderRepository extends JpaRepository<ShopOrderVO, Integer> {

//	@Transactional
//	@Modifying
//	@Query("UPDATE ShopOrderVO o set o.shopOrderShipment = :shopOrderShipment, o.shopOrderShipFee = :shopOrderShipFee, o.beforeDiscountAmount = :beforeDiscountAmount, o.discountCodeId = :discountCodeId, o.discountAmount = :discountAmount, o.afterDiscountAmount = :afterDiscountAmount, o.shopOrderPayment = :shopOrderPayment, o.orderName = :orderName, o.orderEmail = :orderEmail, o.orderPhone = :orderPhone, o.orderShippingAddress = :orderShippingAddress, o.shopOrderNote = :shopOrderNote, o.shopOrderShipDate = :shopOrderShipDate, o.shopOrderStatus = :shopOrderStatus, o.shopReturnApply = :shopReturnApply where o.shopOrderId = :shopOrderId")
//	void updateShopOrder(Byte shopOrderShipment, Integer	shopOrderShipFee, Integer	beforeDiscountAmount, 
//			String discountCodeId, Integer discountAmount, Integer afterDiscountAmount, Byte shopOrderPayment,
//			String orderName, String orderEmail, String orderPhone, String orderShippingAddress, String shopOrderNote,
//			LocalDateTime shopOrderShipDate, Byte shopOrderStatus, Byte shopReturnApply, Integer shopOrderId);
	
//	@Transactional
//	default void updateShopOrder(ShopOrderVO sovo) {
//		save(sovo);
//	}
//	
//
//	
//	List<ShopOrderVO> findAll();
//
////	@Query("SELECT o FROM ShopOrderVO o WHERE o.shopOrderId = :shopOrderId")
////	ShopOrderVO findByShopOrderId(Integer shopOrderId);
//	
//	//使用 findById 來單筆查詢
//	Optional<ShopOrderVO> findById(Integer shopOrderId);
//	
//	
//	//會員訂單資料查詢
//	@Query(value = "SELECT o FROM ShopOrderVO o WHERE o.memId = :memId")
//	List<ShopOrderVO> findByMember(Integer memId);

	
	//會員訂單資料查詢
	@Query(value = "from ShopOrderVO o where o.memId = :memId")
	List<ShopOrderVO> findByMember(MemberVO memId);
	
	
//	//訂單資料單筆查詢
//	@Query(value = "from ShopOrderVO o where o.shopOrderId = :shopOrderId")
//	ShopOrderVO getOneShopOrder(Integer shopOrderId);

}
