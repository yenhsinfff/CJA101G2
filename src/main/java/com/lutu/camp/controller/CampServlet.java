package com.lutu.camp.controller;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import com.lutu.camp.model.*;

public class CampServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		// 查詢單筆資料
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("campid");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入營地編號");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/camp/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			Integer campId = null;
			try {
				campId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("營地編號格式不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/camp/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始查詢資料 *****************************************/
			CampService campSvc = new CampService();
			CampVO campVO = campSvc.getOneCamp(campId);
			
			if (campVO == null) {
				errorMsgs.add("查無資料");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/camp/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("campVO", campVO); // 資料庫取出的empVO物件,存入req
			String url = "/camp/listOneCamp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneCamp.jsp
			successView.forward(req, res);
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer campId = Integer.valueOf(req.getParameter("campId"));

			/*************************** 2.開始查詢資料 ****************************************/
			CampService campSvc = new CampService();   //這邊會new一個service的檔案，將VO跟DAO做結合，可以參考line群組秉豐的圖片分享
			CampVO campVO = campSvc.getOneCamp(campId);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("campVO", campVO); // 資料庫取出的empVO物件,存入req
			String url = "/camp/update_camp_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
			successView.forward(req, res);
		}

		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer campId = Integer.valueOf(req.getParameter("campId").trim());
			Integer ownerId = Integer.valueOf(req.getParameter("campOwnerId").trim());

			String campName = req.getParameter("campName").trim();
			if (campName == null || campName.trim().length() == 0) {
				errorMsgs.add("營地名稱請勿空白");
			}

			String campContent = req.getParameter("campContent").trim();
			if (campContent == null || campContent.trim().length() == 0) {
				errorMsgs.add("營地說明請勿空白");
			}

			String campCity = req.getParameter("campCity").trim();
			if (campCity == null || campCity.trim().length() == 0) {
				errorMsgs.add("營地地址_縣市請勿空白");
			}

			String campDist = req.getParameter("campDist").trim();
			if (campDist == null || campDist.trim().length() == 0) {
				errorMsgs.add("營地地址_鄉鎮區請勿空白");
			}

			String campAddr = req.getParameter("campAddr").trim();
			if (campAddr == null || campAddr.trim().length() == 0) {
				errorMsgs.add("營地地址_詳細地址請勿空白");
			}

			byte campReleaseStatus = Byte.parseByte(req.getParameter("campReleaseStatus").trim());

			// 僅放入需要更新的資料
			CampVO campVO = new CampVO();
			// 塞入固定資料(沒有被調整的)
			campVO.setCampId(campId);
			campVO.setOwnerId(ownerId);
			// 塞入剛剛update_camp_input.jsp的資料
			campVO.setCampName(campName);
			campVO.setCampContent(campContent);
			campVO.setCampCity(campCity);
			campVO.setCampDist(campDist);
			campVO.setCampAddr(campAddr);
			campVO.setCampReleaseStatus(campReleaseStatus);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("campVO", campVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/camp/update_camp_input.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/

			CampService campSvc = new CampService();
			// 開始進行部分欄位的更新(campVO內只包含部分欄位的資料)
			campSvc.updateCamp(campVO);
			// 更新完後，再透過getOneCamp來取得該筆campId最新的完整資料
			CampVO campVO2 = campSvc.getOneCamp(campId);
			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("campVO", campVO2); // 資料庫update成功後,正確的的empVO物件,存入req
			String url = "/camp/listOneCamp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneCamp.jsp
			successView.forward(req, res);
		}

		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

			Integer ownerId = Integer.valueOf(req.getParameter("ownerId").trim());
			// 營地主編號從2000開始，2000以下都是有問題
			if (ownerId == null || ownerId < 2000) {
				errorMsgs.add("請確認營地主編號輸入是否正確");
			}
			String campName = req.getParameter("campName").trim();
			if (campName == null || campName.trim().length() == 0) {
				errorMsgs.add("營地名稱請勿空白");
			}

			String campContent = req.getParameter("campContent").trim();
			if (campContent == null || campContent.trim().length() == 0) {
				errorMsgs.add("營地說明請勿空白");
			}

			String campCity = req.getParameter("campCity").trim();
			if (campCity == null || campCity.trim().length() == 0) {
				errorMsgs.add("營地地址_縣市請勿空白");
			}

			String campDist = req.getParameter("campDist").trim();
			if (campDist == null || campDist.trim().length() == 0) {
				errorMsgs.add("營地地址_鄉鎮區請勿空白");
			}

			String campAddr = req.getParameter("campAddr").trim();
			if (campAddr == null || campAddr.trim().length() == 0) {
				errorMsgs.add("營地地址_詳細地址請勿空白");
			}

			byte campReleaseStatus = Byte.parseByte(req.getParameter("campReleaseStatus").trim());
			byte[] dummyPic = "fake image bytes".getBytes(); // 模擬圖片資料
			// 僅放入需要更新的資料
			CampVO campVO = new CampVO();
			// 塞入固定資料(沒有被調整的)
			campVO.setOwnerId(ownerId);
			// 塞入剛剛update_camp_input.jsp的資料
			campVO.setCampName(campName);
			campVO.setCampContent(campContent);
			campVO.setCampCity(campCity);
			campVO.setCampDist(campDist);
			campVO.setCampAddr(campAddr);
			campVO.setCampReleaseStatus(campReleaseStatus);
			campVO.setCampPic1(dummyPic);
			campVO.setCampPic2(dummyPic);
			campVO.setCampPic3(null);
			campVO.setCampPic4(null);
			campVO.setCampCommentNumberCount(0);
			campVO.setCampCommentSumScore(0);
			campVO.setCampRegDate(Date.valueOf(LocalDate.now()));

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("campVO", campVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/camp/addCamp.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/
			CampService campSvc = new CampService();
			campVO = campSvc.addCamp(campVO);

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/camp/listAllCamp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);
		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			Integer campId = Integer.valueOf(req.getParameter("campId"));

			/*************************** 2.開始刪除資料 ***************************************/
			CampService campSvc = new CampService();
			campSvc.deleteCamp(campId);

			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
			String url = "/camp/listAllCamp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
		}
	}
}
