package com.lutu.prodPic.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		List<ProdPicVO> pics = prodPicRepository.findByProdIdOrderByProdPicIdAsc(prodId);
		List<ProdPicDTO> result = new ArrayList<>();

	    for (ProdPicVO pic : pics) {
	        if (pic.getProdPic() != null) {
	            result.add(toDTO(pic));
	        }
	    }
	    return result;
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
    
    // 新增覆蓋圖片方法
    public boolean updateProductImage(Integer prodPicId, MultipartFile file) {
        try {
            ProdPicVO pic = prodPicRepository.findById(prodPicId).orElse(null);
            if (pic != null) {
                pic.setProdPic(file.getBytes());
                prodPicRepository.save(pic);
                return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 新增商品時預先建立 4 張空白圖片欄位
    public void initProductImages(Integer prodId) {
        List<ProdPicVO> existing = prodPicRepository.findByProdIdOrderByProdPicIdAsc(prodId);
        if (existing != null && !existing.isEmpty()) return; // 已存在就跳出

        for (int i = 0; i < 4; i++) {
            ProdPicVO pic = new ProdPicVO();
            ShopProdVO prod = new ShopProdVO();
            prod.setProdId(prodId);
            pic.setShopProdVO(prod);
            pic.setProdPic(null); // 尚未上傳
            prodPicRepository.save(pic);
        }
    }

    // 依據 prodId + index 更新圖片
    public boolean updateProductImageByIndex(Integer prodId, Integer index, MultipartFile file) {
        try {
            // 找出這個商品的第 N 張圖片（用順序固定，例如第 0~3 筆）
        	List<ProdPicVO> pics = prodPicRepository.findByProdIdOrderByProdPicIdAsc(prodId);
            if (pics.size() <= index) return false;

            ProdPicVO picToUpdate = pics.get(index);
            picToUpdate.setProdPic(file.getBytes());

            prodPicRepository.save(picToUpdate);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 移除商品圖片
    public boolean clearImageByIndex(Integer prodId, Integer index) {
        List<ProdPicVO> pics = prodPicRepository.findByProdIdOrderByProdPicIdAsc(prodId);
        if (index >= 0 && index < pics.size()) {
            ProdPicVO pic = pics.get(index);
            pic.setProdPic(null);
            prodPicRepository.save(pic);
            return true;
        }
        return false;
    }

	
	// VO -> DTO
	private ProdPicDTO toDTO(ProdPicVO vo) {
		ProdPicDTO dto = new ProdPicDTO();
		dto.setProdPicId(vo.getProdPicId());
	    dto.setProdId(vo.getProdId());
		return dto;
	}
}
