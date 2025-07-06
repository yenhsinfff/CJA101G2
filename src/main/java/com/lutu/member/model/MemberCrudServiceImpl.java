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

    @Transactional
    @Override
    public MemberVO updateMemberSelective(Integer memId, UpdateMemberRequest dto, MultipartFile memPic) throws IOException {
        Optional<MemberVO> optional = memberRepository.findById(memId);
        if (optional.isEmpty()) {
            throw new RuntimeException("會員不存在");
        }
        MemberVO member = optional.get();

        if (dto.getMemName() != null && !dto.getMemName().isEmpty()) {
            member.setMemName(dto.getMemName());
        }
        if (dto.getMemPwd() != null && !dto.getMemPwd().isEmpty()) {
            member.setMemPwd(dto.getMemPwd());
        }
        if (dto.getMemMobile() != null && !dto.getMemMobile().isEmpty()) {
            member.setMemMobile(dto.getMemMobile());
        }
        if (dto.getMemAddr() != null && !dto.getMemAddr().isEmpty()) {
            member.setMemAddr(dto.getMemAddr());
        }

        if (memPic != null && !memPic.isEmpty()) {
            member.setMemPic(memPic.getBytes());
        }

        return memberRepository.save(member);
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

    @Override
    public MemberVO updateMember(MemberVO memberVO) {
        return memberRepository.save(memberVO);
    }
    
    
    
}
