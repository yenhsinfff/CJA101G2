package com.lutu.member.model;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lutu.member.dto.UpdateMemberRequest;

import jakarta.transaction.Transactional;


public interface MemberCrudService {
	
    MemberVO addMember(MemberVO memberVO);

    MemberVO updateMember(MemberVO memberVO);

    MemberVO getOneMember(Integer memId);

    List<MemberVO> getAllMembers();

	List<MemberVO> getAll();
	
	MemberVO updateMemberSelective(Integer memId, UpdateMemberRequest dto, MultipartFile memPic) throws IOException;

	void deleteMember(Integer memId);
	

}




