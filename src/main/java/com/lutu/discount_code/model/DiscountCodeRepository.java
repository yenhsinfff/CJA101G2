
package com.lutu.discount_code.model;



import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.lutu.campsite_order.model.CampSiteOrderVO;

public interface DiscountCodeRepository extends JpaRepository<DiscountCodeVO, String>{
	
	@Transactional
	@Modifying
	@Query(value = "delete from discount_code where discountCodeId =?1", nativeQuery = true)
	void deleteByDiscountCodeId(int discountCodeId);

	//● (自訂)條件查詢
	@Query(value = "from DiscountCodeVO where discountCodeId=?1 order by discountCodeId")
	List<DiscountCodeVO> findByOthers(int discountCodeId);
	
	@Query("from DiscountCodeVO where discountCodeId = ?1")
	DiscountCodeVO findByDiscountCodeId(String discountCodeId);

}