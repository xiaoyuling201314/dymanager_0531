$(function(){
//	$("[name=quantity]").on("blur",function(){
//		var number=$("[name=quantity]").val();
//		if(number!=''){
//			if(!/^[0-9]\d*$/.test(number)){
//				alert("出货数量只能是正整数");
//				$("[name=quantity]").val("")
//				check=false;
//			}else{//启用机身码输入框
//				$("[name=instrumentFuselage]").removeAttr("disabled");
//				$("[name=instrumentFuselage]").val("");
//				check=true;
//			}
//		}else{//禁用机身码输入框
//			check=false;
//			$("[name=instrumentFuselage]").val("");
//			$("[name=instrumentFuselage]").attr("disabled","disabled");
//		}
//		
//	});
	//验证机身码和数量是否一致
//	$("[name=instrumentFuselage]").on("blur",function(){
//		var number=$("[name=quantity]").val();
//		var instrumentFuselage=$("[name=instrumentFuselage]").val();
//			if(instrumentFuselage!='' && number!=''){
//				instrumentFuselage=instrumentFuselage.replace("，",",");
//				var arrays=instrumentFuselage.split(",");
//				if(arrays.length!=number){
//					alert("机身码数量与出货数量不一致,请检查输入是否有误");
//					check=false;
//				}else{
//					check=true;
//				}
//			}else if(number==''){
//				check=false;
//			}
//	});

	$("#btnSave").on("click",function(){
		  var id=$("[name=id]").val();
		   var customer=$("[name=customer]").val();
		   var inspectionMan=$("[name=inspectionMan]").val();
		   var shippingDate=$("[name=shippingDate]").val();
		   var quantity=$("[name=quantity]").val();
		   var softwareVersion=$("[name=softwareVersion]").val();
		   var instrumentFuselage=$("[name=instrumentFuselage]").val();
		   instrumentFuselage=instrumentFuselage.replace("，",",");
		if(validate(customer,inspectionMan,shippingDate,quantity,instrumentFuselage)){
			if(checkNum()){
			var comments=$("[name=comments]").val();
		     var objeShip={"id":id,"softwareVersion":softwareVersion,"customer":customer,"inspectionMan":inspectionMan,"shippingDate":shippingDate,"quantity":quantity,"instrumentFuselage":instrumentFuselage,"comments":comments};
			$.ajax({  
		          url: 'shipmentsService/updateshipMents.do' ,  
		          type: 'POST', 
		          data:{"shiDto":JSON.stringify(objeShip)},
		          dataType:"json",
		          success: function (data) { 
		        	  if(data.result=='success'){
							location.href=basePath+"shipmentsService/shipList.do";
					   }else{
						   alert("操作失败");  
					   }
		          },  
		          error: function (returndata) {  
		              alert("操作失败");  
		          }  
		     }); 
		}
		}else{
			alert("必填项不能为空,请检查输入");
		}
	});
	//客户名称自动补全
 	$.ajax({  //异步加载数据，查询客户名称
	          url: 'shipmentsService/queryCustomer.do' ,  
	          type: 'POST', 
	          dataType:"json",
	          success: function (data) {
	          var str=JSON.stringify(data.customer);//转换为字符串
	          var customer=str.split(",");//装换为数组
	        	var  availableTagsAjax=JSON.parse(customer);//转换为json数组
	        	  $( "[name=customer]" ).autocomplete({
				      source: availableTagsAjax,
				      minLength:2
				    });
	          },  
	          error: function (returndata) {  
	              alert("操作失败");  
	          }  
 }); 
});
 function validate(customer,inspectionMan,shippingDate,quantity,instrumentFuselage){
   var count=0;
   if(customer==''){
	   count+=1;
   }
   if(inspectionMan==''){
	   count+=1;
   }	
   if(shippingDate==''){
	   count+=1;
   }
   if(quantity==''){
	   count+=1;
   }
   if(instrumentFuselage==''){
	   count+=1;
   }
   if(count>0){
	   return false;
   }else{
	   
	   return true;
   }
 }
//验证数量和机身码 start
 function checkNum(){
	   var number=$("[name=quantity]").val();
	   var instrumentFuselage=$("[name=instrumentFuselage]").val();
		if(number!=''){
			if(!/^[0-9]\d*$/.test(number)){
				alert("出货数量只能是正整数");
				$("[name=quantity]").val("");
				return false;
			}else{
				if(instrumentFuselage!=''){
					instrumentFuselage=instrumentFuselage.replace(/，/g,",");
					var arrays=instrumentFuselage.split(",");
					var checkData=0;
					for(var i=0;i<arrays.length;i++){
						if(arrays[i]=='' || arrays[i]=="," || arrays[i]=="，"){
							checkData+=1;
						}
					}
					if(arrays.length!=number || checkData!=0){
						alert("机身码数量与出货数量不一致,请检查输入是否有误\n格式要求：(机身码用逗号隔开,例如数量为2，则机身码为：001,001)");
						return false;
					}else
					return true;
				}else if(number==''){
					return false;
				}
			}
			
		}else{
			return false;
		}
 }
 //验证数量和机身码end