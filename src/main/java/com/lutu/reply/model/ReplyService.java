package com.lutu.reply.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.cj.xdevapi.SessionFactory;

@Service("replyService")
public class ReplyService {

    @Autowired
    ReplyRepository repository;

    // @Autowired
    // private SessionFactory sessionFactory;

    public void addReply(ReplyVO replyVO) {
        repository.save(replyVO);
    }

    public void updateReply(ReplyVO replyVO) {
        repository.save(replyVO);
    }

    public void deleteReply(Integer replyId) {
        if (repository.existsById(replyId))
            repository.deleteById(replyId);
    }

    public ReplyVO getOneReply(Integer replyId) {
        Optional<ReplyVO> optional = repository.findById(replyId);
        return optional.orElse(null); // 如果值存在就回傳其值，否則回傳 null
    }

    public List<ReplyVO> getAll() {
        return repository.findAll();
    }

    // 根據文章ID取得留言，並載入會員資料
    public List<ReplyVO> getRepliesByArticleId(Integer acId) {
        return repository.findByArticleIdWithMember(acId);
    }

}
