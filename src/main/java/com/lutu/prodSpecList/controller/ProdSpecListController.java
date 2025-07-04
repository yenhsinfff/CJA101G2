package com.lutu.prodSpecList.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.prodSpecList.model.ProdSpecListDTO;
import com.lutu.prodSpecList.model.ProdSpecListService;

@RestController
@RequestMapping("/api/prod-specs")
public class ProdSpecListController {

    @Autowired
    private ProdSpecListService service;

    /** 取得所有規格 
     * http://localhost:8081/CJA101G02/api/prod-specs
     * */
    @GetMapping("")
    public ApiResponse<List<ProdSpecListDTO>> getAllProdSpecs() {
        List<ProdSpecListDTO> list = service.getAllProdSpecs();
        return new ApiResponse<>("success", list, "查詢成功");
    }

    /** 取得某商品所有規格 
     * http://localhost:8081/CJA101G02/api/prod-specs/{prodId}
     * */
    @GetMapping("/{prodId}")
    public ApiResponse<List<ProdSpecListDTO>> getProdSpecsByProdId(@PathVariable Integer prodId) {
        List<ProdSpecListDTO> list = service.getProdSpecsByProdId(prodId);
        return new ApiResponse<>("success", list, "查詢成功");
    }

    /** 取得單筆規格 */
    @GetMapping("/{prodId}/{specId}")
    public ApiResponse<ProdSpecListDTO> getOne(@PathVariable Integer prodId,
                                           @PathVariable Integer specId) {
        ProdSpecListDTO dto = service.getOne(prodId, specId);
        return dto != null ? new ApiResponse<>("success", dto, "查詢成功")
                           : new ApiResponse<>("fail",   null, "查無資料");
    }

    /** 新增或修改（規格唯一鍵相同時即為更新） */
    @PostMapping
    public ApiResponse<ProdSpecListDTO> saveOrUpdate(@RequestBody @Validated ProdSpecListDTO dto) {
        ProdSpecListDTO saved = service.saveOrUpdate(dto);
        return new ApiResponse<>("success", saved, "儲存成功");
    }

    /** 刪除 */
//    @DeleteMapping("/{prodId}/{specId}")
//    public ApiResponse<Void> delete(@PathVariable Integer prodId,
//                                    @PathVariable Integer specId) {
//        service.delete(prodId, specId);
//        return new ApiResponse<>("success", null, "刪除成功");
//    }
}
