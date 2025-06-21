package com.lutu.shopProd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lutu.product_type.model.ProdTypeVO;

@Transactional
@Service("shopProdService")
public class ShopProdService {

	@Autowired
	ShopProdRepository repository;
	
//	@Autowired
//    private SessionFactory sessionFactory;


	//新增 DTO
	public ShopProdDTO addProdByDTO(ShopProdDTO dto) {
	    ShopProdVO vo = convertToVO(dto); 
	    repository.save(vo);
	    return convertToDTO(vo); // 回傳儲存後的結果
	}

	//更新 DTO
	public ShopProdDTO updateProdByDTO(ShopProdDTO dto) {
	    Optional<ShopProdVO> opt = repository.findById(dto.getProdId());
	    if (opt.isPresent()) {
	        ShopProdVO vo = opt.get();
	        vo.setProdName(dto.getProdName());
            vo.setProdIntro(dto.getProdIntro());
//            vo.setProdReleaseDate(dto.getProdReleaseDate());
            vo.setProdDiscount(dto.getProdDiscount());
            vo.setProdDiscountStart(dto.getProdDiscountStart());
            vo.setProdDiscountEnd(dto.getProdDiscountEnd());
            vo.setProdCommentCount(dto.getProdCommentCount());
            vo.setProdCommentSumScore(dto.getProdCommentSumScore());
            vo.setProdStatus(dto.getProdStatus());
            vo.setProdColorOrNot(dto.getProdColorOrNot());
            if (dto.getProdTypeId() != null) {
                ProdTypeVO type = new ProdTypeVO();
                type.setProdTypeId(dto.getProdTypeId());
                vo.setProdTypeVO(type);
            }
	        repository.save(vo);
	        return convertToDTO(vo);
	    }
	    return null;
	}

	//查詢單筆產品 DTO
	public ShopProdDTO getProdDTOById(Integer prodId) {
	    Optional<ShopProdVO> optional = repository.findById(prodId);

	    if (optional.isPresent()) {
	        return convertToDTO(optional.get());
	    } else {
	        return null;
	    }
	}
	
	//查詢所有產品 DTO
	public List<ShopProdDTO> getAllProdsByDTO() {
	    List<ShopProdVO> voList = repository.findAll();
	    List<ShopProdDTO> dtoList = new ArrayList<>();

	    for (ShopProdVO vo : voList) {
	        dtoList.add(convertToDTO(vo));
	    }

	    return dtoList;
	}
	
	//關鍵字查詢
	public List<ShopProdDTO> getByKeyword(String keyword) {
	    return repository.findByKeyword(keyword).stream()
	                     .map(this::convertToDTO)
	                     .toList();
	}
	//類別查詢
	public List<ShopProdDTO> getByType(Integer typeId) {
	    return repository.findByProdType(typeId).stream()
	                     .map(this::convertToDTO)
	                     .toList();
	}
	//最新上架
	public List<ShopProdDTO> getLatestProds() {
	    return repository.findByReleaseDateDesc().stream()
	                     .map(this::convertToDTO)
	                     .toList();
	}
	//折扣商品
	public List<ShopProdDTO> getDiscountProds() {
	    return repository.findByDiscounted().stream()
	                     .map(this::convertToDTO)
	                     .toList();
	}
	//隨機推薦
	public List<ShopProdDTO> getRandomProds(int limit) {
	    return repository.findRandom(limit).stream()
	                     .map(this::convertToDTO)
	                     .toList();
	}

	//新增 VO
	public void addProd(ShopProdVO shopProdVO) {
		repository.save(shopProdVO);
	}
	//更新 VO
	public void updateProd(ShopProdVO shopProdVO) {
		repository.save(shopProdVO);
	}
	// 查全部 VO
    public List<ShopProdVO> getAll() {
        return repository.findAll();
    }

	// 查單筆 VO
	public ShopProdVO getProdById(Integer prodId) {
		Optional<ShopProdVO> optional = repository.findById(prodId);
//		Optional<ShopProdVO> optional = repository.selectProdById(prodId);
//		return optional.get(); NoSuchElementException
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}
	
	// ✅ VO ➜ DTO 轉換方法（建議和 getAll 一起共用） 
	private ShopProdDTO convertToDTO(ShopProdVO vo) {
		ShopProdDTO dto = new ShopProdDTO();
	    dto.setProdId(vo.getProdId());
	    dto.setProdName(vo.getProdName());
	    dto.setProdIntro(vo.getProdIntro());
	    dto.setProdReleaseDate(vo.getProdReleaseDate());
	    dto.setProdDiscount(vo.getProdDiscount());
	    dto.setProdDiscountStart(vo.getProdDiscountStart());
	    dto.setProdDiscountEnd(vo.getProdDiscountEnd());
	    dto.setProdCommentCount(vo.getProdCommentCount());
	    dto.setProdCommentSumScore(vo.getProdCommentSumScore());

	    if (vo.getProdTypeVO() != null) {
	        dto.setProdTypeId(vo.getProdTypeVO().getProdTypeId());
	    }

	    dto.setProdStatus(vo.getProdStatus());
	    dto.setProdColorOrNot(vo.getProdColorOrNot());
	    return dto;
	}
	
	// ✅ DTO ➜ VO 轉換方法
	private ShopProdVO convertToVO(ShopProdDTO dto) {
	    ShopProdVO vo = new ShopProdVO();

	    vo.setProdId(dto.getProdId());
	    vo.setProdName(dto.getProdName());
	    vo.setProdIntro(dto.getProdIntro());
	    vo.setProdReleaseDate(dto.getProdReleaseDate());
	    vo.setProdDiscount(dto.getProdDiscount());
	    vo.setProdDiscountStart(dto.getProdDiscountStart());
	    vo.setProdDiscountEnd(dto.getProdDiscountEnd());
	    vo.setProdCommentCount(dto.getProdCommentCount());
	    vo.setProdCommentSumScore(dto.getProdCommentSumScore());
	    vo.setProdStatus(dto.getProdStatus());
	    vo.setProdColorOrNot(dto.getProdColorOrNot());

	    // 如果 DTO 有 prodTypeId，建立一個簡單的 ProdTypeVO 對象塞進去
	    if (dto.getProdTypeId() != null) {
	        ProdTypeVO type = new ProdTypeVO();
	        type.setProdTypeId(dto.getProdTypeId());
	        vo.setProdTypeVO(type);
	    }

	    return vo;
	}


	//複合查詢
//	public List<ShopProdVO> getAll(Map<String, String[]> map) {
//		return HibernateUtil_CompositeQuery_Emp3.getAllC(map,sessionFactory.openSession());
//	}

}