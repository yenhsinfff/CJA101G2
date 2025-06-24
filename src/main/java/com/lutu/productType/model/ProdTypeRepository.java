package com.lutu.productType.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdTypeRepository extends JpaRepository<ProdTypeVO, Integer> {
	
	
}
