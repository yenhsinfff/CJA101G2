package com.lutu.campsitetype.controller;

import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.campsite.model.CampsiteDTO;
import com.lutu.campsite.model.CampsiteVO;
import com.lutu.campsitetype.model.CampsiteTypeDTO;
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
//	@GetMapping("/getCampsiteTypes")
//	public ApiResponse<List<CampsiteTypeVO>> getCampsiteTypeList(@PathVariable Integer campId) {
//	    List<CampsiteTypeVO> campsiteTypeList = campsiteTypeSvc.getByCampId(campId);
//	    return new ApiResponse<>("success", campsiteTypeList, "查詢成功");
//	}
	
	//http://localhost:8081/CJA101G02/campsitetype/1001/getCampsiteTypes
	//http://localhost:8081/CJA101G02/campsitetype/{campId}/getCampsiteTypes
	// 查詢特定營地的房型

	@GetMapping("/{campId}/getCampsiteTypes")
	public ApiResponse<List<CampsiteTypeDTO>> getCampsiteTypeList(@PathVariable Integer campId) {
	    List<CampsiteTypeVO> campsiteTypeList = campsiteTypeSvc.getByCampId(campId);

	    List<CampsiteTypeDTO> dtoList = campsiteTypeList.stream()
	        .map(vo -> new CampsiteTypeDTO(
	            vo.getId().getCampsiteTypeId(),
	            vo.getId().getCampId(),
	            vo.getCampsiteName(),
	            vo.getCampsitePeople(),
	            vo.getCampsiteNum(),
	            vo.getCampsitePrice(),
	            vo.getCampsitePic1() != null ? Base64.getEncoder().encodeToString(vo.getCampsitePic1()) : null,
	            vo.getCampsitePic2() != null ? Base64.getEncoder().encodeToString(vo.getCampsitePic2()) : null,
	            vo.getCampsitePic3() != null ? Base64.getEncoder().encodeToString(vo.getCampsitePic3()) : null,
	            vo.getCampsitePic4() != null ? Base64.getEncoder().encodeToString(vo.getCampsitePic4()) : null
	        ))
	        .collect(Collectors.toList());

	    return new ApiResponse<>("success", dtoList, "查詢成功");
	}

	
	
	// 新增營地房型(可自動抓取營地主ID，會員session設置後再開啟此版本)
//	@PostMapping("/addCampsiteType")
//	public ApiResponse<CampsiteTypeVO> addCampsiteType(@RequestBody CampsiteTypeVO campsiteTypeVO,
//	                                                   HttpSession session) {
//	    Integer campId = (Integer) session.getAttribute("campId"); // 或從 SecurityContext 拿
//	    CampsiteTypeVO campsiteType = campsiteTypeSvc.addCampsiteType(campsiteTypeVO, campId);
//	    return new ApiResponse<>("success", campsiteType, "新增成功");
//	}
	
	//http://localhost:8081/CJA101G02/campsitetype/addCampsiteType
	// 新增營地房型，campsiteTypeId可自動遞增(測試使用)
	@PostMapping("/addCampsiteType")
	public ApiResponse<CampsiteTypeVO> addCampsiteType(@RequestBody CampsiteTypeVO campsiteTypeVO) {
	    // 從 VO 中取出 campId
	    Integer campId = campsiteTypeVO.getId() != null ? campsiteTypeVO.getId().getCampId() : null;
	    CampsiteTypeVO campsiteType = campsiteTypeSvc.addCampsiteType(campsiteTypeVO, campId);
	    return new ApiResponse<>("success", campsiteType, "新增成功");
	}
	

	
	//http://localhost:8081/CJA101G02/campsitetype/updateCampsiteType
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
	
	//http://localhost:8081/CJA101G02/campsitetype/deleteCampsiteType
	// 刪除營地房型
	@PostMapping("/deleteCampsiteType")
	public ApiResponse<String> deleteCampsiteType(@RequestBody CampsiteTypeVO.CompositeDetail id) {
	    try {
	        campsiteTypeSvc.deleteCampsiteType(id);
	        return new ApiResponse<>("success", null, "刪除成功");
	    } catch (EntityNotFoundException e) {
	        return new ApiResponse<>("fail", null, e.getMessage());
	    } catch (Exception e) {
	        return new ApiResponse<>("fail", null, "刪除失敗：" + e.getMessage());
	    }
	}
	
	//http://localhost:8081/CJA101G02/campsitetype/2003/1002/getcampsites
	//http://localhost:8081/CJA101G02/campsitetype/{campsiteTypeId}/{campId}/getcampsites
	//取得特定營地房型下所有的房間
//	@GetMapping("/{campsiteTypeId}/{campId}/getcampsites")
//	public ApiResponse<Set<CampsiteVO>> getCampsitesByType(
//			 @PathVariable Integer campsiteTypeId,
//			@PathVariable Integer campId) {
//
//	    CampsiteTypeVO campsiteType = campsiteTypeSvc.getOneCampsiteType(campsiteTypeId, campId);
//	    if (campsiteType == null) {
//	        return new ApiResponse<>("fail", null, "查無此房型");
//	    }
//
//	    Set<CampsiteVO> campsiteList = campsiteType.getCampsites();
//	    return new ApiResponse<>("success", campsiteList, "查詢成功");
//	}
	
	//http://localhost:8081/CJA101G02/campsitetype/2003/1002/getcampsites
	//http://localhost:8081/CJA101G02/campsitetype/{campsiteTypeId}/{campId}/getcampsites
	//使用DTO避免序列化問題(只取得房間資料，不會關聯到房型資料)
	@GetMapping("/{campsiteTypeId}/{campId}/getcampsites")
	public ApiResponse<List<CampsiteDTO>> getCampsitesByType(
	        @PathVariable Integer campsiteTypeId,
	        @PathVariable Integer campId) {

	    CampsiteTypeVO campsiteType = campsiteTypeSvc.getOneCampsiteType(campsiteTypeId, campId);
	    if (campsiteType == null) {
	        return new ApiResponse<>("fail", null, "查無此房型");
	    }

	    Set<CampsiteVO> campsiteList = campsiteType.getCampsites();

	    List<CampsiteDTO> dtoList = campsiteList.stream()
	            .map(c -> new CampsiteDTO(
	                    c.getCampsiteId(),
	                    campId,
	                    campsiteTypeId,
	                    c.getCampsiteIdName(),
	                    c.getCamperName()
	            ))
	            .collect(Collectors.toList());

	    return new ApiResponse<>("success", dtoList, "查詢成功");
	}
	
	
	
	//======================================Postman測試用登入=========================
	@PostMapping("/mockLogin")
	public ApiResponse<String> mockLogin(HttpSession session) {
	    session.setAttribute("campId", 1001); // 假裝是已登入營地主
	    return new ApiResponse<>("success", null, "登入成功，campId 設為 1001");
	}
}
