package com.lutu.member.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lutu.member.dto.ChangePasswordRequest;
import com.lutu.member.model.MemberAuthService;
import com.lutu.member.model.MemberCrudService;
import com.lutu.member.model.MemberVO;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;





@Controller
@RequestMapping("/mem")
public class MemberController {

	@Autowired
	private MemberCrudService memberCrudService;
	
	
    // 新增：處理 select_page 頁面
    @GetMapping("/select_page")
    public String selectPage(Model model) {
        List<MemberVO> list = memberCrudService.getAll();
        model.addAttribute("memListData", list);
        return "back-end/mem/select_page";
    }
	
	
    // 新增：列出所有會員
    @GetMapping("/listAllMem")
    public String listAllMem(Model model) {
        List<MemberVO> list = memberCrudService.getAll();
        model.addAttribute("memListData", list);
        return "back-end/mem/listAllMem";
    }
	
    
    // 新增：處理查詢單一會員的顯示
    @PostMapping("/getOne_For_Display")
    public String getOneForDisplay(@RequestParam("memId") String memId, Model model) {
        MemberVO memberVO = memberCrudService.getOneMember(Integer.valueOf(memId));
        model.addAttribute("memberVO", memberVO);
        
        // 載入所有會員資料供下拉選單使用
        List<MemberVO> list = memberCrudService.getAll();
        model.addAttribute("memListData", list);
        
        return "back-end/mem/select_page";
    }
    
    
	
	@GetMapping("/addMem")
	public String showAddMemForm(Model model) {
	    model.addAttribute("memberVO", new MemberVO()); // 必須這樣命名
	    return "back-end/mem/addMem"; // 路徑要正確
	}

	
	@PostMapping("/insert")
	public String insert(@Valid MemberVO memberVO, BindingResult result, ModelMap model,
			@RequestParam("memPic") MultipartFile[] parts) throws IOException {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		result = removeFieldError(memberVO, result, "memPic");
	    if (parts != null && parts.length > 0 && !parts[0].isEmpty()) {
	        for (MultipartFile multipartFile : parts) {
	            byte[] buf = multipartFile.getBytes();
	            memberVO.setMemPic(buf);
	        }
	    }else {
	        memberVO.setMemPic(null); // 沒上傳圖片則設為 null
	    }

	    // 表單驗證錯誤處理（不再強制圖片為必填）
	    if (result.hasErrors()) {
	        return "back-end/mem/addMem";
	    }
		/*************************** 2.開始新增資料 *****************************************/
	    memberCrudService.addMember(memberVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<MemberVO> list = memberCrudService.getAll();
		model.addAttribute("memListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/mem/listAllMem";
	}
	
	
	@PostMapping("/getOne_For_Update")
	public String getOne_For_Update(@RequestParam("memId") String memId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/

		MemberVO memberVO = memberCrudService.getOneMember(Integer.valueOf(memId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("memberVO", memberVO);
		return "back-end/mem/update_mem_input"; // 查詢完成後轉交update_mem_input.html
	}
	
	
	@PostMapping("/update")
	public String update(@Valid MemberVO memberVO, BindingResult result, ModelMap model,
			@RequestParam("memPic") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		result = removeFieldError(memberVO, result, "memPic");
		if (parts[0].isEmpty()) {
		    memberVO.setMemPic(null);
		} else {
		    memberVO.setMemPic(parts[0].getBytes());
		}
		
		if (result.hasErrors()) {
			return "back-end/mem/update_mem_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
		memberCrudService.updateMember(memberVO);
		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		memberVO = memberCrudService.getOneMember(Integer.valueOf(memberVO.getMemId()));
		model.addAttribute("memberVO", memberVO);
		return "back-end/mem/listOneMem"; // 修改成功後轉交listOneMem.html
	}

	
	@PostMapping("/delete")
	public String delete(@RequestParam("memId") String memId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		
		memberCrudService.deleteMember(Integer.valueOf(memId));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<MemberVO> list = memberCrudService.getAll();
		model.addAttribute("memListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/mem/listAllMem"; //刪除完成後轉交listAllMem.html
	}
	
	
	
	
	public BindingResult removeFieldError(MemberVO memberVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(memberVO, "memberVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
	
	
	
	
	
	@Autowired
	private MemberAuthService memberAuthService;
	
	public MemberAuthService getMemberAuthService() {
		return memberAuthService;
	}

	public void setMemberAuthService(MemberAuthService memberAuthService) {
		this.memberAuthService = memberAuthService;
	}
	


	
    
}