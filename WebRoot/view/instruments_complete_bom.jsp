<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/view/common/head.jsp" %>
  	<title>整机BOM单 - 仪器信息管理系统</title>
   <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
  </head>
  
<body>
<%@ include file="/view/common/top.jsp" %>
 <%@include file="/view/include_left_instruments.jsp" %>
  <div class="main-content instruments">

    <div class="page-head">
      <div class="row">
        <div class="col-md-3">整机BOM单</div>
        <div class="col-md-6">
          <form class="search" action="completeService/completeList.do" method="post" autocomplete="off">
            <input type="text" placeholder="请输入搜索关键词" name="completeName" value="${completeName}" class="form-control round">
            <i class="iconfont icon-search" id="button"></i>
          </form>
        </div>
        <div class="col-md-3 text-right">
          <a href="view/instruments_complete_bom_add.jsp" class="btn btn-primary">新增BOM单</a>
       	 <!--  <a href="instrumentService/instruList.do" class="btn btn-primary">返回</a> -->
        </div>
      </div>
    </div>
    <div class="page-content">
      <div class="subtitle">整机BOM单版本信息</div>
      <table class="table">
        <thead>
          <tr>
            <th>编号</th>
            <th>整机BOM单名称</th>
            <th>版本号</th>
            <th>创建人</th>
            <th>上传时间</th>
            <th>修订记录</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody id="completeForm">
        <c:set var="serNo" value="${pData.startIndex+1}" ></c:set>
         <c:forEach items="${requestScope.pData.itemsData }" var="complete" varStatus="indexId">
           <tr>
             <td>${serNo+indexId.index}</td>
            <td style="text-align: left;width:35%;"><a href="completeService/selectComplete.do?id=${complete.id}">${complete.completeMachineName }</a></td>
            <td>${complete.completeMachineVersion }</td>
            <td>${complete.createPerson }</td>
            <td>${complete.updateTime }</td>
            <td>${complete.revisedRecord }</td>
            <td>
              <a href="#exportModal" class="choise" data-id="${complete.id}" data-action="modal"><i class="iconfont icon-export"></i></a><!--  completeService/exportCompleteBoard.do?compId=${complete.id}" -->
              <a href="completeService/selectComplete.do?id=${complete.id}"><i class="iconfont icon-details"></i></a>
              <a class="delete" data-id="${complete.id }"><i class="iconfont icon-delete"></i></a>
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
          <a id="btnDownload" class="btn btn-green" data-dismiss="modal" >下载</a>
          &nbsp;&nbsp;<a class="btn btn-primary" data-dismiss="modal">返回</a>
      </div>
    </div>
      <div id="pagination" class="pagination"></div>
    </div>
       <!-- 分页切换表单 -->
   <form action="completeService/completeList.do" method="post" id="listForm">
      <input type="hidden" name="curPage">
      <input type="hidden" name="completeName" value="${completeName}" />
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
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/instruments_complete_bom.min.js"></script>
</body>
</html>
