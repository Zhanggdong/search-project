<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	pageContext.setAttribute("ctx", path);
	/*int pageNum = StringUtils.isNotBlank(request
			.getParameter("pageNum")) ? Integer.parseInt(request
			.getParameter("pageNum")) : 0;
	int pageSize = StringUtils.isNotBlank(request.getParameter("pageSize"))
			? Integer.parseInt(request.getParameter("pageSize"))
			: 10;
	pageContext.setAttribute("query", request.getParameter("query"));
	pageContext.setAttribute("searchType",request.getParameter("searchType"));
	pageContext.setAttribute("filterQueryParams", request.getParameter("filterQueryParams"));
	pageContext.setAttribute("pageNum", pageNum);
	pageContext.setAttribute("pageSize", pageSize);*/
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>智慧检索</title>
<meta http-equiv="X-UA-Compatible" content="IE=8">
<script type="text/javascript"
	src="${ctx}/commons/javascript/jquery/jquery-1.8.2.js"></script>
<script type="text/javascript"
	src="${ctx}/commons/javascript/jquery/ui/jquery-ui-1.9.1.custom.min.js"></script>
<%-- <script type="text/javascript" src="${ctx}/commons/javascript/jquery/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/jquery/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/jquery/ui/jquery.ui.position.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/jquery/ui/jquery.ui.menu.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/jquery/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/jquery/ui/jquery.ui.autocomplete.js"></script> --%>
<script type="text/javascript"
	src="${ctx}/commons/javascript/layer/layer.min.js"></script>
<script type="text/javascript"
	src="${ctx}/commons/javascript/pagination/pagination.js"></script>
<script type="text/javascript"
	src="${ctx}/commons/javascript/suggest/kissy-min.js"></script>
<script type="text/javascript"
	src="${ctx}/commons/javascript/suggest/suggest-min.js"></script>
<script type="text/javascript"
	src="${ctx}/commons/javascript/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/commons/javascript/jquery-plugin/jquery-dateFormat.min.js"></script>
<script type="text/javascript" src="${ctx}/filecube/js/permission.js"></script>
<link href="${ctx }/filecube/css/index.css" rel="stylesheet"
	type="text/css" />
<%-- <link href="${ctx }/commons/javascript/jquery/ui/css/themes/base/jquery.ui.all.css" rel="stylesheet" type="text/css"/> --%>
<link
	href="${ctx }/commons/javascript/jquery/ui/css/flick/jquery-ui-1.9.1.custom.min.css"
	rel="stylesheet" type="text/css" />
<style>
body {
	font-size: 62.5%;
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
	var serverip = "${serverip}";
	var contextPath = "${ctx}";
	var _globalCondition = "${folderLevels}";
	var officeJson = "${officeJson}";
	var total = parseInt('${total}');
	var pageNum = parseInt('${pageNum}');
	var pageSize = 10;
	var oaServerUrl = "${oaServerUrl}";
	var oaServeroldUrl = "${oaServeroldUrl}";
	var fileServerUrl = "${fileServerUrl}";
	var oaHistoryServerUrl = "${oaHistoryServerUrl}";
	var userLoginName = "${userLoginName}";
	var searchType = "${searchType}";

	$(function() {
		$("#searchId").children("li").each(function() {
			var currentId = $(this).attr("id");
			if (currentId == "${searchType}") {
				$(this).addClass("ps_search_result_tab_sel");
			}
		});
		var ver;
		var bType;
		var vNumber;
		ver = navigator.appVersion;
		bType = navigator.appName;
		if (bType == "Microsoft Internet Explorer") {
			vNumber = parseFloat(ver.substring(ver.indexOf("MSIE") + 5, ver
					.lastIndexOf("Windows")));
			if (vNumber != 6.0) {
				$('.topTools').after('<div class="topPadding"></div>');
			}
		} else {
			$('.topTools').after('<div class="topPadding"></div>');
		}

		$("#Pagination").pagination(total, {
			num_edge_entries : 2,
			num_display_entries : 5,
			callback : pageselectCallback,
			current_page : pageNum,
			items_per_page : 10
		});
	});

	function pageselectCallback(pageNum, jq) {
		var dataStart = pageNum * pageSize;
		location.href=contextPath+"/api/search/search?searchType="+searchType+"&query="+encodeURIComponent(_List_searchKeyWords)+"&filterQueryParams="+encodeURIComponent(_filterQueryParams) + "&pageNum="+pageNum+"&pageSize="+pageSize;
	}
	var _List_searchKeyWords = '${query}';
	var _filterQueryParams = '${filterQueryParams}';
	//回车事件
	document.onkeydown = function(event) {
		e = event ? event : (window.event ? window.event : null);
		if (e.keyCode == 13) {
			var text = $('#searchText').val();
			if (text.replace(/[ ]/g, "") == "") {
				return;
			}
			submitForm();
		}
	};

	/**
	 * 点击搜索按钮时执行的函数
	 */
	function submitForm() {
		$("#searchId").children("li").each(function() {
			var currentId = $(this).attr("id");
			if (currentId == "${searchType}") {
				searchType = currentId;
			}
		});
		searchByType(searchType);
	}
	
	/**
	 * 根据选择的选项进行查询
	 * type:检索指标
	 */
	function searchByType(type) {
		_filterQueryParams = null;
		var text = $('#searchText').val();
		text = text.replace(/[ ]/g, "");
		if (text == "") {
			alert("请输入查询关键字");
			return;
		}
		else {
			location.href = contextPath + "/api/search/search?searchType=" + type
					+ "&query=" + encodeURIComponent($('#searchText').val());
		}
	}

	/**
	 * 根据guid打开文件
	 */
	function openfile(guid) {
		$.post(contextPath + '/api/query/checkPermission', {
			'guid' : guid
		}, function(data) {
			if (data) {
				alert('将在OA中打开此文件！');
			} else {
				alert('对不起，您没有查看该文件的权限！');
			}
		});
	}

	/**
	 * 过滤文件
	 * fqField：过滤字段
	 * fqValue：字段值
	 */
	function filterQuery(fqField, fqValue) {
		var filterQueryParams = $("#filterQueryParam").val();
		var fqArr = filterQueryParams.split("&fq=");
		var temp = "";
		var count = 0;
		for (var i = 0; i < fqArr.length; i++) {
			if (fqArr[i] == "") {
				continue;
			}
			if (fqArr[i].indexOf(fqField) > -1) {
				count++;
				if (fqValue != "") {
					if (fqValue.indexOf("{") > -1) {
						temp += "&fq=" + fqField + ":\"" + fqValue + "\"";
					} else {
						temp += "&fq=" + fqField + ":" + fqValue + "";
					}
				}
			} else {
				temp += "&fq=" + fqArr[i];
			}
		}
		if (count < 1) {
			temp += "&fq=" + fqField + ":" + fqValue + "";
		}
		$('#filterQueryParam').val(temp);
		$('#filterQueryForm').submit();
	}

	var setting = {
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : this.onClick
		}
	};
	var zNodes = [
	<%=request.getAttribute("orgtree")%>
	];

	function onClick(e, treeId, node) {
		filterQuery("bureauguid", node.id);
	}

	$(document).ready(function() {
		// $.fn.zTree.init($("#treeDemo"), setting, zNodes);
	});

	//正文内容截取
	function csubstr(len) {
		var str = document.getElementById("description").value;
		alert(str + "11");
		if (str.length > 150) {
			return str.substring(0, len) + "...";
		} else {
			return str;
		}
	};
	//正文内容截取
	function wordlimit(article, wordlength) { //参数分别为：类名，要显示的字符串长度
		//var cname=document.getElementsByClassName(cname);  //需要加省略符号的元素对象
		var cname = $("." + article);
		//alert(cname);
		for (var i = 0; i < cname.length; i++) {
			var nowhtml = cname[i].innerHTML; //元素的内容
			var nowlength = cname[i].innerHTML.length; //元素文本的长度
			if (nowlength > wordlength) {
				cname[i].innerHTML = nowhtml.substr(0, 150) + '......'; //截取元素的文本的长度并加上省略号
			}
		}
	};

	/** 
	 * 显示正文
	 *
	 * 
	 */
	function showZw(e,documentRowid,filename,filetype,instanceGUID,isalone,type){
		layer.closeAll();
		var id = instanceGUID.substring(1,instanceGUID.length-1);
		var left=null;
		if(isalone){
			//关闭所有div
			$("#allfile").children().each(function(){
				$(this).css("display","none");
			});
			left = '250px';
		}else{
			//关闭其它div
			var seletor = "#"+id+"";
			$(seletor).siblings().each(function(){
				$(this).css("display","none");
			});
			left = '150px';
		}
		var title=filetype=="zw"?"正文":filetype=="fj"?"附件":"";
		var url = "";
		//调用新OA相关接口
		if(type=="new"){
			//加载正文内容
			if(filetype=='zw'){
				url = oaServerUrl+"/"+"riseoffice/OfficeTagDownloadServlet?instanceGUID="+instanceGUID+"&DocumentRowGUID="+documentRowid; 
			}else if(filetype='fj'){
				url = oaServerUrl+"/"+"servlet/RisefileViewData1?fileName="+filename+"&fileGUID="+documentRowid;
			}
			
		}	
		//调用旧OA相关接口
		if(type=="old"){
			if(filetype=='zw'){
				url = oaServeroldUrl+"/riseoffice/OfficeTagOldDownloadServlet?documentrow_guid="+documentRowid; 
			}else if(filetype='fj'){
				url = oaServeroldUrl+"/servlet/RisefileOldViewData?fileGUID="+documentRowid;
			}
		}
		url =fileServerUrl+"/view/url?url="+encodeURIComponent(url)+"&name="+encodeURIComponent(filename)+"&token=d3hrai5zempzLmdvdi5jbg==";
		$.layer({
		    type: 2,
		    shade:0,
		    fixed:false,
		    title: title,
		    maxmin: true,
		    resize: true,
		    iframe: {src : url},
		    //content:'zw.jsp?id='+id,
		    area: ['1000px' , '500px'],
		    offset: ['50px', left],
		    zIndex: layer.zIndex 
		});
	}
	
	/**
	 * 打开旧OA正文
	 */
	function viewdocumentold(guid){
		//http://172.16.61.133:8099/riseoffice/default/openInstanceAction.do?folder=todo&instanceGUID={AC100599-0000-0000-7AFD-62F30000E5A5}
		url=oaServeroldUrl+'/riseoffice/default/openInstanceAction.do?folder=todo&instanceGUID='+guid+'&loginName='+userLoginName+'&type=newoa';
		//调用历史公文接口
		//注意参数需要转码，否则springmvc会拦截，报400请求状态
		window.open(url);
		//window.open(contextPath+"/history/getGetHistoryOfficeList?instanceGUID="+encodeURIComponent(guid));
	}
	
	
	var advSearchKeyWord = "";
	var advSearchQParams = "";
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
		var dn = $('#dn').val();
		if (dn != null && dn != "") {
			advSearchQParams += "&fq=dn:" + dn;
		}

	}

	/**
	 * 重置查询条件
	 */
	function resetAdvSearchForm() {
		$("#dialogKeyWords").val(null);
		$("#searchOption").val(null);
		$("#creatorname").val(null);
		$("#createdate").val(null);
		$("#dn").val(null);
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
		var dn_datas = [];
		$.post(contextPath + "/query/getDepartmentList", function(data,
				textStatus) {
			for (var i = 0; i < data.data.length; i++) {
				//必须要加上换行符，不然不能提示
				dn_datas.push(data.data[i].dn + '\n');
			}
			$("#dn").autocomplete({
				source : dn_datas
			});
		}, "json");
	}
	
</script>
</head>
<body>
	<div class="mainContain">
		<%-- ${advanceData} --%>
		<div class="ps_search_enter">
			<div class="searchFM">
				<input type="hidden" id="filterQueryParam" name="filterQueryParams"
					value="${requestScope.filterQueryParams}" /> <img alt="icon"
					src="${ctx}/filecube/images/pingshan-icon.png" /> <span><input
					type="text" name="filterQueryParams" id="searchText"
					value="${query == '*' ? '' : query}" /> </span> <span> <input
					id="filterQueryParamsHiddenIpt" class="searchIpt" type="submit" value="搜索"
					onclick="submitForm()" name="query" maxlength="150"
					value="${query == '*' ? '' : query}" />
				</span> &nbsp&nbsp&nbsp&nbsp <span> <input id="advanceSearchBtn"
					class="searchIpt" type="submit" value="高级搜索"
					onclick="advanceSearchDialog()" />
				</span>
			</div>
		</div>
		<div class="ps_search_result">
			<!-- 搜索分类选项-->
			<div class="ps_search_result_tab">
				<ul class="clear" id="searchId">
					<li id="searchAll" class="fl pointer"
						onclick="searchByType('searchAll')">全部</li>
					<li id="title" class="fl pointer" onclick="searchByType('title')">标题</li>
					<li id="commentContent" class="fl pointer"
						onclick="searchByType('commentContent')">意见</li>
					<li id="banwenbianhao" class="fl pointer"
						onclick="searchByType('banwenbianhao')">办文编号</li>
					<li id="laiwendanwei" class="fl pointer"
						onclick="searchByType('laiwendanwei')">来文单位</li>
					<li id="zwtitle" class="fl pointer"
						onclick="searchByType('zwtitle')">正文标题</li>
					<li id="zwcontent" class="fl pointer"
						onclick="searchByType('zwcontent')">正文内容</li>
					<li id="fjname" class="fl pointer" onclick="searchByType('fjname')">附件标题</li>
					<li id="fjcontent" class="fl pointer"
						onclick="searchByType('fjcontent')">附件内容</li>
				</ul>
			</div>
			<div class="ps_search_result_tip">
				<p>找到${requestScope.total}条搜索结果（用时${requestScope.totalTime}毫秒）</p>
			</div>
			<!-- 搜索结果内容-->
			<div id="ps_search_result_con">
				<c:if test="${not empty requestScope.documents}">
					<c:forEach items="${requestScope.documents}"
						var="documents" varStatus="stat">
						<!-- 新OA公文索引库 -->
						<c:if test="${documents.type=='1'}">
							<div class="ps_search_result_con clear">
								<p class="elips" onclick="viewdocument('${documents.guid}')">${documents.title}</p>
								<c:if test="${documents.banwenbianhao!=null}">
									<c:choose>
										<c:when test="${documents.banwenbianhao=='null'}">
											<div>
												<span class="description"></span>
											</div>
										</c:when>
										<c:otherwise>
											<p class="article">办文编号：${documents.banwenbianhao}</p>
										</c:otherwise>
									</c:choose>
								</c:if>
								<c:if test="${documents.laiwendanwei!=null}">
									<c:choose>
										<c:when test="${documents.laiwendanwei=='null'}">
											<div>
												<span class="description"></span>
											</div>
										</c:when>
										<c:otherwise>
											<p class="article">来文单位：${documents.laiwendanwei}</p>
										</c:otherwise>
									</c:choose>
								</c:if>
								<div>
									<span>创建部门:${documents.dn}</span> <span>创建人:${documents.creatorname}</span>
									<span>创建时间:${documents.createdatetime}</span>
								</div>
							</div>
						</c:if>
						<!-- 新OA正文索引库 -->
						<c:if test="${documents.type=='2'}">
							<div class="ps_search_result_con clear">
								<p class="elips"
								   onclick="showZw(event,'${documents.id}','${documents.title}','zw','${documents.guid}','true','new')">${documents.title}</p>
									<%-- <p class="elips" onclick="showZw('${documents.id}','${documents.zwtitle}','zw','${documents.guid}');">${documents.zwtitle}</p> --%>
								<div>
									<input class="fr" type="button" value="打开原件"
										   onclick="viewdocument('${documents.guid}')"
										   style="cursor:pointer;" />
								</div>
								<c:choose>
									<c:when test="${documents.data=='null'}">
										<div>
											<span class="description"></span>
										</div>
									</c:when>
									<c:otherwise>
										<p class="article">${documents.data}</p>
									</c:otherwise>
								</c:choose>
								<div>
									<span>创建部门:${documents.dn}</span> <span>创建人:${documents.creatorname}</span>
									<span>创建时间:${documents.createdatetime}</span>
								</div>
							</div>
						</c:if>
						<!-- 新OA意见索引库 -->
						<c:if test="${documents.type=='3'}">
							<div class="ps_search_result_con clear">
								<p class="elips" onclick="viewdocument('${documents.guid}')">${documents.title}</p>
								<c:choose>
									<c:when test="${documents.data=='null'}">
										<div>
											<span class="description"></span>
										</div>
									</c:when>
									<c:otherwise>
										<p class="article">${documents.data}</p>
									</c:otherwise>
								</c:choose>
								<div>
									<span>创建部门:${documents.dn}</span> <span>创建人:${documents.creatorname}</span>
									<span>创建时间:${documents.createdatetime}</span>
								</div>
							</div>
						</c:if>
						<!-- 新OA附件索引库 -->
						<c:if test="${documents.type=='4'}">
							<div class="ps_search_result_con clear">
								<p class="elips"
								   onclick="showZw(event,'${documents.id}','${documents.title}','fj','${documents.guid}','true','new')">${documents.title}</p>
									<%-- <p class="elips" onclick="showZw('${documents.id}','${documents.title}','fj','${documents.guid}');">${documents.title}</p> --%>
								<div>
									<input class="fr" type="button" value="打开原件"
										   onclick="viewdocument('${documents.guid}')"
										   style="cursor:pointer;" />
								</div>
								<c:choose>
									<c:when test="${documents.data=='null'}">
										<div>
											<span class="description"></span>
										</div>
									</c:when>
									<c:otherwise>
										<p class="article">${documents.data}</p>
									</c:otherwise>
								</c:choose>
								<div>
									<span>创建部门:${documents.dn}</span> <span>创建人:${documents.creatorname}</span>
									<span>创建时间:${documents.createdatetime}</span>
								</div>
							</div>
						</c:if>
					</c:forEach>
				</c:if>
			</div>
			<!-- 分页条 -->
			<div class="ps_search_result_paging">
				<ul class="clear" id="Pagination">
				</ul>
			</div>
		</div>
	</div>
	<script>
		wordlimit("article", 150);
	</script>
	<!-- 高级检索对话框 -->
	<div id="advanceSearchDialog"
		style="display: none; padding-left: 40px;">
		<form id="advSearchForm" action="${ctx}/api/search/search" method="post">
			<center>
				<table width="100%" class="ui-widget ui-widget-content">
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
								<option value="search">全部</option>
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
						<td><label for="dn">部门：</label></td>
						<td style="text-align: left;"><input type="text"
							class="text ui-widget-content ui-corner-all" id="dn"
							name="dn" /></td>
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
			<input type="hidden" id="advSearchKeyWord" name="query" /> <input
				type="hidden" id="advSearchQParams" name="filterQueryParams" value="" />
		</form>
	</div>
</body>
</html>