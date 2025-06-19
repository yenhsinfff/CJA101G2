package com.lutu.owner.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service("ownerService")
public class OwnerService {

	@Autowired
	OwnerRepository repository;
	
	@Autowired
    private SessionFactory sessionFactory;

	public void addOwner(OwnerVO ownerVO) {
		repository.save(ownerVO);
	}

	public void updateOwner(OwnerVO ownerVO) {
		repository.save(ownerVO);
	}

	public void deleteOwner(Integer ownerId) {
		if (repository.existsById(ownerId))
			repository.deleteById(ownerId);
		
	}

	public OwnerVO getOneMem(Integer ownerId) {
		Optional<OwnerVO> optional = repository.findById(ownerId);
		
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<OwnerVO> getAll() {
		return repository.findAll();
	}
	
	
}
