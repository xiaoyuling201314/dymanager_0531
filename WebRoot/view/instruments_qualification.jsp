<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
<%@ include file="/view/common/head.jsp" %>
  <title>仪器资质 - 仪器信息管理系统</title>
	<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
	<style type="text/css">
	table{
	 width: 100%;
	}
	  td{
	  border: 1px solid #000000;
	  padding: 5px;
	  }
	</style>
  </head>
  
 <body>
<%@ include file="/view/common/top.jsp" %>
<%@include file="/view/include_left_instruments.jsp" %>
  <div class="main-content instruments">
    <form action="instrumentService/instruList.do" method="post" id="listForm">
      <input type="hidden" name="curPage">
    </form>
    <div class="page-head">
      <div class="row">
        <div class="col-md-3">仪器信息 / 仪器资质</div>
        <div class="col-md-3 col-md-offset-6 text-right">
          <a href="certificateService/queryCertificate.do?sapNo=${instrumentDTO.sapNo }" class="btn btn-primary">新增资质</a>
          <a href="instrumentService/instruList.do?curPage=${returnPage}" class="btn btn-primary">返回</a>
        </div>
      </div>
    </div>
    <div class="page-content">
      <div class="subtitle">仪器详细信息</div>
      <table class="table">
        <thead>
          <tr>
            <th>SAP代码</th>
            <th>仪器名称</th>
            <th>品牌</th>
            <th>产品线</th>
            <th>状态</th>
            <th>执行标准/标准备案号</th>
            <!-- <th>备注</th> -->
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>${instrumentDTO.sapNo }</td>
            <td>${instrumentDTO.productName }</td>
            <td>${instrumentDTO.brand }</td>
            <td>${instrumentDTO.productLinel }</td>
            <td><c:if test="${instrumentDTO.state=='0' }">在售</c:if>
            <c:if test="${instrumentDTO.state=='1' }">退市</c:if> </td>
            <td>${instrumentDTO.executionStandard }</td><!-- <a href="${implementStandards}${instrumentDTO.executionStandard }"  target="_blank" ></a>-->
            <!-- <td>${instrumentDTO.comment }</td> -->
            <td>
              <a href="instrumentService/editInstru.do?sapNo=${instrumentDTO.sapNo }"><i class="iconfont icon-edit"></i></a>
               <a href="#viewDetailModal" data-action="modal"><i class="iconfont icon-details"></i></a>
            </td>
          </tr>
          <tr>
            
          </tr>
        </tbody>
      </table>
      <div class="subtitle">仪器专利/证书</div>
      <table class="table">
        <thead>
          <tr>
            <th>专利/证书名称</th>
            <th>资质归属</th>
            <th>类型</th>
            <th>登记日期</th>
            <th>有效日期</th>
            <th>上传时间</th>
            <th>修订人</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody id="certificateForm">
         <c:forEach items="${requestScope.pData.itemsData }" var="certificate">
             <tr>
            <td style="text-align: left;width:35%;">${certificate.calibrationCertificate}</td><!-- class="underline" href="certificateService/queryCertificate.do?id=${certificate.id}&typeStr=edit" -->
            <td>${certificate.qualificationAttribution}</td>
            <td>${certificate.certificateType.certificateName}</td>
            <td>${certificate.calibStartTime}</td>
            <td>${certificate.calibEndTime}${certificate.validCalib}</td>
            <td>${certificate.updateTime}</td>
            <td>${certificate.reviser}</td>
            <td>
              <a href="certificateService/downloadCert.do?id=${certificate.id}"><i class="iconfont icon-export"></i></a>
              <a class="delete" data-id="${certificate.id}"><i class="iconfont icon-delete"></i></a>
            </td>
          </tr>
         </c:forEach>
        </tbody>
      </table>
      <div id="pagination" class="pagination"></div>
      <!-- 查看仪器详情 -->
       <div class="modal" id="viewDetailModal" style="height: 500px;" >
        <div class="modal-title primary">${instrumentDTO.productName }</div>
        <div class="modal-content">
            <table style="border: 1px solid #000000">
              <tr >
              <td>仪器名称</td>
              <td>${instrumentDTO.productName }</td>
               <td>SAP代码</td>
              <td>${instrumentDTO.sapNo }</td>
              </tr>
              
               <tr >
              <td>品牌</td>
              <td>${instrumentDTO.brand}</td>
               <td>产品线</td>
              <td>${instrumentDTO.productLinel}</td>
              </tr>
              
                <tr >
              <td>上市日期</td>
              <td>${instrumentDTO.listedTime }</td>
               <td>仪器状态</td>
              <td><c:if test="${instrumentDTO.state=='0' }">在售</c:if>
              <c:if test="${instrumentDTO.state=='1' }">退市</c:if>
              </td>
              </tr>
              
              <tr >
              <td>裸机重量</td>
              <td>${instrumentDTO.netWeight}</td>
               <td>裸机尺寸</td>
              <td>${instrumentDTO.netSize}</td>
              </tr>
              
              <tr >
              <td>装箱重量</td>
              <td>${instrumentDTO.grossWeight}</td>
               <td>装箱尺寸</td>
              <td>${instrumentDTO.grossSize}</td>
              </tr>
              
               <c:if test="${instrumentDTO.state=='0' }">
               <tr>
                <td>执行标准/备案号</td>
                <td colspan="3">${instrumentDTO.executionStandard}</td>
                </tr>
               </c:if>
               <c:if test="${instrumentDTO.state=='1' }">
               <tr>
                <td>执行标准/备案号</td>
                <td>${instrumentDTO.executionStandard}</td>
                 <td>退市日期</td>
              	<td>${instrumentDTO.delistingDate}</td>
              	</tr>
               </c:if>
              
              <tr >
              <td>备注</td>
              <td colspan="3">${instrumentDTO.comment}</td>
              </tr>
              <!-- update by xiaoyuling 2016-12-27 start-->
               <tr>
               <td>仪器图片</td>
              <td colspan="3" class="gallery" >
               <c:forEach items="${instrumentDTO.picture}" var="picture" varStatus="indexId">
                 <c:if test="${indexId.index<4 }">
                <a style="width: 24.36%;margin: 1px 5px;" href="${picturePath}${picture}"><img width="94px" height="90px" src="${picturePath}${picture}" alt="图片" title="图片"></a>
               </c:if>
              </c:forEach>
              </td>
            </tr>
            <!-- update by xiaoyuling 2016-12-27 end-->
            </table>
         </div>
           <!--  <label class="form-label col-md-2">仪器图片</label>
              <div class="col-md-10">
                <ul class="gallery" style="padding: 0px;margin: 0px;">
                    <c:forEach items="${instrumentDTO.picture}" var="picture" varStatus="indexId">
		               <c:if test="${indexId.index<4 }">
		              	 <li class="listPic" style="width: 100px;height: 100px;float: left;"  >
		              	   <img width="94px" height="90px" src="${picturePath}${picture}" title="图片" alt="图片" data-id="${picture}">
		                 </li>
		               </c:if>
             		 </c:forEach>
                </ul>
              </div>   -->
              <div class="modal-action" style="margin-top: -3%;">
		            <button type="button" data-dismiss="modal" class="btn btn-primary" >返回</button>
		          </div>
          </div>
       </div>
        </div>
   <script>
    var pageCount = ${requestScope.pData.pageCount};
    var pageSize = ${requestScope.pData.pageSize};
    var curPage = ${requestScope.pData.curPage};
    var recordCount = ${requestScope.pData.recordCount};
  </script>
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/instruments_qualification.min.js"></script>
</body>
</html>
