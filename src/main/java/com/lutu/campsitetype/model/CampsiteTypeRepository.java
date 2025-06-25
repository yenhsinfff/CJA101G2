package com.lutu.campsitetype.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lutu.campsitetype.model.CampsiteTypeVO.CompositeDetail;

@Repository
public interface CampsiteTypeRepository extends JpaRepository<CampsiteTypeVO, CompositeDetail>{

	
	List<CampsiteTypeVO> findByIdCampId(Integer campId);
	
	@Query("SELECT MAX(c.id.campsiteTypeId) FROM CampsiteTypeVO c")
	Integer findAllMaxCampsiteTypeId();
	
}
