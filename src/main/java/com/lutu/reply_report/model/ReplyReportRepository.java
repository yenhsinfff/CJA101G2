package com.lutu.reply_report.model;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface ReplyReportRepository extends JpaRepository<ReplyReportVO, Integer>{
	
	

}




