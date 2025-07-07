package com.lutu.camp.model;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service("campService")
public class CampService {

	@Autowired
	CampRepository campRepository;
	
//	@Autowired
//	private SessionFactory sessionFactory;
	
    @Transactional
	public List<CampVO> getAllCamp() {
		
		return campRepository.findAll();
		
	}
	
	@Transactional
	public List<CampDTO> getAllCampDTO() {
		
		List<CampVO> camps = campRepository.findAll();
	    return camps.stream()
	        .map(CampDTO::fromEntity)
	        .collect(Collectors.toList());
		
	}
	
	public CampDTO getCampDTOById(Integer campId) {
	    CampVO campVO = campRepository.findById(campId).orElseThrow();
	    return CampDTO.fromEntity(campVO);
	}


	
	
    
    @Transactional
    public CampInsertDTO getOneCampDTO(Integer campId) {
    	CampVO camp = campRepository.findById(campId).orElse(null);
    	CampInsertDTO dto = new CampInsertDTO(camp);
    	return dto;
    }
    
    @Transactional
    public CampVO getOneCamp(Integer campId) {
    	CampVO camp = campRepository.findById(campId).orElse(null);
    	return camp;
    }
    
    @Transactional
    public List<CampDTO> getCampDTOsByOwnerId(Integer ownerId) {
        List<CampVO> camps = campRepository.findByOwnerId(ownerId);
        return camps.stream()
            .map(CampDTO::fromEntity)
            .collect(Collectors.toList());
    }

    
    public CampInsertDTO createOneCamp(CampVO campVO) {
    	campRepository.save(campVO);
    	CampInsertDTO campVO2 = getOneCampDTO(campVO.getCampId());
    	return campVO2;
	}
    
	public void addCamp(CampVO campVO) {
		campRepository.save(campVO);
	}
	
	public void updateCamp(CampVO campVO) {
		campRepository.save(campVO);
	}
	
	public void deleteCamp(Integer campId) {
		if (campRepository.existsById(campId))
			campRepository.deleteById(campId);
	}
	
	
}
