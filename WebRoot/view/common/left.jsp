<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="main-left" >
  <div class="hd">
    <div id="timeNow" ></div>
    <div class="menu"><i class="iconfont icon-list"></i></div>
  </div>
  <div class="nav">
    <ul>
     <li <c:if test="${nav eq 'home'}">class="active"</c:if>>
      <a href="view/index.jsp"><i class="iconfont icon-home-o"></i>首页</a>
      </li>
    <c:forEach items="${sessionScope.rightList}" var="rightId" >
	     <c:if test="${rightId == 'A' }">
		       <li id="A" <c:if test="${nav eq 'instruments'}">class="active"</c:if> >
		      <a href="instrumentService/instruList.do">
		      <i class="iconfont icon-apps-o"></i>仪器列表</a></li>
	     </c:if>
    
	     <c:if test="${rightId == 'B' }">
		      <li id="B" <c:if test="${nav eq 'reagent_home'}">class="active"</c:if> style="display: none;">
		      <a href="view/reagent_home.jsp"><i class="iconfont icon-reagent-o" id=""></i>试剂列表</a>
		      </li>
	     </c:if>
      	<c:if test="${rightId == 'E' }">
	      <li id="E" >
		      <a href="shipmentsService/shipList.do" <c:if test="${nav eq 'shield'}">class="active"</c:if>>
		      <i><img alt="" src="dist/img/shield.png" width="21px;" height="20px;" style="vertical-align: middle;"  ></i>品质管理</a>
	      </li>
    	 </c:if>
	      <c:if test="${rightId == 'C' }">
	      <li id="C" <c:if test="${nav eq 'materials'}">class="active"</c:if>>
		      <a href="materielService/materielList.do" >
		      <i class="iconfont icon-cart-o" ></i>物料管理</a>
	      </li>
    	 </c:if>
     
	      <c:if test="${rightId == 'D' }">
	       <li id="D">
	        <a href="userService/selectDetail.do?userName=${userSession.userName}">
	        <i class="iconfont icon-set-o"></i>用户管理</a>
	      </li>
	     </c:if>
    </c:forEach>
     
    </ul>
  </div>
</div>