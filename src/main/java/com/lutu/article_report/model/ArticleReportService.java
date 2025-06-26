package com.lutu.article_report.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.cj.xdevapi.SessionFactory;

@Service("articleReportService")
public class ArticleReportService {

    @Autowired
    ArticleReportRepository repository;
    
//    @Autowired
//    private SessionFactory sessionFactory;

    public void addArticleReport(ArticleReportVO articleReportVO) {
        repository.save(articleReportVO);
    }

    public void updateArticleReport(ArticleReportVO articleReportVO) {
        repository.save(articleReportVO);
    }

    public void deleteArticleReport(Integer acReportId) {
        if (repository.existsById(acReportId))
            repository.deleteById(acReportId);
    }

    public ArticleReportVO getOneArticleReport(Integer articleReport) {
        Optional<ArticleReportVO> optional = repository.findById(articleReport);
        return optional.orElse(null);  // public T orElse(T other): 如果值存在就回傳其值，否則回傳 other 的值
    }

    public List<ArticleReportVO> getAll() {
        return repository.findAll();
    }

}

