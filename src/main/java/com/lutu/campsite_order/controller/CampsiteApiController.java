package com.lutu.campsite_order.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.camp.model.CampService;
import com.lutu.campsite_order.model.CampSiteOrderService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class CampsiteApiController {
	
	@Autowired
	CampSiteOrderService campsiteOrdSvc;
	
	@Autowired
	CampService campSvc;
	
	@GetMapping("/api/campsite/setorder")
	public void setCampsiteorder(@PathVariable Integer campId, HttpServletResponse response) throws IOException {

		byte[] img = (campSvc.getOneCamp(campId)).getCampPic1(); // 從資料庫取得

		response.setContentType("image/jpeg");
		response.getOutputStream().write(img);
	}

}
