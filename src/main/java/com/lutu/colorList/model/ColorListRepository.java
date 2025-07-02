package com.lutu.colorList.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ColorListRepository extends JpaRepository<ColorListVO, Integer> {
	
	
	Optional<ColorListVO> findByColorName(String colorName);

}