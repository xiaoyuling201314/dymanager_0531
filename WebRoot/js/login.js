$(function() {
	$("#userName").val($.cookie("userName"));
	$("#userPwd").val($.cookie("password"));
	$("#remPwd").attr("checked", $.cookie("rememberPwd"));
	$("#btnLogin").on("click", function() {
		var e = $("#userName").val();
		i = $("#userPwd").val();
		if ("" == e || "" == i) {
			alert("用户名或密码不能为空")
		} else {
			$("#formLogin").submit()
		}
	})

});

function remberPwd() {
	if ($.cookie("rememberPwd") == "on") {
		$("#remPwd").attr("checked", null);
		$.removeCookie("userName", {
			path : "/"
		});
		$.removeCookie("password", {
			path : "/"
		});
		$.removeCookie("rememberPwd", {
			path : "/"
		});
	}
}