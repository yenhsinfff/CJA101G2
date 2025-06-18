package com.lutu.bundleitem.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public BundleItemVO getOneBundleItem(Integer bundleId) {
		Optional<BundleItemVO> optional = repository.findById(bundleId);
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}


	public List<BundleItemVO> getAll() {
		return repository.findAll();

	}
}
