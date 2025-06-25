package com.lutu.campsite.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.campsite.model.CampsiteService;
import com.lutu.campsite.model.CampsiteVO;
import com.lutu.campsitetype.model.CampsiteTypeVO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/campsite")
public class CampsiteApiController {
	
	@Autowired
	CampsiteService campsiteSvc;

	

	// 取得所有營地房間
//	@GetMapping("/getAllCampsite")
//	public ApiResponse<List<CampsiteVO>> getAllCampsite() {
//	    List<CampsiteVO> campsiteList = campsiteSvc.getAll();
//	    return new ApiResponse<>("success", campsiteList, "查詢成功");
//	}
	
//	//取得特定營地房型下所有的房間
//	@GetMapping("/{campsiteTypeId}/{campId}/getcampsites")
//	public ApiResponse<Set<CampsiteVO>> getCampsitesByType(
//	        @PathVariable Integer campId,
//	        @PathVariable Integer campsiteTypeId) {
//
//	    CampsiteTypeVO campsiteType = campsiteTypeSvc.getOneCampsiteType(campsiteTypeId, campId);
//	    if (campsiteType == null) {
//	        return new ApiResponse<>("fail", null, "查無此房型");
//	    }
//
//	    Set<CampsiteVO> campsiteList = campsiteType.getCampsites();
//	    return new ApiResponse<>("success", campsiteList, "查詢成功");
//	}



}
