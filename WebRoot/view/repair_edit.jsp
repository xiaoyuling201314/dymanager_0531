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
  <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
<script type="text/javascript" src="<%=basePath %>js/repair_edit.js"></script>

  <script type="text/javascript">
   var basePath = '<%=basePath%>';
   var countFaultPicture = "${countFaultPicture}";
   var countDealPicture = "${countDealPicture}";
  </script>
  </head>
  
 <body>
<%@ include file="/view/common/top.jsp" %>
<%@ include file="/view/include_left_quality.jsp" %>
  <div class="main-content instruments">
    <div class="page-head">
         <div class="row">
        <div class="col-md-3">维修列表 / 修改</div>
        <div class="col-md-6"></div>
           <div class="col-md-3 text-right">
          <!-- <input type="button" id="importMater" value="导入信息" class="btn btn-green"> -->
        </div>
    </div>
    </div>
    <div class="page-content">
      <div class="row">
        <form method="post" enctype="multipart/form-data" id="repairForm" >
        <input type="hidden" name="shipmentNo" value="${repairRecords.shipmentNo.id }" />
        <input type="hidden" name="repairOrderNumber" value="${repairRecords.repairOrderNumber}"  />
            <div class="form-group" style="height: 38px;" >
              <label class="form-label col-md-2">维修单号<strong style="color: red;">*</strong></label>
              <div class="col-md-4" style="height: 38px;" > 
                <input type="text" class="form-control" name="repairOrderNumber" id="repairOrderNumber" value="${repairRecords.repairOrderNumber}" disabled="disabled">
                <i class="right"><img width='20px' height='20px' style='position: relative;top:-30px ;left: 305px;' src='dist/img/right.png'></img></i>
                <div class="error" ><img width='20px' height='20px' style='position: relative;top:-30px ;left: 100%;' src='dist/img/errorred.png'/>
                <label style='color: red;position: relative;top:-35px ;left: 100%;' class="sapNoMessage"  ></label></div>
              </div>
                <label class="form-label col-md-2">机身码<strong style="color: red;">*</strong></label>
              <div class="col-md-4" style="height: 38px;">
                <input type="text" class="form-control" required="required" disabled="disabled" name="instrumentFuselage" value="${repairRecords.instrumentFuselage}" placeholder="请输入出货机身码">
                <i class="right2"><img width='20px' height='20px' style='position: relative;top:-30px ;left: 305px;' src='dist/img/right.png'></img></i>
                <div class="error2" ><img width='20px' height='20px' style='position: relative;top:-30px ;left: 100%;' src='dist/img/errorred.png'/>
                <label style='color: red;position: relative;top:-35px ;left: 100%;' class="sapNoMessage2"  ></label></div><!-- left: 100%; -->
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-2">SAP代码<strong style="color: red;">*</strong></label>
               <div class="col-md-4">
                <input type="text" class="form-control"  name="sapNo"  disabled="disabled" value="${repairRecords.sapNo }" placeholder="请输入SAP代码">
              </div>
              <label class="form-label col-md-2">产品名称<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control"  name="productName" disabled="disabled" value="${repairRecords.productName }" placeholder="请输入产品名称">
              </div>
            </div>
            <div class="form-group">
               <label class="form-label col-md-2">接收日期<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control" required="required" name="receivedDate" onclick="WdatePicker();" value="${repairRecords.receivedDate }" placeholder="请选择接收日期">
              </div>
 			  <label class="form-label col-md-2">计划完成日期<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control"  name="planCompleteDate" onclick="WdatePicker();" value="${repairRecords.planCompleteDate }" placeholder="请选择日期">
              </div>
            </div>
           <div class="form-group">
               <label class="form-label col-md-2">维修人<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control"  name="repairMan" value="${repairRecords.repairMan }" placeholder="请输入维修人">
              </div>
 			  <label class="form-label col-md-2">实际完成日期</label>
              <div class="col-md-4">
                <input type="text" class="form-control"  name="actualCompleteDate" onclick="WdatePicker();"  placeholder="请输入实际完成日期" value="${repairRecords.actualCompleteDate }">
              </div>
            </div>
           <div class="form-group">
           <label class="form-label col-md-2" >故障描述<strong style="color: red;">*</strong></label><!-- style="margin-top: -10%;" -->
              <div class="col-md-4" ><!-- style="margin-top: -10%;margin-left: 17%;" -->
                <textarea rows="4" cols="2" class="form-control" name="faultDescription">${repairRecords.faultDescription }</textarea>
              </div>
               <label class="form-label col-md-2" style="position: relative;top: -50%;">处理方法</label><!-- style="margin-top: -10%;" -->
              <div class="col-md-4" ><!-- style="margin-top: -10%;margin-left: 17%;" -->
                <textarea rows="4" cols="2" class="form-control" name="processingMethod">${repairRecords.processingMethod }</textarea>
              </div>
            </div>
             <div class="form-group">
              <label class="form-label col-md-2">故障图片</label>
              <div class="col-md-4"><!-- col-md-10 -->
                <ul class="pics" style="padding: 0px;margin: 0px;">
                    <c:forEach items="${repairRecords.faultPicture}" var="picture" varStatus="indexId">
		               <c:if test="${indexId.index<4 }">
		              	 <li class="listPic" style="width: 100px;height: 100px"  onmousemove="showDeletepic(this)" onmouseout="hiddelDeletepic(this)" onclick="deleteMpic(this);">
		              	 <img width="94px" height="90px" src="${picturePath}${picture}" title="${picture}" alt="${picture}" data-id="${picture}">
		              		<div class="deleteMpic" >
			              	  <img width='20px' height='20px' style='position: relative;top:-102px ;left: 85%;' src='dist/img/errorred.png'/>
			                </div>
		              		 </li>
		               </c:if>
             		 </c:forEach>
                </ul>
                 <input type="hidden" name="faultPicture" value="${repairRecords.faultPicture }" autocomplete="off">
                 <input type="hidden" name="delpicture" autocomplete="off">
                <div class="uploader">
                  <div id="picUpload"></div>
                </div>
              </div>
           	
              <label class="form-label col-md-2">处理图片</label>
              <div class="col-md-4"><!-- col-md-10 -->
               <ul class="pics" style="padding: 0px;margin: 0px;">
                    <c:forEach items="${repairRecords.processingPicture}" var="picture" varStatus="indexId">
		               <c:if test="${indexId.index<4 }">
		              	 <li class="listPic" style="width: 100px;height: 100px"  onmousemove="showDeletepic(this)" onmouseout="hiddelDeletepic(this)" onclick="deleteDealMpic(this);">
		              	 <img width="94px" height="90px" src="${picturePath}${picture}" title="${picture}" alt="${picture}" data-id="${picture}">
		              		<div class="deleteMpic" >
			              	  <img width='20px' height='20px' style='position: relative;top:-102px ;left: 85%;' src='dist/img/errorred.png'/>
			                </div>
		              		 </li>
		               </c:if>
             		 </c:forEach>
                </ul>
              <input type="hidden" name="processingPicture" value="${repairRecords.processingPicture}" autocomplete="off">
                <div class="uploader">
                  <div id="picUpload2"></div>
                </div>
              </div>
            </div>
           <div class="form-group">
        		 <label class="form-label col-md-2" >备注</label><!-- style="margin-top: -10%;" -->
              <div class="col-md-4" ><!-- style="margin-top: -10%;margin-left: 17%;" -->
                <textarea rows="4" cols="2" class="form-control" name="comments">${repairRecords.comments }</textarea>
              </div>
            </div>
            <div class="form-group" ><!-- style="margin-top: 15%;" -->
              <div class="col-md-6" style="margin-top: 2%;margin-left: 45%;"><!-- style="margin-left: 40%;" -->
                <a id="btnSave" class="btn btn-green">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
                <a id="returnbtn" class="btn btn-primary">返回</a>
              </div>
            </div>
        </form>

      </div>
    </div>
  </div>
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/repair_edit.min.js"></script>
</body>
</html>
