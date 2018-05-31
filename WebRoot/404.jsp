<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>404 - 对不起，您查找的页面不存在！- JS代码站</title>
<link rel="stylesheet" type="text/css" href="../css/errorcss/main.css">
<!--[if lt IE 9]>
  <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
</head>
<body>
<div id="wrapper"><a class="logo" href="/"></a>
  <div id="main">
    <header id="header">
      <h1><span class="icon">!</span>404<span class="sub">page not found</span></h1>
    </header>
    <div id="content">
      <h2>您打开的这个的页面不存在！</h2>
      <p>当您看到这个页面,表示您的访问出错,这个错误是您打开的页面不存在,请确认您输入的地址是正确的,如果是在本站点击后出现这个页面,请联系管理员进行处理!</p>
      <div class="utilities">
       
        <a class="button right" href="#" onClick="history.go(-1);return true;">返回...</a><a class="button right" href="<%=basePath %>view/index.jsp">返回首页</a>
        <div class="clear"></div>
      </div>
    </div>
   
  </div>
</div>
</html>