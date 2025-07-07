package com.lutu.owner.model;

import java.util.List;

import com.lutu.owner.dto.*;

import jakarta.servlet.http.HttpSession;

public interface OwnerAuthService {
	
    void register(OwnerRegisterRequest request);
    OwnerVO login(String ownerAcc, String ownerPwd);
    void logout(HttpSession session);
    void verifyAccount(String token);
    public OwnerVO update(OwnerUpdateRequest request, HttpSession session);
    void changePassword(OwnerChangePasswordRequest request, HttpSession session);
    List<OwnerVO> getAllOwners();
    
}