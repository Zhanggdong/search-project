var _QTime = 0;
var _CountRows = 0;
var rows = 10;
function data(keyWords, pageStart, QParams) {
  var qstr = "";
  if ($.trim(keyWords) == "*" || $.trim(keyWords) == "") {
    qstr = "*:*";
  } else {
    qstr = "searchStr:" + keyWords;
  }
  $.ajax({
    type : 'POST',
    url : contextPath + '/query/solrData',
    data : {
      q : qstr + _globalCondition,
      QParams : QParams,
      start : pageStart * rows,
      rows : rows,
      wt : 'json',
      hl : true,
      'hl.fl' : 'title,text',
      'hl.snippets' : 2,
      'hl.mergeContiguous' : true,
      'hl.fragsize' : 80,
      // sort : 'createDate desc'
      facet : true,
      'facet.field' : 'biztypename'
    },
    dataType : 'JSON',
    success : function(data) {
      _QTime = data.responseHeader.QTime;
      _CountRows = data.response.numFound;
      var docs = data.response.docs;
      var fqStr = data.responseHeader.params.fq;
      resultContent(keyWords, data, docs);
      $('#resultCount').html('找到  ' + _CountRows + '  条结果（用时  ' + _QTime / 1000 + '&nbsp;秒）');
      var facets = data.facet_counts.facet_fields;
      facetContent(fqStr, facets);
    },
    error : function() {
      alert('查找失败！');
    }
  });

}

/**
 * 拼内容
 * 
 * @param data --
 *          返回的数据
 * @param docs --
 *          返回的文档
 */
function resultContent(keyWords, data, docs) {
  $('#resultData').html('');
  var str = '';
  for (var i = 0; i < docs.length; i++) {
    str += '<table class="result" border="0" cellpadding="0" cellspacing="0" style="margin-bottom: 15px;" width="650px">';
    str += '	<tbody>';
    str += '		<tr>';
    str += '			<td><div class="title">';
    if (docs[i].isfile == 1) {
      str += '<span class="ic ic-' + docs[i].extension.toLowerCase() + '"></span>';
      str += '<a onmousedown="" href=javascript:viewdocument("' + docs[i].id + '",' + docs[i].isfile + ',' + docs[i].biztype + ') >';
    } else {
      str += '<a onmousedown="" href=javascript:openform("' + docs[i].id + '","' + docs[i].bizguid + '",' + docs[i].biztype + ') >';
    }
    // 高亮名称
    var titleStr = "";
    var fileNameHlt = eval('data.highlighting[\"' + docs[i].id + '\"].title');
    if (fileNameHlt == undefined) {
      titleStr = docs[i].title;
    } else {
      titleStr = fileNameHlt;
    }
    if (titleStr.length > 30) {
      str += titleStr.substr(0, 30) + "...";
    } else {
      str += titleStr;
    }
    str += '         </a></div></td></tr>';
    str += '        <tr><td> ';
    str += '         <div class="summary l21">';
    var hlContent = eval('data.highlighting[\"' + docs[i].id + '\"].text');
    str += checkValue(hlContent) + (hlContent != null && hlContent.length > 0 ? "..." : "");

    str += '			</div></td>';
    str += '		</tr>';
    str += '		<tr>';
    str += '			<td>';
    str += '					<div class="detail l21" style="display:block"><span>部门：' + checkValue(docs[i].bureauname);
    str += ' &nbsp;&nbsp;创建者：' + checkValue(docs[i].creatorname);
    str += ' &nbsp;&nbsp;创建时间：' + docs[i].createdate;
    str += ' </span>';
    if (docs[i].isfile == 1) {
      str += '<span class="fr"><a href="#" onclick="openform(\'' + docs[i].id + '\',\'' + docs[i].bizguid + '\',' + docs[i].biztype + ');" >打开原件</a></span>';
    }
    str += '</div>';
    str += '			</td>';
    str += '		</tr>';
    str += '	</tbody>';
    str += '</table>';

  }
  $('#resultData').append(str);
 
}

function fmtDate(value) {
  var d = new Date(value);
  return d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
}

// 检查值是不是undefined，是返回空，否则返回原值
function checkValue(value) {
  if (undefined == value) {
    return "";
  } else {
    return value;
  }
}

function filterQuery(facetfield, facetvalue) {
  var fqArr = _QParams.split("&fq=");
  var temp = "";
  var count = 0;
  for (var i = 0; i < fqArr.length; i++) {
    if (fqArr[i] == "") {
      continue;
    }
    if (fqArr[i].indexOf(facetfield) > -1) {
      count++;
      if (facetvalue != "") {
        if (facetvalue.indexOf("{") > -1) {
          temp += "&fq=" + facetfield + ":\"" + facetvalue + "\"";
        } else {
          temp += "&fq=" + facetfield + ":" + facetvalue + "";
        }
      }
    } else {
      temp += "&fq=" + fqArr[i];
    }
  }
  if (count < 1) {
    temp += "&fq=" + facetfield + ":" + facetvalue + "";
  }
  $('#facetQueryParam').val(temp);
  $('#facetQueryForm').submit();
}

// 统计结果内容
function facetContent(fq, facets) {
  $('#s_tab').html('');
  var biztypename = facets.biztypename;
  var tmp = "";
  var typename = "";
  if (fq != null) {
    for (var j = 0; j < fq.length; j++) {
      if (fq[j].indexOf('biztypename') > -1) {
        typename = fq[j].split(":")[1];
      }
    }
  }
  if (typename != "") {
    tmp += '<li><a href="#" onclick="filterQuery(\'biztypename\',\'\')">全部</a></li>';
  } else {
    tmp += '<li><b>全部</b></li>';
  }

  for (var i = 0; i < biztypename.length / 2; i++) {
    tmp += '<li>';
    if (biztypename[2 * i] == typename) {
      tmp += '<b>' + biztypename[2 * i] + '</b>';
    } else {
      tmp += '<a href="#" onclick="filterQuery(\'biztypename\',\'' + biztypename[2 * i] + '\')">' + biztypename[2 * i] + '</a>';
    }

    tmp += '</li>'
  }
  $('#s_tab').append('<ul>' + tmp + '</ul>');
}

/**
 * 提交表单
 * 
 * @returns
 */
function submitForm() {
  var text = $('#searchText').val();
  text = text.replace(/[ ]/g, "");
  if (text == "") {
    return;
  }
  if (text == '*') {
    location.href = contextPath + "/query/query?keyWord=" + encodeURIComponent($('#searchText').val()) + "&QParams=" + encodeURIComponent(_QParams);
  } else {
    location.href = contextPath + "/query/query?keyWord=" + encodeURIComponent($('#searchText').val());
  }
}

// 分页回调函数
function pageselectCallback(pageStart, jq) {
  alert(3);
  var dataStart = pageStart * rows;
  //location.href = contextPath + "/query/query?keyWord=" + encodeURIComponent(_List_searchKeyWords) + '&QParams=' + encodeURIComponent(_QParams) + '&pageStart=' + pageStart + '&rows=' + rows;
}

function openfile(guid) {
  $.post(contextPath + '/query/checkPermission', {
    'guid' : guid
  }, function(data) {
    if (data) {
      alert('将在OA中打开此文件！');
    } else {
      alert('对不起，您没有查看该文件的权限！');
    }
  });
}

var cfg = ($.hoverintent = {
  sensitivity : 7,
  interval : 100
});

$.event.special.hoverintent = {
  setup : function() {
    $(this).bind("mouseover", jQuery.event.special.hoverintent.handler);
  },
  teardown : function() {
    $(this).unbind("mouseover", jQuery.event.special.hoverintent.handler);
  },
  handler : function(event) {
    var that = this, args = arguments, target = $(event.target), cX, cY, pX, pY;
    function track(event) {
      cX = event.pageX;
      cY = event.pageY;
    }
    pX = event.pageX;
    pY = event.pageY;
    function clear() {
      target.unbind("mousemove", track).unbind("mouseout", arguments.callee);
      clearTimeout(timeout);
    }
    function handler() {
      if ((Math.abs(pX - cX) + Math.abs(pY - cY)) < cfg.sensitivity) {
        clear();
        event.type = "hoverintent";
        event.originalEvent = {};
        jQuery.event.handle.apply(that, args);
      } else {
        pX = cX;
        pY = cY;
        timeout = setTimeout(handler, cfg.interval);
      }
    }
    var timeout = setTimeout(handler, cfg.interval);
    target.mousemove(track).mouseout(clear);
    return true;
  }
};