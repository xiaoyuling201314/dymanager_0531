$(function(){
	 $(".deletFile").attr("style","display:none");
});
//显示/隐藏删除图标
function showDeletepic(){
	  $(".deletFile").attr("style","display:block");
}
function hiddelDeletepic(){
	  $(".deletFile").attr("style","display:none");
}
//删除文件
function deleteFile(){
	     $(".deletFile").attr("style","display:none");
	     $(".fileicon").attr("style","display:none");
	     $("[name=myFile]").val("");
	     $("[name=fileName]").val("");
	     $(".fileNameMessage").html("");
}
function validate(){
	  var completeMachineName=$("[name=completeMachineName]").val();
	  var completeMachineVersion=$("[name=completeMachineVersion]").val();
	  var createPerson=$("[name=createPerson]").val();
	  var revisedRecord=$("[name=revisedRecord]").val();
	  var fileName=$("[name=fileName]").val();
	  var myFile=$("[name=myFile]").val();
	  //var reg=/^(\w|[.-]|[\u4e00-\u9fa5]|\s){1,255}$/;
	  var reg=/^[^*\/\\|:<>?\"]*$/;
	  var count=0;
	 if (fileName!=''){
			 if(fileName!='' && !reg.test(fileName)){
				 $(".fileNameMessage").html("BOM单文件名称不能包含<br/>下列任何字符： \ / \\: * ? \" < > |");
				 count+=1;
			 }else{
				 $(".fileNameMessage").html("");
			 }
	  }
		 if(myFile=='' && fileName!=''){
			 $(".fileNameMessage").html("请选择BOM单文件");
			 count+=1;
		 }
		if(completeMachineName==''){
			$(".completeMachineNameMessage").html("BOM单名称不能为空");
			count+=1;
		}else{
			if(!reg.test(completeMachineName)){
				$(".completeMachineNameMessage").html("BOM单名称不能包含下列任何字符：\ / \\: * ? \" < > |");
				count+=1;
			}else{
			    $(".completeMachineNameMessage").html("");
			}
		}
		
		if(completeMachineVersion==''){
			$(".completeMachineVersionMessage").html("版本号不能为空");
			count+=1;
		}else{
			$(".completeMachineVersionMessage").html("");
		}
		if(createPerson==''){
			$(".createPersonMessage").html("修订人不能为空");
			count+=1;
		}else{
			$(".createPersonMessage").html("");
		}
		if(revisedRecord==''){
			$(".revisedRecordMessage").html("修订记录不能为空");
			count+=1;
		}else{
			$(".revisedRecordMessage").html("");
		}
		 if(count>0){
			     return false;
		}else{
			    return true;
		}
}
