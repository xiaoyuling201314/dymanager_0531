<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="main-left">
  <div class="hd">
    <div id="timeNow"></div>
    <div class="menu"><i class="iconfont icon-list"></i></div>
  </div>
  <div class="nav">
    <ul>
    <c:forEach items="${sessionScope.userSession.rightList}" var="right" >
	     <c:if test="${right == 'D-1' }">
		      <li <c:if test="${nav eq 'userDetail'}">class="active"</c:if>><a href="userService/selectDetail.do?userName=${userSession.userName}"><i class="iconfont icon-user-info-o"></i>基本信息</a></li>
	     </c:if>
	      <c:if test="${right == 'D-2' }">
		      <li <c:if test="${nav eq 'viewPassword'}">class="active"</c:if>><a href="view/users_password_edit.jsp"><i class="iconfont icon-lock-o"></i>密码设置</a></li>
	     </c:if>
	 </c:forEach>
      <c:if test="${sessionScope.userSession.superAdmin=='1' }">
       	<li <c:if test="${nav eq 'userList'}">class="active"</c:if>><a href="userService/userList.do"><i class="iconfont icon-user-add"></i>用户列表</a></li><!-- 新增用户 -->
      </c:if>
     <li ><a href="view/index.jsp"><i class="iconfont icon-home-o"></i>返回首页</a></li>
    </ul>
  </div>
</div>