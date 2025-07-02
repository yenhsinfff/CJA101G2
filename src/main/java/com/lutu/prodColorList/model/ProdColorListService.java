package com.lutu.prodColorList.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lutu.colorList.model.ColorListDTO;
import com.lutu.colorList.model.ColorListRepository;
import com.lutu.colorList.model.ColorListVO;
import com.lutu.prodColorList.model.ProdColorListVO.CompositeDetail;
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
	
	// 查所有商品顏色 
    public List<ProdColorListDTO> getAllProdColors() {
        List<ProdColorListVO> voList = repository.findAll();
        List<ProdColorListDTO> dtoList = new ArrayList<>();

        for (ProdColorListVO vo : voList) {
            dtoList.add(toDTO(vo));
        }
        return dtoList;
    }

	// 查詢某商品所有商品顏色
	public List<ProdColorListDTO> getProdColorsByProdId(Integer prodId) {
		List<ProdColorListVO> voList = repository.findByProdId(prodId);
		List<ProdColorListDTO> dtoList = new ArrayList<>();
		for (ProdColorListVO vo : voList) {
			dtoList.add(toDTO(vo));
		}
		return dtoList;
	}

	// 查單一筆商品顏色
	public ProdColorListDTO getOne(Integer prodId, Integer prodColorId) {
		Optional<ProdColorListVO> optional = repository.findById(new ProdColorListVO.CompositeDetail(prodId, prodColorId));
		if (optional.isPresent()) {
			return toDTO(optional.get());
		} else {
			return null;
		}
	}

	// 新增或更新
	public ProdColorListDTO saveOrUpdate(ProdColorListDTO dto) {
		ProdColorListVO vo = toVO(dto);
		ProdColorListVO saved = repository.save(vo);
		return toDTO(saved);
	}

	// 查詢顏色圖片
	public byte[] getPicByCompositeKey(Integer prodId, Integer colorId) {
	    Optional<ProdColorListVO> opt = repository.findById(new CompositeDetail(prodId, colorId));
	    return opt.map(ProdColorListVO::getProdColorPic).orElse(null);
	}
	
	// 取得顏色名稱
	public List<ColorListDTO> getAllColorNames() {
	    List<ColorListVO> list = colorListRepository.findAll();
	    List<ColorListDTO> result = new ArrayList<>();

	    for (ColorListVO vo : list) {
	        ColorListDTO dto = new ColorListDTO();
	        dto.setColorId(vo.getColorId());
	        dto.setColorName(vo.getColorName());
	        result.add(dto);
	    }
	    return result;
	}
	
	// 上傳商品顏色圖片
	public boolean updateColorPic(Integer prodId, Integer colorId, MultipartFile file) throws IOException {
	    Optional<ProdColorListVO> opt = repository.findById(new CompositeDetail(prodId, colorId));
	    if (opt.isPresent()) {
	        ProdColorListVO vo = opt.get();
	        vo.setProdColorPic(file.getBytes());
	        repository.save(vo);
	        return true;
	    } else {
	        return false;
	    }
	}

	// 刪除
//    public void delete(Integer prodId, Integer prodColorId) {
//        repository.deleteById(new ProdColorListVO.CompositeDetail(prodId, prodColorId));
//    }

	// VO ➜ DTO
	private ProdColorListDTO toDTO(ProdColorListVO vo) {
		ProdColorListDTO dto = new ProdColorListDTO();
		dto.setProdId(vo.getProdId());
		dto.setProdColorId(vo.getProdColorId());
//		dto.setProdColorPic(vo.getProdColorPic());

		if (vo.getColorListVO() != null) {
			dto.setColorName(vo.getColorListVO().getColorName()); // 若 colorName 要顯示
		}
		
		//檢查是否有顏色圖片
		dto.setHasPic(vo.getProdColorPic() != null && vo.getProdColorPic().length > 0);


		return dto;
	}

	// DTO ➜ VO
	private ProdColorListVO toVO(ProdColorListDTO dto) {
		ProdColorListVO vo = new ProdColorListVO();
		vo.setProdId(dto.getProdId());
		vo.setProdColorId(dto.getProdColorId());
//		vo.setProdColorPic(dto.getProdColorPic());

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

}
