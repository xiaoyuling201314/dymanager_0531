require(['../config.min', '../base.min'], function (config, base) {
  require(['common/upload.min'], function () {
    $('#picUpload').uploader({
      server: basePath + 'materielService/uploadMater.do'
    }).on('accept.upload', function (e, data) {
      var picName = $('input[name="picture"]').val();
      if (picName == '') {
        picName = data.fileName;
      } else {
        picName = picName + ',' + data.fileName;
      }
      $('input[name="picture"]').val(picName);

      console.log($('input[name="picture"]').val());
    })
  });
  require(['common/modal.min']);  // 引入模态框
});
