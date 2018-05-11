<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="net.risesoft.filecube.util.Constant"%>
<%
	String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
  pageContext.setAttribute("ctx", path);
  int pageStart = StringUtils.isNotBlank(request.getParameter("pageStart")) ? Integer.parseInt(request.getParameter("pageStart")) : 0;
  int rows = StringUtils.isNotBlank(request.getParameter("rows")) ? Integer.parseInt(request.getParameter("rows")) : 10;
  pageContext.setAttribute("keyWord", request.getParameter("keyWord"));
  pageContext.setAttribute("searchType", request.getParameter("searchType"));
  pageContext.setAttribute("QParams", request.getParameter("QParams"));
  pageContext.setAttribute("pageStart", pageStart);
  pageContext.setAttribute("rows", rows);
  pageContext.setAttribute("serverip",Constant.serverip);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>智慧检索</title>
<script type="text/javascript" src="${ctx}/commons/javascript/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/jquery/ui/jquery-ui-1.9.1.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/pagination/pagination.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/suggest/kissy-min.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/suggest/suggest-min.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/jquery-plugin/jquery-dateFormat.min.js"></script>
<script type="text/javascript" src="${ctx}/filecube/js/permission.js"></script>
<%-- <link href="${ctx }/commons/javascript/qui-v3.3/libs/css/import_basic.css" rel="stylesheet" type="text/css" /> --%>
<%-- <link rel="stylesheet" type="text/css" id="skin" prePath="${ctx }/commons/javascript/qui-v3.3/" /> --%>
<!-- <link rel="stylesheet" type="text/css" id="customSkin" /> -->
<link href="${ctx }/filecube/css/index.css" rel="stylesheet" type="text/css"/>
<%-- <link type="text/css" href="${ctx}/commons/javascript/qui-v3.3/system/modern_accordion/skin/style.css" rel="stylesheet" id="skin" skinPath="${ctx}/commons/javascript/qui-v3.3/system/modern_accordion/skin/" />
<link rel="stylesheet" href="${ctx}/commons/javascript/jquery/ui/css/flick/jquery-ui-1.9.1.custom.min.css" />
<link rel="stylesheet" href="${ctx}/commons/javascript/suggest/reset-grids-min.css" />
<link rel="stylesheet" href="${ctx}/commons/javascript/pagination/pagination.css" /> --%>
<%-- <link rel="stylesheet" href="${ctx}/filecube/css/list.css" /> --%>
<%-- <link rel="stylesheet" href="${ctx}/commons/javascript/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css" /> --%>
<script type="text/javascript" src="${ctx}/commons/javascript/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript">
	var serverip = "${serverip}";
    var contextPath = "${ctx}";
    var _globalCondition = "${folderLevels}";
    var numFound = parseInt('${numFound}');
    var pageStart = parseInt('${pageStart}');
    var rows = 10;
    
    $(function() {
    	$("#searchId").children("li").each(function(){
    		var currentId = $(this).attr("id");
    		if(currentId == "${searchType}"){
    			$(this).addClass("ps_search_result_tab_sel");
    		}
    	});
        var ver;
        var bType;
        var vNumber;
        ver = navigator.appVersion;
        bType = navigator.appName;
        if (bType == "Microsoft Internet Explorer") {
            vNumber = parseFloat(ver.substring(ver.indexOf("MSIE") + 5, ver.lastIndexOf("Windows")));
            if (vNumber != 6.0) {
                $('.topTools').after('<div class="topPadding"></div>');
            }
        } else {
            $('.topTools').after('<div class="topPadding"></div>');
        }

        $('#biztype').change(function() {
            if ($(this).children('option:selected').val() == 1) {
                $("#fileType").removeAttr("disabled");
                $("#fileSource").removeAttr("disabled");

                var typeSelect = '<option value="all">不限</option>';
                $.getJSON(contextPaht + '/search/getAppList', function(data) {
                    if (data) {
                        $.each(data, function(index) {
                            typeSelect += '<option value="' + data[index][0] + '">' + data[index][1] + '</option>';
                        });
                        $('#fileType').html(typeSelect);
                    }
                });

                var sourceSelect = '<option value="all">不限</option><option value="repository">来文</option><option value="workflow">其他资料</option>';
                $('#fileSource').html(sourceSelect);
            } else {
                $("#fileType").attr("disabled", "disabled");
                $("#fileSource").attr("disabled", "disabled");
            }
        });
        

        $("#Pagination").pagination(numFound, {
            num_edge_entries : 2,
            num_display_entries : 5,
            callback : pageselectCallback,
            current_page : pageStart,
            items_per_page : 10
        });
    });
    
    function pageselectCallback(pageStart, jq) {
        var dataStart = pageStart * rows;
        location.href = contextPath + "/search/search?keyWord=" + encodeURIComponent(_List_searchKeyWords) + '&QParams=' + encodeURIComponent(_QParams) + '&pageStart=' + pageStart + '&rows=' + rows;
    }

    var _List_searchKeyWords = '${keyWord}';
    var _QParams = '${QParams}';

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
    }

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
	function searchByType(type){
	 var text = $('#searchText').val();
            text = text.replace(/[ ]/g, "");
        if (text == "") {
       		alert("请输入查询关键字");
            return;
        }else{
		location.href = contextPath + "/search/search?searchType="+ type+"&keyWord=" + encodeURIComponent($('#searchText').val());
	}
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

    function submitForm() {
        var text = $('#searchText').val();
            text = text.replace(/[ ]/g, "");
        if (text == "") {
       		alert("请输入查询关键字");
            return;
        }
        if (text == '*') {
            location.href = contextPath + "/search/search?keyWord=" + encodeURIComponent($('#searchText').val()) + "&QParams=" + encodeURIComponent(_QParams);
        } else {
            location.href = contextPath + "/search/search?keyWord=" + encodeURIComponent($('#searchText').val());
        }
    }

    function openfile(guid) {
        $.post(contextPath + '/search/checkPermission', {
            'guid' : guid
        }, function(data) {
            if (data) {
                alert('将在OA中打开此文件！');
            } else {
                alert('对不起，您没有查看该文件的权限！');
            }
        });
    }
    
    function filterQuery(fqField, fqValue) {
        var QParams = $("#filterQueryParam").val();
        var fqArr = QParams.split("&fq=");
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
      data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
          onClick: this.onClick
    }
  };
  var zNodes =[<%=request.getAttribute("orgtree")%>];
  
  function onClick(e, treeId, node) {
      filterQuery("bureauguid", node.id);
  }
  
  $(document).ready(function(){
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
  });
  
  //正文内容截取
  
  function csubstr(len){
	var str=document.getElementById("description").value;
	 if(str.length>150){
       return str.substring(0,len)+"...";
        }else{
       return str;
      }
  };
  //加载正文
   function showZw(documentRowid,filename,filetype,instanceGUID){
       layer.closeAll();
       var id = instanceGUID.substring(1,instanceGUID.length-1);
       var left=null;
	   var url = "";
	   if(filetype=='zw'){
	     
		    url = "http://10.0.0.109:8088/"+"riseoffice/OfficeTagDownloadServlet?instanceGUID="+instanceGUID+"&DocumentRowGUID="+documentRowid;
	       
	   }else if(filetype='fj'){
		url = "http://10.0.0.109:8088/"+"servlet/RisefileViewData1?fileName="+filename+"&fileGUID="+documentRowid;
	}
    
	    url ="http://10.0.0.81:8080"+"/view/url?url="+encodeURIComponent(url)+"&name="+encodeURIComponent(filename)+"&token=d3hrai5zempzLmdvdi5jbg==";
	  
	$.layer({
	      type: 2,
          shadeClose: false,
          title: '正文',
          closeBtn: [0, true],
          shade: [0.8, '#000'],
          border: [0],
          maxmin:true,
          fixed:false,
          resize:false,
          offset: ['20px',''],
          area: ['1000px', '500px'],
          iframe: {src: url}
	});
  }
</script>
</head>
<body>
<div class="mainContain">
    <div class="ps_search_enter">
        <div class="searchFM">
        	<img alt="icon" src="${ctx }/filecube/images/pingshan-icon.png"/>
            <span><input type="text" name="QParams" id="searchText" value="${keyWord == '*' ? '' : keyWord}"/> </span>
            <span>
            	<input id="QParamsHiddenIpt" class="searchIpt" type="submit" value="搜索" onclick="submitForm()" name="keyWord" maxlength="150" value="${keyWord == '*' ? '' : keyWord}" />
            </span>
          </div>
    </div>
    <div class="ps_search_result">
        <!-- 搜索分类选项-->
        <div class="ps_search_result_tab">
            <ul class="clear" id="searchId">
                <li id="searchStr" class="fl pointer" onclick="searchByType('searchStr')">全部</li>
                <li id="biaoti" class="fl pointer" onclick="searchByType('biaoti')">标题</li>
                <li id="comment" class="fl pointer" onclick="searchByType('comment')">意见</li>
                <li id="banwenbianhao" class="fl pointer" onclick="searchByType('banwenbianhao')">办文编号</li>
                <li id="laiwendanwei" class="fl pointer" onclick="searchByType('laiwendanwei')">来文单位</li>
                <li id="zwtitle" class="fl pointer" onclick="searchByType('zwtitle')">正文标题</li>
                <li id="fjname" class="fl pointer" onclick="searchByType('fjname')">附件标题</li>
            </ul>
        </div>
        <div class="ps_search_result_tip"><p>找到${requestScope.numFound}条搜索结果（用时${requestScope.qTime}毫秒）</p></div>
        <!-- 搜索结果内容-->
        <!-- 1 -->

        <div id="ps_search_result_con">
          <c:choose>
            <c:when test="${not empty requestScope.fileList }">
              <c:forEach items="${requestScope.fileList}" var="file" varStatus="stat">
              	<div class="ps_search_result_con">
			        <c:choose>
	                   <c:when test="${file.zwid=='null'}">
	                       <p class="elips" onclick="viewdocument('${file.id}')">${file.biaoti}</p>        
	                   </c:when>
	                   <c:otherwise>       
	                       <p class="elips" onclick="showZw('${file.zwid}','${file.zwtitle}','zw','${file.id}');">${file.biaoti}</p>                
	                      
	                   </c:otherwise>
	                </c:choose> 
		            <div>	      
		            <!--以附件形式存取的正文-->
		           <!--<p>${file.fjlist}</p>
		               <c:forEach var="fjlist" items="${file.fjlist}">
		                     <c:if test="${fjlist.fjappname=='zw'}">
		                      <p>${fjlist}</p>
		                       <p>${fjlist.fjid}</p>
		                     </c:if>
                       </c:forEach>-->
                      <span>部门:${file.dept_shortdn}</span> <span>创建人:${file.creatorname}</span> <span>创建时间:${file.createdate }</span> 
		            </div>
		             <c:choose>
                       <c:when test="${file.description=='null'}">
                            <div> <span class="description"></span> </div>                                    
                       </c:when>
                       <c:otherwise>       
                           <div style="margin-top:3px;">
                                <input type="hidden" id="description"value="${file.description}"/>
                                <span><script>document.write(csubstr(100));</script></span>
                       
                          </div>  
                           
                       </c:otherwise>
                    </c:choose>
        		</div>
        	
              </c:forEach>
             </c:when>
            <c:otherwise>
            </c:otherwise> 
            </c:choose>
        </div>
        <!-- 分页条 -->
        <div class="ps_search_result_paging" >
            <ul class="clear" id="Pagination">
            </ul>
        </div>
    </div>
</div>
<!--以附件形式存取的正文-->
<!--<p>${file.fjlist}</p>
    <c:forEach var="fjlist" items="${file.fjlist}">
       <c:if test="${fjlist.fjappname=='zw'}">
          <p>${fjlist}</p>
          <p>${fjlist.fjid}</p>
       </c:if>
    </c:forEach>-->
<!--截取正文-->
<!-- <input type="hidden" id="description"value="${file.description}"/>
<p class="article"><script>document.write(csubstr(100));</script></p>
<div><input class="fr" type="button" value="打开原件" onclick="viewdocument('${file.id}')" style="cursor:pointer;"/>
</div>
--> 
</body>
</html>