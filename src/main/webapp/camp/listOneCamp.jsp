<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lutu.camp.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  CampVO campVO = (CampVO) request.getAttribute("campVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>

<html>
<head>
<title>營地資料 - listOneCamp.jsp</title>

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
		 <h3>營地資料 - listOneCamp.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="image/logo.svg" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>營地編號</th>
		<th>營地名稱</th>
		<th>營地說明</th>
		<th>所在縣市</th>
		<th>所在區域</th>
		<th>詳細地址</th>
	</tr>
	<tr>
		<td>${campVO.campId}</td>
		<td>${campVO.campName}</td>
		<td>${campVO.campContent}</td>
		<td>${campVO.campCity}</td>
		<td>${campVO.campDist}</td>
		<td>${campVO.campAddr}</td> 
	</tr>
</table>

</body>
</html>