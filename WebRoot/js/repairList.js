$(function(){
	$(".flip").on("click",function(){//$(".flip").on("mouseover",function(){
		$(".state").attr("size","4");
		$(".panel").attr("style","display:block;text-align: center;");
		
	});
	$("li").on("click",function(){//选中状态后隐藏下拉列表并更新table数据
		//style="color: rgba(40, 139, 255, 1); "
		$("li").attr("style","color:black");
		$(this).attr("style","color: rgba(40, 139, 255, 1);");
		$(".panel").attr("style","display:none");
		var state=$(this).attr("data-id");
		$("[name=state]").val(state);
		$(".search").submit();
		//$("li").attr("style","color:black");
		$(this).attr("style","color: rgba(40, 139, 255, 1);");

	});
	$(".hideState").on("click",function(){//选中状态后隐藏下拉列表并更新table数据
		$(".panel").attr("style","display:none");
	});
	
	var state=$("[name=state]").val();
	if(state==1){
		$(".state1").attr("style","color: rgba(40, 139, 255, 1);");
	}else if(state==2){
		$(".state2").attr("style","color: rgba(40, 139, 255, 1);");
	}else if(state==3){
		$(".state3").attr("style","color: rgba(40, 139, 255, 1);");
	}else {
		$(".state0").attr("style","color: rgba(40, 139, 255, 1);");
	}
});