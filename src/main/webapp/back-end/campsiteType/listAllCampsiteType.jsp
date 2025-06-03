<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lutu.campsitetype.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
    CampsiteTypeService campsiteTypeSvc = new CampsiteTypeService();
    List<CampsiteTypeVO> list = campsiteTypeSvc.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>所有營地房型資料 - listAllCampsiteType.jsp</title>

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
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有營地房型資料 - listAllCampsiteType.jsp</h3>
		 <h4><a href="${pageContext.request.contextPath}/back-end/campsiteType/select_page.jsp"><img src="${pageContext.request.contextPath}/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>營地房型編號</th>
		<th>營地編號</th>
		<th>營地房型名稱</th>
		<th>可入住人數</th>
		<th>房間數量</th>
		<th>房間價格</th>
<!-- 		<th>房間照片1</th> -->
<!-- 		<th>房間照片2</th> -->
<!-- 		<th>房間照片3</th> -->
<!-- 		<th>房間照片4</th> -->
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="campsiteTypeVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
			<td>${campsiteTypeVO.campsiteTypeId}</td>
			<td>${campsiteTypeVO.campId}</td>
			<td>${campsiteTypeVO.campsiteName}</td>
			<td>${campsiteTypeVO.campsitePeople}</td>
			<td>${campsiteTypeVO.campsiteNum}</td>
			<td>${campsiteTypeVO.campsitePrice}</td> 
<%-- 			<td>${campsiteTypeVO.campsitePic1}</td> --%>
<%-- 			<td>${campsiteTypeVO.campsitePic2}</td> --%>
<%-- 			<td>${campsiteTypeVO.campsitePic3}</td> --%>
<%-- 			<td>${campsiteTypeVO.campsitePic4}</td> --%>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/campsiteType/campsiteType.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="campsiteTypeId"  value="${campsiteTypeVO.campsiteTypeId}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/campsiteType/campsiteType.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="campsiteTypeId"  value="${campsiteTypeVO.campsiteTypeId}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>