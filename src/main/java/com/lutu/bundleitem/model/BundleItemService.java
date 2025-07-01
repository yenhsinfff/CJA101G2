package com.lutu.bundleitem.model;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service("bundleItemService")
public class BundleItemService {

	@Autowired
	BundleItemRepository repository;
	
	public BundleItemDTO addBundleItem(BundleItemDTO dto) {
		 // 將 DTO 轉為 Entity
        BundleItemVO entity = new BundleItemVO();
        entity.setCampId(dto.getCampId());
        entity.setBundleName(dto.getBundleName());
        entity.setBundlePrice(dto.getBundlePrice());
        entity.setBundleAddDate(LocalDate.now()); // 設定當日日期

        // 儲存至資料庫
        BundleItemVO saved = repository.save(entity);

        // 將儲存後的 Entity 轉為 DTO 回傳
        BundleItemDTO result = new BundleItemDTO();
        result.setBundleId(saved.getBundleId());
        result.setCampId(saved.getCampId());
        result.setBundleName(saved.getBundleName());
        result.setBundlePrice(saved.getBundlePrice());
        result.setBundleAddDate(saved.getBundleAddDate());

        return result;
	}
	
	
	public BundleItemDTO updateBundleItem(BundleItemDTO dto) {
	    Optional<BundleItemVO> optional = repository.findById(dto.getBundleId());

	    if (optional.isEmpty()) {
	        throw new NoSuchElementException("找不到要更新的加購項目：" + dto.getBundleId());
	    }

	    BundleItemVO entity = optional.get();
	    entity.setCampId(dto.getCampId());
	    entity.setBundleName(dto.getBundleName());
	    entity.setBundlePrice(dto.getBundlePrice());
	    entity.setBundleAddDate(LocalDate.now()); // 改為當下日期

	    BundleItemVO saved = repository.save(entity);

	    return new BundleItemDTO(
	        saved.getBundleId(),
	        saved.getCampId(),
	        saved.getBundleName(),
	        saved.getBundleAddDate(),
	        saved.getBundlePrice()
	    );
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
	
	// 查詢特定營地下的加購項目
		public List<BundleItemVO> getByCampId(Integer campId) {
			return repository.findByCampId(campId);
		}
}
