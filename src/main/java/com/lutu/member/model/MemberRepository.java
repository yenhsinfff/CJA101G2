package com.lutu.member.model;



import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberVO, Integer> {
	
	Optional<MemberVO> findByMemAcc(String memAcc);

    Optional<MemberVO> findByMemAccAndMemPwd(String memAcc, String memPwd);
    
    Optional<MemberVO> findByVerificationToken(String token);
    
    boolean existsByMemEmail(String memEmail);
    Optional<MemberVO> findByMemEmail(String memEmail);
    
}
