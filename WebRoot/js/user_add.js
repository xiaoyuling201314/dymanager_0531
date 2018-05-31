$(function(){
	 $(".right").attr("style","display:none");
     $(".error").attr("style","display:none");
     $(".rightEmail").attr("style","display:none");
     $(".errorEmail").attr("style","display:none");
     
	 $("#setRight").on("click",function(){
	   	var rights=document.getElementsByName("rights");
		 var str="";
		 for (var j in rights){
			 if(rights[j].checked){
			 if(str=="")
			 str+=rights[j].value;
			 else
			 	str+=","+rights[j].value;
			 }
		 }
		 $("input[name=rightList]").val(str);

	 });
	
	//验证邮件格式
	 $("[name=email]").on("blur",function(){
	     var reg=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;//;/^[_'.0-9a-z-]+@([0-9a-z][0-9a-z-]+'.){1,4}[a-z]{2,3}$/i;
		   var email=$("[name=email]").val();
		   if(email!=''){
		      if(!reg.test(email)){
		   		 $(".errorEmail").attr("style","display:block");
				 $(".rightEmail").attr("style","display:none");
				 $(".sapNoMessageEmail").html("邮箱格式错误");
				 checkEmail=false;
		      }else{
		        $(".rightEmail").attr("style","display:block");
		        $(".errorEmail").attr("style","display:none");
		         $(".sapNoMessageEmail").html("");
		          checkEmail=true;
		      }
		       $(".emailMessage").html("");
		   }else{
		   $(".emailMessage").html("邮箱不能为空");
	         checkEmail=false;
	       }
			
	    });
		 
	 $("#checkUser").on("blur",function(){
	    var userName=$("[name=userName]").val();
	    if(userName!=''){
	        $.ajax({
 				url : "userService/checkUser.do",
 				type : "POST",
 				data : {"userName" : userName},
 				success : function(data) {
					if(data.isExit>0){
					  $(".error").attr("style","display:block");
				      $(".right").attr("style","display:none");
				      $(".sapNoMessage").html("该用户已存在");
				      checkUser=false;
					}else{
						$(".right").attr("style","display:block");
		                $(".error").attr("style","display:none");
		                $(".sapNoMessage").html("");
		                checkUser=true;
					}
					 $(".userNameMessage").html("");
 			    },
 				error : function() {
 					alert("操作失败");
 				}
 			})
	    }else{
	      checkUser=false;
	    }
	   
	    });
	    
       $("#returnbtn").on("click",function(){
		     if($("[name=userName]").val()!='' || $("[name=password]").val()!=''){
		        if(confirm("确定放弃编辑返回吗？")){
		           location.href=basePath+"userService/userList.do";
		        }
		       }else{
		          location.href=basePath+"userService/userList.do";
		       }
	     });
});
function validate(){
	 var count=0;
	 	var userName=$("[name=userName]").val();
	 	var password=$("[name=password]").val();
	 	var name=$("[name=name]").val();
	 	var email=$("[name=email]").val();
		 if(userName==''){
		   $(".userNameMessage").html("用户名不能为空");
		   count+=1;
		 }else{
		   $(".userNameMessage").html("");
		 } 
		 if(password==''){
		   $(".passwordMessage").html("密码不能为空");
		   count+=1;
		 }else{
		   $(".passwordMessage").html("");
		 }
		  if(name==''){
		   $(".nameMessage").html("姓名不能为空");
		   count+=1;
		 }else{
		   $(".nameMessage").html("");
		 }
		  if(email==''){
		   $(".emailMessage").html("邮箱不能为空");
		   count+=1;
		 }else{
		   $(".emailMessage").html("");
		 }
	   if(checkUser==false || checkEmail==false || count>0){
	     return false;
	   }else
	   return true;
	 }