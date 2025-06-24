package com.lutu.bundleitemdetails.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service("bundleItemDetailsService")
public class BundleItemDetailsService {

	@Autowired
	BundleItemDetailsRepository repository;
	
	public void addBundleItemDetails(BundleItemDetailsVO bundleItemDetailsVO) {
		repository.save(bundleItemDetailsVO);
	}
	
	public void updateBundleItemDetails(BundleItemDetailsVO bundleItemDetailsVO) {
		repository.save(bundleItemDetailsVO);
	}
	
	public void deleteBundleItemDetails(Integer bundleDetailsId) {
		if (repository.existsById(bundleDetailsId))
			repository.deleteById(bundleDetailsId);
	}
	
	@Transactional
	public BundleItemDetailsVO getOneBundleItemDetails(Integer bundleDetailsId) {
		Optional<BundleItemDetailsVO> optional = repository.findById(bundleDetailsId);
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}
	
	public List<BundleItemDetailsVO> getAll() {
		return repository.findAll();

	}
	
	
}
