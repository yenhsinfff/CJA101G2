package com.lutu.campsite_order_details.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("campsiteOrderDetailsService")
public class CampsiteOrderDetailsService {
	
	@Autowired
	CampsiteOrderDetailsRepository campsiteOrderDetailsRepository;
	
	@Transactional
	public List<CampSiteOrderDetailsVO> CampsiteOrderDetail() {

		return campsiteOrderDetailsRepository.findAll();

	}

}
