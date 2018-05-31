$(function(){
	$("#fileicon-pdf").attr("style","display:none");
	$("#fileicon-word").attr("style","display:none");
});
//删除文件
	function deleteMpic(litag) {
	var picName = $(litag).children(".deleteMpic").prev().attr("data-id");
	var picture = $("[name=picture]").val();
	if (confirm("确认要删除吗？")) {
		//if (picture.indexOf(",") != -1) {// 只有一个文件
			var location = picture.indexOf(picName + ",");
			var subpicName;
			if (location > -1) {//删除第一个或中间位置的图片
				subpicName = picName + ",";
				 newPict = picture.replace(subpicName, "");
			} else {
				subpicName = "," + picName;
				if( picture.indexOf(subpicName)>-1){//删除最后一张图片
					newPict = picture.replace(subpicName, "");
				}else{//只有一张图片
					newPict = picture.replace(picture, "");
				}
				
			}
			$("[name=picture]").val(newPict);
			if ($("[name=delpicture]").val() == '') {
				$("[name=delpicture]").val(picName);
			} else {
				picName += "," + $("[name=delpicture]").val();
				$("[name=delpicture]").val(picName);
			}
			$(litag).detach();
//		} else {
//			alert("最少保留一张图片");
//		}
	}
}
	// 显示/隐藏删除图标
	function showDeletepic(litag) {
		$(litag).children(".deleteMpic").attr("style", "display:block");
	}
	function hiddelDeletepic(litag) {
		$(litag).children(".deleteMpic").attr("style", "display:none");
	}
	//显示/隐藏删除图标
	function showExecutionDeletepic() {//executionStandard
		$(".deletFile").attr("style", "display:block");
	}
	function hiddelExecutionDeletepic() {
		$(".deletFile").attr("style", "display:none");
	}
	//删除文件
	function deleteFile() {
		$("#fileicon-pdf").attr("style", "display:none");
		$("#fileicon-word").attr("style", "display:none");
		$(".deletFile").attr("style", "display:none");
		$("[name=executionStandard]").val("");
		$("[name=myFile]").val("");
	}
	function validate(){
		   var executionStandard=$("[name=executionStandard]").val();
		   var reg=/^[^*\/\\|:<>?\"]*$/;
		   var myFile=$("[name=myFile]").val();
		   var picture=$("[name=picture]").val();
		   if(executionStandard!=''){
			   if(!reg.test(executionStandard)){
				   alert("执行标/准备案号不能包含下列字符:\ / \\: * ? \" < >|");
				   return false;
				 }else{
					 if(myFile==''){
						 alert("请选择执行标/准备案号文件");
						return false;
					 }else{
						 return true;
					 }
				 }
		   }

		 }