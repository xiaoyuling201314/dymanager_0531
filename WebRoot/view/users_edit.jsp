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
    <script type="text/javascript" src="<%=basePath %>js/user_edit.js"></script>
	<% request.setAttribute("nav", "userList"); %>
	 <script type="text/javascript">
  	   var checkEmail;
  	    var basePath = '<%=basePath%>';
  	    $(function(){
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
    <div class="page-head">用户列表 / 编辑</div>
    <div class="page-content">
      <div class="row">
        <div class="col-md-offset-2 col-md-8">
          <form action="userService/updateUser.do" method="post" class="userForm" onsubmit="return validate();">
          <input type="hidden" name="state" value="${sysUserDTO.state }" />
          <input type="hidden" name="superAdmin" value="${sysUserDTO.superAdmin }" />
           <input type="hidden" name="rightList"/>
            <div class="form-group">
              <label class="form-label col-md-3">用户名<strong style="color: red;">*</strong></label>
              <div class="col-md-9">
                <input type="text" class="form-control" value="${sysUserDTO.userName }" name="userName" disabled="disabled">
                <input type="hidden" value="${sysUserDTO.userName }" name="userName" />
              </div>
            </div>
            <div class="form-group" style="height: 38px;">
              <label class="form-label col-md-3">密码<strong style="color: red;">*</strong></label>
              <div class="col-md-9">
                <input type="password" class="form-control" value="${sysUserDTO.password }" name="password" >
                <div class="check" >
              <label style='color: red;position: relative;top:-30px ;left: 103%;' class="passwordMessage"  ></label>
            </div>
              </div>
            </div>
            <div class="form-group" style="height: 38px;">
              <label class="form-label col-md-3">姓名<strong style="color: red;">*</strong></label>
              <div class="col-md-9"  style="height: 38px;">
                <input type="text" class="form-control" value="${sysUserDTO.name }" name="name">
               <div class="check" >
              <label style='color: red;position: relative;top:-30px ;left: 103%;' class="nameMessage"  ></label>
            </div>
              </div>
            </div>

            <div class="form-group" style="height: 38px;">
              <label class="form-label col-md-3">电子邮件<strong style="color: red;">*</strong></label>
              <div class="col-md-9" >
                <input type="text" class="form-control" value="${sysUserDTO.email }" name="email" >
                 <i class="rightEmail"><img width='20px' height='20px' style='position: relative;top:-30px ;left: 450px;' src='dist/img/right.png'></img></i>
                <div class="errorEmail" ><img width='20px' height='20px' style='position: relative;top:-30px ;left: 100%;' src='dist/img/errorred.png'/>
                <label style='color: red;position: relative;top:-35px ;left: 100%;' class="sapNoMessageEmail"  ></label></div>
              </div>
              <div class="check" >
              <label style='color: red;position: relative;top:-30px ;left: 100%;' class="emailMessage"  ></label>
            </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">手机号码</label>
              <div class="col-md-9">
                <input type="text" class="form-control" value="${sysUserDTO.phone }" name="phone">
              </div>
            </div>
            <div class="form-group">
              <label class="form-label col-md-3">所属部门</label>
              <div class="col-md-9">
                <select id="" class="form-control" name="department">
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
                <button type="button" class="btn btn-block btn-set" data-action="modal" data-target="#viewRights" data-place="center">点击设置</button>
              </div>
            </div>
            <br>
            <div class="form-group">
              <div class="col-md-offset-5 col-md-6">
                <input type="submit" class="btn btn-green" value="保存" />
                 &nbsp;&nbsp;&nbsp;&nbsp;<a id="returnbtn" class="btn btn-primary">返回</a>
              </div>
            </div>
          </form>
          <div class="modal" id="viewRights"  style="height: 98%;overflow: scroll;" >
            <div class="modal-title primary">权限设置选项</div>
          
             <div class="modal-content">
              <div class="rights">
                <h5>仪器产品列表</h5>
                <div class="checkbox-inline">
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="A-1" name="rights" value="A-1">
                    <label for="A-1">仪器资质</label>
                  </div>
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="A-2" name="rights" value="A-2">
                    <label for="A-2">仪器说明书</label>
                  </div>
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="A-3" name="rights" value="A-3">
                    <label for="A-3">整机BOM单</label>
                  </div>
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="A-4" name="rights" value="A-4">
                    <label for="A-4">电路板BOM单</label>
                  </div>
                   <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="A-5" name="rights" value="A-5">
                    <label for="A-5">装箱清单</label>
                  </div>
                   <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="A-6" name="rights" value="A-6">
                    <label for="A-6">仪器设计文档</label>
                  </div>
                </div>
              </div>
              <div class="rights" style="display: none;">
                <h5>试剂产品列表</h5>
                <div class="checkbox-inline">
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="B-1" name="rights" value="B-1">
                    <label for="B-1">试剂资质</label>
                  </div>
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="B-2" name="rights" value="B-2">
                    <label for="B-2">试剂说明书</label>
                  </div>
                  <div class="checkbox checkbox-primary"  >
                    <input type="checkbox" id="B-3" name="rights" value="B-3">
                    <label for="B-3">试剂BOM单</label>
                  </div>
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="B-4" name="rights" value="B-4">
                    <label for="B-4">试剂装箱清单</label>
                  </div>
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="B-5" name="rights" value="B-5">
                    <label for="B-5">试剂设计文档</label>
                  </div>
                </div>
              </div>
              <!-- 品质管理权限设置start 2016-11-24 -->
               <div class="rights">
                <h5>品质管理</h5>
                <div class="checkbox-inline">
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="E-1" name="rights" value="E-1">
                    <label for="E-1">出货记录</label>
                  </div>
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="E-2" name="rights" value="E-2">
                    <label for="E-2">维修记录</label>
                  </div>
                  <div class="checkbox checkbox-primary"  >
                    <input type="checkbox" id="E-3" name="rights" value="E-3">
                    <label for="E-3">统计分析</label>
                  </div>
                </div>
              </div>
              <!--品质管理权限设置end 2016-11-24   -->
             <div class="rights">
                <h5>物料管理</h5>
                <div class="checkbox-inline">
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="C-1" name="rights" value="C-1">
                    <label for="C-1">物料管理</label>
                  </div>
                </div>
              </div>
             <div class="rights">
                <h5>用户管理</h5>
                <div class="checkbox-inline">
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="D-1" name="rights" value="D-1">
                    <label for="D-1">基本信息</label>
                  </div>
                  <div class="checkbox checkbox-primary">
                    <input type="checkbox" id="D-2" name="rights" value="D-2">
                    <label for="D-2">密码设置</label>
                  </div>
                </div>
                
              </div>
            
              <div class="modal-action">
                <button type="button" data-dismiss="modal" class="btn btn-primary" id="setRight" >确定</button>
                <button type="button" data-dismiss="modal" class="btn btn-orange">取消</button>
              </div>
          
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/users_edit.min.js"></script>
</body>
</html>
