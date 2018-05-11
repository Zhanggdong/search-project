<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>浏览历史</title>
<%@include file="../../../commons/commons.jsp"%>
<script type="text/javascript" src="${ctx}/commons/javascript/qui-v3.3/libs/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/qui-v3.3/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/qui-v3.3/libs/js/framework.js"></script>
<link href="${ctx}/commons/javascript/qui-v3.3/libs/css/import_basic.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx}/commons/javascript/qui-v3.3/" scrollerY="false" />
<link rel="stylesheet" type="text/css" id="customSkin" />
<script type="text/javascript" src="${ctx}/commons/javascript/qui-v3.3/libs/js/tree/ztree/ztree.js"></script>
<link href="${ctx}/commons/javascript/qui-v3.3/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/commons/javascript/qui-v3.3/libs/js/nav/layout.js"></script>
<script src="${ctx}/commons/javascript/qui-v3.3/libs/js/table/quiGrid.js" type="text/javascript"></script>
<style>
.l-layout-center {
	border: none !important;
}

.l-layout-left {
	border-bottom: none !important;
}

.l-layout-drophandle-left {
	width: 10px;
}
</style>
</head>
<body>
  <div id="layout1">
    <div id="centerCon" position="center" style="">
      <div class="box_tool_min padding_top2 padding_bottom2">
        <div class="center">
          <div class="left">
            <div class="right">
              <table width="100%">
                <tr>
                  <td width="50%" style="padding-left: 10px;">当前位置：个人中心&gt;&gt;浏览历史</td>
                  <td><div class="padding_right10" style="float: right">
                      <div class="box_tool_line"></div>
                    </div></td>
                </tr>
              </table>
            </div>
          </div>
        </div>
        <div class="clear"></div>
      </div>
      <div class="box3" panelTitle="查询" showStatus="false">
        <table>
          <tr>
            <td>名称：</td>
            <td><input type="text" style="width: 200px;" /></td>
            <td><button id="search">
                <span class="icon_find">查询</span>
              </button></td>
          </tr>
        </table>
      </div>
      <div style="margin: 0; padding: 0 5px 0 0;">
        <div id="maingrid"></div>
      </div>
    </div>
  </div>


  <script type="text/javascript">
      var g;
      function initComplete() {
        $("#layout1").layout({
          leftWidth : 150,
          onEndResize : function() {
            g.resetWidth();
          }
        });

        g = $("#maingrid").quiGrid({
          columns : [ {
            display : '文件名称',
            name : 'fileInfo.title',
            align : 'left',
            width : "50%"
          }, {
            display : '类型',
            name : 'fileInfo.bizTypeName',
            align : 'center',
            width : "20%"
          }, {
            display : '浏览时间',
            name : 'openDate',
            align : 'center',
            width : "20%"
          }],
          url : '/search/openHistoryList',
          pageSize : 20,
          sortName : 'openDate',
          rownumbers : true,
          checkbox : true,
          height : '100%',
          width : "100%",
          percentWidthMode : true
        });
      }
      function customHeightSet(contentHeight) {
        $(".cusBoxContent").height(contentHeight - 55)
        $(".orgTreeContainer").height(contentHeight - 30);
      }
    </script>
</body>
</html>