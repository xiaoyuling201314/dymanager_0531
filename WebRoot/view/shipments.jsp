<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
  <%@ include file="/view/common/head.jsp" %>
  <title>出货列表 - 仪器信息管理系统</title>
  <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
  <script type="text/javascript" src="<%=basePath %>js/shipments.js"></script>
  <script type="text/javascript">
   var basePath = '<%=basePath%>';
  </script>
<style type="text/css">
	table{
	 width: 100%;
	}
	  td{
	  border: 1px solid #000000;
	  padding: 5px;
	  }
	  
	   .tr1 td{
	     background-color: #ffd850;
	   }
	  .columeStyle{
	  width: 120px;
	  }
	</style>
</head>
<body>
<%@ include file="/view/common/top.jsp" %>
<%@ include file="/view/include_left_quality.jsp" %>

<div class="main-content instruments">

  <div class="page-head">
    <div class="row">
      <div class="col-md-3"  style="width: 15%;">出货列表</div><!--  style="width: 15%;"  -->
      <div class="col-md-6">
          <form class="search" action="shipmentsService/shipList.do" method="post" autocomplete="off">
            <input type="text" placeholder="请输入搜索关键词" name="shipmentsKeys" value="${requestScope.shipmentsKeys}" class="form-control round" >
            <i class="iconfont icon-search" id="button"></i>
          </form>
        </div>
      <div class="col-md-3  text-right" style="width: 35%;" ><!-- col-md-offset-6   <div class="col-md-3  text-right" style="width: 35%;" <a class="btn btn-red" id="deleteList">删除</a> -->
      	<a class="btn btn-green" id="exportShip">导出</a>&nbsp;&nbsp;&nbsp;&nbsp;
      	<input type="hidden" name="listID" />
      	<a class="btn btn-red" id="deleteList">删除</a> &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="view/shipments_add.jsp" class="btn btn-primary">新增</a>
      </div>
    </div>
  </div>
  <div class="page-content">
     <div class="subtitle">出货信息列表</div>
      <table class="table">
        <thead>
          <tr>
            <th>序号</th>
            <th>SAP代码</th>
            <th>产品名称</th>
            <th>客户名称</th>
            <th>出货日期</th>
             <th>软件版本</th>
            <th>数量</th>
            <th>  
            	 <div class="checkbox checkbox-white" style='margin-right:-5px;'>
                  <input type="checkbox" id="selectAll"  onchange="sel(this)">
                  <label for="selectAll"></label>
                </div>
                </th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody id="manualList">
        <c:set var="serNo" value="${pData.startIndex+1}" ></c:set>
        <c:forEach items="${pData.itemsData}" var="shipments" varStatus="indexId">
          <tr <c:if test="${shipments.countRepair>0}">class="tr1"</c:if> > <!-- 有维修记录的数据改变底色 -->
           <td>${serNo+indexId.index}</td>
            <td>${shipments.sapNo.sapNo}</td>
            <td  style="text-align: left;width:35%;" >${shipments.sapNo.productName}</td>
            <td class="textAlign" style="text-align: left;width:20%;">${shipments.customer}</td>
            <td>${shipments.shippingDate}</td>
            <td>${shipments.softwareVersion}</td>
            <td>${shipments.quantity}</td>
            <td>
            <div class='checkbox' style='margin-right:-5px;'>
	            <input type='checkbox' value='${shipments.id}' class='for-all${indexId.index}' name='selectId' onchange='changeallsel(this)'  id='select${indexId.index}' >
	            <label for='select${indexId.index}' ></label> 
	            </div>
            </td>
            <td>
               <a href="#viewDetailModal" data-action="modal" class="detail" data-id="${shipments.id}"><i class="iconfont icon-details"></i></a>
               <a href="shipmentsService/viewshipMents.do?id=${shipments.id }" ><i class="iconfont icon-edit"></i></a>
               <a class="delete" data-id="${shipments.id}"><i class="iconfont icon-delete"></i></a>
            </td>
          </tr>
        </c:forEach>
        
          <tr>
            
          </tr>
        </tbody>
      </table>
    <div class="text-center">
      <div id="pagination" class="pagination"></div>
    </div>
  </div>
    <!-- 分页切换表单 -->
	<form action="shipmentsService/shipList.do" method="post" id="listForm">
      <input type="hidden" name="curPage">
      <input type="hidden" name="shipmentsKeys" value="${requestScope.shipmentsKeys}" />
    </form>
</div>

<!-- 查看仪器详情 -->
       <div class="modal" id="viewDetailModal" style="height: 450px;" >
        <div class="modal-title primary" name="detail_Title"></div>
        <div class="modal-content">
            <table style="border: 1px solid #000000;margin-top: 2%;width: 99%;">
              <tr >
	               <td >SAP代码</td>
	              <td name="sapNo" class="columeStyle"></td>
	              <td>产品名称</td>
	              <td name="productName"></td>
              </tr>
              
               <tr >
	              <td >客户名称</td>
	              <td name="customer"></td>
	               <td>检验人</td>
	              <td name="inspectionMan"></td>
              </tr>
              
             <tr >
              	 <td >软件版本</td>
	              <td name="softwareVersion"></td>
	              <td >出货日期</td>
	              <td name="shippingDate"></td>
              </tr>
             <tr>
                <td >出货数量</td>
	            <td name="quantity"></td>
                <td >机身号码</td>
                <td colspan="2" name="instrumentFuselage" class="columeStyle"></td>
                </tr>
              <tr >
              <tr >
              <td>备注</td>
              <td colspan="3" name="comments"></td>
              </tr>
            </table>
         </div>
 
              <div class="modal-action">
		            <button type="button" data-dismiss="modal" class="btn btn-primary" >返回</button>
		       </div>
          </div>
      
<script>
    var pageCount = ${requestScope.pData.pageCount};
    var pageSize = ${requestScope.pData.pageSize};
    var curPage = ${requestScope.pData.curPage};
    var recordCount = ${requestScope.pData.recordCount};
  </script>
<script src="dist/js/lib/require.min.js" data-main="dist/js/page/shipments.min.js"></script>
</body>
</html>
