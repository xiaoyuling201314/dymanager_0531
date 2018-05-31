
define(['jquery', 'webuploader'], function ($,WebUploader) {

  var Uploader = function(element, options) {
    this.$element = $(element);
    this.options = $.extend({}, options);
    this.$uploadBox = null;  //预览盒子
    this.$actionBar = null;  //底部操作条
    this.$btnPick = null;  //选择图片按钮
    this.$btnUpload = null;  //上传按钮
    this.$info = null;  //文字信息
    this.fileCount = 0;  //文件数量
    this.fileSize = 0;  //文件大小

    this.init();
  };
  
  Uploader.prototype.init = function() {
    var options = this.options;
    var that = this;
    var pickId = ('#'+ this.$element.attr('id'));

    // 获取参数
    $.each(this._setOptions(pickId),function(key, value){
      options[key] = options[key] || value; 
    });


    // objec类型参数需要重组。或者调用时直接赋予此参数的全部参数
    if(options.multiple !== undefined) {
      options['pick']['multiple'] = options.multiple;
      delete options.multiple;
    };

    if(options.innerHTML) {
      options['pick']['innerHTML'] = options.innerHTML;
      delete options.innerHTML;
    };

    if(options.thumbWidth) {
      options['thumb']['width'] = options.thumbWidth;
      delete options.thumbWidth;
    };

    if(options.thumbHeight) {
      options['thumb']['height'] = options.thumbHeight;
      delete options.thumbHeight;
    };

    var uploader =  this._newUpload(options);
    var $uploadBox = $('<div class="upload-box">'+
                          '<ul class="file-list"></ul>'+
                          '<div class="action-bar">'+
                            '<div class="info"></div>'+
                            '<div class="btn-area">'+
                                '<a class="btn-add">继续添加</a>'+
                                '<a class="btn btn-green btn-upload">开始上传</a>'+
                            '</div>'+
                          '</div>'+
                        '</div>');
    this.$element.after($uploadBox);
    this.$uploadBox = $uploadBox;
    this.$actionBar = $uploadBox.find('.action-bar');

    this.$btnPick = this.$element.find('.webuploader-pick');
    this.$btnUpload = $uploadBox.find('.btn-upload');
    this.$info = $uploadBox.find('.info');

    // WebUploader 插件自带
    if (!WebUploader.Uploader.support()) {  
      alert('上传组件不支持您的浏览器！');
      return;
    }
    this._bindEvents(uploader);
  };

  // 实例化WebUploader
  Uploader.prototype._newUpload = function(options) {
    return new WebUploader.Uploader(options);
  };

  // 创建图片上传DOM
  Uploader.prototype._createBox = function(file, uploader) {
    var $element = this.$element;
    var that = this;
    var $uploadBox = this.$uploadBox;
    var options = this.options;
    
    var $li = $('<li id="'+file.id+'">' +
                  '<div class="preview"></div>' +
                  '<div class="progress linear linear-primary"><div class="determinate"></div></div>' +
                  '<div class="badge badge-radius"></div>'+
                  '<ul class="file-panel">'+
                    '<li><a class="cancel" title="删除"><i class="iconfont icon-delete"></i></a></li>'+
                  '</ul>'+
                  '<div class="title" title="'+file.name+'">' + file.name + '</div>' +
                '</li>'
              ).appendTo($uploadBox.find('.file-list'));

    $li.css('width', this.options.thumb.width);

    //生成预览缩略图
    uploader.makeThumb(file, function(error, src) {
      if (error) {
        $li.find('.preview').text('不能生成预览');
        return;
      }
      $li.find('.preview').append('<img src="'+src+'" >').css({
        height: options.thumb.height,
        width: options.thumb.width
      });;
    });

    if ($uploadBox.find('li').length > 0) {
      that.$btnPick.hide();
      that.$actionBar.show();  

      if ($uploadBox.find('.webuploader-pick').length <= 0) {
        var $btnPickContinue = $uploadBox.find('.btn-add');

        if (options.fileNumLimit === 1) {
          $btnPickContinue.remove();
        } else {
          uploader.addButton({
            id: $uploadBox.find('.btn-add')
          });
        }
      }
    }

    // 删除图片及缩略图
    $li.on('click', '.file-panel .cancel', function() {
      that._removeFile($(this).parents('li'), file, uploader);
    });

    // 上传
    this.$btnUpload.on('click', function() {
      uploader.upload();
    });
  };

  // WebUploader 事件
  // http://fex.baidu.com/webuploader/doc/index.html#WebUploader_Uploader_events
  Uploader.prototype._bindEvents = function(uploader) {
    var that = this;

    // 当文件被加入队列以后触发
    uploader.on('fileQueued', function(file) {
      that.fileCount++;
      that.fileSize += file.size;

      that._createBox(file, uploader);
      that._updateInfo('ready', uploader);
      that._updateState('ready', file);
    });

    // 当文件被移除队列后触发
    uploader.on('fileDequeued', function(file) {
      that.fileCount--;
      that.fileSize -= file.size;
      that._updateInfo('ready', uploader);
    });

    // 上传过程中触发，携带上传进度
    uploader.on('uploadProgress', function(file, percentage) {
      $('#'+file.id).find('.progress').children('.determinate').css('width', Math.round(percentage * 100 ) + '%');
    });

    // 当某个文件上传到服务端响应后，会派送此事件来询问服务端响应是否有效。
    uploader.on('uploadAccept', function(object, data) {
      that.$element.trigger('accept.upload', data);  //创建自定义事件将服务器返回数据传给前台。
    });

    // 文件错误触发
    uploader.on('error', function(code) {
      var txt = '';
      switch(code) {
        case  'F_DUPLICATE': txt = '该文件已经被选择了!' ;
        break;
        case  'Q_EXCEED_NUM_LIMIT': txt = '上传文件数量超过限制!' ;
        break;
        case  'F_EXCEED_SIZE': txt = '文件大小超过限制!';
        break;
        case  'Q_EXCEED_SIZE_LIMIT': txt = '所有文件总大小超过限制!';
        break;
        case 'Q_TYPE_DENIED': txt = '文件类型不正确或者是空文件!';
        break;
        case 'F_CANNOT_REPEAT': txt = '文件不能重复!';
        break;
        default: txt = '未知错误!';
        break;
      }
      alert(txt);
    });

    // 当所有文件上传结束时触发
    uploader.on('uploadFinished', function() {
      that._updateInfo('finished', uploader);
    });

    // 当开始上传流程时触发
    uploader.on('startUpload', function() {
      that._updateInfo('uploading', uploader);
    });

    // 当开始上传流程暂停时触发
    uploader.on('stopUpload', function() {
      that._updateInfo('paused', uploader);
    });

    // 不管成功或者失败，文件上传完成时触发。
    uploader.on('uploadComplete', function(file) {
      that._updateState('complete', file);
    });

    // 某个文件开始上传前触发，一个文件只会触发一次
    uploader.on('uploadStart', function(file) {
      that._updateState('start', file);
    });

    // 当文件上传出错时触发
    uploader.on('uploadError', function(file) {
      that._updateState('error', file);
    });

    // 当文件上传成功时触发
    uploader.on('uploadSuccess', function(file) {
      that._updateState('success', file);
    });
  };


  // 删除图片
  Uploader.prototype._removeFile = function($li, file, uploader) {
    var $element = this.$element;
    var that = this;

    uploader.removeFile(file);
    if ($li.siblings('li').length <= 0) {
      that.$actionBar.hide();
      that.$btnPick.show();
    }
    $li.remove();
  };

  // 更新文件统计信息
  Uploader.prototype._updateInfo = function(state, uploader) {
    var $info = this.$info;
    var that = this;

    switch(state) {
      case 'ready':
        $info.html('选中<b class="text-primary">' + that.fileCount + '</b>张图片，共<b class="text-primary">'+ WebUploader.formatSize(that.fileSize) + '</b>。');
        break;
      case 'finished':
        var stats = uploader.getStats();  //http://fex.baidu.com/webuploader/doc/index.html#WebUploader_Uploader_getStats
        $info.html('已成功上传<b class="text-primary">' + stats.successNum+ '</b>张图片。<b class="text-red">' + stats.uploadFailNum + '</b>张照片上传失败，<a class="retry">重新上传</a>失败图片');
        that.$btnUpload.text('开始上传').removeClass('btn-outline btn-primary').addClass('btn-green');
        that.$btnUpload.siblings('.btn-add').removeAttr('style');
        break;

      case 'uploading':
        that.$btnUpload.text('暂停上传').removeClass('btn-green').addClass('btn-outline btn-primary');
        that.$btnUpload.siblings('.btn-add').css('visibility', 'hidden');
        break;

      case 'paused':
        that.$btnUpload.text('继续上传');
        break;
    }

    // 暂停、开始转换
    this.$btnUpload.on('click', function() {
      if (state === 'paused') {
        uploader.upload();
      } else if (state === 'uploading') {
        uploader.stop();
      } 
    });

    // 重新上传失败的文件
    this.$info.on('click', '.retry', function() {
      var stats = uploader.getStats();
      if (stats.uploadFailNum > 0) {
        uploader.retry();
      }
    });
  };

  // 更新每个文件状态信息
  Uploader.prototype._updateState = function(state, file) {
    var $li = $('#'+file.id);
    var $progress = $li.find('.progress');
    var $badge = $li.find('.badge');

    switch(state) {
      case 'success':
        $badge.text('上传成功').removeClass('badge-red').addClass('badge-green').show();
        $li.children('.file-panel').addClass('disabled');
        break;
      case 'error':
        $badge.text('上传失败').addClass('badge-red').show();
        break;
      case 'complete':
        $progress.hide();
        break;
      case 'start': 
        $progress.show();
        break;
      case 'ready': 
        $progress.hide();
        $badge.hide();
        break;
    }
  }

  //上传组件参数，具体参照:http://fex.baidu.com/webuploader/doc/index.html
  Uploader.prototype._setOptions = function(selectorId){
    return {
      // 具体参照:http://fex.baidu.com/webuploader/doc/index.html
      pick: {
        id: selectorId,
        innerHTML: '添加图片', // {String}, 指定按钮文字，不指定时优先从指定的容器中看是否自带文字
        multiple: true, // {Boolean} 是否开起同时选择多个文件能力
      },

      auto: false,  // {Boolean} 默认值：false, 设置为 true 后，不需要手动调用上传，有文件选择即开始上传
      
      // {Obejct}, 指定接受哪些类型的文件
      accept: {
        title: 'Images',  // {String} 文字描述
        extensions: 'gif,jpg,jpeg,bmp,png,svg',  // {String} 允许的文件后缀，不带点，多个用逗号分割
        mimeTypes: 'image/*'  //{String} 多个用逗号分割
      },
      
      // {Object} 配置生成缩略图的选项
      thumb: {
        width: 120,  //缩略图宽度
        height: 120,  //缩略图高度
        quality: 70,  // 图片质量，只有type为`image/jpeg`的时候才有效
        allowMagnify: false, // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
        crop: true,  // 是否允许裁剪
        type: "",  // 为空的话则保留原有图片格式, 否则强制转换成指定的类型
      },

      fileVal: 'file',  // 设置文件上传域的name, 默认值：'file'

      dnd: '',  //{Selector}, default：undefined, 指定Drag And Drop拖拽的容器，如果不指定，则不启动
      paste: '',  //{Selector} default：undefined, 通过粘贴来添加截屏的图片。建议设置为document.body

      swf: '',  // {String}, SWF位置，用于flash上传

      sendAsBinary: false,  //{Boolean}, 是否已二进制的流的方式发送文件，这样整个上传内容php://input都为文件内容

      chunked: true,  //{Boolean}, 是否要分片处理大文件上传
      chunkSize: 512 * 1024, // {Int}, 分片大小
      
      method: 'POST', //{String}, 文件上传方式，POST或者GET
      server:'',  // {String}, 服务器上传地址

      disableGlobalDnd: true,  // {Boolean}, 是否禁掉整个页面的拖拽功能，如果不禁用，图片拖进来的时候会默认被浏览器打开
      
      fileNumLimit: 300,  // {int}, 验证文件总数量, 超出则不允许加入队列
      fileSizeLimit: 200 * 1024 * 1024,  // {int}, 验证文件总大小是否超出限制, 超出则不允许加入队列
      fileSingleSizeLimit: 50 * 1024 * 1024  //{int}, 验证单个文件大小是否超出限制, 超出则不允许加入队列
    }
  }

  // 定义插件
  // ========================
  function Plugin(option) {
    return this.each(function() {
      var $this = $(this);
      var data = $this.data('bn.uploader');
      var options = $.extend({}, $this.data(), typeof option == 'object' && option);  //optiopn 绑定data-{}

      if (!data) {
        $this.data('bn.uploader', (data = new Uploader(this, options)));
      }

      if (typeof option == 'string') {
        data[option]();
      }
    });
  }

  var old = $.fn.uploader;

  $.fn.uploader = Plugin;
  $.fn.uploader.Constructor = Uploader;


  // 冲突
  // ==================
  $.fn.uploader.noConflict = function () {
    $.fn.uploader = old;
    return this;
  };
});
