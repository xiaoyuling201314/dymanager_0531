  $(function(){
	  var drawsIndex=0;
		     $("#materialDraws").attr("style","display:none");
		     $("#fileicon-pdf").attr("style","display:none");
		     $("#fileicon-picture").attr("style","display:none");
		     $(".deletFile").attr("style","display:none");
		     $("#fileicon").attr("style","display:none");
		     $(".fileicon").attr("style","display:none");
		     if($(".type")=='add'){
		      	$("#materials").attr("style","display:none");
		       	$("#materialDraws").attr("style","display:block");
		     }
	      $("#addDraws").on("click",function(){
		       $("#materials").attr("style","display:none");
		       $("#materialDraws").attr("style","display:block");
	       
	      }); 
	           $("#saveFrom,#returnForm").on("click",function(){
		       $("#materialDraws").attr("style","display:none");
		       $("#materials").attr("style","display:block");
	       
	      });
	       	//校验SAP是否可用
	   		$("[name=materielNo]").on("blur", function() {
	   				var materielNo = $(this).val();
	   				if (materielNo != '') {
	   					$.ajax({
	   						url : "materielService/queryMaterielNo.do",
	   						type : "POST",
	   						data : {
	   							"materielNo" : materielNo
	   						},
	   						dataType : "json",
	   						success : function(data) {
	   							if (data.result == 'exitMaterielNo') {
	   								$(".error").attr("style", "display:block");
	   								$(".right").attr("style", "display:none");
	   								$(".materielNoMessage").html("该SAP已被占用");
	   								check=false;
	   							} else {
	   								$(".right").attr("style", "display:block");
	   								$(".error").attr("style", "display:none");
	   								$("[name=materielNo]").val(materielNo);
	   								$(".materielNoMessage").html("");
	   								check=true;
	   							}
	   							
	   						},
	   						error : function() {
	   							alert("操作失败");
	   						}
	   					});
	   					$(".materielNullMessage").html("");
	   				}else{
	   					$(".right").attr("style", "display:none");
	   					$(".error").attr("style", "display:none");
	   					$(".materielNoMessage").html("");
	   					$(".materielNullMessage").html("SAP代码不能为空");
	   					check=false;
	   				}
	   			});
	   	 $(".right").attr("style","display:none");
   		 $(".error").attr("style","display:none");//#importMater
   		 $("#materialsImportform").attr("style","display:none");
   		 $("#importMater").on("click",function(){
   		    $("#materials-form").attr("style","display:none");
   		    $("#materialsImportform").attr("style","display:block");
   		 });
   		  $("#inputMater,#returnInput").on("click",function(){
   		     $("#materials-form").attr("style","display:block");
   		     $("#materialsImportform").attr("style","display:none");
   		 });
	 	
   		 $("#addMater").on("click",function(){//提交表单添加物料
			  $("#materials-form").submit();
			  $("#drawsForm tr").remove();
		});	
		$("#btnSave").on("click",function(){
		var fileName=$("[name=fileName]").val();
		var materFile=$("[name=materFile]").val();
		//var reg=/^(\w|[.-]|[\u4e00-\u9fa5]|\s){1,255}$/;
		 var reg=/^[^*\/\\|:<>?\"]*$/;
		    if(fileName!='' && materFile !=''){
		    	 if(!reg.test(fileName)){
				    $(".fileNameMessage").html("文件名称不能包含<br/>下列任何字符：\ / \\ : * ? \" < > |");
				 }else{
				       var formData = new FormData($("#materialsImportform")[0]);  // 要求使用的html对象
				     $.ajax({  
				          async: false, 
				          url: 'materielService/importMateriel.do' ,  
				          type: 'POST',  
				          data: formData, 
				          cache: false,  
				          processData: false, 
				          contentType: false,  
				          dataType:"json",
				          success: function (returndata) { 
				             alert("导入成功"+returndata.count+"条数据");
		                    location.href=basePath+"materielService/materielList.do";
				          },  
				          error: function (returndata) {  
				              alert("操作失败");  
				          }  
				     }); 
				 }
		    }else{
		     // alert("请选择物料文件");
		    	$(".fileNameMessage").html("请选择物料文件");
		    }
		});
     $("#returnbtn").on("click",function(){
     if($("[name=materielNo]").val()!='' || $("[name=materielName]").val()!=''){
        if(confirm("确定放弃编辑返回吗？")){
           location.href=basePath+"materielService/materielList.do";
        }
       }else{
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
			$(".fileicon").attr("style","display:block");
		     $("[name=drawingName]").val(fileName);
		     $(".drawingNameMessage").html("");
		}
	}
	  
  function doUpload(event) { 
	  //先验证是否为空
	  if(validateDraws()){
		  //data-dismiss="modal" 
			 // $("#saveDraws").attr("data-dismiss","modal");
		  var formData = new FormData($("#drawingsForm")[0]);  // 要求使用的html对象
		  var materNo=$("[name=materielNo]").val();
		     $.ajax({  
		      	  async: false,  
		          url: 'materielDrawService/uploadMaterielDraws.do' ,  
		          type: 'POST',  
		          data: formData,  
		          // 下面三个参数要指定，如果不指定，会报一个JQuery的错误 
		　　　　　　	cache: false,  
		          processData: false, 
		          contentType: false,  
		          dataType:"json",
		          success: function (returndata) {  
		        	  	var json = eval(returndata); //数组  
						//$("#drawsForm tr").empty("");
		               $.each(json, function (index, item) {  
		                   //循环获取数据      
		                   var serNo=parseInt(drawsIndex)+1;  //json[index].materielNo
		               		$("#drawsForm").append("<tr><td>"+serNo+"</td><td style='text-align:left;'>"+json[index].drawingName+"</td><td>"+json[index].version+"</td> <td>"+json[index].reviser+"</td><td>"+json[index].revisedRecord+"</td> <td><a onclick='deleteDraws(this);' data-id='"+json[index].drawingName+"' data-idNo='"+materNo+"'><i class='iconfont icon-delete'></i></a></td></tr>");//data-id='"+json[index].drawingName+"'
		               		$("#addDraws").append("<input type='hidden' name='drawings["+parseInt(drawsIndex)+"].drawingName' class='hiddenDraws' value='"+json[index].drawingName+"' />");
		               	   $("#addDraws").append("<input type='hidden' name='drawings["+parseInt(drawsIndex)+"].version' class='hiddenDraws' value='"+json[index].version+"' />");
			          	   $("#addDraws").append("<input type='hidden' name='drawings["+parseInt(drawsIndex)+"].reviser' class='hiddenDraws' value='"+json[index].reviser+"' />");
			               $("#addDraws").append("<input type='hidden' name='drawings["+parseInt(drawsIndex)+"].revisedRecord' class='hiddenDraws' value='"+json[index].revisedRecord+"' />");
		               		drawsIndex+=1;
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
	 var materNo=$(tag).attr("data-idNo");
	if(confirm("您确定要删除吗？")){
			$.ajax({
			url:"materielDrawService/deleteMaterielDraws.do?type=add",
			type:"POST",
			data:{"id":drawsName,"materNo":materNo},
			success:function(data){//文件删除成功，删除列表和隐藏的数据
				$(tag).parent().parent().remove();
				var tagList=$(".hiddenDraws");
				for(var i=0;i<tagList.length;i++){
					if(tagList[i].value==drawsName){
						$(tagList[i]).remove();
						$(tagList[i+1]).remove();
						$(tagList[i+2]).remove();
						$(tagList[i+3]).remove();
//						tagList[i].next().detach();
//						tagList[i].next().detach();
//						tagList[i].next().detach();
						return;
					}
				}
			},
			error:function(){
				alert("删除失败");
			}
			})
	}
}
  //显示/隐藏删除图标
  function showDeletepic(){
	  $(".deletFile").attr("style","display:block");
  }
  function hiddelDeletepic(){
	  $(".deletFile").attr("style","display:none");
  }
  //删除文件
  function deleteFile(){
	     $("#fileicon").attr("style","display:none");
	     $(".fileicon").attr("style","display:none");
	     $(".deletFile").attr("style","display:none");
	     $(".fileicon2").val("");
	     $("[name=drawingName]").val("");
	     //导入物料时文件清除
	     $("[name=myFile]").val("");
	     $("[name=fileName]").val("");
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
				$(".drawingNameMessage").html("图纸名称不能包含<br/>下列任何字符： \ / \\: * ? \" < > |");
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

  function selectMaterlFile(){
   	var myfile= $("input[name=materFile]");
   	var subfix=test(myfile.val());
   	var fileName=myfile.val();
   	fileName=fileName.substring(fileName.lastIndexOf('\\')+1,fileName.lastIndexOf("."));//截取文件名
   	if(myfile.val()==''){
   		alert("请选择文件");
   	}else if(subfix=='.xls' || subfix=='.xlsx'){
   	 $(".matericon").attr("style","display:block");
       $("[name=fileName]").val(fileName);
   	}else {
   		alert("请选择excel格式文件");
   	}
    }
       function validate(){
       var count=0;
  		  var materielNo=$("[name=materielNo]").val();
  		  var materielName=$("[name=materielName]").val();
  			if(materielNo==''){
  				$(".materielNullMessage").html("SAP代码不能为空");
  				count+=1;
  			}else{
  				$(".materielNullMessage").html("");
  			}
  			
  			if(materielName==''){
  				$(".materielNameMessage").html("物料名称不能为空");
  				count+=1;
  			}else{
  				$(".materielNameMessage").html("");
  			}
  			 if(count>0 || check==false){
  			     return false;
  			   }else{
  			    return true;
  			   }
  	}