<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'TestDisplayState.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="dist/js/page/jquery.min.js"></script>
	<script type="text/javascript"> 
		$(document).ready(function(){
		  $(".flip").mouseover(function(){
		    $(this).next("div").slideDown(500);
		  });
		  $(".content").mouseleave(function(){
		    $(this).children("div").slideUp(500);
		  });
		});
</script>
   
	<style type="text/css"> 
		div.panel,p.flip
		{
		  
		margin:0px;
		padding:5px;
		text-align:center;
		
		border:solid 1px #c3c3c3;
		}
		div.panel
		{
		  
		height:120px;
		display:none;
		}
	</style>
   
<body>
 <div class="content" style="float:left;display:block;width:300px;">
<p class="flip">滑过这里</p>
<div class="panel">
<p>11111111111111111</p>
<p>2222222222222222222222</p>
</div>
 </div>
<div class="content" style="float:left;display:block;width:300px;">
<p class="flip">滑过这里</p>
<div class="panel">
<p>11111111111111111</p>
<p>2222222222222222222222</p>
</div>
 </div>
</body>

</html>
