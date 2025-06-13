package com.lutu.administrator.model;

import java.util.List;

public class AdministratorService {

	private AdministratorDAO_interface dao;

	public AdministratorService() {
		dao = new AdministratorJDBCDAO();
	}
	
	public AdministratorVO addAdministrator(String adminAcc, String adminPwd, 
			String adminPwdHash, byte adminStatus, String adminName) {
		
		AdministratorVO administratorVO = new AdministratorVO();
		
		administratorVO.setAdminAcc(adminAcc);
		administratorVO.setAdminPwd(adminPwd);
		administratorVO.setAdminPwdHash(adminPwdHash);
		administratorVO.setAdminStatus(adminStatus);
		administratorVO.setAdminName(adminName);
		dao.insert(administratorVO);
		
		return administratorVO;
	}
	
	public AdministratorVO updateAdministrator(Integer adminId, String adminAcc, String adminPwd, 
			String adminPwdHash, byte adminStatus, String adminName) {
		
		AdministratorVO administratorVO = new AdministratorVO();
		
		administratorVO.setAdminId(adminId);
		administratorVO.setAdminAcc(adminAcc);
		administratorVO.setAdminPwd(adminPwd);
		administratorVO.setAdminPwdHash(adminPwdHash);
		administratorVO.setAdminStatus(adminStatus);
		administratorVO.setAdminName(adminName);
		dao.insert(administratorVO);
		
		return administratorVO;
	}
	
	public void deleteAdministrator(Integer adminId) {
		dao.delete(adminId);
	}
	
	public AdministratorVO getOneAdministrator(Integer adminId) {
		return dao.findByPrimaryKey(adminId);
	}
	
	public List<AdministratorVO> getAll(){
		return dao.getAll();
	}
	
}
