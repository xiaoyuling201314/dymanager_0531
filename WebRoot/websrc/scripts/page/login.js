require(['../lib/jquery.min', '../common/alert.min'], function (jq, alerty) {
  
  require(['../lib/jquery.cookie.min'], function() {
    $("#userName").val($.cookie('userName'));
    $('#userPwd').val($.cookie('password'));
    $("#remPwd").attr("checked",$.cookie('rememberPwd'));
  });

  $(function(){
    $('#btnLogin').on('click', function() {
      var userName = $("#userName").val();
      var password = $('#userPwd').val();

      if (userName == '' || password == '') {
        alerty.alert('用户名或密码不能为空');
        return;
      }

      $('#formLogin').submit();

    });
  })
});