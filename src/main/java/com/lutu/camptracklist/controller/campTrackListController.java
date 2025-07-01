package com.lutu.camptracklist.controller;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.camp.model.CampService;
import com.lutu.campsitetype.model.CampsiteTypeDTO;
import com.lutu.campsitetype.model.CampsiteTypeVO;
import com.lutu.camptracklist.model.CampTrackListDTO;
import com.lutu.camptracklist.model.CampTrackListService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/camptracklist")
public class campTrackListController {

	@Autowired
	CampTrackListService campTrackListSvc;

	@Autowired
	CampService campSvc;  //來自camp_id的關聯
	
//	@Autowired
//	MemService memSvc; //來自mem_id的關聯


	// http://localhost:8081/CJA101G02/camptracklist/10000001/getCampTrackLists
	// http://localhost:8081/CJA101G02/camptracklist/{memId}/getCampTrackLists
	//查詢會員收藏的營地
	@GetMapping("/{memId}/getCampTrackLists")
	public ApiResponse<List<CampTrackListDTO>> getCampFavoritesByMember(@PathVariable Integer memId) {
        List<CampTrackListDTO> result = campTrackListSvc.getCampTracksByMemberId(memId);
        return new ApiResponse<>("success", result, "查詢成功");
    }

}