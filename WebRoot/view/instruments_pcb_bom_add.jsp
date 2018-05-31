<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/view/common/head.jsp" %>
  <title>新增 - 电路板BOM单 - 仪器信息管理系统</title>
   <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
 <!--  <script type="text/javascript" src="<%=basePath %>js/instrument_pcb_bom_detail.js"></script> -->
 <script type="text/javascript" src="<%=basePath %>/js/instruments_pcb_bom_add.js"></script>
  <% request.setAttribute("nav", "instruments_pcb_bom"); %>
  <script type="text/javascript">
    var basePath = '<%=basePath%>';
  </script>
  </head>
 <body>
<%@ include file="/view/common/top.jsp" %>
<%@include file="/view/include_left_instruments.jsp" %>
  <div class="main-content instruments">
    <div class="page-head">电路板BOM单 / 新增</div>
    <div class="page-content">
      <div class="row">
        <form action="circuitBoardService/addCirCuit.do" method="post" enctype="multipart/form-data" name="pcbForm">
          <div class="form-group" style="height: 38px;">
            <label class="form-label col-md-3">电路板BOM单文件</label>
            <div class="col-md-5">
              <input type="text" class="form-control" name="fileName">
            </div>
             <div class="col-md-4" style="height: 38px;">
              <div class="file-control">
                <input type="file" name="myFile" onchange="selectExcelFile();" >浏览...
              </div>
              <div class="file-type" onmousemove="showDeletepic()" onmouseout="hiddelDeletepic()" onclick="deleteFile();">
                <i class="fileicon"><img width="35px" height="34px;" style="position: relative;top: -6px;" src="dist/img/excel.png"></img></i>
                 <div class="deletFile" ><img width='20px' height='20px' style='position: relative;top:-55px ;left: 40%;' src='dist/img/errorred.png'/>
		         </div>
              </div>
            </div>
            <div class="error" >
              <label style='color: red;position: relative;top:-38px ;left: 81%;' class="fileNameMessage"  ></label>
            </div>
          </div>
           <div class="form-group">
            <label class="form-label col-md-3">电路板BOM单名称<strong style="color: red;">*</strong></label>
            <div class="col-md-5" style="height: 38px;">
              <input type="text" class="form-control" name="circuitBoardName" placeholder="请输入名称">
              <div class="error" >
              <label style='color: red;position: relative;top:-30px ;left: 103%;' class="circuitBoardNameMessage"  ></label>
            </div>
            </div>
          </div>
          <div class="form-group">
            <label class="form-label col-md-3">版本号<strong style="color: red;">*</strong></label>
            <div class="col-md-5" style="height: 38px;">
              <input type="text" class="form-control" name="circuitBoardVersion" placeholder="请输入版本号">
               <div class="error" >
              <label style='color: red;position: relative;top:-30px ;left: 103%;' class="circuitBoardVersionMessage"  ></label>
            </div>
            </div>
          </div>
          <div class="form-group">
            <label class="form-label col-md-3">创建人<strong style="color: red;">*</strong></label>
            <div class="col-md-5" style="height: 38px;"> 
              <input type="text" class="form-control" required="required" name="createPerson" placeholder="请输入创建人" value="${userSession.userName}">
             <div class="error" >
              <label style='color: red;position: relative;top:-30px ;left: 103%;' class="createPersonMessage"  ></label>
            </div>
            </div>
          </div>
          <div class="form-group">
            <label class="form-label col-md-3">修订记录<strong style="color: red;">*</strong></label>
            <div class="col-md-5">
              <textarea rows="4" class="form-control" name="revisedRecord"></textarea>
            </div>
             <div class="error" >
              <label style='color: red;position: relative;top:50px ;' class="revisedRecordMessage"  ></label>
            </div>
          </div>

          <div class="form-group">
            <div class="col-md-offset-5 col-md-6" style="margin-left: 30%;" >
              <a class="btn btn-green" id="btnSave" >保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
              <a id="returnbtn" class="btn btn-primary">返回</a>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/instruments_pcb_bom_add.min.js"></script>
</body>
</html>
