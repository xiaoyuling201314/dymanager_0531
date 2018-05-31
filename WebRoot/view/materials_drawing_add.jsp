<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
	<%@ include file="/view/common/head.jsp" %>
  <title>图纸新增 - 物料管理 - 仪器信息管理系统</title>

  </head>
  
<body>
  <div id="top"><%@include file="/view/include_top.jsp" %></div>
  <div id="left"><%@include file="/view/include_left.jsp" %></div>
  <div class="main-content materials">
    <div class="page-head">物料管理 / 图纸新增</div>
    <div class="page-content">
      <div class="row">
        <div class="col-md-offset-2 col-md-8">
          <form action="materielDrawService/addSigleMaterielDraws.do" method="post">
        <!--   <input type="hidden" value="" name="materielNo" /> -->
            <div class="form-group">
              <label class="form-label col-md-3">图纸文件名称</label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="drawingName" placeholder="请输入图纸文件名称">
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
            <br>
            <div class="form-group">
              <div class="col-md-offset-5 col-md-6">
                <input type="submit" class="btn btn-green" value="保存" />
                <a href="view/materials_add.jsp" class="btn btn-primary">返回</a>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/materials_add_drawing.min.js"></script>
</body>
</html>
