package com.lutu.camptracklist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.ApiResponse;
import com.lutu.camp.model.CampService;
import com.lutu.camptracklist.model.CampTrackListDTO;
import com.lutu.camptracklist.model.CampTrackListDTO_insert;
import com.lutu.camptracklist.model.CampTrackListService;
import com.lutu.camptracklist.model.CampTrackListVO;

import jakarta.persistence.EntityNotFoundException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/camptracklist")
public class CampTrackListController {

	@Autowired
	CampTrackListService campTrackListSvc;

	@Autowired
	CampService campSvc; // 來自camp_id的關聯

//	@Autowired
//	MemService memSvc; //來自mem_id的關聯

	// http://localhost:8081/CJA101G02/camptracklist/10000001/getCampTrackLists
	// http://localhost:8081/CJA101G02/camptracklist/{memId}/getCampTrackLists
	// 查詢會員收藏的營地
	@GetMapping("/{memId}/getCampTrackLists")
	public ApiResponse<List<CampTrackListDTO>> getCampFavoritesByMember(@PathVariable Integer memId) {
		List<CampTrackListDTO> result = campTrackListSvc.getCampTracksByMemberId(memId);
		return new ApiResponse<>("success", result, "查詢成功");
	}
	

	// http://localhost:8081/CJA101G02/camptracklist/deleteCampTrackList
	// 刪除收藏
	@PostMapping("/deleteCampTrackList")
	public ApiResponse<String> deleteCampTrackList(@RequestBody CampTrackListVO.CompositeDetail id) {
		try {
			campTrackListSvc.deleteCampTrackList(id);
			return new ApiResponse<>("success", null, "刪除成功");
		} catch (EntityNotFoundException e) {
			return new ApiResponse<>("fail", null, e.getMessage());
		} catch (Exception e) {
			return new ApiResponse<>("fail", null, "刪除失敗：" + e.getMessage());
		}
	}


	// http://localhost:8081/CJA101G02/camptracklist/addCampTrackList
	// 新增收藏
	@PostMapping("/addCampTrackList")
	public ApiResponse<String> addCampTrackList(@RequestBody CampTrackListDTO_insert dto) {
		try {
			campTrackListSvc.addCampTrackList(dto);
			return new ApiResponse<>("success", null, "新增收藏成功");
		} catch (Exception e) {
			return new ApiResponse<>("fail", null, "新增收藏失敗：" + e.getMessage());
		}
	}

}