
package com.lutu.discount_code.model;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lutu.administrator.model.AdministratorRepository;
import com.lutu.administrator.model.AdministratorVO;
import com.lutu.owner.model.OwnerRepository;
import com.lutu.owner.model.OwnerVO;

import jakarta.transaction.Transactional;




@Service("discountCodeService")
public class DiscountCodeService {
	
	@Autowired
	DiscountCodeRepository repository;
	
	@Autowired
	AdministratorRepository adminRepo;
	
	
	@Autowired
    private SessionFactory sessionFactory;

	//新增折價券
//	public void addDiscountCode(String prefix, DiscountCodeVO discountCodeVO) {
//		
//		//產生折價券ID
//		String newDiscountCodeId = getNextDiscountCodeId(prefix);
//		System.out.println("newDiscountCodeId:"+newDiscountCodeId);
//		discountCodeVO.setDiscountCodeId(newDiscountCodeId);
//		System.out.println("discountCodeVO:"+discountCodeVO);
//		repository.save(discountCodeVO);
//	}
	
	//新增折價券
	@Transactional
	public void addDiscountCode(String prefix, DiscountCodeDTO_insert dto) {
		
		//產生折價券ID
		String newDiscountCodeId = getNextDiscountCodeId(prefix);

		// DTO 轉 VO，存入Entity
		DiscountCodeVO vo = new DiscountCodeVO();
	    vo.setDiscountCodeId(newDiscountCodeId);
	    vo.setDiscountCode(dto.getDiscountCode());
	    vo.setDiscountType(dto.getDiscountType());
	    vo.setDiscountValue(dto.getDiscountValue());
	    vo.setDiscountExplain(dto.getDiscountExplain());
	    vo.setMinOrderAmount(dto.getMinOrderAmount());
	    vo.setStartDate(dto.getStartDate());
	    vo.setEndDate(dto.getEndDate());
	   

	    // 查找 admin
	    if (dto.getAdminId() != null) {
	        AdministratorVO admin = adminRepo.findById(dto.getAdminId())
	            .orElseThrow(() -> new RuntimeException("找不到對應的管理員 ID"));
	        vo.setAdministratorVO(admin);
	    }
	    
		repository.save(vo);

	}

	public void updateDiscountCode(DiscountCodeVO discountCodeVO) {
		
		repository.save(discountCodeVO);
	}

//	public void deleteDiscountCode(Integer discountCodeId) {
//		if (repository.existsById(discountCodeId))
//			repository.deleteById(discountCodeId);
//		
//	}

	public DiscountCodeVO getOneDiscountCode(String discountCodeId) {
		DiscountCodeVO discountCodeVO = repository.findByDiscountCodeId(discountCodeId);
		return discountCodeVO;
	}

	public List<DiscountCodeVO> getAll() {
		return repository.findAll();
	}
	
	// 根據 prefix 產生下一個 discount_code_id
    public String getNextDiscountCodeId(String prefix) {
        List<DiscountCodeVO> allCodes = getAll();

        // 過濾出指定 prefix 的折價券
        Optional<Integer> maxNumber = allCodes.stream()
            .map(DiscountCodeVO::getDiscountCodeId)
            .filter(id -> id != null && id.startsWith(prefix))
            .map(id -> {
                try {
                    return Integer.parseInt(id.substring(1));
                } catch (Exception e) {
                    return null;
                }
            })
            .filter(num -> num != null)
            .max(Comparator.naturalOrder());

        int nextNum = maxNumber.map(num -> num + 1).orElse(1);

        // 組合成 6 碼折價券編號
        return prefix + String.format("%05d", nextNum);
    }

}
