package com.lutu.campsite.controller;

import java.util.List;
import java.util.Map;
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
import com.lutu.campsite.model.CampsiteService;
import com.lutu.campsite.model.CampsiteVO;
import com.lutu.campsitetype.model.CampsiteTypeService;
import com.lutu.campsitetype.model.CampsiteTypeVO;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/campsite")
public class CampsiteController {

	@Autowired
	CampsiteService campsiteSvc;

	@Autowired
	CampsiteTypeService campsiteTypeSvc;

	// 取得所有營地房間
	@GetMapping("/getAllCampsite")
	public ApiResponse<List<CampsiteDTO>> getAllCampsite() {
		List<CampsiteVO> campsiteList = campsiteSvc.getAll();
		List<CampsiteDTO> dtoList = campsiteList.stream()
				.map(vo -> new CampsiteDTO(vo.getCampsiteType().getCampsitePeople(), vo.getCampsiteId(), vo.getCampId(),
						vo.getCampsiteTypeId(), vo.getCampsiteIdName(), vo.getCamperName()

				)).collect(Collectors.toList());
		return new ApiResponse<>("success", dtoList, "查詢成功");
	}

	@GetMapping("/{campId}/getCampsiteByCampId")
	public ApiResponse<List<CampsiteDTO>> getCampsiteByCampId(@PathVariable Integer campId) {
		List<CampsiteDTO> dtoList = campsiteSvc.getCampsiteDTOsByCampId(campId);
		return new ApiResponse<>("success", dtoList, "查詢成功");
	}

//	@GetMapping("/getAllCampsite")
//	public ApiResponse<List<CampsiteVO>> getAllCampsite() {
//	    List<CampsiteVO> campsiteList = campsiteSvc.getAll();
//	    return new ApiResponse<>("success", campsiteList, "查詢成功");
//	}

	// http://localhost:8081/CJA101G02/campsite/addCampsite
	// 新增營地房間，關聯出現campsiteType資料，改傳至DTO
	@PostMapping("/addCampsite")
	public ApiResponse<CampsiteDTO> addCampsite(@RequestBody @Valid CampsiteVO campsiteVO) {
		try {
			// 直接從 campsiteVO 取得 campId 和 campsiteTypeId
			Integer campId = campsiteVO.getCampId();
			Integer campsiteTypeId = campsiteVO.getCampsiteTypeId();

			// 查詢房型
			CampsiteTypeVO campsiteType = campsiteTypeSvc.getOneCampsiteType(campsiteTypeId, campId);
			if (campsiteType == null) {
				return new ApiResponse<>("fail", null, "新增失敗：找不到指定的房型");
			}

			// 設定房型關聯（建立關聯）
			campsiteVO.setCampsiteType(campsiteType);

			CampsiteVO saved = campsiteSvc.addCampsite(campsiteVO);

			CampsiteDTO dto = new CampsiteDTO(saved.getCampsiteType().getCampsitePeople(), saved.getCampsiteId(),
					saved.getCampId(), saved.getCampsiteTypeId(), saved.getCampsiteIdName(), saved.getCamperName());

			return new ApiResponse<>("success", dto, "新增成功");
		} catch (EntityNotFoundException e) {
			return new ApiResponse<>("fail", null, "新增失敗：" + e.getMessage());
		} catch (Exception e) {
			return new ApiResponse<>("fail", null, "新增失敗：" + e.getMessage());
		}
	}

	// 修改房間資料
	@PostMapping("/updateCampsite")
	public ApiResponse<CampsiteDTO> updateCampsite(@RequestBody @Valid CampsiteVO campsiteVO) {
		try {
			// 先取得 campId / campsiteTypeId，然後查出完整的 CampsiteTypeVO
			Integer campsiteTypeId = campsiteVO.getCampsiteType().getId().getCampsiteTypeId();
			Integer campId = campsiteVO.getCampsiteType().getId().getCampId();

			CampsiteTypeVO campsiteType = campsiteTypeSvc.getOneCampsiteType(campsiteTypeId, campId);
			if (campsiteType == null) {
				return new ApiResponse<>("fail", null, "修改失敗：找不到房型");
			}

			campsiteVO.setCampsiteType(campsiteType); // 設回正確關聯

			CampsiteVO updated = campsiteSvc.updateCampsite(campsiteVO);

			CampsiteDTO dto = new CampsiteDTO(updated.getCampsiteType().getCampsitePeople(), updated.getCampsiteId(),
					campsiteType.getId().getCampId(), campsiteType.getId().getCampsiteTypeId(),
					updated.getCampsiteIdName(), updated.getCamperName());

			return new ApiResponse<>("success", dto, "修改成功");

		} catch (EntityNotFoundException e) {
			return new ApiResponse<>("fail", null, "修改失敗：" + e.getMessage());
		} catch (Exception e) {
			return new ApiResponse<>("fail", null, "修改失敗：" + e.getMessage());
		}
	}

	// 刪除房間資料
	// http://localhost:8081/CJA101G02/campsite/deleteCampsite
	@PostMapping("/deleteCampsite")
	public ApiResponse<String> deleteCampsite(@RequestBody Map<String, Integer> payload) {
		try {
			Integer campsiteId = payload.get("campsiteId");
			campsiteSvc.deleteCampsite(campsiteId);
			return new ApiResponse<>("success", null, "刪除成功");
		} catch (EntityNotFoundException e) {
			return new ApiResponse<>("fail", null, e.getMessage());
		} catch (Exception e) {
			return new ApiResponse<>("fail", null, "刪除失敗：" + e.getMessage());
		}
	}

}
