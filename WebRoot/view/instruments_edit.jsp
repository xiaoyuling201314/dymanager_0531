<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
  <%@ include file="/view/common/head.jsp" %>
  <title>编辑 - 仪器资质 - 仪器信息管理系统</title>
  <script type="text/javascript" src="<%=basePath%>My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
   <script type="text/javascript" src="<%=basePath %>js/instruments_edit.js"></script>
   <style type="text/css">
		label[id*='-error']{
		  color: red;
		} 
		
	</style>
  <script type="text/javascript">
   	var basePath = '<%=basePath%>';
	var countPicture = "${countPicture}";
	var executionStandard = "${instrumentDTO.executionStandard }";
	var state="${instrumentDTO.state}";
	var sapNo="${instrumentDTO.sapNo}";

  </script>
  </head>
  
 <body>
<% request.setAttribute("nav", "instruments_qualification"); %>
<%@ include file="/view/common/top.jsp" %>
<%@include file="/view/include_left_instruments.jsp" %>
  <div class="main-content instruments">
    <div class="page-head">仪器信息 / 编辑</div>
    <div class="page-content">
      <div class="row">
        <form action="instrumentService/updateInstru.do" method="post" enctype="multipart/form-data" onsubmit="return validate();">
            <div class="form-group" style="height: 38px;">
              <label class="form-label col-md-2">仪器名称<strong style="color: red;">*</strong></label>
              <div class="col-md-4" style="height: 38px;" >
                <input type="text" class="form-control" name="productName" value="${instrumentDTO.productName }" required="required">
              </div>

              <label class="form-label col-md-2">SAP代码<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control" name="sapNo" value="${instrumentDTO.sapNo }" disabled="disabled">
                <input type="hidden" name="sapNo" value="${instrumentDTO.sapNo }" />
                 <!--    <i class="right"><img width='20px' height='20px' style='position: relative;top:-30px ;left: 305px;' src='dist/img/right.png'></img></i>
                <div class="error" ><img width='20px' height='20px' style='position: relative;top:-30px ;left: 100%;' src='dist/img/error.png'/>
                <label style='color: red;position: relative;top:-35px ;left: 100%;' class="sapNoMessage"  ></label>
                </div> -->
                
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-2">品牌<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <select id="" class="form-control" name="brand">
          		 <option value="达元" <c:if test="${instrumentDTO.brand=='达元' }">selected="selected"</c:if>>达元</option>
                  <option value="绿洲" <c:if test="${instrumentDTO.brand=='绿洲' }">selected="selected"</c:if>>绿洲</option>
                  <option value="天绿" <c:if test="${instrumentDTO.brand=='天绿' }">selected="selected"</c:if>>天绿</option>
                  <option value="绿卡" <c:if test="${instrumentDTO.brand=='绿卡' }">selected="selected"</c:if>>绿卡</option>
                </select>
              </div>

              <label class="form-label col-md-2">产品线<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <select id="" class="form-control" name="productLinel">
                  <option value="农药残留快检系列" <c:if test="${instrumentDTO.productLinel=='农药残留快检系列' }">selected="selected"</c:if>>农药残留快检系列</option>
                  <option value="保化快速检测系列" <c:if test="${instrumentDTO.productLinel=='保化快速检测系列' }">selected="selected"</c:if>>保化快速检测系列</option>
                  <option value="微生物快检系列" <c:if test="${instrumentDTO.productLinel=='微生物快检系列' }">selected="selected"</c:if>>微生物快检系列</option>
                   <option value="劣质油快筛系列" <c:if test="${instrumentDTO.productLinel=='劣质油快筛系列' }">selected="selected"</c:if>>劣质油快筛系列</option>
                  <option value="重金属快检系列" <c:if test="${instrumentDTO.productLinel=='重金属快检系列' }">selected="selected"</c:if>>重金属快检系列</option>
                  <option value="非法添加快检系列" <c:if test="${instrumentDTO.productLinel=='非法添加快检系列' }">selected="selected"</c:if>>非法添加快检系列</option>
                   <option value="兽残与疫病快检系列" <c:if test="${instrumentDTO.productLinel=='兽残与疫病快检系列' }">selected="selected"</c:if>>兽残与疫病快检系列</option>
                  <option value="现场便携综合解决方案" <c:if test="${instrumentDTO.productLinel=='现场便携综合解决方案' }">selected="selected"</c:if>>现场便携综合解决方案</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-2">上市日期<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control" name="listedTime" onclick="WdatePicker();" value="${instrumentDTO.listedTime }">
              </div>

              <label class="form-label col-md-2">仪器状态<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <select id="statusSelect" class="form-control"  name="state">
                  <option value="0" <c:if test="${instrumentDTO.state=='0' }">selected="selected"</c:if>>在售</option>
                  <option value="1" <c:if test="${instrumentDTO.state=='1' }">selected="selected"</c:if>>退市</option>
                </select>
              </div>
            </div>
            
             <div class="form-group hide" id="statusRelated">
              <label class="form-label col-md-2">退市日期<strong style="color: red;">*</strong></label>
              <div class="col-md-4">
                <input type="text" class="form-control" name="delistingDate" value="${instrumentDTO.delistingDate }" onclick="WdatePicker();" placeholder="请输入退市日期">
              </div>
            </div>
              <div class="form-group">
              <label class="form-label col-md-2">裸机重量</label>
              <div class="col-md-4">
                <input type="text" class="form-control"  name="netWeight" value="${instrumentDTO.netWeight}" placeholder="请输入裸机重量">
              </div>

              <label class="form-label col-md-2">裸机尺寸</label>
              <div class="col-md-4">
                <input type="text" class="form-control"  name="netSize" value="${instrumentDTO.netSize}"  placeholder="请输入裸机尺寸">
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-2">装箱重量</label>
              <div class="col-md-4">
                <input type="text" class="form-control"  name="grossWeight" value="${instrumentDTO.grossWeight}" placeholder="请输入装箱重量">
              </div>

              <label class="form-label col-md-2">装箱尺寸</label>
              <div class="col-md-4">
                <input type="text" class="form-control" name="grossSize" value="${instrumentDTO.grossSize}"  placeholder="请输入装箱尺寸">
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-2">执行标准/备案号</label>
              <div class="col-md-4">
                <input type="text" class="form-control" name="executionStandard" value="${instrumentDTO.executionStandard }">
              </div>
              <div class="col-md-4" style="height: 38px;">
                <div class="file-control">
                  <input type="file" onchange="selectInstrumentFile();" name="myFile" >浏览...
                </div>
                <div class="file-type" onmousemove="showExecutionDeletepic()" onmouseout="hiddelExecutionDeletepic()" onclick="deleteFile();">
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
                <textarea rows="3" class="form-control" name="comment">${instrumentDTO.comment }</textarea>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-2">仪器图片</label><!-- <strong style="color: red;">*</strong> -->
              <div class="col-md-10">
                <ul class="pics" style="padding: 0px;margin: 0px;">
                    <c:forEach items="${instrumentDTO.picture}" var="picture" varStatus="indexId">
		               <c:if test="${indexId.index<4 }">
		              	 <li class="listPic" style="width: 100px;height: 100px"  onmousemove="showDeletepic(this)" onmouseout="hiddelDeletepic(this)" onclick="deleteMpic(this);">
		              	 <img width="94px" height="90px" src="${picturePath}${picture}" title="${picture}" alt="${picture}" data-id="${picture}">
		              		<div class="deleteMpic" >
			              	  <img width='20px' height='20px' style='position: relative;top:-102px ;left: 85%;' src='dist/img/errorred.png'/>
			                </div>
		              		 </li>
		               </c:if>
             		 </c:forEach>
                </ul>
                 <input type="hidden" name="picture" value="${instrumentDTO.picture }" autocomplete="off">
                 <input type="hidden" name="delpicture" autocomplete="off">
                <div class="uploader">
                  <div id="picUpload"></div>
                </div>
              </div>
            </div>
         
            <div class="form-group">
              <div class="col-md-offset-5 col-md-6" style="margin-left: 40%;">
               <!--  <a id="btnSave" class="btn btn-green">保存</a> -->
                <input type="submit" class="btn btn-green" value="保存" />&nbsp;&nbsp;&nbsp;&nbsp;
                <a id="returnbtn" class="btn btn-primary">返回</a>
              </div>
            </div>
        </form>
      </div>
    </div>
  </div>
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/instruments_edit.min.js"></script>
</body>
</html>
