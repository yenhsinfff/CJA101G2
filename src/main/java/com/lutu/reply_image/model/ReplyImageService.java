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

    // @Autowired
    // private SessionFactory sessionFactory;

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
        return optional.orElse(null); // 如果值存在就回傳其值，否則回傳 null
    }

    public List<ReplyImageVO> getAll() {
        return repository.findAll();
    }

    // 根據留言ID取得該留言的所有圖片
    public List<ReplyImageVO> getReplyImagesByReplyId(Integer replyId) {
        return repository.findByReplyVO_ReplyId(replyId);
    }

    // 批量刪除留言的所有圖片
    public void deleteImagesByReplyId(Integer replyId) {
        List<ReplyImageVO> images = getReplyImagesByReplyId(replyId);
        for (ReplyImageVO image : images) {
            repository.delete(image);
        }
    }

}
