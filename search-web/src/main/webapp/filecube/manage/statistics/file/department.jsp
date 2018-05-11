<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>部门分类统计</title>
<%@include file="../../../../commons/commons.jsp"%>
<script type="text/javascript" src="${ctx}/commons/javascript/qui-v3.3/libs/js/framework.js"></script>
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx}/commons/javascript/qui-v3.3/" />
<link rel="stylesheet" type="text/css" id="customSkin" />
<script type="text/javascript" src="${ctx}/commons/javascript/highcharts-4.1.5/js/highcharts.js"></script>
<script type="text/javascript">
    var contextPath = '${ctx}';
</script>
<script type="text/javascript" src="${ctx}/filecube/js/statistics/department.js"></script>
</head>
<body>
  <div class="box_tool_min padding_top2 padding_bottom2">
    <div class="center">
      <div class="left">
        <div class="right">
          <table width="100%">
            <tr>
              <td width="50%" style="padding-left: 10px;">当前位置：统计报表&gt;&gt;部门分类</td>
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
  <div id="container" style="height: 200px; margin: 0 auto; overflow: auto;"></div>
</body>
</html>