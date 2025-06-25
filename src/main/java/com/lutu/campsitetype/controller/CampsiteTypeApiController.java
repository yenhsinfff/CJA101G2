package com.lutu.campsitetype.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.campsite.model.CampsiteVO;
import com.lutu.campsitetype.model.CampsiteTypeService;
import com.lutu.campsitetype.model.CampsiteTypeVO;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/campsitetype")
public class CampsiteTypeApiController {
	
	@Autowired
	CampsiteTypeService campsiteTypeSvc;



	// 取得所有營地房型
	@GetMapping("/getCampsiteTypes")
	public ApiResponse<List<CampsiteTypeVO>> getCampsiteTypeList(@PathVariable Integer campId) {
	    List<CampsiteTypeVO> campsiteTypeList = campsiteTypeSvc.getByCampId(campId);
	    return new ApiResponse<>("success", campsiteTypeList, "查詢成功");
	}
	
	// 查詢特定營地的房型
	@GetMapping("/{campId}/getCampsiteTypes")
	public ApiResponse<List<CampsiteTypeVO>> getCampsiteTypeList() {
	    List<CampsiteTypeVO> campsiteTypeList = campsiteTypeSvc.getAll();
	    return new ApiResponse<>("success", campsiteTypeList, "查詢成功");
	}

	
	//查詢房型的房間List
	@GetMapping("/getCampsites")
	public ApiResponse<Set<CampsiteVO>> getCampsitesList(
	        @RequestParam Integer campId,
	        @RequestParam Integer campsiteTypeId) {

	    CampsiteTypeVO campsiteTypeVO = campsiteTypeSvc.getOneCampsiteType(campsiteTypeId, campId);

	    if (campsiteTypeVO == null) {
	        return new ApiResponse<>("fail", null, "查無此房型");
	    }

	    Set<CampsiteVO> campsites = campsiteTypeVO.getCampsites();
	    return new ApiResponse<>("success", campsites, "查詢成功");
	}
	
	// 新增營地房型(可自動抓取營地主ID，會員session設置後再開啟此版本)
//	@PostMapping("/addCampsiteType")
//	public ApiResponse<CampsiteTypeVO> addCampsiteType(@RequestBody CampsiteTypeVO campsiteTypeVO,
//	                                                   HttpSession session) {
//	    Integer campId = (Integer) session.getAttribute("campId"); // 或從 SecurityContext 拿
//	    CampsiteTypeVO campsiteType = campsiteTypeSvc.addCampsiteType(campsiteTypeVO, campId);
//	    return new ApiResponse<>("success", campsiteType, "新增成功");
//	}
	
	// 新增營地房型(測試使用)
	@PostMapping("/addCampsiteType")
	public ApiResponse<CampsiteTypeVO> addCampsiteType(@RequestBody CampsiteTypeVO campsiteTypeVO) {
	    // 從 VO 中取出 campId
	    Integer campId = campsiteTypeVO.getId() != null ? campsiteTypeVO.getId().getCampId() : null;
	    CampsiteTypeVO campsiteType = campsiteTypeSvc.addCampsiteType(campsiteTypeVO, campId);
	    return new ApiResponse<>("success", campsiteType, "新增成功");
	}
	

	
	
	// 修改營地房型
	@PostMapping("/updateCampsiteType")
	public ApiResponse<CampsiteTypeVO> updateCampsiteType(@RequestBody @Valid CampsiteTypeVO campsiteTypeVO) {
		try {
	        CampsiteTypeVO updated = campsiteTypeSvc.updateCampsiteType(campsiteTypeVO);
	        return new ApiResponse<>("success", updated, "修改成功");
	    } catch (EntityNotFoundException e) {
	        return new ApiResponse<>("fail", null, e.getMessage());
	    }
	}
	

	// 刪除營地房型
	@PostMapping("/deleteCampsiteType")
	public ApiResponse<String> deleteCampsiteType(
	        @RequestParam Integer campId,
	        @RequestParam Integer campsiteTypeId) {
	    try {
	        CampsiteTypeVO.CompositeDetail id = new CampsiteTypeVO.CompositeDetail(campsiteTypeId, campId);
	        campsiteTypeSvc.deleteCampsiteType(id);
	        return new ApiResponse<>("success", null, "刪除成功");
	    } catch (EntityNotFoundException e) {
	        return new ApiResponse<>("fail", null, e.getMessage());
	    } catch (Exception e) {
	        return new ApiResponse<>("fail", null, "刪除失敗：" + e.getMessage());
	    }
	}
	
	
	
	//======================================Postman測試用登入=========================
	@PostMapping("/mockLogin")
	public ApiResponse<String> mockLogin(HttpSession session) {
	    session.setAttribute("campId", 1001); // 假裝是已登入營地主
	    return new ApiResponse<>("success", null, "登入成功，campId 設為 1001");
	}
}
