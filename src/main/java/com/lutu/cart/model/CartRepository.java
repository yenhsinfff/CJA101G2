package com.lutu.cart.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lutu.cart.model.CartVO.CartKey;

public interface CartRepository extends JpaRepository<CartVO, CartKey>{
	
	// 依會員編號查詢
	@Query(value = "SELECT o FROM CartVO o WHERE o.memId = :memId")
	List<CartVO> findByMember(Integer memId);

}
