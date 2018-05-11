<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	pageContext.setAttribute("ctx", path);
%>
<html>
<head>
<title>查看文件</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/commons/javascript/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
  function setFont() {
    var fonts = document.all.item("riseOffice").ActiveDocument.Application.FontNames;
    var flag = 0;
    for (var i = 1; i <= fonts.Count; i++) {
      if (fonts(i) == '华文中宋') {
        flag = 1;
        break;
      }
    }
    if (flag == 0) {
      document.all.item("riseOffice").ActiveDocument.Content.Font.Name = '宋体';
    }
  }

  function openDocument() {
    try {
      document.all.item("riseOffice").OpenFromURL("../file/download?fileGUID=${param.fileGUID}", false);
    } catch (e) {
    }
    showToolbars(false);
    setReadOnly(true);
    enableFileSaveMenu(false);
    var riseObj = document.all.item("riseOffice");
    enableReviewBar(true);
    riseObj.IsShowToolMenu = true;
    var vw = riseObj.ActiveDocument.ActiveWindow.View;
    var doc = document.all.item("riseOffice").ActiveDocument;
    var app = doc.Application;
    var cmdbars = app.CommandBars;
    doc.TrackRevisions = true;
    cmdbars("Reviewing").Enabled = true;
    cmdbars("Reviewing").Visible = true;
    cmdbars(40).Enabled = true;
    cmdbars(40).Visible = true;
    vw.RevisionsView = 0;
    vw.ShowRevisionsAndComments = false;
    setFont();
  }

  function showMenubar(bool) {
    document.all.item("riseOffice").Menubar = bool;
  }

  function showStatusbar(bool) {
    document.all.item("riseOffice").Statusbar = bool;
  }

  function showToolbars(da) {
    var s = da.value;
    if (s == '显示工具栏') {
      document.all.item("riseOffice").Toolbars = true;
      da.value = '隐藏工具栏';
    } else {
      document.all.item("riseOffice").Toolbars = false;
      da.value = '显示工具栏';
    }
  }

  function setReviewMode(bool) {
    document.all.item("riseOffice").ActiveDocument.TrackRevisions = bool;
  }

  function showRevisions(da) {
    var s = da.value;
    if (s == '显示痕迹') {
      document.all.item("riseOffice").ActiveDocument.ShowRevisions = true;
      printRevisions(true);
      da.value = '隐藏痕迹';
    } else {
      document.all.item("riseOffice").ActiveDocument.ShowRevisions = false;
      printRevisions(false);
      da.value = '显示痕迹';
    }
  }

  function printRevisions(bool) {
    document.all.item("riseOffice").ActiveDocument.PrintRevisions = bool;
  }

  function acceptAllRevisions() {
    document.all.item("riseOffice").ActiveDocument.AcceptAllRevisions();
  }

  function rejectAllRevisions() {
    document.all.item("riseOffice").ActiveDocument.RejectAllRevisions();
  }

  function setNoCopy() {
    document.all.item("riseOffice").IsNoCopy = true;
  }

  function setReadOnly(bool) {
    var i;
    try {
      if (bool) {
        document.all.item("riseOffice").IsShowToolMenu = false;
      }
      with (document.all.item("riseOffice").ActiveDocument) {
        if (document.all.item("riseOffice").DocType == 1) { // word
          if ((ProtectionType != -1) && !bool) {
            Unprotect();
          }
          if ((ProtectionType == -1) && bool) {
            Protect(1, true, "");
          }
        } else if (document.all.item("riseOffice").DocType == 2) { // excel
          for (i = 1; i <= Application.Sheets.Count; i++) {
            if (bool) {
              Application.Sheets(i).Protect("", true, true, true);
            } else {
              Application.Sheets(i).Unprotect("");
            }
          }
          if (bool) {
            Application.ActiveWorkbook.Protect("", true);
          } else {
            Application.ActiveWorkbook.Unprotect("");
          }
        }
      }
    } catch (err) {
      alert("文档读写状态错误！");
    } finally {
    }
  }

  function enableReviewBar(bool) {
    try {
      document.all.item("riseOffice").ActiveDocument.CommandBars("Reviewing").Enabled = bool;
      document.all.item("riseOffice").ActiveDocument.CommandBars("Track Changes").Enabled = bool;
      document.all.item("riseOffice").IsShowToolMenu = bool;
    } catch (e) {
      var doc = document.all.item("riseOffice").ActiveDocument;
      var app = doc.Application;
      var doctype = document.all.item("riseOffice").DocType;
      if (6 != doctype) {
        return;
      }
      var cmdbars = app.CommandBars;
      document.all.item("riseOffice").IsShowToolMenu = !bool;
      doc.TrackRevisions = bool;
      cmdbars("Reviewing").Enabled = !bool;
      cmdbars("Reviewing").Visible = !bool;
      cmdbars(40).Enabled = !bool;
      cmdbars(40).Visible = !bool;
    }
  }

  function setMarkModify(bool) {
    setReviewMode(bool);
    enableReviewBar(!bool);
  }

  function showToolMenu(bool) {
    document.all.item("riseOffice").IsShowToolMenu = bool;
  }

  function enableFileNewMenu(bool) {
      bool= document.all.item("riseOffice").EnableFileCommand(0);
  }

  function enableFileOpenMenu(bool) {
	  bool= document.all.item("riseOffice").EnableFileCommand(1);
  }

  function enableFileCloseMenu(bool) {
	  bool= document.all.item("riseOffice").EnableFileCommand(2);
  }

  function enableFileSaveMenu(bool) {
	  bool= document.all.item("riseOffice").EnableFileCommand(3);
  }

  function enableFileSaveAsMenu(bool) {
	  bool= document.all.item("riseOffice").EnableFileCommand(4);
  }

  function enableFilePrintMenu(bool) {
	  bool= document.all.item("riseOffice").EnableFileCommand(5);
  }

  function enableFilePrintPreviewMenu(bool) {
	  bool= document.all.item("riseOffice").EnableFileCommand(6);
  }

  window.onload = function() {
    openDocument();
  }
</script>
</head>
<body topmargin=0 leftmargin=0 rightmargin=0 bottommargin=0 marginwidth=0 marginheight=0>
   <object id="riseOffice" classid="clsid:C9BC4DFF-4248-4a3c-8A49-63A7D317F404" codeBase="${ctx }/commons/ocx/OfficeControl.cab#version=5,0,2,4" width="100%" height="100%">
      <param name="BorderStyle" value="0" />
      <param name="BorderColor" value="14402205" />
      <param name="TitlebarColor" value="53668" />
      <param name="TitlebarTextColor" value="0" />
      <param name="MenubarColor" value="13160660" />
      <param name="Caption" value="欢迎使用！" />
      <param name="Titlebar" value="0" />
      <param name="MaxUploadSize" value="10000000" />
      <param name="CustomMenuCaption" value="辅助选项" />
      <param name="ProductCaption" value="深圳市罗湖区信息中心" />
      <param name="ProductKey" value="CF5FA619AD4331233437B6AE9E9137C09D5347BF" />
      <SPAN STYLE="color: red">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>
   </object>
</body>
</html>
