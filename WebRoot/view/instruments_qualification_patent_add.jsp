<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
   <%@ include file="/view/common/head.jsp" %>
  <title>资质 - 仪器资质 - 仪器信息管理系统</title>
  <script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
  <script type="text/javascript" src="<%=basePath %>js/instruments_qualification_patent_add.js"></script>
  <script type="text/javascript">
    var basePath='<%=basePath %>';
    	$(function(){
    		$("#fileicon-pdf").attr("style", "display:none");
			$("#fileicon-word").attr("style", "display:none");
			$(".deletFile").attr("style", "display:none");
		  $("#returnbtn").on("click",function(){
			     if($("[name=calibrationCertificate]").val()!=''){
			        if(confirm("确定放弃编辑返回吗？")){
			           location.href=basePath+"instrumentService/queryInstrument.do?sapNo=${instruSapNo}";
			        }
			       }else{
			          location.href=basePath+"instrumentService/queryInstrument.do?sapNo=${instruSapNo}";
			       }
		     });
	});
  </script>

  </head>
 <body>
 <% request.setAttribute("nav", "instruments_qualification"); %>
<%@ include file="/view/common/top.jsp" %>
<%@ include file="/view/include_left_instruments.jsp" %>
  <div class="main-content instruments">
    <div class="page-head" >仪器信息 / 仪器资质 / 新增</div>
    <div class="page-content">
      <div class="row">
        <div class="col-md-offset-2 col-md-8">
          <form action="certificateService/addCertficate.do" method="post" enctype="multipart/form-data" id="qualification_Add">
          <input type="hidden" value="${sapNo }" name="sapNo" />
            <div class="form-group" style="height: 38px;">
              <label class="form-label col-md-3">专利/证书名称<strong style="color: red;">*</strong></label>
              <div class="col-md-5">
                <input type="text" class="form-control" name="calibrationCertificate" >
              </div>
              <div class="col-md-4" style="height: 38px;">
                <div class="file-control">
                  <input type="file" name="myFile" onchange="selectInstrumentFile();">浏览...
                </div>
                <div class="file-type">
                   <div class="file-type" onmousemove="showExecutionDeletepic()" onmouseout="hiddelExecutionDeletepic()" onclick="deleteFile();">
	                 <i id="fileicon-pdf"  class="iconfont icon-pdf"></i>
	                 <i id="fileicon-word" class="iconfont icon-word"></i>
                   <div class="deletFile" ><img width='20px' height='20px' style='position: relative;top:-35px ;left: 40%;' src='dist/img/errorred.png'/>
		         </div>
		         </div>
                </div>
              </div>
               <div class="check" >
              <label style='color: red;position: relative;top:-30px ;left: 90%;' class="calibrationCertificateMessage"  ></label>
            </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">类型<strong style="color: red;">*</strong></label>
              <div class="col-md-9">
                <select id="" class="form-control" name="certificateType.certificateNo">
                <c:forEach items="${cerTypeDTO}" var="cerType">
                 <option value="${cerType.certificateNo }">${cerType.certificateName }</option>
                </c:forEach>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">资质归属<strong style="color: red;">*</strong></label>
              <div class="col-md-9">
                <select id="" class="form-control" name="qualificationAttribution">
                  <option value="达元">达元</option>
                  <option value="绿洲">绿洲</option>
                  <option value="天绿">天绿</option>
                  <option value="绿卡">绿卡</option>
                </select>
              </div>
            </div>
            <div class="form-group" style="height: 38px;">
              <label class="form-label col-md-3">修订人<strong style="color: red;">*</strong></label>
              <div class="col-md-9">
                <input type="text" class="form-control"  name="reviser" value="${userSession.userName}">
                <div class="error" >
	              <label style='color: red;position: relative;top:-30px ;left: 103%;' class="reviserMessage"  ></label>
	            </div>
              </div>
            </div>
            <div class="form-group" style="height: 38px;">
              <label class="form-label col-md-3">登记日期<strong style="color: red;">*</strong></label>
              <div class="col-md-9"  style="height: 38px;">
                <input id="startTime" type="text" class="form-control" onclick="WdatePicker();" name="calibStartTime" placeholder="请选择登记日期" >
                <div class="error" >
	              <label style='color: red;position: relative;top:-30px ;left: 103%;' class="calibStartTimeMessage"  ></label>
	            </div>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">有效日期</label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="calibEndTime" onclick="WdatePicker();" placeholder="请选择有效日期，默认为永久有效">
              </div>
            </div>
           
            <br>
            <div class="form-group">
              <div class="col-md-offset-5 col-md-6" style="margin-left: 40%;">
               <a id="btnSave"  class="btn btn-green">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
                <a id="returnbtn" class="btn btn-primary">返回</a>
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
