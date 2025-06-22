package com.lutu.prodColorList.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lutu.colorList.model.ColorListRepository;
import com.lutu.colorList.model.ColorListVO;
import com.lutu.shopProd.model.ShopProdRepository;
import com.lutu.shopProd.model.ShopProdVO;

@Service
@Transactional
public class ProdColorListService {

	@Autowired
	private ProdColorListRepository repository;

	@Autowired
	private ColorListRepository colorListRepository; // 用來補上 colorName 等關聯欄位（若有需要）

	@Autowired
	private ShopProdRepository shopProdRepository; // 若未來要補商品資料進來，可用

	// 🔁 新增或更新（DTO）
	public ProdColorListDTO saveOrUpdate(ProdColorListDTO dto) {
		ProdColorListVO vo = toVO(dto);
		ProdColorListVO saved = repository.save(vo);
		return toDTO(saved);
	}

	// ✅ 查詢某商品所有顏色（回傳 DTO）
	public List<ProdColorListDTO> findByProdIdDTO(Integer prodId) {
		return repository.findByProdId(prodId)
				.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	// ✅ 查單一筆（DTO）
	public ProdColorListDTO findOneDTO(Integer prodId, Integer prodColorId) {
		Optional<ProdColorListVO> optional = repository.findById(
				new ProdColorListVO.CompositeDetail(prodId, prodColorId));
		return optional.map(this::toDTO).orElse(null);
	}

	// ✅ 查單一筆（VO）
	public ProdColorListVO findOne(Integer prodId, Integer prodColorId) {
		return repository.findById(new ProdColorListVO.CompositeDetail(prodId, prodColorId)).orElse(null);
	}

	// ✅ 查所有顏色（VO）
	public List<ProdColorListVO> findByProdId(Integer prodId) {
		return repository.findByProdId(prodId);
	}

	// ✅ 刪除
	public void delete(Integer prodId, Integer prodColorId) {
		repository.deleteById(new ProdColorListVO.CompositeDetail(prodId, prodColorId));
	}

	// 🔁 DTO ➜ VO
	private ProdColorListVO toVO(ProdColorListDTO dto) {
		ProdColorListVO vo = new ProdColorListVO();
		vo.setProdId(dto.getProdId());
		vo.setProdColorId(dto.getProdColorId());
		vo.setProdColorPic(dto.getProdColorPic());

		// 補上關聯（如果需要）
		if (dto.getProdColorId() != null) {
			ColorListVO color = colorListRepository.findById(dto.getProdColorId()).orElse(null);
			vo.setColorListVO(color);
		}
		if (dto.getProdId() != null) {
			ShopProdVO prod = shopProdRepository.findById(dto.getProdId()).orElse(null);
			vo.setShopProdVO(prod);
		}

		return vo;
	}

	// 🔁 VO ➜ DTO
	private ProdColorListDTO toDTO(ProdColorListVO vo) {
		ProdColorListDTO dto = new ProdColorListDTO();
		dto.setProdId(vo.getProdId());
		dto.setProdColorId(vo.getProdColorId());
		dto.setProdColorPic(vo.getProdColorPic());

		if (vo.getColorListVO() != null) {
			dto.setColorName(vo.getColorListVO().getColorName()); // 若 colorName 要顯示
		}

		return dto;
	}
}
