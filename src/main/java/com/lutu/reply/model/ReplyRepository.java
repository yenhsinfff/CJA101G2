package com.lutu.reply.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyVO, Integer> {

    // 根據文章ID刪除該文章的所有留言
    void deleteByArticlesVO_AcId(Integer acId);

}
