package com.lutu.bundleitem.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("bundleItemService")
public class BundleItemService {
	
	@Autowired
	BundleItemRepository bundleItemRepository;
	
	 @Transactional
		public List<BundleItemVO> getAllBundleItem() {
			
			return bundleItemRepository.findAll();
			
		}
}
