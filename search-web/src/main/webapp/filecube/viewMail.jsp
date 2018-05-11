<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
  String path = request.getContextPath();
  pageContext.setAttribute("ctx", path);
%>
<head>
<title>邮件详情</title>
<script type="text/javascript" src="${ctx }/commons/javascript/qui-v3.3/libs/js/jquery.js"></script>
<script type="text/javascript" src="${ctx }/commons/javascript/qui-v3.3/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${ctx }/commons/javascript/qui-v3.3/libs/js/framework.js"></script>
<link href="${ctx }/commons/javascript/qui-v3.3/libs/css/import_basic.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx }/commons/javascript/qui-v3.3/" />
<link rel="stylesheet" type="text/css" id="customSkin" />
<link type="text/css" href="${ctx}/commons/javascript/qui-v3.3/system/modern_accordion/skin/style.css" rel="stylesheet" id="skin" skinPath="${ctx}/commons/javascript/qui-v3.3/system/modern_accordion/skin/" />
<style type="text/css">
.box_tool_min a {
	float: right;
}

.topPadding {
	padding-top: 130px;
}

.topTools {
	left: 0px;
	width: 100%;
	position: fixed;
	z-index: 299;
}
</style>
</head>
<body>
  <div id="mainFrame" class="topTools">
    <div id="hbox">
      <div id="bs_bannercenter">
        <div id="bs_bannerright">
          <div id="bs_bannerleft">
            <div class="float_right padding_top2 padding_right10">
              <table width="300" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="30" class="float_right padding_top10 padding_right5"><a href="http://10.169.2.3/riseway/home.jsp" target="_parent" onclick='backHome()'><span
                      class="icon_home hand">首页</span></a>&nbsp;<span class="icon_exit hand" onclick='top.Dialog.confirm("确定要退出系统吗？",function(){windowClose()});'>退出</span></td>
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
    <div class="box_tool_min padding_top2 padding_bottom2 padding_right5">
      <div class="center">
        <div class="left">
          <div class="right">
            <table width="100%">
              <tr>
                <td width="50%" style="padding-left: 10px;">邮件详情</td>
                <td><div class="clear"></div> <a href="javascript:window.close();"><span class="icon_delete">关闭</span></a></td>
              </tr>
            </table>
          </div>
        </div>
      </div>
      <div class="clear"></div>
    </div>
  </div>
  <div class="topPadding"></div>
  <center>
    <div class="box1" style="width: 700px">
      <table width="100%" class="tableStyle" formMode="line">
        <tr id="jieshouren">
          <td width="15%">接收人：</td>
          <td width="620px"><input id="sender" name="sender" readonly="readonly" style="width: 90%" value="${requestScope.innerMail.receiver}" /></td>
        </tr>
        <tr>
          <td>发件人：</td>
          <td><input id="sender" name="sender" readonly="readonly" style="width: 90%" value="${requestScope.innerMail.sender}" /></td>
        </tr>
        <tr>
          <td>标题：</td>
          <td><input id="title" name="title" readonly="readonly" style="width: 90%" value="${requestScope.innerMail.title}" /></td>
        </tr>
        <tr>
          <td>内容：</td>
          <td><textarea rows="5" cols="70" id="content" name="content" readonly="readonly" style="width: 90%; height: 100px;">${requestScope.innerMail.content }</textarea></td>
        </tr>
        <tr>
          <td width="15%">附件:</td>
          <td colspan="2" width="85%"><table class="tableStyle">
              <tr>
                <th width="15">序号</th>
                <th width="100">标题</th>
                <th width="50">上传时间</th>
                <th width="20">操作</th>
              </tr>
              <c:choose>
                <c:when test="${not empty requestScope.fileList }">
                  <c:forEach items="${requestScope.fileList }" var="file" varStatus="stat">
                    <tr>
                      <td style="text-align: center">${stat.index+1}</td>
                      <td>${file.fileName}</td>
                      <td><fmt:formatDate value="${file.createDate}"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
                      <td><a href="${ctx}/file/downloadFile?fileGUID=${file.fileGUID}">下载</a></td>
                    </tr>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <tr>
                    <td colspan="5">没有数据！</td>
                  </tr>
                </c:otherwise>
              </c:choose>
            </table></td>
        </tr>
      </table>
    </div>
  </center>
</body>
</html>