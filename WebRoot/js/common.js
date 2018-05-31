$(function() {
	//隐藏文件图标
	 $("#fileicon").attr("style","display:none");
	 $(".fileicon").attr("style","display:none");
	//提交搜索表单
	$("#button").on("click", function() {
		$(".search").submit();
	});

});
function test(file_name){
	var result ="." + file_name.replace(/.+\./, "");//截取文件后缀名
	return result;
  }
function selectFile(){
 	var myfile= $("input[name=myFile]");
 	if(myfile.val()==''){
 		alert("请选择文件");
 	}else{
 	var fileName=myfile.val();
 	 fileName=fileName.substring(fileName.lastIndexOf('\\')+1,fileName.lastIndexOf("."));//截取文件名
 	 $("#fileicon").attr("style","display:block");
 	$("[name=fileName]").val(fileName);
 	}
  }

function selectExcelFile(){
 	var myfile= $("input[name=myFile]");
 	var subfix=test(myfile.val());
 	var fileName=myfile.val();
 	fileName=fileName.substring(fileName.lastIndexOf('\\')+1,fileName.lastIndexOf("."));//截取文件名
 	if(myfile.val()==''){
 		alert("请选择文件");
 	}else if(subfix=='.xls' || subfix=='.xlsx'){
 	 $(".fileicon").attr("style","display:block");
     $("[name=fileName]").val(fileName);
     $(".fileNameMessage").html("");
 	}else {
 		alert("请选择excel格式文件");
 	}
  }
 function selectInstrumentFile() {
	var myfile = $("input[name=myFile]");
	var fileName = myfile.val();
	if (myfile.val() == '') {
		alert("请选择文件" + myfile.val());
	} else {
		var subfix = test(fileName);
		fileName = fileName.substring(fileName.lastIndexOf('\\') + 1,fileName.lastIndexOf("."));// 截取文件名
		if (subfix == '.pdf') {
			$("#fileicon-pdf").attr("style", "display:block");
			$("#fileicon-word").attr("style", "display:none");
			$("[name=executionStandard]").val(fileName);
		} else if (subfix == '.doc' || subfix == '.docx') {
			$("#fileicon-word").attr("style", "display:block");
			$("#fileicon-pdf").attr("style", "display:none");
			$("[name=executionStandard]").val(fileName);
		} else {
			alert("请选择word或pdf格式文件");
			$("input[name=myFile]").val("");
		}

	}
}
 //////////////////////////////////////////////////
 //显示/隐藏删除图标
 function showDeletepic(){
	  $(".deletFile").attr("style","display:block");
 }
 function hiddelDeletepic(){
	  $(".deletFile").attr("style","display:none");
 }
 function clearDraws(){
	  $("[name=drawingName]").val("");
     $("[name=myFile]").val("");
     $("[name=version]").val("");
     $("[name=revisedRecord]").val("");
     $("[name=reviser]").val("");
     $(".deletFile").attr("style","display:none");
	  $(".fileicon").attr("style","display:none");
 }