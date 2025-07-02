package com.lutu.prodPic.model;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lutu.shopProd.model.ShopProdVO;

@Service
public class ProdPicService {

	@Autowired
	ProdPicRepository prodPicRepository;

	public ProdPicService(ProdPicRepository prodPicRepository) {
		this.prodPicRepository = prodPicRepository;
	}
	
	//依照商品ID取得圖片
	public List<ProdPicDTO> getByProdId(Integer prodId) {
		List<ProdPicVO> pics = prodPicRepository.findByProdId(prodId);
		return pics.stream().map(this::toDTO).collect(Collectors.toList());
	}

	//取得單筆商品圖片
	public byte[] getProdPicById(Integer prodPicId) {
		return prodPicRepository.findById(prodPicId).map(ProdPicVO::getProdPic).orElse(null);
	}

	//新增商品圖片
    public Boolean saveProductImage(Integer prodId, MultipartFile file) {
        try {
            ProdPicVO pic = new ProdPicVO();
            
            // 設定關聯商品
            ShopProdVO prod = new ShopProdVO();
            prod.setProdId(prodId);
            pic.setShopProdVO(prod);
            
            // 設定圖片內容
            pic.setProdPic(file.getBytes());

            prodPicRepository.save(pic);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	// VO -> DTO
	private ProdPicDTO toDTO(ProdPicVO vo) {
		ProdPicDTO dto = new ProdPicDTO();
		dto.setProdPicId(vo.getProdPicId());
	    dto.setProdId(vo.getProdId());
		return dto;
	}
}
