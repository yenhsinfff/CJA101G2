package com.lutu.article_type.model;

import java.util.*;

public interface  ArticleTypeDAO_interface {

    public void insert(ArticleTypeVO ArticleTypeVO);
    
    public void update(ArticleTypeVO ArticleTypeVO);
    
    public void delete(Integer acTypeId); 
    
    public ArticleTypeVO findByPrimaryKey(Integer acTypeId);
   
    public List<ArticleTypeVO> getAll();
    
	
	
}
