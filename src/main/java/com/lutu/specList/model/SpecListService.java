package com.lutu.specList.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecListService {

    @Autowired
    private SpecListRepository repository;

    public List<SpecListDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public SpecListDTO findById(Integer id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public SpecListDTO saveOrUpdate(SpecListDTO dto) {
        SpecListVO vo = toVO(dto);
        SpecListVO saved = repository.save(vo);
        return toDTO(saved);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    private SpecListDTO toDTO(SpecListVO vo) {
        return new SpecListDTO(vo.getSpecId(), vo.getSpecName());
    }

    private SpecListVO toVO(SpecListDTO dto) {
        return new SpecListVO(dto.getSpecId(), dto.getSpecName());
    }
}
