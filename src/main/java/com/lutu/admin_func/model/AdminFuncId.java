package com.lutu.admin_func.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AdminFuncId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "admin_id")
    private Integer adminId;

    @Column(name = "func_id")
    private Integer funcId;

    public AdminFuncId() {
    }

    public AdminFuncId(Integer adminId, Integer funcId) {
        this.adminId = adminId;
        this.funcId = funcId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdminFuncId)) return false;
        AdminFuncId that = (AdminFuncId) o;
        return Objects.equals(adminId, that.adminId) &&
               Objects.equals(funcId, that.funcId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adminId, funcId);
    }
}
