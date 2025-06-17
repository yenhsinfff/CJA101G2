package com.lutu.article_type.model;

import java.sql.Date;
import java.util.List;

public class ArticleTypeService {
	
	private ArticleTypeDAO_interface dao;
	
	public ArticleTypeService() {
		dao = new ArticleTypeDAO();
	}
	
	public ArticleTypeVO addArticleType(ArticleTypeVO articleTypeVO) {
		dao.insert(articleTypeVO);
		return articleTypeVO;
	}
	
	public ArticleTypeVO updateArticleType(ArticleTypeVO articleTypeVO) {
		dao.update(articleTypeVO);
		return articleTypeVO;
	}

    public void deleteArticleType(Integer acTypeId) {
        dao.delete(acTypeId);
    }
    
    public ArticleTypeVO getOneArticleType(Integer acTypeId) {
        return dao.getOneArticleType(acTypeId);
    }

    public List<ArticleTypeVO> getAll() {
        return dao.getAll();
    }
}
