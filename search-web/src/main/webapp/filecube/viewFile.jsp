<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	pageContext.setAttribute("ctx", path);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>查看文件</title>
<script type="text/javascript" src="../commons/javascript/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="../commons/javascript/jquery/ui/jquery-ui-1.9.1.custom.min.js"></script>
<link rel="stylesheet" type="text/css" href="../commons/javascript/jquery/ui/css/flick/jquery-ui-1.9.1.custom.min.css" />
<link rel="stylesheet" type="text/css" href="../filecube/css/listDataTable.css" />
<script type="text/javascript" src="../filecube/js/viewFile.js"></script>
<script type="text/javascript">
  var contextPath = '${ctx}';
  var viewFile_FileUid = '${param.fileGUID}';
  var isFavorited = ${requestScope.isFavorited};
  $(function() {
    $("#info").tabs({
      active : 0
    });
    if (isFavorited) {
      $('#notFavoritedButtonSpan').css('display', 'none');
      $('#favoritedButtonSpan').css('display', '');
    }
  });
</script>
<style type="text/css">
html {
	font-size: 12px;
	overflow: auto;
}

a:link {
	color: blue;
	text-decoration: none;
}
</style>
</head>
<body style="margin: 0px 0px;">
   <div id="info">
      <ul>
         <li><a href="#tabs-1">文件</a></li>
         <li><a href="#tabs-2">评论</a></li>
      </ul>
      <div id="tabs-1" style="padding: 0px 0px; min-height: 660px">
         <div id="viewFile_FileDiv" style="width: 100%;">
            <table width="100%" cellpadding="0" cellspacing="0" id="viewFileTable">
               <tr>
                  <td style="vertical-align: top; padding-top: 6px;">
                     <div id="viewerPlaceHolder">
                        <iframe height="660px" width="100%" frameborder="0" style="margin: 0px 0px;" id="relevanceFileIframe" src="../file/view?fileGUID=${requestScope.fileInfo.GUID}"></iframe>
                     </div>
                  </td>
                  <td width="280" rowspan="2" style="padding-left: 4px; padding-top: 4px;">
                     <div style="font-family: Verdana; font-size: 9pt; border: 1px solid #F7F5F5;" id="viewFileRight">
                        <div style="border-bottom: 1px solid #F7F5F5; background: none repeat scroll 0 0 #F5F5F5; margin-left: 2px; margin-right: 2px; padding-left: 5px; padding-right: 5px; height: 280px;">
                           <h1 style="margin-left: 10px; margin-top: 5px; padding-top: 5px; color: #333333; font-family: '微软雅黑', '黑体', arial; font-size: 16px;">${requestScope.fileInfo.title}</h1>
                           <span style="color: #999999;">已有：</span> <span style="color: #F7A600; font-size: 24px; height: 13px; font-style: italic; font-weight: bold;" id="userCountSpan">${requestScope.commentCount == null ? 0 : requestScope.commentCount}</span> 人评论
                           <p style="margin-bottom: 2px; margin-top: 2px;"></p>
                           <span style="color: #999999;">浏览：</span> <span style="color: #999999;"> <span id="viewCount" style="color: #F7A600; font-size: 24px; font-style: italic; font-weight: bold;">${requestScope.fileInfo.openTimes == null ? 0 : requestScope.fileInfo.openTimes}</span> 次
                           </span> <span style="color: #999999;">下载：</span> <span id="downCount" style="color: #F7A600; font-size: 24px; font-style: italic; font-weight: bold;">${requestScope.fileInfo.downloadTimes == null ? 0 : requestScope.fileInfo.downloadTimes}</span> 次
                           <p style="margin-bottom: 10px; margin-top: 10px;"></p>
                           <span style="color: #999999;">所属部门：</span> <a style="color: blue;">${requestScope.fileInfo.bureauName}</a>
                           <p style="margin-bottom: 10px; margin-top: 10px;"></p>
                           <span style="color: #999999;">创建者：</span> <a style="color: blue;">${requestScope.fileInfo.creatorName}</a>
                           <p style="margin-bottom: 10px; margin-top: 10px;"></p>
                           <span style="color: #999999;">创建时间：<fmt:formatDate value="${requestScope.fileInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                           </span>
                           <p style="margin-bottom: 10px; margin-top: 10px;"></p>
                           <span style="color: #999999;">格式：</span>
                           <c:choose>
                              <c:when test="${requestScope.fileInfo.extension != null}">
                                 <b style="margin-top: 5px;"><img src="../filecube/images/file/small/files/${requestScope.fileInfo.extension}.gif" style="vertical-align: middle;" /></b>
                                 <span style="margin-left: 5px !important; color: #333333;">${requestScope.fileInfo.extension}</span>
                              </c:when>
                              <c:otherwise>
                                 <span style="margin-left: 5px !important; color: #333333;">表单数据</span>
                              </c:otherwise>
                           </c:choose>
                           <p style="margin-bottom: 10px; margin-top: 10px;"></p>
                           <span class="buttonSpan" id="favoritedButtonSpan" style="display: none" title="取消收藏"> <img src="../commons/javascript/grade/img/star-on.png" style="vertical-align: middle; margin-right: 6px;" />取消收藏 </span>
                           <span class="buttonSpan" id="notFavoritedButtonSpan" title="收藏此文档"> <img src="../commons/javascript/grade/img/star-off.png" style="vertical-align: middle; margin-right: 6px;" />收藏 </span>
                           <!-- <span class="buttonSpan" id="ViewFileDownload" title="下载此文档"> <img src="../filecube/images/button_operation/download.gif" style="vertical-align: middle; margin-right: 6px;" />下载 </span>
                           <span class="buttonSpan" id="ViewFileShare" title="共享此文档"> <img src="../filecube/images/icons/share_16.png" style="vertical-align: middle; margin-right: 6px;" />共享 </span> -->
                        </div>
                        <!-- 关联信息 -->
                        <!-- <div id="relation">
                           <h3>打开此文件的人也打开了...</h3>
                           <div style="background: none repeat scroll 0 0 #F5F5F5;" id="relationOpenHistoryDiv">
                              <div class="loading-indicator" style="height: 280px;">加载中...</div>
                           </div>
                           <h3>收藏此文件的人也收藏了...</h3>
                           <div style="background: none repeat scroll 0 0 #F5F5F5;" id="relationFavoriteDiv">
                              <div class="loading-indicator">加载中...</div>
                           </div>
                        </div> -->
                     </div>
                  </td>
               </tr>
            </table>
         </div>
      </div>
      <div id="tabs-2" style="padding: 0px 0px;">
         <iframe height="650px" width="100%" frameborder="0" style="margin: 0px 0px;" id="commentaryIframe" src="../comment/view?fileGUID=${requestScope.fileInfo.GUID}"></iframe>
      </div>
   </div>
</body>
</html>