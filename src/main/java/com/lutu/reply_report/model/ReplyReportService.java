package com.lutu.reply_report.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.cj.xdevapi.SessionFactory;

@Service("replyReportService")
public class ReplyReportService {

    @Autowired
    ReplyReportRepository repository;
    
//    @Autowired
//    private SessionFactory sessionFactory;

    public void addReplyReport(ReplyReportVO replyReportVO) {
        repository.save(replyReportVO);
    }

    public void updateReplyReport(ReplyReportVO replyReportVO) {
        repository.save(replyReportVO);
    }

    public void deleteReplyReport(Integer replyReportId) {
        if (repository.existsById(replyReportId))
            repository.deleteById(replyReportId);
    }

    public ReplyReportVO getOneReplyReport(Integer replyReportId) {
        Optional<ReplyReportVO> optional = repository.findById(replyReportId);
        return optional.orElse(null);  // 如果值存在就回傳其值，否則回傳 null
    }

    public List<ReplyReportVO> getAll() {
        return repository.findAll();
    }

}

