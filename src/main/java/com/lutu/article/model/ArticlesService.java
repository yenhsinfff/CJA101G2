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

    public List<ArticlesVO> findByAcTitleContaining(String acTitle) {
        return repository.findByAcTitleContaining(acTitle);
    }

    public List<ArticlesVO> findByAcTitleAndOptionalType(String acTitle, Integer acTypeId) {
        if (acTypeId == null) {
            return repository.findByAcTitleContaining(acTitle);
        } else {
            return repository.findByAcTitleContainingAndArticleTypeVO_AcTypeId(acTitle, acTypeId);
        }
    }

    public List<ArticlesVO> findByAcContextContaining(String acContext) {
        return repository.findByAcContextContaining(acContext);
    }

    public List<ArticlesVO> findByAcTitleOrContextContaining(String keyword) {
        return repository.findByAcTitleContainingOrAcContextContaining(keyword, keyword);
    }

    public List<ArticlesVO> findByAcTitleOrContextAndOptionalType(String keyword, Integer acTypeId) {
        if (acTypeId == null) {
            return repository.findByAcTitleContainingOrAcContextContaining(keyword, keyword);
        } else {
            return repository
                    .findByAcTitleContainingAndArticleTypeVO_AcTypeIdOrAcContextContainingAndArticleTypeVO_AcTypeId(
                            keyword, acTypeId, keyword, acTypeId);
        }
    }

    /**
     * 根據文章類別查詢文章數量
     * 
     * @param acTypeId 文章類別ID
     * @return 該類別的文章數量
     */
    public long getArticleCountByType(Integer acTypeId) {
        return repository.countByArticleTypeVOAcTypeId(acTypeId);
    }

    /**
     * 根據會員ID查詢文章數量
     * 
     * @param memId 會員ID
     * @return 該會員的文章數量
     */
    public long getArticleCountByMember(Integer memId) {
        return repository.countByMemberVOMemId(memId);
    }

    /**
     * 根據留言者姓名查詢所有有該留言者留言的文章
     * 
     * @param memName 留言者姓名
     * @return 有該留言者留言的所有文章
     */
    public List<ArticlesVO> findArticlesByReplyMemberName(String memName) {
        return repository.findDistinctArticlesByReplyMemberName(memName);
    }

    // 根據會員名稱查詢所有有該會員發表的文章
    public List<ArticlesVO> findByMemberName(String memName) {
        return repository.findByMemberVOMemName(memName);
    }

}