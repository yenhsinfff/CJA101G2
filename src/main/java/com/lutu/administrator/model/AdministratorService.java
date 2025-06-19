package com.lutu.administrator.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service("administratorService")
public class AdministratorService {

	@Autowired
	AdministratorRepository repository;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	
	public void addAdministrator(AdministratorVO administratorVO) {
		repository.save(administratorVO);
	}

	public void updateAdministrator(AdministratorVO administratorVO) {
		repository.save(administratorVO);
	}

	public void deleteAdministrator(Integer adminId) {
		if (repository.existsById(adminId))
			repository.deleteById(adminId);
		
	}

	public AdministratorVO getOneAdministrator(Integer adminId) {
		Optional<AdministratorVO> optional = repository.findById(adminId);
		
		return optional.orElse(null); 
	}
	
	public List<AdministratorVO> getAll(){
		return repository.findAll();
	}
	
}
