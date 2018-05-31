<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
	<%@ include file="/view/common/head.jsp" %>
  <title>新增 - 仪器资质 - 仪器信息管理系统</title>
  <script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
  </head>
  
 <body>
<%@ include file="/view/common/top.jsp" %>
<%@include file="/view/include_left_instruments.jsp" %>
  <div class="main-content instruments">
    <div class="page-head">仪器资质 / 新增</div>
    <div class="page-content">
      <div class="row">
        <form action="instrumentService/updateInstru.do" method="post">
            <div class="form-group">
              <label class="form-label col-md-2">仪器名称</label>
              <div class="col-md-4">
                <input type="text" class="form-control" required="required" name="productName">
              </div>

              <label class="form-label col-md-2">SAP代码</label>
              <div class="col-md-4">
                <input type="text" class="form-control" required="required" name="sapNo">
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-2">品牌</label>
              <div class="col-md-4">
                <select id="" class="form-control" name="brand">
                  <option value="">品牌一</option>
                  <option value="">品牌二</option>
                  <option value="">品牌三</option>
                </select>
              </div>

              <label class="form-label col-md-2">产品线</label>
              <div class="col-md-4">
                <select id="" class="form-control" name="productLinel">
                  <option value="">产品线一</option>
                  <option value="">产品线二</option>
                  <option value="">产品线三</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-2">上市日期</label>
              <div class="col-md-4">
                <input type="text" class="form-control" required="required" value="listedTime"/>
              </div>

              <label class="form-label col-md-2">仪器状态</label>
              <div class="col-md-4">
                <select id="" class="form-control" name="state">
                  <option value="0">在售</option>
                  <option value="1">退市</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-2">执行标准/备案号</label>
              <div class="col-md-4">
                <input type="text" class="form-control" name="executionStandard">
              </div>
              <div class="col-md-4">
                <div class="file-control">
                  <input type="file">浏览...
                </div>
                <div class="file-type">
                  <i class="iconfont icon-pdf"></i>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-2">备注</label>
              <div class="col-md-8">
                <textarea rows="3" class="form-control" name="comment"></textarea>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-2">仪器图片</label>
              <div class="col-md-10">
                <ul class="pics">
                  <li><img src="dist/img/temp/test1.jpg"></li>
                  <li><img src="dist/img/temp/test2.png"></li>
                </ul>
                <div class="uploader">
                  <div id="picUpload"></div>
                </div>
              </div>
            </div>
         
            <div class="form-group">
              <div class="col-md-offset-5 col-md-6">
               <!--  <a id="btnSave" class="btn btn-green">保存</a> -->
                <input type="submit" class="btn btn-green" value="保存" />
                <a href="instrumentService/queryInstrument.do?sapNo=${instrumentDTO.sapNo }" class="btn btn-primary">返回</a>
              </div>
            </div>
        </form>
      </div>
    </div>
  </div>
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/instruments_qualification_edit.min.js"></script>
</body>
</html>
