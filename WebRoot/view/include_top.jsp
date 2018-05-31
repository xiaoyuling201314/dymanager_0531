<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="header">
  <div class="fl">
    <a href="view/index.jsp">
      <div class="logo"></div>
      <h2>仪器信息管理系统</h2>  
    </a>
  </div>
  <div class="fr user-info">
    <div class="info">
      <span><i class="iconfont icon-user-avatar"></i></span><!--  class="avatar" class="arrow" -->
      <span>${userSession.userName}-系统管理员</span>
      <span ><i class="iconfont icon-arrow-down"></i></span>
    </div>
    <div class="logout" id="logout">
    <a href="userService/logout.do">
      <i class="iconfont icon-logout"></i>
      <span>退出系统</span>
    </a>
    </div>
  </div>
</div>