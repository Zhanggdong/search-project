//耗时
var _QTime = 0;
//总条数
var _CountRows = 0;
//每页显示10条
var _pageSize = 10;
function data(keyWords,dataStart,QParams){
	if($.trim(keyWords) == "*"){
		keyWords = " ";
	}else{
		keyWords = keyWords + " AND ";
	}
	$.ajax({
		  type: 'POST',
		  url: 'http://localhost:8080/filecube/search/searchByKeyword?keyWords=%E7%BD%97%E6%B9%96',
		  data: {
			  q:keyWords + " entityType:file " + (QParams == '' ? '' : (' AND ' + QParams)),
			  start:dataStart,
			  rows:_pageSize,
			  wt:'json',			
			  //高亮参数
			  'hl.fl':'name,keyWords,content',
			  fl:'name,type,createdate,deleted,description,extension,directory,id,year,downLoadTimes,openTimes,kind,departmentName,creatorName,relevanceFileUids,attachmentUids',
			  hl:true,
			  'hl.fragsize':150,
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
			  //统计结果内容
			  var facets = data.facet_counts.facet_fields;
			  //拼统计结果内容
			  facetContent(facets);
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
		  str += '<table class="result" cellpadding="0" cellspacing="0" style="margin-left: 15px;" width="800px">';
		  str +=  '	<tbody>';
		  str +=  '		<tr>';
		  str +=  '			<td class="f">';
		  str +=  '				<h3 class="t">';
		  //图片
		  str +=  '<img src="/filecube/images/file/small/files/' + docs[i].extension + '.gif"  style="vertical-align:middle;margin-right:3px;"/>';
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
		  str +=  '<div style="margin-top:7px;">描述：<font style="color:#666666">' + checkValue(docs[i].description) + ' -- </font></div> ';
		  str +=  "</div>";
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
		  str +=  ' &nbsp;&nbsp;下载次数：' + ((docs[i].downLoadTimes == undefined) ? 0 : docs[i].downLoadTimes);
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


function facetSearch(facetType){	
	if(_QParams == ""){
		_QParams += facetType; 
	}else{
		_QParams += " AND " + facetType;
	}	
	
	$('#facetSearchParam').val(_QParams);
	$('#facetSearchForm').submit();
}

/**
 * 统计结果内容
 * @param facets 
 */
function facetContent(facets){		
	//文件类型	
	$('#fileTypeGroupUL').html('');
	var fileTypes = facets.type;
	var tmp = "";
	for(var i = 0;i < fileTypes.length / 2;i++){
		var facetCount = fileTypes[2 * i + 1];
		if(facetCount == 0) continue;
		tmp += '<li onclick=facetSearch("type:' + fileTypes[2 * i].toLowerCase() + '") > ' + eval('_filetype.' + fileTypes[2 * i].toLowerCase()) + ' ( ' + facetCount + ' ) </li>';
	}
	$('#fileTypeGroupUL').append(tmp);
	
	//文件扩展名	
	var fileExtensions = facets.extension;	
	$('#fileExtensionGroupUL').html('');
	tmp = "";
	for(var i = 0;i < fileExtensions.length / 2;i++){
		var facetCount = fileExtensions[2 * i + 1];
		if(facetCount == 0) continue;
		tmp += '<li onclick=facetSearch("extension:' + fileExtensions[2 * i] + '") > ' + fileExtensions[2 * i] + ' ( ' + facetCount + ' ) </li>';
	}
	$('#fileExtensionGroupUL').append(tmp);
	
	//文件所属部门
	var fileDepartmentNames = facets.departmentName;
	$('#fileDepartmentNameGroupUL').html('');
	tmp = "";
	for(var i = 0;i < fileDepartmentNames.length / 2;i++){
		var facetCount = fileDepartmentNames[2 * i + 1];
		if(facetCount == 0) continue;
		tmp += '<li onclick=facetSearch("departmentName:' + fileDepartmentNames[2 * i] + '") > ' + fileDepartmentNames[2 * i] + ' ( ' + facetCount + ' ) </li>';
	}
	$('#fileDepartmentNameGroupUL').append(tmp);
	
	//文件所属年度
	var fileYears = facets.year;
	$('#fileYearGroupUL').html('');
	tmp = "";
	for(var i = 0;i < fileYears.length / 2;i++){
		var facetCount = fileYears[2 * i + 1];
		if(facetCount == 0) continue;
		tmp += '<li onclick=facetSearch("year:' + fileYears[2 * i] + '") > ' + fileYears[2 * i] + ' ( ' + facetCount + ' ) </li>';
	}
	$('#fileYearGroupUL').append(tmp);
	
	//文种
	var fileKinds = facets.kind;
	$('#fileKindGroupUL').html('');
	tmp = "";
	for(var i = 0;i < fileKinds.length / 2;i++){
		var facetCount = fileKinds[2 * i + 1];
		if(facetCount == 0) continue;
		tmp += '<li onclick=facetSearch("kind:' + fileKinds[2 * i] + '") > ' + fileKinds[2 * i] + ' ( ' + facetCount + ' ) </li>';
	}
	$('#fileKindGroupUL').append(tmp);
	//左侧统计区域
	$( "#accordionFacet" ).accordion({
		event: "click hoverintent"
	});
}

//文件类型
var _filetype = {
	"document":"文档",
	"image":"图片",
	"video":"视频",
	"audio":"音乐",
	"other":"其它"
}


//分页回调函数
function pageselectCallback(start, jq){
	alert(2);
	var cur_pageStart = start * 10;	
	location.href = "/fulltext/search_preCommonsStatisticsList.action?keyWords=" + _List_searchKeyWords + '&QParams=' + encodeURIComponent(_QParams)
	+ '&dataStart=' + cur_pageStart + '&pageStart=' + start;
}

var cfg = ($.hoverintent = {
	sensitivity: 7,
	interval: 100
});

$.event.special.hoverintent = {
	setup: function() {
		$( this ).bind( "mouseover", jQuery.event.special.hoverintent.handler );
	},
	teardown: function() {
		$( this ).unbind( "mouseover", jQuery.event.special.hoverintent.handler );
	},
	handler: function( event ) {
		var that = this,
			args = arguments,
			target = $( event.target ),
			cX, cY, pX, pY;

		function track( event ) {
			cX = event.pageX;
			cY = event.pageY;
		};
		pX = event.pageX;
		pY = event.pageY;
		function clear() {
			target
				.unbind( "mousemove", track )
				.unbind( "mouseout", arguments.callee );
			clearTimeout( timeout );
		}
		function handler() {
			if ( ( Math.abs( pX - cX ) + Math.abs( pY - cY ) ) < cfg.sensitivity ) {
				clear();
				event.type = "hoverintent";
				// prevent accessing the original event since the new event
				// is fired asynchronously and the old event is no longer
				// usable (#6028)
				event.originalEvent = {};
				jQuery.event.handle.apply( that, args );
			} else {
				pX = cX;
				pY = cY;
				timeout = setTimeout( handler, cfg.interval );
			}
		}
		var timeout = setTimeout( handler, cfg.interval );
		target.mousemove( track ).mouseout( clear );
		return true;
	}
};