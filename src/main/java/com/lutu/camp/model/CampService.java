package com.lutu.camp.model;

import java.sql.Date;
import java.util.List;

public class CampService {
    private CampDAO_interface dao;

    public CampService() {
        dao = new CampDAO();
    }

//    public CampVO addCamp(int ownerId, String campName, String campContent, String campCity, String campDist,
//            String campAddr, byte campReleaseStatus, byte[] campPic1, byte[] campPic2, byte[] campPic3, byte[] campPic4,
//            int campCommentNumberCount, int campCommentSumScore, Date campRegDate) {
//
//        CampVO campVO = new CampVO();
//        campVO.setOwnerId(ownerId);
//        campVO.setCampName(campName);
//        campVO.setCampContent(campContent);
//        campVO.setCampCity(campCity);
//        campVO.setCampDist(campDist);
//        campVO.setCampAddr(campAddr);
//        campVO.setCampReleaseStatus(campReleaseStatus);
//        campVO.setCampPic1(campPic1);
//        campVO.setCampPic2(campPic2);
//        campVO.setCampPic3(campPic3);
//        campVO.setCampPic4(campPic4);
//        campVO.setCampCommentNumberCount(campCommentNumberCount);
//        campVO.setCampCommentSumScore(campCommentSumScore);
//        campVO.setCampRegDate(campRegDate);
//        dao.insert(campVO);
//        return campVO;
//        // dao.insert(campDAO);
//    }
    
    public CampVO addCamp(CampVO campVO) {

//        campVO.setOwnerId(campVO.getOwnerId());
//        campVO.setCampName(campVO.getCampName());
//        campVO.setCampContent(campVO.getCampContent());
//        campVO.setCampCity(campVO.getCampCity());
//        campVO.setCampDist(campVO.getCampDist());
//        campVO.setCampAddr(campVO.getCampAddr());
//        campVO.setCampReleaseStatus(campVO.getCampReleaseStatus());
//        campVO.setCampPic1(campVO.getCampPic1());
//        campVO.setCampPic2(campVO.getCampPic2());
//        campVO.setCampPic3(campVO.getCampPic3());
//        campVO.setCampPic4(campVO.getCampPic4());
//        campVO.setCampCommentNumberCount(campVO.getCampCommentNumberCount());
//        campVO.setCampCommentSumScore(campVO.getCampCommentSumScore());
//        campVO.setCampRegDate(campVO.getCampRegDate());
        dao.insert(campVO);
        return campVO;
    }
    
    public CampVO updateCamp(CampVO campVO) {

        campVO.setOwnerId(campVO.getOwnerId());
        campVO.setCampName(campVO.getCampName());
        campVO.setCampContent(campVO.getCampContent());
        campVO.setCampCity(campVO.getCampCity());
        campVO.setCampDist(campVO.getCampDist());
        campVO.setCampAddr(campVO.getCampAddr());
        campVO.setCampReleaseStatus(campVO.getCampReleaseStatus());
        campVO.setCampPic1(campVO.getCampPic1());
        campVO.setCampPic2(campVO.getCampPic2());
        campVO.setCampPic3(campVO.getCampPic3());
        campVO.setCampPic4(campVO.getCampPic4());
        campVO.setCampCommentNumberCount(campVO.getCampCommentNumberCount());
        campVO.setCampCommentSumScore(campVO.getCampCommentSumScore());
        campVO.setCampRegDate(campVO.getCampRegDate());
        dao.update(campVO);
        return campVO;
    }
    
    public void deleteCamp(Integer campId) {
        dao.delete(campId);
    }
    
    public CampVO getOneCamp(Integer campId) {
        return dao.getOneCamp(campId);
    }

    public List<CampVO> getAll() {
        return dao.getAll();
    }
}
