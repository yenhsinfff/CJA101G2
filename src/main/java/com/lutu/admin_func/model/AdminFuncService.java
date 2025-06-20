package com.lutu.admin_func.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminFuncService")
public class AdminFuncService {

    @Autowired
    private AdminFuncRepository adminFuncRepository;

    // ✅ 新增一筆管理員權限關聯
    public void addAdminFunc(AdminFuncVO adminFuncVO) {
        adminFuncRepository.save(adminFuncVO);
    }

    // ✅ 查詢所有管理員權限關聯
    public List<AdminFuncVO> getAll() {
        return adminFuncRepository.findAll();
    }

    // ✅ 根據複合主鍵查詢單筆
    public Optional<AdminFuncVO> getOne(AdminFuncId id) {
        return adminFuncRepository.findById(id);
    }

    // ✅ 刪除一筆管理員權限關聯
    public void deleteAdminFunc(AdminFuncId id) {
        adminFuncRepository.deleteById(id);
    }

    // ✅ 查詢某管理員擁有的所有功能
    public List<AdminFuncVO> getByAdminId(Integer adminId) {
        return adminFuncRepository.findByAdministrator_AdminId(adminId);
    }

    // ✅ 查詢某功能被哪些管理員擁有
    public List<AdminFuncVO> getByFuncId(Integer funcId) {
        return adminFuncRepository.findByFunction_FuncId(funcId);
    }

    // ✅ 檢查是否存在某筆關聯
    public boolean exists(AdminFuncId id) {
        return adminFuncRepository.existsById(id);
    }
}
