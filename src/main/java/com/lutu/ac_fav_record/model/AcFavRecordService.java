package com.lutu.ac_fav_record.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lutu.ac_fav_record.model.AcFavRecordVO.AcFavRecordId;

@Service("acFavRecordService")
public class AcFavRecordService {
	
	@Autowired
	AcFavRecordRepository repository;
	
//	@Autowired
//	private SessionFactory sessionFactory;
	
//	@Autowired
//	private com.mysql.cj.xdevapi.SessionFactory sessionFactory;
	

	
	public void addAcFavRecord(AcFavRecordVO acFavRecordVO) {
		repository.save(acFavRecordVO);
	}
	
	public void updateAcFavRecord(AcFavRecordVO acFavRecordVO) {
		repository.save(acFavRecordVO);
	}
	
	public void deleteAcFavRecord(AcFavRecordId compositeKey) {
		if (repository.existsById(compositeKey))
			repository.deleteById(compositeKey);
	}
	
	// 也可以提供使用 acId 和 memId 的刪除方法
	public void deleteAcFavRecord(Integer acId, Integer memId) {
		AcFavRecordId compositeKey = new AcFavRecordId(acId, memId);
		if (repository.existsById(compositeKey))
			repository.deleteById(compositeKey);
	}
	
	public AcFavRecordVO getOneAcFavRecord(AcFavRecordId compositeKey) {
		Optional<AcFavRecordVO> optional = repository.findById(compositeKey);
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}
	
	// 也可以提供使用 acId 和 memId 的查詢方法
	public AcFavRecordVO getOneAcFavRecord(Integer acId, Integer memId) {
		AcFavRecordId compositeKey = new AcFavRecordId(acId, memId);
		Optional<AcFavRecordVO> optional = repository.findById(compositeKey);
		return optional.orElse(null);
	}
	
	public List<AcFavRecordVO> getAll() {
		return repository.findAll();
	}
	
	// 額外的業務方法：根據會員ID查詢收藏記錄
	public List<AcFavRecordVO> getByMemId(Integer memId) {
		return repository.findByMemId(memId);
	}
	
	// 額外的業務方法：根據文章ID查詢收藏記錄
	public List<AcFavRecordVO> getByAcId(Integer acId) {
		return repository.findByAcId(acId);
	}
	
	// 額外的業務方法：檢查特定會員是否已收藏特定文章
	public boolean isFavorited(Integer acId, Integer memId) {
		AcFavRecordId compositeKey = new AcFavRecordId(acId, memId);
		return repository.existsById(compositeKey);
	}
}