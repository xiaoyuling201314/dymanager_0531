  $(function(){
		     if($(".type")=='add'){
		      	$("#materials").attr("style","display:none");
		       	$("#materialDraws").attr("style","display:block");
		     }
	      $("#addDraws").on("click",function(){//
		       $("#materials").attr("style","display:none");
		       $("#materialDraws").attr("style","display:block");
	       //查询已有图纸信息
		       var materielNo = $(this).attr("data-id");
 	      		$.ajax({
 						url : "materielDrawService/selectDrawsList.do",
 						type : "POST",
 						data : {"materielNo" : materielNo},
 						success : function(data) {
 							var json = eval(data); //数组  
 							$("#drawsForm tr").empty("");
 					               $.each(json, function (index, item) {  
 					                   //循环获取数据      
 					                   var serNo=(index+1);  
 					              	$("#drawsForm").append(" <tr><td>"+serNo+"</td><td style='text-align:left;'>"+json[index].drawingName+"</td><td>"+json[index].version+"</td><td>"+json[index].reviser+"</td> <td>"+json[index].revisedRecord+"</td> <td><a onclick='deleteDraws(this)' data-id='"+json[index].id+"'><i class='iconfont icon-delete'></i></a></td></tr>");
 					               });
 						},
 						error : function() {
 						}
 					})
	      }); 
	           $("#saveFrom,#returnForm").on("click",function(){
			       $("#materialDraws").attr("style","display:none");
			       $("#materials").attr("style","display:block");
	       
	      });
	           
	       //查看物料图纸信息
	   		//查看图纸列表
	  		 $(".viewDrawing").on("click",function(){
	  	      		var materielNo = $(this).attr("data-id");
	  	      		$.ajax({
	  						url : "materielDrawService/selectDrawsList.do",
	  						type : "POST",
	  						data : {"materielNo" : materielNo},
	  						success : function(data) {
	  							var json = eval(data); //数组  
	  							$("#drawsList tr").empty("");
	  					               $.each(json, function (index, item) {  
	  					                   //循环获取数据      
	  					                   var serNo=(index+1);  
	  					              	$("#drawsList").append(" <tr><td>"+serNo+"</td><td style='text-align:left;width:350px;'>"+json[index].drawingName+"</td><td>"+json[index].version+"</td><td>"+json[index].reviser+"</td> <td>"+json[index].updateTime+"</td> <td><div class='checkbox'><input type='checkbox' class='for-all"+serNo+"' name='selectId' onchange='changeallsel(this)' value='"+json[index].drawingName+"' id='select"+serNo+"'><label for='select"+serNo+"'></label></div> </td></tr>");
	  					               });
	  						},
	  						error : function() {
	  						}
	  					})
	  	      });

	  		   $("#addMater").on("click",function(){//提交表单添加物料
	  				  $("#materials-form").submit();
	  				  $("#drawsForm tr").remove();
	  			});
	  	//验证图纸信息
	  	$("#saveDraws").on("click", function() {
	  			var fileName = $("[name=drawingName]").val();
	  			var version = $("[name=version]").val();
	  			var subfix = test($("input[name=myFile]").val());
	  			var materielNo=$("[name=materielNo]").val();
	  			$.ajax({
	  				url : "materielDrawService/checkFileVersion.do",
	  				type : "POST",
	  				dataType : "json",
	  				data : {
	  				    "materielNo":materielNo,
	  					"fileName" : fileName,
	  					"version" : version,
	  					"subfix" : subfix
	  				},
	  				success : function(data) {
	  					if (data.isExit == 'true') {// 存在
	  						alert("该图纸" + version + "版本已存在,请确认图纸文件或版本号是否有误");
	  						//clearDraws();
	  					} else {
	  						doUpload();
	  					}
	  				},
	  				error : function() {
	  					alert("操作失败");
	  				}
	  			});
	  		});	
	  		  $("#returnbtn").on("click",function(){
	  	        if(confirm("确定放弃编辑返回吗？")){
	  	           location.href=basePath+"materielService/materielList.do";
	  	        }
	  	     });
	    });
  function selectDawsFile() {
		var myfile = $(".fileicon2");
		var fileName = myfile.val();
		if (myfile.val() == '') {
			alert("请选择文件" + myfile.val());
		} else {
			var subfix = test(fileName);
			fileName = fileName.substring(fileName.lastIndexOf('\\') + 1,fileName.lastIndexOf("."));// 截取文件名
			$(".fileicon").attr("style", "display:block");
			$("[name=drawingName]").val(fileName);
			$(".drawingNameMessage").html("");
			check=true;
			//			if (subfix == '.pdf') {
//				$("#fileicon-pdf").attr("style", "display:block");
//				  $("#fileicon-picture").attr("style","display:none");
//				$("[name=drawingName]").val(fileName);
//			} else if(subfix=='.jpg' || subfix=='.jpeg' || subfix=='.png'){
//			    $("#fileicon-picture").attr("style","display:block");
//		        $("#fileicon-pdf").attr("style","display:none");
//		        $("[name=drawingName]").val(fileName);
//			} else {
//				alert("请选择图片或pdf格式文件");
//				$("input[name=myFile]").val("");
//			}

		}
	}
  function doUpload() {  
	  if(validateDraws()){
		 // $("#saveDraws").attr("data-dismiss","modal");
		     var formData = new FormData($("#drawingsForm")[0]);  // 要求使用的html对象
		     $.ajax({  
		      	  async: false,  
		          url: 'materielDrawService/uploadMaterielDraws.do?type=edit' ,  
		          type: 'POST',  
		          data: formData,  
		          // 下面三个参数要指定，如果不指定，会报一个JQuery的错误 
		　　　　　　   cache: false,  
		          processData: false, 
		          contentType: false,  
		          dataType:"json",
		          success: function (returndata) {  
		           var json = eval(returndata); //数组  
						$("#drawsForm tr").empty("");
		               $.each(json, function (index, item) {  
		                   //循环获取数据      
		                  // var serNo=parseInt(drawsCount)+1;  //json[index].materielNo
		            	   var serNo=(index+1);
		               		//$("#drawsForm").append("<tr><td>"+serNo+"</td><td>"+json[index].drawingName+"</td><td>"+json[index].version+"</td> <td>"+json[index].reviser+"</td><td>"+json[index].revisedRecord+"</td> <td><a onclick='deleteDraws(this)' data-id='"+json[index].id+"'><i class='iconfont icon-delete'></i></a></td></tr>");
		                   $("#drawsForm").append("<tr><td>"+serNo+"</td><td style='text-align:left;width:350px;'>"+json[index].drawingName+"</td><td>"+json[index].version+"</td> <td>"+json[index].reviser+"</td><td>"+json[index].revisedRecord+"</td> <td><a onclick='deleteDraws(this);' data-id='"+json[index].drawingName+"'><i class='iconfont icon-delete'></i></a></td></tr>");//data-id='"+json[index].drawingName+"'
//		              		$("#addDraws").append("<input type='hidden' name='drawings["+parseInt(drawsIndex)+"].drawingName' class='hiddenDraws' value='"+json[index].drawingName+"' />");
//		              	   $("#addDraws").append("<input type='hidden' name='drawings["+parseInt(drawsIndex)+"].version' class='hiddenDraws' value='"+json[index].version+"' />");
//			          	   $("#addDraws").append("<input type='hidden' name='drawings["+parseInt(drawsIndex)+"].reviser' class='hiddenDraws' value='"+json[index].reviser+"' />");
//			               $("#addDraws").append("<input type='hidden' name='drawings["+parseInt(drawsIndex)+"].revisedRecord' class='hiddenDraws' value='"+json[index].revisedRecord+"' />");
			               //drawsIndex+=1;
		               });
		               clearDraws();
		          },  
		          error: function (returndata) {  
		              alert("操作失败");  
		          }  
		     }); 
		     $("#drawingAddModal").modal('hide');
	  }

     } 
  function deleteDraws(tag){
		 var drawsName=$(tag).attr("data-id");
		if(confirm("您确定要删除吗？")){
				$.ajax({
				url:"materielDrawService/deleteMaterielDraws.do?type=edit",
				type:"POST",
				data:{"id":drawsName},
				success:function(data){
					var json = eval(data); //数组  
					$("#drawsForm tr").empty("");
			               $.each(json, function (index, item) {  
			                  // 循环获取数据      
			                   var serNo=(index+1);  
			               		$("#drawsForm").append("<tr><td>"+serNo+"</td><td style='text-align:left;width:350px;'>"+json[index].drawingName+"</td><td>"+json[index].version+"</td><td>"+json[index].reviser+"</td><td>"+json[index].revisedRecord+"</td><td><a onclick='deleteDraws(this);' data-id="+json[index].id+"><i class='iconfont icon-delete'></i></a></td> </tr>");
			               });
						},
				error:function(){
					alert("删除失败");
				}
				})
		}
  }
  //显示/隐藏删除图标
  function showDeleteMpic(litag){
	  $(litag).children(".deleteMpic").attr("style","display:block");
  }
  function hiddelDeleteMpic(litag){
	  $(litag).children(".deleteMpic").attr("style","display:none");
  }
  //删除文件
  function deleteMpic(litag){
	 if(confirm("确认要删除吗？")){
		 var picName=$(litag).children(".deleteMpic").prev().attr("data-id");
		  var picture=$("[name=picture]").val();
		// var newPict=picture.replace(picName," ");
		  var location=picture.indexOf(picName+",");
		  var subpicName;
		 if(location>-1){
			  subpicName=picName+",";
			 var newPict=picture.replace(subpicName,"");
		 }else if(picture.indexOf(",")==-1){
			 subpicName=picName;
			 var newPict=picture.replace(subpicName,"");
		 }
		 else{
			 subpicName=","+picName;
			 var newPict=picture.replace(subpicName,"");
		 }
		  $("[name=picture]").val(newPict);
		  if($("[name=delpicture]").val()==''){
			    $("[name=delpicture]").val(picName);
			  }else{
			  	picName+=","+$("[name=delpicture]").val();
			    $("[name=delpicture]").val(picName);
			  }
		  $(litag).detach();
	 }
	  
  }
  
  //新增图纸
//显示/隐藏删除图标
  function showDeletepic(){
	  $(".deletFile").attr("style","display:block");
  }
  function hiddelDeletepic(){
	  $(".deletFile").attr("style","display:none");
  }
  //删除文件
  function deleteFile(){
	     $(".fileicon").attr("style","display:none");
	     $(".deletFile").attr("style","display:none");
	     $(".fileicon2").val("");
	     $("[name=drawingName]").val("");
	     $("[name=myFile]").val("");
  }
  function validateDraws(){ 
	  var drawingName=$("[name=drawingName]").val();
	  var myFile=$("[name=myFile]").val();
	  var version=$("[name=version]").val();
	  var reviser=$("[name=reviser]").val();
	  var revisedRecord=$("[name=revisedRecord]").val();
	  //var reg=/^(\w|[.-]|[\u4e00-\u9fa5]|\s){1,255}$/;
	  var reg=/^[^*\/\\|:<>?\"]*$/;
	  if(drawingName==''){
			$(".drawingNameMessage").html("图纸名称不能为空");
			check=false;
		}else{
			if(!reg.test(drawingName)){
				$(".drawingNameMessage").html("图纸名称不能包含下列任何字符：<br/> \ / \\: * ? \" < > |");
				check=false;
			}else{
				if(myFile==''){
					$(".drawingNameMessage").html("请选择图纸文件");
					check=false;
				}else{
					$(".drawingNameMessage").html("");
					check=true;
				}
			}
		}
		if(reviser==''){
			$(".reviserMessage").html("修订人不能为空");
			check=false;
		}else{
			$(".reviserMessage").html("");
			check=true;
		}
		
		if(revisedRecord==''){
			$(".revisedRecordMessage").html("修订记录不能空");
			check=false;
		}else{
			$(".revisedRecordMessage").html("");
			check=true;
		}
		if(version==''){
			$(".versionMessage").html("版本号不能为空");
			check=false;
		}else{
			$(".versionMessage").html("");
			check=true;
		}
		if(check==false){
		     return false;
		   }else{
		    return true;
		   }
  }
  function validate(){
	  var materielName=$("[name=materielName]").val();
		if(materielName==''){
			$(".materielNameMessage").html("物料名称不能为空");
			check=false;
		}else{
			$(".materielNameMessage").html("");
			check=true;
		}
		 if(check==false){
		     return false;
		   }else{
		    return true;
		   }
}