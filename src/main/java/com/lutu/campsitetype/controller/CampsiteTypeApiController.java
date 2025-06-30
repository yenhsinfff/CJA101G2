package com.lutu.campsitetype.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lutu.ApiResponse;
import com.lutu.campsite.model.CampsiteDTO;
import com.lutu.campsite.model.CampsiteVO;
import com.lutu.campsitetype.model.CampsiteTypeDTO;
import com.lutu.campsitetype.model.CampsiteTypeDTO_Info;
import com.lutu.campsitetype.model.CampsiteTypeDTO_insert;
import com.lutu.campsitetype.model.CampsiteTypeService;
import com.lutu.campsitetype.model.CampsiteTypeVO;
import com.lutu.campsitetype.model.CampsiteTypeVO.CompositeDetail;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
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

	// http://localhost:8081/CJA101G02/campsitetype/1001/getCampsiteTypes
	// http://localhost:8081/CJA101G02/campsitetype/{campId}/getCampsiteTypes
	// 查詢特定營地的房型

	@GetMapping("/{campId}/getCampsiteTypes")
	public ApiResponse<List<CampsiteTypeDTO>> getCampsiteTypeList(@PathVariable Integer campId) {
		List<CampsiteTypeVO> campsiteTypeList = campsiteTypeSvc.getByCampId(campId);

		List<CampsiteTypeDTO> dtoList = campsiteTypeList.stream()
				.map(vo -> new CampsiteTypeDTO(vo.getId().getCampsiteTypeId(), vo.getId().getCampId(),
						vo.getCampsiteName(), vo.getCampsitePeople(), vo.getCampsiteNum(), vo.getCampsitePrice(),
						vo.getCampsitePic1() != null ? Base64.getEncoder().encodeToString(vo.getCampsitePic1()) : null,
						vo.getCampsitePic2() != null ? Base64.getEncoder().encodeToString(vo.getCampsitePic2()) : null,
						vo.getCampsitePic3() != null ? Base64.getEncoder().encodeToString(vo.getCampsitePic3()) : null,
						vo.getCampsitePic4() != null ? Base64.getEncoder().encodeToString(vo.getCampsitePic4()) : null))
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


	// http://localhost:8081/CJA101G02/campsitetype/1001/addCampsiteType
	// http://localhost:8081/CJA101G02/campsitetype/{campId}/addCampsiteType
	// 新增營地房型，campsiteTypeId可自動遞增
	@PostMapping("/{campId}/addCampsiteType")
	public ResponseEntity<?> addCampsiteType(
            @PathVariable Integer campId,
            @Valid @ModelAttribute CampsiteTypeDTO_insert dto,
            BindingResult result) {
        
        if (result.hasErrors()) {
            // 傳回第一筆錯誤訊息（也可以全部合併傳回）
            String errorMessage = result.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body("驗證錯誤：" + errorMessage);
        }
        
        if (dto.getCampsitePic1() == null || dto.getCampsitePic1().isEmpty()) {
            return ResponseEntity.badRequest().body("房間照片: 至少上傳1張");
        }

        try {
            // 將 DTO 資料轉換成 VO
            CampsiteTypeVO vo = new CampsiteTypeVO();
            vo.setCampsiteName(dto.getCampsiteName());
            vo.setCampsitePeople(dto.getCampsitePeople());
            vo.setCampsiteNum(dto.getCampsiteNum());
            vo.setCampsitePrice(dto.getCampsitePrice());

            vo.setCampsitePic1(dto.getCampsitePic1().getBytes());
            vo.setCampsitePic2(getBytesOrNull(dto.getCampsitePic2()));
            vo.setCampsitePic3(getBytesOrNull(dto.getCampsitePic3()));
            vo.setCampsitePic4(getBytesOrNull(dto.getCampsitePic4()));

            CampsiteTypeVO saved = campsiteTypeSvc.addCampsiteType(vo, campId);

            // 你也可以轉成 DTO 回傳
            return ResponseEntity.ok(saved);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("圖片轉換失敗：" + e.getMessage());
        }
    }

    private byte[] getBytesOrNull(MultipartFile file) throws IOException {
        return (file != null && !file.isEmpty()) ? file.getBytes() : null;
    }



	// http://localhost:8081/CJA101G02/campsitetype/deleteCampsiteType
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

	// http://localhost:8081/CJA101G02/campsitetype/2003/1002/getcampsites
	// http://localhost:8081/CJA101G02/campsitetype/{campsiteTypeId}/{campId}/getcampsites
	// 取得房型的房間列表，使用DTO避免序列化問題(只取得房間資料，不會關聯到房型資料)
	@GetMapping("/{campsiteTypeId}/{campId}/getcampsites")
	public ApiResponse<List<CampsiteDTO>> getCampsitesByType(@PathVariable Integer campsiteTypeId,
			@PathVariable Integer campId) {

		CampsiteTypeVO campsiteType = campsiteTypeSvc.getOneCampsiteType(campsiteTypeId, campId);
		if (campsiteType == null) {
			return new ApiResponse<>("fail", null, "查無此房型");
		}

		Set<CampsiteVO> campsiteList = campsiteType.getCampsites();

		List<CampsiteDTO> dtoList = campsiteList.stream().map(c -> new CampsiteDTO(c.getCampsiteId(), campId,
				campsiteTypeId, c.getCampsiteIdName(), c.getCamperName())).collect(Collectors.toList());

		return new ApiResponse<>("success", dtoList, "查詢成功");
	}

	// 更新房型(圖片以外)，前端先呼叫
	// http://localhost:8081/CJA101G02/campsitetype/updateCampsiteType
	@PostMapping("/updateCampsiteType")
	public ResponseEntity<ApiResponse<CampsiteTypeDTO_Info>> updateCampsiteTypeInfo(
			@RequestBody @Valid CampsiteTypeDTO_Info dto) {
		try {
			CampsiteTypeVO.CompositeDetail id = new CampsiteTypeVO.CompositeDetail(dto.getCampsiteTypeId(),
					dto.getCampId());
			CampsiteTypeVO existing = campsiteTypeSvc.getById(id);
			if (existing == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("fail", null, "找不到指定的房型"));
			}

			// 更新基本欄位
			existing.setCampsiteName(dto.getCampsiteName());
			existing.setCampsitePeople(dto.getCampsitePeople());
			existing.setCampsiteNum(dto.getCampsiteNum());
			existing.setCampsitePrice(dto.getCampsitePrice());

			CampsiteTypeVO updated = campsiteTypeSvc.updateCampsiteType(existing);

			CampsiteTypeDTO_Info responseDTO = new CampsiteTypeDTO_Info(updated.getId().getCampsiteTypeId(),
					updated.getId().getCampId(), updated.getCampsiteName(), updated.getCampsitePeople(),
					updated.getCampsiteNum(), updated.getCampsitePrice());

			return ResponseEntity.ok(new ApiResponse<>("success", responseDTO, "資料更新成功"));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>("fail", null, "資料更新失敗：" + e.getMessage()));
		}
	}

//===============================圖片處理=================================================
	// http://localhost:8081/CJA101G02/campsitetype/2025/1001/update-images
	// http://localhost:8081/CJA101G02/campsitetype/{campsiteTypeId}/{campId}/update-images
	// 更新房型(圖片以外)，前端後呼叫
	// 接收上傳的房型照片
	@PostMapping("/{campsiteTypeId}/{campId}/update-images")
	public ResponseEntity<ApiResponse<String>> updatePics(@PathVariable Integer campsiteTypeId,
			@PathVariable Integer campId, @RequestParam(value = "pic1", required = false) MultipartFile pic1,
			@RequestParam(value = "pic2", required = false) MultipartFile pic2,
			@RequestParam(value = "pic3", required = false) MultipartFile pic3,
			@RequestParam(value = "pic4", required = false) MultipartFile pic4) {
		CompositeDetail id = new CompositeDetail(campsiteTypeId, campId);
		Boolean success = campsiteTypeSvc.updatePics(id, pic1, pic2, pic3, pic4);

		if (success) {
			return ResponseEntity.ok(new ApiResponse<>("success", null, "圖片更新成功"));
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>("fail", null, "圖片更新失敗"));
		}
	}

	// 取得房型照片，根據index回傳第幾張圖片
	// http://localhost:8081/CJA101G02/campsitetype/2025/1001/images/1
	// http://localhost:8081/CJA101G02/campsitetype/{campsiteTypeId}/{campId}/images/{index}
	@GetMapping("/{campsiteTypeId}/{campId}/images/{index}")
	public void getCampsiteTypePic(@PathVariable Integer campsiteTypeId, @PathVariable Integer campId,
			@PathVariable Integer index, HttpServletResponse response) throws IOException {

		CampsiteTypeVO campsiteType = campsiteTypeSvc.getOneCampsiteType(campsiteTypeId, campId);
		byte[] imageData = null;

		switch (index) {
		case 1 -> imageData = campsiteType.getCampsitePic1();
		case 2 -> imageData = campsiteType.getCampsitePic2();
		case 3 -> imageData = campsiteType.getCampsitePic3();
		case 4 -> imageData = campsiteType.getCampsitePic4();
		default -> {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		}

		if (imageData != null && imageData.length > 0) {
			try (InputStream is = new ByteArrayInputStream(imageData)) {
				String mimeType = URLConnection.guessContentTypeFromStream(is);
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				response.getOutputStream().write(imageData);
			}
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	// ======================================Postman測試用登入=========================
	@PostMapping("/mockLogin")
	public ApiResponse<String> mockLogin(HttpSession session) {
		session.setAttribute("campId", 1001); // 假裝是已登入營地主
		return new ApiResponse<>("success", null, "登入成功，campId 設為 1001");
	}
}
