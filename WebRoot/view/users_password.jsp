<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
	<%@ include file="/view/common/head.jsp" %>
  	<title>密码设置 - 用户管理 - 仪器信息管理系统</title>
	<% request.setAttribute("nav", "viewPassword"); %>
  </head>
 <body>
<%@ include file="/view/common/top.jsp" %>
<%@include file="/view/include_left_users.jsp" %>
  <div class="main-content users">
    <div class="page-head">密码设置</div>
    <div class="page-content">
      <div class="row">
        <div class="col-md-offset-2 col-md-8">
          <form>
            <div class="form-group">
              <label class="form-label col-md-3">用户名</label>
              <div class="col-md-9">
                <input type="text" class="form-control" value="${userSession.userName}">
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">密码</label>
              <div class="col-md-9">
                <input type="password" class="form-control" value="${userSession.password}">
              </div>
            </div>
            <br>
            <div class="form-group">
              <div class="col-md-offset-5 col-md-6">
                <a href="view/users_password_edit.jsp" class="btn btn-primary">修改</a>
              </div>
            </div>
          </form>
         
        </div>
      </div>
    </div>
  </div>
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/users_password.min.js"></script>
</body>
</html>
