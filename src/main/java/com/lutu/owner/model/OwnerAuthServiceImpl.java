package com.lutu.owner.model;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lutu.owner.dto.OwnerChangePasswordRequest;
import com.lutu.owner.dto.OwnerLoginResponse;
import com.lutu.owner.dto.OwnerRegisterRequest;
import com.lutu.owner.dto.OwnerUpdateRequest;

import jakarta.servlet.http.HttpSession;

@Service
public class OwnerAuthServiceImpl implements OwnerAuthService {

    private final OwnerRepository ownerRepo;
    private final JavaMailSender mailSender;

    public OwnerAuthServiceImpl(OwnerRepository ownerRepo, JavaMailSender mailSender) {
        this.ownerRepo = ownerRepo;
        this.mailSender = mailSender;
    }

    @Override
    public void register(OwnerRegisterRequest request) {
        if (ownerRepo.findByOwnerAcc(request.getOwnerAcc()).isPresent()) {
            throw new RuntimeException("帳號已存在");
        }

        OwnerVO owner = new OwnerVO();
        owner.setOwnerAcc(request.getOwnerAcc());
        owner.setOwnerPwd(request.getOwnerPwd());
        owner.setOwnerName(request.getOwnerName());
        owner.setOwnerGui(request.getOwnerGui());
        owner.setOwnerRep(request.getOwnerRep());
        owner.setOwnerTel(request.getOwnerTel());
        owner.setOwnerPoc(request.getOwnerPoc());
        owner.setOwnerConPhone(request.getOwnerConPhone());
        owner.setOwnerAddr(request.getOwnerAddr());
        owner.setOwnerEmail(request.getOwnerEmail());
        owner.setBankAccount(request.getBankAccount());
        owner.setOwnerPic(request.getOwnerPic());
        owner.setAccStatus((byte) 0); // 初始狀態：未啟用
        owner.setVerificationToken(UUID.randomUUID().toString()); // 產生驗證碼

        ownerRepo.save(owner);

        sendVerificationEmail(owner.getOwnerAcc(), owner.getVerificationToken());
    }

    private void sendVerificationEmail(String to, String token) {
        String verifyLink = "http://lutu.ddnsking.com/api/owner/verify?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("【露營系統】營地主帳號驗證信");
        message.setText("您好，請點擊以下連結完成帳號驗證：\n" + verifyLink);
        message.setFrom("ju25433@gmail.com");

        mailSender.send(message);
    }

    @Override
    public OwnerVO login(String ownerAcc, String ownerPwd) {
        OwnerVO owner = ownerRepo.findByOwnerAcc(ownerAcc)
            .orElseThrow(() -> new IllegalArgumentException("帳號不存在"));

        if (!owner.getOwnerPwd().equals(ownerPwd)) {
            throw new IllegalArgumentException("密碼錯誤");
        }

        if (owner.getAccStatus() != 1) {
            throw new IllegalArgumentException("帳號尚未啟用或已停用");
        }

        return owner; // ✅ 直接回傳 OwnerVO
    }

    @Override
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @Override
    @Transactional
    public void verifyAccount(String token) {
        OwnerVO owner = ownerRepo.findByVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("驗證碼錯誤或已使用"));

        owner.setAccStatus((byte) 1); // 啟用帳號
        owner.setVerificationToken(null); // 清除驗證碼
        ownerRepo.save(owner);
    }

    
    @Override
    public OwnerVO update(OwnerUpdateRequest request, HttpSession session) {
        OwnerVO loginOwner = (OwnerVO) session.getAttribute("loggedInOwner");
        if (loginOwner == null) {
            throw new RuntimeException("尚未登入");
        }

        Optional<OwnerVO> optional = ownerRepo.findById(loginOwner.getOwnerId());
        if (optional.isPresent()) {
            OwnerVO owner = optional.get();

            // 只更新非空且非空字串欄位
            if (request.getOwnerRep() != null && !request.getOwnerRep().isBlank()) {
                owner.setOwnerRep(request.getOwnerRep());
            }
            if (request.getOwnerTel() != null && !request.getOwnerTel().isBlank()) {
                owner.setOwnerTel(request.getOwnerTel());
            }
            if (request.getOwnerPoc() != null && !request.getOwnerPoc().isBlank()) {
                owner.setOwnerPoc(request.getOwnerPoc());
            }
            if (request.getOwnerConPhone() != null && !request.getOwnerConPhone().isBlank()) {
                owner.setOwnerConPhone(request.getOwnerConPhone());
            }
            if (request.getBankAccount() != null && !request.getBankAccount().isBlank()) {
                owner.setBankAccount(request.getBankAccount());
            }

            // 圖片部分判斷檔案是否存在且不為空
            if (request.getOwnerPic() != null && !request.getOwnerPic().isEmpty()) {
                try {
                    owner.setOwnerPic(request.getOwnerPic().getBytes());
                } catch (IOException e) {
                    throw new RuntimeException("圖片讀取失敗");
                }
            }

            // 儲存更新
            ownerRepo.save(owner);
            return owner;
        }

        throw new RuntimeException("找不到營地主資料");
    }
    
    @Override
    public void changePassword(OwnerChangePasswordRequest request, HttpSession session) {
    	
    	OwnerVO loggedIn = (OwnerVO) session.getAttribute("loggedInOwner");
        if (loggedIn == null) {
            throw new RuntimeException("尚未登入");
        }

        OwnerVO owner = ownerRepo.findById(loggedIn.getOwnerId())
            .orElseThrow(() -> new RuntimeException("找不到使用者"));
        

        if (!owner.getOwnerPwd().equals(request.getOldPassword())) {
            throw new RuntimeException("舊密碼錯誤");
        }

        owner.setOwnerPwd(request.getNewPassword());
        ownerRepo.save(owner);
    }
    
    @Override
    public List<OwnerVO> getAllOwners() {
        return ownerRepo.findAll();
    }
    
    
}
