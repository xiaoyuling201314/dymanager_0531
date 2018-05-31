$(function() {
		 $(".rightEmail").attr("style","display:none");
	     $(".errorEmail").attr("style","display:none");
		 $("input[name=rightList]").val("${sysUserDTO.rightList }");
			//获取权限
			$("#setRight").on("click", function() {
				var rights = document.getElementsByName("rights");
				var str = "";
				for ( var j in rights) {
					if (rights[j].checked) {
						if (str == "")
							str += rights[j].value;
						else
							str += "," + rights[j].value;
					}
				}
				$("input[name=rightList]").val(str);

			});
			
			  $("#returnbtn").on("click",function(){
		        if(confirm("确定放弃编辑返回吗？")){
		           location.href=basePath+"userService/userList.do";
		        }
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
});

function validate(){
   var count=0;
 	var password=$("[name=password]").val();
 	var name=$("[name=name]").val();
 	var email=$("[name=email]").val();
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
   if(checkEmail==false || count>0){
     return false;
   }else
   return true;
 }