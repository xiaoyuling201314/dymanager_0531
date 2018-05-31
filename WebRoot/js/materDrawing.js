  //add by xiaoyuling
function sel(all){
			var peakhours = document.getElementsByName("selectId");
			for(var i=0;i<peakhours.length;i++){
				peakhours[i].checked = all.checked;
			}
		}
		function changeallsel(){
			if(getallselect()){
				document.getElementById("selectAll").checked=true;
			}else{
				document.getElementById("selectAll").checked=false;
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
		
		
    $(function(){
      $("#search").on("click",function(){
      var materielName=$("input[name=materielName]").val();
		$.ajax({
		url : "materielService/materielList.do",
		type : "POST",
		dataType : "json",
		data : {"materielName":materielName},
		success : function(data) {
		var json = eval(data); //数组  
		$("#materielList tr").empty("");
               $.each(json, function (index, item) {  
                   //循环获取数据      
                   var serNo=(index+1);  
               		$("#materielList").append("<tr><td>"+serNo+"</td> <td>"+json[index].materielNo+"</td> <td>"+json[index].materielTypeId.materielTypeName+"</td> <td>"+json[index].materielName+"</td><td>"+json[index].modelSpecification+"</td> <td>"+json[index].footprint+"</td> <td><c:forEach items='"+json[index].picture+"' var='pict' varStatus='index'><c:if test='${index.index==0 }'><a href='${picturePath}${pict}'>${pict}</a></c:if><c:if test='${index.index!=0 }'><a style='display: none;' href='${picturePath}${pict}'>${pict}</a></c:if></c:forEach></td> <td><a href='#drawingModal' class='viewDrawing' data-id='"+json[index].materielNo+"' data-action='modal'>"+json[index].showDrawings+"</a></td><td>"+json[index].comment+"</td> <td> <div class='checkbox'><input type='checkbox' value='"+json[index].materielNo+"' class='for-all"+serNo+"' name='selectId' onchange='changeallsel(this)'  id='select"+serNo+"'><label for='select"+serNo+"' ></label> </div></td> </tr>");
                
               });
			}
		})
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
					              	$("#drawsList").append(" <tr><td>"+serNo+"</td><td style='text-align:left;'>"+json[index].drawingName+"</td><td>"+json[index].version+"</td><td>"+json[index].reviser+"</td> <td>"+json[index].updateTime+"</td> <td><div class='checkbox'><input type='checkbox' class='for-all"+serNo+"' name='selectId' onchange='changeallsel(this)' value='"+json[index].drawingName+"' id='select"+serNo+"' data-id='"+materielNo+"'><label for='select"+serNo+"'></label></div></td></tr>");
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
			var peakhours = document.getElementsByName("selectId");
			var materielNo="";
			var drawsList="";
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
			if(drawsList==''){
				alert("请选择图纸进行下载");
			}else{
				location.href=basePath+"materielDrawService/downloadDrawing.do?drawsList="+drawsList+"&materielNo="+materielNo;
			}
		})
      
});