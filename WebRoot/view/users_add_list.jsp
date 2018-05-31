<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
  <%@ include file="/view/common/head.jsp" %>
  <title>新增用户 - 用户管理 - 仪器信息管理系统</title>
  <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
</head>
<body>
<%@ include file="/view/common/top.jsp" %>
  <%@include file="/view/include_left_users.jsp" %>
  <div class="main-content users list">

    <div class="page-head">
      <div class="row">
        <div class="col-md-3">用户列表</div>
        <div class="col-md-6">
          <form class="search" action="userService/userList.do" method="post" autocomplete="off">
            <input type="text" placeholder="请输入搜索关键词" name="userName"  value="${requestScope.userName}" class="form-control round">
            <i class="iconfont icon-search" id="button"></i>
          </form>
        </div>
        <div class="col-md-3 text-right">
          <a href="view/users_add.jsp" class="btn btn-primary">新增</a>
        </div>
      </div>
    </div>
    <div class="page-content">
      <table class="table">
        <thead>
          <tr>
            <th>序号</th>
            <th>用户名</th>
            <th>姓名</th>
            <th>电子邮箱</th>
            <th>所属部门</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody id="userList">
          <c:set var="serNo" value="${pData.startIndex+1}" ></c:set>
          <c:forEach items="${requestScope.pData.itemsData}" var="sysUser" varStatus="indexId">
          <tr>
            <td>${serNo+indexId.index}</td>
            <td><a href="userService/selectDetail.do?userName=${sysUser.userName}&type=view">${sysUser.userName}</a></td>
            <td>${sysUser.name}</td>
            <td>${sysUser.email}</td>
            <td>${sysUser.department}</td>
            <td>
            <c:set value="${sysUser.userName}" var="userName"></c:set>
              <a href="userService/editUser.do?userName=${sysUser.userName}"><i class="iconfont icon-edit"></i></a>
              <a class="delete" data-id="${sysUser.userName}"><i class="iconfont icon-delete"></i></a>
            </td>
          </tr>
          </c:forEach>
        </tbody>
      </table>
      <div class="text-center">
        <div id="pagination" class="pagination"></div>
      </div>
    </div>
  <!-- 分页切换表单 -->
    <form action="userService/userList.do" method="post" id="listForm">
      <input type="hidden" name="curPage">
      <input type="hidden"  name="userName"  value="${requestScope.userName}" />
    </form>
    </div>
  <script>
    var pageCount = ${requestScope.pData.pageCount};
    var pageSize = ${requestScope.pData.pageSize};
    var curPage = ${requestScope.pData.curPage};
    var recordCount = ${requestScope.pData.recordCount};
    var pDataStartIndex=${pData.startIndex};
  </script>
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/users_add_list.min.js"></script>
</body>
</html>
