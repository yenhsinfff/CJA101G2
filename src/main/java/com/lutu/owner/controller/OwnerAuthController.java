package com.lutu.owner.controller;

import java.util.HashMap;
import java.util.Map;

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
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentOwner(HttpSession session) {
    	System.out.println("getCurrentOwner:尋找cuuremowner session");
    	OwnerLoginResponse response = (OwnerLoginResponse) session.getAttribute("loggedInOwner");
    	System.out.println("getCurrentOwner_response"+response);
        if (response != null) {
        	System.out.println("currentOwner:"+response);
            return ResponseEntity.ok(response);
        } else {
        	 // 登入失敗（帳號密碼錯或帳號未啟用）
            Map<String, Object> body = new HashMap<>();
            body.put("success", false);
            body.put("message", "登入失敗");
            return ResponseEntity.status(401).body(body);
        }
    }

    // 登入
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody OwnerLoginRequest request, HttpSession session) {
        try {
            OwnerLoginResponse response = authService.login(request.getOwnerAcc(), request.getOwnerPwd());
            System.out.println("currentOwner_login:"+response);
            // 登入成功，儲存 session
            session.setAttribute("loggedInOwner", response);
            System.out.println("login:成功");
            OwnerLoginResponse response1 = (OwnerLoginResponse) session.getAttribute("loggedInOwner");
            System.out.println("response1"+response1);
            // 包裝回傳 JSON
            Map<String, Object> body = new HashMap<>();
            body.put("success", true);
            body.put("message", "登入成功");
            body.put("owner", response);

            return ResponseEntity.ok(body);
        } catch (IllegalArgumentException e) {
            // 登入失敗（帳號密碼錯或帳號未啟用）
            Map<String, Object> body = new HashMap<>();
            body.put("success", false);
            body.put("message", e.getMessage());
            return ResponseEntity.status(401).body(body);
        }
    }

    // 登出
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        System.out.println("登出成功");
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