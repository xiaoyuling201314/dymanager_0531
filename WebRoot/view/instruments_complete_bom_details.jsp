<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
   	<%@ include file="/view/common/head.jsp" %>
	<title>整机BOM单 - 仪器信息管理系统</title>
    <script type="text/javascript" src="<%=basePath %>js/instrument_completer_bom_detail.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
    <style type="text/css">
     .showMaterielStyle{
       text-align: left;
       width:25%;
      }
     .showCommentStyle{
       text-align: left;
       width:15%;
      }
    </style>
 
   <script type="text/javascript">
      $(function(){
       //提交搜索表单
		$("#button").on("click",function(){
			var completeKeys=$("[name=completeKeys]").val();
			var id=$("[name=id]").val();
			var path=location.href=basePath+"completeService/selectComplete.do?id="+id+"&completeKeys="+completeKeys;
			//window.location.href=path;
			location.href=path;

		});
      });
    </script>
  </head>
  
  <body onload="initData();">
<%@ include file="/view/common/top.jsp" %>
<%@include file="/view/include_left_instruments.jsp" %>
  <div class="main-content instruments">
  
    <!-- 物料查询分页切换表单 -->
    <form action="completeService/selectMateriel.do" method="post" id="listMaterForm">
      <input type="hidden" name="curPage" value="1">
    </form>
    <div class="page-head">
      <div class="row">
        <div class="col-md-3">整机BOM单</div>
        <div class="col-md-6">
          <form class="search" autocomplete="off" name="materDetailSearch"><!-- action="completeService/selectComplete.do" method="post"  -->
            <input type="hidden" value="${comDto.id}" name="id" />
            <input type="text" placeholder="请输入搜索关键词" name="completeKeys" value="${completeKeys}" class="form-control round">
            <i class="iconfont icon-search" id="button"></i>
          </form>
        </div>
        <div class="col-md-3 text-right">
          <a href="#addModal" class="btn btn-primary" id="selectMateriel" data-action="modal">添加物料</a>
          <a href="completeService/completeList.do" class="btn btn-primary">返回</a>
        </div>
      </div>
    </div>
    <div class="page-content" id="detailPage">
      <div class="subtitle" style="float: left;">仪器整机BOM单详细信息</div> <div style="margin-left: 90%;"><input type="button" id="importComplete" value="导入BOM" class="btn btn-primary"></div>
      <table class="table">
        <thead>
          <tr>
            <th>序号</th>
            <th>SAP代码</th>
            <th>物料名称</th>
            <th>型号/规格</th>
            <th>数量</th>
            <th>备注</th>
            <th>图片</th>
            <th>图纸</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody id="completerForm">
        <c:set var="serNo" value="${pData.startIndex+1}" ></c:set>
         <c:forEach items="${requestScope.pData.itemsData }" var="completerDetail" varStatus="indexId">
            <tr>
            <td>${serNo+indexId.index}</td>
            <td>${completerDetail.materielNo.materielNo }</td>
            <td class="showMaterielStyle">${completerDetail.materielNo.materielName }</td>
            <td class="showMaterielStyle">${completerDetail.materielNo.modelSpecification }</td>
            <td class="edit" id="quantity" data-id="${completerDetail.id }">${completerDetail.quantity }</td>
            <td class="edit showCommentStyle" data-id="${completerDetail.id }">${completerDetail.comment }</td>
            <td class="gallery">
              <c:forEach items="${completerDetail.materielNo.picture}" var="pict" varStatus="index">
                <c:if test="${index.index==0 }"><a href="${picturePath}${pict}"><img width="20px" height="20px" alt="图片" src="dist/img/pic.png"></a></c:if>
           		<c:if test="${index.index!=0 }"><a style="display: none;" href="${picturePath}${pict}">${pict}</a></c:if>
              </c:forEach>
            </td>
            <td>
            <c:if test="${completerDetail.materielNo.drawings!=null && completerDetail.materielNo.drawings.size()>0 }">
            <a href="#drawingModal" class="viewDrawing" data-id="${completerDetail.materielNo.materielNo }" data-action="modal">
            <img width="20px" height="20px" alt="图纸" src="dist/img/draws.png">
            </a></c:if>
            </td>
            <td style="text-align:center;">
              <a class="delete" data-id="${completerDetail.id }"><i class="iconfont icon-delete"></i></a>
            </td>
          </tr>
         </c:forEach>
        </tbody>
      </table>
      <div id="pagination" class="pagination"></div>
    </div>
    <!-- 导入BOM信息 -->
      <div class="page-content">
      <form enctype="multipart/form-data" id="completeForm">
         <input type="hidden" value="${comDto.id}" name="completeId" />
          <div class="form-group"  style="height: 38px;">
            <label class="form-label col-md-3">整机BOM单文件</label>
            <div class="col-md-5">
              <input type="text" class="form-control"  name="fileName">
            </div>
            <div class="col-md-4" style="height: 38px;">
              <div class="file-control">
                <input type="file" name="myFile" onchange="selectExcelFile();" >浏览...
              </div>
              <div class="file-type" onmousemove="showDeletepic()" onmouseout="hiddelDeletepic()" onclick="deleteFile();">
                <i class="fileicon"><img width="35px" height="34px;" style="position: relative;top: -6px;" src="dist/img/excel.png"></img></i>
                 <div class="deletFile" ><img width='20px' height='20px' style='position: relative;top:-55px ;left: 40%;' src='dist/img/errorred.png'/>
		         </div>
              </div>
            </div>
            <div >
              <label style='color: red;position: relative;top:-38px ;left: 81%;' class="fileNameMessage"  ></label>
            </div>
          </div>
           <div class="form-group" >
              <div class="col-md-offset-5 col-md-6" >
                 <a  class="btn btn-green" id="btnSave" style="position: relative;left: -30%;">导入</a>
                <input type="button" id="returnDetail" class="btn btn-primary" value="返回" style="position: relative;left: -20%;" />
              </div>
            </div>
          </form>
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
                  <input type="checkbox" id="selectAll"  onchange="selDraws(this)">
                  <label for="selectAll"></label>
                </div>
              </th>
            </tr>
          </thead>
          <tbody id="drawsList">
            
          </tbody>
        </table>
        <div class="modal-action">
          <a id="btnDownload" class="btn btn-green" data-dismiss="modal">下载</a>
          <a class="btn btn-primary" data-dismiss="modal">返回</a>
        </div>
      </div>
    </div>

    <div class="modal lg" id="addModal" style="height: 95%;overflow: scroll;">
      <div class="modal-content search-modal">
        <div class="row">
          <div class="col-md-offset-2 col-md-8">
            <form class="search" method="post" autocomplete="off">
              <div class="form-group">
                <label class="col-md-2 form-label">物料查询</label>
                <div class="col-md-10">
                  <input type="text" placeholder="请输入搜索关键词" name="materielKeys" class="form-control round">
                  <i id="search" class="iconfont icon-search"></i>
                </div>
              </div>
            </form>
          </div>
          <c:if test="${isShow=='YES'}">
           <a href="materieTypeService/queryAll.do" class="btn btn-primary" data-dismiss="modal">新增物料</a>
         </c:if>
        </div>
        <!--  <div class="modal-action" id="floatButton">
          <input type="hidden" name="completeId" value="${comDto.id }" />
          <a id="btnConfirm" data-dismiss="modal" class="btn btn-green" style="margin-left: -24px;">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <a class="btn btn-primary" data-dismiss="modal">返回</a>
        </div> -->
        <br>
        <br>
        <br>
        <table class="table" id="selectMaterList" style="margin-top: -5%">
          <thead>
            <tr>
              <th>序号</th>
              <th>SAP代码</th>
              <th>类别</th>
              <th>物料名称</th>
              <th>型号/规格</th>
              <th>备注</th>
              <th>图片</th>
              <th>图纸</th>
              <th style="width: 50px;">
                <div class="checkbox checkbox-white" style='margin-left:20px;'>
                  <input type="checkbox" id="selectAll2"  onchange="sel(this)">
                  <label for="selectAll2"></label>
                </div>
              </th>
            </tr>
          </thead>
          <tbody id="materForm">
           
          </tbody>
        </table>
         <div class="modal-action" id="floatButton">
          <input type="hidden" name="completeId" value="${comDto.id }" />
          <a id="btnConfirm" data-dismiss="modal" class="btn btn-green" style="margin-left: -24px;">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <a class="btn btn-primary" data-dismiss="modal">返回</a>
        </div>
         <div id="paginationSelect" class="pagination" style="margin-top: -0.5%;"></div>
        <div id='paginationSelect2' class='pagination' style="margin-top: -0.5%;"></div>
      </div>
   
    </div>
    <!-- 分页切换表单 -->
    <form action="completeService/selectComplete.do" method="post" id="listForm">
      <input type="hidden" name="id" value="${comDto.id}" />
      <input type="hidden" name="curPage">
      <input type="hidden" name="completeKeys" value="${requestScope.completeKeys}"  />
   </form>
  </div>
 <script>
   var pageCount = ${requestScope.pData.pageCount};
	    var pageSize = ${requestScope.pData.pageSize};
	    var curPage = ${requestScope.pData.curPage};
	    var recordCount = ${requestScope.pData.recordCount};
	    var basePath = '<%=basePath%>';
	    var pDataStartIndex=${pData.startIndex};
	      //物料检索分页参数
	    var pageCountMateriel = ${requestScope.pDataMateriel.pageCount};
	    var pageSizeMateriel = ${requestScope.pDataMateriel.pageSize};
	    var curPageMateriel = ${requestScope.pDataMateriel.curPage};
	    var recordCountMateriel = ${requestScope.pDataMateriel.recordCount};
	    var pDataMaterStartIndex=${requestScope.pDataMateriel.startIndex};
	  
  </script>

  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/instruments_complete_bom_details.min.js"></script>
</body>
</html>
