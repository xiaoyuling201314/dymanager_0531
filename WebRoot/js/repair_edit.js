$(function(){
	$(".right").attr("style","display:none"),
	$(".error").attr("style","display:none"),
	$(".right2").attr("style","display:none"),
	$(".error2").attr("style","display:none"),
	$(".deletFile").attr("style", "display:none");
	$(".deleteMpic").attr("style", "display:none");
	//导入出货信息
	 $("#repairImportform").attr("style","display:none");
		 $("#importMater").on("click",function(){
		    $("#repairForm").attr("style","display:none");
		    $("#repairImportform").attr("style","display:block");
		 });
		  $("#returnInput").on("click",function(){
		     $("#repairForm").attr("style","display:block");
		     $("#repairImportform").attr("style","display:none");
		 });
		  

	$("#btnSave").on("click",function(){
		   var repairOrderNumber=$("[name=repairOrderNumber]").val();
		   var instrumentFuselage=$("[name=instrumentFuselage]").val();
		   var receivedDate=$("[name=receivedDate]").val();
		   var planCompleteDate=$("[name=planCompleteDate]").val();
		   var repairMan=$("[name=repairMan]").val();
		   var faultDescription=$("[name=faultDescription]").val();
		  // var shipmentNo=$("[name=shipmentNo]").val();shipmentNo,"shipmentNo":shipmentNo,
		if(validate(repairOrderNumber,instrumentFuselage,receivedDate,planCompleteDate,repairMan,faultDescription)){
		     var comments=$("[name=comments]").val();
		     var processingMethod=$("[name=processingMethod]").val();
		     var faultPicture=$("[name=faultPicture]").val();
		     var processingPicture=$("[name=processingPicture]").val();
		     var actualCompleteDate=$("[name=actualCompleteDate]").val();
		     var delpicture=$("[name=delpicture]").val();
		     var sapNo=$("[name=sapNo]").val();
		     var repairObject={"addType":"","sapNo":sapNo,"repairOrderNumber":repairOrderNumber,"receivedDate":receivedDate,"planCompleteDate":planCompleteDate,"repairMan":repairMan,"faultDescription":faultDescription,"faultPicture":faultPicture,"processingMethod":processingMethod,"processingPicture":processingPicture,"comments":comments,"actualCompleteDate":actualCompleteDate,"instrumentFuselage":instrumentFuselage,"delpicture":delpicture};
			$.ajax({  
		          url: 'repairService/updateRepaid.do' ,  
		          type: 'POST', 
		          data:{"repairStr":JSON.stringify(repairObject)},
		          dataType:"json",
		          success: function (data) { 
		        	  if(data.result=='success'){
							location.href=basePath+"repairService/repairList.do";
					   }else{
						   alert("操作失败");  
					   }
		          },  
		          error: function (returndata) {  
		              alert("操作失败");  
		          }  
		     }); 
		
		}else{
			alert("必填项不能为空,请检查输入");
		}
	});

 	//导入维修记录
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
							url : 'repairService/importShipMents.do',
							type : 'POST',
							data : formData,
							cache : false,
							processData : false,
							contentType : false,
							dataType : "json",
							success : function(returndata) {
								if (returndata.count > 0) {
									alert("导入成功" + returndata.count + "条数据");
									location.href=basePath+"repairService/repairList.do";
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
					$(".fileNameMessage").html("请选择维修记录文件");
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
//删除故障图片
function deleteMpic(litag) {
var picName = $(litag).children(".deleteMpic").prev().attr("data-id");
var picture = $("[name=faultPicture]").val();
if (confirm("确认要删除吗？")) {
	//if (picture.indexOf(",") != -1) {// 只有一个文件
		var location = picture.indexOf(",");
		var location2 = picture.indexOf(","+picName);
		var location3 = picture.indexOf(picName+",");
		var subpicName;
		if (location2 > -1) {//p1,p2 删除p2
			subpicName = ","+picName ;
			var newPict = picture.replace(subpicName, "");
		} else if (location3 > -1) {//p1,p2 删除p1
			subpicName =picName+"," ;
			var newPict = picture.replace(subpicName, "");
		}else if (location== -1) {//只有一个图片
			subpicName=picName;
			var newPict = picture.replace(subpicName, "");
		} 

		$("[name=faultPicture]").val(newPict);
		if ($("[name=delpicture]").val() == '') {
			$("[name=delpicture]").val(picName);
		} else {
			picName += "," + $("[name=delpicture]").val();
			$("[name=delpicture]").val(picName);
		}
		$(litag).detach();
	//}
}
}
//删除处理方法图片
//删除文件
function deleteDealMpic(litag) {
var picName = $(litag).children(".deleteMpic").prev().attr("data-id");
var picture = $("[name=processingPicture]").val();
if (confirm("确认要删除吗？")) {
	var location = picture.indexOf(",");
	var location2 = picture.indexOf(","+picName);
	var location3 = picture.indexOf(picName+",");
	var subpicName;
	if (location2 > -1) {//p1,p2 删除p2
		subpicName = ","+picName ;
		var newPict = picture.replace(subpicName, "");
	} else if (location3 > -1) {//p1,p2 删除p1
		subpicName =picName+"," ;
		var newPict = picture.replace(subpicName, "");
	}else if (location== -1) {//只有一个图片
		subpicName=picName;
		var newPict = picture.replace(subpicName, "");
	} 

	$("[name=processingPicture]").val(newPict);
	if ($("[name=delpicture]").val() == '') {
		$("[name=delpicture]").val(picName);
	} else {
		picName += "," + $("[name=delpicture]").val();
		$("[name=delpicture]").val(picName);
	}
	$(litag).detach();
}
}
// 显示/隐藏删除图标
function showDeletepic(litag) {
	$(litag).children(".deleteMpic").attr("style", "display:block");
}
function hiddelDeletepic(litag) {
	$(litag).children(".deleteMpic").attr("style", "display:none");
}
 function validate(repairOrderNumber,instrumentFuselage,shipmentNo,receivedDate,planCompleteDate,repairMan,faultDescription){
//repairOrderNumber,instrumentFuselage,shipmentNo,receivedDate,planCompleteDate,repairMan,faultDescription
   var count=0;
   if(repairOrderNumber==''){
		   count+=1;
   }
   if(instrumentFuselage==''){
	   count+=1;
   }
//   if(shipmentNo==''){
//	   count+=1;
//   }	
   if(receivedDate==''){
	   count+=1;
   }
   if(planCompleteDate==''){
	   count+=1;
   }
   if(repairMan==''){
	   count+=1;
   }
   if(faultDescription==''){
	   count+=1;
   }
   if(count>0){
	   return false;
   }else{
	   
	   return true;
   }
 }