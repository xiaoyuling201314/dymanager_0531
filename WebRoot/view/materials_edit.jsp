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
  <title>编辑 - 物料管理 - 仪器信息管理系统</title>
    <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
  <script type="text/javascript" src="<%=basePath %>js/material_edit.js"></script>
  
  <script type="text/javascript">
    var basePath = '<%=basePath%>';
    var countPicture="${countPicture}";
    var drawsCount="${drawsCount}";
    var drawsIndex=0;
    $(function(){
      	$("#materialDraws").attr("style","display:none");
		$(".deletFile").attr("style","display:none");
		$(".right").attr("style","display:none");
	  	$(".error").attr("style","display:none");
	  	$("#fileicon-pdf").attr("style","display:none");
	  	$(".deleteMpic").attr("style","display:none");
    });
  </script>
  </head>
 <body>
 <% request.setAttribute("nav", "materials"); %>
<%@ include file="/view/common/top.jsp" %>
<%@ include file="/view/common/left.jsp" %>
  <div id="materials" class="main-content materials">
    <div class="page-head">物料管理 / 编辑</div>
    <div class="page-content">
      <div class="row">
        <div class="col-md-offset-2 col-md-8">
          <form action="materielService/updateMateriel.do"  method="post" enctype="multipart/form-data" id="materials-form" onsubmit="return validate();">
           <input type="hidden" value="${materielDTO.picture }" name="oldPicture" />
            <div class="form-group">
              <label class="form-label col-md-3">SAP代码<strong style="color: red;">*</strong></label>
              <div class="col-md-9" style="height: 38px;">
                <input type="text" class="form-control" value="${materielDTO.materielNo}" name="materielNo" disabled="disabled">
                <input type="hidden" value="${materielDTO.materielNo}" name="materielNo" />
                <!--  <i class="right"><img width='20px' height='20px' style='position: relative;top:-30px ;left: 30%;' src='dist/img/right.png'></img></i>
                <i class="error"><img width='20px' height='20px' style='position: relative;top:-30px ;left: 450px;' src='dist/img/error.png'></img></i> -->
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3" >类别<strong style="color: red;">*</strong></label>
              <div class="col-md-9">
                <select id="" class="form-control" name="materielTypeId.materielTypeId">
                <c:forEach items="${requestScope.materTypeList}" var="materType" >
                <option  value="${materType.materielTypeId}" <c:if test="${materType.materielTypeId==materielDTO.materielTypeId.materielTypeId}">selected="selected"</c:if>>${materType.materielTypeName}</option>
                </c:forEach>
                
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">物料名称<strong style="color: red;">*</strong></label>
              <div class="col-md-9" style="height: 38px;">
                <input type="text" class="form-control" value="${materielDTO.materielName}" name="materielName" required="required">
                <div class="check" >
              <label style='color: red;position: relative;top:-30px ;left: 103%;' class="materielNameMessage"  ></label>
            </div>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">型号/规格</label>
              <div class="col-md-9">
                <input type="text" class="form-control" value="${materielDTO.modelSpecification}" name="modelSpecification">
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">封装</label>
              <div class="col-md-9">
                <input type="text" class="form-control" value="${materielDTO.footprint }" name="footprint">
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">图片</label>
              <div class="col-md-9">
               <input type="hidden" name="picture" value="${materielDTO.picture}" autocomplete="off">
                <input type="hidden" name="delpicture" autocomplete="off">
                <ul class="pics">
	               <c:forEach items="${materielDTO.picture}" var="pict" varStatus="index">
	              	 <li class="listPic" style="width: 100px;height: 100px"  onmousemove="showDeleteMpic(this)" onmouseout="hiddelDeleteMpic(this)" onclick="deleteMpic(this);">
	              	 <img width="94px" height="90px" title="图片" alt="图片" data-id="${pict}" src="${picturePath}${pict}">
	              	  <div class="deleteMpic" >
	              	  <img width='20px' height='20px' style='position: relative;top:-102px ;left: 85%;' src='dist/img/errorred.png'/>
	                </div>
	              	 </li>
	              	</c:forEach>
                </ul>
                <div class="uploader">
                  <div id="picUpload"></div>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">图纸</label>
              <div class="col-md-9">
                <a id="addDraws" data-id="${materielDTO.materielNo}"  class="btn btn-block btn-set">点击查看图纸</a>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">备注</label>
              <div class="col-md-9">
                <textarea rows="2" class="form-control" name="comment"  >${materielDTO.comment }</textarea>
              </div>
            </div>
            <br>
            <div class="form-group">
              <div class="col-md-offset-5 col-md-6">
              <!--  <input type="submit" class="btn btn-green" value="保存"/> -->
               <a id="addMater" class="btn btn-green">保存</a>
                <a id="returnbtn" class="btn btn-primary">返回</a>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
  
  <!-- 新增物料物质信息 -->
  <div id="materialDraws" class="main-content materials">
    <div class="page-head">
      <div class="row">
        <div class="col-md-3">物料管理 / 新增图纸</div>
        <div class="col-md-offset-6 col-md-3 text-right">
          <a href="#drawingAddModal" class="btn btn-primary" data-action="modal">新增图纸</a>
        </div>
      </div>
    </div>
    <div class="page-content">
      <table class="table">
        <thead>
          <tr>
            <th>序号</th>
            <th>图纸名称</th>
            <th>版本号</th>
            <th>修订人</th>
            <th>修订记录</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody id="drawsForm">
      	 <!-- <c:forEach items="${materielDTO.drawings}" var="drawsing" varStatus="indexId">
      		<tr>
            <td>${indexId.index+1}</td>
            <td>${drawsing.drawingName }</td>
            <td>${drawsing.version }</td>
            <td>${drawsing.reviser }</td>
            <td>${drawsing.revisedRecord }</td>
            <td>
              <a class="deleteDraws" data-id="${drawsing.id }"><i class="iconfont icon-delete"></i></a>
            </td>
           </tr>
      	</c:forEach> -->
        </tbody>
      </table>
      <br>
      <div class="text-center">
        <!-- <a id="saveFrom" class="btn btn-green">保存</a> -->
        <a id="returnForm" class="btn btn-primary">返回</a>
      </div>
      
      <div class="modal" id="drawingAddModal" style="height: 450px;" >
        <div class="modal-title primary">图纸新增</div>
        <div class="modal-content">
          <div class="row">
            <div class="col-md-offset-1 col-md-10">
              <form enctype="multipart/form-data" id="drawingsForm" >
              <input type="hidden" name="materielNo" value="${materielDTO.materielNo}" />
                <div class="form-group" style="height: 38px;">
                  <label class="form-label col-md-3">图纸文件名称<strong style="color: red;">*</strong></label>
                  <div class="col-md-5">
                    <input type="text" class="form-control" required="required"  name="drawingName">
                  </div>
                  <div class="col-md-4" style="height: 38px;">
                    <div class="file-control">
                      <input type="file" name="myFile"  class="fileicon2"  onchange="selectDawsFile();" required="required" >浏览...
                    </div>
                    <div class="file-type" onmousemove="showDeletepic()" onmouseout="hiddelDeletepic()" onclick="deleteFile();">
                       <i class="fileicon"><img width="35px" height="34px;" style="position: relative;top: -6px;" src="dist/img/common.jpg"></img></i>
                       <div class="deletFile" ><img width='20px' height='20px' style='position: relative;top:-56px ;left: 48%;' src='dist/img/errorred.png'/>
		                </div>
                    </div>
                  </div>
                 <div class="check" >
		              <label style='color: red;position: relative;top:-35px ;left: 90%;' class="drawingNameMessage"  ></label>
		            </div>
                </div>
                <div class="form-group">
                  <label class="form-label col-md-3">版本号<strong style="color: red;">*</strong></label>
                  <div class="col-md-9" style="height: 38px;">
                    <input type="text" class="form-control" required="required" name="version" placeholder="请输入版本号">
                   <div class="check" >
		              <label style='color: red;position: relative;top:-30px ;left: 100%;' class="versionMessage"  ></label>
		            </div>
                  </div>
                </div>
                <div class="form-group" style="height: 38px;">
                  <label class="form-label col-md-3">修订人<strong style="color: red;">*</strong></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" required="required" name="reviser" placeholder="请输入修订人" value="${userSession.userName}">
                  <div class="check" >
		              <label style='color: red;position: relative;top:-30px ;left: 100%;' class="reviserMessage"  ></label>
		            </div>
                  </div>
                </div>
                <div class="form-group" style="height: 38px;">
                  <label class="form-label col-md-3">修订记录<strong style="color: red;">*</strong></label>
                  <div class="col-md-9">
                    <textarea rows="2" class="form-control" name="revisedRecord"></textarea>
                  </div>
                     <div class="check" >
		              <label style='color: red;position: relative;top:-30px ;left: 97%;' class="revisedRecordMessage"  ></label>
		           </div>
                </div>
              </form>
              <div class="modal-action">
		            <button type="button" class="btn btn-primary"  id="saveDraws">保存</button><!-- data-dismiss="modal" -->
		            <button type="button" data-dismiss="modal" class="btn btn-orange">取消</button>
		          </div>
            </div>
          </div>
         
        </div>
      </div>
    </div>
  </div>
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/materials_edit.min.js"></script>
</body>
</html>
