
define(function () {

  var Modal = function(element, options) {
    this.$element = $(element);
    this.options = $.extend({}, Modal.defaults, options);
    this.$target = $(this.$element.attr('data-target') || $(this.$element.attr('href')));
    this.$body = $(document.body);
    this.$overlay = null;
    this.isShown = false;

    this.init();
  };

  Modal.defaults = {
    overlay: true,
    esc: true,
    place: 'center',
    target: undefined
  };

  Modal.transitionDuration = 350;
  Modal.overlayTransitionDuration = 150;

  // 初始化
  Modal.prototype.init = function() {
    var $element = this.$element;

    // 计算位置
    var targetHeight = this.$target.height();
    if (this.options.place == 'aside') {
      this.$target.addClass('modal-aside');
    } else if (this.options.place == 'top') {
      this.$target.addClass('modal-top');
    } else if (this.options.place == 'bottom') {
      this.$target.addClass('modal-bottom');
    } else if (this.options.place == 'center') {
      this.$target.css({
        'top': '50%',
        'margin-top': -(targetHeight / 2)
      });
    } else if (this.options.place == 'full') {
      this.$target.addClass('modal-full');
    }
    
    $element.on('click', $.proxy(function(e) {
      if ($element.is('a')) {
        e.preventDefault();
      }

      if ($element.attr('data-dismiss') === 'modal') {
        var that = this;
        setTimeout(function(){
          that._show();
        }, Modal.transitionDuration + Modal.overlayTransitionDuration + 50);
        return;
      }
      this._show();
    }, this));
  };

  // 显示模态框
  Modal.prototype._show = function() {
    if (this.options.overlay !== false) {
      this._overlay();
    }

    this.$body.addClass('modal-open');
    this.$target.addClass('active');

     
    this.isShown = true;
    
    this._escape();

    this.$target.on('click.dismiss.modal', '[data-dismiss="modal"]', $.proxy(this._hide, this));
  };

  // 显示遮罩
  Modal.prototype._overlay = function() {

    this.$overlay = $(document.createElement('div')).addClass('modal-overlay fade').appendTo(this.$body);
    var $overlay = this.$overlay;
    var options = this.options;

    setTimeout(function(){
      $overlay.addClass('active');
    }, 1);

    if (options.overlay !== 'static') {
      $overlay.one('click', $.proxy(this._hide, this));
    }
  };

  // 隐藏遮罩
  Modal.prototype._removeOverlay = function() {
    if (this.$overlay === null) {
      return;
    }
    setTimeout(function(){
      $('.modal-overlay').removeClass('active');

      setTimeout(function(){
        $('.modal-overlay').remove();
      }, Modal.overlayTransitionDuration);
    }, Modal.transitionDuration);

    this.$overlay = null;
  };

  // 隐藏模态框
  Modal.prototype._hide = function() {
    if (!this.isShown) {
      return;
    }
    this.$target.removeClass('active');

    this._removeOverlay();
    this.$body.removeClass('modal-open');
    this.isShown = false;
    this._escape();
  };

  // ESC键隐藏
  Modal.prototype._escape = function() {
    var $element = this.$element;

    if (this.isShown && this.options.esc) {
      $(document).on('keydown.dismiss.modal', $.proxy(function (e) {
        if (e.which == 27) {
          this._hide();
        }
      }, this));
    } else if (!this.isShown) {
      $(document).off('keydown.dismiss.modal');
    }
  };

  $.fn.modal = function () {
    var args = Array.prototype.slice.call(arguments);
    return jQuery(this).each( function() {
      if (!args[0] || typeof args[0] === 'object') {
        new Modal(this, args[0] || {});
      } else if (typeof args[0] === 'string') {
        Modal.prototype[args[0]].apply(new Modal(this), args.slice(1));
      }
    });
  };

  $('[data-action="modal"]').modal();

});
