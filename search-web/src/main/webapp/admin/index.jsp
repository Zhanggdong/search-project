<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	pageContext.setAttribute("date", date);
	pageContext.setAttribute("ctx", path);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta name="renderer" content="webkit"/>
	<title>配置首页</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${ctx}/commons/javascript/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<link rel="stylesheet" href="${ctx}/commons/javascript/jq-pagination/pagination.css" type="text/css">
	<script type="text/javascript" src="${ctx}/commons/javascript/jquery/jquery-1.8.2.js"></script>
	<script type="text/javascript" src="${ctx}/commons/javascript/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
	<script src="${ctx}/commons/javascript/jq-pagination/jquery.pagination.js"></script>
	<script type="application/javascript" src="js/index.js"></script>
	<style type="text/css">
		html {
			font-size: 12px;
			overflow: auto;
		}
		body,head,html{
			height:100%;
			width:100%;
		}
		#left{
			width:200px;
			background:white;
			float:left;
		}
		#content{
			background:white;
			margin-left:210px;
			background:white;
		}
		#pagination{
			background: #e3e3e3;
			height: 40px;
			line-height: 40px;
			width: 990px;
			margin-top: 20px;
			text-align: right;
		}
		#pagination .active{
			color: #c41929;
			border: 1px solid transparent;
			background: #e3e3e3;
			font-size: 14px;
			padding: 3px 4px;
			margin-right: 10px;
		}
		#pagination a,#pagination span{
			border: 1px solid #cdcdcd;
			background: #fff;
			font-size: 14px;
			padding: 3px 4px;
			color: #000;
			margin-right: 10px;
		}
		#pagination a.jump-btn{
			margin-right: 20px;
			margin-left: 20px;
		}
		#pagination input{
			height: 18px;
			line-height:18px;
			width: 30px;
		}
	</style>
</head>
<body>
	<div id="left" style="height:100%">
		<div>
			<ul id="treeDemo" class="ztree"></ul>
		</div>
	</div>
	<div id="content" style="width:100%;height:100%">
		<table>
			<thead><tr><th width="300">资源ID</th><th width="200">资源名称</th><th width="200">资源类型</th></tr></thead>
			<tbody id="body"></tbody>
		</table>
		<div id="pagination" class="pagelist"></div>
	</div>
</body>
</html>