package com.lutu.user_discount.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.user_discount.model.UserDiscountService;
import com.lutu.user_discount.model.UserDiscountVO;

@RestController
@RequestMapping("/api/userdiscount")
public class UserDiscountApiController {
	
	@Autowired
    private UserDiscountService userDiscountService;
	
	
	// 1. 新增
    @PostMapping("/add")
    public ResponseEntity<UserDiscountVO> addUserDiscount(@RequestBody UserDiscountVO userDiscountVO) {
        userDiscountService.addUserDiscount(userDiscountVO);
        return ResponseEntity.ok(userDiscountVO);
    }
    
 // 2. 查詢
    @GetMapping("/search/{memId}")
    public ResponseEntity<List<UserDiscountVO>> getDiscountsByMemberId(@PathVariable Integer memId) {
        List<UserDiscountVO> list = userDiscountService.getDiscountsByMemberId(memId);
        return ResponseEntity.ok(list);
    }
    
    @GetMapping("/used")
    public ResponseEntity<UserDiscountVO> updateUsedAtNow(@RequestParam Integer memId, @RequestParam String discountCodeId) {
        UserDiscountVO.CompositeDetail id = new UserDiscountVO.CompositeDetail();
        id.setMemId(memId);
        id.setDiscountCodeId(discountCodeId);
        Optional<UserDiscountVO> opt = userDiscountService.getById(id);
        if (opt.isPresent()) {
            UserDiscountVO vo = opt.get();
            vo.setUsedAt(java.time.LocalDateTime.now());
            userDiscountService.updateUserDiscount(vo);
            return ResponseEntity.ok(vo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    

}
