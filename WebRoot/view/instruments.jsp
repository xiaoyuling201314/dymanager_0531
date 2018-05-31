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
  <title>仪器列表 - 仪器信息管理系统</title>
  <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
  
  <style type="text/css">
 
  </style>
</head>
<body>
<%@ include file="/view/common/top.jsp" %>
<%@ include file="/view/common/left.jsp" %>

<div class="main-content instruments">

  <div class="page-head">
    <div class="row">
      <div class="col-md-3">仪器列表</div>
      <div class="col-md-6">
          <form class="search" action="instrumentService/instruList.do" method="post" autocomplete="off">
            <input type="text" placeholder="请输入搜索关键词" name="instruMentName" value="${requestScope.instruMentName}" class="form-control round" >
            <i class="iconfont icon-search" id="button"></i>
          </form>
        </div>
      <div class="col-md-3  text-right"><!-- col-md-offset-6 -->
        <a href="view/instruments_add.jsp" class="btn btn-primary">新增</a>
      </div>
    </div>
  </div>
  <div class="page-content">
    <div class="row" id="instruMentList">
     <c:forEach items="${requestScope.pData.itemsData }" var="instrument" >
      	<div class="col-md-6">
        <table <c:if test="${instrument.state=='0'}">class="table active list" </c:if>  <c:if test="${instrument.state=='1'}">class="table inactive list" </c:if>  >
          <thead>
            <tr>
             <th colspan="2"><a  href="instrumentService/queryInstrument.do?sapNo=${instrument.sapNo}">${instrument.productName}</a>
                <span class="close" data-id="${instrument.sapNo}"><i class="iconfont icon icon-close-circle"></i></span>
              </th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>SAP代码</td>
              <td>${instrument.sapNo}</td>
            </tr>
            <tr>
              <td>品牌</td>
              <td>${instrument.brand}</td>
            </tr>
            <tr>
              <td>产品线</td>
              <td>${instrument.productLinel}</td>
            </tr>
            <tr>
              <td>上市日期</td>
              <td>${instrument.listedTime}</td>
            </tr>
            <tr>
              <td>仪器状态</td>
              <td><c:if test="${instrument.state=='0'}">在售 </c:if>
              <c:if test="${instrument.state=='1'}">退市 </c:if></td>
            </tr>
            <tr align="left">
              <td colspan="2" class="gallery" style="height: 65px;">
              <c:forEach items="${instrument.picture}" var="picture" varStatus="indexId">
               <c:if test="${indexId.index<4 }">
                <a style="width: 24.36%;" href="${picturePath}${picture}"><img style="width: 80%;" src="${picturePath}${picture}" alt="图片" title="图片"></a>
               </c:if>
              </c:forEach>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      </c:forEach>
      
    </div>
    <div class="text-center">
      <div id="pagination" class="pagination"></div>
    </div>
  </div>
    <!-- 分页切换表单 -->
	<form action="instrumentService/instruList.do" method="post" id="listForm">
      <input type="hidden" name="curPage">
      <input type="hidden" name="instruMentName" value="${requestScope.instruMentName}" />
    </form>
</div>
<script>
    var pageCount = ${requestScope.pData.pageCount};
    var pageSize = ${requestScope.pData.pageSize};
    var curPage = ${requestScope.pData.curPage};
    var recordCount = ${requestScope.pData.recordCount};
  </script>
<script src="dist/js/lib/require.min.js" data-main="dist/js/page/instruments.min.js"></script>
</body>
</html>
