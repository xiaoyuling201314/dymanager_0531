<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'deletePicJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=basePath %>dist/js/jquery.min.js"></script>
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
   <img id="a1IMG" alt="a1"          src="http://www.iduona.com/image/cashTicket/logo/b6/57/70/5b/995cb7f4-4c2b-4136-a87f-504696a80d7a.jpg" onclick="previewPhoto('a1');" onmouseover="showDeleteDiv('a1');" onmouseout="imgOnmouseout(),hideDeleteDiv('a1');"
style="width:50px;height:50px;padding-left:10px;"/>
</span>

<span style="position: relative;">
<img id="a2IMG" alt="a2"          src="http://www.iduona.com/image/cashTicket/logo/52/49/7b/f6/33cc4c43-7675-4d94-aaaf-c5d8d3d58a2b.jpg" onclick="previewPhoto('a2');" onmouseover="showDeleteDiv('a2');" onmouseout="imgOnmouseout(),hideDeleteDiv('a2');"
style="width:50px;height:50px;padding-left:10px;"/>
</span>

<span style="position: relative;">
<img id="a3IMG" alt="a3"          src="http://www.iduona.com/image/cashTicket/logo/32/ee/e7/be/4a4baca1-f348-4252-9111-4f84ee4a1500.jpg" onclick="previewPhoto('a3');" onmouseover="showDeleteDiv('a3');" onmouseout="imgOnmouseout(),hideDeleteDiv('a3');"
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

function previewPhoto(resourceCode) {
    alert("查看图片"+resourceCode);
};

function removeGroupNotActivitySharingPhotoByResourceCode(resourceCode) {
    alert("删除图片"+resourceCode);
    $("#"+resourceCode+"IMG").parent().detach();
}

</script>
<script type="text/javascript">
$(document).ready(function(){
    $("img").each(function(i){
        var divObj=$("<div onclick=removeGroupNotActivitySharingPhotoByResourceCode('"+$(this).attr('alt')+"'); onmouseover=divOnmouseover('"+$(this).attr('alt')+"'); onmouseout=divOnmouseout('"+$(this).attr('alt')+"');>×</div>");
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
