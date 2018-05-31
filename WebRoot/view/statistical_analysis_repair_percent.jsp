<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
   <%@ include file="/view/common/head.jsp" %>
  <title>统计分析 - 仪器信息管理系统</title>
 <script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath %>js/repair_statics_percent.js"></script>
 <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
 <script type="text/javascript" src="<%=basePath %>dist/js/echarts.js"></script>
  <script type="text/javascript">
   var basePath = '<%=basePath%>';
        
  </script>
  <style type="text/css">
    #colorStyle{
     background-color: #AFEEEE;
	 border-color:#AFEEEE;
	 color: rgba(40, 139, 255, 1);;
    }
     #displayMassage{
     text-align: center;
     margin-left: -20%;
    }
    #pic2{
     margin-top: -5%;
    }
    #pic{
     text-align: center;
     margin-left: -20%;
    }
  </style>
   <% 	request.setAttribute("nav", "repair");
     	SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd");
     	Date d=new Date();
	   String nowTime=simple.format(d);
	   d.setDate(d.getDate()-7);
	   String startTime=simple.format(d);
	   request.setAttribute("nav", "statics");
    %>
  </head>
  
 <body>
<%@ include file="/view/common/top.jsp" %>
<%@ include file="/view/include_left_quality.jsp" %>
  <div class="main-content instruments">
    <div class="page-head">
         <div class="row">
        <div class="col-md-3">统计分析</div>
        <div class="col-md-8" style="margin-left: -15%;">
         <a  class="btn btn-primary" id="colorStyle" href="view/statistical_analysis_ship.jsp">仪器出货统计分析</a>&nbsp;&nbsp;  <!-- class="btn btn-green" -->
          <a class="btn btn-primary" id="colorStyle"  href="view/statistical_analysis_repair.jsp">仪器维修统计分析</a>&nbsp;&nbsp;
          <a class="btn btn-primary" href="view/statistical_analysis_repair_percent.jsp">仪器返修率统计分析</a>
        </div>
           <div class="col-md-3 text-right">
        </div>
    </div>
    </div>
    <div class="page-content">
      <div class="row">
      <!-- 出货统计分析 -->
       <div style="margin-top: -2%;">
         <form >
            <label class="form-label col-md-2">统计时间段:</label>
            <select name="staticsType" onchange="quickStatics(this)">
              <option value="">请选择</option>
           	  <option value="week" selected="selected">近一个星期</option>
              <option value="oneMonth">近一个月</option>
              <option value="threeMonth">近三个月</option>
              <option value="oneYear">近一年</option>
            </select>
             <label style="margin-right: 5px;margin-left: 2%;"> 统计时间:</label>  
              <input type="text" onclick="WdatePicker();"  value="<%=startTime %>" style="width: 150px;" name="startTime" />&nbsp;&nbsp;一一&nbsp;&nbsp;<!-- {dateFmt:'yyyyMM'} -->
            <input type="text" onclick="WdatePicker();" value="<%=nowTime %>" style="width: 150px;" name="endTime"/>&nbsp;&nbsp;
            <a class="btn btn-primary" id="statics"><i class="iconfont icon-search"></i>统计</a>
         </form>
       </div>
      <br/>
      </div>
      
     <div style="width: 78%;height: 500px;text-align: center;margin-left: 10%;" id="pic">
     </div>
     <div style="width: 78%;height: 200px;text-align: center;margin-left: 10%;margin-top: -5%" id="pic2">
     </div>
     <div style="display: none;" id="displayMassage">暂无数据
     </div>
    </div>
 
  </div>
  <script type="text/javascript">
   var myChart = echarts.init(document.getElementById('pic'));
   var myChart2 = echarts.init(document.getElementById('pic2'));
    var categoriesShip = [];  
    var valuesShip = [];  
    var categoriesRepair = [];  
    var valuesRepair = [];  
    var percentList=[]; 
    var sumTotalRepair=[];
    var sumTotalShip=[];
  	var sumTotalPercent=[];
  	
  </script>
   <!-- 加载时间 -->
  <script src="dist/js/lib/require.min.js" data-main="dist/js/page/statistical.min.js"></script>
</body>
</html>
