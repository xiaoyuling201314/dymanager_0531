require(['../config.min', '../base.min'], function () {
  // 分页
  require(['common/pagination.min'], function () {
    $('#pagination').pagination({
      pageCount: 20,
      current: 1
    })
  });

  // 图片灯箱
  require(['common/lightbox.min'], function (baguetteBox) {
    baguetteBox.run('.gallery');
  });

  require(['common/alert.min'], function(alerty) {
    $('.close').on('click', function() {
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