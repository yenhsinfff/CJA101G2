<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lutu.campsitetype.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  CampsiteTypeVO campsiteTypeVO = (CampsiteTypeVO) request.getAttribute("campsiteTypeVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>


<html>
<head>
<title>營地房型資料 - listOneCampsiteType.jsp</title>

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
	width: 600px;
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

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>營地房型資料 - listOneCampsiteType.jsp</h3>
		 <h4><a href="<%= request.getContextPath() %>/back-end/campsiteType/select_page.jsp"><img src="<%= request.getContextPath() %>/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
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
	</tr>
	<tr>
		<td><%=campsiteTypeVO.getCampsiteTypeId()%></td>
		<td><%=campsiteTypeVO.getCampId()%></td>
		<td><%=campsiteTypeVO.getCampsiteName()%></td>
		<td><%=campsiteTypeVO.getCampsitePeople()%></td>
		<td><%=campsiteTypeVO.getCampsiteNum()%></td>
		<td><%=campsiteTypeVO.getCampsitePrice()%></td>
<%-- 		<td><%=campsiteTypeVO.getCampsitePic1()%></td> --%>
<%-- 		<td><%=campsiteTypeVO.getCampsitePic2()%></td> --%>
<%-- 		<td><%=campsiteTypeVO.getCampsitePic3()%></td> --%>
<%-- 		<td><%=campsiteTypeVO.getCampsitePic4()%></td> --%>
	</tr>
</table>

</body>
</html>