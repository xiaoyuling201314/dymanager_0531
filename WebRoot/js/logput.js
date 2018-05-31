//$(function() {
//	$(".info").on("click", function() {
//		$("#logout").attr("style", "display: block;");
//		$(".arrow").attr("style","-webkit-transform: rotate(180deg);-ms-transform: rotate(180deg);-o-transform: rotate(180deg);transform: rotate(180deg)");
//	})
//	$("#logout").on("mouseout", function() {
//		$("#logout").attr("style", "display: none");
//		$(".arrow").attr("style","margin-left: 16px;display: inline-block;-webkit-transition: -webkit-transform .3s ease-in-out;-o-transition: -o-transform .3s ease-in-out;transition: transform .3s ease-in-out;transition: transform .3s ease-in-out, -webkit-transform .3s ease-in-out,-o-transform .3s ease-in-out");
//	})
//
//	$("#operator").on("mouseover", function() {
//		$("#logout").attr("style", "display: block");
//	})
//
//});


$(function() {
	$(".info").on("click", function() {
		$("#logout").attr("style", "display: block;");
		$(".arrow").attr("style","-webkit-transform: rotate(180deg);-ms-transform: rotate(180deg);-o-transform: rotate(180deg);transform: rotate(180deg)");
		
	})
	$("#logout").on("mouseout", function() {
		$("#logout").attr("style", "display: none");
		$(".arrow").attr("style","margin-left: 16px;display: inline-block;-webkit-transition: -webkit-transform .3s ease-in-out;-o-transition: -o-transform .3s ease-in-out;transition: transform .3s ease-in-out;transition: transform .3s ease-in-out, -webkit-transform .3s ease-in-out,-o-transform .3s ease-in-out");
	})
	$("#operator").on("mouseover", function() {
		$("#logout").attr("style", "display: block");
	})
});