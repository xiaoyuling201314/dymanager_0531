<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
	<%@ include file="/view/common/head.jsp" %>
  <title>物料管理 - 仪器信息管理系统</title>
  <script type="text/javascript" src="<%=basePath %>js/materDrawing.js"></script>
  <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
  </head>
 <body>
<%@ include file="/view/common/top.jsp" %>
<%@include file="/view/common/left.jsp" %>
  <div class="main-content materials">

    <div class="page-head">
      <div class="row">
        <div class="col-md-3">物料管理</div>
        <div class="col-md-6">
          <form class="search" action="materielService/materielList.do" method="post" autocomplete="off">
            <input type="text" placeholder="请输入搜索关键词" value="${materielName}" name="materielName" class="form-control round">
            <i  id="button" class="iconfont icon-search"></i>
          </form>
        </div>
        <div class="col-md-3 text-right">
          <a href="materielService/exportMateriel.do" class="btn btn-green">导出</a>
          <a href="materieTypeService/queryAll.do" class="btn btn-primary">新增</a>
        </div>
      </div>
    </div>
    <div class="page-content">
      <div class="subtitle">物料详细信息</div>
      <table class="table">
        <thead>
          <tr>
            <th>序号</th>
            <th>SAP代码</th>
            <th>类别</th>
            <th>物料名称</th>
            <th>型号/规格</th>
            <th>封装</th>
            <th>备注</th>
            <th>图片</th>
            <th>图纸</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody id="materielList">
        <c:set var="serNo" value="${pData.startIndex+1}" ></c:set>
         <c:forEach items="${requestScope.pData.itemsData }" var="materials" varStatus="indexId" >
          <tr>
             <td>${serNo+indexId.index}</td>
            <td>${materials.materielNo }</td>
            <td>${materials.materielTypeId.materielTypeName }</td>
            <td style="text-align: left;width: 20%;">${materials.materielName }</td>
            <td style="text-align: left;width: 20%;">${materials.modelSpecification }</td>
            <td style="text-align: left;width: 12%;">${materials.footprint }</td>
            <td style="text-align: left;width: 12%;">${materials.comment}</td>
            <td class="gallery">
              <c:forEach items="${materials.picture}" var="pict" varStatus="index">
                <c:if test="${index.index==0 }"><a href="${picturePath}${pict}"><img width="20px" height="20px" alt="图片" src="dist/img/pic.png"></a></c:if>
           		<c:if test="${index.index!=0 }"><a style="display: none;" href="${picturePath}${pict}">${pict}</a></c:if>
              </c:forEach>
            </td>
            <td><c:if test="${materials.drawings!=null && materials.drawings.size()>0 }">
            <a href="#drawingModal" class="viewDrawing" data-id="${materials.materielNo }" data-action="modal">
            <img width="20px" height="20px" alt="图纸" src="dist/img/draws.png">
            </a></c:if>
            </td>
            <td>
              <a href="materielService/selectDetail.do?materielNo=${materials.materielNo }"><i class="iconfont icon-edit"></i></a>
              <a class="delete" data-id="${materials.materielNo }"><i class="iconfont icon-delete"></i></a>
            </td>
          </tr>
         
         </c:forEach>
         
          
        </tbody>
      </table>
      <div id="pagination" class="pagination"></div>
    </div>

    <div class="modal" id="drawingModal">
      <div class="modal-title">图纸下载</div>
      <div class="modal-content">
        <table class="table">
          <thead>
            <tr>
              <th>序号</th>
              <th>图纸名称</th>
              <th>版本号</th>
              <th>修订人</th>
              <th>上传时间</th>
              <th>
                <div class="checkbox checkbox-white">
                  <input type="checkbox" id="selectAll"  onchange="sel(this)">
                  <label for="selectAll"></label>
                </div>
              </th>
            </tr>
          </thead>
          <tbody id="drawsList">
          
          </tbody>
        </table>
        <div class="modal-action">
          <a id="btnDownload" data-dismiss="modal" class="btn btn-green">下载</a>
          <a class="btn btn-primary" data-dismiss="modal">返回</a>
        </div>
      </div>
      
    </div>
    <!-- 分页切换表单 -->
   <form action="materielService/materielList.do" method="post" id="materForm">
      <input type="hidden" name="curPage">
      <input type="hidden" name="materielName" value="${materielName}"  />
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
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/materials.min.js"></script>
</body>
</html>
