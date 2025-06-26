package com.lutu.nice_article.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NiceArticleRepository extends JpaRepository<NiceArticleVO, NiceArticleId> {
	
	// 根據會員ID查詢按讚記錄
	List<NiceArticleVO> findByMemId(Integer memId);
	
	// 根據文章ID查詢按讚記錄
	List<NiceArticleVO> findByAcId(Integer acId);
	
	// 根據會員ID和文章ID查詢
	NiceArticleVO findByAcIdAndMemId(Integer acId, Integer memId);
	
	// 根據會員ID查詢按讚記錄數量
	long countByMemId(Integer memId);
	
	// 根據文章ID查詢按讚記錄數量
	long countByAcId(Integer acId);
	
	// 根據會員ID刪除該會員的所有按讚記錄
	void deleteByMemId(Integer memId);
	
	// 根據文章ID刪除該文章的所有按讚記錄
	void deleteByAcId(Integer acId);
	
	// 查詢會員的按讚記錄，並按時間排序（最新的在前）
	List<NiceArticleVO> findByMemIdOrderByLikeTimeDesc(Integer memId);
	
	// 查詢文章的按讚記錄，並按時間排序（最新的在前）
	List<NiceArticleVO> findByAcIdOrderByLikeTimeDesc(Integer acId);
	
	// 使用 JPQL 查詢會員按讚的文章列表（包含文章資訊）
	@Query("SELECT na FROM NiceArticleVO na JOIN FETCH na.articlesVO WHERE na.memId = :memId ORDER BY na.likeTime DESC")
	List<NiceArticleVO> findLikedArticlesByMemId(@Param("memId") Integer memId);
	
	// 使用 JPQL 查詢特定時間範圍內的按讚記錄
	@Query("SELECT na FROM NiceArticleVO na WHERE na.likeTime BETWEEN :startTime AND :endTime")
	List<NiceArticleVO> findByLikeTimeBetween(@Param("startTime") java.time.LocalDateTime startTime, 
	                                          @Param("endTime") java.time.LocalDateTime endTime);
	
	// 查詢熱門文章（被按讚次數最多的文章）
	@Query("SELECT na.acId, COUNT(na.acId) as likeCount FROM NiceArticleVO na GROUP BY na.acId ORDER BY likeCount DESC")
	List<Object[]> findPopularArticles();
	
	// 檢查特定會員是否已按讚特定文章
	boolean existsByAcIdAndMemId(Integer acId, Integer memId);
}
