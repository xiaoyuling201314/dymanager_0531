//删除文件
 function deleteFile(){
	  	 $("#fileicon-pdf").attr("style","display:none");
    	 $("#fileicon-word").attr("style","display:none");
	     $(".deletFile").attr("style","display:none");
	     $("[name=executionStandard]").val("");
	     $("[name=myFile]").val("");
 }
 function validate(){
   var executionStandard=$("[name=executionStandard]").val();
   var reg=/^[^*\/\\|:<>?\"]*$/;
   var myFile=$("[name=myFile]").val();
   var picture=$("[name=picture]").val();
   var count=0;
   if(executionStandard!=''){
	   if(!reg.test(executionStandard)){
		   alert("执行标/准备案号不能包含下列字符:\ / \\: * ? \" < >|");
		   count+=1;
		 }else{
			 if(myFile==''){
				 alert("请选择执行标/准备案号文件");
				 count+=1;
			 }else{
				 if(picture==''){
				      alert("请至少上传一张图片");
				      count+=1;
				   }
			 }
		 }
   }
  if(count==0){
//	  if(picture==''){
//	      alert("请至少上传一张图片");
//	      count+=1;
//	   }
  }
   if(count>0){
	   return false;
   }else{
	   return true;
   }
 }