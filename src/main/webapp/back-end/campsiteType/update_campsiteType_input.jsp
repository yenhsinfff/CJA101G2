<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.lutu.campsitetype.model.*"%>

<% //見com.emp.controller.EmpServlet.java第163行存入req的empVO物件 (此為從資料庫取出的empVO, 也可以是輸入格式有錯誤時的empVO物件)
   CampsiteTypeVO campsiteTypeVO = (CampsiteTypeVO) request.getAttribute("campsiteTypeVO");
%>
--<%= campsiteTypeVO==null %>--${campsiteTypeVO.campId}-- <!-- for line 100 -->
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>營地房型資料修改 - update_campsiteType_input.jsp</title>

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
	width: 480px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    width: 110px;
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>營地房型資料修改 - update_campsiteType_input.jsp</h3>
		 <h4><a href="${pageContext.request.contextPath}/back-end/campsiteType/select_page.jsp"><img src="${pageContext.request.contextPath}/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料修改:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="campsiteType.do" name="form1">
<table>
	<tr>
		<td>營地房型編號:</td>
		<td><%=campsiteTypeVO.getCampsiteTypeId()%></td>
	</tr>
	
	<tr>
		<td>營地編號:</td>
		<td><%=campsiteTypeVO.getCampId()%></td>
	</tr>
	
	<tr>
		<td>營地房型名稱:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="campsiteName" value="<%=(campsiteTypeVO.getCampsiteName()==null)?"":campsiteTypeVO.getCampsiteName()%>" size="45"/></td>
	</tr>
	<tr>
		<td>可入住人數:<font color=red><b>*</b></font></td>
		<td><input  type="TEXT" name="campsitePeople" value="<%=(campsiteTypeVO.getCampsitePeople()==null)?"":campsiteTypeVO.getCampsitePeople()%>" size="45"/></td>
	</tr>
	<tr>
		<td>房間數量:<font color=red><b>*</b></font></td>
		<td><input  type="TEXT" name="campsiteNum" value="<%=(campsiteTypeVO.getCampsiteNum()==null)?"":campsiteTypeVO.getCampsiteNum()%>" size="45"/></td>
	</tr>
	<tr>
		<td>房間價格:<font color=red><b>*</b></font></td>
		<td><input  type="TEXT" name="campsitePrice" value="<%=(campsiteTypeVO.getCampsitePrice()==null)?"":campsiteTypeVO.getCampsitePrice()%>" size="45"/></td>
	</tr>


</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="campsiteTypeId" value="<%=campsiteTypeVO.getCampsiteTypeId()%>">
<input type="hidden" name="campId" value="<%=campsiteTypeVO.getCampId()%>">
<input type="submit" value="送出修改"></FORM>
</body>


</html>