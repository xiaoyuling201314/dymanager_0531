require(['../config.min', '../base.min'], function () {
  require(['common/upload.min'], function () {
    $('#picUpload').uploader({
      server: ''
    })
  });

  $(function(){
    $('#statusSelect').on('change', function() {
      if ($(this).val() == '2') {
        $('#statusRelated').show();
      } else {
        $('#statusRelated').hide();
      }
    });
  })
});
