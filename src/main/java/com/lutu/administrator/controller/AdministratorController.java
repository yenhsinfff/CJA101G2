package com.lutu.administrator.controller;

import com.lutu.administrator.dto.AdminDTO;
import com.lutu.administrator.dto.LoginRequest;
import com.lutu.administrator.model.AdministratorVO;
import com.lutu.administrator.model.AdministratorRepository;
import com.lutu.administrator.model.AdministratorService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/admin")
public class AdministratorController {

    @Autowired
    private AdministratorRepository adminRepo;
    
    @Autowired
    private AdministratorService administratorService;

    // 登入
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
//        System.out.println("AdminAcc:"+loginRequest.getAdminAcc());
//        System.out.println("AdminPwd:"+loginRequest.getAdminPwd());
        Optional<AdministratorVO> optionalAdmin = adminRepo.findByAdminAcc(loginRequest.getAdminAcc());

        if (optionalAdmin.isPresent()) {
            AdministratorVO admin = optionalAdmin.get();

            if (!admin.getAdminPwd().equals(loginRequest.getAdminPwd())) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "帳號或密碼錯誤");
                return ResponseEntity.status(401).body(error);
            }

            if (admin.getAdminStatus() != 1) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "帳號未啟用或已停權");
                return ResponseEntity.status(403).body(error);
            }

            // 登入成功 → 設定 session 並回傳 admin 資料
            session.setAttribute("loggedInAdmin", admin);
            System.out.println("管理員登入成功");
            Map<String, Object> adminData = new HashMap<>();
            adminData.put("adminId", admin.getAdminId());
            adminData.put("adminAcc", admin.getAdminAcc());
            adminData.put("adminName", admin.getAdminName());
            adminData.put("adminStatus", admin.getAdminStatus());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "登入成功");
            response.put("admin", adminData);

            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "帳號或密碼錯誤");
            return ResponseEntity.status(401).body(error);
        }
    }
 

    // 登出
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        System.out.println("管理員登出成功");
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


   
    //新增管理員
    @PostMapping("/add")
    public ResponseEntity<String> addAdmin(
        @RequestParam String adminAcc,
        @RequestParam String adminName,
        @RequestParam String adminPwd,
        @RequestParam(defaultValue = "0") String adminStatus,
        HttpSession session) {
        
        try {
            AdministratorVO admin = new AdministratorVO();
            admin.setAdminAcc(adminAcc);
            admin.setAdminName(adminName);
            admin.setAdminPwd(adminPwd);
            admin.setAdminStatus(Byte.parseByte(adminStatus));
            
            administratorService.addAdministrator(admin);
            return ResponseEntity.ok("新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("新增失敗：" + e.getMessage());
        }
    }
    
    
    // 修改狀態
    @PutMapping("/update-status/{id}")
    public ResponseEntity<Map<String, String>> updateStatus(@PathVariable Integer id, @RequestParam byte status) {
        return adminRepo.findById(id).map(admin -> {
            admin.setAdminStatus(status);
            adminRepo.save(admin);

            Map<String, String> body = new HashMap<>();
            body.put("status", "success");
            body.put("message", "狀態更新成功");
            return ResponseEntity.ok(body);

        }).orElseGet(() -> {
            Map<String, String> body = new HashMap<>();
            body.put("status", "fail");
            body.put("message", "找不到管理員");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        });
    }
    
    
}