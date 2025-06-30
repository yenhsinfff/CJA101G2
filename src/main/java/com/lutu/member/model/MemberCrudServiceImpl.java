package com.lutu.member.model;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lutu.member.dto.UpdateMemberRequest;

import jakarta.transaction.Transactional;

@Service
public class MemberCrudServiceImpl implements MemberCrudService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public MemberVO addMember(MemberVO memberVO) {
        return memberRepository.save(memberVO);
    }

    @Override
    @Transactional
    public MemberVO updateMember(MemberVO updatedMember) {
        // 確認會員存在
        MemberVO member = memberRepository.findById(updatedMember.getMemId())
                .orElseThrow(() -> new RuntimeException("會員不存在"));

        // 這裡示範全部欄位覆蓋，可依需求調整保留未更新欄位
        member.setMemName(updatedMember.getMemName());
        member.setMemPwd(updatedMember.getMemPwd());
        member.setMemMobile(updatedMember.getMemMobile());
        member.setMemAddr(updatedMember.getMemAddr());
        member.setMemPic(updatedMember.getMemPic());

        // 不能改 email 等關鍵欄位就不改
        // member.setMemEmail(...) 留空即可

        return memberRepository.save(member);
    }

    @Override
    @Transactional
    public MemberVO updateMemberSelective(Integer memId, UpdateMemberRequest dto, MultipartFile memPic) throws IOException {
        // 檢查會員是否存在
        MemberVO member = memberRepository.findById(memId)
                .orElseThrow(() -> new RuntimeException("會員不存在"));

        System.out.println("=== 開始更新會員資料 ===");
        System.out.println("會員ID: " + memId);
        System.out.println("更新前資料 - 姓名: " + member.getMemName() + ", 手機: " + member.getMemMobile() + ", 地址: " + member.getMemAddr());

        boolean hasUpdate = false;

        // 只更新有值且不為空的欄位，保留舊資料
        if (dto.getMemName() != null && !dto.getMemName().trim().isEmpty()) {
            String newName = dto.getMemName().trim();
            if (!newName.equals(member.getMemName())) {
                member.setMemName(newName);
                System.out.println("更新姓名: " + member.getMemName() + " -> " + newName);
                hasUpdate = true;
            }
        }
        
        if (dto.getMemPwd() != null && !dto.getMemPwd().trim().isEmpty()) {
            String newPwd = dto.getMemPwd().trim();
            // 這裡可以加入密碼加密邏輯
            // String encryptedPwd = passwordEncoder.encode(newPwd);
            if (!newPwd.equals(member.getMemPwd())) {
                member.setMemPwd(newPwd);
                System.out.println("密碼已更新");
                hasUpdate = true;
            }
        }
        
        if (dto.getMemMobile() != null && !dto.getMemMobile().trim().isEmpty()) {
            String newMobile = dto.getMemMobile().trim();
            if (!newMobile.equals(member.getMemMobile())) {
                member.setMemMobile(newMobile);
                System.out.println("更新手機: " + member.getMemMobile() + " -> " + newMobile);
                hasUpdate = true;
            }
        }
        
        if (dto.getMemAddr() != null && !dto.getMemAddr().trim().isEmpty()) {
            String newAddr = dto.getMemAddr().trim();
            if (!newAddr.equals(member.getMemAddr())) {
                member.setMemAddr(newAddr);
                System.out.println("更新地址: " + member.getMemAddr() + " -> " + newAddr);
                hasUpdate = true;
            }
        }
        
        // 處理圖片上傳
        if (memPic != null && !memPic.isEmpty()) {
            // 檔案驗證
            validateImageFile(memPic);
            
            byte[] newPicBytes = memPic.getBytes();
            member.setMemPic(newPicBytes);
            System.out.println("照片已更新，檔案大小: " + newPicBytes.length + " bytes，檔案類型: " + memPic.getContentType());
            hasUpdate = true;
        }

        if (!hasUpdate) {
            System.out.println("沒有資料需要更新");
            return member;
        }

        // 儲存更新
        MemberVO savedMember = memberRepository.save(member);
        System.out.println("=== 會員資料更新完成 ===");
        
        return savedMember;
    }

    /**
     * 驗證圖片檔案
     */
    private void validateImageFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("圖片檔案不能為空");
        }
        
        // 檢查檔案大小 (限制 5MB)
        long maxSize = 5 * 1024 * 1024; // 5MB
        if (file.getSize() > maxSize) {
            throw new RuntimeException("圖片檔案過大，請上傳小於 5MB 的檔案");
        }
        
        // 檢查檔案類型
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("請上傳有效的圖片檔案");
        }
        
        // 檢查支援的圖片格式
        String[] allowedTypes = {"image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"};
        boolean isValidType = false;
        for (String type : allowedTypes) {
            if (type.equals(contentType.toLowerCase())) {
                isValidType = true;
                break;
            }
        }
        
        if (!isValidType) {
            throw new RuntimeException("不支援的圖片格式，請上傳 JPG、PNG、GIF 或 WebP 格式的圖片");
        }
        
        System.out.println("圖片檔案驗證通過 - 檔案名: " + file.getOriginalFilename() + 
                          ", 大小: " + file.getSize() + " bytes, 類型: " + contentType);
    }

    
    
    @Override
    public MemberVO getOneMember(Integer memId) {
        return memberRepository.findById(memId).orElse(null);
    }

    @Override
    public List<MemberVO> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public List<MemberVO> getAll() {
        return memberRepository.findAll();
    }

    @Override
    public void deleteMember(Integer memId) {
        memberRepository.deleteById(memId);
    }
    
    
    
    
}
