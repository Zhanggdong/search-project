<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="net.risesoft.filecube.util.FilecubeUtil"%>
<%@page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="net.risesoft.filecube.util.Constant"%>
<%@ page import="net.risesoft.common.consts.RiseUser"%>
<%@ page import="net.risesoft.common.consts.SessionConst"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	pageContext.setAttribute("ctx", path);
	int pageStart = StringUtils.isNotBlank(request.getParameter("pageStart")) ? Integer.parseInt(request.getParameter("pageStart")) : 0;
	int rows = StringUtils.isNotBlank(request.getParameter("rows")) ? Integer.parseInt(request.getParameter("rows")) : 10;
	pageContext.setAttribute("serverip",Constant.serverip);
	pageContext.setAttribute("oaServerUrl", FilecubeUtil.oaServerUrl);
	pageContext.setAttribute("oaServeroldUrl", FilecubeUtil.oaServeroldUrl);
	pageContext.setAttribute("fileServerUrl",FilecubeUtil.fileServerUrl);
	pageContext.setAttribute("oaHistoryServerUrl",FilecubeUtil.oaHistoryServerUrl); 
	RiseUser user = (RiseUser) request.getSession().getAttribute(SessionConst.RISEUSER);
	pageContext.setAttribute("userLoginName",user.getLoginName()); 
	pageContext.setAttribute("instanceGUID",request.getParameter("instanceGUID")); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>查看历史公文</title>
<meta http-equiv="x-ua-compatible" content="IE=5" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/commons/javascript/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	var serverip = "${serverip}";
	var contextPath = "${ctx}";
	var oaServerUrl = "${oaServerUrl}";
	var oaServeroldUrl = "${oaServeroldUrl}";
	var fileServerUrl = "${fileServerUrl}";
	var oaHistoryServerUrl = "${oaHistoryServerUrl}";
	var userLoginName = "${userLoginName}";
	var instanceGUID = "${instanceGUID}";
  $(function(){
	  //这里用两个iframe是为了让公文能够正常打开：需要添加功能列表的相关控件
	  $("#historyofficeList").attr("src",oaHistoryServerUrl+"?loginName="+userLoginName+"&pageType=office");
	  //openHistoryofficeList();	  
	  $("#historyoffice").attr("src",oaHistoryServerUrl+"/riseoffice/default/openInstanceAction.do?lfolder=todo&instanceGUID="+instanceGUID);  
  });
  
  function openHistoryofficeList(){  
	  location.href =  contextPath+"/history/getGetHistoryOfficeList?instanceGUID="+encodeURIComponent(instanceGUID);
	  $("#historyofficeList").attr("src",oaHistoryServerUrl+"?loginName="+userLoginName+"&pageType=office");
	  //parent.location.reload();
	  historyofficeList.location.reload(true); 
	  openHistoryoffice();
  }
  function openHistoryoffice(){
	  $("#historyofficeList").attr("src",oaHistoryServerUrl+"?loginName="+userLoginName+"&pageType=office");
  }
  
</script>
</head>
<body>
	<div style="height:100%;width:100%">
		<iframe id="historyofficeList" style="height:100%;width:100%;display:none"></iframe>
		<iframe id="historyoffice" style="height:100%;width:100%;display:blok"></iframe>
	</div>
   
</body>
</html>
