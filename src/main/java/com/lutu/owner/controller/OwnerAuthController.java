package com.lutu.owner.controller;

import org.springframework.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lutu.owner.dto.*;
import com.lutu.owner.model.OwnerAuthService;
import com.lutu.owner.model.OwnerRepository;
import com.lutu.owner.model.OwnerVO;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/owner")
public class OwnerAuthController {

    private final OwnerAuthService authService;
    
    @Autowired
    private OwnerRepository ownerRepo;

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
            // 建議直接回傳 VO
        	OwnerVO owner = authService.login(request.getOwnerAcc(), request.getOwnerPwd());
        	session.setAttribute("loggedInOwner", owner); // ✅ 存 VO

        	// 給前端回傳 DTO
        	OwnerLoginResponse response = new OwnerLoginResponse(owner);

        	Map<String, Object> body = new HashMap<>();
        	body.put("success", true);
        	body.put("message", "登入成功");
        	body.put("owner", response);
        	return ResponseEntity.ok(body);

        } catch (IllegalArgumentException e) {
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

    //更新
    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@ModelAttribute OwnerUpdateRequest request, HttpSession session) {
        OwnerVO owner = authService.update(request, session); 

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", Map.of(
            "ownerId", owner.getOwnerId(),
            "ownerPic", "/api/owner/avatar/" + owner.getOwnerId()
        ));
        return ResponseEntity.ok(response);
    }
    
    
    //顯示圖片
    @GetMapping("/avatar/{ownerId}")
    public ResponseEntity<byte[]> getOwnerAvatar(@PathVariable Integer ownerId) {
        Optional<OwnerVO> optional = ownerRepo.findById(ownerId);
        if (optional.isPresent()) {
            OwnerVO owner = optional.get();
            byte[] imageData = owner.getOwnerPic(); // 假設圖片存 byte[]
            if (imageData != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // 或 IMAGE_PNG 根據實際類型
                return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
            }
        }
        return ResponseEntity.notFound().build();
    }

    
    //修改密碼
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody OwnerChangePasswordRequest request, HttpSession session) {
        authService.changePassword(request, session);
        return ResponseEntity.ok(Map.of("status", "success", "message", "密碼修改成功"));
    }
    
    //取得資料
    @GetMapping("/profile")
    public ResponseEntity<?> getOwnerProfile(HttpSession session) {
        OwnerVO owner = (OwnerVO) session.getAttribute("loggedInOwner");

        if (owner == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("尚未登入");
        }

        // 為了安全與簡潔，傳給前端的資料可以用 DTO
        OwnerProfileDTO dto = new OwnerProfileDTO(owner);
        return ResponseEntity.ok(dto);
        
        
    }
    
    
}
    
