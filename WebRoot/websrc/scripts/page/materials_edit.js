require(['../config.min', '../base.min'], function () {
  require(['common/upload.min'], function () {
    $('#picUpload').uploader({
      server: ''
    })
  });
});
