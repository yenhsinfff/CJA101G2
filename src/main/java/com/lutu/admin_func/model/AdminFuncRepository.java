package com.lutu.admin_func.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AdminFuncRepository extends JpaRepository<AdminFuncVO, AdminFuncId> {
	
    // 根據管理員ID查詢他擁有的所有功能
    List<AdminFuncVO> findByAdministrator_AdminId(Integer adminId);

    // 根據功能ID查詢有哪些管理員有此權限
    List<AdminFuncVO> findByFunction_FuncId(Integer funcId);

    // 判斷某個管理員是否有某個功能（複合主鍵查詢）
    boolean existsById(AdminFuncId id);

    // 刪除特定管理員的某個功能
    void deleteById(AdminFuncId id);
    
}