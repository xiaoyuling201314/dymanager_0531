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
  <title>新增 - 出货列表 - 仪器信息管理系统</title>
 <script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath %>js/shipments_add.js"></script>
 <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
 <!-- add for autoComplete  <link rel="stylesheet" href="/resources/demos/style.css">-->
  <link rel="stylesheet" href="<%=basePath %>js/autoComplete/jquery-ui.css">
  <script src="<%=basePath %>js/autoComplete/jquery-1.12.4.js"></script>
  <script src="<%=basePath %>js/autoComplete/jquery-ui.js"></script>
  
  <script type="text/javascript">
   var basePath = '<%=basePath%>';
	var check=true;  
  </script>
   <% request.setAttribute("nav", "shield");
	   SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd");
	   String nowTime=simple.format(new Date());
	  //Date nowTims=simple.parse(new Date());
    %>
  </head>
  
 <body>
<%@ include file="/view/common/top.jsp" %>
<%@ include file="/view/include_left_quality.jsp" %>
  <div class="main-content instruments">
    <div class="page-head">
     <div class="row">
        <div class="col-md-3">出货列表 / 新增</div>
        <div class="col-md-6"></div>
           <div class="col-md-3 text-right">
          <input type="button" id="importMater" value="导入信息" class="btn btn-green">
        </div>
    </div>
    </div>
    <div class="page-content">
      <div class="row">
        <form id="shipmentsForm" name="shipmentsForm"  >
            <div class="form-group" style="height: 38px;" >
              <label class="form-label col-md-2">SAP代码<strong style="color: red;">*</strong></label>
              <div class="col-md-4" style="height: 38px;" > 
                <input type="text" class="form-control" required="required" name="sapNo" id="mysapNo" placeholder="请输入SAP代码">
                <i class="right"><img width='20px' height='20px' style='position: relative;top:-30px ;left: 305px;' src='dist/img/right.png'></img></i>
                <div class="error" ><img width='20px' height='20px' style='position: relative;top:-30px ;left: 100%;' src='dist/img/errorred.png'/>
                <label style='color: red;position: relative;top:-35px ;left: 100%;' class="sapNoMessage"  ></label></div>
              </div>
                <label class="form-label col-md-2">产品名称<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control" required="required" name="productName" disabled="disabled"  placeholder="请输入产品名称">
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-2">客户名称<strong style="color: red;">*</strong></label>
               <div class="col-md-4">
                <input type="text" class="form-control"  name="customer" placeholder="请输入客户名称" autocomplete="on" >
              </div>
              <label class="form-label col-md-2">检验人<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control"  name="inspectionMan" placeholder="请输入检验人">
              </div>
            </div>
            <div class="form-group" style="height: 38px;">
               <label class="form-label col-md-2">软件版本</label><!-- <strong style="color: red;">*</strong> -->
              <div class="col-md-4" >
                <input type="text" class="form-control" required="required" name="softwareVersion" placeholder="请输入软件版本">
              </div>
              <label class="form-label col-md-2">出货日期<strong style="color: red;">*</strong></label>
              <div class="col-md-4" >
                <input type="text" class="form-control" required="required" name="shippingDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" value="<%=nowTime %>" placeholder="请选择出货日期">
              </div>
            </div>
           <div class="form-group" style="height: 38px;">
               <label class="form-label col-md-2">出货数量<strong style="color: red;">*</strong></label>
              <div class="col-md-4" style="height: 38px;">
                <input type="text" class="form-control"  name="quantity" placeholder="请输入出货数量">
              </div>
            </div>
            
            <div class="form-group" style="height: 38px;">
              <label class="form-label col-md-2" >机身码<strong style="color: red;">*</strong></label>
	             <div class="col-md-4">
                 <textarea rows="4" cols="2" class="form-control" style="ime-mode:disabled" name="instrumentFuselage" placeholder="请输入机身码" ></textarea><!--  disabled="disabled" -->
              <div class="notice" style="color: #9D9D9D;" >机身码之间请以逗号隔开,如:10273,100086</div>
              </div>
               <label class="form-label col-md-2" >备注</label><!-- style="margin-top: -10%;" -->
              <div class="col-md-4" ><!-- style="margin-top: -10%;margin-left: 17%;" -->
                <textarea rows="4" cols="2" class="form-control" name="comments"></textarea>
              </div>
            </div>
            
            <div class="form-group" ><!-- style="margin-top: 15%;" -->
              <div class="col-md-6" style="margin-top: 2%;margin-left: 45%;"><!-- style="margin-left: 40%;" -->
                <a  id="btnSave" class="btn btn-green">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
                <a id="returnbtn" class="btn btn-primary">返回</a>
              </div>
            </div>
        </form>
        <!-- 导入出货信息文件 -->
         <form enctype="multipart/form-data" id="shipmentsImportform" >
              <div class="form-group" style="height: 38px;">
            <label class="form-label col-md-3">出货记录文件</label>
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
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/shipments_add.min.js"></script>
</body>
</html>
