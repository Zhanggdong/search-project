$(function() {
  $.post(contextPath + '/search/hotWordsTop10', function(data) {
    var str = "";
    if (data) {
      for (var i = 0; i < data.length; i++) {
        str += '<li><a href="' + contextPath + '/search/search?keyWord=' + encodeURIComponent(data[i].keyWord) + '" >';
        str += data[i].keyWord;
        str += '</a></li>';
        if (i > 5)
          str += '<p/>';
      }
    } else {
      str = "搜索热词加载失败。。。";
    }
    $('#hotKeyWorsUL').append(str);
  })

  $('#biztype').change(function() {
    if ($(this).children('option:selected').val() == 1) {
      $("#fileType").removeAttr("disabled");
      $("#fileSource").removeAttr("disabled");

      var typeSelect = '<option value="all">不限</option>';
      $.getJSON(contextPath + '/search/getAppList', function(data) {
        if (data) {
          $.each(data, function(index) {
            typeSelect += '<option value="' + data[index][0] + '">' + data[index][1] + '</option>';
          });
          $('#fileType').html(typeSelect);
        }
      });

      var sourceSelect = '<option value="all">不限</option><option value="repository">来文</option><option value="workflow">其他资料</option>';
      $('#fileSource').html(sourceSelect);
    } else {
      $("#fileType").attr("disabled", "disabled");
      $("#fileSource").attr("disabled", "disabled");
    }
  });

  $('#searchBtn').click(function() {
    submitForm();
  });
});

// 回车事件
document.onkeydown = function(event) {
  e = event ? event : (window.event ? window.event : null);
  if (e.keyCode == 13) {
    submitForm();
  }
}

/**
 * 提交表单
 * 
 * @returns
 */
function submitForm() {
  var text = $('#searchText').val();
  if (text.replace(/[ ]/g, "") == "") {
    return;
  }
  location.href = contextPath + "/search/search?keyWord=" + encodeURIComponent($('#searchText').val());
}
