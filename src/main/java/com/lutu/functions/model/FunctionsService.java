package com.lutu.functions.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service("functionsService")
public class FunctionsService {
	
	@Autowired
	FunctionsRepository repository;
	
	@Autowired
    private SessionFactory sessionFactory;

	public void addFunctions(FunctionsVO functionsVO) {
		repository.save(functionsVO);
	}

	public void updateMember(FunctionsVO functionsVO) {
		repository.save(functionsVO);
	}

	public void deleteFunctions(Integer funcId) {
		if (repository.existsById(funcId))
			repository.deleteById(funcId);
		
	}

	public FunctionsVO getOneFunction(Integer funcId) {
		Optional<FunctionsVO> optional = repository.findById(funcId);
		
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<FunctionsVO> getAll() {
		return repository.findAll();
	}
	
	

}
