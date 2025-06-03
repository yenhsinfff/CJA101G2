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

		//查詢單筆資料
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
//			String str = req.getParameter("campsiteTypeId");
//			if (str == null || (str.trim()).length() == 0) {
//				errorMsgs.add("請輸入房型編號");
//			}
//			// Send the use back to the form, if there were errors
//			if (!errorMsgs.isEmpty()) {
//				RequestDispatcher failureView = req.getRequestDispatcher("<%= request.getContextPath() %>/back-end/campsiteType/select_page.jsp");
//				failureView.forward(req, res);
//				return;// 程式中斷
//			}

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
			if (campId == null) {
				errorMsgs.add("請輸入營地編號!");
			}

			String campsiteName = req.getParameter("campsiteName").trim();
			if (campsiteName == null || campsiteName.trim().length() == 0) {
				errorMsgs.add("請輸入營地房型名稱!");
			}

			Integer campsitePeople = Integer.valueOf(req.getParameter("campsitePeople").trim());
			if (campsitePeople == null) {
				errorMsgs.add("請輸入可入住人數!");
			}


	        Byte campsiteNum = null;
	        try {
	        	campsiteNum = Byte.valueOf(req.getParameter("campsiteTypeNum").trim());
	        }catch(NumberFormatException e) {
	        	errorMsgs.add("請輸入房間數量!");
	        }

			Integer campsitePrice = Integer.valueOf(req.getParameter("campsiteTypePrice").trim());
			if (campsitePrice == null) {
				errorMsgs.add("請輸入房間價格!");
			}
			byte[] dummyPic = "fake image bytes".getBytes(); // 模擬圖片資料
			byte[] campsitePic1 = dummyPic;
			byte[] campsitePic2 = dummyPic;
			byte[] campsitePic3 = dummyPic;
			byte[] campsitePic4 = dummyPic;
	
	
			CampsiteTypeVO campsiteTypeVO = new CampsiteTypeVO();
			campsiteTypeVO.setCampsiteTypeId(campsiteTypeId);
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
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/campsiteType/update_campsiteType_input.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/
			CampsiteTypeService campsiteTypeSvc = new CampsiteTypeService();
			campsiteTypeVO = campsiteTypeSvc.updateCampsiteType(campsiteTypeId, campId, campsiteName, campsitePeople, campsiteNum, campsitePrice, campsitePic1, campsitePic2, campsitePic3, campsitePic4);
		
			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("campsiteTypeVO", campsiteTypeVO); // 資料庫update成功後,正確的的empVO物件,存入req
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
Integer campsiteTypeId = Integer.valueOf(req.getParameter("campsiteTypeId").trim());
			
			
			Integer campId = Integer.valueOf(req.getParameter("campId").trim());
			if (campId == null) {
				errorMsgs.add("請輸入營地編號!");
			}

			String campsiteName = req.getParameter("campsiteName").trim();
			if (campsiteName == null || campsiteName.trim().length() == 0) {
				errorMsgs.add("請輸入營地房型名稱!");
			}

			Integer campsitePeople = Integer.valueOf(req.getParameter("campsitePeople").trim());
			if (campsitePeople == null) {
				errorMsgs.add("請輸入可入住人數!");
			}


	        Byte campsiteNum = null;
	        try {
	        	campsiteNum = Byte.valueOf(req.getParameter("campsiteTypeNum").trim());
	        }catch(NumberFormatException e) {
	        	errorMsgs.add("請輸入房間數量!");
	        }

			Integer campsitePrice = Integer.valueOf(req.getParameter("campsiteTypePrice").trim());
			if (campsitePrice == null) {
				errorMsgs.add("請輸入房間價格!");
			}
			byte[] dummyPic = "fake image bytes".getBytes(); // 模擬圖片資料
			byte[] campsitePic1 = dummyPic;
			byte[] campsitePic2 = dummyPic;
			byte[] campsitePic3 = dummyPic;
			byte[] campsitePic4 = dummyPic;
	
	
			CampsiteTypeVO campsiteTypeVO = new CampsiteTypeVO();
			campsiteTypeVO.setCampsiteTypeId(campsiteTypeId);
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
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/campsiteType/update_campsiteType_input.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始新增資料 ***************************************/
			CampsiteTypeService campsiteTypeSvc = new CampsiteTypeService();
			campsiteTypeVO = campsiteTypeSvc.addCampsiteType(campsiteTypeId, campId, campsiteName, campsitePeople, campsiteNum, campsitePrice, campsitePic1, campsitePic2, campsitePic3, campsitePic4);

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/back-end/campsiteType/listAllcampsiteType.jsp";
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
