package com.lutu.article.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lutu.ac_fav_record.model.AcFavRecordRepository;
import com.lutu.article_report.model.ArticleReportRepository;
import com.lutu.nice_article.model.NiceArticleRepository;
import com.lutu.reply.model.ReplyRepository;
import com.lutu.reply_report.model.ReplyReportRepository;

@Service("articlesService")
public class ArticlesService {

    @Autowired
    ArticlesRepository repository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    ReplyReportRepository replyReportRepository;

    @Autowired
    ArticleReportRepository articleReportRepository;

    @Autowired
    NiceArticleRepository niceArticleRepository;

    @Autowired
    AcFavRecordRepository acFavRecordRepository;

    public void addArticles(ArticlesVO articlesVO) {
        repository.save(articlesVO);
    }

    public void updateArticles(ArticlesVO articlesVO) {
        repository.save(articlesVO);
    }

    @Transactional
    public void deleteArticles(Integer acId) {
        if (repository.existsById(acId)) {
            // 先刪除相關的 reply_report 記錄
            replyReportRepository.deleteByAcId(acId);

            // 再刪除相關的 reply 記錄
            replyRepository.deleteByArticlesVO_AcId(acId);

            // 刪除相關的文章檢舉記錄
            articleReportRepository.deleteByArticlesVO_AcId(acId);

            // 刪除相關的文章按讚記錄
            niceArticleRepository.deleteByAcId(acId);

            // 刪除相關的文章收藏記錄
            acFavRecordRepository.deleteByAcId(acId);

            // 最後刪除文章 (article_images 會因為 cascade = CascadeType.ALL, orphanRemoval = true
            // 自動刪除)
            repository.deleteById(acId);
        }
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