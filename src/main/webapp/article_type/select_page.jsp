<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>IBM ArticleType: Home</title>

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
   <tr><td><h3>IBM ArticleType: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for IBM ArticleType: Home</p>

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
  <li><a href='listAllArticleType.jsp'>List</a> 顯示所有文章類別  <br><br></li>
  
  
  <li>
 
    <FORM METHOD="post" ACTION="articleType.do" >
        <b>輸入文章類別編號 (如30001):</b>
        <input type="text" name="acTypeId">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

  <jsp:useBean id="ArticleTypeService" scope="page" class="com.lutu.article_type.model.ArticleTypeService" />
   
  <li>
     <FORM METHOD="post" ACTION="articleType.do" >
       <b>選擇文章類別編號:</b>
       <select size="1" name="acTypeId">
         <c:forEach var="ArticleTypeVO" items="${ArticleTypeService.all}" > 
          <option value="${ArticleTypeVO.acTypeId}">${ArticleTypeVO.acTypeId}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="articleType.do" >
       <b>選擇文章類別名稱:</b>
       <select size="1" name="acTypeId">
         <c:forEach var="ArticleTypeVO" items="${ArticleTypeService.all}" > 
          <option value="${ArticleTypeVO.acTypeId}">${ArticleTypeVO.acTypeKind}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li>
</ul>


<h3>文章類別管理</h3>

<ul>
  <li><a href='addArticleType.jsp'>新增</a> 文章類別資料</li>
</ul>

</body>
</html>