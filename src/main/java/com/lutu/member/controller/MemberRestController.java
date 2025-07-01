package com.lutu.member.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.lutu.member.dto.ChangePasswordRequest;
import com.lutu.member.dto.LoginRequest;
import com.lutu.member.dto.MemberLoginDTO;
import com.lutu.member.dto.RegisterRequest;
import com.lutu.member.dto.UpdateMemberRequest;
import com.lutu.member.model.MemberAuthService;
import com.lutu.member.model.MemberCrudService;
import com.lutu.member.model.MemberRepository;
import com.lutu.member.model.MemberVO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/member")
public class MemberRestController {

    @Autowired
    private MemberAuthService authService;
    
    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
    	System.out.println("login_MEM:"+loginRequest.getMemAcc());
    	System.out.println("login_PWD:"+loginRequest.getMemPwd());
    	MemberLoginDTO member = authService.loginDTO(loginRequest.getMemAcc(), loginRequest.getMemPwd());
    
        if (member != null) {
            if (authService.checkAccountStatus(member)) {
                session.setAttribute("loggedInMember", member);
                
                // 回傳 JSON 包含狀態與會員資料
                Map<String, Object> response = new HashMap<>();
                response.put("status", "登入成功");
                response.put("member", member);
                
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(403).body("帳號未啟用");
            }
        } else {
            return ResponseEntity.status(401).body("帳號或密碼錯誤");
        }
    }
    
    

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("登出成功");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            MemberVO created = authService.register(registerRequest);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentMember(HttpSession session) {
        Object member = session.getAttribute("loggedInMember");
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.status(401).body("尚未登入");
        }
    }
    
    
    
    @Autowired
    private MemberCrudService memberCrudService; 
    
    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateMember(
            @RequestParam(value = "memName", required = false) String memName,
            @RequestParam(value = "memPwd", required = false) String memPwd,
            @RequestParam(value = "memMobile", required = false) String memMobile,
            @RequestParam(value = "memAddr", required = false) String memAddr,
            @RequestPart(name = "memPic", required = false) MultipartFile memPic,
            HttpSession session) {

    	MemberLoginDTO loggedInMember = (MemberLoginDTO) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return ResponseEntity.status(401).body("未登入");
        }

        // 建立 DTO 物件
        UpdateMemberRequest dto = new UpdateMemberRequest();
        dto.setMemName(memName);
        dto.setMemPwd(memPwd);
        dto.setMemMobile(memMobile);
        dto.setMemAddr(memAddr);

        System.out.println("收到資料：");
        System.out.println("memName: " + dto.getMemName());
        System.out.println("memPwd: " + (dto.getMemPwd() != null ? "***" : "null"));
        System.out.println("memMobile: " + dto.getMemMobile());
        System.out.println("memAddr: " + dto.getMemAddr());
        System.out.println("memPic is null: " + (memPic == null));
        
        try {
            MemberVO updated = memberCrudService.updateMemberSelective(loggedInMember.getMemId(), dto, memPic);
            session.setAttribute("loggedInMember", updated); // 更新 Session
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("更新失敗：" + e.getMessage());
        }
    }





    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        Optional<MemberVO> optional = memberRepository.findByVerificationToken(token);
        
        if (optional.isPresent()) {
            MemberVO member = optional.get();

            if (member.getAccStatus() == 1) {
                return ResponseEntity.ok("此帳號已經啟用過囉！");
            }

            member.setAccStatus((byte) 1); // 啟用帳號
            member.setVerificationToken(null); // 清除 token
            memberRepository.save(member);
            
            return ResponseEntity.ok("帳號啟用成功，請返回登入畫面！");
        } else {
            return ResponseEntity.badRequest().body("驗證失敗，Token 無效或帳號不存在。");
        }
    }

    
    
  

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            HttpSession session) {

        // === 修改處：改成 MemberLoginDTO 來接 session 物件
        MemberLoginDTO memberDTO = (MemberLoginDTO) session.getAttribute("loggedInMember");
        if (memberDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("請先登入");
        }

        // === 修改處：用 memberDTO.getMemId() 從資料庫查 MemberVO
        Optional<MemberVO> optionalMember = memberRepository.findById(memberDTO.getMemId());
        if (optionalMember.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("找不到會員資料");
        }
        MemberVO member = optionalMember.get();

        // 確認舊密碼是否正確
        if (!member.getMemPwd().equals(request.getOldPassword())) {
            return ResponseEntity.badRequest().body("舊密碼錯誤");
        }

        //更新密碼並存入資料庫
        member.setMemPwd(request.getNewPassword());
        memberRepository.save(member);

        return ResponseEntity.ok("密碼修改成功");
    }
    

    
}