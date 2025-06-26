package com.lutu.reply_image.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.cj.xdevapi.SessionFactory;

@Service("replyImageService")
public class ReplyImageService {

    @Autowired
    ReplyImageRepository repository;
    
//    @Autowired
//    private SessionFactory sessionFactory;

    public void addReplyImage(ReplyImageVO replyImageVO) {
        repository.save(replyImageVO);
    }

    public void updateReplyImage(ReplyImageVO replyImageVO) {
        repository.save(replyImageVO);
    }

    public void deleteReplyImage(Integer replyImgId) {
        if (repository.existsById(replyImgId))
            repository.deleteById(replyImgId);
    }

    public ReplyImageVO getOneReplyImage(Integer replyImgId) {
        Optional<ReplyImageVO> optional = repository.findById(replyImgId);
        return optional.orElse(null);  // 如果值存在就回傳其值，否則回傳 null
    }

    public List<ReplyImageVO> getAll() {
        return repository.findAll();
    }

}

