package com.lutu.bundleitem.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service("bundleItemService")
public class BundleItemService {

	@Autowired
	BundleItemRepository repository;
	
	public void addBundleItem(BundleItemVO bundleItemVO) {
		repository.save(bundleItemVO);
	}
	
	public void updateBundleItem(BundleItemVO bundleItemVO) {
		repository.save(bundleItemVO);
	}
	
	public void deleteBundleItem(Integer bundleId) {
		if (repository.existsById(bundleId))
			repository.deleteById(bundleId);
	}
	
	@Transactional
	public BundleItemVO getOneBundleItem(Integer bundleId) {
		Optional<BundleItemVO> optional = repository.findById(bundleId);
		if (optional.isPresent()) {
	        BundleItemVO vo = optional.get();	        
	        vo.getBundleItemDetails().size(); 
	        return vo;
	    }
	    return null;
	}


	public List<BundleItemVO> getAll() {
		return repository.findAll();

	}
}
