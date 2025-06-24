package com.lutu.member.model;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;




@Service("memberService")
public class MemberService {

	@Autowired
	MemberRepository repository;
	
//	@Autowired
//    private SessionFactory sessionFactory;

	public void addMember(MemberVO memberVO) {
		repository.save(memberVO);
	}

	public void updateMember(MemberVO memberVO) {
		repository.save(memberVO);
	}

	public void deleteMember(Integer memId) {
		if (repository.existsById(memId))
			repository.deleteById(memId);
		
	}

	@Transactional
	public MemberVO getOneMember(Integer memId) {
		Optional<MemberVO> optional = repository.findById(memId);
		MemberVO member = optional.orElse(null);

		if (member != null) {
			member.getCampTrackLists().size();
		}
		return member;
	}

	public List<MemberVO> getAll() {
		return repository.findAll();
	}
	
	
	@Transactional
	public Boolean updateMemberPicture(Integer memId, MultipartFile file) {
	    MemberVO member = repository.findById(memId)
	        .orElseThrow(() -> new RuntimeException("會員不存在"));
	    try {
			member.setMemPic(file.getBytes());
			repository.save(member);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	    
	}

}