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
	    <c:choose>
	       <c:when test="${right == 'E-1' && nav eq 'shield'}">
		      <li <c:if test="${nav eq 'shield'}">class="active"</c:if> >
		      <a href="shipmentsService/shipList.do">
		      <i><img alt="" src="dist/img/car_white.png" width="21px;" height="22px;" style="vertical-align: middle;" ></i>出货记录</a></li>
	       </c:when>
	       <c:when test="${right == 'E-1' && nav !='shield'}">
		        <li <c:if test="${nav eq 'shield'}">class="active"</c:if> >
			     <a href="shipmentsService/shipList.do">
			     <i><img alt="" src="dist/img/car_blue.png" width="21px;" height="22px;" style="vertical-align: middle;z-index: 100;" ></i>出货记录</a>
			     </li>
	       </c:when>
	       <c:when test="${right == 'E-2' }">
	          <li <c:if test="${nav eq 'repair'}">class="active"</c:if>><a href="repairService/repairList.do">
		      <i class="iconfont icon-settings"></i>维修记录</a></li>
	       </c:when>
	       <c:when test="${right == 'E-3' }">
	         <li <c:if test="${nav eq 'statics'}">class="active"</c:if>><a href="view/statistical_analysis_ship.jsp">
		      <i class="iconfont icon-statistic"></i>统计分析</a></li>
	       </c:when>
	       <c:otherwise></c:otherwise>
	    </c:choose>
	 </c:forEach>
     <li ><a href="view/index.jsp"><i class="iconfont icon-home-o"></i>返回首页</a></li>
    </ul>
  </div>
</div>