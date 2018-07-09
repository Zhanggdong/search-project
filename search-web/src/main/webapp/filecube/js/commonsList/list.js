//耗时
var _QTime = 0;
//总条数
var _CountRows = 0;
//每页显示5条
var _pageSize = 10;
//查询参数
var _QParams = "";
function data(keyWords,dataStart,QParams){		
	if($.trim(keyWords) == "*"){
		keyWords = " ";
	}else{
		keyWords = keyWords + " AND ";
	}
	$.ajax({
		  type: 'POST',
		  url: '/fulltext/select?facet.field=type&facet.field=extension&facet.field=year&facet.field=kind&facet.field=departmentName',
		  data: {
			  q:keyWords + " entityType:file " + (QParams == '' ? '' : (' AND ' + QParams)),
			  start:dataStart,
			  rows:_pageSize,
			  wt:'json',			
			  //高亮参数   当关键字为空或为所有时 ，高亮字符串为空
			  'hl.fl': 'name,keyWords,content',
			  fl:'name,type,createdate,deleted,description,extension,directory,id,year,downLoadTimes,openTimes,kind,departmentName,creatorName,relevanceFileUids,attachmentUids',
			  hl:true,
			  'hl.fragsize':100,
			  sort:'createdate desc',
			  //分组统计参数
			  facet:true
		  },
		  dataType: 'JSON',
		  success: function(data){			  
			  //获取耗时
			  _QTime = data.responseHeader.QTime;
			  //获取总数
			  _CountRows = data.response.numFound;			 
			  //获取文档
			  var docs = data.response.docs;
			  //搜索结果内容
			  resultContent(data,docs);
			  //结果数
			  $('#resultCount').html('找到  ' + _CountRows + '  条结果（用时  ' + _QTime + '&nbsp;毫秒）');
			 
		  },
		  error:function(){
			  alert('查找失败！');
		  }			  
	});	
}
/**
 * 拼内容
 * @param data -- 返回的数据
 * @param docs -- 返回的文档
 */
function resultContent(data,docs){
	  $('#resultData').html('');
	  //结果字符串
	  var str = '';
	  for(var i = 0;i < docs.length;i++){		  
		  str += '<table class="result" cellpadding="0" cellspacing="0" style="margin-left: 10px;padding-right: 15px;" width="100%">';
		  str +=  '	<tbody>';
		  str +=  '		<tr>';
		  str +=  '			<td class="f">';
		  str +=  '				<h3 class="t">';
		  //图片
		  str +=  '<img src="/filecube/images/file/small/files/' + docs[i].extension + '.gif" style="vertical-align:middle;margin-right:3px;"/>';
		  str +=  '<a onmousedown="" href=javascript:preViewdocument("' + docs[i].id + '") >';
		  //高亮名称		   
		  var fileNameHlt = eval('data.highlighting[\"' + docs[i].id + '\"].name');
		  if(fileNameHlt == undefined){
			  str += docs[i].name ;
		  }else{
			  str += fileNameHlt;
		  }		 
		  str +=  '					</a>';
		  str +=  '				</h3>';
		  str +=  '				<span style="font-family:arial;font-size: 13px;"> ';
		  str +=  '					<div style="margin-top:7px;">';
		  var hlContent = eval('data.highlighting[\"' + docs[i].id + '\"].content');
		  str +=  checkValue(hlContent) ;
		  var hlKeyWords = eval('data.highlighting[\"' + docs[i].id + '\"].keyWords')
		  str +=  checkValue(hlKeyWords) + '…';
		  
		  //描述
		  //str +=  '<div style="margin-top:7px;">描述：<font style="color:#666666">' + checkValue(docs[i].description) + ' -- </font></div> ';
		  //str +=  "</div>";
		  //附件
		  str +=  '<div style="margin-top:7px;" id="' + docs[i].id + '_attDiv" >'
		  getAttachmentByIds(docs[i].id,docs[i].attachmentUids);		  
	      str +=  '</div>';
	      //关联文件
		  str +=  '<div style="margin-top:7px;" id="' + docs[i].id + '_relfDiv" >'
		  getrelevanceFileByIds(docs[i].id + '_relfDiv',docs[i].relevanceFileUids);		  
	      str +=  '</div>'
		  //路径、创建时间
		  str +=  '					<div class="g">部门：' + checkValue(docs[i].departmentName);
		  str +=  ' &nbsp;&nbsp;创建者：' + checkValue(docs[i].creatorName);
		  str +=  ' &nbsp;&nbsp;创建时间：' + docs[i].createdate;
		  str +=  ' <br/>下载次数：' + ((docs[i].downLoadTimes == undefined) ? 0 : docs[i].downLoadTimes);
		  str +=  ' &nbsp;&nbsp;浏览次数：' + ((docs[i].openTimes == undefined) ? 0 : docs[i].openTimes);
		  str +=  '</div>';
		  
		  str +=  '				</span>';
		  str +=  '			</td>';
		  str +=  '		</tr>';
		  str +=  '	</tbody>';
		  str +=  '</table><br>';
		 
	  }
	  $('#resultData').append(str);
	  //分页

	  //获取起始页	 
	  $("#Pagination").pagination(_CountRows, {
		  num_edge_entries: 2,
		  num_display_entries: 5,
		  callback: pageselectCallback,
		  current_page : _pageStart,
		  items_per_page:_pageSize		  
	  });
}
/**
 * 检查值是不是undefined，是返回空，否则返回原值
 * @param value -- 需要检查的字符串
 * @returns
 */
function checkValue(value){
	if(undefined == value){
		return "";
	}else{
		return value;
	}
}
/**
 * 根据多个附件id获取附件信息 
 * @param docId			-- 将附件信息添加的层的docId + '_attDiv'
 * @param attachmentIds -- 附件的id 数组
 */
function getAttachmentByIds(docId,attachmentIds){	
	if(undefined == attachmentIds || attachmentIds == []) return;
	var tmp = "";
	for(var i = 0;i < attachmentIds.length;i++){
		tmp += "id:" + attachmentIds[i] + " ";	  
	}	
	$.ajax({
		type: 'POST',
		url: '/fulltext/select?',
		data: {
			q:tmp,
			wt:'json',
			fl:'name,id'
		},
		dataType: 'JSON',
		success: function(data){
			var attDocs = data.response.docs;
			var str = "附件（<font color=red>" + attDocs.length + "</font>）:";
			for(var i = 0;i < attDocs.length;i++){
				str += "<span  style='margin-left:10px;'>";
			    str += '<font color=red>' + (i + 1) + '</font>.';
				str += "<a href=javascript:openAttrDoc('" + docId + "','" + attDocs[i].id + "')  >";
				str += attDocs[i].name;
				str += "</a></span>";
			}
			$('#' + docId + '_attDiv').append(str);
		},
		error:function(){
			alert('查找失败！');
		}	
	});
}

/**
 * 根据多个关联文件id获取关联文件信息 
 * @param relfDiv          -- 将文件信息添加的层的id
 * @param relevanceFileIds -- 关联文件的id 数组
 */
function getrelevanceFileByIds(relfDiv,relevanceFileIds){
	if(undefined == relevanceFileIds || relevanceFileIds == []) return;
	var tmp = "";
	for(var i = 0;i < relevanceFileIds.length;i++){
		tmp += "id:" + relevanceFileIds[i] + " ";	  
	}	
	$.ajax({
		type: 'POST',
		url: '/fulltext/select?',
		data: {
			q:tmp,
			wt:'json',
			fl:'name,id'
		},
		dataType: 'JSON',
		success: function(data){
			var relfDocs = data.response.docs;
			var str = "关联文件（<font color=red>" + relfDocs.length + "</font>）:";
			for(var i = 0;i < relfDocs.length;i++){
				str += "<span  style='margin-left:10px;'>";
			    str += '<font color=red>' + (i + 1) + '</font>.';
				str += "<a href='#" + relfDocs[i].id + "'>";
				str += relfDocs[i].name;
				str += "</a></span>";
			}
			$('#' + relfDiv).append(str);
		},
		error:function(){
			alert('查找失败！');
		}	
	});
}

//分页回调函数
function pageselectCallback(start, jq){
	alert(1);
	var cur_pageStart = start * _pageSize;	
	//遍历searchId下得li，含有class为ps_search_result_tab_sel，获取这个id，表示当前是选择这个type的
	var type='biaoti';
	//location.href = "/fulltext/search_preCommonsList.action?keyWords=" + encodeURIComponent(_keyWords)
	location.href = "/query/SearchController.action?keyWords=" + encodeURIComponent(_keyWords)
	+ '&dataStart=' + cur_pageStart + '&pageStart=' + start + "&QParams=" + encodeURIComponent(_QParams)+"&searchType=" + type;
}