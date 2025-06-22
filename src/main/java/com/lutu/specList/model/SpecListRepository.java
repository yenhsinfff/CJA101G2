package com.lutu.specList.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecListRepository extends JpaRepository<SpecListVO, Integer> {
    // 可加上 findBySpecName 等自訂方法
	
}