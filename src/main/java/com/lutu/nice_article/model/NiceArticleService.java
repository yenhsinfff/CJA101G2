package com.lutu.nice_article.model;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("niceArticleService")
public class NiceArticleService {

	@Autowired
	NiceArticleRepository repository;

	@Autowired
	private SessionFactory sessionFactory;

	public void addNiceArticle(NiceArticleVO niceArticleVO) {
		repository.save(niceArticleVO);
	}

	public void updateNiceArticle(NiceArticleVO niceArticleVO) {
		repository.save(niceArticleVO);
	}

	public void deleteNiceArticle(NiceArticleId compositeKey) {
		if (repository.existsById(compositeKey))
			repository.deleteById(compositeKey);
	}

	// 也可以提供使用 acId 和 memId 的刪除方法
	public void deleteNiceArticle(Integer acId, Integer memId) {
		NiceArticleId compositeKey = new NiceArticleId(acId, memId);
		if (repository.existsById(compositeKey))
			repository.deleteById(compositeKey);
	}

	public NiceArticleVO getOneNiceArticle(NiceArticleId compositeKey) {
		Optional<NiceArticleVO> optional = repository.findById(compositeKey);
		return optional.orElse(null); // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	// 也可以提供使用 acId 和 memId 的查詢方法
	public NiceArticleVO getOneNiceArticle(Integer acId, Integer memId) {
		NiceArticleId compositeKey = new NiceArticleId(acId, memId);
		Optional<NiceArticleVO> optional = repository.findById(compositeKey);
		return optional.orElse(null);
	}

	public List<NiceArticleVO> getAll() {
		return repository.findAll();
	}

	// 額外的業務方法：根據會員ID查詢按讚記錄
	public List<NiceArticleVO> getByMemId(Integer memId) {
		return repository.findByMemId(memId);
	}

	// 額外的業務方法：根據文章ID查詢按讚記錄
	public List<NiceArticleVO> getByAcId(Integer acId) {
		return repository.findByAcId(acId);
	}

	// 額外的業務方法：檢查特定會員是否已按讚特定文章
	public boolean isLiked(Integer acId, Integer memId) {
		NiceArticleId compositeKey = new NiceArticleId(acId, memId);
		return repository.existsById(compositeKey);
	}

	// 額外的業務方法：統計文章被按讚次數
	public long getLikeCount(Integer acId) {
		return repository.countByAcId(acId);
	}

	// 額外的業務方法：統計會員按讚次數
	public long getMemberLikeCount(Integer memId) {
		return repository.countByMemId(memId);
	}

	// 額外的業務方法：查詢會員按讚的文章（含文章資訊）
	public List<NiceArticleVO> getLikedArticlesByMember(Integer memId) {
		return repository.findLikedArticlesByMemId(memId);
	}

	// 查詢熱門文章（按讚數最多）
	public List<Object[]> getPopularArticles() {
		return repository.findPopularArticles();
	}
}
