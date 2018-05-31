<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
	<%@ include file="/view/common/head.jsp" %>
   <title>基本信息 - 用户管理 - 仪器信息管理系统</title>
  <script type="text/javascript">
   $(function(){
   var object="${sessionScope.userSession.rightList}".split(",");
	  for(var i=0;i<object.length;i++){
	  	$("#"+object[i]+"").attr("checked","checked");
	  }
    });
  </script>
  </head>
<body>
<%@ include file="/view/common/top.jsp" %>
<%@include file="/view/include_left_users.jsp" %>
  <div class="main-content users">
    <div class="page-head">基本信息</div>
    <div class="page-content">
      <div class="row">
        <div class="col-md-offset-2 col-md-8">
          <form>
            <div class="form-group">
              <label class="form-label col-md-3">用户名</label>
              <div class="col-md-8">
                <input type="text" class="form-control" value="${sysUserDTO.userName }" readonly>
              </div>
            </div>
              <div class="form-group">
              <label class="form-label col-md-3">姓名</label>
              <div class="col-md-8">
                <input type="text" class="form-control" value="${sysUserDTO.name }" readonly>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">所属部门</label>
              <div class="col-md-8">
                <select class="form-control" disabled >
                  <option value="${sysUserDTO.department}" >${sysUserDTO.department}</option>
                </select>
              </div>
            </div>
          
            <div class="form-group">
              <label class="form-label col-md-3">电子邮件</label>
              <div class="col-md-8">
                <input type="text" class="form-control" value="${sysUserDTO.email }" readonly>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">手机号码</label>
              <div class="col-md-8">
                <input type="text" class="form-control" value="${sysUserDTO.phone }"  readonly>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">操作权限</label>
              <div class="col-md-8">
                <a href="#viewRights" class="btn btn-block btn-set" data-action="modal">点击查看</a>
              </div>
            </div>
          </form>
          <div class="modal" id="viewRights" ><!-- style="height: 95%;overflow: scroll;" -->
            <div class="modal-title">查看权限</div>
            <div class="modal-content">
              <div class="rights">
                <h5>仪器产品列表</h5>
                <div class="checkbox-inline">
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="A-1" disabled  >
                    <label for="ch1">仪器资质</label>
                  </div>
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="A-2" disabled >
                    <label for="A-2">仪器说明书</label>
                  </div>
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="A-3" disabled >
                    <label for="A-3">整机BOM单</label>
                  </div>
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="A-4" disabled >
                    <label for="A-4">电路板BOM单</label>
                  </div>
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="A-5" disabled >
                    <label for="A-5">装箱清单</label>
                  </div>
                   <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="A-6" disabled >
                    <label for="A-6">仪器设计文档</label>
                  </div>
                </div>
              </div>
              <div class="rights" style="display: none;">
                <h5>试剂产品列表</h5>
                <div class="checkbox-inline">
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="B-1" disabled >
                    <label for="B-1">试剂资质</label>
                  </div>
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="B-2" disabled >
                    <label for="B-2">试剂说明书</label>
                  </div>
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="B-3" disabled >
                    <label for="B-3">试剂BOM单</label>
                  </div>
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="B-4" disabled >
                    <label for="B-4">试剂装箱清单</label>
                  </div>
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="B-5" disabled >
                    <label for="B-5">试剂设计文档</label>
                  </div>
                </div>
              </div>
                <!--品质管理权限配置start 2016-11-24  -->
                <div class="rights">
                <h5>品质管理</h5>
                <div class="checkbox-inline">
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="E-1" disabled >
                    <label for="E-1">出货记录</label>
                  </div>
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="E-2" disabled >
                    <label for="E-2">维修记录</label>
                  </div>
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="E-3" disabled >
                    <label for="E-3">统计分析</label>
                  </div>
                </div>
              </div>
              <!--品质管理权限配置end 2016-11-24  -->
             <div class="rights">
                <h5>物料管理</h5>
                <div class="checkbox-inline">
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="C-1" disabled>
                    <label for="C-1">物料管理</label>
                  </div>
                </div>
              </div>
             <div class="rights">
                <h5>用户管理</h5>
                <div class="checkbox-inline">
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="D-1" disabled>
                    <label for="D-1">基本信息</label>
                  </div>
                  <div class="checkbox checkbox-primary" >
                    <input type="checkbox" id="D-2" disabled >
                    <label for="D-2">密码设置</label>
                  </div>
                </div>
                
              </div>
              <div class="modal-action">
                <button type="button" data-dismiss="modal" class="btn btn-primary">返回</button>
              </div>
            </div>
          <!--  -->
          </div>
        </div>
      </div>
    </div>
  </div>

  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/users.min.js"></script>
</body>
</html>
