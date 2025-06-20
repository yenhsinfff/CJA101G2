package com.lutu.user_discount.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDiscountRepository extends JpaRepository<UserDiscountVO, UserDiscountId> {

    // 根據複合主鍵查詢（單一筆）
    Optional<UserDiscountVO> findByIdMemIdAndIdDiscountCodeId(Integer memId, String discountCodeId);

    // 根據會員查詢該會員所有折扣碼
    List<UserDiscountVO> findByIdMemId(Integer memId);

    // 根據折扣碼查詢所有使用者
    List<UserDiscountVO> findByIdDiscountCodeId(String discountCodeId);

    // 根據會員與折扣碼類型查詢
    List<UserDiscountVO> findByIdMemIdAndDiscountCodeType(Integer memId, byte discountCodeType);

    
    
}



