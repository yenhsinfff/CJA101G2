package com.lutu.functions.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;


@Entity
@Table(name = "functions")
public class FunctionsVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id  
	@Column(name = "func_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer funcId; //權限編號
	
	
	@Column(name = "func_name")
	@NotEmpty(message="權限功能名稱: 請勿空白")
	private String funcName;//權限功能名稱
	
	
	@Column(name = "func_des")
	@NotEmpty(message="功能敘述: 請勿空白")
	private String funcDes;//功能敘述
	
	
	
	
	public FunctionsVO() {
	}
	
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
