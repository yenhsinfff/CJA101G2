package com.lutu.member.controller;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lutu.ApiResponse;
import com.lutu.member.model.MemberService;
import com.lutu.member.model.MemberVO;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/member")
public class MemberApiController {

	@Autowired
	MemberService memberSvc = new MemberService();

	// 接收會員大頭照
	@PostMapping("/{memId}/picture")
	public ApiResponse<String> getNewAvatar(@PathVariable Integer memId,
	        @RequestParam("file") MultipartFile file) {
		Boolean response = memberSvc.updateMemberPicture(memId,file);
		if(response) {
			return new ApiResponse<>("success", "ok", "更新成功");
		}else {
			return new ApiResponse<>("fail", "fail", "更新失敗");
		}
		
	}
	
	//取德會員大頭照
	@GetMapping("/{memId}/pic")
	public void getMemPic(@PathVariable Integer memId, HttpServletResponse response) throws IOException {

		byte[] img = (memberSvc.getOneMember(memId)).getMemPic(); // 從資料庫取得

		  if (img != null && img.length > 0) {
		        try (InputStream is = new ByteArrayInputStream(img)) {
		            String mimeType = URLConnection.guessContentTypeFromStream(is);
		            if (mimeType == null) {
		                mimeType = "application/octet-stream"; // fallback 預設
		            }
		            response.setContentType(mimeType);
		            response.getOutputStream().write(img);
		        }
		    }
		  }
	
	//取得會員
	
	@GetMapping("/getallmembers")
	public List <MemberVO> getAllMembers() {
		List <MemberVO> memberList =  memberSvc.getAll();
		return memberList;
	}

}
