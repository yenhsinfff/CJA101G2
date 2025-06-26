package com.lutu.article_type.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.cj.xdevapi.SessionFactory;

@Service("articleTypeService")
public class ArticleTypeService {

    @Autowired
    ArticleTypeRepository repository;
    
//    @Autowired
//    private SessionFactory sessionFactory;

    public void addArticleType(ArticleTypeVO articleTypeVO) {
        repository.save(articleTypeVO);
    }

    public void updateArticleType(ArticleTypeVO articleTypeVO) {
        repository.save(articleTypeVO);
    }

    public void deleteArticleType(Integer acTypeId) {
        if (repository.existsById(acTypeId))
            repository.deleteById(acTypeId);
    }

    public ArticleTypeVO getOneArticleType(Integer acTypeId) {
        Optional<ArticleTypeVO> optional = repository.findById(acTypeId);
        return optional.orElse(null);  // 如果值存在就回傳其值，否則回傳 null
    }

    public List<ArticleTypeVO> getAll() {
        return repository.findAll();
    }

}

