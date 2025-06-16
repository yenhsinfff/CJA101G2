package com.lutu.camptracklist.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lutu.camp.model.CampService;
import com.lutu.camptracklist.model.CampTrackListService;
import com.lutu.camptracklist.model.CampTrackListVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/campTrackList")
public class campTrackListController {

	@Autowired
	CampTrackListService campTrackListSvc;

	@Autowired
	CampService campSvc;  //來自camp_id的關聯
	
	@Autowired
//	MemService memSvc; //來自mem_id的關聯

	/*
	 * This method will serve as addCampTrackList.html handler.
	 */
	@GetMapping("addCampTrackList")
	public String addCampTrackList(ModelMap model) {
		CampTrackListVO campTrackListVO = new CampTrackListVO();
		model.addAttribute("campTrackListVO", campTrackListVO);
		return "back-end/camptracklist/addCampTrackList";
	}

	/*
	 * This method will be called on addCampTrackList.html form submission, handling POST request It also validates the user input
	 */
//	@PostMapping("insert")
//	public String insert(
//			@Valid CampTrackListVO campTrackListVO, 
//			BindingResult result, 
//			ModelMap model) throws IOException {
//
//		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		if (result.hasErrors()) {
//			return "back-end/camptracklist/addCampTrackList";
//		}
//		/*************************** 2.開始新增資料 *****************************************/
//		campTrackListSvc.addCampTrackList(campTrackListVO);
//		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
//		List<CampTrackListVO> list = campTrackListSvc.getAll();
//		model.addAttribute("campTrackListData", list); // for listAllCampTrackList.html 第85行用
//		model.addAttribute("success", "- (新增成功)");
//		return "redirect:/camptracklist/listAllCampTrackList"; // 新增成功後重導至IndexController_inSpringBoot.java的第58行@GetMapping("/emp/listAllEmp")
//	}

	@PostMapping("delete")
	public String delete(@RequestParam("memId") Integer memId, @RequestParam("campId") Integer campId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		
		campTrackListSvc.deleteCampTrackList(memId, campId);
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<CampTrackListVO> list = campTrackListSvc.getAll();
		model.addAttribute("campTrackListData", list); // for listAllEmp.html 第85行用
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/camptracklist/listAllCampTrackList"; // 刪除完成後轉交listAllEmp.html
	}

	/*
	 * 第一種作法 Method used to populate the List Data in view. 如 : 
	 * <form:select path="deptno" id="deptno" items="${deptListData}" itemValue="deptno" itemLabel="dname" />
	 */
//	@ModelAttribute("deptListData")
//	protected List<DeptVO> referenceListData() {
//		// DeptService deptSvc = new DeptService();
//		List<DeptVO> list = deptSvc.getAll();
//		return list;
//	}

	/*
	 * 【 第二種作法 】 Method used to populate the Map Data in view. 如 : 
	 * <form:select path="deptno" id="deptno" items="${depMapData}" />
	 */
//	@ModelAttribute("campMapData") //
//	protected Map<Integer, String> referenceMapData() {
//		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
//		map.put(10, "財務部");
//		map.put(20, "研發部");
//		map.put(30, "業務部");
//		map.put(40, "生管部");
//		return map;
//	}
	

}