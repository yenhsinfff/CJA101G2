package com.lutu.campsite.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampsiteRepository extends JpaRepository<CampsiteVO, Integer>{

	List<CampsiteVO> findByCampId(Integer campId);
	
	
	
}
