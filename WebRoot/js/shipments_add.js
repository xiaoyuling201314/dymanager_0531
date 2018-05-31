$(function(){
	$(".right").attr("style","display:none"),
	$(".error").attr("style","display:none"),
	$(".deletFile").attr("style", "display:none");
	//导入出货信息
	 $("#shipmentsImportform").attr("style","display:none");
		 $("#importMater").on("click",function(){
		    $("#shipmentsForm").attr("style","display:none");
		    $("#shipmentsImportform").attr("style","display:block");
		 });
		  $("#inputMater,#returnInput").on("click",function(){
		     $("#shipmentsForm").attr("style","display:block");
		     $("#shipmentsImportform").attr("style","display:none");
		 });
		  
//	$("[name=quantity]").on("blur",function(){
//		var number=$("[name=quantity]").val();
//		if(number!=''){
//			if(!/^[0-9]\d*$/.test(number)){
//				alert("出货数量只能是正整数");
//				$("[name=quantity]").val("");
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
	
//	//在输入机身码前先检查出货数量是否输入
//	$("[name=instrumentFuselage]").on("focus",function(){
//		var number=$("[name=quantity]").val();
//			if(number=='' && check==true){
//				check=false;
//				alert("请先输入出货数量");
//			}else{
//				check=true;
//			}
//	});
	$("#btnSave").on("click",function(){
		  var sapNo=$("[name=sapNo]").val();
		   var customer=$("[name=customer]").val();
		   var inspectionMan=$("[name=inspectionMan]").val();
		   var shippingDate=$("[name=shippingDate]").val();
		   var quantity=$("[name=quantity]").val();
		   var softwareVersion=$("[name=softwareVersion]").val();
		   var instrumentFuselage=$("[name=instrumentFuselage]").val();
		   instrumentFuselage=instrumentFuselage.replace("，",",");
		if(validate(sapNo,customer,inspectionMan,shippingDate,quantity,instrumentFuselage)){
			if(checkNum()){
		     var comments=$("[name=comments]").val();
		     var objeShip={"sapNo":sapNo,"softwareVersion":softwareVersion,"customer":customer,"inspectionMan":inspectionMan,"shippingDate":shippingDate,"quantity":quantity,"instrumentFuselage":instrumentFuselage,"comments":comments};
			$.ajax({  
		          url: 'shipmentsService/inputShipments.do' ,  
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
 	//导入出货记录
	 	$("#btnShipSave").on(
			"click",
			function() {
				var fileName = $("[name=fileName]").val();
				var myFile = $("[name=myFile]").val();
				var reg = /^[^*\/\\|:<>?\"]*$/;
				if (fileName != '' && myFile != '') {
					if (!reg.test(fileName)) {//$(".fileNameMessage").html
						$(".fileNameMessage").html("文件名称不能包含<br/>下列任何字符：\ / \\ : * ? \" < > |");
					} else {
						var formData = new FormData($("#shipmentsImportform")[0]); // 要求使用的html对象
						$.ajax({
							async : false,
							url : 'shipmentsService/importShipMents.do',
							type : 'POST',
							data : formData,
							cache : false,
							processData : false,
							contentType : false,
							dataType : "json",
							success : function(returndata) {
								if (returndata.count > 0) {
									alert("导入成功" + returndata.count + "条数据");
									location.href=basePath+"shipmentsService/shipList.do";
								} else if (returndata.count == 0) {
									alert("导入成功" + returndata.count
											+ "条数据，请检查数据文件");
								} else {
									alert("导入失败，请检查数据文件");
								}

							},
							error : function(returndata) {
								alert("操作失败");
							}
						});
					}
				} else {
					// alert("请选择电路板BOM单文件");
					$(".fileNameMessage").html("请选择出货记录文件");
				}
			});
});
//删除文件
function deleteFile(){
	     $(".fileicon").attr("style","display:none");
	     $(".deletFile").attr("style","display:none");
	     $("[name=myFile]").val("");
	     $("[name=fileName]").val("");
}	
 function validate(sapNo,customer,inspectionMan,shippingDate,quantity,instrumentFuselage){
   var count=0;
   if(sapNo==''){
		   count+=1;
   }
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