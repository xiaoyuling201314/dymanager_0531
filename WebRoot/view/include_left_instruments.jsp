<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="main-left">
  <div class="hd">
    <div id="timeNow"></div>
    <div class="menu"><i class="iconfont icon-list"></i></div>
  </div>
  <div class="nav">
    <ul >
     <c:forEach items="${sessionScope.userSession.rightList}" var="right" >
	     <c:if test="${right == 'A-1' }">
		      <li style="height: 67px;" id="A-1" <c:if test="${nav eq 'instruments_qualification'}">class="active"</c:if>><a href="instrumentService/queryInstrument.do?sapNo=${instruSapNo }"><i class="iconfont icon-badge"></i>仪器资质</a></li>
	     </c:if>
	 	 <c:if test="${right == 'A-2' }">
		     <li style="height: 67px;" id="A-2" <c:if test="${nav eq 'instruments_manual'}">class="active"</c:if>><a href="manualService/manualList.do?sapNo=${instruSapNo }"><i class="iconfont icon-doc "></i>仪器说明书</a></li>
	     </c:if>
	     <c:if test="${right == 'A-3' }">
		      <li style="height: 67px;" id="A-3" <c:if test="${nav eq 'instruments_complete_bom'}">class="active"</c:if>><a href="completeService/completeList.do?sapNo=${instruSapNo }"><i class="iconfont icon-file"></i>整机BOM单</a></li>
	     </c:if>
	     <c:if test="${right == 'A-4' }">
		       <li  style="height: 67px;" id="A-4" <c:if test="${nav eq 'instruments_pcb_bom'}">class="active"</c:if>><a href="circuitBoardService/cirCuitList.do?sapNo=${instruSapNo }"><i class="iconfont icon-file"></i>电路板BOM单</a></li>
	     </c:if>
	     <c:if test="${right == 'A-5' }">
		         <li style="height: 67px;" id="A-5" <c:if test="${nav eq 'instruments_plist'}">class="active"</c:if>><a href="packlingService/packingList.do"><i class="iconfont icon-packing-list"></i>装箱清单</a></li>
	     </c:if>
	     <c:if test="${right == 'A-6' }">
		       <li style="height: 67px;" id="A-6" <c:if test="${nav eq 'instruments_document'}">class="active"</c:if>><a href="documentService/documentList.do"><i class="iconfont icon-files"></i>设计文档</a></li>
	     </c:if>
	  </c:forEach>
      <li style="height: 65px;" ><a href="view/index.jsp"><i  class="iconfont icon-home-o"></i>返回首页</a></li>
     
    </ul>
  </div>
</div>