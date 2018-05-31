<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
  <%@ include file="/view/common/head.jsp" %>
  <title>仪器信息管理系统</title>
 <% request.setAttribute("nav", "reagent_home"); %>
</head>
<body>
<%@ include file="/view/common/top.jsp" %>
<%@ include file="/view/common/left.jsp" %>
<div class="main-content home">
  <div class="logo"></div>
</div>
<script src="dist/js/lib/require.min.js" data-main="dist/js/page/home.min.js"></script>
</body>
</html>
