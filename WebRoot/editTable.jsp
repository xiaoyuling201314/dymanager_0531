<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'editTable.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=basePath %>dist/js/jquery.min.js"></script>
	<script language="JavaScript">
$(function(){
	$(".editableTable td").click(function(){
		var cell = $(this);
		if (cell.children("input").length > 0) {
			return false;
		}
		
		var input = $("<input/>");
		var originalValue = cell.html();
		input.attr("value", originalValue);
		cell.html("");
		input.width(cell.width());
		input.css({"border-width":"0","color":"blue","font-size":"16px"});
		input.appendTo(cell);
		input.trigger("focus").trigger("select");
		input.click(function(){
			return false;
		});
		input.keyup(function(event){
			var keycode = event.which;
			if (13 == keycode) {
				var str = $(this).attr("value");
				cell.html(str);
			}
			if (27 == keycode) {
				cell.html(originalValue);
			}
		});
		input.blur(function(){
			if ($(this).attr("value") == originalValue) {
				var str = $(this).attr("value");
				cell.html(str);
			}
		});
	});
});
</script>
<style>
.editableTable{
	border-collapse:collapse;
	width:200px;
}
.editableTable td{
	border:1px solid red;
	width:50%;
}
</style>
</head>
<body>
<table class="editableTable">
<tr>
<td>1</td>
<td>Sylar</td>
</tr>
<tr>
<td>2</td>
<td>Blare</td>
</tr>
<tr>
<td>3</td>
<td>Lina</td>
</tr>
<tr>
<td>4</td>
<td>于美娇</td>
</tr>
</table>
  </body>
</html>
