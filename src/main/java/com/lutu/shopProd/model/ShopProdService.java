package com.lutu.shopProd.model;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("ShopProdService")
public class ShopProdService {

	@Autowired
	ShopProdRepository repository;
	
	@Autowired
    private SessionFactory sessionFactory;

	public void addProd(ShopProdVO shopProdVO) {
		repository.save(shopProdVO);
	}

	public void updateProd(ShopProdVO shopProdVO) {
		repository.save(shopProdVO);
	}

	public void deleteEmp(Integer prodId) {
		if (repository.existsById(prodId)) {
			repository.deleteByProdId(prodId);
//		    repository.deleteById(prodId);
		}

	}

	public ShopProdVO getOneEmp(Integer empno) {
		Optional<ShopProdVO> optional = repository.findById(empno);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<ShopProdVO> getAll() {
		return repository.findAll();
	}

//	public List<ShopProdVO> getAll(Map<String, String[]> map) {
//		return HibernateUtil_CompositeQuery_Emp3.getAllC(map,sessionFactory.openSession());
//	}

}