package com.lutu.campsite.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("campsiteService")
public class CampsiteService {

	@Autowired
	CampsiteRepository repository;
	
	public void addCampsite(CampsiteVO campsiteVO) {
		repository.save(campsiteVO);
	}
	
	public void updateCampsite(CampsiteVO campsiteVO) {
		repository.save(campsiteVO);
	}
	
	public void deleteCampsite(Integer campsiteId) {
		if (repository.existsById(campsiteId))
			repository.deleteById(campsiteId);
	}
	
	public CampsiteVO getOneCampsite(Integer campsiteId) {
		Optional<CampsiteVO> optional = repository.findById(campsiteId);
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}


	public List<CampsiteVO> getAll() {
		return repository.findAll();

	}
}

