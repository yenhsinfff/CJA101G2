package com.lutu.campsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lutu.ApiResponse;
import com.lutu.campsite.model.CampsiteService;
import com.lutu.campsite.model.CampsiteVO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/campsite")
public class CampsiteApiController {
	
	@Autowired
	CampsiteService campsiteSvc;

	

	// 取得所有營地房間
	@GetMapping("/getAllCampsite")
	public ApiResponse<List<CampsiteVO>> getAllCampsite() {
	    List<CampsiteVO> campsiteList = campsiteSvc.getAll();
	    return new ApiResponse<>("success", campsiteList, "查詢成功");
	}
	



}
