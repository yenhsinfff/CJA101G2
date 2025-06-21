package com.lutu.campsitetype.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lutu.campsitetype.model.CampsiteTypeVO.CompositeDetail;

@Repository
public interface CampsiteTypeRepository extends JpaRepository<CampsiteTypeVO, CompositeDetail>{

}
