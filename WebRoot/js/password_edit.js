$(function(){
	    $("#btnSave").on("click",function(){
	    var password=$("[name=password]").val();
	    var newPassword=$("[name=newPassword]").val();
	    var rePassword=$("[name=rePassword]").val();
		   if(validate(password,newPassword,rePassword)){
		        $.ajax({
 				url : "userService/modifyPassword.do",
 				type : "POST",
 				data : {"password" : password,"newPassword":newPassword},
 				success : function(data) {
					if(data.count>0){
					 alert("修改成功");
					 $("[name=password]").val("");
					 $("[name=newPassword]").val("");
					 $("[name=rePassword]").val("");
					}else if(data.count=='-1'){
					   alert("原密码错误");
					}
					else{
					 alert("修改失败");
					}
 			    },
 				error : function() {
 					alert("操作失败");
 				}
 			})
		   }
	     
	    });
	  });
	  		function validate(password,newPassword,rePassword){
	  		var count=0;
			   if(password==''){
			      $(".passwordMessage").html("原密码不能为空");
			      count+=1;
			   }else{
			       $(".passwordMessage").html("");
			   }
			   if(newPassword==''){
			      $(".newPasswordMessage").html("新密码不能为空");
			   }else{
			       if(password==newPassword){
			       	$(".newPasswordMessage").html("新密码不能和原密码相同");
			    	 count+=1;
				   }else{
				       $(".newPasswordMessage").html("");
				   }
			   }
			  
			   if(rePassword==''){
			      $(".rePasswordMessage").html("确认密码不能为空");
			     count+=1;
			   }else{
			     if(rePassword!=newPassword){
			      $(".rePasswordMessage").html("两次输入密码不一致");
				    count+=1;
				   }else{
				     $(".rePasswordMessage").html("");
				   }
			   }
			  
			   if(count>0){
			     return false;
			   }else{
			    return true;
			   }
		}