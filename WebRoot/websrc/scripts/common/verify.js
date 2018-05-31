define(function() {
  var verifyModule = {
    /**
     * [检查输入字符长度]
     * @param  {[Node]}   id  [id名称]
     * @param  {[Number]} min [最小位数]
     * @param  {[Number]} max [最大位数]
     * @return {[Boolean]}    [0: 为空, true: 通过, false: 不通过]
     */
    checkLength: function(id, min, max) {
      var len = $('#'+id).val().length;
      if (len === 0) {
        return 0;
      } else if (len > max || len < min) {
        return false;
      }
      return true;
    },

    /**
     * [checkConfirmPwd description]
     * @param  {[Object]} idName1 [description]
     * @param  {[Object]} idName2 [description]
     * @return {[String]}         [description]
     */
    checkConfirmPwd: function(idName1, idName2) {
      var pwd1 = $('#'+idName1).val();
      var pwd2 = $('#'+idName2).val();
      pwd1 === pwd2 ? true : false;
    },
  };

  return verifyModule;
})

