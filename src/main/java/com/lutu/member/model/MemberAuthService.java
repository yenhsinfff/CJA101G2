package com.lutu.member.model;

import com.lutu.member.model.MemberVO;
import com.lutu.member.dto.MemberLoginDTO;
import com.lutu.member.dto.RegisterRequest;

public interface MemberAuthService {
	
    MemberVO login(String memAcc, String memPwd);
    
    boolean checkAccountStatus(MemberLoginDTO member);
    
    MemberVO register(RegisterRequest dto);
    
    MemberLoginDTO loginDTO(String memAcc,String memPwd);
    
    
}
