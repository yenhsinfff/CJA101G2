package com.lutu.specList.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecListService {

    @Autowired
    private SpecListRepository repository;

    public List<SpecListDTO> getAllSpecs() {
        List<SpecListVO> voList = repository.findAll();
        List<SpecListDTO> dtoList = new ArrayList<>();
        for (SpecListVO vo : voList) {
            dtoList.add(toDTO(vo));
        }
        return dtoList;
    }

    public SpecListDTO getSpecById(Integer id) {
        Optional<SpecListVO> optional = repository.findById(id);
        if (optional.isPresent()) {
            return toDTO(optional.get());
        }
        return null;
    }

    public SpecListDTO saveOrUpdate(SpecListDTO dto) {
        SpecListVO vo = toVO(dto);
        SpecListVO saved = repository.save(vo);
        return toDTO(saved);
    }

//    public void delete(Integer id) {
//        repository.deleteById(id);
//    }

    // ========== DTO -> VO ==========
    private SpecListDTO toDTO(SpecListVO vo) {
        SpecListDTO dto = new SpecListDTO();
        dto.setSpecId(vo.getSpecId());
        dto.setSpecName(vo.getSpecName());
        return dto;
    }
    // ========== VO -> DTO ==========
    private SpecListVO toVO(SpecListDTO dto) {
        SpecListVO vo = new SpecListVO();
        vo.setSpecId(dto.getSpecId());
        vo.setSpecName(dto.getSpecName());
        return vo;
    }
}

