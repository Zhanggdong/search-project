<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	pageContext.setAttribute("ctx", path);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>资料详情</title>
<script type="text/javascript" src="${ctx }/commons/javascript/qui-v3.3/libs/js/jquery.js"></script>
<script type="text/javascript" src="${ctx }/commons/javascript/qui-v3.3/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${ctx }/commons/javascript/qui-v3.3/libs/js/framework.js"></script>
<link href="${ctx }/commons/javascript/qui-v3.3/libs/css/import_basic.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx }/commons/javascript/qui-v3.3/" />
<link rel="stylesheet" type="text/css" id="customSkin" />
<link type="text/css" href="${ctx}/commons/javascript/qui-v3.3/system/modern_accordion/skin/style.css" rel="stylesheet" id="skin" skinPath="${ctx }/commons/javascript/qui-v3.3/system/modern_accordion/skin/" />
<style type="text/css">
.box_tool_min a {
	float: right;
}

.topPadding {
	padding-top: 140px;
}

.topTools {
	left: 0px;
	width: 100%;
	position: fixed;
	z-index: 299;
}
</style>
<script type="text/javascript">
function windowClose() {
 window.location.href='${sessionScope.outUrl}'
}
</script>
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
                <td width="50%" style="padding-left: 10px;">资料详情</td>
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
    <div class="box1" style="width: 720px">
      <table width="100%" class="tableStyle" formMode="line">
        <tr>
          <td>标&nbsp;&nbsp;&nbsp;题：</td>
          <td width="620px"><input type="text" value="${requestScope.archData.archCaption}" style="width: 90%" readonly="readonly" ="readonly="readonly" " /></td>
        </tr>
        <tr>
          <td width="15%">文&nbsp;&nbsp;&nbsp;号：</td>
          <td><input type="text" value="${requestScope.archData.archCaption}" style="width: 90%" readonly="readonly" /></td>
        </tr>
        <tr>
          <td>来&nbsp;&nbsp;&nbsp;源：</td>
          <td><input type="text" value="${requestScope.archData.archCaption}" style="width: 90%" readonly="readonly" /></td>
        </tr>
        <tr>
          <td>成文日期:</td>
          <td><input type="text" value="<fmt:formatDate value="${requestScope.archData.writenTime}" pattern="yyyy-MM-dd"/>" style="width: 90%" readonly="readonly" /></td>
        </tr>
        <tr>
          <td>上传人&nbsp;：</td>
          <td><input type="text" value="${requestScope.archData.archAuthor}" style="width: 90%" readonly="readonly" /></td>
        </tr>
        <tr>
          <td>所属部门：</td>
          <td><input type="text" value="${requestScope.archData.authorsShortDN}" style="width: 90%" readonly="readonly" /></td>
        </tr>
        <tr>
          <td>关键字&nbsp;：</td>
          <td><input type="text" value="${requestScope.archData.archKeywords}" style="width: 90%" readonly="readonly" /></td>
        </tr>
        <tr>
          <td>摘&nbsp;&nbsp;&nbsp;要：</td>
          <td><textarea name="textarea" cols="62" rows="5" style="width: 90%" readonly="readonly">${requestScope.archData.archAbstract}</textarea></td>
        </tr>
        <tr>
          <td>所属分类：</td>
          <td><input type="text" value="${requestScope.archData.archCaption}" style="width: 90%" readonly="readonly" /></td>
        </tr>
        <tr>
          <td>上传日期：</td>
          <td><input type="text" value="${requestScope.archData.archCreateDate}" style="width: 90%" readonly="readonly" /></td>
        </tr>
        <tr>
          <td>是否发布：</td>
          <td><select name="issaunce" disabled="disabled" style="width: 50%;">
              <option value="1">发布</option>
              <option value="0">取消</option>
          </select></td>
        </tr>
        <tr>
          <td>附&nbsp;&nbsp;&nbsp;件：</td>
          <td><table class="tableStyle">
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
                      <td style="text-align:center"><fmt:formatDate value="${file.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
