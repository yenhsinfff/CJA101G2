package com.lutu.member.model;

import java.util.Optional;
import java.util.UUID;

import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lutu.member.dto.MemberLoginDTO;
import com.lutu.member.dto.RegisterRequest;

@Service
public class MemberAuthServiceImpl implements MemberAuthService {

	
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JavaMailSender mailSender;
   
    
    
    
    @Override
    public MemberVO login(String memAcc, String memPwd) {
        Optional<MemberVO> optional = memberRepository.findByMemAcc(memAcc);
        System.out.println("MEMBER:"+memAcc);
        if (optional.isPresent()) {
            MemberVO member = optional.get();
            //明文密碼比對
            if (member.getMemPwd().equals(memPwd)) {
                return member;
            }
        }
        return null;
    }
    
//    @Override
//    public MemberLoginDTO loginDTO(String memAcc,String memPwd) {
//    	Optional<MemberLoginDTO> optional =memberRepository.findDtoByMemAcc(memAcc);
//    	
//    	if (optional.isPresent()) {
//    		MemberLoginDTO member = optional.get();
//            //明文密碼比對
//            if (member.getMemPwd().equals(memPwd)) {
//                return member;
//            }
//        }
//        return null;
//    }
    
    @Override
    public MemberLoginDTO loginDTO (String memAcc, String memPwd) {
        Optional<MemberVO> optional = memberRepository.findByMemAcc(memAcc);
        if (optional.isPresent()) {
            MemberVO member = optional.get();
            if (member.getMemPwd().equals(memPwd)) {
                return new MemberLoginDTO(member); // 轉換為DTO
            }
        }
        return null;
    }

    
    @Override
    public boolean checkAccountStatus(MemberLoginDTO member) {
        // 假設accStatus=1表示啟用
        return member.getAccStatus() == 1;
    }



    @Override
    public MemberVO register(RegisterRequest dto) {
        Optional<MemberVO> existing = memberRepository.findByMemAcc(dto.getMemAcc());
        if (existing.isPresent()) {
            throw new RuntimeException("帳號已存在");
        }

        MemberVO member = new MemberVO();
        member.setMemAcc(dto.getMemAcc());
        member.setMemPwd(dto.getMemPwd());
        member.setAccStatus((byte) 0); // 新註冊預設 accStatus = 0（未啟用）
        // 其他欄位照原本設定
        member.setMemName(dto.getMemName());
        member.setMemGender(dto.getMemGender());
        member.setMemEmail(dto.getMemEmail());
        member.setMemMobile(dto.getMemMobile());
        member.setMemAddr(dto.getMemAddr());
        member.setMemNation(dto.getMemNation());
        member.setMemNationId(dto.getMemNationId());
        member.setMemPic(dto.getMemPic());
        member.setMemBirth(dto.getMemBirth());

        // 產生驗證用 token 並儲存
        String token = UUID.randomUUID().toString();
        member.setVerificationToken(token);

        memberRepository.save(member);

        // 發送驗證信
        sendVerificationEmail(member.getMemEmail(), token);

        return member;
    }

    private void sendVerificationEmail(String toEmail, String token) {
        try {
            String subject = "請確認您的註冊信箱";
            String verificationLink = "http://localhost:8081/CJA101G02/api/member/verify?token=" + token;
            String content = "<p>感謝您的註冊！</p>"
                    + "<p>請點擊下列連結以啟用帳號：</p>"
                    + "<a href=\"" + verificationLink + "\">啟用帳號</a>";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("無法發送驗證信：" + e.getMessage());
        }
    }
    
    
    
    
   
}