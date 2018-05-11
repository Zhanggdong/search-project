<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>他人共享</title>
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
    <div id="centerCon">
      <div class="box_tool_min padding_top2 padding_bottom2">
        <div class="center">
          <div class="left">
            <div class="right">
              <table width="100%">
                <tr>
                  <td width="50%" style="padding-left: 10px;">当前位置：个人中心&gt;&gt;他人共享</td>
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
      <div class="box3">
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
            display : '名称',
            name : 'name',
            align : 'left',
            width : "25%"
          }, {
            display : '类型',
            name : 'sex',
            align : 'left',
            width : "25%"
          }, {
            display : '大小',
            name : 'deptName',
            align : 'left',
            width : "25%",
            isSort : false
          }, {
            display : '创建时间',
            name : 'degree',
            align : 'left',
            width : "25%"
          }, {
            display : '操作',
            isAllowHide : false,
            align : 'center',
            width : 180,
            //操作列渲染为图标
            render : function(rowdata, rowindex, value, column) {
              return '<div class="padding_top4 padding_left5">' + '<span class="img_delete hand" title="取消收藏" onclick="onDelete(' + rowdata.id + ',' + rowindex + ')"></span>' + '</div>';
            }
          } ],
          url : '/favorite/list',
          pageSize : 20,
          sortName : 'id',
          rownumbers : true,
          checkbox : true,
          height : '100%',
          width : "100%",
          percentWidthMode : true
        });
      }
      //添加
      function addUser() {
        top.Dialog.open({
          URL : "${ctx}/commons/javascript/qui-v3.3/sample/layout/user-management-content.jsp",
          Title : "新增",
          Width : 600,
          Height : 400
        });
      }

      //批量删除
      function deleteUser() {
        top.Dialog.alert("向后台发送ajax请求来批量删除。见JAVA版或.NET版演示。");
      }

      //查看
      function onView(rowid) {
        alert("选择的记录Id是:" + rowid);
        top.Dialog.open({
          URL : "${ctx}/commons/javascript/qui-v3.3/sample/layout/user-management-content2.jsp",
          Title : "查看",
          Width : 500,
          Height : 330
        });
      }

      //修改
      function onEdit(rowid) {
        top.Dialog.alert("见JAVA版或.NET版演示。");
      }

      //删除
      function onDelete(rowid, rowidx) {
        top.Dialog.confirm("确定要删除该记录吗？", function() {
          top.Dialog.alert("向后台发送ajax请求来删除。见JAVA版或.NET版演示。");
        });
      }

      function customHeightSet(contentHeight) {
        $(".cusBoxContent").height(contentHeight - 55)
        $(".orgTreeContainer").height(contentHeight - 30);
      }
    </script>
</body>
</html>