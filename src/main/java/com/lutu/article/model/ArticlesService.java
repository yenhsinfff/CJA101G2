package com.lutu.article.model;

import java.util.List;
import java.util.Optional;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lutu.article.model.ArticlesVO;
import com.lutu.article.model.ArticlesRepository;

@Service("articlesService")
@Transactional
public class ArticlesService {

    @Autowired
    private ArticlesRepository repository;

    @Autowired
    private SessionFactory sessionFactory;

    // 方案1：使用 Hibernate Session（推薦用於需要延遲載入的情況）
    @Transactional(readOnly = true)
    public ArticlesVO getArticleWithImages(Integer articleId) {
        Session session = sessionFactory.getCurrentSession();
        ArticlesVO article = session.get(ArticlesVO.class, articleId);
        
        if (article != null) {
            // 強制初始化需要的集合
            Hibernate.initialize(article.getArticleImages());
        }
        
        return article;
    }

    // 方案2：使用 HQL Fetch Join（更推薦）
    @Transactional(readOnly = true)
    public ArticlesVO getArticleWithImagesByHQL(Integer articleId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT a FROM ArticlesVO a LEFT JOIN FETCH a.articleImages WHERE a.acId = :acId";
        
        return session.createQuery(hql, ArticlesVO.class)
                     .setParameter("acId", articleId)
                     .uniqueResult();
    }

    // 方案3：使用 JPA Repository（適用於不需要延遲載入的情況）
    @Transactional(readOnly = true)
    public ArticlesVO getOneArticles(Integer acId) {
        Optional<ArticlesVO> optional = repository.findById(acId);
        return optional.orElse(null);
    }

    @Transactional
    public void addArticles(ArticlesVO articlesVO) {
        repository.save(articlesVO);
    }

    @Transactional
    public void updateArticles(ArticlesVO articlesVO) {
        repository.save(articlesVO);
    }

    @Transactional
    public void deleteArticles(Integer acId) {
        if (repository.existsById(acId)) {
            repository.deleteById(acId);
        }
    }

    @Transactional(readOnly = true)
    public List<ArticlesVO> getAll() {
        return repository.findAll();
    }

    // 如果需要載入所有關聯資料
    @Transactional(readOnly = true)
    public ArticlesVO getArticleWithAllRelations(Integer articleId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = """
            SELECT DISTINCT a FROM ArticlesVO a 
            LEFT JOIN FETCH a.articleImages 
            LEFT JOIN FETCH a.replies 
            LEFT JOIN FETCH a.niceArticleVO 
            LEFT JOIN FETCH a.articleReport 
            LEFT JOIN FETCH a.acFavRecord 
            WHERE a.acId = :acId
            """;
        
        return session.createQuery(hql, ArticlesVO.class)
                     .setParameter("acId", articleId)
                     .uniqueResult();
    }

    // 只初始化特定的集合
    @Transactional(readOnly = true)
    public ArticlesVO getArticleWithSpecificCollections(Integer articleId, boolean loadImages, boolean loadReplies) {
        Session session = sessionFactory.getCurrentSession();
        ArticlesVO article = session.get(ArticlesVO.class, articleId);
        
        if (article != null) {
            if (loadImages) {
                Hibernate.initialize(article.getArticleImages());
            }
            if (loadReplies) {
                Hibernate.initialize(article.getReplies());
            }
        }
        
        return article;
    }

    // 萬用複合查詢-可由客戶端隨意增減任何想查詢的欄位
    // public List<ArticlesVO> getAll(Map<String, String[]> map) {
    //     return HibernateUtil_CompositeQuery_Emp3.getAllC(map, sessionFactory.openSession());
    // }
}