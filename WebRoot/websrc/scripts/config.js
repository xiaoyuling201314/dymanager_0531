requirejs.config({
  baseUrl: 'dist/js/lib',
  paths: {
    'common': '../common',
    'page': '../page',
    'jquery': 'jquery.min',
    'cookie': 'jquery.cookie.min',
    'webuploader': 'webuploader.min'
  },
  shim:{
    'webuploader':{deps:['jquery'],exports:'WebUploader'}
  }
});
