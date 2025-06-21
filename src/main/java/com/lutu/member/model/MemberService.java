package com.lutu.member.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




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

	public MemberVO getOneMember(Integer memId) {
		Optional<MemberVO> optional = repository.findById(memId);
		
		return optional.orElse(null);
	}

	public List<MemberVO> getAll() {
		return repository.findAll();
	}
	

}