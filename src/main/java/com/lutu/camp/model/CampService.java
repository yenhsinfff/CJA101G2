package com.lutu.camp.model;

import java.sql.Date;
import java.util.List;

public class CampService {
    private CampDAO_interface dao;

    public CampService() {
        dao = new CampDAO();
    }

    
    public CampVO addCamp(CampVO campVO) {
        dao.insert(campVO);
        return campVO;
    }
    
    public CampVO updateCamp(CampVO campVO) {
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
