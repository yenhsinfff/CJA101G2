package com.lutu.discount_code.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service("DiscountCodeService")
public class DiscountCodeService {
	
	@Autowired
	DiscountCodeRepository repository;
	
	@Autowired
    private SessionFactory sessionFactory;

	public void addDiscountCode(DiscountCodeVO discountCodeVO) {
		repository.save(discountCodeVO);
	}

	public void updateDiscountCode(DiscountCodeVO discountCodeVO) {
		repository.save(discountCodeVO);
	}

	public void deleteDiscountCode(Integer discountCodeId) {
		if (repository.existsById(discountCodeId))
			repository.deleteById(discountCodeId);
		
	}

	public DiscountCodeVO getOneDiscountCode(Integer discountCodeId) {
		Optional<DiscountCodeVO> optional = repository.findById(discountCodeId);
		
		return optional.orElse(null);
	}

	public List<DiscountCodeVO> getAll() {
		return repository.findAll();
	}

}
