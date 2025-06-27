package com.lutu.campsitetype.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lutu.campsitetype.model.CampsiteTypeVO.CompositeDetail;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service("campsiteTypeService")
public class CampsiteTypeService {

	@Autowired
	CampsiteTypeRepository repository;
	
	
	//查詢特定營地下的房型
	public List<CampsiteTypeVO> getByCampId(Integer campId) {
	    return repository.findByIdCampId(campId);
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
