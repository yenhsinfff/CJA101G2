package com.lutu.article_type.controller;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import com.lutu.article_type.model.ArticleTypeService;
import com.lutu.article_type.model.ArticleTypeVO;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ArticleTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		
		
		// 查詢單筆資料====================================================================================

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to (將此集合儲存在請求範圍內，以備不時之需)
			// send the ErrorPage view. (傳送 ErrorPage 視圖)
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("acTypeId");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入文章類別編號");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
// /emp/select_page.jsp
				RequestDispatcher failureView = req.getRequestDispatcher("/article_type/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			Integer getAcTypeId = null;
			try {
				getAcTypeId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("文章類別編號格式不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
// /emp/select_page.jsp
				RequestDispatcher failureView = req.getRequestDispatcher("/article_type/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷

			}

			/*************************** 2.開始查詢資料 *****************************************/
			ArticleTypeService articleTypeSvc = new ArticleTypeService();
			ArticleTypeVO articleTypeVO = articleTypeSvc.getOneArticleType(getAcTypeId);

			if (articleTypeVO == null) {
				errorMsgs.add("查無資料");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
// /emp/select_page.jsp
				RequestDispatcher failureView = req.getRequestDispatcher("/article_type/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("articleTypeVO", articleTypeVO); // 資料庫取出的articleTypeVO物件,存入req
// /emp/listOneEmp.jsp		
			String url = "/article_type/listOneArticleType.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);

		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer acTypeId = Integer.valueOf(req.getParameter("acTypeId"));

			/*************************** 2.開始查詢資料 ****************************************/
			ArticleTypeService acTypeIdSvc = new ArticleTypeService(); // 這邊會new一個service的檔案，將VO跟DAO做結合，可以參考line群組秉豐的圖片分享
			ArticleTypeVO articleTypeVO = acTypeIdSvc.getOneArticleType(acTypeId);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("articleTypeVO", articleTypeVO); // 資料庫取出的empVO物件,存入req
// /emp/update_emp_input.jsp
			String url = "/article_type/update_ArticleType_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
			successView.forward(req, res);
		}
		
		
		
		
		// 修改資料=======================================================================================================================

		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer acTypeId = Integer.valueOf(req.getParameter("acTypeId").trim());

			// 如果需要限制輸入範圍可以參考以下程式碼

//			String ename = req.getParameter("ename");
//			String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";

//			if (ename == null || ename.trim().length() == 0) {
//				errorMsgs.add("員工姓名: 請勿空白");
//			} else if (!ename.trim().matches(enameReg)) { // 以下練習正則(規)表示式(regular-expression)
//				errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
//			}

			// 填入文章類別編號
			String acTypeKind = req.getParameter("acTypeKind").trim();
			
			// 填入文章類別名稱
			String acTypeKindReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,15}$";

			if (acTypeKind == null || acTypeKind.trim().length() == 0) {
				errorMsgs.add("文章類別名稱請勿空白");
			} else if (!acTypeKind.trim().matches(acTypeKindReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("文章類別名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到15之間");
			}
			// 填入文章類別敘述
			String acTypeText = req.getParameter("acTypeText").trim();

			// 僅放入需要更新的資料
			ArticleTypeVO articleTypeVO = new ArticleTypeVO();

			// 塞入固定資料(沒有被調整的)
			articleTypeVO.setAcTypeId(acTypeId);

			// 塞入剛剛update_camp_input.jsp的資料
			articleTypeVO.setAcTypeKind(acTypeKind);
			articleTypeVO.setAcTypeText(acTypeText);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("articleTypeVO", articleTypeVO); // 含有輸入格式錯誤的empVO物件,也存入req
// /emp/update_emp_input.jsp
				RequestDispatcher failureView = req.getRequestDispatcher("/article_type/update_ArticleType_input.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/

			ArticleTypeService articleTypeSvc = new ArticleTypeService();

			// 開始進行部分欄位的更新(articleTypeVO內只包含部分欄位的資料)
//-----------------------------------------------------------------------------------

			// 如果在ArticleTypeService的updataArticleType方法()是例如：
//			public EmpVO updateEmp(Integer empno, String ename, String job,
//					java.sql.Date hiredate, Double sal, Double comm, Integer deptno)
//			的話用以下方法填入

//			articleTypeVO = articleTypeSvc.updataArticleType(acTypeId,acTypeKind,acTypeText);
//-----------------------------------------------------------------------------------
			// 如果ArticleTypeService的updataArticleType方法()是public ArticleTypeVO
			// updataArticleType(ArticleTypeVO articleTypeVO)
			// 的話用articleTypeVO物件填入

			articleTypeSvc.updateArticleType(articleTypeVO);
//			articleTypeVO = articleTypeSvc.updateArticleType(acTypeId, acTypeKind, acTypeText);

			// 更新完後，再透過getOneCamp來取得該筆campId最新的完整資料
			ArticleTypeVO articleTypeVO2 = articleTypeSvc.getOneArticleType(acTypeId);

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("articleTypeVO", articleTypeVO2); // 資料庫update成功後,正確的的empVO物件,存入req
// /emp/listOneEmp.jsp
			String url = "/article_type/listOneArticleType.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);
		}

		
		
		
		// 新增資料============================================================================================================
		if ("insert".equals(action))

		{ // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			String acTypeKind = req.getParameter("acTypeKind").trim();
			String acTypeKindReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,15}$";

			// 填入文章類別名稱
			if (acTypeKind == null || acTypeKind.trim().length() == 0) {
				errorMsgs.add("文章類別名稱請勿空白");
			} else if (!acTypeKind.trim().matches(acTypeKindReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("文章類別名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到15之間");
			}
			
			// 填入文章類別敘述
			String acTypeText = req.getParameter("acTypeText").trim();

			// 僅放入需要更新的資料
			ArticleTypeVO articleTypeVO = new ArticleTypeVO();
			
			// 塞入剛剛update_camp_input.jsp的資料
			articleTypeVO.setAcTypeKind(acTypeKind);
			articleTypeVO.setAcTypeText(acTypeText);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("articleTypeVO", articleTypeVO); // 含有輸入格式錯誤的empVO物件,也存入req
// /emp/addEmp.jsp
				RequestDispatcher failureView = req.getRequestDispatcher("/article_type/addArticleType.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/
			ArticleTypeService articleTypeSvc = new ArticleTypeService();
			articleTypeSvc.addArticleType(articleTypeVO);
			
			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
// /emp/listAllEmp.jsp
			String url = "/article_type/listAllArticleType.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);
		}
		
		
		
		
		
		// 刪除資料 ============================================================================================================
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			Integer acTypeId = Integer.valueOf(req.getParameter("acTypeId"));

			/*************************** 2.開始刪除資料 ***************************************/
			ArticleTypeService articleTypeSvc = new ArticleTypeService();
			articleTypeSvc.deleteArticleType(acTypeId);

			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
// /emp/listAllEmp.jsp
			String url = "/article_type/listAllArticleType.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
		}

	}
}
