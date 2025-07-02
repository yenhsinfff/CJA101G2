package com.lutu.colorList.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColorListService {

    @Autowired
    private ColorListRepository repository;

    /** 查全部顏色 */
    public List<ColorListDTO> getAllColors() {
        List<ColorListVO> voList = repository.findAll();
        List<ColorListDTO> dtoList = new ArrayList<>();
        for (ColorListVO vo : voList) {
            dtoList.add(toDTO(vo));
        }
        return dtoList;
    }

    /** 查單筆顏色 by ID */
    public ColorListDTO getColorById(Integer id) {
        Optional<ColorListVO> optional = repository.findById(id);
        return optional.map(this::toDTO).orElse(null);
    }

    /** 新增或修改顏色 */
    public ColorListDTO saveOrUpdate(ColorListDTO dto) {
    	// 新增時檢查是否已有相同名稱
        if (dto.getColorId() == null) {
            Optional<ColorListVO> exists = repository.findByColorName(dto.getColorName());
            if (exists.isPresent()) {
                return toDTO(exists.get()); // 已存在就直接回傳，不重複新增
            }
        }
        ColorListVO vo = toVO(dto);
        ColorListVO saved = repository.save(vo);
        return toDTO(saved);
    }

    /** 刪除顏色 */
//    public void deleteColor(Integer id) {
//        repository.deleteById(id);
//    }

    // ========== DTO -> VO ==========
    private ColorListVO toVO(ColorListDTO dto) {
        ColorListVO vo = new ColorListVO();
        vo.setColorId(dto.getColorId());
        vo.setColorName(dto.getColorName());
        return vo;
    }

    // ========== VO -> DTO ==========
    private ColorListDTO toDTO(ColorListVO vo) {
        ColorListDTO dto = new ColorListDTO();
        dto.setColorId(vo.getColorId());
        dto.setColorName(vo.getColorName());
        return dto;
    }
}
