<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	  $(function(){
	      var users="${userSession}";
	   if(users==null || users==''){
		   if(confirm("用户会话过期或异地登录导致退出系统")){
		      location.href='<%=basePath%>'+"login.jsp";
		   }
	   }
	  });

	</script>
<div class="header">
  <div class="fl">
    <a href="view/index.jsp">
      <div class="logo"></div>
      <h2>仪器信息管理系统</h2>  
    </a>
  </div>
    <div class="fr user-info" id="test" >
   
    <div class="info">
      <span class="avatar"><i class="iconfont icon-user-avatar"></i></span>
	  <span>${userSession.userName}-${userSession.name}</span>
      <span class="arrow"><i class="iconfont icon-arrow-down"></i></span>
    </div>
    
    <div class="logout" id="logout">
    	<a href="userService/logout.do"  id="operator">
	      <i class="iconfont icon-logout" class="info"></i>
	      <span >退出系统</span>
        </a>
    </div>
 
  </div>

</div>