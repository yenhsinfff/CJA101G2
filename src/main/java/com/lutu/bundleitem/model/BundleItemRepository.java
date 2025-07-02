package com.lutu.bundleitem.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BundleItemRepository extends JpaRepository<BundleItemVO, Integer>{

//	@Transactional
//	@Modifying
//	@Query(value = "delete from bundle_item where bundle_id =?1", nativeQuery = true)
//	void deleteByBundleId(int bundleId);
	
	List<BundleItemVO> findByCampId(Integer campId);
	
	
}
