package com.lutu.shop_order_items_details.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lutu.shop_order.model.ShopOrderRepository;

@Service
public class ShopOrderItemsDetailsService {
	
	@Autowired
	ShopOrderItemsDetailsRepository soidr;
	
	@Autowired
	ShopOrderRepository sor;
	
	// 新增
	
	
	// 全部查詢
	public List<ShopOrderItemsDetailsVO> getAll() {
		return soidr.findAll();
	}
	
	
	// 依訂單編號查詢
	public List<ShopOrderItemsDetailsVO> getAll(Integer shopOrderId) {
		return soidr.findByShopOrderId(shopOrderId);
	}

}
