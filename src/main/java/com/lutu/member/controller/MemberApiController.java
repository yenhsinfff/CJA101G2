package com.lutu.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lutu.ApiResponse;
import com.lutu.member.model.MemberService;

@RestController
@RequestMapping("/members")
public class MemberApiController {

	@Autowired
	MemberService memberSvc = new MemberService();

	// 接收會員大頭照
	@PatchMapping("/{memId}/picture")
	public ApiResponse<String> getNewAvatar(@PathVariable Integer memId,
	        @RequestParam("file") MultipartFile file) {
		Boolean response = memberSvc.updateMemberPicture(memId,file);
		if(response) {
			return new ApiResponse<>("success", "ok", "更新成功");
		}else {
			return new ApiResponse<>("fail", "fail", "更新失敗");
		}
		
	}

}
