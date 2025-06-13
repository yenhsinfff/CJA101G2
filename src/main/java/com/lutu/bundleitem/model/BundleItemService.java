package com.lutu.bundleitem.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lutu.camp_hibernate.model.CampRepository;
import com.lutu.camp_hibernate.model.CampVO;

@Service("bundleItemService")
public class BundleItemService {
	
	@Autowired
	BundleItemRepository bundleItemRepository;
	
	 @Transactional
		public List<BundleItemVO> getAllBundleItem() {
			
			return bundleItemRepository.findAll();
			
		}
}
