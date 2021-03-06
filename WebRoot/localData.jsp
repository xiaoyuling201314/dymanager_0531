<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML >
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'localData.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="dist/js/jquery.min.js"></script>
	<style type="text/css">
textarea {
width: 300px;
height: 300px;
}
.button {
width: 100px;
}
</style>
<script type="text/javascript">
//使用HTML5 Web存储的localStorage和sessionStorage方式进行Web页面数据本地存储。
//页面参考如下图，能将页面上的数据进行本地存储。并能读取存储的数据显示在页面上。
function saveSession() {
var t1 = document.getElementById("t1");
var t2 = document.getElementById("t2");
var mydata = t2.value;
var oStorage = window.sessionStorage;
oStorage.mydata = mydata;
t1.value += "sessionStorage保存mydata:" + mydata + "\n";
}
function readSession() {
var t1 = document.getElementById("t1");
var oStorage = window.sessionStorage;
var mydata = "不存在";
if (oStorage.mydata) {
mydata = oStorage.mydata;
}
t1.value += "sessionStorage读取mydata:" + mydata + "\n";
}
function cleanSession() {
var t1 = document.getElementById("t1");
var oStorage = window.sessionStorage;
var mydata = "不存在";
if (oStorage.mydata) {
mydata = oStorage.mydata;
}
oStorage.removeItem("mydata");
t1.value += "sessionStorage清除mydata:" + mydata + "\n";
}
function saveStorage() {
var t1 = document.getElementById("t1");
var t2 = document.getElementById("t2");
var mydata = t2.value;
var oStorage = window.localStorage;
oStorage.mydata = mydata;
t1.value += "localStorage保存mydata:" + mydata + "\n";
}
function readStorage() {
var t1 = document.getElementById("t1");
var oStorage = window.localStorage;
var mydata = "不存在";
if (oStorage.mydata) {
mydata = oStorage.mydata;
}
t1.value += "localStorage读取mydata:" + mydata + "\n";
}
function cleanStorage() {
var t1 = document.getElementById("t1");
var oStorage = window.localStorage;
var mydata = "不存在";
if (oStorage.mydata) {
mydata = oStorage.mydata;
}
oStorage.removeItem("mydata");
t1.value += "localStorage清除mydata:" + mydata + "\n";
}
</script>
  </head>
  
  <body>
   <div>
<textarea id="t1"></textarea>
<label>需要保存的数据: </label>
<input type="text" id="t2" />
<input type="button" class="button" onclick="saveSession()" name="b1" value="session保存" />
<input type="button" class="button" onclick="readSession()" value="session读取" />
<input type="button" class="button" onclick="cleanSession()" value="session清除" />
<input type="button" class="button" onclick="saveStorage()" value="local保存" />
<input type="button" class="button" onclick="readStorage()" value="local读取" />
<input type="button" class="button" onclick="cleanStorage()" value="local清除" />
</div>
  </body>
</html>
