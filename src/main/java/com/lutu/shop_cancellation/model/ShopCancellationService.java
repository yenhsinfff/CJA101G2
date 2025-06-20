package com.lutu.shop_cancellation.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopCancellationService {
	
	@Autowired
	ShopCancellationRepository scr;
	
	// 全部查詢
	public List<ShopCancellationVO> getAll() {
		return scr.findAll();
	}

}
