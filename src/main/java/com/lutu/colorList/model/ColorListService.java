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

    // 新增或修改
    public ColorListDTO save(ColorListDTO dto) {
        ColorListVO vo = toVO(dto);
        ColorListVO savedVO = repository.save(vo);
        return toDTO(savedVO);
    }

    // 查全部
    public List<ColorListDTO> findAll() {
        List<ColorListVO> voList = repository.findAll();
        List<ColorListDTO> dtoList = new ArrayList<>();

        for (ColorListVO vo : voList) {
            dtoList.add(toDTO(vo));
        }

        return dtoList;
    }

    // 用 ID 查單筆
    public ColorListDTO findById(Integer id) {
        Optional<ColorListVO> optional = repository.findById(id);
        if (optional.isPresent()) {
            ColorListVO vo = optional.get();
            return toDTO(vo);
        }
        return null;
    }

    // 刪除
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

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
