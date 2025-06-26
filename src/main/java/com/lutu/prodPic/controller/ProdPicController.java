package com.lutu.prodPic.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.prodPic.model.ProdPicDTO;
import com.lutu.prodPic.model.ProdPicService;

@RestController
@RequestMapping("/api/prod-pic")
@CrossOrigin(origins = "*") // 允許所有網域跨域存取
public class ProdPicController {

    private final ProdPicService prodPicService;

    public ProdPicController(ProdPicService prodPicService) {
        this.prodPicService = prodPicService;
    }

    // 透過商品 ID 查圖片
    @GetMapping("/by-prod/{prodId}")
    public List<ProdPicDTO> getPicsByProdId(@PathVariable Integer prodId) {
        return prodPicService.getByProdId(prodId);
    }
}
