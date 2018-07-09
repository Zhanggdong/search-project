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
<title>检索首页</title>
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
.bdnuarrow {
    display: block;
    font-size:12px ;
    height: 0;
    left: 50%;
    line-height: 0;
    margin-left: -5px;
    position: absolute;
    top: -10px;
    width: 0;
}
.bdpfmenu {
    background-color: #fff;
    border: 1px solid #d1d1d1;
    box-shadow: 1px 1px 5px #d1d1d1;
    margin-top: -1px;
    position: absolute;
    right: 160px;
    top: 36px;
    width: 75px;
    z-index: 2;
}
.bdpfmenu a {
    display: block;
    line-height: 26px;
    margin: 0 !important;
    padding: 0 9px;
    text-align: left;
    text-decoration: none;
    font-size:12px;
}

.out-div {
        font-size: 14px;
        line-height: 25px;
        width: 200px;
        text-align: center;
        border-radius: 5px;
        margin-left: 32px;
        vertical-align: top;
        background-color: #fff;
        border: 1px solid #d1d1d1;
    }

    .arrow {
        width: 0px;
        height: 0px;
        border-top: 10px solid transparent;
        border-right: 10px solid;
        border-bottom: 10px solid transparent;
        position: absolute;
        margin-left: -10px;
        margin-top: 10px;
        border-right-color: #eee;
    }

label,input,select {
	display: block;
}

input.text,select.text {
	margin-bottom: 12px;
	width: 95%;
	padding: .4em;
}

fieldset {
	padding: 0;
	border: 0;
	margin-top: 25px;
}

h1 {
	font-size: 1.2em;
	margin: .6em 0;
}

div#users-contain {
	width: 350px;
	margin: 20px 0;
}

div#users-contain table {
	margin: 1em 0;
	border-collapse: collapse;
	width: 100%;
}

div#users-contain table td,div#users-contain table th {
	border: 1px solid #eee;
	padding: .6em 10px;
	text-align: left;
}

.ui-dialog .ui-state-error {
	padding: .3em;
}

.validateTips {
	border: 1px solid transparent;
	padding: 0.3em;
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
        $.getJSON(contextPath + '/api/query/getBureauList?timestamp=' + new Date(), function(data) {
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

    function submitForm() {
        var text = $('#searchText').val();
        text = text.replace(/[ ]/g, "");
        if (text == "") {
            alert("请输入查询关键字");
            return;
        }
        if (text == '*') {
            location.href = contextPath + "/api/search/search?query=" + encodeURIComponent($('#searchText').val()) + "&filters=" + encodeURIComponent(_QParams);
        } else {
            location.href = contextPath + "/api/search/search?query=" + encodeURIComponent($('#searchText').val());
        }
    }

	//高级搜索对话框
	function advanceSearchDialog() {
		$("#advanceSearchDialog").dialog({
			resizable : false,
			title : '高级搜索',
			width : 431,
			height : 396,
			modal : true,
			buttons : {
				"搜索" : function() {
					//检查并拼接参数
					checkAdvSearchForm();
					$('#advSearchKeyWord').val(advSearchKeyWord);
					$('#advSearchQParams').val(advSearchQParams);
					$("#advSearchForm").submit();
					$(this).dialog("close");
				},
				"重置" : function() {
					resetAdvSearchForm();
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			}
		});
		//初始化参数
		initAdvParams();
		//输入自动补全部门
		autocomplete();

	}
	function checkAdvSearchForm() {
		var keyWords = $('#dialogKeyWords').val();
		if (keyWords.replace(/[ ]/g, "") == "") {
			keyWords = "";
		}
		advSearchKeyWord = keyWords;

		//文件来源
		var fileSource = $('#fileSource').val();
		if (!(fileSource != "1")) {
			advSearchQParams += "&fq=fileSource:" + fileSource;
		}
		if (!(fileSource != "2")) {
			advSearchQParams += "&fq=fileSource:" + fileSource;
		}
		if (!(fileSource != "3")) {
			advSearchQParams += "&fq=fileSource:" + fileSource;
		}

		//创建人
		var creatorname = $('#creatorname').val();
		if (creatorname != null && creatorname != "") {
			advSearchQParams += "&fq=creatorname:" + creatorname;
		}

		//创建时间
		//日期范围
		var startDate = $('#createdate_startDate').val();
		var endDate = $('#createdate_endDate').val();
		if (startDate > endDate) {
			alert("结束时间必须在开始时间之后！");
			return true;
		} else if (startDate != "" && endDate == "") {
			advSearchQParams += "&fq=createdate:[" + startDate + " TO "
					+ systemDate + "]";
		} else if (startDate != "" && endDate != "") {
			advSearchQParams += "&fq=createdate:[" + startDate + " TO "
					+ endDate + "]";
		} else if (startDate == "" && endDate != "") {
			advSearchQParams += "&fq=createdate:[" + "2012-01-01" + " TO "
					+ endDate + "]";
		}
		//创建部门
		var dept_shortdn = $('#dept_shortdn').val();
		if (dept_shortdn != null && dept_shortdn != "") {
			advSearchQParams += "&fq=dept_shortdn:" + dept_shortdn;
		}

	}

	function setDeaultAdvIpt() {

	}

	function dateToUTC(datestr) {
		return datestr + "T00:00:00.000Z";
	}

	/**
	 * 重置查询条件
	 */
	function resetAdvSearchForm() {
		$("#dialogKeyWords").val(null);
		$("#searchOption").val(null);
		$("#creatorname").val(null);
		$("#createdate").val(null);
		$("#dept_shortdn").val(null);
		$("#fileSource").val(null);
	}

	/**
	 * 初始化参数
	 */
	function initAdvParams() {

	}
	/**
	 * 自动补全
	 */
	function autocomplete() {
		var dept_shortdn_datas = [];
		$.post(contextPath + "/query/getDepartmentList", function(data,
				textStatus) {
			for (var i = 0; i < data.data.length; i++) {
				//必须要加上换行符，不然不能提示
				dept_shortdn_datas.push(data.data[i].DEPT_SHORTDN + '\n');
			}
			$("#dept_shortdn").autocomplete({
				source : dept_shortdn_datas
			});
		}, "json");
	}

	function showhid(id){
	    document.getElementById(id).style.display ='block';
	}
	function showhid2(id){
		document.getElementById(id).style.display ='none';
	}
	function show(obj,id) {
	   	var objDiv = $("#"+id+"");
	   	$(objDiv).css("display","block");
	}
	function hide(obj,id) {
		var objDiv = $("#"+id+"");
		$(objDiv).css("display", "none");
	}
</script>
</head>
<jsp:include page="header.jsp"></jsp:include>
 <body>
<div class="mainContain">
      <div class="ps_zhihuijiansuozinan" onmouseover="showhid('downmenu1')" onmouseout="showhid2('downmenu1')">
                <a href="#" class="zinan" onclick="showhid('downmenu1');">检索指南</a>

               <div class="bdpfmenu"  id="downmenu1" style="left: 76%; top: 120px; display: none;">
               <div class="bdnuarrow">
                 <em></em>
                 <i></i>
                </div>
                <a onmouseover="javascript:show(this,'ruku');" onmouseout="hide(this,'ruku')">入库原则 </a>
				<a onmouseover="javascript:show(this,'fenchi');" onmouseout="hide(this,'fenchi')">分词原则</a>
				<a onmouseover="javascript:show(this,'chazhao');" onmouseout="hide(this,'chazhao')">查找原则</a>
				<a onmouseover="javascript:show(this,'paixu');" onmouseout="hide(this,'paixu')">排序原则</a>
				<a onmouseover="javascript:show(this,'shousuo');" onmouseout="hide(this,'shousuo')">搜索范围</a>
				<a onmouseover="javascript:show(this,'fjchaxun');" onmouseout="hide(this,'fjchaxun')">附件查询</a>
				<a onmouseover="javascript:show(this,'quxian');" onmouseout="hide(this,'quxian')">权限说明</a>
				<a onmouseover="javascript:show(this,'anli');" onmouseout="hide(this,'anli')">查询案例</a></div>

				<!-- 入库原则 -->
				<div id="ruku" style="padding-left:70px;display: none;">
				<div class="out-div" >
			    <div class="arrow" ></div>
			    <span>经过个人的件（发文，收文，阅件）用排序号的方式入库，已进入回收站的文件，不入检索库。</span>
			    </div>
			    </div>
			    <!-- 分词原则 -->
			    <div id="fenchi" style="padding-left:70px;margin-top:25px;display: none;">
				<div class="out-div">
			    <div class="arrow" ></div>
			    <span>智慧检索配置的了IKAnalyzer中文分词器他的分词原则如下：例如输入“我爱中华人民共和国”时会匹配成两部分如“我爱中
			                          华人民共和国”整体和分成单个词语“我 | 爱 | 中华人民共和国 | 中华人民 | 中华 | 华人 | 人民共和国 ”（以竖杠划分为一个词）。</span>
			    </div>
			    </div>
			    <!-- 查找原则 -->
			    <div id="chazhao" style="padding-left:70px;margin-top:50px;display: none;">
				<div class="out-div">
			    <div class="arrow" ></div>
			    <span>查询全部：在对数据进行全部查询时，它遵循的是并行查询，（例如输入”移动测试”) 他会把文件标题、意见、办文编号、
			                          来文单位、正文标题、附件标题,等关键词内容中包含有“移动，测试，移动测试”的相关公文都查询出来。按其他条件查询：
			                          所遵循的过滤查询。例如（输入“移动测试”时当我们点击按“文 件标题”查询他只会查询出”标题“中所含有“移动，测试，移
			                          动测试”的相关公文）按其他条件也遵循这一原则。</span>
			    </div>
			    </div>
			    <!-- 排序原则 -->
			    <div id="paixu" style="padding-left:70px;margin-top:75px;display: none;">
				<div class="out-div">
			    <div class="arrow" ></div>
			    <span>所有查询结果返回时，按查询的匹配程度进行排序。</span>
			    </div>
			    </div>
			    <!-- 搜索范围原则 -->
			    <div id="shousuo" style="padding-left:70px;margin-top:100px;display: none;">
				<div class="out-div">
			    <div class="arrow" ></div>
			    <span>查询全部：在查询全部时，暂定为在“文件标题”、“意见”、“办文编号”、“来文单位”、“正文标题”、“正文内容”、“附件标题”、“附件内容”这些内容中
			                          查找与关键字相匹配的公文，只要一项符合都会被 查询出来。 按其他条件查询：例如当我们选择按“标题”查询时，关键字只
			                          在 文件标题这一字段中进行检索，（“意见”、“办文编号”、“来文单位”、“正文内容”、“附件标题”、“附件内容”）也都遵循这 一查询方法。</span>
			    </div>
			    </div>
			    <!-- 附件内容查询范围 -->
			    <div id="fjchaxun" style="padding-left:70px;margin-top:125px;display: none;">
				<div class="out-div">
			    <div class="arrow" ></div>
			    <span>附件内容查询只支持对word、excle、txt、.properties等可编辑的文件类型查询，对于图片(例如：png、JPG格式图片)、PDF的内容是不支持查询的</span>
			    </div>
			    </div>
			     <!-- 权限说明 -->
			    <div id="quxian" style="padding-left:70px;margin-top:150px;display: none;">
				<div class="out-div">
			    <div class="arrow" ></div>
			    <span>
			    	全区所有件查询组：查系统内所有文件<br/>
			    	区领导：OA最上层部门文件和兼职的委办局的所有文件<br/>
			    	局办领导：查本局办所有文件和兼职所在的委办局所有文件，另外还能查自己的经手件（因为两办存在特殊，管委会文件也要经过局办领导）<br/>
			    	局办所有件查询组：能够查询局办所有文件（预防出现兼职，若存在兼职应把兼职所在部门的委办局所有文件也查出来）（不包含OA最上层部门文件）<br/>
			    	普通角色：同一科室查询的权限一致<br/>
			    	特殊情况：如果在旧OA中没有账号的用户是无法查询到相关文件的<br/>
			    </span>
			    </div>
			    </div>
			    <!-- 查询案例 -->
			    <div id="anli" style="padding-left:70px;margin-top:175px;display: none;">
				<div class="out-div">
			    <div class="arrow" ></div>
			    <span>在输入关键字时可以在中间加上空格（不加的话默认使用配置中分词器进行分词），例如输入“测试综合办”，它返回包含
                                                           关键字  “测试综合办”和“测试”以及“综合办”的结果。</span>
			    </div>
			    </div>


		</div>
        <div class="ps_logo">
                    <img alt="坪山" src="images/pingshan.png"/>
       </div>
    <div class="ps_search">
        <span><input type="text" id="searchText"/></span>
        <span><input type="submit" value="搜索" onclick="submitForm()"/></span>
        &nbsp&nbsp&nbsp&nbsp
        <span><input type="submit" value="高级搜索" onclick="advanceSearchDialog()"/></span>
    </div>
</div>
<!-- 高级检索对话框 -->
	<div id="advanceSearchDialog"
		style="display: none; padding-left: 40px;">
		<form id="advSearchForm" action="${ctx}/search/search" method="post">
			<center>
				<table class="table-adv" width="100%" class="ui-widget ui-widget-content">
					<tr>
						<td><label for="name">关键字：</label></td>
						<td style="text-align: left;"><input type="text"
							class="text ui-widget-content ui-corner-all" id="dialogKeyWords"
							name="dialogKeyWords" /></td>
					</tr>
					<tr>
						<td><label for="name">查询选项：</label></td>
						<td style="text-align: left;"><select
							class="text ui-widget-content ui-corner-all" id="searchOption"
							name="searchType">
								<option value="searchAll">全部</option>
								<option value="title">标题</option>
								<option value="commentContent">意见</option>
								<option value="banwenbianhao">办文编号</option>
								<option value="laiwendanwei">来文单位</option>
								<option value="zwtitle">正文标题</option>
								<option value="zwcontent">正文内容</option>
								<option value="fjname">附件标题</option>
								<option value="fjcontent">附件内容</option>
						</select></td>
					</tr>
					<tr>
						<td><label for="name">创建人：</label></td>
						<td style="text-align: left;"><input
							class="text ui-widget-content ui-corner-all" type="text"
							id="creatorname" name="creatorname" /></td>
					</tr>
					<tr>
						<td><label for="name">开始时间：</label></td>
						<td style="text-align: left;"><input type="text"
							class="text ui-widget-content ui-corner-all" readonly="readonly"
							id="createdate_startDate"
							onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
					</tr>
					<tr>
						<td><label for="name">结束时间：</label></td>
						<td style="text-align: left;"><input type="text"
							class="text ui-widget-content ui-corner-all" readonly="readonly"
							id="createdate_endDate"
							onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
					</tr>
					<tr>
						<td><label for="dept_shortdn">部门：</label></td>
						<td style="text-align: left;"><input type="text"
							class="text ui-widget-content ui-corner-all" id="dept_shortdn"
							name="dept_shortdn" /></td>
					</tr>
					<tr>
						<td><label for="name">信息来源：</label></td>
						<td style="text-align: left;"><select
							class="text ui-widget-content ui-corner-all" id="fileSource"
							name="fileSource">
								<option value="all">不限</option>
								<option value="1">新OA</option>
								<option value="2">旧OA</option>
								<option value="3">易办文</option>
						</select></td>
					</tr>
				</table>
			</center>
			<input type="hidden" id="advSearchKeyWord" name="keyWord" /> <input
				type="hidden" id="advSearchQParams" name="QParams" value="" />
		</form>
	</div>

</body>
</html>