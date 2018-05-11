<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的文件夹</title>
<%@include file="../../../commons/commons.jsp"%>
<script type="text/javascript" src="${ctx}/commons/javascript/qui-v3.3/libs/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/qui-v3.3/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/qui-v3.3/libs/js/framework.js"></script>
<link href="${ctx}/commons/javascript/qui-v3.3/libs/css/import_basic.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx}/commons/javascript/qui-v3.3/" scrollerY="false" />
<link rel="stylesheet" type="text/css" id="customSkin" />
<link href="${ctx}/commons/javascript/qui-v3.3/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/commons/javascript/qui-v3.3/libs/js/tree/ztree/ztree.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/qui-v3.3/libs/js/nav/layout.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/qui-v3.3/libs/js/table/quiGrid.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/qui-v3.3/libs/js/popup/dialog.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/qui-v3.3/libs/js/form/form.js"></script>

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
  <div class="box_tool_min padding_top2 padding_bottom2">
    <div class="center">
      <div class="left">
        <div class="right">
          <table width="100%">
            <tr>
              <td width="50%" style="padding-left: 10px;">当前位置：个人中心&gt;&gt;我的文件夹</td>
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
  <div id="layout1">
    <div id="leftCon" position="left" style="" panelTitle="个人文件夹">
      <div style="text-align: center;">
        <a href="#" onclick="addFolder();">新建</a>&nbsp;|&nbsp;<a href="#" onclick="delFolder();">删除</a>
      </div>
      <div class="folderTreeContainer">
        <ul id="folderTree" class="ztree"></ul>
      </div>
    </div>
    <div id="centerCon" position="center" style="">
      <div style="margin: 0; padding: 0 5px 0 0;">
        <div id="maingrid"></div>
      </div>
    </div>
  </div>
  <div style="display: none" id="addfolderdiv">
    <form id="folderForm" action="/folder/add" method="post">
      <input id = "parentUUID" name="parentUUID" type="hidden" />
      <table class="tableStyle" formMode="line">
        <tr>
          <td width="100">名称：</td>
          <td><input id="name" name="name" type="text" class="validate[required]" style="width: 240px;" /></td>
        </tr>
        <tr>
          <td>描述：</td>
          <td><textarea id="description" name="description" style="width: 240px; height: 80px;"></textarea></td>
        </tr>
        <tr>
          <td colspan="2"><input type="button" onclick="saveFolder('')" value="提交" />&nbsp;<input type="reset" value="重置" /></td>
        </tr>
      </table>
    </form>
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

      // 文件夹目录树
      //初始化tree
      initTree();

      // 文件列表
      g = $("#maingrid").quiGrid(
              {
                  columns : [
                          {
                              display : '名称',
                              name : 'name',
                              align : 'left',
                              width : "25%"
                          },
                          {
                              display : '类型',
                              name : 'sex',
                              align : 'left',
                              width : "25%"
                          },
                          {
                              display : '大小',
                              name : 'deptName',
                              align : 'left',
                              width : "25%",
                              isSort : false
                          },
                          {
                              display : '创建时间',
                              name : 'degree',
                              align : 'left',
                              width : "25%"
                          },
                          {
                              display : '操作',
                              isAllowHide : false,
                              align : 'center',
                              width : 180,
                              render : function(rowdata, rowindex, value, column) {
                                  return '<div class="padding_top4 padding_left5">' + '<span class="img_view hand" title="查看" onclick="onView(' + rowdata.id + ')"></span>' + '<span class="img_edit hand" title="修改" onclick="onEdit(' + rowdata.id + ')"></span>'
                                          + '<span class="img_delete hand" title="删除" onclick="onDelete(' + rowdata.id + ',' + rowindex + ')"></span>' + '</div>';
                              }
                          } ],
                  url : '',
                  pageSize : 10,
                  sortName : 'id',
                  rownumbers : true,
                  checkbox : true,
                  height : '100%',
                  width : "100%",
                  percentWidthMode : true
              });

  }

  //树属性配置
  var selectionSetting = {
      async : {
          enable : true,
          dataType : 'JSON',
          url : "${ctx}/folder/tree"
      },
      view : {
          dblClickExpand : true
      },
      callback : {
          //响应右键
          //onRightClick: OnRightClick,
          onClick : zTreeSelect
      }

  };

  //初始化tree处理
  function initTree() {
      $.fn.zTree.init($("#folderTree"), selectionSetting);
      zTree = $.fn.zTree.getZTreeObj("folderTree");

  }

  //点击树节点刷选对应的表格数据
  function zTreeSelect(event, treeId, treeNode) {
      var zTree = $.fn.zTree.getZTreeObj("folderTree");
      zTree.expandNode(treeNode);
      $("#parentUUID").val(treeNode.id);
  }

  function saveFolder() {
      $.ajax({
          url : "${ctx }/folder/add",
          data : $('#folderForm').serialize(),
          dataType : "json",
          type : "post",
          success : function() {
              top.Dialog.alert("保存成功|提示信息", function() {
                  top.frmright.window.location.reload();
                  top.Dialog.close();
              })

          },
          error : function(data, textStatus, jqXHR) {
              //  $("#result").html(data);  
          }
      });
  }

  function delFolder() {
      top.Dialog.confirm("确定要删除该记录吗？", function() {
          //删除记录
          var id = $("#parentUUID").val();
          alert(id)
          $.post("${ctx}/folder/del", {
              "id" : id
          }, function(result) {
              handleResult(result.status);
          }, "json");
          //刷新表格

      });

  }

  //删除后的提示
  function handleResult(result) {
      if (result == 1) {
          top.Dialog.alert("删除成功！", null, null, null, 1);
          window.location.reload();
      } else {
          top.Dialog.alert("删除失败！");
      }
  }

  // 批量删除
  function deleteUser() {
      top.Dialog.alert("向后台发送ajax请求来批量删除。见JAVA版或.NET版演示。");
  }

  // 查看
  function onView(rowid) {
      alert("选择的记录Id是:" + rowid);
      top.Dialog.open({
          URL : "${ctx}/commons/javascript/qui-v3.3/sample/layout/user-management-content2.jsp",
          Title : "查看",
          Width : 500,
          Height : 330
      });
  }

  // 修改
  function onEdit(rowid) {
      top.Dialog.alert("见JAVA版或.NET版演示。");
  }

  // 删除
  function onDelete(rowid, rowidx) {
      top.Dialog.confirm("确定要删除该记录吗？", function() {
          top.Dialog.alert("向后台发送ajax请求来删除。见JAVA版或.NET版演示。");
      });
  }

  function customHeightSet(contentHeight) {
      $(".cusBoxContent").height(contentHeight - 55)
      $(".orgTreeContainer").height(contentHeight - 30);
  }

  //新建文件夹
  function addFolder() {
      var aa = $.fn.zTree.getZTreeObj("folderTree");
      //	var checkedNodes = zTree.getCheckedNodes(true);
      Dialog.open({
          InvokeElementId : "addfolderdiv",
          Title : "新增文件夹",
          Width : 400,
          Height : 160
      });
  }
  </script>
</body>
</html>