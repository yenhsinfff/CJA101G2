package com.lutu.article_image.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface ArticleImageRepository extends JpaRepository<ArticleImageVO, Integer> {

    // 根據文章ID查詢該文章的所有圖片
    List<ArticleImageVO> findByArticlesVO_AcId(Integer acId);

}
