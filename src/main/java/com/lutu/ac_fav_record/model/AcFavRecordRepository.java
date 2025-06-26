package com.lutu.ac_fav_record.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lutu.ac_fav_record.model.AcFavRecordVO.AcFavRecordId;

@Repository
public interface AcFavRecordRepository extends JpaRepository<AcFavRecordVO, AcFavRecordId> {
	
	// 根據會員ID查詢收藏記錄
	List<AcFavRecordVO> findByMemId(Integer memId);
	
	// 根據文章ID查詢收藏記錄
	List<AcFavRecordVO> findByAcId(Integer acId);
	
	// 根據會員ID和文章ID查詢（其實用 findById 也可以，這裡提供另一種方式）
	AcFavRecordVO findByAcIdAndMemId(Integer acId, Integer memId);
	
	// 根據會員ID查詢收藏記錄數量
	long countByMemId(Integer memId);
	
	// 根據文章ID查詢收藏記錄數量
	long countByAcId(Integer acId);
	
	// 根據會員ID刪除該會員的所有收藏記錄
	void deleteByMemId(Integer memId);
	
	// 根據文章ID刪除該文章的所有收藏記錄
	void deleteByAcId(Integer acId);
	
	// 查詢會員的收藏記錄，並按時間排序（最新的在前）
	List<AcFavRecordVO> findByMemIdOrderByAcFavTimeDesc(Integer memId);
	
	// 查詢文章的收藏記錄，並按時間排序（最新的在前）
	List<AcFavRecordVO> findByAcIdOrderByAcFavTimeDesc(Integer acId);
	
	// 使用 JPQL 查詢會員收藏的文章列表（包含文章資訊）
	@Query("SELECT af FROM AcFavRecordVO af JOIN FETCH af.articlesVO WHERE af.memId = :memId ORDER BY af.acFavTime DESC")
	List<AcFavRecordVO> findFavoriteArticlesByMemId(@Param("memId") Integer memId);
	
	// 使用 JPQL 查詢特定時間範圍內的收藏記錄
	@Query("SELECT af FROM AcFavRecordVO af WHERE af.acFavTime BETWEEN :startTime AND :endTime")
	List<AcFavRecordVO> findByAcFavTimeBetween(@Param("startTime") java.time.LocalDateTime startTime, 
	                                          @Param("endTime") java.time.LocalDateTime endTime);
	
	// 查詢熱門文章（被收藏次數最多的文章）
	@Query("SELECT af.acId, COUNT(af.acId) as favCount FROM AcFavRecordVO af GROUP BY af.acId ORDER BY favCount DESC")
	List<Object[]> findPopularArticles();
	
	// 檢查特定會員是否收藏了特定文章
	boolean existsByAcIdAndMemId(Integer acId, Integer memId);
}