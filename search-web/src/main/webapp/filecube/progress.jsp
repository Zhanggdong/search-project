<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="net.risesoft.filecube.util.Constant"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
  pageContext.setAttribute("ctx", path);
  pageContext.setAttribute("serverip",Constant.serverip);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>数据读取</title>
<script type="text/javascript" src="${ctx}/commons/javascript/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/jquery/ui/jquery-ui-1.9.1.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/suggest/kissy-min.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/suggest/suggest-min.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/jquery-plugin/jquery-dateFormat.min.js"></script>
<script type="text/javascript" src="${ctx}/filecube/js/permission.js"></script>
<link rel="stylesheet" href="${ctx}/commons/bootstrap/dist/css/bootstrap.min.css"/>
<script src="${ctx}/commons/bootstrap/dist/js/bootstrap.min.js"></script>
</head>
<body>


</body>
</html>