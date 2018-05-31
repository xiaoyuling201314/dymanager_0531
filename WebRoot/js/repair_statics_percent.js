$(function(){
	initData();//初始化图表
	 $("#statics").on("click",function(){
		 var startTime=$("[name=startTime]").val();
		 var endTime=$("[name=endTime]").val();
		 if(validateTime(startTime,endTime)){
			 $.ajax({  
			     url: 'barChartService/genBarPercentChart.do' ,  
			     type: 'POST', 
			     data:{"startTime":startTime,"endTime":endTime},
			     dataType:"json",
			     success: function (data) { 
			       if(data.shippingrecords!=''){
			    	   $("#displayMassage").attr("style","display:none;");
			    	   $("#pic").attr("style","width: 700px;height: 500px;text-align: center;margin-left: 10%;display:block");
				       $("#pic2").attr("style","width: 700px;height: 500px;text-align: center;margin-left: 10%;display:block");
				       categoriesShip = [];  
			    	     valuesShip = [];  
			    	     categoriesRepair = [];  
			    	     valuesRepair = [];  
			    	     percentList=[]; 
			    	     sumTotalRepair=[];
			    	     sumTotalShip=[];
					
					//维修数量
					for ( var i = 0; i < data.repairRecorder.length; i++) {
						if(data.repairRecorder[i]!=null){
							categoriesRepair.push(data.repairRecorder[i].sapNo);
							valuesRepair.push(data.repairRecorder[i].quantity);
						}else{
							categoriesRepair.push("");
							valuesRepair.push(0);
						}
					}
					//出货数量
					for ( var i = 0; i < data.shippingrecords.length; i++) {
						categoriesShip.push(data.shippingrecords[i].sapNo);
						valuesShip.push(data.shippingrecords[i].quantity);
					}
					for ( var i = 0; i < data.percentList.length; i++) {
						percentList.push(data.percentList[i]);
					}
					sumTotalShip.push(data.sumTotalShip);
					sumTotalRepair.push(data.sumTotalRepair);
					sumTotalPercent.push(data.totalPercent);
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
	     url: 'barChartService/genBarPercentChart.do' ,  
	     type: 'POST', 
	     data:{"staticsType":staticsType},
	     dataType:"json",
	     success: function (data) { 
		    if(data.shippingrecords!=''){
		    	$("#displayMassage").attr("style","display:none");
		    	$("#pic").attr("style","width: 700px;height: 500px;text-align: center;margin-left: 10%;display:block");
		    	  $("#pic2").attr("style","width: 700px;height: 500px;text-align: center;margin-left: 10%;display:block");
		    	categories=[];
		    	 categoriesShip = [];  
	    	     valuesShip = [];  
	    	     categoriesRepair = [];  
	    	     valuesRepair = [];  
	    	     percentList=[]; 
	    	     sumTotalRepair=[];
	    	     sumTotalShip=[];
			//维修数量
//			for ( var i = 0; i < data.repairRecorder.length; i++) {
//				if(data.repairRecorder[i]==null){
//					categoriesRepair.push("");
//				}else{
//					categoriesRepair.push(data.repairRecorder[i].sapNo);
//				}
//			}
//			for ( var i = 0; i < data.repairRecorder.length; i++) {
//				if(data.repairRecorder[i]==null){
//					valuesRepair.push(0);
//				}else{
//					valuesRepair.push(data.repairRecorder[i].quantity);
//				}
//				
//			}
//			//出货数量
//			for ( var i = 0; i < data.shippingrecords.length; i++) {
//				categoriesShip.push(data.shippingrecords[i].sapNo);
//			}
//			for ( var i = 0; i < data.shippingrecords.length; i++) {
//				valuesShip.push(data.shippingrecords[i].quantity);
//			}
	    	   //维修数量
					for ( var i = 0; i < data.repairRecorder.length; i++) {
						if(data.repairRecorder[i]!=null){
							categoriesRepair.push(data.repairRecorder[i].sapNo);
							valuesRepair.push(data.repairRecorder[i].quantity);
						}else{
							categoriesRepair.push("");
							valuesRepair.push(0);
						}
					}
					//出货数量
					for ( var i = 0; i < data.shippingrecords.length; i++) {
						categoriesShip.push(data.shippingrecords[i].sapNo);
						valuesShip.push(data.shippingrecords[i].quantity);
					}
			for ( var i = 0; i < data.percentList.length; i++) {
				percentList.push(data.percentList[i]);
			}
			sumTotalShip.push(data.sumTotalShip);
			sumTotalRepair.push(data.sumTotalRepair);
			sumTotalPercent.push(data.totalPercent);
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
		   tooltip : {},
		    legend: {
		        data: ['出货数量','返修数量','返修率']
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis:  [{
		         type: 'category',
		        data: [],
		        axisLabel:{  
                    interval:0,//横轴信息全部显示  
                    rotate:30,//-30度角倾斜显示  
                    margin:2,
				}  
		    }],
		     yAxis: [
		             {type:'value',name:'数量',min: 0, 
		            	 //interval: 1,
		            	 //splitLine:false,
		            	 }
		             ,
		            {
		            	type: 'value',
			            name: '返修率',
			            interval:5,
			            max: 100,
			            axisLabel: {
			                show:true,
			                formatter: '{value}%'
			            },
			            splitLine:false,
		            }
		             ],
		    series: [ 
		      {
	            name: '返修数量',
	            type: 'bar',
	            stack: '总量',
	            label: {
	                normal: {
	                    show: true,
	                    position: 'insideTop'//insideTop
	                }
	            },
 
	            //itemStyle : { normal: {label : {show: true, position: 'top',textStyle:{color:'black'}},color: function (value){return "#"+("00000"+((Math.random()*16777215+0.5)>>0).toString(16)).slice(-6); }}},
	            data: []
	        }
	        ,
		        {
		            name: '出货数量',
		            type: 'bar',
		            stack: '总量',
		            label: {
		                normal: {
		                    show: true,
		                    position: 'top'
		                }
		            },
		            itemStyle:{normal:{label:
		            {
		            	show:true,
		            	textStyle:{color:'black'}
		            }}},
		           // itemStyle : { normal: {label : {show: true, position: 'top',textStyle:{color:'black'}},color: function (value){return "#"+("00000"+((Math.random()*16777215+0.5)>>0).toString(16)).slice(-6); }}},
		            data: []
		        },
		        {
			            name:'返修率',
			            type:'line',
			            yAxisIndex: 1,
			            itemStyle:{normal:{label:
			            {
			            	show:true,
			            	 formatter: function(a){
			            		 return a.data+"%";
			            	 },
			            	textStyle:{color:'black'}
			            }}},
			            data:[]
			       		 }
		        
		    ]
		});

	myChart2.setOption({
	title : {
        text: ''
    },
    tooltip : {
        //trigger: 'axis'
    },
    legend: {
    data: ['返修合计','出货合计'],
        show:false
    },
    grid:{
      height:50
    },
    calculable : true,
    xAxis : [
        {
        	name:'数量',
            type : 'value',
            boundaryGap : [0, 0.01]
        }
    ],
    yAxis : [
        {
            type : 'category',
            data : ['合计']
        }
    ],
    series : [
        {
            name:'返修合计',
            type:'bar',
            data:[],
            stack: '总量',
             barWidth: 50,
              label: {
                normal: {
                    show: true,
                    position: 'insideRight'//insideRight
                }
            },
              itemStyle : { normal: {label : {show: true, position: 'right',textStyle:{color:'blue'}}}}
        },
        {
            name:'出货合计',
            type:'bar',
            data:[],
            stack: '总量',
             barWidth: 50,
              label: {
                normal: {
                    show: true,
                    position: 'insideRight'
                }
            },
              itemStyle : { normal: {label : {show: true, position: 'right',textStyle:{color:'red'}}}}
        }
    ]
});
	myChart.showLoading();

	// 加载数据
	$.ajax({
		url : 'barChartService/genBarPercentChart.do',
		type : 'POST',
		data : {
			"staticsType" : "week"
		},
		dataType : "json",
		success : function(data) {
		if(data.shippingrecords!=''){
			$("#displayMassage").attr("style","display:none");
			$("#pic").attr("style","width: 700px;height: 500px;text-align: center;margin-left: 10%;display:block");
	    	  $("#pic2").attr("style","width: 700px;height: 500px;text-align: center;margin-left: 10%;display:block");
	    	   	 categoriesShip = [];  
	    	     valuesShip = [];  
	    	     categoriesRepair = [];  
	    	     valuesRepair = [];  
	    	     percentList=[]; 
	    	     sumTotalRepair=[];
	    	     sumTotalShip=[];
//			//维修数量
//			for ( var i = 0; i < data.repairRecorder.length; i++) {
//				if(data.repairRecorder[i]==null){
//					categoriesRepair.push("");
//				}else{
//					categoriesRepair.push(data.repairRecorder[i].sapNo);
//				}
//			}
//			for ( var i = 0; i < data.repairRecorder.length; i++) {
//				if(data.repairRecorder[i]==null){
//					valuesRepair.push("");
//				}else{
//					valuesRepair.push(data.repairRecorder[i].quantity);
//				}
//			}
//			//出货数量
//			for ( var i = 0; i < data.shippingrecords.length; i++) {
//				categoriesShip.push(data.shippingrecords[i].sapNo);
//			}
//			for ( var i = 0; i < data.shippingrecords.length; i++) {
//				valuesShip.push(data.shippingrecords[i].quantity);
//			}
	    	   //维修数量
					for ( var i = 0; i < data.repairRecorder.length; i++) {
						if(data.repairRecorder[i]!=null){
							categoriesRepair.push(data.repairRecorder[i].sapNo);
							valuesRepair.push(data.repairRecorder[i].quantity);
						}else{
							categoriesRepair.push("");
							valuesRepair.push(0);
						}
					}
					//出货数量
					for ( var i = 0; i < data.shippingrecords.length; i++) {
						categoriesShip.push(data.shippingrecords[i].sapNo);
						valuesShip.push(data.shippingrecords[i].quantity);
					}
			for ( var i = 0; i < data.percentList.length; i++) {
				percentList.push(data.percentList[i]);
			}
			sumTotalShip.push(data.sumTotalShip);
			sumTotalRepair.push(data.sumTotalRepair);
			sumTotalPercent.push(data.totalPercent);
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
      xAxis : {name:'   SAP码',data:categoriesShip
    
      },//categoriesShip,categoriesRepair  interval: 10
      yAxis: [{type:'value',name:'数量',min: 0},{type:'value',name:'数量',min: 0}],
      series : [
			{
				 name: '返修数量',
				//barMinHeight:'10',
			   data:valuesRepair,
			   barWidth: 50,
			},
	        {
	        	 name: '出货数量',
	            data:valuesShip,
	            barWidth: 50,
	        },{
	        		name:'返修率',
	        		axisLabel: {
		                show:true,
		                formatter: '{value}%'
		            },
		            data:percentList
		            
		        }
	    ]
    });
    //设置合计图表
       myChart2.setOption({//加载数据图表
      xAxis : {data:"合计"},
      series : [
	       {
	        	name:'返修合计',
	            data:sumTotalRepair
//	            itemStyle:{normal:{label:
//	            {
//	            	show:true,
//	            	 formatter: function(a){
//	            		 return a.data+"%";
//	            	 },
//	            	textStyle:{color:'black'}
//	            }}},
	        },
	        {
	            name:'出货合计',
	            data:sumTotalShip
	        }
	    ]
    });
}  


                 
