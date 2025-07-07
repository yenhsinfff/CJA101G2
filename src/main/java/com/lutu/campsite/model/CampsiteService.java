package com.lutu.campsite.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lutu.campsitetype.model.CampsiteTypeRepository;
import com.lutu.campsitetype.model.CampsiteTypeService;
import com.lutu.campsitetype.model.CampsiteTypeVO;

import jakarta.persistence.EntityNotFoundException;

@Service("campsiteService")
public class CampsiteService {

	@Autowired
	private CampsiteTypeRepository campsiteTypeRepo;

	@Autowired
	private CampsiteRepository campsiteRepo;

	@Autowired
	private CampsiteTypeService campsiteTypeService;

//	public CampsiteVO addCampsite(CampsiteVO campsiteVO) {
//	    return campsiteRepo.save(campsiteVO);
//	}

	public CampsiteVO updateCampsite(CampsiteVO campsiteVO) {
		if (campsiteVO.getCampsiteId() == null) {
			throw new IllegalArgumentException("房間 ID 不可為 null");
		}

		CampsiteVO existing = campsiteRepo.findById(campsiteVO.getCampsiteId())
				.orElseThrow(() -> new EntityNotFoundException("查無房間 ID：" + campsiteVO.getCampsiteId()));

		existing.setCampsiteIdName(campsiteVO.getCampsiteIdName());
		existing.setCamperName(campsiteVO.getCamperName());
		existing.setCampsiteType(campsiteVO.getCampsiteType());

		return campsiteRepo.save(existing);
	}

	public void deleteCampsite(Integer campsiteId) {
		if (!campsiteRepo.existsById(campsiteId)) {
			throw new EntityNotFoundException("找不到要刪除的房間");
		}
		campsiteRepo.deleteById(campsiteId);
	}

	public CampsiteVO getOneCampsite(Integer campsiteId) {
		Optional<CampsiteVO> optional = campsiteRepo.findById(campsiteId);
		return optional.orElse(null); // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值

	}

	public List<CampsiteDTO> getCampsiteDTOsByCampId(Integer campId) {
		List<CampsiteVO> voList = campsiteRepo.findByCampId(campId);
		System.out.println("getCampsiteDTOsByCampId");
		return voList.stream().map(CampsiteService::toDTO).collect(Collectors.toList());
	}

	public List<CampsiteVO> getAll() {
		return campsiteRepo.findAll();

	}

	public CampsiteVO addCampsite(CampsiteVO campsiteVO) {
		CampsiteTypeVO.CompositeDetail typeId = campsiteVO.getCampsiteType().getId();

		// 驗證房型是否存在
		boolean exists = campsiteTypeRepo.existsById(typeId);
		if (!exists) {
			throw new EntityNotFoundException("房型不存在，無法新增房間");
		}

		// 不查詢房型完整內容，不需要放入關聯物件（或只設主鍵）
		campsiteVO.setCampsiteType(new CampsiteTypeVO()); // 避免關聯序列化出現全部空值
		campsiteVO.getCampsiteType().setId(typeId);

		return campsiteRepo.save(campsiteVO);
	}

	public static CampsiteDTO toDTO(CampsiteVO vo) {
		System.out.println("getCampsiteDTOsByCampId_toDTO:"+vo.getCampsiteType().getCampsitePeople());
		return new CampsiteDTO(vo.getCampsiteType().getCampsitePeople(), vo.getCampsiteId(), vo.getCampId(),
				vo.getCampsiteTypeId(), vo.getCampsiteIdName(), vo.getCamperName());
	}

}
