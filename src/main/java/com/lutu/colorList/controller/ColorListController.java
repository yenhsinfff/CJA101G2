package com.lutu.colorList.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.colorList.model.ColorListDTO;
import com.lutu.colorList.model.ColorListService;

@RestController
@RequestMapping("/api/colors") // 所有 API 路徑開頭為 /api/colors
public class ColorListController {

    @Autowired
    private ColorListService service;

    /**
     * 查詢所有顏色資料
     * GET /api/colors
     */
    @GetMapping
    public List<ColorListDTO> getAllColors() {
        return service.getAllColors();
    }

    /**
     * 根據 ID 查詢單筆顏色
     * GET /api/colors/{id}
     */
    @GetMapping("/{id}")
    public ColorListDTO getColorById(@PathVariable Integer id) {
        return service.getColorById(id);
    }

    /**
     * 新增顏色
     * POST /api/colors
     * @RequestBody：從前端接收 JSON 格式資料，並轉成 DTO 對象
     */
    @PostMapping
    public ColorListDTO addColor(@RequestBody ColorListDTO dto) {
        return service.saveOrUpdate(dto);
    }

    /**
     * 修改顏色
     * PUT /api/colors/{id}
     */
    @PutMapping("/{id}")
    public ColorListDTO updateColor(@PathVariable Integer id, @RequestBody ColorListDTO dto) {
        dto.setColorId(id); // 確保 ID 一致，避免修改錯誤資料
        return service.saveOrUpdate(dto);
    }

    /**
     * 刪除顏色
     * DELETE /api/colors/{id}
     */
//    @DeleteMapping("/{id}")
//    public void deleteColor(@PathVariable Integer id) {
//        service.deleteById(id);
//    }
    
}
