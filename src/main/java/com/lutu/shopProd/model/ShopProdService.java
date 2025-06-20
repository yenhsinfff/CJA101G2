package com.lutu.shopProd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("ShopProdService")
public class ShopProdService {

	@Autowired
	ShopProdRepository repository;
	
	@Autowired
    private SessionFactory sessionFactory;

	public void addProd(ShopProdVO shopProdVO) {
		repository.save(shopProdVO);
	}

	public void updateProd(ShopProdVO shopProdVO) {
		repository.save(shopProdVO);
	}

//	public void deleteEmp(Integer prodId) {
//		if (repository.existsById(prodId)) {
//			repository.deleteByProdId(prodId);
//		    repository.deleteById(prodId);
//		}
//	}
	
	// 查單筆 VO
	public ShopProdVO getProdById(Integer prodId) {
		Optional<ShopProdVO> optional = repository.findById(prodId);
//		Optional<ShopProdVO> optional = repository.selectProdById(prodId);
//		return optional.get(); NoSuchElementException
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}
	
	//查詢單筆產品 DTO
	public ShopProdSelectDTO getProdDTOById(Integer prodId) {
	    Optional<ShopProdVO> optional = repository.findById(prodId);

	    if (optional.isPresent()) {
	        return convertToDTO(optional.get());
	    } else {
	        return null;
	    }
	}
	
   // 查全部 VO
    public List<ShopProdVO> getAll() {
        return repository.findAll();
    }

	//查詢所有產品 DTO
	public List<ShopProdSelectDTO> getAllProdsByDTO() {
	    List<ShopProdVO> voList = repository.findAll();
	    List<ShopProdSelectDTO> dtoList = new ArrayList<>();

	    for (ShopProdVO vo : voList) {
	        dtoList.add(convertToDTO(vo));
	    }

	    return dtoList;
	}
	
	
	// ✅ VO ➜ DTO 轉換方法（建議和 getAll 一起共用） 
	private ShopProdSelectDTO convertToDTO(ShopProdVO vo) {
	    ShopProdSelectDTO dto = new ShopProdSelectDTO();
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

	//複合查詢
//	public List<ShopProdVO> getAll(Map<String, String[]> map) {
//		return HibernateUtil_CompositeQuery_Emp3.getAllC(map,sessionFactory.openSession());
//	}

}