// 判断此当前用户是否可查看此文件
function getPermission(fileGUID) {
    var allowed;
    $.ajax({
        type : 'GET',
        url : contextPath + '/file/getPermission/' + fileGUID + "?timestamp=" + new Date().getTime(),
        dataType : 'JSON',
        async : false,
        success : function(data) {
            allowed = data;
        },
        error : function() {
            alert('服务器出现异常，请稍后再试！');
        }
    });
    return allowed;
}

// 文件查看
function viewdocument(fileGUID) {
   /* var allowed = getPermission(fileGUID);
    if (!allowed) {
        alert("抱歉，您没有权限查看此文件！");
        return;
    }
    var url = contextPath + '/file/viewFile?fileGUID=' + fileGUID + '&isFile=' + isFile + '&bizType=' + bizType;
   */ 
	var url = serverip+'/riseoffice/default/openInstanceAction.do?folder=done&searchType=searchType&instanceGUID='+fileGUID;
	window.open(url);
}


// 打开原件
function openform(fileGUID, bizGUID, bizType) {
    var allowed = getPermission(fileGUID);
    if (!allowed) {
        alert("抱歉，您没有权限查看此文件！");
        return;
    }
    var url = contextPath + '/file/viewFile?fileGUID=' + fileGUID + '&bizGUID=' + bizGUID + '&bizType=' + bizType;
    window.open(url);
}
