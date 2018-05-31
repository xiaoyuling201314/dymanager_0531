<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML >
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'message.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-easyui-1.5/jquery.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
	td{
	padding: 8px;
	}
	.even{background:gray;} 
	.odd{background:red;} 
	.selected{background:pink;}
	</style>
	<script type="text/javascript"> 
$(function(){ 
//$("tr:odd").addClass("odd"); 
$("tr:even").addClass("even"); 
}); 
</script> 
  </head>
  
  <body>
   12343241324<input type="text"  name="userName" value=""/>
   <table class="gridtable" border="1"  align="center" style="border-style: solid;border-width: 1px;border-collapse: collapse;">
     <tr >
       <td>用户名</td>
        <td>邮箱</td>
         <td>部门</td>
     </tr>
     <!-- <c:forEach items="${listData}" var="sysUser">
    <tr>
       <td>${sysUser.userName}</td>
        <td>${sysUser.email}</td>
         <td>${sysUser.department}</td>
     </tr>
   </c:forEach> -->
   </table>
   
  </body>
</html>
