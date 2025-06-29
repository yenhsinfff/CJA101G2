package com.lutu.article_report.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleReportRepository extends JpaRepository<ArticleReportVO, Integer> {

    // 根據文章ID刪除該文章的所有檢舉記錄
    void deleteByArticlesVO_AcId(Integer acId);

}
