package com.lutu.prodPic.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdPicService {

	@Autowired
	ProdPicRepository prodPicRepository;

	public ProdPicService(ProdPicRepository prodPicRepository) {
		this.prodPicRepository = prodPicRepository;
	}
	
	//依照商品取得圖片
	public List<ProdPicDTO> getByProdId(Integer prodId) {
		List<ProdPicVO> pics = prodPicRepository.findByProdId(prodId);
		return pics.stream().map(this::toDTO).collect(Collectors.toList());
	}

	//取得單筆商品圖片
	public byte[] getProdPicById(Integer prodPicId) {
		return prodPicRepository.findById(prodPicId).map(ProdPicVO::getProdPic).orElse(null);
	}

	
	
	// VO -> DTO
	private ProdPicDTO toDTO(ProdPicVO vo) {
		ProdPicDTO dto = new ProdPicDTO();
		dto.setProdPicId(vo.getProdPicId());
		return dto;
	}
}
