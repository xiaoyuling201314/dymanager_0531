<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
   <%@ include file="/view/common/head.jsp" %>
  <title>新增 - 仪器列表 - 仪器信息管理系统</title>
 <script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath %>js/instruments_add.js"></script>
 <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
   <style type="text/css">
		label[id*='-error']{
		  color: red;
		} 
	</style>
  <script type="text/javascript">
   var basePath = '<%=basePath%>';
   var sapNoValue=$("[name=sapNo]").val();
  </script>
   <% request.setAttribute("nav", "instruments"); %>
  </head>
  
 <body>
<%@ include file="/view/common/top.jsp" %>
<%@ include file="/view/common/left.jsp" %>
  <div class="main-content instruments">
    <div class="page-head">仪器列表 / 新增</div>
    <div class="page-content">
      <div class="row">
        <form action="instrumentService/addInstru.do" method="post" enctype="multipart/form-data" class="instrumentsForm" onsubmit="return validate();" >
            <div class="form-group" style="height: 38px;" >
              <label class="form-label col-md-2">仪器名称<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control" required="required" name="productName" placeholder="请输入仪器名称">
              </div>
              <label class="form-label col-md-2">SAP代码<strong style="color: red;">*</strong></label>
              <div class="col-md-4" style="height: 38px;" > 
                <input type="text" class="form-control" required="required" name="sapNo" id="mysapNo" placeholder="请输入SAP代码">
                <i class="right"><img width='20px' height='20px' style='position: relative;top:-30px ;left: 305px;' src='dist/img/right.png'></img></i>
                <div class="error" ><img width='20px' height='20px' style='position: relative;top:-30px ;left: 100%;' src='dist/img/errorred.png'/>
                <label style='color: red;position: relative;top:-35px ;left: 100%;' class="sapNoMessage"  ></label></div>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-2">品牌<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <select id="" class="form-control" name="brand">
                  <option value="达元">达元</option>
                  <option value="绿洲">绿洲</option>
                  <option value="天绿">天绿</option>
                  <option value="绿卡">绿卡</option>
                </select>
              </div>

              <label class="form-label col-md-2">产品线<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <select id="" class="form-control" name="productLinel">
                  <option value="农药残留快检系列">农药残留快检系列</option>
                  <option value="保化快速检测系列">保化快速检测系列</option>
                  <option value="微生物快检系列">微生物快检系列</option>
                   <option value="劣质油快筛系列">劣质油快筛系列</option>
                  <option value="重金属快检系列">重金属快检系列</option>
                  <option value="非法添加快检系列">非法添加快检系列</option>
                   <option value="兽残与疫病快检系列">兽残与疫病快检系列</option>
                  <option value="现场便携综合解决方案">现场便携综合解决方案</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-2">上市日期<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control" required="required" name="listedTime" onclick="WdatePicker();" placeholder="请输入上市日期">
              </div>

              <label class="form-label col-md-2">仪器状态<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <select id="statusSelect" class="form-control" name="state">
                  <option value="0">在售</option>
                  <option value="1">退市</option>
                </select>
              </div>
            </div>
            <div class="form-group hide" id="statusRelated">
              <label class="form-label col-md-2">退市日期<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control" name="delistingDate" onclick="WdatePicker();" placeholder="请输入退市日期">
              </div>
            </div>
              <div class="form-group">
              <label class="form-label col-md-2">裸机重量</label>
              <div class="col-md-4">
                <input type="text" class="form-control"  name="netWeight" placeholder="请输入裸机重量">
              </div>

              <label class="form-label col-md-2">裸机尺寸</label>
              <div class="col-md-4">
                <input type="text" class="form-control"  name="netSize"  placeholder="请输入裸机尺寸">
              </div>
            </div>
            <div class="form-group" style="height: 38px;">
              <label class="form-label col-md-2">装箱重量</label>
              <div class="col-md-4">
                <input type="text" class="form-control"  name="grossWeight" placeholder="请输入装箱重量">
              </div>

              <label class="form-label col-md-2">装箱尺寸</label>
              <div class="col-md-4">
                <input type="text" class="form-control"  name="grossSize"  placeholder="请输入装箱尺寸">
              </div>
            </div>
            <div class="form-group" style="height: 38px;">
              <label class="form-label col-md-2">执行标准/备案号</label>
              <div class="col-md-4">
                <input type="text" class="form-control" name="executionStandard">
              </div>
              <div class="col-md-4" style="height: 38px;">
                <div class="file-control">
                  <input type="file" name="myFile" onchange="selectInstrumentFile();" >浏览...
                </div>
                <div class="file-type" onmousemove="showDeletepic()" onmouseout="hiddelDeletepic()" onclick="deleteFile();">
                  <i id="fileicon-pdf"  class="iconfont icon-pdf"></i>
                  <i id="fileicon-word" class="iconfont icon-word"></i>
                  <div class="deletFile" ><img width='20px' height='20px' style='position: relative;top:-35px ;left: 40%;' src='dist/img/errorred.png'/>
              </div>
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
              <label class="form-label col-md-2">仪器图片</label><!-- <strong style="color: red;">*</strong> -->
              <div class="col-md-10">
              <input type="hidden" name="picture" autocomplete="off">
                <div class="uploader">
                  <div id="picUpload"></div>
                </div>
              </div>
            </div>
         
            <div class="form-group">
              <div class="col-md-offset-5 col-md-6" style="margin-left: 40%;">
              <!--<a id="btnSave" class="btn btn-green">保存</a> -->
                <input type="submit" value="保存" id="btnSave" class="btn btn-green"/>&nbsp;&nbsp;&nbsp;&nbsp;
                <a id="returnbtn" class="btn btn-primary">返回</a>
              </div>
            </div>
        </form>
      </div>
    </div>
  </div>
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/instruments_add.min.js"></script>
</body>
</html>
