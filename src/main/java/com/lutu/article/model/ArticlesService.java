package com.lutu.article.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lutu.camp.model.CampRepository;
import com.lutu.camp.model.CampVO;

public class ArticlesService {

	@Autowired
	ArticlesRepository repository;
	
	@Autowired
//	private SessionFactory sessionFactory;
	
    @Transactional
	public List<ArticlesVO> getAllArticles() {
		
		return repository.findAll();
		
	}
    
//    @Transactional
    public ArticlesVO getAllArticles(Integer acId) {
    	ArticlesVO articles = repository.findById(acId).orElse(null);
//    	byte[] img = (camp != null) ? camp.getCampPic1() : null;
//    	if (camp != null) {
//            camp.getCampsiteOrders().size(); // 強制初始化
//        }
    	return articles;
    	
    }
    
    public ArticlesVO createOneArticles(ArticlesVO articlesVO) {
    	repository.save(articlesVO);
    	ArticlesVO articlesVO2 = getOneArticles(articlesVO.getAcId());
    	return articlesVO2;
	}
}
