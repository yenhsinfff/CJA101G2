package com.lutu.article_image.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UploadSessionRepository extends JpaRepository<UploadSessionVO, String> {

    // 根據狀態查詢上傳會話
    List<UploadSessionVO> findByStatus(String status);

    // 查詢過期的上傳會話（超過指定時間）
    @Query("SELECT u FROM UploadSessionVO u WHERE u.createdTime < :expireTime")
    List<UploadSessionVO> findExpiredSessions(@Param("expireTime") LocalDateTime expireTime);

    // 根據文章ID查詢上傳會話
    List<UploadSessionVO> findByArticleId(Integer articleId);

    // 刪除過期的上傳會話
    @Modifying
    @Transactional
    @Query("DELETE FROM UploadSessionVO u WHERE u.createdTime < :expireTime")
    int deleteExpiredSessions(@Param("expireTime") LocalDateTime expireTime);

    // 根據狀態和時間刪除會話
    @Modifying
    @Transactional
    @Query("DELETE FROM UploadSessionVO u WHERE u.status = :status AND u.createdTime < :expireTime")
    int deleteByStatusAndCreatedTimeBefore(@Param("status") String status,
            @Param("expireTime") LocalDateTime expireTime);
}