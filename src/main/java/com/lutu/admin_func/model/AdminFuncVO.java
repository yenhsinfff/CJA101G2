package com.lutu.admin_func.model;

import java.io.Serializable;

public class AdminFuncVO implements Serializable{
	
	private Integer adminId; //管理員編號
	private Integer funcId; //權限編號
	
	public AdminFuncVO() {
		super();
	}

	public AdminFuncVO(Integer adminId, Integer funcId) {
		super();
		this.adminId = adminId;
		this.funcId = funcId;
	}
	
	
	// --- Getters and Setters ---
	
	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Integer getFuncId() {
		return funcId;
	}

	public void setFuncId(Integer funcId) {
		this.funcId = funcId;
	}
	
	
}
