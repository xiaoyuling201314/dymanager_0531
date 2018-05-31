<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
  <%@ include file="/view/common/head.jsp" %>
  <title>密码设置 - 用户管理 - 仪器信息管理系统</title>
	<script type="text/javascript" src="<%=basePath %>js/password_edit.js"></script>
  </head>
<body>
<% request.setAttribute("nav", "viewPassword"); %>
<%@ include file="/view/common/top.jsp" %>
<%@include file="/view/include_left_users.jsp" %>
  <div class="main-content users">
    <div class="page-head">密码设置</div>
    <div class="page-content">
      <div class="row">
        <div class="col-md-offset-2 col-md-8">
          <form action="userService/modifyPassword.do" method="post" class="userForm">
            <div class="form-group">
              <label class="form-label col-md-3">原密码<strong style="color: red;">*</strong></label>
              <div class="col-md-9" style="height: 38px;">
                <input type="password" class="form-control" required="required" name="password" placeholder="请输入原密码">
                <div class="error" >
                   <label style='color: red;position: relative;top:-35px ;left: 103%;' class="passwordMessage"  ></label>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">新密码<strong style="color: red;">*</strong></label>
              <div class="col-md-9" style="height: 38px;">
                <input type="password" class="form-control" required="required" name="newPassword" placeholder="请输入新密码">
                 <div class="error" >
                   <label style='color: red;position: relative;top:-35px ;left: 103%;' class="newPasswordMessage"  ></label>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">确认密码<strong style="color: red;">*</strong></label>
              <div class="col-md-9" style="height: 38px;">
                <input type="password" class="form-control" required="required" name="rePassword" placeholder="请再次输入新密码">
                 <div class="error" >
                   <label style='color: red;position: relative;top:-35px ;left: 103%;' class="rePasswordMessage"  ></label>
                </div>
              </div>
            </div>
            <br>
            <div class="form-group">
              <div class="col-md-offset-5 col-md-6">
                <input type="button" id="btnSave" class="btn btn-green" value="保存"/>
               <!--  <a href="view/users_password.jsp" class="btn btn-primary">返回</a> -->
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
