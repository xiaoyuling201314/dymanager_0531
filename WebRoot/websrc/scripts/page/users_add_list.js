require(['../config.min', '../base.min'], function () {
  // 分页
  require(['common/pagination.min'], function () {
    $('#pagination').pagination({
      pageCount: pageCount,
      current: curPage,
      callback: function(api) {
        var cur = api.getCurrent(); //点击的页码
        $("#listForm input[name='curPage']").val(cur);
        $('#listForm').submit();
      }
    })
  });
  
  require(['common/alert.min'], function(alerty) {
    $('.delete').on('click', function() {
      alerty.confirm('您要确定删除吗？', function() {
        $.ajax({
          url: '/path/to/file',
          type: 'default GET (Other values: POST)',
          dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
          data: {param1: 'value1'},
          success: function() {
            
          }
        })
      })
    });  
  });
});
