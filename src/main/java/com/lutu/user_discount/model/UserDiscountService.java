package com.lutu.user_discount.model;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service("userDiscountService")
public class UserDiscountService {
	
	@Autowired
	UserDiscountRepository repository;
	
	@Autowired
    private SessionFactory sessionFactory;

	//新增
	public void addUserDiscount(UserDiscountVO userDiscountVO) {
		repository.save(userDiscountVO);
	}

	//更新
	public void updateUserDiscount(UserDiscountVO userDiscountVO) {
		repository.save(userDiscountVO);
	}

	//刪除（必須傳入複合主鍵）
	public UserDiscountVO getOneUserDiscount(UserDiscountId id) {
		Optional<UserDiscountVO> optional = repository.findById(id);
		return optional.orElse(null);	
	}

	//查詢所有
	public List<UserDiscountVO> getAll() {
		return repository.findAll();
	}
	
	// 🔍 可擴充自訂查詢，例如查某會員所有折扣記錄：
	public List<UserDiscountVO> getByMemId(Integer memId) {
		return repository.findByIdMemId(memId);
	}

}
