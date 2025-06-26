package com.lutu.article.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("articlesService")
public class ArticlesService {

    @Autowired
    ArticlesRepository repository;

    public void addArticles(ArticlesVO articlesVO) {
        repository.save(articlesVO);
    }

    public void updateArticles(ArticlesVO articlesVO) {
        repository.save(articlesVO);
    }

    public void deleteArticles(Integer acId) {
        if (repository.existsById(acId))
            repository.deleteById(acId);
    }

    public ArticlesVO getOneArticles(Integer acId) {
        Optional<ArticlesVO> optional = repository.findById(acId);
        // return optional.get();
        return optional.orElse(null); // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
    }

    public List<ArticlesVO> getAll() {
        return repository.findAll();
    }



}