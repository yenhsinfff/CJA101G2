package com.lutu.product_type.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdTypeRepository extends JpaRepository<ProdTypeVO, Integer> {
	
	
}
