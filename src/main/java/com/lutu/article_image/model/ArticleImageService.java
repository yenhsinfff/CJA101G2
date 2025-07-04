package com.lutu.article_image.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lutu.ac_fav_record.model.AcFavRecordRepository;
import com.mysql.cj.xdevapi.SessionFactory;

@Service("articleImageService")
public class ArticleImageService {

    @Autowired
    ArticleImageRepository repository;

    // @Autowired
    // private SessionFactory sessionFactory;

    // @Autowired
    // private com.mysql.cj.xdevapi.SessionFactory sessionFactory;

    public void addArticleImage(ArticleImageVO articleImageVO) {
        repository.save(articleImageVO);
    }

    public void updateArticleImage(ArticleImageVO articleImageVO) {
        repository.save(articleImageVO);
    }

    public void deleteArticleImage(Integer acImgId) {
        if (repository.existsById(acImgId))
            repository.deleteById(acImgId);
    }

    public ArticleImageVO getOneArticleImage(Integer acImgId) {
        Optional<ArticleImageVO> optional = repository.findById(acImgId);
        // return optional.get();
        return optional.orElse(null); // public T orElse(T other): 如果值存在就回傳其值，否則回傳 other 的值
    }

    public List<ArticleImageVO> getAll() {
        return repository.findAll();
    }

    // 根據文章ID取得該文章的所有圖片
    public List<ArticleImageVO> getArticleImagesByArticleId(Integer acId) {
        return repository.findByArticlesVO_AcId(acId);
    }

}
