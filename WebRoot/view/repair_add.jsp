<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
   <%@ include file="/view/common/head.jsp" %>
  <title>新增 - 维修列表 - 仪器信息管理系统</title>
 <script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath %>js/repair_add.js"></script>
 <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
  <script type="text/javascript">
   var basePath = '<%=basePath%>';
   var check;
  </script>
   <% 	request.setAttribute("nav", "repair");
     	SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd");
     	Date d=new Date();
	   String nowTime=simple.format(d);
	   d.setDate(d.getDate()+7);
	   String planTime=simple.format(d);
    %>
  </head>
  
 <body>
<%@ include file="/view/common/top.jsp" %>
<%@ include file="/view/include_left_quality.jsp" %>
  <div class="main-content instruments" >
    <div class="page-head">
         <div class="row">
        <div class="col-md-3">维修列表 / 新增</div>
        <div class="col-md-6"></div>
           <div class="col-md-3 text-right">
          <input type="button" id="importMater" value="导入文件" class="btn btn-green">
        </div>
    </div>
    </div>
    <div class="page-content">
      <div class="row">
        <form method="post" enctype="multipart/form-data" id="repairForm">
        <input type="hidden" name="shipmentNo" />
            <div class="form-group" style="height: 38px;" >
              <label class="form-label col-md-2">维修单号<strong style="color: red;">*</strong></label>
              <div class="col-md-4" style="height: 38px;" > 
                <input type="text" class="form-control" required="required" name="repairOrderNumber" id="repairOrderNumber" placeholder="请输入维修单号">
                <i class="right"><img width='20px' height='20px' style='position: relative;top:-30px ;left: 305px;' src='dist/img/right.png'></img></i>
                <div class="error" ><img width='20px' height='20px' style='position: relative;top:-30px ;left: 100%;' src='dist/img/errorred.png'/>
                <label style='color: red;position: relative;top:-35px ;left: 100%;' class="sapNoMessage"  ></label></div>
              </div>
                <label class="form-label col-md-2">机身码<strong style="color: red;">*</strong></label>
              <div class="col-md-4" style="height: 38px;">
                <input type="text" class="form-control" required="required" name="instrumentFuselage" placeholder="请输入出货机身码">
                <i class="right2"><img width='20px' height='20px' style='position: relative;top:-30px ;left: 305px;' src='dist/img/right.png'></img></i>
                <div class="error2" ><img width='20px' height='20px' style='position: relative;top:-30px ;left: 100%;' src='dist/img/errorred.png'/>
                <label style='color: red;position: relative;top:-35px ;left: 100%;' class="sapNoMessage2"  ></label></div><!-- left: 100%; -->
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-2">SAP代码<strong style="color: red;">*</strong></label>
               <div class="col-md-4">
                <input type="text" class="form-control"  name="sapNo" disabled="disabled" placeholder="请输入SAP代码">
              </div>
              <label class="form-label col-md-2">产品名称<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control"  name="productName" disabled="disabled" placeholder="请输入产品名称">
              </div>
            </div>
            <div class="form-group">
               <label class="form-label col-md-2">接收日期<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control" required="required" name="receivedDate" onclick="WdatePicker();" value="<%=nowTime %>"  placeholder="请选择接收日期">
              </div>
 			  <label class="form-label col-md-2">计划完成日期<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control"  name="planCompleteDate" onclick="WdatePicker();" value="<%=planTime %>" placeholder="请选择日期">
              </div>
            </div>
           <div class="form-group">
               <label class="form-label col-md-2">维修人<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control"  name="repairMan" placeholder="请输入维修人">
              </div>
 			  <label class="form-label col-md-2">实际完成日期</label>
              <div class="col-md-4">
                <input type="text" class="form-control"  name="actualCompleteDate" onclick="WdatePicker();"  placeholder="请输入实际完成日期">
              </div>
            </div>
           <div class="form-group">
           <label class="form-label col-md-2" >故障描述<strong style="color: red;">*</strong></label><!-- style="margin-top: -10%;" -->
              <div class="col-md-4" ><!-- style="margin-top: -10%;margin-left: 17%;" -->
                <textarea rows="4" cols="2" class="form-control" name="faultDescription"></textarea>
              </div>
               <label class="form-label col-md-2" style="position: relative;top: -50%;">处理方法</label><!-- style="margin-top: -10%;" -->
              <div class="col-md-4" ><!-- style="margin-top: -10%;margin-left: 17%;" -->
                <textarea rows="4" cols="2" class="form-control" name="processingMethod"></textarea>
              </div>
            </div>
             <div class="form-group">
              <label class="form-label col-md-2">故障图片</label>
              <div class="col-md-4"><!-- col-md-10 -->
              <input type="hidden" name="faultPicture" autocomplete="off">
                <div class="uploader">
                  <div id="picUpload"></div>
                </div>
              </div>
           	
              <label class="form-label col-md-2">处理图片</label>
              <div class="col-md-4"><!-- col-md-10 -->
              <input type="hidden" name="processingPicture" autocomplete="off">
                <div class="uploader">
                  <div id="picUpload2"></div>
                </div>
              </div>
            </div>
           <div class="form-group">
        		 <label class="form-label col-md-2" >备注</label><!-- style="margin-top: -10%;" -->
              <div class="col-md-4" ><!-- style="margin-top: -10%;margin-left: 17%;" -->
                <textarea rows="4" cols="2" class="form-control" name="comments"></textarea>
              </div>
            </div>
            <div class="form-group" ><!-- style="margin-top: 15%;" -->
              <div class="col-md-6" style="margin-top: 2%;margin-left: 45%;"><!-- style="margin-left: 40%;" -->
                <a id="btnSave" class="btn btn-green">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
                <a id="returnbtn" class="btn btn-primary">返回</a>
              </div>
            </div>
        </form>
         <!-- 导入维修信息文件 -->
         <form enctype="multipart/form-data" id="repairImportform" >
         <input type="hidden" name="operationType" value="normal" />
              <div class="form-group" style="height: 38px;">
            <label class="form-label col-md-3">维修记录文件</label>
            <div class="col-md-5">
              <input type="text" class="form-control"  name="fileName">
            </div>
            <div class="col-md-4" style="height: 38px;">
              <div class="file-control">
                <input type="file" name="myFile" onchange="selectExcelFile();">浏览...
              </div>
              <div class="file-type" onmousemove="showDeletepic()" onmouseout="hiddelDeletepic()" onclick="deleteFile();">
                <i class="fileicon" class="matericon"><img width="35px" height="34px;" style="position: relative;top: -6px;" src="dist/img/excel.png"></img></i>
                 <div class="deletFile" ><img width='20px' height='20px' style='position: relative;top:-55px ;left: 40%;' src='dist/img/errorred.png'/>
		         </div>
              </div>
            </div>
             <div >
              <label style='color: red;position: relative;top:-30px ;left: 80%;' class="fileNameMessage"  ></label>
            </div>
          </div>
           <div class="form-group" >
              <div class="col-md-offset-5 col-md-6" >
                 <a  class="btn btn-green" id="btnShipSave" style="position: relative;left: -30%;">导入</a>
                <input type="button" id="returnInput" class="btn btn-primary" value="返回" style="position: relative;left: -20%;" />
              </div>
            </div>
          </form>
      </div>
    </div>
  </div>
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/repair_add.min.js"></script>
</body>
</html>
