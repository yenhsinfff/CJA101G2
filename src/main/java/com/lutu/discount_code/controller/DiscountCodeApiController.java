
package com.lutu.discount_code.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.discount_code.model.DiscountCodeDTO_insert;
import com.lutu.discount_code.model.DiscountCodeService;
import com.lutu.discount_code.model.DiscountCodeVO;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/discount")
public class DiscountCodeApiController {

	@Autowired
	private DiscountCodeService service;

	@GetMapping("/all")
	public List<DiscountCodeVO> getAll() {
		return service.getAll();
	}

	@GetMapping("/nextcode")
	public String getNextCode(@RequestParam String prefix) {
		return service.getNextDiscountCodeId(prefix);
	}

//    http://localhost:8081/CJA101G02/api/discount/add?prefix=A
//    @PostMapping("/add")
//    public void addDiscount(@RequestBody DiscountCodeVO vo, @RequestParam String prefix) {
//        service.addDiscountCode(prefix,vo);
//    }

	@PostMapping("/add")
	public ResponseEntity<ApiResponse<String>> addDiscount(@RequestBody DiscountCodeDTO_insert dto, @RequestParam String prefix) {
        
	    service.addDiscountCode(prefix, dto);
	    
		 ApiResponse<String> response = new ApiResponse<>();
		    response.setStatus("success");
		    response.setMessage("新增成功");
		    response.setData(null);
		    
        return ResponseEntity.ok(response);
    }

	@PostMapping("/update")
	public void updateDiscount(@RequestBody DiscountCodeVO vo) {
		service.updateDiscountCode(vo);
	}
}
