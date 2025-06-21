package com.lutu.cart.model;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lutu.cart.model.CartVO.CartKey;

public interface CartRepository extends CrudRepository<CartVO, CartVO.CartKey>{
	
	// 依會員編號查詢
	List<CartVO> findByMember(Integer memId);

}
