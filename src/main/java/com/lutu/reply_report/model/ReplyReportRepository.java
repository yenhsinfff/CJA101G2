package com.lutu.reply_report.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyReportRepository extends JpaRepository<ReplyReportVO, Integer> {

    // 根據文章ID刪除該文章的所有留言檢舉記錄
    void deleteByAcId(Integer acId);

}
