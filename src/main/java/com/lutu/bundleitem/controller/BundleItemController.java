package com.lutu.bundleitem.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
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

import com.lutu.bundleitem.model.BundleItemService;
import com.lutu.bundleitem.model.BundleItemVO;
import com.lutu.camp.model.CampService;
import com.lutu.camp.model.CampVO;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/bundleitem")
public class BundleItemController {

	@Autowired
	BundleItemService bundleItemSvc;
	
	@Autowired
	CampService campSvc;

	/*
	 * This method will serve as addEmp.html handler.
	 */
	@GetMapping("addBundleItem")
	public String addBundleItem(Model model) {
		BundleItemVO bundleItemVO = new BundleItemVO();
		 bundleItemVO.setBundleAddDate(LocalDate.now()); // 設定今天日期
		model.addAttribute("bundleItemVO", bundleItemVO);
		return "back-end/bundleitem/addBundleItem";
	}

	/*
	 * This method will be called on addEmp.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("insert")
	public String insert(@Valid BundleItemVO bundleItemVO, BindingResult result, ModelMap model) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/

		if (result.hasErrors() ) {
			return "back-end/bundleitem/addBundleItem";
		}
		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		bundleItemSvc.addBundleItem(bundleItemVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<BundleItemVO> list = bundleItemSvc.getAll();
		model.addAttribute("bundleItemListData", list); // for listAllEmp.html 第85行用
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/bundleitem/listAllBundleItem"; // 新增成功後重導至IndexController_inSpringBoot.java的第58行@GetMapping("/emp/listAllEmp")
	}

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("bundleId") String bundleId,Model model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/

		BundleItemVO bundleItemVO = bundleItemSvc.getOneBundleItem(Integer.valueOf(bundleId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("bundleItemVO", bundleItemVO);
		return "back-end/bundleitem/update_bundleitem_input"; // 查詢完成後轉交update_emp_input.html
	}

	


	
	/*
	 * This method will be called on update_emp_input.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("update")
	public String update(@Valid BundleItemVO bundleItemVO, BindingResult result, ModelMap model) throws IOException {


		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		if (result.hasErrors()) {
			return "back-end/bundleitem/update_bundleitem_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		
		bundleItemVO.setBundleAddDate(LocalDate.now()); //更新為修改日期
		bundleItemSvc.updateBundleItem(bundleItemVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		bundleItemVO = bundleItemSvc.getOneBundleItem(Integer.valueOf(bundleItemVO.getBundleId()));
		model.addAttribute("bundleItemVO", bundleItemVO);
		return "back-end/bundleitem/listOneBundleItem"; // 修改成功後轉交listOneEmp.html
	}
	

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
	@PostMapping("delete")
	public String delete(@RequestParam("bundleId") String bundleId, Model model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/

		bundleItemSvc.deleteBundleItem(Integer.valueOf(bundleId));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<BundleItemVO> list = bundleItemSvc.getAll();
		model.addAttribute("bundleItemListData", list); // for listAllBundleItem.html 第85行用
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/bundleitem/listAllBundleItem"; // 刪除完成後轉交listAllEmp.html
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
//	@ModelAttribute("deptMapData") //
//	protected Map<Integer, String> referenceMapData() {
//		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
//		map.put(10, "財務部");
//		map.put(20, "研發部");
//		map.put(30, "業務部");
//		map.put(40, "生管部");
//		return map;
//	}
	
	@GetMapping("listAllBundleItem")
	public String listAllBundleItem(Model model) {
	    List<BundleItemVO> list = bundleItemSvc.getAll();
	    model.addAttribute("bundleItemListData", list);
	    return "back-end/bundleitem/listAllBundleItem";
	}
	
	@GetMapping("select_page")
	public String select_page(Model model) {
		return "back-end/bundleitem/select_page";
	}
	
    @ModelAttribute("bundleItemListData")  // for select_page.html 第97 109行用 // for listAllEmp.html 第85行用
	protected List<BundleItemVO> referenceListData(Model model) {		
    	List<BundleItemVO> list = bundleItemSvc.getAll();
		return list;
	}
	
    @PostMapping("getOne_For_Display")
    public String getOneForDisplay(@RequestParam("bundleId") Integer bundleId, Model model) {
        BundleItemVO bundleItemVO = bundleItemSvc.getOneBundleItem(bundleId);
        model.addAttribute("bundleItemVO", bundleItemVO);
        return "back-end/bundleitem/listOneBundleItem";
    }
    
    @PostMapping("getOne_For_CampId")
    public String getOneForCampId(@RequestParam("campId") Integer bundleId, Model model) {
        BundleItemVO bundleItemVO = bundleItemSvc.getOneBundleItem(bundleId);
        model.addAttribute("bundleItemVO", bundleItemVO);
        return "back-end/bundleitem/listOneBundleItem";
    }
    
    @ModelAttribute("campListData")
    public List<CampVO> getCampList() {
        return campSvc.getAllCamp(); // 從 CampService 撈出所有營地
    }

	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(BundleItemVO bundleItemVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(bundleItemVO, "bundleItemVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
	


}