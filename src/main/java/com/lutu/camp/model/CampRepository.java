package com.lutu.camp.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampRepository extends JpaRepository<CampVO, Integer>{
	
	List<CampVO> findByOwnerId(Integer ownerId);

}
