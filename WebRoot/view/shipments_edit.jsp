<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
   <%@ include file="/view/common/head.jsp" %>
  <title>编辑 - 出货列表 - 仪器信息管理系统</title>
 <script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath %>js/shipments_edit.js"></script>
 <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
  <!-- add for autoComplete  <link rel="stylesheet" href="/resources/demos/style.css">-->
  <link rel="stylesheet" href="<%=basePath %>js/autoComplete/jquery-ui.css">
  <script src="<%=basePath %>js/autoComplete/jquery-1.12.4.js"></script>
  <script src="<%=basePath %>js/autoComplete/jquery-ui.js"></script>
  <script type="text/javascript">
   var basePath = '<%=basePath%>';
   var check=true;  
  </script>
   <% request.setAttribute("nav", "shield"); %>
  </head>
  
 <body>
<%@ include file="/view/common/top.jsp" %>
<%@ include file="/view/include_left_quality.jsp" %>
  <div class="main-content instruments">
    <div class="page-head">出货列表 / 编辑</div>
    <div class="page-content">
      <div class="row">
        <form >
            <div class="form-group" style="height: 38px;" >
              <label class="form-label col-md-2">SAP代码<strong style="color: red;">*</strong></label>
              <div class="col-md-4" style="height: 38px;" > 
              <input type="hidden" name="sapNo" value="${shipMents.sapNo.sapNo }" />
                <input type="text" class="form-control" disabled="disabled" name="sapNo" id="mysapNo" placeholder="请输入SAP代码" value="${shipMents.sapNo.sapNo }">
                <input type="hidden" name="id" value="${shipMents.id }" />
              </div>
                <label class="form-label col-md-2">产品名称<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control" name="productName" disabled="disabled"   placeholder="请输入仪器名称" value="${shipMents.sapNo.productName }">
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-2">客户名称<strong style="color: red;">*</strong></label>
               <div class="col-md-4">
                <input type="text" class="form-control"  name="customer" placeholder="请输入客户名称" value="${shipMents.customer }">
              </div>
              <label class="form-label col-md-2">检验人<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control"  name="inspectionMan" value="${shipMents.inspectionMan }" placeholder="请输入检验人">
              </div>
            </div>
              <div class="form-group">
            	<label class="form-label col-md-2">软件版本</label><!-- <strong style="color: red;">*</strong> -->
                <div class="col-md-4" >
                <input type="text" class="form-control" required="required" name="softwareVersion" value="${shipMents.softwareVersion}" placeholder="请输入软件版本">
              </div>
               <label class="form-label col-md-2">出货日期<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control" name="shippingDate" onclick="WdatePicker();" placeholder="请选择出货日期" value="${shipMents.shippingDate }">
              </div>
            </div>
           <div class="form-group">
            <label class="form-label col-md-2">出货数量<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control"  name="quantity" placeholder="请输入出货数量" value="${shipMents.quantity }">
              </div>
          </div>
            <div class="form-group" style="height: 38px;">
              <label class="form-label col-md-2" >机身码<strong style="color: red;">*</strong></label>
	             <div class="col-md-4">
                 <textarea rows="6" cols="2" class="form-control" style="ime-mode:disabled"  name="instrumentFuselage" placeholder="请输入机身码" >${shipMents.instrumentFuselage}</textarea><!-- <c:if test="${shipMents.quantity==''}">disabled="disabled" </c:if> -->
             	<div class="notice" style="color: #9D9D9D;" >机身码之间请以逗号隔开,如:10273,100086</div>
              </div>
               <label class="form-label col-md-2" >备注</label><!-- style="margin-top: -10%;" -->
              <div class="col-md-4" ><!-- style="margin-top: -10%;margin-left: 17%;" -->
                <textarea rows="6" cols="2" class="form-control" name="comments" >${shipMents.comments }</textarea>
              </div>
            </div>
            <div class="form-group" ><!-- style="margin-top: 15%;" -->
              <div class="col-md-6" style="margin-top: 2%;margin-left: 45%;"><!-- style="margin-left: 40%;" -->
                <a  id="btnSave" class="btn btn-green">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
                <a id="returnbtn" class="btn btn-primary">返回</a>
              </div>
            </div>
        </form>
      </div>
    </div>
  </div>
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/shipments_edit.min.js"></script>
</body>
</html>
