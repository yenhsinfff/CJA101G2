package com.lutu.functions.model;

import java.io.Serializable;

public class FunctionsVO implements Serializable{

	private Integer funcId; //權限編號
	private String funcName;//權限功能名稱
	private String funcDes;//功能敘述
	
	public FunctionsVO() {
		super();
	}

	public FunctionsVO(Integer funcId, String funcName, String funcDes) {
		super();
		this.funcId = funcId;
		this.funcName = funcName;
		this.funcDes = funcDes;
	}

	
	// --- Getters and Setters ---
	
	public Integer getFuncId() {
		return funcId;
	}

	public void setFuncId(Integer funcId) {
		this.funcId = funcId;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getFuncDes() {
		return funcDes;
	}

	public void setFuncDes(String funcDes) {
		this.funcDes = funcDes;
	}


}
