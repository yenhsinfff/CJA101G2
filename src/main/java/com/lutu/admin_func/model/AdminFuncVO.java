package com.lutu.admin_func.model;

import java.io.Serializable;

import com.lutu.administrator.model.AdministratorVO;
import com.lutu.functions.model.FunctionsVO;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;



@Entity
@Table(name = "admin_func") 
public class AdminFuncVO implements Serializable{
	private static final long serialVersionUID = 1L;	
	
    @EmbeddedId
    private AdminFuncId id;
	
	@ManyToOne
	@MapsId("adminId") // 對應複合主鍵中的 adminId
	@JoinColumn(name = "admin_id")
	private AdministratorVO administrator; //管理員編號
	
	@ManyToOne
	@MapsId("funcId") // 對應複合主鍵中的 funcId
	@JoinColumn(name = "func_id")
	private FunctionsVO function; //權限編號
	
	
	public AdminFuncVO() {
	}
	
    public AdminFuncId getId() {
        return id;
    }

    public void setId(AdminFuncId id) {
        this.id = id;
    }

	public AdministratorVO getAdministrator() {
		return administrator;
	}

    public void setAdministrator(AdministratorVO administrator) {
        this.administrator = administrator;
    }

    public FunctionsVO getFunction() {
        return function;
    }

    public void setFunction(FunctionsVO function) {
        this.function = function;
    }
    
    
}
	
	
