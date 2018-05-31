<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
	<%@ include file="/view/common/head.jsp" %>
  <title>仪器设计文档 - 仪器信息管理系统</title>
	<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
  </head>
  
 <body>
<%@ include file="/view/common/top.jsp" %>
<%@include file="/view/include_left_instruments.jsp" %>
  <div class="main-content instruments">

    <div class="page-head">
      <div class="row">
        <div class="col-md-3">仪器设计文档</div>
        <div class="col-md-6">
          <form class="search" action="documentService/documentList.do" method="post" autocomplete="off">
            <input type="text" placeholder="请输入搜索关键词" name="documentName" value="${requestScope.documentName }" class="form-control round">
            <i class="iconfont icon-search" id="button"></i>
          </form>
        </div>
        <div class="col-md-3 text-right">
          <a href="view/instruments_document_add.jsp" class="btn btn-primary">新增设计文档</a>
         <!-- <a href="instrumentService/instruList.do" class="btn btn-primary">返回</a> -->
        </div>
      </div>
    </div>
    <div class="page-content">
      <div class="subtitle">仪器设计文档</div>
      <table class="table">
        <thead>
          <tr>
            <th>序号</th>
            <th>仪器设计文档名称</th>
            <th>版本号</th>
            <th>修订人</th>
            <th>上传时间</th>
            <th>修订记录</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody id="documentList">
         <c:set var="serNo" value="${pData.startIndex+1}" ></c:set>
        <c:forEach items="${pData.itemsData}" var="document" varStatus="indexId">
          <tr>
            <td>${serNo+indexId.index}</td>
            <td style="text-align: left;width:35%;">${document.fileName }</td>
            <td>${document.version }</td>
            <td>${document.reviser }</td>
            <td>${document.updateTime}</td>
            <td>${document.revisedRecord }</td>
            <td>
              <a href="documentService/downloadDocument.do?id=${document.id}"><i class="iconfont icon-export"></i></a>
              <a class="delete" data-id="${document.id }"><i class="iconfont icon-delete"></i></a>
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
    <form action="documentService/documentList.do" method="post" id="listForm">
      <input type="hidden" name="curPage">
      <input type="hidden" name="documentName" value="${requestScope.documentName }" />
    </form>
  </div>
  <script>
    var pageCount = ${requestScope.pData.pageCount};
    var pageSize = ${requestScope.pData.pageSize};
    var curPage = ${requestScope.pData.curPage};
    var recordCount = ${requestScope.pData.recordCount};
    var pDataStartIndex=${pData.startIndex};
  </script>
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/instruments_document.min.js"></script>
</body>
</html>
