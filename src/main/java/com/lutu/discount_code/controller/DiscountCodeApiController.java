
package com.lutu.discount_code.controller;

import com.lutu.discount_code.model.DiscountCodeService;
import com.lutu.discount_code.model.DiscountCodeVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    @PostMapping("/add")
    public void addDiscount(@RequestBody DiscountCodeVO vo, @RequestParam String prefix) {
        service.addDiscountCode(prefix,vo);
    }
    
    
    @PostMapping("/update")
    public void updateDiscount(@RequestBody DiscountCodeVO vo) {
        service.updateDiscountCode(vo);
    }
}
