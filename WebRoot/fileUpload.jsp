<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'fileUpload.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  
  <script type="text/javascript" src="js/jquery-easyui-1.5/jquery.min.js"></script>
  <script type="text/javascript">
    function add(){
     var formData = new FormData($("#uploadForm")[0]);//用form 表单直接 构造formData 对象; 就不需要下面的append 方法来为表单进行赋值了。 
     $.ajax({ 
         async: false,//要求同步 不是不需看你的需求
         url : "materielDrawService/gexingSet.do",  
         type : 'POST',  
         data : formData,  
         processData : false,  //必须false才会避开jQuery对 formdata 的默认处理   
         contentType : false,  //必须false才会自动加上正确的Content-Type 
         success : function(result) {  
                if(result==1){
                    forward=true;
                }else{
                    
                    $(".myModal-click").trigger("click");
                    forward=false;
                } 
         },  
         error : function(result) {  
             $(".myModal-click").trigger("click");
                forward=false; 
         }  
     });  
    
}
  </script>
  </head>
  
  <body>
    <form action="userService/fileUploads.do" method="post" enctype="multipart/form-data">
      <input type="file" name="myFile" /><br/>
       <input type="file" name="myFile" /><br/>
      <input type="submit" value="上传" />
    </form>
   
   <a href="userService/download.do?fileName=WEB概要设计说明书(基本功能).doc" >点击下载</a>
  
  <!-- 
   <form id="upload" action="userService/fileUploads.do" method="post" enctype="multipart/form-data">
      <input type="file" name="myFile" id="btn_file" value="123" style="display: none;" /><br/>
      <input type="button" name="btn_upload" value="浏览" onclick="choise();" />
      <input type="submit" value="上传" />
    </form>
    <script type="text/javascript">
      function choise(){
      $("#btn_file").click();
      alert($("#btn_file").val())
      }
    </script> -->
      <form class="inpform" id="uploadForm" enctype="multipart/form-data">
                        <div class="f-inp">
                            <div>&#12288;<i>诊所名称：</i>
                                <input type="text"  name="name" id="name"  >
                            </div>
                            <div>&#12288;<i>诊所地址：</i>
                                <input type="text"  name="addrInfo"  id="addrInfo" >
                            </div>
        
                            <h4>Logo</h4>   
                      <input type="file" id="file" name="file" />
                        </div>
                    </form>
       <input  type="button" value="提交" onclick="add();">
    
  </body>
</html>
