package com.lutu.campsitetype.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lutu.campsite.model.CampsiteVO;
import com.lutu.campsitetype.model.CampsiteTypeVO.CompositeDetail;

@Repository
public interface CampsiteTypeRepository extends JpaRepository<CampsiteTypeVO, CompositeDetail>{

	
	
}
