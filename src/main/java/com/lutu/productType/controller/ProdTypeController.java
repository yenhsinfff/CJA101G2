package com.lutu.productType.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.productType.model.ProdTypeDTO;
import com.lutu.productType.model.ProdTypeService;

@RestController
@CrossOrigin(origins = "*")
public class ProdTypeController {

    @Autowired
    private ProdTypeService prodTypeService;

    // 查詢所有商品類型
    @GetMapping("/api/product-types")
    public ApiResponse<List<ProdTypeDTO>> getAllTypes() {
        return new ApiResponse<>("success", prodTypeService.getAllTypes(), "查詢成功");
    }

    // 查詢單筆商品類型 by ID
    @GetMapping("/api/product-types/{id}")
    public ApiResponse<ProdTypeDTO> getById(@PathVariable Integer id) {
        ProdTypeDTO dto = prodTypeService.getById(id);
        return (dto != null)
            ? new ApiResponse<>("success", dto, "查詢成功")
            : new ApiResponse<>("fail", null, "查無此類別");
    }

    // 新增商品類型
    @PostMapping("/api/product-types")
    public ApiResponse<ProdTypeDTO> addType(@RequestBody ProdTypeDTO dto) {
        return new ApiResponse<>("success", prodTypeService.addType(dto), "新增成功");
    }

    // 修改商品類型
    @PutMapping("/api/product-types")
    public ApiResponse<ProdTypeDTO> updateType(@RequestBody ProdTypeDTO dto) {
        ProdTypeDTO updated = prodTypeService.updateType(dto);
        return (updated != null)
            ? new ApiResponse<>("success", updated, "修改成功")
            : new ApiResponse<>("fail", null, "查無此類別，無法修改");
    }

    // 刪除商品類型
//    @DeleteMapping("/api/product-types/{id}")
//    public ApiResponse<String> deleteById(@PathVariable Integer id) {
//        boolean success = prodTypeService.deleteById(id);
//        return success
//            ? new ApiResponse<>("success", "刪除成功", "成功")
//            : new ApiResponse<>("fail", null, "刪除失敗：查無此類別");
//    }
    
} 