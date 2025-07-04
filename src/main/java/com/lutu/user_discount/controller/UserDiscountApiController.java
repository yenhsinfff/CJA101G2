package com.lutu.user_discount.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.discount_code.model.DiscountCodeService;
import com.lutu.user_discount.model.UserDiscountDTO;
import com.lutu.user_discount.model.UserDiscountDTO_request;
import com.lutu.user_discount.model.UserDiscountService;
import com.lutu.user_discount.model.UserDiscountVO;

@RestController
@CrossOrigin(origins = "*")
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
  
 // http://localhost:8081/CJA101G02/api/userdiscount/search/10000001
 // http://localhost:8081/CJA101G02/api/userdiscount/search/{memId}
 // 2. 查詢
    @GetMapping("/search/{memId}")
    public ResponseEntity<List<UserDiscountDTO>> getDiscountsByMemberId(@PathVariable Integer memId) {
        List<UserDiscountDTO> dto = userDiscountService.getDiscountsByMemberId(memId);
        return ResponseEntity.ok(dto);
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
    
//  http://localhost:8081/CJA101G02/api/userdiscount/sendDiscount
//  發送折價券給單一會員
    @PostMapping("/sendDiscount")
    public ResponseEntity<ApiResponse<UserDiscountDTO>> sendDiscount(@RequestBody UserDiscountDTO_request dto) {
        UserDiscountDTO result = userDiscountService.sendDiscountToUser(dto);

        ApiResponse<UserDiscountDTO> response = new ApiResponse<>();
        response.setStatus("success");
        response.setMessage("折價券已成功發送給會員");
        response.setData(result); // 回傳完整 DTO 給前端

        return ResponseEntity.ok(response);
    }

//  http://localhost:8081/CJA101G02/api/userdiscount/sendDiscountToAll?discountCodeId=A00001
//  http://localhost:8081/CJA101G02/api/userdiscount/sendDiscountToAll?discountCodeId=A00001
 // 發送折價券給所有會員
    @PostMapping("/sendDiscountToAll")
    public ResponseEntity<ApiResponse<String>> sendDiscountToAll(@RequestParam String discountCodeId) {
        userDiscountService.sendDiscountToAllMembers(discountCodeId);

        ApiResponse<String> response = new ApiResponse<>();
        response.setStatus("success");
        response.setMessage("折價券已成功發送給所有會員");
        response.setData(null);

        return ResponseEntity.ok(response);
    }
    

}
