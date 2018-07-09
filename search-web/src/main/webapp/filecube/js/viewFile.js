$(function() {
  $('.buttonSpan').button();
  var _favoritedUid = '';

  // 收藏按钮点击事件
  $('#notFavoritedButtonSpan').click(function() {
    $.get(contextPath + '/file/addFileFavorite/' + viewFile_FileUid, function(data) {
      if (data) {
        $('#notFavoritedButtonSpan').css('display', 'none');
        $('#favoritedButtonSpan').css('display', '');
        alert('收藏成功！');
      } else {
        alert('收藏失败！');
      }
    });
  });

  // 取消收藏按钮
  $('#favoritedButtonSpan').click(function() {
    $.get(contextPath + '/file/removeFileFavorite/' + viewFile_FileUid, function(data) {
      if (data) {
        $('#notFavoritedButtonSpan').css('display', '');
        $('#favoritedButtonSpan').css('display', 'none');
        alert('取消收藏成功！');
      } else {
        alert('取消收藏失败！');
      }
    });
  });

  // 下载按钮点击事件
  $('#ViewFileDownload').click(function() {
    var fileInfo = global_ajax('file_getFileItemType.action', {
      fileUid : viewFile_FileUid
    }, false);
    if (_file_persional_ItemType != fileInfo.itemType) {
      var allow = permission_getPermissionByFileUid(viewFile_FileUid, viewFile_operationKey_download);
      if (!allow) {
        alert("对不起您没有下载此文件的权限！");
        return;
      }
    }
    location.href = 'file_download.action?acFileInfo.fileUid=' + viewFile_FileUid;
  });

  // 共享点击按钮
  $('#ViewFileShare').click(function() {
    var fileInfo = global_ajax('file_getFileItemType.action', {
      fileUid : viewFile_FileUid
    }, false);
    if (_file_persional_ItemType != fileInfo.itemType) {
      var allow = permission_getPermissionByFileUid(viewFile_FileUid, viewFile_operationKey_share);
      if (!allow) {
        alert("对不起您没有下载此文件的权限！");
        return;
      }
    }
    $('#viewerPlaceHolder').toggle();
    $('#shareDialogParent').html('<div id="shareDialog" style="padding: 0px 0px;position: relative;"></div>');
    $('#shareDialog').load('share_preShare.action?fileShare.fileInfo.fileUid=' + viewFile_FileUid);
    $("#shareDialog").dialog({
      resizable : false,

      width : 300,
      height : 455,
      modal : true,
      buttons : {
        "确定" : function() {
          orgTree_doShare();
          $(this).dialog("close");
          $('#shareDialog').remove();
          $('#shareDialogParent').html('');
        },
        '取消' : function() {
          $(this).dialog("close");
          $('#shareDialog').remove();
          $('#shareDialogParent').html('');
        }
      },
      beforeClose : function() {
        $('#shareDialog').remove();
        $('#shareDialogParent').html('');
        $('#viewerPlaceHolder').toggle();
      }
    });

  });

  // 获取关联打开文件记录
  /*$.post('/query/getRelationByOpenHistory', {
    "fileGUID" : viewFile_FileUid
  }, function(data) {
    var str = '';
    for (var i = 0; i < data.length; i++) {
      str += '<span title="' + data[i].fileName + '" onclick=preViewDocument("' + data[i].fileUid + '") >';
      str += '<img src="images/file/small/files/' + data[i].fileExtension + '.gif" style="vertical-align: middle;margin-right:4px;">'
      str += '<font color="blue" style="cursor: pointer;" >' + data[i].fileShortName + '</font>';
      str += '</span>';
      str += '<p style="margin-bottom: 10px;margin-top: 10px;"></p>';
    }
    $('#relationOpenHistoryDiv').html(str);
  });*/

  // 获取关联收藏文件记录
  /*$.post('/query/getRelationByFavorite', {
    "fileGUID" : viewFile_FileUid
  }, function(data) {
    var str = '';
    for (var i = 0; i < data.length; i++) {
      str += '<span title="' + data[i].fileName + '" onclick=preViewDocument("' + data[i].fileUid + '") >';
      str += '<img src="images/file/small/files/' + data[i].fileExtension + '.gif" style="vertical-align: middle;margin-right:4px;">'
      str += '<font color="blue" style="cursor: pointer;" >' + data[i].fileShortName + '</font>';
      str += '</span>';
      str += '<p style="margin-bottom: 10px;margin-top: 10px;"></p>';
    }
    $('#relationFavoriteDiv').html(str);
  });*/
  
});

var winWidth = 0;
var winHeight = 0;
function findDimensions() {
  if (window.innerWidth)
    winWidth = window.innerWidth;
  else if ((document.body) && (document.body.clientWidth))
    winWidth = document.body.clientWidth;
  if (window.innerHeight)
    winHeight = window.innerHeight;
  else if ((document.body) && (document.body.clientHeight))
    winHeight = document.body.clientHeight;
  if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth) {
    winHeight = document.documentElement.clientHeight;
    winWidth = document.documentElement.clientWidth;
  }
}
$(document).ready(function() {
  findDimensions();
  $('#viewerPlaceHolder').attr('style', "height:" + (winHeight - 60) + "px");
  $('#relevanceFileIframe').attr('height', (winHeight - 50));
  $('#commentaryIframe').attr('height', (winHeight - 50) + "px");
  $('#info').css('display', '');
  $('#viewFileRight').attr('style', " font-family:Verdana;font-size:9pt;border: 1px solid #F7F5F5;height:" + (winHeight - 60) + "px");
  $('#relation').accordion();
});