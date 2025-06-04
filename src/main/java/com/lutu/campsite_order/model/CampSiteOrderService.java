package com.lutu.campsite_order.model;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("campsiteOrderService")
public class CampSiteOrderService {
	
	@Autowired
	CampSiteOrderRepository campSiteOrderRepository;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public List<CampSiteOrderVO> getAllCampsiteOrder() {
		
		return campSiteOrderRepository.findAll();
		
	}

}
