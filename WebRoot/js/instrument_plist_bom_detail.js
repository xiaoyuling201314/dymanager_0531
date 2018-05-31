function sel(all){
			var peakhours = document.getElementsByName("selectId");
			for(var i=0;i<peakhours.length;i++){
				peakhours[i].checked = all.checked;
			}
		}
function changeallsel(){
			if(getallselect()){
				document.getElementById("selectAll2").checked=true;
			}else{
				document.getElementById("selectAll2").checked=false;
			}
		}
function getallselect(){
			var peakhours = document.getElementsByName("selectId");
			var flag = true;
			for(var i=0;i<peakhours.length;i++){
				flag = flag&peakhours[i].checked;
			}
			
			return flag;
		}
//物料图纸下载选择
function selDraws(all){
	var peakhours = document.getElementsByName("selectDrawsId");
	for(var i=0;i<peakhours.length;i++){
		peakhours[i].checked = all.checked;
	}
}
function changeallDrawssel(){
	if(getallselect()){
		document.getElementById("selectAll2").checked=true;
	}else{
		document.getElementById("selectAll2").checked=false;
	}
}
function getallDrawsselect(){
	var peakhours = document.getElementsByName("selectDrawsId");
	var flag = true;
	for(var i=0;i<peakhours.length;i++){
		flag = flag&peakhours[i].checked;
	}
	
	return flag;
}
//删除文件
function deleteFile(){
	     $(".fileicon").attr("style","display:none");
	     $(".deletFile").attr("style","display:none");
	     $("[name=myFile]").val("");
	     $("[name=fileName]").val("");
}		
$(function(){
	$(".deletFile").attr("style", "display:none");
		$("#btnConfirm").on("click",function(){
			var peakhours = document.getElementsByName("selectId");
			var materNoList="";
			for(var i=0;i<peakhours.length;i++){
				if(peakhours[i].checked){
				 if(materNoList==''){
				 materNoList+=peakhours[i].value;
				 }
				 else{
				  materNoList+=","+peakhours[i].value;
				  }
				}
			}
			var packId=$("input[name=packId]").val();
			if(materNoList!=''){
				$.ajax({
				url : "packlingService/addDetail.do",
				type : "POST",
				dataType : "json",
				data : {"packId":packId,"materNoList":materNoList},
				success : function(data) {
				var json = eval(data); //数组  
				if(data.message!=''){
					alert(data.message);
				}
				$("#packDetailForm tr").empty("");
//		               $.each(json, function (index, item) {  
//		                   //循环获取数据      
////		                   var serNo=(index+1);  
////		                   $("#packDetailForm").append(" <tr><td>"+serNo+"</td><td>"+json[index].materielNo.materielNo+"</td><td>"+json[index].materielNo.materielName+"</td><td>"+json[index].materielNo.modelSpecification+"</td><td>"+json[index].quantity+"</td><td>"+json[index].comment+"</td><td class='gallery'><c:forEach items='"+json[index].materielNo.picture+"' var='pict' varStatus='index'><c:if test='${index.index==0 }'><a href='${picturePath}${pict}'><img width='20px' height='20px' alt='图片' src='dist/img/pic.png'></a></c:if><c:if test='${index.index!=0 }'><a style='display: none;' href='${picturePath}${pict}'>${pict}</a></c:if></c:forEach></td><td><c:if test='"+json[index].materielNo.drawings!=null && json[index].materielNo.drawings.size>0+"'><a href='#drawingModal' class='viewDrawing' data-id='"+json[index].materielNo.materielNo+"' data-action='modal'><img width='20px' height='20px' alt='图纸' src='dist/img/draws.png'></a></c:if></td><td><a class='delete' data-id='"+json[index].id+"'><i class='iconfont icon-delete'></i></a></td></tr>");
//		               });
		              // location.reload();
						location.href=basePath+"packlingService/selectPackList.do?id="+packId
					}
				})
			}
		})
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
					              	$("#drawsList").append(" <tr><td>"+serNo+"</td><td>"+json[index].drawingName+"</td><td>"+json[index].version+"</td><td>"+json[index].reviser+"</td> <td>"+json[index].updateTime+"</td> <td><div class='checkbox'><input type='checkbox' class='for-all"+serNo+"' name='selectDrawsId' onchange='changeallDrawssel(this)' value='"+json[index].drawingName+"' id='select"+serNo+"' data-id='"+materielNo+"'><label for='select"+serNo+"'></label></div> </td></tr>");
					               });
						},
						error : function() {
						}
					})
	      });
      
      //文件下载方法,
      //1.获取要下载的图纸名称
      //2.进行下载操作
      $("#btnDownload").on("click",function(){
			var peakhours = document.getElementsByName("selectDrawsId");
			var drawsList="";
			var materielNo="";
			for(var i=0;i<peakhours.length;i++){
				if(peakhours[i].checked){
				 if(drawsList==''){
					 drawsList+=peakhours[i].value;
				 }
				 else{
					 drawsList+=","+peakhours[i].value;
				  }
				 if(materielNo==''){
					 materielNo=$(peakhours[i]).attr("data-id");
				 }
				}
			}
			location.href=basePath+"materielDrawService/downloadDrawing.do?drawsList="+drawsList+"&materielNo="+materielNo;
		});
      //表单编辑
      $("#packDetailForm .edit").dblclick(function(){
		    var td = $(this);
		    // 根据表格文本创建文本框 并加入表表中--文本框的样式自己调整
		    var text = td.text();
		   var txt ="";
		    if($(td).attr("id")=='quantity'){
		      txt = $("<input type='text' style='width:40px;'>").val(text);
		    }else{
		      txt = $("<input type='text'>").val(text);
		    }
		    txt.blur(function(){
		        // 失去焦点，保存值。于服务器交互自己再写,最好ajax
		        var newText = $(this).val();
		        var comment= $(this).val();
		         if($(td).attr("id")=='quantity'){
		         	comment="";
		         	if(!/^[0-9]\d*$/.test(newText)){
						alert("数量只能输入正整数");
						td.text(text);
			    		return;
					}
		           }else{
		         	 newText="";
		           }
		           var id=$(td).attr("data-id");
		           $.ajax({
		             	url:"packlingService/updateDetail.do",
						type : "POST",
						data : {
							"quantityNum" : newText,"packId":id,"comment":comment
						},
						dataType:"json",
						success:function(data) {
				              
						}
		           });
		        // 移除文本框,显示新值
		        $(this).remove();
		         td.text(newText!="" ? newText:comment);
		    });
		    td.text("");
		    td.append(txt);
		});
//		$("#button").on("click",function(){
//		   $("[name=plistSearchForm]").submit();
//		})
			//物料导入 start
			//$(".fileicon").attr("style","display:none");
			 $(".deletFile").attr("style","display:none");
			 $("#completeForm").attr("style","display:none");
		     $("#importComplete").on("click",function(){
		         $("#detailPage").attr("style","display:none");
		         $("#completeForm").attr("style","display:block");
		         $("#importComplete").attr("style","display:none");
		         $(".subtitle").removeAttr("style");
		      });
		      
		        $("#returnDetail").on("click",function(){
		          location.reload();
		      });
		      //物料导入end
});
function initData(){
    var materielKeys=$("input[name=materielKeys]").val();
		$.ajax({
		url : "completeService/selectMateriel.do",
		type : "POST",
		dataType : "json",
		data : {"materielKeys":materielKeys},
		success : function(data) {
		var json = eval(data.listData); //数组  
		var serNo=(pDataMaterStartIndex+1);
		$("#materForm tr").empty("");
             $.each(json, function (index, item) {  
                 //循环获取数据      
            	 var serNoright=(serNo+index);
	                 if(json[index].picture!='' && !json[index].drawings.length>0){
	              	   $("#materForm").append("<tr><td>"+serNoright+"</td> <td>"+json[index].materielNo+"</td> <td>"+json[index].materielTypeId.materielTypeName+"</td> <td class='showMaterielStyle'>"+json[index].materielName+"</td><td class='showMaterielStyle'>"+json[index].modelSpecification+"</td><td class='showCommentStyle'>"+json[index].comment+"</td><td class='gallery'><img width='20px' height='20px' alt='图片' src='dist/img/pic.png'></td><td></td><td style='width: 50px;'> <div class='checkbox' style='margin-right:-5px;'><input type='checkbox' value='"+json[index].materielNo+"' class='for-all"+serNoright+"' name='selectId' onchange='changeallsel(this)'  id='select"+serNoright+"'><label for='select"+serNoright+"' ></label> </div></td> </tr>");
	                 }else if(json[index].drawings.length>0 && json[index].picture==''){//只有图纸
	              	   $("#materForm").append("<tr><td>"+serNoright+"</td> <td>"+json[index].materielNo+"</td> <td>"+json[index].materielTypeId.materielTypeName+"</td> <td class='showMaterielStyle'>"+json[index].materielName+"</td><td class='showMaterielStyle'>"+json[index].modelSpecification+"</td><td class='showCommentStyle'>"+json[index].comment+"</td><td></td><td class='viewDrawing'><img width='20px' height='20px' alt='图纸' src='dist/img/draws.png'></td><td style='width: 50px;'> <div class='checkbox' style='margin-right:-5px;'><input type='checkbox' value='"+json[index].materielNo+"' class='for-all"+serNoright+"' name='selectId' onchange='changeallsel(this)'  id='select"+serNoright+"'><label for='select"+serNoright+"' ></label> </div></td> </tr>");
	                 }else if (json[index].picture!='' &&  json[index].drawings.length>0){//图片图纸都有
	              	   $("#materForm").append("<tr><td>"+serNoright+"</td> <td>"+json[index].materielNo+"</td> <td>"+json[index].materielTypeId.materielTypeName+"</td> <td class='showMaterielStyle'>"+json[index].materielName+"</td><td class='showMaterielStyle'>"+json[index].modelSpecification+"</td><td class='showCommentStyle'>"+json[index].comment+"</td><td class='gallery'><img width='20px' height='20px' alt='图片' src='dist/img/pic.png'></td><td class='viewDrawing'><img width='20px' height='20px' alt='图纸' src='dist/img/draws.png'></td><td style='width: 50px;'> <div class='checkbox' style='margin-right:-5px;' ><input type='checkbox' value='"+json[index].materielNo+"' class='for-all"+serNoright+"' name='selectId' onchange='changeallsel(this)'  id='select"+serNoright+"' ><label for='select"+serNoright+"' ></label> </div></td> </tr>");
	                 }else{//没有图片图纸
	              	   $("#materForm").append("<tr><td>"+serNoright+"</td> <td>"+json[index].materielNo+"</td> <td>"+json[index].materielTypeId.materielTypeName+"</td> <td class='showMaterielStyle'>"+json[index].materielName+"</td><td class='showMaterielStyle'>"+json[index].modelSpecification+"</td><td class='showCommentStyle'>"+json[index].comment+"</td><td class='gallery'></td><td class='viewDrawing'></td><td style='width: 50px;'> <div class='checkbox' style='margin-right:-5px;'><input type='checkbox' value='"+json[index].materielNo+"' class='for-all"+serNoright+"' name='selectId' onchange='changeallsel(this)'  id='select"+serNoright+"' ><label for='select"+serNoright+"' ></label> </div></td> </tr>");
	                 }
                 //$("#materForm").append("<tr><td>"+serNo+"</td> <td>"+json[index].materielNo+"</td> <td>"+json[index].materielTypeId.materielTypeName+"</td> <td>"+json[index].materielName+"</td><td>"+json[index].modelSpecification+"</td><td>"+json[index].comment+"</td><td><img width='20px' height='20px' alt='图片' src='dist/img/pic.png'></td><td><img width='20px' height='20px' alt='图纸' src='dist/img/draws.png'></td><td> <div class='checkbox'><input type='checkbox' value='"+json[index].materielNo+"' class='for-all"+serNo+"' name='selectId' onchange='changeallsel(this)'  id='select"+serNo+"'><label for='select"+serNo+"' ></label> </div></td> </tr>");
             });
			}
		})
		
	}