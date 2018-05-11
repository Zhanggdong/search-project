<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<style>

.inputLable{	
	text-align:right;
	vertical-align: middle;	
}

.inputTxt{
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

.inputSelect{
	display:block;
	width: 296px;
    height: 25px;    
    padding: 4px 4px;
    line-height: 25px;
}

.advanceSearchDialogTr{
	height: 40px;
    line-height: 40px;
}

</style>
<script type="text/javascript">
//文种信息，从后台获取
var _kinds;
//部门信息，从后台获取
var _departMents;
$(function(){	 
//	getDepartAndKind();
	$( "#createdate_startDate" ).datepicker({
		dateFormat:'yy-mm-dd',
		regional:'zh-CN'
	});
	$( "#createdate_endDate" ).datepicker({
		dateFormat:'yy-mm-dd',
		regional:'zh-CN'
	});
});
/**
* 获取部门与文种信息
*/
function getDepartAndKind(){
	$.ajax({
		  type: 'POST',
		  url: '<%=path%>/select?facet.field=kind&facet.field=departmentName',
		  dataType: 'JSON',
		  data:{
			  facet:true,
			  wt:'json'
			  //q:' entityType:file ',
			  //fl:'qq'//任意值 则不出现doc，即只统计
		  },
		  async: false,      
		  success: function(data){
			  _numFound = data.response.numFound;
			  _kinds = data.facet_counts.facet_fields.kind;
			  _departMents = data.facet_counts.facet_fields.departmentName;
		  },
		  error:function(){			 
			  alert("服务器异常，请稍后再试！");
		  }			  
	});	
}

/**
* 将高级搜索的输入框重置为默认
*/
function setDeaultAdvIpt(){
	//重置关键字
	$('#dialogKeyWords').val('');	
	//重置时间段
	$('#createdate_startDate').val('');
	$('#createdate_endDate').val('');
	
	//重置部门名称
	var departNameSelect = '<option value="all">不限</option>';
	for(var i = 0 ;i < _departMents.length / 2;i++){
		var departMentName = _departMents[2 * i];		
		departNameSelect += '<option value="' + departMentName + '">' + departMentName + '</option>';
	}
	$('#departmentName').html(departNameSelect);
	
	//重置文种
	var kindSelect = '<option value="all">不限</option>';
	for(var i = 0 ;i < _kinds.length / 2;i++){
		var kind = _kinds[2 * i];		
		kindSelect += '<option value="' + kind + '">' + kind + '</option>';
	}
	$('#kind').html(kindSelect);
	
	//重置文件类型
	$('#type').val('all');
}

var _advSearchKeyWords;
/**
* 打开高级搜索对话框
*/
function advanceSearchDialog(){	
	setDeaultAdvIpt();
	$( "#advanceSearchDialog" ).dialog({
		resizable: false,
		title:'高级搜索',
		width:461,
		height:340,
		modal: true,
		buttons: {
			"搜索": function() {	
				//检查高级搜索的表单验证
				var checkRtn = checkAdvSearchForm();
				if(checkRtn == true) return;
				$('#advSearchQParamsHidden').val(checkRtn);
				$('#advSearchKeyWordsHidden').val(_advSearchKeyWords);
				$('#advSearchForm').submit();
				
				$( this ).dialog( "close" );				
			},
			'取消': function() {
				$( this ).dialog( "close" );			
			}
		}
	});
}
/**
* 检查高级搜索对话框的表单值
*/
function checkAdvSearchForm(){	
	//关键字
	var keyWords = $('#dialogKeyWords').val();
	if(keyWords.replace(/[ ]/g,"") == ""){
		//alert("关键字不能为空！");
		//return true;
		keyWords = "*";
	}
	_advSearchKeyWords = keyWords;
	keyWords = "";
	//时间段
	var startDate = $('#createdate_startDate').val();
	var endDate = $('#createdate_endDate').val();	
	if(startDate > endDate){
		alert("结束时间必须在开始时间之后！");
		return true;
	}else if(startDate != "" && endDate == ""){
		keyWords += " createdate:[" + startDate + " TO " + systemDate + "] ";		
	}else if(startDate != "" && endDate != ""){
		keyWords += " createdate:[" + startDate + " TO " + endDate + "] ";		
	}else if(startDate == "" && endDate != ""){
		keyWords += " createdate:[2012-01-01 TO " + endDate + "] ";		
	}
	
	//部门名称
	var departmentName = $('#departmentName').val();
	if(!(departmentName == 'all')){	
		if(keyWords == ""){
			keyWords += " departmentName:" + departmentName;
		}else{
			keyWords += " AND departmentName:" + departmentName;
		}		
	}
	
	//文种
	var kind = $('#kind').val();
	if(!(kind == 'all')){		
		if(keyWords == ""){
			keyWords += " kind:" + kind;
		}else{
			keyWords += " AND kind:" + kind;
		}		
	}
	
	//文件类型
	var type = $('#type').val();
	if(!(type == 'all')){	
		if(keyWords == ""){
			keyWords += " type:" + type;
		}else{
			keyWords += " AND type:" + type;
		}		
	}	
	return keyWords;
}

</script>

<!-- 高级检索对话框 -->
<div id="advanceSearchDialog" style="display: none;padding-left: 40px;padding-top: 20px;">
<center>
	<table width="100%">
		<tr class="advanceSearchDialogTr">
			<td class="inputLable">关键字：</td>
			<td style="text-align: left;"><input type="text" class="inputTxt" id="dialogKeyWords"/></td>
		</tr>		
		<tr class="advanceSearchDialogTr">
			<td class="inputLable">时间：</td>
			<td style="text-align: left;">
				<input type="text" class="inputTxt" style="width: 132px"
				readonly="readonly" id="createdate_startDate"/>
				至
				<input type="text" class="inputTxt" style="width: 132px" 
				readonly="readonly" id="createdate_endDate"/>
			</td>
		</tr>
		<tr class="advanceSearchDialogTr">
			<td class="inputLable">部门：</td>
			<td style="text-align: left;">
				<select class="inputSelect" id="departmentName">
					<option value="all">不限</option>
				</select>
			</td>
		</tr>
		<tr class="advanceSearchDialogTr">
			<td class="inputLable">文种：</td>
			<td style="text-align: left;">
				<select class="inputSelect" id="kind">
					<option value="all">不限</option>
				</select>
			</td>
		</tr>
		<tr class="advanceSearchDialogTr">
			<td class="inputLable">文件类型：</td>
			<td style="text-align: left;">
				<select class="inputSelect" id="type">
					<option value="all">不限</option>
					<option value="document">文档</option>
					<option value="image">图片</option>
					<option value="video">视频</option>
					<option value="audio">音乐</option>
				</select>
			</td>
		</tr>
	</table>
</center>
<form action="<%=path%>/${param.prePostUrl}" method="post" id = "advSearchForm">
	<input type="hidden" name="keyWords" id="advSearchKeyWordsHidden"/>
	<input type="hidden" name="QParams" id="advSearchQParamsHidden"/>
</form>
</div>	
