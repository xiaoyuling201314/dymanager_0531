<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="main-left">
  <div class="hd">
    <div id="timeNow"></div>
    <div class="menu"><i class="iconfont icon-list"></i></div>
  </div>
  <div class="nav">
    <ul>
      <li class="active"><a href="#!"><i class="iconfont icon-home-o"></i>首页</a></li>
      <li id=""><a href="instrumentService/instruList.do"><i class="iconfont icon-apps-o"></i>仪器列表</a></li>
      <li id=""><a href="#!"><i class="iconfont icon-reagent-o"></i>试剂列表</a></li>
      <li id=""><a href="materielService/materielList.do"><i class="iconfont icon-cart-o"></i>物料管理</a></li>
      <li id="">
        <a href="userService/selectDetail.do?userName=${userSession.userName}"><i class="iconfont icon-set-o"></i>用户管理</a>
      </li>
    </ul>
  </div>
</div>