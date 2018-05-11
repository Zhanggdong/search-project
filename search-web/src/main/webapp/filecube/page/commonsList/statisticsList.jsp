<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>文件资源立方</title>
<link rel="stylesheet" href="<%=path%>/commons/jquery/ui/flick/jquery-ui-1.9.1.custom.min.css" />
<link rel="stylesheet" href="<%=path%>/commons/suggest/reset-grids-min.css">

<link rel="stylesheet" href="<%=path%>/css/list.css">
<link rel="stylesheet" href="<%=path%>/commons/pagination/pagination.css">
<style type="text/css">
html {
	font-size: 12px;
	overflow: auto;
}
</style>
<script type="text/javascript" src="<%=path%>/commons/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=path%>/commons/jquery/ui/jquery-ui-1.9.1.custom.min.js"></script>
<script type="text/javascript" src="<%=path%>/commons/pagination/pagination.js"></script>

<script type="text/javascript" src="<%=path%>/js/document.js"></script>
<script type="text/javascript" src="<%=path%>/js/file/permission.js"></script>
<script type="text/javascript" src="<%=path%>/js/commonsList/statisticsList.js"></script>
<script type="text/javascript">
	//分页起始位置
	var _pageStart = '${pageStart}';
	_pageStart = parseInt(_pageStart);
	//ajax获取数据 ,每页显示10条数据
	data('${keyWords}', '${dataStart}', '${QParams}');
	var _List_searchKeyWords = '${keyWords}';
	var _QParams = '${QParams}';
</script>
</head>
<body>

  <table>
    <!-- 搜索结果数，耗时
	<tr class="searchResultQtimeTr">
		<td style="padding-left: 10px;">
			<span style="color: #DD4B39;font: 20px 'Arial';">搜索</span>
		</td>
		<td style="padding-left: 10px;font-size: 13px;">
			<span class="nums" style="margin-left: 20px" id="resultCount"></span>
		</td>
	</tr>
	 -->
    <tr valign="top">
      <td width="170px" style="padding-left: 10px; padding-top: 15px;">
        <div id="accordionFacet" style="width: 220px;">

          <h3>文种</h3>
          <div>
            <ul id="fileKindGroupUL">

            </ul>
          </div>

          <h3>部门</h3>
          <div>
            <ul id="fileDepartmentNameGroupUL">

            </ul>
          </div>

          <h3>年度</h3>
          <div>
            <ul id="fileYearGroupUL">

            </ul>
          </div>

          <h3>文件类型</h3>
          <div>
            <ul id="fileTypeGroupUL">

            </ul>
          </div>

          <h3>文件扩展名</h3>
          <div>
            <ul id="fileExtensionGroupUL">

            </ul>
          </div>
        </div>
      </td>
      <td width="700px" style="padding-top: 10px; padding-bottom: 10px">
        <!--  内容 -->
        <div id="resultData"></div> <!-- 分页 -->
        <div id="Pagination" class="pagination" style="margin-left: 200px; padding-bottom: 10px; padding-top: 10px"></div>
      </td>
    </tr>
  </table>
  <form action="<%=path%>/search_preCommonsStatisticsList.action" method="post" id="facetSearchForm">
    <input type="hidden" value="${keyWords}" name="keyWords" /> <input type="hidden" id="facetSearchParam" name="QParams" />
  </form>
</body>
</html>