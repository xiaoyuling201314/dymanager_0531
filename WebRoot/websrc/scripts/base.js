define(['jquery.min'], function () {
  var baseModule = {
    getTimeNow: function (idName) {
      var dt = new Date;
      var year = dt.getFullYear();
      var month = dt.getMonth();
      var date = dt.getDate();
      var hours = dt.getHours();
      var minutes = dt.getMinutes();
      var day = dt.getDay();
      var timeString =  year+'-'+month+'-'+date + '&nbsp;&nbsp;' + hours + ':' + minutes;
      
      $('#'+idName).html(timeString);
    },

    logout: function () {
      $.ajax({
        url: '/path/to/file',
        type: 'POST',
        dataType: 'json',
        data: {param1: 'value1'},
        success: function() {

        }
      })
      
    }
  };
  $(function () {
    // 当前时间
    baseModule.getTimeNow('timeNow');
  })
});
