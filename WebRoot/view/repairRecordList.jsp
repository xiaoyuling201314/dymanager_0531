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
  <title>维修列表 - 仪器信息管理系统</title>
  <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
  <script type="text/javascript" src="<%=basePath %>js/repairList.js"></script>
  <style type="text/css"> 
		div.panel,p.flip
		{
		margin:0px;
		text-align:center;
		}
		div.panel
		{
		display:none;
		width:82px;
		position: absolute;
		right:15.2%;
		margin-top:50px;
		background-color: #FFFFFF;
		}
		table{
		 width: 100%;
		}
	  td{
		  border: 1px solid #000000;
		  padding: 5px;
	  }
	.tr1 td{
	   background-color: #00B2EE;
	  }
	   .tr2 td{
	   background-color: #00FF7F;
	  }
	   .tr3 td{
	   background-color: #ffd850;
	  }
	  .heightStyle{
	   width: 20%;
	   }
	</style>
</head>
<body>
<%@ include file="/view/common/top.jsp" %>
<%@ include file="/view/include_left_quality.jsp" %>

<div class="main-content instruments">

  <div class="page-head">
    <div class="row">
      <div class="col-md-3">维修列表</div>
      <div class="col-md-6">
          <form class="search" action="repairService/repairList.do" method="post" autocomplete="off">
            <input type="text" placeholder="请输入搜索关键词" name="repairKeys" value="${requestScope.repairKeys}" class="form-control round" >
           <input type="hidden" name="state" value="${requestScope.state }" />
            <i class="iconfont icon-search" id="button"></i>
          </form>
        </div>
      <div class="col-md-3  text-right"><!-- col-md-offset-6 <a class="btn btn-red" id="deleteList">删除</a> -->
        <a href="view/repair_add_special.jsp" class="btn btn-primary">新增</a>
        <!--  <a href="view/repair_add_special.jsp" class="btn btn-primary">历史维修</a> -->
      </div>
    </div>
  </div>
   
  <div class="page-content">
     <div class="subtitle">维修信息列表</div>
                <!-- 状态下拉列表 -->
    <div class="panel">
		<ul>
		   <li data-id="0" class="state0">所有状态</li>
		   <li data-id="1" class="state1">正在处理</li>
		   <li data-id="3" class="state3">逾期处理</li>
		    <li data-id="2" class="state2">已完成</li>
		  </ul>
				 
	</div>
      <table class="table" style="z-index: -1;">
        <thead>
          <tr>
            <th>序号</th>
            <th>维修单号</th>
            <th >产品名称</th>
            <th>故障描述</th>
            <th>故障图片</th>
            <th class="hideState">接收日期</th>
            <th style="width:82px;">
      		   <div class="content" style="float:left;display:block;width:82px;">
				<p class="flip">状态<span ><i class="iconfont icon-arrow-down"></i></span></p><!-- <span class="arrow"><i class="iconfont icon-arrow-down"></i></span> -->
				</div>
            </th>
            <th class="hideState" style="width: 16.7%;">操作</th>
          </tr>
        </thead>
        <tbody id="repairList">
        <c:set var="serNo" value="${pData.startIndex+1}" ></c:set>
		<c:forEach items="${pData.itemsData}" var="repairRecords" varStatus="indexId">
        <tr <c:if test="${repairRecords.state==1}">class="tr1"</c:if> <c:if test="${repairRecords.state==2}">class="tr2"</c:if><c:if test="${repairRecords.state==3}">class="tr3"</c:if> >
           <td >${serNo+indexId.index}</td>
            <td style="text-align: left;">${repairRecords.repairOrderNumber}</td>
            <td style="text-align: left;width:20%;">${repairRecords.productName}</td>
            <td style="text-align: left;width:20%;">${repairRecords.faultDescription}</td>
            <td  class="gallery">
               <c:forEach items="${repairRecords.faultPicture}" var="picture" varStatus="index">
                <c:if test="${index.index==0 }"><a href="${picturePath}${picture}"><img width="20px" height="20px" alt="图片" src="dist/img/pic.png"></a></c:if>
           		<c:if test="${index.index!=0 }"><a style="display: none;" href="${picturePath}${picture}">${picture}</a></c:if>
              </c:forEach>
              
              </td>
            <td>
            ${repairRecords.receivedDate }
            </td>
            <td>
              <c:choose>
                 <c:when test="${repairRecords.state==1 }">正在处理</c:when>
                 <c:when test="${repairRecords.state==2 }">已完成</c:when>
                 <c:when test="${repairRecords.state==3 }">逾期处理</c:when>
              </c:choose>
            </td>
            <td style="width: 17%;">
              <a href="#viewDetailModal" data-action="modal" class="detail" data-id="${repairRecords.repairOrderNumber}"><i class="iconfont icon-details"></i></a>
               <a href="repairService/exportRepair.do?repairOrderNumber=${repairRecords.repairOrderNumber}"><i class="iconfont icon-export"></i></a>
               <a href="repairService/queryRepair.do?repairOrderNumber=${repairRecords.repairOrderNumber}"><i class="iconfont icon-edit"></i></a>
               <a class="delete" data-id="${repairRecords.repairOrderNumber}"><i class="iconfont icon-delete"></i></a>
            </td>
          </tr>
        </c:forEach>
        
        </tbody>
      </table>
    <div class="text-center">
      <div id="pagination" class="pagination"></div>
    </div>
  </div>
    <!-- 分页切换表单 -->
	<form action="repairService/repairList.do" method="post" id="listForm">
      <input type="hidden" name="curPage">
      <input type="hidden" name="repairKeys" value="${requestScope.repairKeys}" />
      <input type="hidden" name="state" value="${requestScope.state }" />
    </form>
    <!-- 状态下拉列表 
    <div class="panel">
		<ul>
		   <li>所有状态</li>
		   <li>正在处理</li>
		   <li>已完成</li>
		   <li>未完成</li>
		  </ul>
				 
	</div>-->
	<!-- 查看仪器详情 -->
       <div class="modal" id="viewDetailModal" style="height: 600px;" >
        <div class="modal-title primary" name="detail_Title" ></div>
        <div class="modal-content">
            <table style="border: 1px solid #000000">
              <tr >
              <td>维修单号</td>
              <td name="repairOrderNumber"></td>
               <td>机身码</td>
              <td name="instrumentFuselage"></td>
              </tr>
              
               <tr >
              <td>SAP代码</td>
              <td name="sapNo"></td>
               <td>产品名称</td>
              <td name="productName"></td>
              </tr>
              
                <tr >
              <td>接收日期</td>
              <td name="receivedDate"></td>
               <td>计划完成日期</td>
              <td name="planCompleteDate"></td>
              </tr>
              
              <tr >
              <td>维修人</td>
              <td name="repairMan"></td>
               <td>实际完成日期</td>
              <td name="actualCompleteDate"></td>
              </tr>

               <tr>
                <td>故障描述</td>
                <td colspan="3" name="faultDescription"></td>
                </tr>
               <tr>
                <td>处理方法</td>
                <td colspan="3" name="processingMethod"></td>
              	</tr>
              
              <tr >
              <td>备注</td>
              <td colspan="3" name="comments"></td>
              </tr>
              <!-- update by xiaoyuling 2016-12-27 start 
              <tr style="height: 100px;">
               <td>故障图片</td>
              <td colspan="3" class="gallery" id="pics">
               <c:forEach items="${instrumentDTO.picture}" var="picture" varStatus="indexId">
                 <c:if test="${indexId.index<4 }">
                <a style="width: 24.36%;margin: 1px 5px;" href="${picturePath}${picture}"><img width="94px" height="90px" src="${picturePath}${picture}" alt="图片" title="图片"></a>
               </c:if>
              </c:forEach>
              </td>
            </tr>
             <tr style="height: 100px;">
               <td>处理图片</td>
              <td colspan="3" class="gallery" id="picsDeal" >
               <c:forEach items="${instrumentDTO.picture}" var="picture" varStatus="indexId">
                 <c:if test="${indexId.index<4 }">
                <a style="width: 24.36%;margin: 1px 5px;" href="${picturePath}${picture}"><img width="94px" height="90px" src="${picturePath}${picture}" alt="图片" title="图片"></a>
               </c:if>
              </c:forEach>
              </td>
            </tr>
             update by xiaoyuling 2016-12-27 end -->
            </table>
         </div>
         	 <div style="height: 100px;">
				<label class="form-label col-md-2">故障图片</label>
				<div class="col-md-10">
				<ul class="pics" style="padding: 0px;margin: 0px;">

				</ul>
			</div>
			</div> 
			<div style="height: 100px;">
				<label class="form-label col-md-2">处理图片</label>
				<div class="col-md-10">
					<ul class="picsDeal" style="padding: 0px;margin: 0px;">
					</ul>
				</div>
			</div>
			<!---->
			<div class="modal-action" style="margin-top: -2%;">
		            <button type="button" data-dismiss="modal" class="btn btn-primary" style="margin: 0px auto;" >返回</button>
		          </div>
          </div>
       </div>
<script>
    var pageCount = ${requestScope.pData.pageCount};
    var pageSize = ${requestScope.pData.pageSize};
    var curPage = ${requestScope.pData.curPage};
    var recordCount = ${requestScope.pData.recordCount};
  </script>
<script src="dist/js/lib/require.min.js" data-main="dist/js/page/repairList.min.js"></script>
</body>
</html>
