package com.lutu.member.model;

import com.lutu.member.model.MemberVO;
import com.lutu.member.dto.RegisterRequest;

public interface MemberAuthService {
	
    MemberVO login(String memAcc, String memPwd);
    
    boolean checkAccountStatus(MemberVO member);
    
    MemberVO register(RegisterRequest dto);
    
    
}
