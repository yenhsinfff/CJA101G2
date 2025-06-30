package com.lutu.forget_password;

import com.lutu.member.model.MemberRepository;
import com.lutu.owner.model.OwnerRepository;
import com.lutu.administrator.model.AdministratorRepository;
import com.lutu.member.model.MemberVO;
import com.lutu.owner.model.OwnerVO;
import com.lutu.administrator.model.AdministratorVO;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class PasswordResetController {

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private OwnerRepository ownerRepo;

    @Autowired
    private AdministratorRepository adminRepo;

    @Autowired
    private ResetPasswordTokenRepository tokenRepo;

    @Autowired
    private EmailService emailService;

    // ✅ 1. 寄送忘記密碼信件
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");

        boolean exists = memberRepo.existsByMemEmail(email)
                       || ownerRepo.existsByOwnerEmail(email)
                       || adminRepo.existsByAdminAcc(email);

        if (!exists) {
            return "此信箱未註冊";
        }

        String token = UUID.randomUUID().toString();

        ResetPasswordToken resetToken = new ResetPasswordToken();
        resetToken.setEmail(email);
        resetToken.setToken(token);
        resetToken.setExpiry(LocalDateTime.now().plusHours(1)); // ⏰ 有效期限 1 小時
        tokenRepo.save(resetToken);

        String resetUrl = "http://localhost:3000/reset-password.html?token=" + token;
        String emailBody = "請點擊以下連結重設您的密碼（連結一小時內有效）：\n\n" + resetUrl;

        emailService.sendEmail(email, "密碼重設連結", emailBody);

        return "密碼重設連結已寄出，請至信箱查收";
    }

    // ✅ 2. 根據 token 重設密碼
    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String newPassword = body.get("newPassword");

        Optional<ResetPasswordToken> optional = tokenRepo.findByToken(token);
        if (optional.isEmpty() || optional.get().getExpiry().isBefore(LocalDateTime.now())) {
            return "連結已過期或無效";
        }

        String email = optional.get().getEmail();
        boolean updated = false;

        // 嘗試更新露營者密碼
        if (memberRepo.existsByMemEmail(email)) {
            Optional<MemberVO> memOpt = memberRepo.findByMemEmail(email);
            if (memOpt.isPresent()) {
                MemberVO m = memOpt.get();
                m.setPassword(newPassword); // 明文儲存（你目前的設計）
                memberRepo.save(m);
                updated = true;
            }
        }

        // 嘗試更新營地主密碼
        if (!updated && ownerRepo.existsByOwnerEmail(email)) {
            Optional<OwnerVO> ownerOpt = ownerRepo.findByOwnerEmail(email);
            if (ownerOpt.isPresent()) {
                OwnerVO o = ownerOpt.get();
                o.setPassword(newPassword);
                ownerRepo.save(o);
                updated = true;
            }
        }

        // 嘗試更新管理員密碼
        if (!updated && adminRepo.existsByAdminAcc(email)) {
            Optional<AdministratorVO> adminOpt = adminRepo.findByAdminAcc(email);
            if (adminOpt.isPresent()) {
                AdministratorVO a = adminOpt.get();
                a.setPassword(newPassword);
                adminRepo.save(a);
                updated = true;
            }
        }

        tokenRepo.delete(optional.get());

        return updated ? "密碼已成功重設，請重新登入" : "重設失敗：找不到對應帳號";
    }
}
