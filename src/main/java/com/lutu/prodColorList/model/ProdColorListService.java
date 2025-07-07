package com.lutu.prodColorList.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lutu.colorList.model.ColorListDTO;
import com.lutu.colorList.model.ColorListRepository;
import com.lutu.colorList.model.ColorListService;
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
	
	@Autowired
	private ColorListService colorListService;
	
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

	// 查詢指定商品的「上架」顏色
	public List<ProdColorListDTO> getActiveProdColorsByProdId(Integer prodId) {
	    List<ProdColorListVO> voList = repository.findByProdIdAndProdColorStatus(prodId, (byte) 1);
	    List<ProdColorListDTO> dtoList = new ArrayList<>();

	    for (ProdColorListVO vo : voList) {
	        dtoList.add(toDTO(vo));
	    }

	    return dtoList;
	}

	// 更新商品顏色（新增 / 上架 / 下架）
	public void updateProdColors(Integer prodId, List<ProdColorListDTO> newColorDTOs) {
	    // 查詢目前所有顏色（含上下架）
	    List<ProdColorListVO> currentColors = repository.findByProdId(prodId);

	    // 建立目前已有的 colorId 清單（Map 更方便取 VO）
	    Map<Integer, ProdColorListVO> currentColorMap = new HashMap<>();
	    for (ProdColorListVO vo : currentColors) {
	        currentColorMap.put(vo.getCompositeKey().getProdColorId(), vo);
	    }

	    // 建立新的 colorId 清單
	    Set<Integer> newColorIds = new HashSet<>();
	    List<ProdColorListDTO> validColorDTOs = new ArrayList<>();

	    // 判斷是否選擇單一顏色（假設 ID = 1）
	    boolean hasSingleColor = newColorDTOs.stream()
	        .anyMatch(dto -> dto.getProdColorId() != null && dto.getProdColorId() == 1);

	    for (ProdColorListDTO dto : newColorDTOs) {
	        Integer colorId = dto.getProdColorId();
	        if (hasSingleColor && colorId != 1) continue;

	        newColorIds.add(colorId);
	        validColorDTOs.add(dto);
	    }

	    // 新增或更新（只改狀態與欄位）
	    for (ProdColorListDTO dto : validColorDTOs) {
	        Integer colorId = dto.getProdColorId();
	        CompositeDetail key = new ProdColorListVO.CompositeDetail(prodId, colorId);

	        Optional<ProdColorListVO> optional = repository.findById(key);

	        if (optional.isPresent()) {
	            // ✅ 資料存在，直接修改狀態為上架
	            ProdColorListVO existing = optional.get();
	            if (existing.getProdColorStatus() != 1) {
	                existing.setProdColorStatus((byte) 1); // 上架
	                repository.save(existing);
	            }
	        } else {
	            // ✅ 資料真的不存在，才能新增（避免主鍵衝突）
	            ProdColorListVO newVO = new ProdColorListVO();
	            newVO.setCompositeKey(key);
	            newVO.setProdColorStatus((byte) 1); // 上架

	            // 可選補上關聯（非必要）
	            ShopProdVO prod = shopProdRepository.findById(prodId).orElse(null);
	            ColorListVO color = colorListRepository.findById(colorId).orElse(null);
	            newVO.setShopProdVO(prod);
	            newVO.setColorListVO(color);

	            repository.save(newVO);
	        }
	    }

	    // 原本有但這次沒傳 → 下架
	    for (Integer oldColorId : currentColorMap.keySet()) {
	        if (!newColorIds.contains(oldColorId)) {
	            ProdColorListVO vo = currentColorMap.get(oldColorId);
	            if (vo.getProdColorStatus() != 0) {
	                vo.setProdColorStatus((byte) 0); // 下架
	                repository.save(vo);
	            }
	        }
	    }
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

	// 刪除prodcolorlist
    public void delete(Integer prodId, Integer prodColorId) {
        repository.deleteById(new ProdColorListVO.CompositeDetail(prodId, prodColorId));
    }
    
	// 刪除 by ProdId
	public void deleteByProdId(Integer prodId) {
	    List<ProdColorListVO> list = repository.findByProdId(prodId);
	    for (ProdColorListVO vo : list) {
	        repository.deleteById(new CompositeDetail(vo.getProdId(), vo.getProdColorId()));
	    }
	}

	
	// VO ➜ DTO
	private ProdColorListDTO toDTO(ProdColorListVO vo) {
		ProdColorListDTO dto = new ProdColorListDTO();
		dto.setProdId(vo.getProdId());
		dto.setProdColorId(vo.getProdColorId());
//		dto.setProdColorPic(vo.getProdColorPic());
		dto.setProdColorStatus(vo.getProdColorStatus());
		
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
		vo.setProdColorStatus(dto.getProdColorStatus());

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
