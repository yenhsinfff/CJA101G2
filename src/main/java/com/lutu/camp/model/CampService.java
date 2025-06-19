package com.lutu.camp.model;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("campService")
public class CampService {

	@Autowired
	CampRepository campRepository;
	
	@Autowired
//	private SessionFactory sessionFactory;
	
    @Transactional
	public List<CampVO> getAllCamp() {
		
		return campRepository.findAll();
		
	}
    
//    @Transactional
    public CampVO getOneCamp(Integer campId) {
    	CampVO camp = campRepository.findById(campId).orElse(null);
//    	byte[] img = (camp != null) ? camp.getCampPic1() : null;
//    	if (camp != null) {
//            camp.getCampsiteOrders().size(); // 強制初始化
//        }
    	return camp;
    	
    }
    
    public CampVO createOneCamp(CampVO campVO) {
    	campRepository.save(campVO);
    	CampVO campVO2 = getOneCamp(campVO.getCampId());
    	return campVO2;
	}
}
