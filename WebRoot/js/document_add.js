$(function(){
	//隐藏图标
	$(".fileicon").attr("style","display:none");
	 $(".deletFile").attr("style","display:none");
	 $("#fileicon-pdf").attr("style","display:none");
	 $("#fileicon-word").attr("style","display:none");
	 $("#fileicon").attr("style","display:none");
});
function test(file_name){
		var result ="." + file_name.replace(/.+\./, "");//截取文件后缀名
		return result;
	  }
function selectManualFile() {
		var myfile = $("input[name=myFile]");
		var fileName = myfile.val();
		if (myfile.val() == '') {
			alert("请选择文件" + myfile.val());
		} else {
			var subfix = test(fileName);
			fileName = fileName.substring(fileName.lastIndexOf('\\') + 1,
					fileName.lastIndexOf("."));// 截取文件名
			if (subfix == '.pdf') {
				$("#fileicon-pdf").attr("style", "display:block");
				$("#fileicon-word").attr("style", "display:none");
				$("[name=fileName]").val(fileName);
			} else if (subfix == '.doc' || subfix == '.docx') {
				$("#fileicon-word").attr("style", "display:block");
				$("#fileicon-pdf").attr("style", "display:none");
				$("[name=fileName]").val(fileName);
				$(".fileNameMessage").html("");
			} else {
				alert("请选择word或pdf格式文件");
				$("input[name=myFile]").val("");
			}

		}
	}
		  // 删除文件
	function deleteFile() {
		$("#fileicon-pdf").attr("style", "display:none");
		$("#fileicon-word").attr("style", "display:none");
		$(".deletFile").attr("style", "display:none");
		$("[name=fileName]").val("");
		$("[name=myFile]").val("");
	}
		function validate() {
	var fileName = $("[name=fileName]").val();
	var myFile = $("[name=myFile]").val();
	var version = $("[name=version]").val();
	var reviser = $("[name=reviser]").val();
	var revisedRecord = $("[name=revisedRecord]").val();
	var count = 0;
	//var reg=/^(\w|[.-]|[\u4e00-\u9fa5]|\s){1,255}$/;
	 var reg=/^[^*\/\\|:<>?\"]*$/;
	if (fileName == '') {
		$(".fileNameMessage").html("仪器设计文档名称不能为空");
		count += 1;
	} else {
		if(!reg.test(fileName)){
			$(".fileNameMessage").html("说明书名称不能包含下列任何字符：<br/>\ / \\: * ? \" < > |");
			count += 1;
		}else{
			if (myFile == '') {
				$(".fileNameMessage").html("请选择设计文档文件");
				count += 1;
			} else {
				$(".fileNameMessage").html("");
			}
		}
	}

	if (version == '') {
		$(".versionMessage").html("版本号不能为空");
		count += 1;
	} else {
		$(".versionMessage").html("");
	}
	if (reviser == '') {
		$(".reviserMessage").html("修订人不能为空");
		count += 1;
	} else {
		$(".reviserMessage").html("");
	}
	if (revisedRecord == '') {
		$(".revisedRecordMessage").html("修订记录不能为空");
		count += 1;
	} else {
		$(".revisedRecordMessage").html("");
	}
	if (count > 0) {
		return false;
	} else {
		return true;
	}
	}