<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'deleteJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="dist/js/jquery.min.js"></script>
	   <style type="text/css">
.divX
{
    z-index:100;
    border-style:solid;
    border-width:1px;
    border-color:#FF0000;
    -moz-border-radius:20px;
    -webkit-border-radius:20px;
    background-color:#ffffff;
    line-height:10px;
    text-align:center;
    font-weight:bold;
    cursor:pointer;
    font-size:10px;
    color:red;
    display: none;
}
</style>
</head>
<body>
<span style="position: relative;">
   <img id="a1IMG" alt="a1" src="dist/img/login_title.png" onclick="previewPhoto('a1');" onmouseover="showDeleteDiv('a1');" onmouseout="imgOnmouseout(),hideDeleteDiv('a1');"
style="width:50px;height:50px;padding-left:10px;"/>
</span>

<span style="position: relative;">
<img id="a2IMG" alt="a2" src="dist/img/login_bg.png" onmouseover="showDeleteDiv('a2');" onmouseout="imgOnmouseout(),hideDeleteDiv('a2');"
style="width:50px;height:50px;padding-left:10px;"/>
</span>

<span style="position: relative;">
<img id="a3IMG" alt="a3" src="dist/img/login_logo.png"  onmouseover="showDeleteDiv('a3');" onmouseout="imgOnmouseout(),hideDeleteDiv('a3');"
style="width:50px;height:50px;padding-left:10px;"/>
</span>

<script type="text/javascript">
var imgFlag = false; //鼠标放在图片上标记
var divFlag = false; //鼠标放在删除层上标记

function showDeleteDiv(resourceCode){
    imgFlag = true;
    $("#"+resourceCode+"DIV").css("display","block");
};

function hideDeleteDiv(resourceCode){
    if(!imgFlag && !divFlag){
        $("#"+resourceCode+"DIV").css("display","none");  
    }
};

function imgOnmouseout(){
    imgFlag=false;
};

function divOnmouseover(resourceCode){
    divFlag = true;
    showDeleteDiv(resourceCode);
};

function divOnmouseout(resourceCode){
    divFlag = false;
    if(imgFlag){
        showDeleteDiv(resourceCode);
    }else{
        hideDeleteDiv(resourceCode);
    }
};


function removeGroupNotActivitySharingPhotoByResourceCode(resourceCode) {
    alert("删除图片"+resourceCode);
    $("#"+resourceCode+"IMG").parent().detach();
}

</script>
<script type="text/javascript">
$(document).ready(function(){
    $("img").each(function(i){
        var divObj=$("<div onclick=removeGroupNotActivitySharingPhotoByResourceCode('"+$(this).attr('alt')+"'); onmouseover=divOnmouseover('"+$(this).attr('alt')+"'); onmouseout=divOnmouseout('"+$(this).attr('alt')+"');><i class='iconfont icon-delete'></i></div>");
        divObj.addClass("divX");
        divObj.attr("id",$(this).attr("alt")+"DIV");
        divObj.attr("title","删除图片"+$(this).attr('alt'));
        divObj.css({position:"absolute", left: $(this).position().left+45, top:$(this).position().top-10});
        $(this).parent().append(divObj);
    });
 });
</script>
</body>
</html>
