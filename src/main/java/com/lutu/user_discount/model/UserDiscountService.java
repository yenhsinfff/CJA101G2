package com.lutu.user_discount.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
//	public UserDiscountVO getOneUserDiscount(Integer memId) {
//		Optional<UserDiscountVO> optional = repository.findById(id);
//		return optional.orElse(null);	
//	}
	
	public List<UserDiscountDTO> getDiscountsByMemberId(Integer memId) {
	    List<UserDiscountVO> voList = repository.findByIdMemId(memId);

	    // 將 VO 轉為 DTO
	    return voList.stream()
	            .map(vo -> new UserDiscountDTO(
	                    vo.getId().getMemId(),
	                    vo.getId().getDiscountCodeId(),
	                    vo.getDiscountCodeType(),
	                    vo.getUsedAt()
	            ))
	            .collect(Collectors.toList());
	}
        
	//查詢所有
	public List<UserDiscountVO> getAll() {
		return repository.findAll();
	}
	
	// 🔍 可擴充自訂查詢，例如查某會員所有折扣記錄：
	public List<UserDiscountVO> getByMemId(Integer memId) {
		return repository.findByIdMemId(memId);
	}
	
	public Optional<UserDiscountVO> getById(UserDiscountVO.CompositeDetail compositeDetail) {
		return repository.findById(compositeDetail);
	}

}
