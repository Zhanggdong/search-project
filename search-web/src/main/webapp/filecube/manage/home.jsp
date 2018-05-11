<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9"></meta>
<title>文件管理</title>
<%@include file="../../commons/commons.jsp"%>
<link href="${ctx}/commons/javascript/qui-v3.3/system/modern_accordion/skin/style.css" rel="stylesheet" type="text/css" id="skin" skinPath="system/modern_accordion/skin/" />
<script type="text/javascript" src="${ctx}/commons/javascript/qui-v3.3/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/qui-v3.3/libs/js/popup/dialog.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/qui-v3.3/libs/js/popup/messager.js"></script>
<script>
  function bookmarksite(title, url) {
    if (window.sidebar) // firefox
      window.sidebar.addPanel(title, url, "");
    else if (window.opera && window.print) { // opera
      var elem = document.createElement('a');
      elem.setAttribute('href', url);
      elem.setAttribute('title', title);
      elem.setAttribute('rel', 'sidebar');
      elem.click();
    } else if (document.all)// ie 
      window.external.AddFavorite(url, title);
  }
  function windowClose() {
    window.opener = null;
    window.open('', '_self'); //IE7必需的.
    window.close();
  }
  function backHome() {
    document.getElementById("frmleft").contentWindow.homeHandler();
  }
  $(function() {

  })

  function showPhoneBar() {
    $("#contentPhone").css("top", "30px");
  }
  function hidePhoneBar() {
    $("#contentPhone").css("top", "-200px");
  }
  var phoneFlag = false;
  function initSoft() {
    InitSoftPhone();
    phoneFlag = true;
  }
</script>
<style>
a {
	behavior:
		url(${ctx}/commons/javascript/qui-v3.3/libs/js/method/focus.htc)
}
</style>
</head>
<body>
  <!--头部与导航start-->
  <div id="mainFrame">
    <div id="hbox">
      <div id="bs_bannercenter">
        <div id="bs_bannerright">
          <div id="bs_bannerleft">
            <div class="float_right padding_top2 padding_right10">
              <table width="300" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="30" class="float_right padding_top10 padding_right5"><a href="../index.jsp" target="_parent" onclick="backHome()"><span
                      class="icon_home hand">首页</span></a>&nbsp;<span class="icon_exit hand"
                      onclick="top.Dialog.confirm('确定要退出系统吗？',function(){windowClose()});">退出</span></td>
                </tr>
                <tr>
                  <td height="30" class="float_right padding_top10 padding_right10">当前用户：${sessionScope.riseUser.userName }|所属部门：${sessionScope.riseUser.bureauName }</td>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!--头部与导航end-->
  <div id="mainLayout">
    <div position="left">
      <div id="lbox">
        <div id="lbox_topcenter">
          <div id="lbox_topleft">
            <div id="lbox_topright">
              <div class="lbox_title">操作菜单</div>
            </div>
          </div>
        </div>
        <div id="lbox_middlecenter">
          <div id="lbox_middleleft">
            <div id="lbox_middleright">
              <div id="bs_left" style="width: 100%;">
                <iframe height="100%" width="100%" frameBorder=0 id=frmleft name=frmleft src="left.jsp" allowTransparency="true"></iframe>
              </div>
              <!--更改左侧栏的宽度需要修改id="bs_left"的样式-->
            </div>
          </div>
        </div>
        <div id="lbox_bottomcenter">
          <div id="lbox_bottomleft">
            <div id="lbox_bottomright">
              <div class="lbox_foot"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div position="center">
      <div class="ali01 ver01" width="100%">
        <div id="rbox">
          <div id="rbox_topcenter">
            <div id="rbox_topleft">
              <div id="rbox_topright"></div>
            </div>
          </div>
          <div id="rbox_middlecenter">
            <div id="rbox_middleleft">
              <div id="rbox_middleright">
                <div id="bs_right">
                  <c:choose>
                    <c:when test="${empty param.url}">
                      <iframe height="100%" width="100%" frameBorder=0 id=frmright name=frmright allowTransparency="true"></iframe>
                    </c:when>
                    <c:otherwise>
                      <iframe height="100%" width="100%" frameBorder=0 id=frmright name=frmright allowTransparency="true"></iframe>
                    </c:otherwise>
                  </c:choose>
                </div>
              </div>
            </div>
          </div>
          <div id="rbox_bottomcenter">
            <div id="rbox_bottomleft">
              <div id="rbox_bottomright"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!--浏览器resize事件修正start-->
  <div id="resizeFix"></div>
  <!--浏览器resize事件修正end-->

  <!--窗口任务栏区域start-->
  <div id="dialogTask" class="dialogTaskBg" style="display: none;"></div>
  <!--窗口任务栏区域end-->

  <!--载进度条start-->
  <div class="progressBg" id="progress" style="display: none;">
    <div class="progressBar"></div>
  </div>
  <!--载进度条end-->
</body>
</html>
