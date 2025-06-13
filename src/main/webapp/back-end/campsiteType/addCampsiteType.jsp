<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.lutu.campsitetype.model.*"%>

<% //見com.emp.controller.EmpServlet.java第238行存入req的empVO物件 (此為輸入格式有錯誤時的empVO物件)
   CampsiteTypeVO campsiteTypeVO = (CampsiteTypeVO) request.getAttribute("campsiteTypeVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>營地房型資料新增 - addCampsiteType.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr>
	<td><h3>營地房型資料新增 - addCampsiteType.jsp</h3></td>
	</tr>
	<tr><td>
		 <h4><a href="${pageContext.request.contextPath}/back-end/campsiteType/select_page.jsp"><img src="${pageContext.request.contextPath}/images/tomcat.png" width="80" height="80" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料新增:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/campsiteType/campsiteType.do" name="form1">
<table>	

  
<!-- 	<tr> -->
<!-- 		<td>營地編號:</td> -->
<%-- 		<td><input type="TEXT" name="campId" value="<%= (campsiteTypeVO==null || campsiteTypeVO.getCampId() == null )? "" : campsiteTypeVO.getCampId()%>" size="45"/></td> --%>
<!-- 	</tr> -->

	<jsp:useBean id="campSvc" scope="page" class="com.lutu.camp.model.CampService" />
	<tr>
		<td>營地:<font color=red><b>*</b></font></td>
		<td><select size="1" name="campId">
			<c:forEach var="campVO" items="${campSvc.all}">
				<option value="${campVO.campId}" ${(campsiteTypeVO.campId==campVO.campId)? 'selected':'' } >${campVO.campName}
			</c:forEach>
		</select></td>
	</tr>
	
	<tr>
		<td>營地房型名稱:</td>
		<td><input type="TEXT" name="campsiteName" value="<%= (campsiteTypeVO==null || campsiteTypeVO.getCampsiteName() == null)? "" : campsiteTypeVO.getCampsiteName()%>" size="45"/></td>
	</tr>
	<tr>
		<td>可入住人數:</td>
		<td><input  type="TEXT" name="campsitePeople" value="<%= (campsiteTypeVO==null || campsiteTypeVO.getCampsitePeople() == null)? "" : campsiteTypeVO.getCampsitePeople()%>" size="45"/></td>
	</tr>
	<tr>
		<td>房間數量:</td>
		<td><input  type="TEXT" name="campsiteNum" value="<%= (campsiteTypeVO==null || campsiteTypeVO.getCampsiteNum() == null)? "" : campsiteTypeVO.getCampsiteNum()%>" size="45"/></td>
	</tr>
	<tr>
		<td>房間價格:</td>
		<td><input  type="TEXT" name="campsitePrice" value="<%= (campsiteTypeVO==null || campsiteTypeVO.getCampsitePrice() == null)? "" : campsiteTypeVO.getCampsitePrice()%>" size="45"/></td>
	</tr>
	

</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>

</body>


</html>