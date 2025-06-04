package com.lutu.campsitetype.model;

import java.util.List;

public class CampsiteTypeService {

	private CampsiteTypeDAO_interface dao;

	public CampsiteTypeService() {
		dao = new CampsiteTypeDAO();
	}

	public CampsiteTypeVO addCampsiteType(Integer campsiteTypeId, Integer campId, String campsiteName,
			Integer campsitePeople, Byte campsiteNum, Integer campsitePrice, 
			byte[] campsitePic1, byte[] campsitePic2, byte[] campsitePic3, byte[] campsitePic4) {

		CampsiteTypeVO campsiteTypeVO = new CampsiteTypeVO();

		campsiteTypeVO.setCampsiteTypeId(campsiteTypeId);
		campsiteTypeVO.setCampId(campId);
		campsiteTypeVO.setCampsiteName(campsiteName);
		campsiteTypeVO.setCampsitePeople(campsitePeople);
		campsiteTypeVO.setCampsiteNum(campsiteNum);
		campsiteTypeVO.setCampsitePrice(campsitePrice);
		campsiteTypeVO.setCampsitePic1(campsitePic1);
		campsiteTypeVO.setCampsitePic2(campsitePic2);
		campsiteTypeVO.setCampsitePic3(campsitePic3);
		campsiteTypeVO.setCampsitePic4(campsitePic4);
		dao.insert(campsiteTypeVO);

		return campsiteTypeVO;
	}

	public CampsiteTypeVO updateCampsiteType(Integer campsiteTypeId, Integer campId, String campsiteName,
			Integer campsitePeople, Byte campsiteNum, Integer campsitePrice, 
			byte[] campsitePic1, byte[] campsitePic2, byte[] campsitePic3, byte[] campsitePic4) {


		CampsiteTypeVO campsiteTypeVO = new CampsiteTypeVO();

		campsiteTypeVO.setCampsiteTypeId(campsiteTypeId);
		campsiteTypeVO.setCampId(campId);
		campsiteTypeVO.setCampsiteName(campsiteName);
		campsiteTypeVO.setCampsitePeople(campsitePeople);
		campsiteTypeVO.setCampsiteNum(campsiteNum);
		campsiteTypeVO.setCampsitePrice(campsitePrice);
		campsiteTypeVO.setCampsitePic1(campsitePic1);
		campsiteTypeVO.setCampsitePic2(campsitePic2);
		campsiteTypeVO.setCampsitePic3(campsitePic3);
		campsiteTypeVO.setCampsitePic4(campsitePic4);
		dao.update(campsiteTypeVO);

		return campsiteTypeVO;
	}

	public void deleteCampsiteType(Integer campsiteTypeId) {
		dao.delete(campsiteTypeId);
	}

	public CampsiteTypeVO getOneCampsiteType(Integer campsiteTypeId) {
		return dao.findByPK(campsiteTypeId);
	}

	public List<CampsiteTypeVO> getAll() {
		return dao.getAll();
	}
}
