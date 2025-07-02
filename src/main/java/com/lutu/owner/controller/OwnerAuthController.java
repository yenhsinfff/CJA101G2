package com.lutu.owner.controller;

import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lutu.owner.dto.*;
import com.lutu.owner.model.OwnerAuthService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/owner")
public class OwnerAuthController {

    private final OwnerAuthService authService;

    public OwnerAuthController(OwnerAuthService authService) {
        this.authService = authService;
    }

    // 註冊
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody OwnerRegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("註冊成功，請至信箱收取驗證信。");
    }

    // 登入
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody OwnerLoginRequest request, HttpSession session) {
        OwnerLoginResponse response = authService.login(request.getOwnerAcc(), request.getOwnerPwd());
        session.setAttribute("loggedInOwner", response);  // 也可直接放 OwnerVO，但為了簡化前端資料回應使用 DTO
        return ResponseEntity.ok(response);
    }

    // 登出
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("登出成功");
    }

    // 驗證信箱
    @GetMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam String token) {
        authService.verifyAccount(token);
        return ResponseEntity.ok("帳號啟用成功");
    }

    //更新資料
    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@ModelAttribute OwnerUpdateRequest request, HttpSession session) {
        authService.update(request, session);
        return ResponseEntity.ok("資料更新成功");
    }
    
    //修改密碼
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody OwnerChangePasswordRequest request, HttpSession session) {
        authService.changePassword(request, session);
        return ResponseEntity.ok("密碼修改成功");
    }
    
}