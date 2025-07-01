package com.lutu.owner.model;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lutu.owner.dto.OwnerLoginResponse;
import com.lutu.owner.dto.OwnerChangePasswordRequest;
import com.lutu.owner.dto.OwnerLoginRequest;
import com.lutu.owner.dto.OwnerRegisterRequest;
import com.lutu.owner.dto.OwnerUpdateRequest;
import com.lutu.owner.model.OwnerVO;
import com.lutu.owner.model.OwnerRepository;

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
        String verifyLink = "http://localhost:8080/api/owner/verify?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("【露營系統】營地主帳號驗證信");
        message.setText("您好，請點擊以下連結完成帳號驗證：\n" + verifyLink);
        message.setFrom("ju25433@gmail.com");

        mailSender.send(message);
    }

    @Override
    public OwnerLoginResponse login(String acc, String pwd) {
        OwnerVO owner = ownerRepo.findByOwnerAcc(acc)
                .orElseThrow(() -> new RuntimeException("帳號不存在"));

        if (!owner.getOwnerPwd().equals(pwd)) {
            throw new RuntimeException("密碼錯誤");
        }

        if (owner.getAccStatus() == 0) {
            throw new RuntimeException("帳號尚未驗證");
        } else if (owner.getAccStatus() == 2) {
            throw new RuntimeException("帳號已被停權");
        }

        return new OwnerLoginResponse(owner.getOwnerId(), owner.getOwnerAcc(), owner.getAccStatus());
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
    public void update(OwnerUpdateRequest request, HttpSession session) {
        OwnerLoginResponse loginOwner = (OwnerLoginResponse) session.getAttribute("loggedInOwner");
        Optional<OwnerVO> optional = ownerRepo.findById(loginOwner.getOwnerId());

        if (optional.isPresent()) {
            OwnerVO owner = optional.get();

            if (request.getOwnerPwd() != null) owner.setOwnerPwd(request.getOwnerPwd());
            if (request.getOwnerRep() != null) owner.setOwnerRep(request.getOwnerRep());
            if (request.getOwnerTel() != null) owner.setOwnerTel(request.getOwnerTel());
            if (request.getOwnerPoc() != null) owner.setOwnerPoc(request.getOwnerPoc());
            if (request.getOwnerConPhone() != null) owner.setOwnerConPhone(request.getOwnerConPhone());
            if (request.getBankAccount() != null) owner.setBankAccount(request.getBankAccount());

            // 關鍵圖片處理
            if (request.getOwnerPic() != null && !request.getOwnerPic().isEmpty()) {
                try {
                    owner.setOwnerPic(request.getOwnerPic().getBytes());
                } catch (IOException e) {
                    e.printStackTrace(); // 或改成丟 RuntimeException
                    throw new RuntimeException("圖片讀取失敗");
                }
            }

            ownerRepo.save(owner);
        }
    }

    
    @Override
    public void changePassword(OwnerChangePasswordRequest request, HttpSession session) {
        OwnerLoginResponse loggedIn = (OwnerLoginResponse) session.getAttribute("loggedInOwner");
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
}
