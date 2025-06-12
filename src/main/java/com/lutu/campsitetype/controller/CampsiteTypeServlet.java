package com.lutu.campsitetype.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.lutu.campsitetype.model.CampsiteTypeService;
import com.lutu.campsitetype.model.CampsiteTypeVO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CampsiteTypeServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("head:"+action);

		//查詢單筆資料
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("campsiteTypeId");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入房型編號");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/campsiteType/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			Integer campsiteTypeId = null;
			try {
				campsiteTypeId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("營地房型編號格式不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/campsiteType/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始查詢資料 *****************************************/
			CampsiteTypeService campsiteTypeSvc = new CampsiteTypeService();
			CampsiteTypeVO campsiteTypeVO = campsiteTypeSvc.getOneCampsiteType(campsiteTypeId);
			if (campsiteTypeVO == null) {
				errorMsgs.add("查無資料");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/campsiteType/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("campsiteTypeVO", campsiteTypeVO); // 資料庫取出的campsiteTypeVO物件,存入req
			String url = "/back-end/campsiteType/listOneCampsiteType.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllCampsiteType.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer campsiteTypeId = Integer.valueOf(req.getParameter("campsiteTypeId"));

			/*************************** 2.開始查詢資料 ****************************************/
			CampsiteTypeService campsiteTypeSvc = new CampsiteTypeService();
			CampsiteTypeVO campsiteTypeVO = campsiteTypeSvc.getOneCampsiteType(campsiteTypeId);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("campsiteTypeVO", campsiteTypeVO); // 資料庫取出的empVO物件,存入req
			String url = "/back-end/campsiteType/update_campsiteType_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
			successView.forward(req, res);
		}

		if ("update".equals(action)) { // 來自update_campsiteType_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

			Integer campsiteTypeId = Integer.valueOf(req.getParameter("campsiteTypeId").trim());
			Integer campId = Integer.valueOf(req.getParameter("campId").trim());			

			String campsiteName = req.getParameter("campsiteName");
			String campsiteNameReg = "^[\u4e00-\u9fa5a-zA-Z0-9_]{3,20}$";
			if (campsiteName == null || campsiteName.trim().length() == 0) {
				errorMsgs.add("營地房型名稱請勿空白");
			}else if(!campsiteName.trim().matches(campsiteNameReg)) { //以下練習正則(規)表示式(regular-expression)
				campsiteName = "";
				errorMsgs.add("營地房型名稱: 只能是中、英文字母、數字和_ , 且長度必需在3到20之間");
            }

			
			Integer campsitePeople = null;
	        try {     
	        	campsitePeople = Integer.valueOf(req.getParameter("campsitePeople").trim());
	        	if(campsitePeople <= 0) {
	        		errorMsgs.add("可入住人數須大於0人");
	        	}
	        	
	        }catch(NumberFormatException e) {
	        	campsitePeople = null;
	        	errorMsgs.add("請確認可入住人數輸入是否正確");
	        }


	        Byte campsiteNum = null;
	        try {
	        	campsiteNum = Byte.valueOf(req.getParameter("campsiteNum").trim());
	        	if(campsiteNum < 0) {
	        		errorMsgs.add("房間數量最少為0間");
	        	}
	        }catch(NumberFormatException e) {
	        	campsiteNum= null;
	        	errorMsgs.add("請確認房間數量輸入是否正確");
	        }
	        
	        Integer campsitePrice = null;
	        try {
	        	
	        	campsitePrice = Integer.valueOf(req.getParameter("campsitePrice").trim());
	        	if(campsitePrice <= 0) {
	        		errorMsgs.add("房型價格應為正整數");
	        	}
	        }catch(NumberFormatException e) {
	        	campsitePrice= null;
	        	errorMsgs.add("請確認房型價格輸入是否正確");
	        }
		
			
			byte[] dummyPic = "fake image bytes".getBytes(); // 模擬圖片資料
			byte[] campsitePic1 = dummyPic;
			byte[] campsitePic2 = dummyPic;
			byte[] campsitePic3 = dummyPic;
			byte[] campsitePic4 = dummyPic;
	
	
			// 僅放入需要更新的資料
			CampsiteTypeVO campsiteTypeVO = new CampsiteTypeVO();
			// 塞入固定資料(沒有被調整的)
			campsiteTypeVO.setCampsiteTypeId(campsiteTypeId);
			campsiteTypeVO.setCampId(campId);
			// 塞入剛剛update_camp_input.jsp的資料
			campsiteTypeVO.setCampsiteName(campsiteName);
			campsiteTypeVO.setCampsitePeople(campsitePeople);
			campsiteTypeVO.setCampsiteNum(campsiteNum);
			campsiteTypeVO.setCampsitePrice(campsitePrice);
			campsiteTypeVO.setCampsitePic1(dummyPic);
			campsiteTypeVO.setCampsitePic2(dummyPic);
			campsiteTypeVO.setCampsitePic3(dummyPic);
			campsiteTypeVO.setCampsitePic4(dummyPic);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("campsiteTypeVO", campsiteTypeVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/campsiteType/update_campsiteType_input.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/
			CampsiteTypeService campsiteTypeSvc = new CampsiteTypeService();
			campsiteTypeSvc.updateCampsiteType(campsiteTypeId, campId, campsiteName, campsitePeople, campsiteNum, campsitePrice, campsitePic1, campsitePic2, campsitePic3, campsitePic4);
			// 更新完後，再透過getOneCampsiteType來取得該筆campsiteTypeId最新的完整資料
			CampsiteTypeVO campsiteTypeVO2 = campsiteTypeSvc.getOneCampsiteType(campsiteTypeId);			
			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("campsiteTypeVO", campsiteTypeVO2);
			String url = "/back-end/campsiteType/listOneCampsiteType.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);
		}

		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
		
			Integer campId = null;
	        try {     
	        	campId = Integer.valueOf(req.getParameter("campId").trim());
	        	if(campId <= 1000) { // 營地房型編號從1000開始，1000以下都是有問題
	        		errorMsgs.add("營地房型編號從1000開始");
	        	}
	        	
	        }catch(NumberFormatException e) {
	        	campId = null;
	        	errorMsgs.add("請確認營地編號輸入是否正確");
	        }
			
		
			String campsiteName = req.getParameter("campsiteName");
			String campsiteNameReg = "^[\u4e00-\u9fa5a-zA-Z0-9_]{3,20}$";
			if (campsiteName == null || campsiteName.trim().length() == 0) {
				errorMsgs.add("營地房型名稱請勿空白");
			}else if(!campsiteName.trim().matches(campsiteNameReg)) { //以下練習正則(規)表示式(regular-expression)
				campsiteName = "";
				errorMsgs.add("營地房型名稱: 只能是中、英文字母、數字和_ , 且長度必需在3到20之間");
            }

			
			Integer campsitePeople = null;
	        try {     
	        	campsitePeople = Integer.valueOf(req.getParameter("campsitePeople").trim());
	        	if(campsitePeople <= 0) {
	        		errorMsgs.add("可入住人數須大於0人");
	        	}
	        	
	        }catch(NumberFormatException e) {
	        	campsitePeople = null;
	        	errorMsgs.add("可入住人數請勿空白");
	        }


	        Byte campsiteNum = null;
	        try {
	        	campsiteNum = Byte.valueOf(req.getParameter("campsiteNum").trim());
	        	if(campsiteNum < 0) {
	        		errorMsgs.add("房間數量最少為0間");
	        	}
	        }catch(NumberFormatException e) {
	        	campsiteNum= null;
	        	errorMsgs.add("房間數量請勿空白");
	        }
	        
	        Integer campsitePrice = null;
	        try {
	        	
	        	campsitePrice = Integer.valueOf(req.getParameter("campsitePrice").trim());
	        	if(campsitePrice <= 0) {
	        		errorMsgs.add("房型價格應為正整數");
	        	}
	        }catch(NumberFormatException e) {
	        	campsitePrice= null;
	        	errorMsgs.add("房型價格請勿空白");
	        }
		
			
			byte[] dummyPic = "fake image bytes".getBytes(); // 模擬圖片資料
			byte[] campsitePic1 = dummyPic;
			byte[] campsitePic2 = dummyPic;
			byte[] campsitePic3 = dummyPic;
			byte[] campsitePic4 = dummyPic;
	
	
			// 僅放入需要更新的資料
			CampsiteTypeVO campsiteTypeVO = new CampsiteTypeVO();

			// 塞入剛剛update_camp_input.jsp的資料
			campsiteTypeVO.setCampId(campId);
			campsiteTypeVO.setCampsiteName(campsiteName);
			campsiteTypeVO.setCampsitePeople(campsitePeople);
			campsiteTypeVO.setCampsiteNum(campsiteNum);
			campsiteTypeVO.setCampsitePrice(campsitePrice);
			campsiteTypeVO.setCampsitePic1(dummyPic);
			campsiteTypeVO.setCampsitePic2(dummyPic);
			campsiteTypeVO.setCampsitePic3(dummyPic);
			campsiteTypeVO.setCampsitePic4(dummyPic);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("campsiteTypeVO", campsiteTypeVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/campsiteType/addCampsiteType.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始新增資料 ***************************************/
			CampsiteTypeService campsiteTypeSvc = new CampsiteTypeService();
			campsiteTypeVO = campsiteTypeSvc.addCampsiteType(campId, campsiteName, campsitePeople, campsiteNum, campsitePrice, campsitePic1, campsitePic2, campsitePic3, campsitePic4);
			                                                 
			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/back-end/campsiteType/listAllCampsiteType.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);
		}

		if ("delete".equals(action)) { // 來自listAllCampsiteType.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			Integer campsiteTypeId = Integer.valueOf(req.getParameter("campsiteTypeId"));

			/*************************** 2.開始刪除資料 ***************************************/
			CampsiteTypeService campsiteTypeSvc = new CampsiteTypeService();
			campsiteTypeSvc.deleteCampsiteType(campsiteTypeId);

			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
			String url = "/back-end/campsiteType/listAllCampsiteType.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
		}
	}
}
