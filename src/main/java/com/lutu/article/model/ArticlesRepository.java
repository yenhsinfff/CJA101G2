package com.lutu.article.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface ArticlesRepository extends JpaRepository<ArticlesVO, Integer>{
	

	
	
    // 自訂 JPQL 查詢（舉例：一篇文章加載它的圖片）
    @Query("SELECT a FROM ArticlesVO a LEFT JOIN FETCH a.articleImages WHERE a.acId = :acId")
    Optional<ArticlesVO> findByIdWithImages(@Param("acId") Integer acId);
	
	

}
