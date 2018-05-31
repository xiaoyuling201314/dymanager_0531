$(function(){
	//验证设计文档文件，验证通过并提交
	$("#btnSave").on("click", function() {
		var calibrationCertificate = $("[name=calibrationCertificate]").val();
		var subfix = test($("input[name=myFile]").val());
		if(validate()){
		$.ajax({
			url : "certificateService/checkFileVersion.do",
			type : "POST",
			dataType : "json",
			data : {
				"calibrationCertificate" : calibrationCertificate,
				"subfix" : subfix
			},
			success : function(data) {
				if (data.isExit == 'true') {// 存在
					alert("该专利/证书已存在,请确认选择是否有误");
				} else {
					$("#qualification_Add").submit();
				}
			},
			error : function() {
				alert("操作失败");
			}
		});
		}
	});
});
// 显示文件删除图标
function showExecutionDeletepic() {
	$(".deletFile").attr("style", "display:block");
}
//隐藏文件删除图标
function hiddelExecutionDeletepic() {
	$(".deletFile").attr("style", "display:none");
}
// 删除文件
function deleteFile() {
	$("#fileicon-pdf").attr("style", "display:none");
	$("#fileicon-word").attr("style", "display:none");
	$(".deletFile").attr("style", "display:none");
	$("[name=calibrationCertificate]").val("");
	$("[name=myFile]").val("");
}
function selectInstrumentFile() {
	var myfile = $("input[name=myFile]");
	var fileName = myfile.val();
	if (myfile.val() == '') {
		alert("请选择文件" + myfile.val());
	} else {
		var subfix = test(fileName);
		fileName = fileName.substring(fileName.lastIndexOf('\\') + 1, fileName
				.lastIndexOf("."));// 截取文件名
		if (subfix == '.pdf') {
			$("#fileicon-pdf").attr("style", "display:block");
			$("#fileicon-word").attr("style", "display:none");
			$("[name=calibrationCertificate]").val(fileName);
		} else if (subfix == '.doc' || subfix == '.docx') {
			$("#fileicon-word").attr("style", "display:block");
			$("#fileicon-pdf").attr("style", "display:none");
			$("[name=calibrationCertificate]").val(fileName);
		} else {
			alert("请选择word或pdf格式文件");
			$("input[name=myFile]").val("");
		}

	}
}

function validate(){
var calibrationCertificate=$("[name=calibrationCertificate]").val();
var myFile=$("[name=myFile]").val();
var calibStartTime=$("[name=calibStartTime]").val();
var reviser=$("[name=reviser]").val();
var reg=/^(\w|[.-]|[\u4e00-\u9fa5]|\s){1,255}$/;
var count=0;
	if(calibrationCertificate==''){
		$(".calibrationCertificateMessage").html("专利名称不能为空");
		count+=1;
	}else{
		if(!reg.test(calibrationCertificate)){
			$(".calibrationCertificateMessage").html("专利名称不能包含下列任何字符：<br/>\ / \\ : * ? \" < > |");
			count+=1;
		}else{
			if(myFile==''){
				$(".calibrationCertificateMessage").html("请选择专利文件");
				count+=1;
			}else{
				$(".calibrationCertificateMessage").html("");
			}
		}
	}
	if(reviser==''){
		$(".reviserMessage").html("修订人不能为空");
		count+=1;
	}else{
		$(".reviserMessage").html("");
	}
	if(calibStartTime==''){
		$(".calibStartTimeMessage").html("登记日期不能为空");
		count+=1;
	}else{
		$(".calibStartTimeMessage").html("");
	}
	 if(count>0){
		     return false;
	}else{
		    return true;
	}
}