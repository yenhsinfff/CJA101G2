package com.lutu.shopProd.model;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

//	public void deleteEmp(Integer prodId) {
//		if (repository.existsById(prodId)) {
//			repository.deleteByProdId(prodId);
//		    repository.deleteById(prodId);
//		}
//	}
	
	@Transactional
	public ShopProdVO getProdById(Integer prodId) {
		Optional<ShopProdVO> optional = repository.findById(prodId);
//		Optional<ShopProdVO> optional = repository.selectProdById(prodId);
	
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	@Transactional
	public List<ShopProdVO> getAll() {
//		return repository.selectAllProducts();
		return repository.findAll();
	}

	//複合查詢
//	public List<ShopProdVO> getAll(Map<String, String[]> map) {
//		return HibernateUtil_CompositeQuery_Emp3.getAllC(map,sessionFactory.openSession());
//	}

}