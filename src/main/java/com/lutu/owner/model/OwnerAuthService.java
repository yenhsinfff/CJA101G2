package com.lutu.owner.model;

import com.lutu.owner.dto.*;

import jakarta.servlet.http.HttpSession;

public interface OwnerAuthService {
	
    void register(OwnerRegisterRequest request);
    OwnerLoginResponse login(String acc, String pwd);
    void logout(HttpSession session);
    void verifyAccount(String token);
    void update(OwnerUpdateRequest request, HttpSession session);
    void changePassword(OwnerChangePasswordRequest request, HttpSession session);
    
}