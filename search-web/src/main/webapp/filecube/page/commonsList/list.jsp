<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>搜索列表</title>

<link rel="stylesheet" href="<%=path %>/css/list.css">
<style type="text/css">
html{		
	overflow: auto;
}
${(keyWords == '*') ? 'em' : 'haha'}{
	font-family: arial;
	color:#000000;
}
</style>
<link rel="stylesheet" href="<%=path %>/commons/pagination/pagination.css">
<script type="text/javascript" src="<%=path %>/commons/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/commons/pagination/pagination.js"></script>
<script type="text/javascript" src="<%=path %>/js/file/permission.js"></script>
<script type="text/javascript" src="<%=path %>/js/commonsList/list.js"></script>
<script type="text/javascript">
	//分页起始位置
	var _pageStart = '${pageStart}';	
	_pageStart = parseInt(_pageStart);
	_QParams = '${QParams}';
	//ajax获取数据 ,每页显示10条数据	
	data('${keyWords}','${dataStart}','${QParams}');	
	var _keyWords = '${keyWords}';
</script>

</head>
<body style="margin: 0px 0px;width: 98%">
	<br/>
	<span class="nums" style="margin-left: 10px;border-bottom: 1px solid #E5E5E5;" id="resultCount"></span>
	<!--  内容 -->
	<div id="resultData" style="margin-top: 10px;">
		
	</div>
	<!-- 分页 -->			
	<div id="Pagination" class="pagination" style="margin-left:10px;padding-bottom: 10px;padding-top: 10px">
    </div>	
</body>
</html>