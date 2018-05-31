<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
  <%@ include file="/view/common/head.jsp" %>
  <title>专利 - 仪器资质 - 仪器信息管理系统</title>
  <script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
  <style type="text/css">
		label[id*='-error']{
		  color: red;
		} 
  </style>
  </head>
 <body>
<%@ include file="/view/common/top.jsp" %>
<%@ include file="/view/include_left_instruments.jsp" %>
  
  <div class="main-content instruments">
    <div class="page-head">仪器信息 / 仪器资质 / 编辑</div>
    <div class="page-content">
      <div class="row">
        <div class="col-md-offset-2 col-md-8">
          <form action="certificateService/updateCert.do" method="post" enctype="multipart/form-data">
            <input type="hidden" value="${sapNo }" name="sapNo" />
            <div class="form-group">
              <label class="form-label col-md-3">专利/证书名称</label>
              <div class="col-md-5">
                <input type="text" class="form-control" value="${certificateDTO.calibrationCertificate }" name="calibrationCertificate">
              </div>
              <div class="col-md-4">
                <div class="file-control">
                  <input type="file" name="myFile" onchange="selectFile();">浏览...
                </div>
                <div class="file-type">
                  <i class="iconfont icon-pdf"></i>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">类型</label>
              <div class="col-md-9">
                <select id="" class="form-control"  name="certificateType.certificateNo" >
                 <c:forEach items="${requestScope.cerTypeDTO }" var="cerType">
                 <option <c:if test="${cerType.certificateNo==certificateDTO.certificateType.certificateNo }"> selected="selected" </c:if> value="${cerType.certificateNo }" >${certificateNo.certificateName }</option>
                </c:forEach>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">资质归属</label>
              <div class="col-md-9">
                <select id="" class="form-control" name="qualificationAttribution">
                   <option value="达元" <c:if test="${certificateDTO.qualificationAttribution=='达元' }">selected="selected"</c:if> >达元</option>
                   <option value="绿洲" <c:if test="${certificateDTO.qualificationAttribution=='绿洲' }">selected="selected"</c:if>>绿洲</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">修订人</label>
              <div class="col-md-9">
                <input type="text" class="form-control" value="${certificateDTO.reviser }" name="reviser">
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">登记日期</label>
              <div class="col-md-9">
                <input type="text" class="form-control" value="${certificateDTO.calibStartTime }" name="calibStartTime">
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">有效日期</label>
              <div class="col-md-9">
                <input type="text" class="form-control" value="${certificateDTO.calibEndTime }" name="calibEndTime">
              </div>
            </div>
           
            <br>
            <div class="form-group">
              <div class="col-md-offset-5 col-md-6">
               <!--  <a id="btnSave" class="btn btn-green">保存</a> -->
                <input type="submit" value="保存" class="btn btn-green"/>
                <a href="instrumentService/queryInstrument.do?sapNo=${certificateDTO.sapNo}" class="btn btn-primary">返回</a>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/instruments_qualification_patent_edit.min.js"></script>
</body>
</html>
