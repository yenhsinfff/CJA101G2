<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>lutu CampsiteType: Home</title>


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
   <tr><td><h3>lutu CampsiteType: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for lutu CampsiteType: Home</p>

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
  <li><a href='listAllCampsiteType.jsp'>List</a> all CampsiteTypes.  <br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="campsiteType.do" >
        <b>輸入營地房型編號 (如2001):</b>
        <input type="text" name="campsiteTypeId">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

  <jsp:useBean id="campsiteTypeSvc" scope="page" class="com.lutu.campsitetype.model.CampsiteTypeService" />
   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/campsiteType/campsiteType.do" >
       <b>選擇營地房型編號3:</b>
       <select size="1" name="campsiteTypeId">
         <c:forEach var="campsiteTypeVO" items="${campsiteTypeSvc.all}" > 
          <option value="${campsiteTypeVO.campsiteTypeId}">${campsiteTypeVO.campsiteTypeId}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/campsiteType/campsiteType.do" >
       <b>選擇營地房型名稱:</b>
       <select size="1" name="campsiteTypeName">
         <c:forEach var="campsiteTypeVO" items="${campsiteTypeSvc.all}" > 
          <option value="${campsiteTypeVO.campsiteTypeId}">${campsiteTypeVO.campsiteTypeId}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li>
</ul>


<h3>營地房型管理</h3>

<ul>
  <li><a href='addCampsiteType.jsp'>Add</a> a new CampsiteType.</li>
</ul>

</body>
</html>