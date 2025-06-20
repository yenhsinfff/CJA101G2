package com.lutu.return_order.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReturnOrderService {
	
	@Autowired
	ReturnOrderRepository ror;
	
	// 全部查詢
	public List<ReturnOrderVO> getAll() {
		return ror.findAll();
	}

}
