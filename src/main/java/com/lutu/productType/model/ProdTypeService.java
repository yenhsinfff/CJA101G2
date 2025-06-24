package com.lutu.productType.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProdTypeService {

	@Autowired
	private ProdTypeRepository repository;

	// 查詢所有商品類型
	public List<ProdTypeDTO> getAllTypes() {
		List<ProdTypeVO> voList = repository.findAll();
		List<ProdTypeDTO> dtoList = new ArrayList<>();
		for (ProdTypeVO vo : voList) {
			dtoList.add(convertToDTO(vo));
		}
		return dtoList;
	}

	// 查詢單筆商品類型 by ID
	public ProdTypeDTO getProdTypeById(Integer id) {
		Optional<ProdTypeVO> optional = repository.findById(id);
		if (optional.isPresent()) {
			ProdTypeVO vo = optional.get();
			return convertToDTO(vo);
		} else {
			return null;
		}
	}

	// 新增商品類型
	public ProdTypeDTO addType(ProdTypeDTO dto) {
		ProdTypeVO vo = convertToVO(dto);
		repository.save(vo);
		return convertToDTO(vo);
	}

	// 修改商品類型
	public ProdTypeDTO updateType(ProdTypeDTO dto) {
		Optional<ProdTypeVO> optional = repository.findById(dto.getProdTypeId());
		if (optional.isPresent()) {
			ProdTypeVO vo = optional.get();
			vo.setProdTypeName(dto.getProdTypeName());
			repository.save(vo);
			return convertToDTO(vo);
		} else {
			return null;
		}
	}

	// 刪除商品類型
//    public boolean deleteById(Integer id) {
//        if (repository.existsById(id)) {
//            repository.deleteById(id);
//            return true;
//        } else {
//            return false;
//        }
//    }

	// VO ➜ DTO 轉換
	private ProdTypeDTO convertToDTO(ProdTypeVO vo) {
		return new ProdTypeDTO(vo.getProdTypeId(), vo.getProdTypeName());
	}

	// DTO ➜ VO 轉換
	private ProdTypeVO convertToVO(ProdTypeDTO dto) {
		return new ProdTypeVO(dto.getProdTypeId(), dto.getProdTypeName());
	}
}
