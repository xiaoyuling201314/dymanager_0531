<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
	<%@ include file="/view/common/head.jsp" %>
   <title>电路板BOM单 - 仪器信息管理系统</title>
	<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
  </head>
 <body>
<%@ include file="/view/common/top.jsp" %>
 <%@include file="/view/include_left_instruments.jsp" %>
  <div class="main-content instruments">

    <div class="page-head">
      <div class="row">
        <div class="col-md-3">电路板BOM单</div>
        <div class="col-md-6">
          <form class="search" action="circuitBoardService/cirCuitList.do" method="post" autopcb="off">
            <input type="text" placeholder="请输入搜索关键词" name="circuitKeys" value="${circuitKeys}" class="form-control round">
            <i class="iconfont icon-search" id="button"></i>
          </form>
        </div>
        <div class="col-md-3 text-right">
          <a href="view/instruments_pcb_bom_add.jsp" class="btn btn-primary">新增BOM单</a>
          <!-- <a href="instrumentService/instruList.do" class="btn btn-primary">返回</a> -->
        </div>
      </div>
    </div>
    <div class="page-content">
      <div class="subtitle">电路板BOM单版本信息</div>
      <table class="table">
        <thead>
          <tr>
            <th>编号</th>
            <th>电路板BOM单名称</th>
            <th>版本号</th>
            <th>创建人</th>
            <th>上传时间</th>
            <th>修订记录</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody id="circuitForm">
        <c:set var="serNo" value="${pData.startIndex+1}" ></c:set>
          <c:forEach items="${requestScope.pData.itemsData }" var="circuitBoard" varStatus="indexId">
            <tr>
             <td>${serNo+indexId.index}</td>
            <td style="text-align: left;width:35%;"><a href="circuitBoardService/selectCirCuit.do?id=${circuitBoard.id}">${circuitBoard.circuitBoardName }</a></td>
            <td>${circuitBoard.circuitBoardVersion }</td>
            <td>${circuitBoard.createPerson }</td>
            <td>${circuitBoard.updateTime }</td>
            <td>${circuitBoard.revisedRecord }</td>
            <td>
              <a href="#exportModal" class="choise" data-id="${circuitBoard.id}" data-action="modal" ><i class="iconfont icon-export"></i></a>
              <a href="circuitBoardService/selectCirCuit.do?id=${circuitBoard.id}"><i class="iconfont icon-details"></i></a>
              <a class="delete" data-id="${circuitBoard.id }"><i class="iconfont icon-delete"></i></a>
            </td>
          </tr>
          </c:forEach>
        </tbody>
      </table>
       <div class="modal" id="exportModal" style="height: 28%;width: 25%;margin: 0px -10%;">
      <div class="modal-title">请选择导出格式</div>
      <div class="modal-content" style="margin-left: 35px;">
       <input type="radio" name="domtype" checked="checked" value="excel" />excel格式&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" name="domtype" value="pdf" />pdf格式<br/><br/>
          <a id="btnDownload" class="btn btn-green" data-dismiss="modal">下载</a>
          &nbsp;&nbsp;&nbsp;<a class="btn btn-primary" data-dismiss="modal">返回</a>
      </div>
     
    </div>
   <div id="pagination" class="pagination"></div>
  </div>
     <!-- 分页切换表单 -->
 	 <form action="circuitBoardService/cirCuitList.do" method="post" id="listForm">
      <input type="hidden" name="curPage">
      <input type="hidden" name="circuitKeys" value="${circuitKeys}" />
    </form>
  </div>
  <script>
    var pageCount = ${requestScope.pData.pageCount};
    var pageSize = ${requestScope.pData.pageSize};
    var curPage = ${requestScope.pData.curPage};
    var recordCount = ${requestScope.pData.recordCount};
     var basePath = '<%=basePath%>';
     var pDataStartIndex=${pData.startIndex};
  </script>
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/instruments_pcb_bom.min.js"></script>
</body>
</html>
