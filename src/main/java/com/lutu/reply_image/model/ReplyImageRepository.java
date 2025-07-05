package com.lutu.reply_image.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface ReplyImageRepository extends JpaRepository<ReplyImageVO, Integer> {

    // 根據留言ID取得該留言的所有圖片
    List<ReplyImageVO> findByReplyVO_ReplyId(Integer replyId);

    // 可選：根據留言ID和圖片ID查詢特定圖片
    @Query("SELECT ri FROM ReplyImageVO ri WHERE ri.replyVO.replyId = :replyId AND ri.replyImgId = :replyImgId")
    Optional<ReplyImageVO> findByReplyIdAndImageId(@Param("replyId") Integer replyId,
            @Param("replyImgId") Integer replyImgId);
}
