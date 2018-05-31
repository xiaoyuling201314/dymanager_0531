<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
      欢迎<c:out value="${userName}"></c:out>登录系统
    <a href="instrumentService/queryInstrument.do">查询仪器及关联的资质证书</a>
    
    
    <a href="certificate/queryCertificate.do">资质证书及证书类型关联</a>
    <a href="certificate/queryCertificate.do">资质证书及证书类型关联2</a>
    
<!--   <a href="materielDrawService/downloadDrawing.do?fileName=123.xls" >图纸下载</a>
      <a href="certificateService/addCert.do?file=GZDY-null.xls" >资质下载</a>
     <a href="materielService/importMateriel.do?fileName=123.xls" >图纸下载</a>
     <form action="materielService/importMateriel.do" method="post" enctype="multipart/form-data">
     <input type="file" name="myFile" />
     <input type="submit" value="提交"/>
     </form> -->
     
        <a href="certificateService/downloadCert.do?id=1" >资质下载</a>
      <form action="certificateService/addCert.do" method="post" enctype="multipart/form-data">
     <input type="file" name="myFile" />
     <input type="submit" value="上传资质"/>
     </form>
  </body>
</html>
