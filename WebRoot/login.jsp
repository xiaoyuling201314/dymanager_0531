<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
  <%@ include file="/view/common/head.jsp" %>
  <title>登录 - 仪器信息管理系统</title>
  <script type="text/javascript" src="<%=basePath %>dist/js/jquery.min.js"></script>
  <script type="text/javascript" src="<%=basePath %>dist/js/jquery.cookie.js"></script>
  <script type="text/javascript" src="<%=basePath %>js/login.js"></script>
  <script type="text/javascript">
     $(function(){
       var messgae="${error}";
       if(messgae!=''){
       alert(messgae);
          $.removeCookie("userName",{path:"/"});
          $.removeCookie("password",{path:"/"});
          $.removeCookie("rememberPwd",{path:"/"});
       <% request.getSession().removeAttribute("error"); %>
       }
       //绑定回车登录事件
       $(document).keydown(function(event){
        if(event.keyCode==13){
        // alert("13");
         $("#btnLogin").click();
        }
       });
     });
  </script>
</head>

<body class="bg-white">
  <div class="page page-login">
    <div class="logo text-hide">食安科技</div>
    <div class="bd">
      <div class="container relative">
        <div class="login-bg text-hide">仪器信息管理系统</div>
        <div class="login-box">
          <div class="login-title text-hide">用户登录</div>
          <form action="userService/login.do" method="post" id="formLogin">
         <label id="message" style="color:red;"></label>
            <div class="form-group">
              <input type="text" class="form-control" id="userName" name="userName"  placeholder="请输入用户名" maxlength="64">
              <i class="iconfont icon-user"></i>
            </div>
            <div class="form-group">
              <input type="password" class="form-control" maxlength="16" id="userPwd" name="password"  placeholder="请输入密码" autocomplete="off">
              <i class="iconfont icon-lock"></i>
            </div>
            <div class="form-group">
              <div class="checkbox checkbox-primary">
                <input type="checkbox" id="remPwd" onchange="remberPwd();" name="rememberMe"/>
                <label for="remPwd">记住密码</label>  
              </div>
            </div>
            <div class="form-group">
              <button type="button" class="btn btn-primary btn-block" id="btnLogin">登录</button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <div class="copy">广东达元食品药品安全技术有限公司 &copy; 版权所有</div>
  </div>
</body>
</html>
