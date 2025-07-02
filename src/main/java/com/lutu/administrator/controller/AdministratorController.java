package com.lutu.administrator.controller;

import com.lutu.administrator.dto.AdminDTO;
import com.lutu.administrator.dto.LoginRequest;
import com.lutu.administrator.model.AdministratorVO;
import com.lutu.administrator.model.AdministratorRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdministratorController {

    @Autowired
    private AdministratorRepository adminRepo;

    // 登入
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        return adminRepo.findByAdminAcc(loginRequest.getAdminAcc())
                .filter(admin -> admin.getAdminPwd().equals(loginRequest.getAdminPwd()))
                .map(admin -> {
                    if (admin.getAdminStatus() != 1) {
                        return ResponseEntity.status(403).body("帳號未啟用或停權");
                    }
                    session.setAttribute("admin", admin);
                    return ResponseEntity.ok("登入成功");
                })
                .orElse(ResponseEntity.status(401).body("帳號或密碼錯誤"));
    }

    // 登出
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("已登出");
    }

    // 查全部管理員
    @GetMapping("/all")
    public List<AdminDTO> getAllAdmins() {
        return adminRepo.findAll().stream()
                .map(admin -> new AdminDTO(
                        admin.getAdminId(),
                        admin.getAdminAcc(),
                        admin.getAdminStatus(),
                        admin.getAdminName()
                ))
                .collect(Collectors.toList());
    }

    // 新增管理員
    @PostMapping("/add")
    public ResponseEntity<String> addAdmin(@RequestBody AdministratorVO admin) {
        adminRepo.save(admin);
        return ResponseEntity.ok("新增成功");
    }

    // 修改狀態
    @PutMapping("/update-status/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Integer id, @RequestParam byte status) {
        return adminRepo.findById(id).map(admin -> {
            admin.setAdminStatus(status);
            adminRepo.save(admin);
            return ResponseEntity.ok("更新成功");
        }).orElse(ResponseEntity.notFound().build());
    }
}