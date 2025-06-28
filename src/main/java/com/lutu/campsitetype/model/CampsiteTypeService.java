package com.lutu.campsitetype.model;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.lutu.campsitetype.model.CampsiteTypeVO.CompositeDetail;
import com.lutu.member.model.MemberVO;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service("campsiteTypeService")
public class CampsiteTypeService {

	@Autowired
	CampsiteTypeRepository repository;

	// 查詢特定營地下的房型
	public List<CampsiteTypeVO> getByCampId(Integer campId) {
		return repository.findByIdCampId(campId);
	}
	
	//複合主鍵查詢
	 public CampsiteTypeVO getById(CampsiteTypeVO.CompositeDetail id) {
	        return repository.findById(id)
	                .orElse(null); // 找不到就回傳 null
	    }


	public CampsiteTypeVO addCampsiteType(CampsiteTypeVO campsiteTypeVO, Integer campId) {
		// 查詢所有營地目前的最大房型 ID
		Integer maxCampsiteTypeId = repository.findAllMaxCampsiteTypeId();
		int nextCampsiteTypeId = (maxCampsiteTypeId == null) ? 1 : maxCampsiteTypeId + 1;

		// 設定主鍵（campsiteTypeId 為全域唯一，campId 為傳入值）
		CampsiteTypeVO.CompositeDetail id = new CampsiteTypeVO.CompositeDetail(nextCampsiteTypeId, campId);
		campsiteTypeVO.setId(id);

		return repository.save(campsiteTypeVO);
	}

	public CampsiteTypeVO updateCampsiteType(CampsiteTypeVO campsiteTypeVO) {
		CampsiteTypeVO.CompositeDetail id = campsiteTypeVO.getId();

		if (!repository.existsById(id)) {
			throw new EntityNotFoundException("找不到要修改的營地房型");
		}

		return repository.save(campsiteTypeVO);
	}

	public void deleteCampsiteType(CampsiteTypeVO.CompositeDetail id) {
		if (!repository.existsById(id)) {
			throw new EntityNotFoundException("找不到要刪除的房型");
		}
		repository.deleteById(id);
	}

	@Transactional
	public CampsiteTypeVO getOneCampsiteType(Integer campsiteTypeId, Integer campId) {
		CompositeDetail id = new CompositeDetail();
		id.setCampId(campId);
		id.setCampsiteTypeId(campsiteTypeId);
		CampsiteTypeVO vo = repository.findById(id).orElse(null);

		if (vo != null)
			vo.getCampsites().size();
		return vo;

	}

	public List<CampsiteTypeVO> getAll() {
		return repository.findAll();
	}

	
	//圖片上傳至資料庫
//	@Transactional
//	public void saveImages(Integer campsiteTypeId, Integer campId, MultipartFile pic1, MultipartFile pic2,
//	        MultipartFile pic3, MultipartFile pic4) throws IOException {
//
//	    CompositeDetail id = new CompositeDetail();
//	    id.setCampsiteTypeId(campsiteTypeId);
//	    id.setCampId(campId);
//
//	    boolean exists = repository.existsById(id);
//	    System.out.println("是否找到房型資料？ " + exists);
//	    if (!exists) {
//	        throw new RuntimeException("找不到指定房型：" + id);
//	    }
//
//	    CampsiteTypeVO entity = repository.findById(id).orElseThrow(() -> new RuntimeException("找不到指定房型：" + id));
//
//	    entity.setCampsitePic1(pic1.getBytes());
//	    entity.setCampsitePic2(pic2 != null && !pic2.isEmpty() ? pic2.getBytes() : null);
//	    entity.setCampsitePic3(pic3 != null && !pic3.isEmpty() ? pic3.getBytes() : null);
//	    entity.setCampsitePic4(pic4 != null && !pic4.isEmpty() ? pic4.getBytes() : null);
//
//	    repository.save(entity);
//	}
	
	@Transactional
	public Boolean updatePics(CompositeDetail id, 
			MultipartFile pic1, MultipartFile pic2, MultipartFile pic3, MultipartFile pic4) {
	    CampsiteTypeVO campsiteType = repository.findById(id)
	        .orElseThrow(() -> new RuntimeException("房型不存在"));
	    
	    // 使用者可能只想更新某一張圖片，沒傳的就保留原圖
	    try {
	        if (pic1 != null && !pic1.isEmpty()) {
	            campsiteType.setCampsitePic1(pic1.getBytes());
	        }
	        if (pic2 != null && !pic2.isEmpty()) {
	            campsiteType.setCampsitePic2(pic2.getBytes());
	        }
	        if (pic3 != null && !pic3.isEmpty()) {
	            campsiteType.setCampsitePic3(pic3.getBytes());
	        }
	        if (pic4 != null && !pic4.isEmpty()) {
	            campsiteType.setCampsitePic4(pic4.getBytes());
	        }
			repository.save(campsiteType);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	    
	}
	
	


//	//自動產生主鍵(會員session設置完成後開啟)
//	public CampsiteTypeVO addCampsiteTypeAutoId(CampsiteTypeVO campsiteTypeVO, Integer campId) {
//	    // 查出該營地最大編號
//	    Integer maxId = repository.findMaxTypeIdByCampId(campId);
//	    int nextId = (maxId == null) ? 1 : maxId + 1;
//
//	    // 組合主鍵
//	    CampsiteTypeVO.CompositeDetail id = new CampsiteTypeVO.CompositeDetail(nextId, campId);
//	    campsiteTypeVO.setId(id);
//
//	    return repository.save(campsiteTypeVO);
//	}

//	private CampsiteTypeDAO_interface dao;
//	
//	
//
//	public CampsiteTypeService() {
//		dao = new CampsiteTypeDAO();
//	}
//
//	public CampsiteTypeVO addCampsiteType(Integer campId, String campsiteName,
//			Integer campsitePeople, Byte campsiteNum, Integer campsitePrice, 
//			byte[] campsitePic1, byte[] campsitePic2, byte[] campsitePic3, byte[] campsitePic4) {
//
//		CampsiteTypeVO campsiteTypeVO = new CampsiteTypeVO();
//
//		campsiteTypeVO.setCampId(campId);
//		campsiteTypeVO.setCampsiteName(campsiteName);
//		campsiteTypeVO.setCampsitePeople(campsitePeople);
//		campsiteTypeVO.setCampsiteNum(campsiteNum);
//		campsiteTypeVO.setCampsitePrice(campsitePrice);
//		campsiteTypeVO.setCampsitePic1(campsitePic1);
//		campsiteTypeVO.setCampsitePic2(campsitePic2);
//		campsiteTypeVO.setCampsitePic3(campsitePic3);
//		campsiteTypeVO.setCampsitePic4(campsitePic4);
//		dao.insert(campsiteTypeVO);
//
//		return campsiteTypeVO;
//	}
//
//	public CampsiteTypeVO updateCampsiteType(Integer campsiteTypeId, Integer campId, String campsiteName,
//			Integer campsitePeople, Byte campsiteNum, Integer campsitePrice, 
//			byte[] campsitePic1, byte[] campsitePic2, byte[] campsitePic3, byte[] campsitePic4) {
//
//
//		CampsiteTypeVO campsiteTypeVO = new CampsiteTypeVO();
//
//		campsiteTypeVO.setCampsiteTypeId(campsiteTypeId);
//		campsiteTypeVO.setCampId(campId);
//		campsiteTypeVO.setCampsiteName(campsiteName);
//		campsiteTypeVO.setCampsitePeople(campsitePeople);
//		campsiteTypeVO.setCampsiteNum(campsiteNum);
//		campsiteTypeVO.setCampsitePrice(campsitePrice);
//		campsiteTypeVO.setCampsitePic1(campsitePic1);
//		campsiteTypeVO.setCampsitePic2(campsitePic2);
//		campsiteTypeVO.setCampsitePic3(campsitePic3);
//		campsiteTypeVO.setCampsitePic4(campsitePic4);
//		dao.update(campsiteTypeVO);
//
//		return campsiteTypeVO;
//	}
//
//	public void deleteCampsiteType(Integer campsiteTypeId) {
//		dao.delete(campsiteTypeId);
//	}
//
//	public CampsiteTypeVO getOneCampsiteType(Integer campsiteTypeId) {
//		return dao.findByPK(campsiteTypeId);
//	}
//
//	public List<CampsiteTypeVO> getAll() {
//		return dao.getAll();
//	}
}
