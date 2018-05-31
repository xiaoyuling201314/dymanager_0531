define(function () {
  return {
    selectAll: function(allId, itemClass) {
      var btn = document.getElementById(allId);
      var items = document.querySelectorAll('.'+itemClass);
      btn.onclick = function() {
        for ( var i = 0; i < items.length; i+=1) {  
          items[i].checked = btn.checked;
        }
      }
      $('.'+itemClass).on('click', function(e) {
        e.stopPropagation();
        var currentLen = $(document).find('input:checked.'+itemClass).length;
        if (currentLen === items.length) {
          btn.checked = true;
        } else {
          btn.checked = false;
        }
      });
    }
  };
});