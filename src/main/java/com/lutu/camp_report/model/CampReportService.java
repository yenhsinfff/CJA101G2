package com.lutu.camp_report.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("campReportService")
public class CampReportService {
	
	@Autowired
	CampReportRepository campReportRepository;
	
	@Transactional
	public List<CampReportVO> getAllCampReport() {
		
		return campReportRepository.findAll();
		
	}

}
