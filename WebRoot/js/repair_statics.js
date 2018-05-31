$(function(){
	initData();//初始化图表data.dataresource[i].sapNo+"-"+
	 $("#statics").on("click",function(){
		 var startTime=$("[name=startTime]").val();
		 var endTime=$("[name=endTime]").val();
		 if(validateTime(startTime,endTime)){
			 $.ajax({  
			     url: 'barChartService/genBarPieChart.do' ,  
			     type: 'POST', 
			     data:{"startTime":startTime,"endTime":endTime},
			     dataType:"json",
			     success: function (data) { 
			       if(data.dataresource!=''){
			    	   $("#displayMassage").attr("style","display:none");
			    	   $("#pic").attr("style","width: 750px;height: 500px;text-align: center;margin-left: 10%;display:block");
			    	   categoriesName=[];
			    	   legend=[];
						sumTotal=0;
						  for(var i=0;i<data.dataresource.length;i++){
						    	 categoriesName[i]={name:data.dataresource[i].sapNo+"-"+data.dataresource[i].productName,value:data.dataresource[i].quantity};
						    	 legend[i]=data.dataresource[i].sapNo+"-"+data.dataresource[i].productName;
						  }
					     sumTotal=data.sumTotal;
			      }else{
			    	  $("#pic").attr("style","display:none");
			    	  $("#displayMassage").attr("style","display:block;margin-top: 10%;");
			      }
			     myChart.hideLoading();
			     //加载数据图表
			     showBar();
			     },  
			     error: function () {  
			        alert("图表请求数据失败!");
			     	 myChart.hideLoading();
			     }  
			 });
		 }
	 });
	
});
function quickStatics(staType){
	 var staticsType=$(staType).val();
	// 加载数据  
	 $.ajax({  
	     url: 'barChartService/genBarPieChart.do' ,  
	     type: 'POST', 
	     data:{"staticsType":staticsType},
	     dataType:"json",
	     success: function (data) { 
		    if(data.dataresource!=''){
		    	$("#displayMassage").attr("style","display:none");
		    	$("#pic").attr("style","width: 750px;height: 500px;text-align: center;margin-left: 10%;display:block");
		    	categoriesName=[];
		    	legend=[];
				sumTotal=0;
				 for(var i=0;i<data.dataresource.length;i++){
			    	 categoriesName[i]={name:data.dataresource[i].sapNo+"-"+data.dataresource[i].productName,value:data.dataresource[i].quantity};
			    	 legend[i]=data.dataresource[i].sapNo+"-"+data.dataresource[i].productName;
				}
			     sumTotal=data.sumTotal;
		    }else{
		    	$("#pic").attr("style","display:none");
		    	  $("#displayMassage").attr("style","display:block;margin-top: 10%;");
		    }
		    $("[name=startTime]").val(data.startTime);
			 $("[name=endTime]").val(data.endTime);
	     myChart.hideLoading();
	     //加载数据图表
	     showBar();
	     },  
	     error: function () {  
	        alert("图表请求数据失败!");
	     	 myChart.hideLoading();
	     }  
	 });
}
function initData() {
	// 指定图表的配置项和数据
	myChart.setOption({
		title : {
        text: '仪器维修统计分析',
        subText:'维修合计台',
        x:'center',
       
    },
    tooltip : {
       trigger: 'item',
        formatter: "{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'horizontal',//vertical
       // left: 'left',
		left:'-10%',
		padding:[5,5,5,-20]
       // data: []
    },
    series : [
        {
            name: '维修数量',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                normal: {
                    label:{
                      show:true,
                      formatter: ' {c} ({d}%)',//{b} :
                      testStyle:{
                        fontFamily:'宋体',
                        fontSize:20,
                        fontWeight:'bold'
                      }
                    },
                    textStyle : {
                    	color : 'black',
                    	fontSize : 12,
                    	fontWeight : 'bold'
                    	},
                    labelLine:{show:true}
                }
            }
        }
    ]
    });

	myChart.showLoading();

	// 加载数据
	$.ajax({
		url : 'barChartService/genBarPieChart.do',
		type : 'POST',
		data : {
			"staticsType" : "week"
		},
		dataType : "json",
		success : function(data) {
		if(data.dataresource!=''){
			$("#displayMassage").attr("style","display:none");
			$("#pic").attr("style","width: 750px;height: 500px;text-align: center;margin-left: 10%;display:block");
			categoriesName=[];
			legend=[];
			sumTotal=0;
			 for(var i=0;i<data.dataresource.length;i++){
		    	 categoriesName[i]={name:data.dataresource[i].sapNo+"-"+data.dataresource[i].productName,value:data.dataresource[i].quantity};
		    	 legend[i]=data.dataresource[i].sapNo+"-"+data.dataresource[i].productName;
			 }
			sumTotal=data.sumTotal;
		}else{
			$("#pic").attr("style","display:none");
	    	  $("#displayMassage").attr("style","display:block;margin-top: 10%;");
		}
			myChart.hideLoading();
			// 加载数据图表
			showBar();
		},
		error : function() {
			alert("图表请求数据失败!");
			myChart.hideLoading();
		}
	});
}

function validateTime(startTime,endTime){
	if(startTime==''){
		alert("请选择统计开始时间");
		return false;
	}else if(endTime==''){
		alert("请选择统计结束时间");
		return false;
	}else if(startTime>endTime){
		alert("结束时间不能小于开始时间");
		$("[name=endTime]").val("");
		return false;
	}else{
		return true;
	}
	
}
function showBar() {
    //加载数据图表
    myChart.setOption({
    	legend:{
    		show:true,
    		orient: 'vertical',
            left: '0',
			 formatter: function (name) {
				return echarts.format.truncateText(name, 200, '14px Microsoft Yahei', '…');
				},
			
			tooltip:{show:true},
            data:legend},
      series : [
	        {
	            name:'维修数量',
	            data:categoriesName,
	            itemStyle: {
	                normal: {
	                    label:{
	                      show:true,
	                      formatter: '{c} ({d}%)',//{b} :
	                      testStyle:{
	                        fontFamily:'宋体',
	                        fontSize:20,
	                        fontWeight:'bold'
	                      }
	                    },
	                    labelLine:{show:true}
	                }
	            }
	        }
	    ]
    });
}  

