$(function(){
	initData();//初始化图表
	 $("#statics").on("click",function(){
		 var startTime=$("[name=startTime]").val();
		 var endTime=$("[name=endTime]").val();
		 if(validateTime(startTime,endTime)){
			 $.ajax({  
			     url: 'barChartService/statiscalShip.do' ,  
			     type: 'POST', 
			     data:{"startTime":startTime,"endTime":endTime},
			     dataType:"json",
			     success: function (data) { 
			       if(data.dataresource!=''){
			    	   $("#displayMassage").attr("style","display:none");
			    	   $("#pic").attr("style","width: 700px;height: 500px;text-align: center;margin-left: 10%;display:block");
				       $("#pic2").attr("style","width: 700px;height: 500px;text-align: center;margin-left: 10%;display:block");
			    	   categories=[];
						values=[];
						sumTotal=[];
					     for(var i=0;i<data.dataresource.length;i++){
					       categories.push(data.dataresource[i].sapNo);
					     }
					     for(var i=0;i<data.dataresource.length;i++){
					     	values.push(data.dataresource[i].quantity);
					     }
					     sumTotal.push(data.sumTotal);
			      }else{
			    	  $("#pic").attr("style","display:none");
			    	  $("#pic2").attr("style","display:none");
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
	     url: 'barChartService/statiscalShip.do' ,  
	     type: 'POST', 
	     data:{"staticsType":staticsType},
	     dataType:"json",
	     success: function (data) { 
		    if(data.dataresource!=''){
		    	$("#displayMassage").attr("style","display:none");
		    	$("#pic").attr("style","width: 700px;height: 500px;text-align: center;margin-left: 10%;display:block");
		    	  $("#pic2").attr("style","width: 700px;height: 500px;text-align: center;margin-left: 10%;display:block");
		    	categories=[];
				values=[];
				sumTotal=[];
			     for(var i=0;i<data.dataresource.length;i++){
			       categories.push(data.dataresource[i].sapNo);
			     }
			     for(var i=0;i<data.dataresource.length;i++){
			     	values.push(data.dataresource[i].quantity);
			     }
			     sumTotal.push(data.sumTotal);
		    }else{
		    	$("#pic").attr("style","display:none");
		    	  $("#pic2").attr("style","display:none");
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
					text : '出货统计分析',
					x : 'center',
					y : 'top',
					textAlign : 'left'
				},
				tooltip : {
					show : true
				},
				legend : {
					show : false
				},
				xAxis : [{
					data : [],
					axisLabel:{  
	                         interval:0,//横轴信息全部显示  
	                         rotate:60,//-30度角倾斜显示  
	                         margin:2,
						}  
				}],// 先置为空
				yAxis : {},
				series : [ {
					name : '出货数量',
					type : 'bar',
					itemStyle : {normal : {label : {
								show : true,
								position : 'top',
								textStyle : {
									color : 'black'
								}
							},
							color : function(value) {
								return "#"
										+ ("00000" + ((Math.random() * 16777215 + 0.5) >> 0)
												.toString(16)).slice(-6);
							}
						}
					},
					data : [],
					barWidth : 50
				} ],
			});

	myChart2.setOption({
		title : {
			text : ''
		},
		tooltip : {
		},
		legend : {
			show : false
		},
		grid : {
			height : 50
		},
		calculable : true,
		xAxis : [ {
			name : '出货总数',
			type : 'value',
			boundaryGap : [ 0, 0.01 ]
		} ],
		yAxis : [ {
			type : 'category',
			data : [ '合计' ]
		} ],
		series : [ {
			name : '出货总数',
			type : 'bar',
			data : [],
			barWidth : 50,
			 itemStyle : { normal: {label : {show: true, position: 'right',textStyle:{color:'black'}}}}
		} ]
	});
	myChart.showLoading();

	// 加载数据
	$.ajax({
		url : 'barChartService/statiscalShip.do',
		type : 'POST',
		data : {
			"staticsType" : "week"
		},
		dataType : "json",
		success : function(data) {
		if(data.dataresource!=''){
			$("#displayMassage").attr("style","display:none");
			$("#pic").attr("style","width: 700px;height: 500px;text-align: center;margin-left: 10%;display:block");
	    	  $("#pic2").attr("style","width: 700px;height: 500px;text-align: center;margin-left: 10%;display:block");
			categories=[];
			values=[];
			sumTotal=[];
			for ( var i = 0; i < data.dataresource.length; i++) {
				categories.push(data.dataresource[i].sapNo);
			}
			for ( var i = 0; i < data.dataresource.length; i++) {
				values.push(data.dataresource[i].quantity);
			}
			sumTotal.push(data.sumTotal);
		}else{
			$("#pic").attr("style","display:none");
	    	  $("#pic2").attr("style","display:none");
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
      xAxis : {name:'SAP代码',data:categories},
      yAxis: {type:'value',name:'出货数量',min: 0},
      series : [
	        {
	            name:'出货数量',
	            data:values
	        }
	    ]
    });
    //设置合计图表
       myChart2.setOption({//加载数据图表
      xAxis : {data:"合计"},
      series : [
	        {
	            name:'出货合计',
	            data:sumTotal
	        }
	    ]
    });
}  

