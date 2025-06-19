package com.lutu.campsite_order.model;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lutu.camp.model.CampVO;

import jakarta.transaction.Transactional;

@Service("campsiteOrderService")
public class CampSiteOrderService {

	@Autowired
	CampSiteOrderRepository campSiteOrderRepository;

	@Autowired
	private SessionFactory sessionFactory;
	

	public List<CampSiteOrderVO> getAllCampsiteOrder() {

		return campSiteOrderRepository.findAll();

	}
	
//	@Transactional
	public CampSiteOrderVO getOneCampsiteOrder(String campSiteOrderId) {

		CampSiteOrderVO campSiteOrderVO = campSiteOrderRepository.findByCampSiteOrderId(campSiteOrderId);
//		if (campSiteOrderVO != null) {
//			campSiteOrderVO.getCampSiteOrderDetails().size(); // 強制初始化
//		}
		return campSiteOrderVO;

	}
	
	public CampSiteOrderVO createOneCampOrder(CampSiteOrderVO campSiteOrderVO) {
		campSiteOrderRepository.save(campSiteOrderVO);
    	CampSiteOrderVO thiscampSiteOrderVO = getOneCampsiteOrder(campSiteOrderVO.getCampsiteOrderId());
    	return thiscampSiteOrderVO;
	}

}
