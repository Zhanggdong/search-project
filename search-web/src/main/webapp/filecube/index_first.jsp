<%@page import="net.risesoft.common.consts.RiseUser"%>
<%@page import="net.risesoft.common.consts.SessionConst"%>
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
<title>文件资源立方</title>
<link href="${ctx }/commons/javascript/qui-v3.3/libs/css/import_basic.css" rel="stylesheet" type="text/css" />
<link type="text/css" id="skin" prePath="${ctx }/commons/javascript/qui-v3.3/" />
<link type="text/css" id="customSkin" />
<link type="text/css" href="${ctx}/commons/javascript/qui-v3.3/system/modern_accordion/skin/style.css" rel="stylesheet" id="skin" skinPath="system/modern_accordion/skin/" />
<link type="text/css" href="${ctx}/commons/javascript/suggest/reset-grids-min.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/commons/javascript/jquery/ui/css/flick/jquery-ui-1.9.1.custom.min.css" rel="stylesheet" />
<link type="text/css" href="css/index.css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/commons/javascript/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/jquery/ui/jquery-ui-1.9.1.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/suggest/kissy-min.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/suggest/suggest-min.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<style type="text/css">
html {
	font-size: 12px;
	overflow: auto;
}

.inputLable {
	text-align: right;
	vertical-align: middle;
}

.inputTxt {
	background: none repeat scroll 0 0 transparent;
	border: 1px solid #C3C3C3;
	height: 20px;
	line-height: 20px;
	overflow: hidden;
	padding: 2px 0 2px 3px;
	text-overflow: ellipsis;
	white-space: nowrap;
	width: 292px;
}

.inputSelect {
	display: block;
	width: 296px;
	height: 25px;
	padding: 4px 4px;
	line-height: 25px;
}

.advanceSearchDialogTr {
	height: 38px;
	line-height: 38px;
}
</style>
<script type="text/javascript">
    var systemDate = '${date}';
    var contextPath = '${ctx}';
    function setDeaultAdvIpt() {
        $("#fileType").attr("disabled", "disabled");
        $("#fileSource").attr("disabled", "disabled");

        $('#dialogKeyWords').val('');
        $('#createdate_startDate').val('');
        $('#createdate_endDate').val('');

        var departNameSelect = '<option value="all">不限</option>';
        $.getJSON(contextPath + '/search/getBureauList?timestamp=' + new Date(), function(data) {
            if (data) {
                $.each(data, function(index) {
                    departNameSelect += '<option value="' + data[index].guid + '">' + data[index].name + '</option>';
                });
                $('#departmentName').html(departNameSelect);
            }
        });
    }

    var advSearchKeyWord = "";
    var advSearchQParams = "";

    //高级搜索对话框
    function advanceSearchDialog() {
        $("#advanceSearchDialog").dialog({
            resizable : false,
            title : '高级搜索',
            width : 461,
            height : 340,
            modal : true,
            buttons : {
                "搜索" : function() {
                    checkAdvSearchForm();
                    $('#advSearchKeyWord').val(advSearchKeyWord);
                    $('#advSearchQParams').val(advSearchQParams);
                    $('#advSearchForm').submit();
                    $(this).dialog("close");
                },
                '取消' : function() {
                    $(this).dialog("close");
                }
            }
        });
        setDeaultAdvIpt();
    }

    function dateToUTC(datestr) {
        return datestr + "T00:00:00.000Z";
    }

    function checkAdvSearchForm() {
        var keyWords = $('#dialogKeyWords').val();
        if (keyWords.replace(/[ ]/g, "") == "") {
            keyWords = "";
        } else {
            keyWords = keyWords;
        }
        advSearchKeyWord = keyWords;

        //文件分类
        var type = $('#biztype').val();
        if (!(type == 'all')) {
            advSearchQParams += "&fq=biztype:" + type;
        }
        //日期范围
        var startDate = $('#createdate_startDate').val();
        var endDate = $('#createdate_endDate').val();
        if (startDate > endDate) {
            alert("结束时间必须在开始时间之后！");
            return true;
        } else if (startDate != "" && endDate == "") {
            advSearchQParams += "&fq=createdate:[" + dateToUTC(startDate) + " TO " + dateToUTC(systemDate) + "]";
        } else if (startDate != "" && endDate != "") {
            advSearchQParams += "&fq=createdate:[" + dateToUTC(startDate) + " TO " + dateToUTC(endDate) + "]";
        } else if (startDate == "" && endDate != "") {
            advSearchQParams += "&fq=createdate:[" + dateToUTC("2012-01-01") + " TO " + dateToUTC(endDate) + "]";
        }

        //部门
        var bureauguid = $('#departmentName').val();
        if (!(bureauguid == 'all')) {
            advSearchQParams += "&fq=bureauguid:\"" + bureauguid + "\"";
        }

        //文件类型
        var ftype = $('#fileType').val();
        if (!(ftype == 'all')) {
            advSearchQParams += "&fq=appid:" + ftype;
        }

        //文件来源
        var source = $('#fileSource').val();
        if (!(source == 'all')) {
            advSearchQParams += "&fq=filetype:" + source;
        }
    }
</script>
</head>
<jsp:include page="header.jsp"></jsp:include>
<body>
  <!-- top区域 -->
  <% /*<div id="mainFrame" class="topTools">
    <div id="hbox">
      <div id="bs_bannercenter">
        <div id="bs_bannerright">
          <div id="bs_bannerleft">
          <div class="float_right padding_top2 padding_right10">
              <table width="300" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="30" class="float_right padding_top10 padding_right5"><a href="../index.jsp" target="_parent" onclick='backHome()'><span
                      class="icon_home hand">首页</span></a>&nbsp;<span class="icon_exit hand"
                      onclick="top.Dialog.confirm('确定要退出系统吗？',function(){windowClose()});">退出</span></td>
                </tr>
                <tr>
                  <td height="30" class="float_right padding_top10 padding_right10">当前用户：${sessionScope.riseUser.userName }&nbsp;|&nbsp;所属部门：${sessionScope.riseUser.bureauName }</td>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="topPadding"></div>*/ %>
  <!-- top区域结束 -->
  <center>
    <div class="middle">
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>
        <img src="images/logo.jpg" title="" />
      </p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <div class="searchFM">
        <input maxlength="100" name="keyWords" class="searchIpt" id="searchText" /> <img src="images/search.png" id="searchBtn" style="cursor: pointer; vertical-align: top;" /> 
     <% //<img src="images/adv_search.png" id="advanceSearchBtn" style="cursor: pointer; vertical-align: top;" onclick="advanceSearchDialog()" /> %>
      </div>
      <div class="hotKeyWors">
        <table width="100%">
          <tr>
           <% //<td width="80" valign="top" style="font-weight: bold; padding-top: 5px;">热词推荐：</td>%>
            <td>
              <ul id="hotKeyWorsUL" style="float: left;">
              </ul>
            </td>
          </tr>
          <tr>
            <td colspan="2" align="center"><br /> <br />
              <p class="cp"></p></td>
          </tr>
        </table>
      </div>
    </div>
  </center>
  <!-- 高级检索对话框 -->
  <div id="advanceSearchDialog" style="display: none; padding-left: 40px;">
    <center>
      <table width="100%">
        <tr class="advanceSearchDialogTr">
          <td class="inputLable">关键字：</td>
          <td style="text-align: left;"><input type="text" class="inputTxt" id="dialogKeyWords" /></td>
        </tr>
        <tr class="advanceSearchDialogTr">
          <td class="inputLable">文件分类：</td>
          <td style="text-align: left;"><select class="inputSelect" id="biztype">
              <option value="all">不限</option>
              <option value="1">办件</option>
              <option value="2">内部邮箱</option>
              <option value="3">资料中心</option>
          </select></td>
        </tr>
        <tr class="advanceSearchDialogTr">
          <td class="inputLable">时间：</td>
          <td style="text-align: left;"><input type="text" class="inputTxt" style="width: 132px" readonly="readonly" id="createdate_startDate"
            onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd'})" /> 至 <input type="text" class="inputTxt" style="width: 132px" readonly="readonly" id="createdate_endDate"
            onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd'})" /></td>
        </tr>
        <tr class="advanceSearchDialogTr">
          <td class="inputLable">部门：</td>
          <td style="text-align: left;"><select class="inputSelect" id="departmentName">
              <option value="all">不限</option>
          </select></td>
        </tr>
        <tr class="advanceSearchDialogTr">
          <td class="inputLable">文件类型：</td>
          <td style="text-align: left;"><select class="inputSelect" id="fileType">
              <option value="all">不限</option>
          </select></td>
        </tr>
          <tr class="advanceSearchDialogTr">
          <td class="inputLable">文件来源：</td>
          <td style="text-align: left;"><select class="inputSelect" id="fileSource">
              <option value="all">不限</option>
          </select></td>
        </tr>
      </table>
    </center>
    <form action="${ctx}/search/search" method="post" id="advSearchForm">
      <input type="hidden" id="advSearchKeyWord" name="keyWord" /> <input type="hidden" id="advSearchQParams" name="QParams" />
    </form>
  </div>
</body>
</html>