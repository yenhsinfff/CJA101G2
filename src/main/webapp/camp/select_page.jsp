<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
<title>IBM Camp: Home</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
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

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>IBM Camp: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for IBM Camp: Home</p>

<h3>資料查詢:</h3>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='listAllCamp.jsp'>顯示所有營地</a><br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="camp.do" >
        <b>輸入營地編號 (如1001):</b>
        <input type="text" name="campid">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

  <jsp:useBean id="campSvc" scope="page" class="com.lutu.camp.model.CampService" />
   
  <li>
     <FORM METHOD="post" ACTION="camp.do" >
       <b>選擇營地編號:</b>
       <select size="1" name="campid">
         <c:forEach var="campVO" items="${campSvc.all}" > 
          <option value="${campVO.campId}">${campVO.campId}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="camp.do" >
       <b>選擇營地名稱:</b>
       <select size="1" name="campid">
         <c:forEach var="campVO" items="${campSvc.all}" > 
          <option value="${campVO.campId}">${campVO.campName}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li>
</ul>


<h3>營地管理</h3>

<ul>
  <li><a href='addCamp.jsp'>新增</a> 營地資料</li>
</ul>

</body>
</html>