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
  <title>新增 - 物料管理 - 仪器信息管理系统</title>
  <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
  <script type="text/javascript">
    $(function(){
      $("#test").on("click",function(){
	     	$("#drawingsForm").on("click",function(){
	     	 $(this).submit();
	     	})
      })
    });
  </script>
  </head>
 <body>
  <!-- <div id="top"><%@include file="/view/include_top.jsp" %></div>
  <div id="left"><%@include file="/view/include_left.jsp" %></div> -->
  <div id="materialDraws" class="main-content materials">
    <div class="page-head">
      <div class="row">
        <div class="col-md-3">物料管理 / 新增图纸</div>
        <div class="col-md-offset-6 col-md-3 text-right">
          <a href="#drawingAddModal" class="btn btn-primary" data-action="modal">新增</a>
        </div>
      </div>
    </div>
    <div class="page-content">
      <table class="table">
        <thead>
          <tr>
            <th>序号</th>
            <th>图纸名称</th>
            <th>版本号</th>
            <th>修订人</th>
            <th>修订记录</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody id="drawsForm">
      		<c:forEach items="${drawsList}" var="drawsing" varStatus="indexId">
      	<tr>
            <td>${indexId.index+1}</td>
            <td>${drawsing.drawingName }</td>
            <td>${drawsing.version }</td>
            <td>${drawsing.reviser }</td>
            <td>${drawsing.revisedRecord }</td>
            <td>
              <a class="delete" data-id="${drawsing.drawingName }"><i class="iconfont icon-delete"></i></a>
            </td>
          </tr>
      		</c:forEach>
        </tbody>
      </table>
      <br>
      <div class="text-center">
        <a href="view/materials_add.jsp" class="btn btn-green">保存</a>
        <a href="view/materials_add.jsp" class="btn btn-primary">返回</a>
      </div>
      
      <div class="modal" id="drawingAddModal" >
        <div class="modal-title primary">图纸新增</div>
        <div class="modal-content">
          <div class="row">
            <div class="col-md-offset-1 col-md-10">
              <form action="materielDrawService/uploadMaterielDraws.do" method="post" enctype="multipart/form-data" id="drawingsForm">
                <div class="form-group">
                  <label class="form-label col-md-3">图纸文件名称</label>
                  <div class="col-md-5">
                    <input type="text" class="form-control"  name="drawingName">
                  </div>
                  <div class="col-md-4">
                    <div class="file-control">
                      <input type="file" name="myFile" onchange="selectFile()" >浏览...
                    </div>
                    <div class="file-type">
                      <i id="fileicon" class="iconfont icon-pdf"></i>
                    </div>
                  </div>
                </div>
                <div class="form-group">
                  <label class="form-label col-md-3">版本号</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="version" placeholder="请输入版本号">
                  </div>
                </div>
                <div class="form-group">
                  <label class="form-label col-md-3">修订人</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="reviser" placeholder="请输入修订人">
                  </div>
                </div>
                <div class="form-group">
                  <label class="form-label col-md-3">修订记录</label>
                  <div class="col-md-9">
                    <textarea rows="2" class="form-control" name="revisedRecord"></textarea>
                  </div>
                </div>
                 <div class="modal-action">
		            <button type="submit" class="btn btn-primary" data-dismiss="modal" id="test">保存</button>
		            <button type="button" data-dismiss="modal" class="btn btn-orange">取消</button>
		          </div>
              </form>
            </div>
          </div>
         
        </div>
      </div>
    </div>
  </div>
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/materials_drawing.min.js"></script>
</body>
</html>
