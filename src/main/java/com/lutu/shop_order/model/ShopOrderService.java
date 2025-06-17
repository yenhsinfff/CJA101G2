package com.lutu.shop_order.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import compositeQuery.HibernateUtil_CompositeQuery_ShopOrder;

@Service
public class ShopOrderService {

	@Autowired
	ShopOrderRepository sor;

	@Autowired
	private SessionFactory sessionFactory;

	public ShopOrderVO addShopOrder(ShopOrderDTO_insert dto) {

		ShopOrderVO sovo = new ShopOrderVO();

		sovo.setMemId(dto.getMemId());
		sovo.setShopOrderShipment(dto.getShopOrderShipment());
		sovo.setShopOrderShipFee(dto.getShopOrderShipFee());
		sovo.setBeforeDiscountAmount(dto.getBeforeDiscountAmount());
		sovo.setDiscountCodeId(dto.getDiscountCodeId());
		sovo.setDiscountAmount(dto.getDiscountAmount());
		sovo.setAfterDiscountAmount(dto.getAfterDiscountAmount());
		sovo.setShopOrderPayment(dto.getShopOrderPayment());
		sovo.setOrderName(dto.getOrderName());
		sovo.setOrderEmail(dto.getOrderEmail());
		sovo.setOrderPhone(dto.getOrderPhone());
		sovo.setOrderShippingAddress(dto.getOrderShippingAddress());
		sovo.setShopOrderNote(dto.getShopOrderNote());
		sovo.setShopOrderShipDate(dto.getShopOrderShipDate());

		sovo.setShopOrderStatus(dto.getShopOrderStatus() == null ? (byte) 0 : dto.getShopOrderStatus());
		sovo.setShopReturnApply(dto.getShopReturnApply() == null ? (byte) 0 : dto.getShopReturnApply());

		sor.save(sovo);
		ShopOrderVO sovo2 = getOneShopOrder(sovo.getShopOrderId());
		return sovo2;
	}

	public ShopOrderVO updateShopOrder(ShopOrderDTO_update dtoUpdate) {

		// 1. 先查出原本的VO
		ShopOrderVO sovo = getOneShopOrder(dtoUpdate.getShopOrderId());
		
		// 2. 只更新有傳值的欄位（可用if判斷，避免null覆蓋）
		if (dtoUpdate.getMemId() != null) {
			sovo.setMemId(dtoUpdate.getMemId());
		}
		
		if (dtoUpdate.getShopOrderShipment() != null) {
			sovo.setShopOrderShipment(dtoUpdate.getShopOrderShipment());
		}
		if (dtoUpdate.getShopOrderShipFee() != null) {
			sovo.setShopOrderShipFee(dtoUpdate.getShopOrderShipFee());
		}
		if (dtoUpdate.getBeforeDiscountAmount() != null) {
			sovo.setBeforeDiscountAmount(dtoUpdate.getBeforeDiscountAmount());
		}
		if (dtoUpdate.getDiscountCodeId() != null) {
			sovo.setDiscountCodeId(dtoUpdate.getDiscountCodeId());
		}
		if (dtoUpdate.getDiscountAmount() != null) {
			sovo.setDiscountAmount(dtoUpdate.getDiscountAmount());
		}
		if (dtoUpdate.getAfterDiscountAmount() != null) {
			sovo.setAfterDiscountAmount(dtoUpdate.getAfterDiscountAmount());
		}
		if (dtoUpdate.getShopOrderPayment() != null) {
			sovo.setShopOrderPayment(dtoUpdate.getShopOrderPayment());
		}
		if (dtoUpdate.getOrderName() != null) {
			sovo.setOrderName(dtoUpdate.getOrderName());
		}
		if (dtoUpdate.getOrderEmail() != null) {
			sovo.setOrderEmail(dtoUpdate.getOrderEmail());
		}
		if (dtoUpdate.getOrderPhone() != null) {
			sovo.setOrderPhone(dtoUpdate.getOrderPhone());
		}
		if (dtoUpdate.getOrderShippingAddress() != null) {
			sovo.setOrderShippingAddress(dtoUpdate.getOrderShippingAddress());
		}
		if (dtoUpdate.getShopOrderNote() != null) {
			sovo.setShopOrderNote(dtoUpdate.getShopOrderNote());
		}
		if (dtoUpdate.getShopOrderShipDate() != null) {
			sovo.setShopOrderShipDate(dtoUpdate.getShopOrderShipDate());
		}
		if (dtoUpdate.getShopOrderStatus() != null) {
			sovo.setShopOrderShipment(dtoUpdate.getShopOrderStatus());
		}
		if (dtoUpdate.getShopReturnApply() != null) {
			sovo.setShopOrderShipment(dtoUpdate.getShopReturnApply());
		}
		
		
		
		
		
		
		
		

		
		
		

		sor.save(sovo);
		ShopOrderVO sovo2 = getOneShopOrder(sovo.getShopOrderId());
		return sovo2;
	}

	public ShopOrderVO getOneShopOrder(Integer shopOrderId) {
		return sor.findById(shopOrderId)
				.orElseThrow(() -> new RuntimeException("查無此筆訂單資料"));
		// public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<ShopOrderVO> getAll() {
		return sor.findAll();
	}

	public List<ShopOrderVO> getAll(Integer memId) {
		return sor.findByMember(memId);
	}

//	public List<ShopOrderVO> getByMember(MemberVO memId) {
//		return sor.findByMember(memId);
//	}

	public List<ShopOrderVO> getAll(Map<String, String[]> map) {
		return HibernateUtil_CompositeQuery_ShopOrder.getAllC(map, sessionFactory.openSession());
	}

}
