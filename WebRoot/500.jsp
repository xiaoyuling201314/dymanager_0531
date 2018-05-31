<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>500服务器内部错误~</title>
<link rel="stylesheet" type="text/css" href="../css/errorcss/main.css">
<link rel="stylesheet" type="text/css" href="css/errorcss/main.css">
<!--[if lt IE 9]>
  <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
</head>
<body>
<div id="wrapper"><a class="logo" href="/"></a>
  <div id="main">
    <header id="header">
      <h1><span class="icon">!</span>500<span class="sub">SERVER ERROR</span></h1>
    </header>
    <div id="content">
      <h2>抱歉,服务器开小差了~~~</h2>
      <p>当您看到这个页面,表示服务器开小差了,这个错误是由服务器引起的,请您尝试<a class="button right" href="#" onClick="history.go(-1);return true;">返回前一页</a>继续浏览其他内容！</p>
      <div class="utilities">
        <a class="button right" href="#" onClick="history.go(-1);return true;">返回...</a><a class="button right" href="<%=basePath %>view/index.jsp">返回首页</a>
        <div class="clear"></div>
      </div>
    </div>
    
  </div>
</div>
</html>