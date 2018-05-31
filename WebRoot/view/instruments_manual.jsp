<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
	<%@ include file="/view/common/head.jsp" %>
  <title>仪器说明书 - 仪器信息管理系统</title>
  <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
  </head>
  
 <body>
<%@ include file="/view/common/top.jsp" %>
<%@include file="/view/include_left_instruments.jsp" %>
  <div class="main-content instruments">

    <div class="page-head">
      <div class="row">
        <div class="col-md-3">仪器说明书</div>
        <div class="col-md-6">
          <form class="search" action="manualService/manualList.do" method="post" autocomplete="off">
            <input type="text" placeholder="请输入搜索关键词" name="manualName" value="${requestScope.manualName}" class="form-control round">
            <i class="iconfont icon-search" id="button"></i>
          </form>
        </div>
        <div class="col-md-3 text-right">
          <a href="view/instruments_manual_add.jsp" class="btn btn-primary">新增说明书</a>
         <!-- <a href="instrumentService/instruList.do" class="btn btn-primary">返回</a> -->
        </div>
      </div>
    </div>
    <div class="page-content">
      <div class="subtitle">仪器版本说明书</div>
      <table class="table">
        <thead>
          <tr>
            <th>序号</th>
            <th>仪器说明书名称</th>
            <th>版本号</th>
            <th>修订人</th>
            <th>上传时间</th>
            <th>修订记录</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody id="manualList">
        <c:set var="serNo" value="${pData.startIndex+1}" ></c:set>
        <c:forEach items="${pData.itemsData}" var="manual" varStatus="indexId">
          <tr>
           <td>${serNo+indexId.index}</td>
            <td style="text-align: left;width:35%;" >
            ${manual.fileName }</td><!-- <a href="${manualPath}${manual.fileName }"  target="_blank" class="underline"></a> -->
            <td>${manual.version }</td>
            <td>${manual.reviser }</td>
            <td>${manual.updateTime}</td>
            <td>${manual.revisedRecord }</td>
            <td>
              <a href="manualService/downloadManual.do?id=${manual.id}"><i class="iconfont icon-export"></i></a>
              <a class="delete" data-id="${manual.id }"><i class="iconfont icon-delete"></i></a>
            </td>
          </tr>
        </c:forEach>
        
          <tr>
            
          </tr>
        </tbody>
      </table>
      
      <div id="pagination" class="pagination"></div>
    </div>
    <!-- 分页切换表单 -->
    <form action="manualService/manualList.do" method="post" id="listForm">
      <input type="hidden" name="curPage">
      <input type="hidden" name="manualName" value="${requestScope.manualName}" />
    </form>
  </div>
  <script>
    var pageCount = ${requestScope.pData.pageCount};
    var pageSize = ${requestScope.pData.pageSize};
    var curPage = ${requestScope.pData.curPage};
    var recordCount = ${requestScope.pData.recordCount};
    var pDataStartIndex=${pData.startIndex};
  </script>
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/instruments_manual.min.js"></script>
</body>
</html>
