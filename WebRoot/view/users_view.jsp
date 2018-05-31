<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
	<%@ include file="/view/common/head.jsp" %>
  	<title>编辑 - 用户管理 - 仪器信息管理系统</title>
	<script type="text/javascript">
		$(function() {
			var object = "${sysUserDTO.rightList}".split(",");
			for ( var i = 0; i < object.length; i++) {//设置用户已有权限为选中状态
				$("#" + object[i] + "").attr("checked", "checked");
			}
			});
</script>
  </head>
  
<body>
<%@ include file="/view/common/top.jsp" %>
<%@include file="/view/include_left_users.jsp" %>
  <div class="main-content users">
    <div class="page-head">新增用户 / 编辑</div>
    <div class="page-content">
      <div class="row">
        <div class="col-md-offset-2 col-md-8">
          <form>
            <div class="form-group">
              <label class="form-label col-md-3">用户名</label>
              <div class="col-md-9">
                <input type="text" class="form-control" value="${sysUserDTO.userName }" readonly>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">密码</label>
              <div class="col-md-9">
                <input type="password" class="form-control" value="${sysUserDTO.password }" readonly>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">姓名</label>
              <div class="col-md-9">
                <input type="text" class="form-control" value="${sysUserDTO.name }" readonly>
              </div>
            </div>
   <!--          <div class="form-group">
              <label class="form-label col-md-3">办公电话</label>
              <div class="col-md-9">
                <input type="text" class="form-control" value="020-12341234" readonly>
              </div>
            </div> -->
            <div class="form-group">
              <label class="form-label col-md-3">电子邮件</label>
              <div class="col-md-9">
                <input type="text" class="form-control" value="${sysUserDTO.email }" readonly>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">手机号码</label>
              <div class="col-md-9">
                <input type="text" class="form-control" value="${sysUserDTO.phone }" readonly>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">所属部门</label>
              <div class="col-md-9">
                  <select id="" class="form-control" name="department" disabled>
                  <option <c:if test="${sysUserDTO.department=='管理中心' }">selected="selected"</c:if>   value="管理中心">管理中心</option>
                  <option <c:if test="${sysUserDTO.department=='研发中心' }">selected="selected"</c:if> value="研发中心" >研发中心</option>
                  <option <c:if test="${sysUserDTO.department=='营销中心' }">selected="selected"</c:if> value="营销中心">营销中心</option>
                  <option <c:if test="${sysUserDTO.department=='生产中心' }">selected="selected"</c:if> value="生产中心">生产中心</option>
                  <option <c:if test="${sysUserDTO.department=='仪器信息集成中心' }">selected="selected"</c:if> value="仪器信息集成中心">仪器信息集成中心</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">权限设置</label>
              <div class="col-md-9">
                <button type="button" class="btn btn-block btn-set" data-action="modal" data-target="#viewRights" data-place="center">点击查看</button>
              </div>
            </div>
            <br>
            <div class="form-group">
              <div class="col-md-offset-5 col-md-6">
                <a href="userService/editUser.do?userName=${sysUserDTO.userName }" class="btn btn-green">编辑</a>
                <a href="userService/userList.do" class="btn btn-primary">返回</a>
              </div>
            </div>
          </form>
          <div class="modal" id="viewRights" style="height: 98%;overflow: scroll;"><!-- style="height: 98%;overflow: scroll;" -->
            <div class="modal-title primary">权限设置选项</div>
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
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/users_edit.min.js"></script>
</body>
</html>
