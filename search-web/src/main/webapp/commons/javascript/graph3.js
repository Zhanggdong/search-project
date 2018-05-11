/**第二版 加了滚动时候上面和左边不动*/
// 第1列宽度
var _g_titleColWidth=120;

// 表格数据
var _g_orginTableID = "";	// 初始表格ID
var _g_arr_totalData = new Array();
var _g_arr_totalHtml = new Array();
var _g_arr_subData = new Array();
var _g_arr_subHtml = new Array();
var _g_rowsCount = 0;
var _g_colsCount = 0;

// 排序
var _g_oldSortRow = 0;		// 记录上一次排序行
var _g_oldSortDirect = 1;	// 0升序，1降序

// 层次关系，父下标，子下标
var _g_ccgx="";
var _g_arr_ccgx = null;
var _g_ccgxCount = 0;
var _g_arrRowIsParent = new Array();
var _g_arr_oldColsWidth = new Array(0,0);

// 绘图相关
var _g_arr_offsetData = new Array();
//var _g_LineColor=new Array( '#88df76', '#58A2C7', '#e7786f');
//var _g_FillColor=new Array( '#268c1f', '#156290', '#ab2d21');
//var _g_FillColor2=new Array('#58c745', '#3BA4D1', '#d1493b');

var _g_LineColor=new Array( '#88df76', '#58A2C7', '#e7786f');
var _g_FillColor=new Array( '#268c1f', '#156290', '#ff0000');
var _g_FillColor2=new Array('#58c745', '#3BA4D1', '#e50707');
var _g_nBarWidth = 22; //32
var _g_nBarAreaHeight = 195;
var _g_nBarMaxHeight = 190;
var _g_nBarLeftJust = 4;
var _g_barContent = "";		// 内容
var _g_maxValue = 0;
var _g_minHighDrawText = 8;	// 只有8点高才写文字（百分比）
var drawrow = 1; //默认生成第1行的图形

// 行列切换
var _g_isSwitched = false;

var _g_isProcing = false;

var _oldfultablen = 0;//原始table大小
var _topnum =  1;//第1行的列数，不包括第1列
var _onelen = 66;//每列的宽度
var _isfirstload = false; //是否第一次加载
// 修复层位置
function fixDivPostion()
{
if(!_g_isProcing)
{
	scrollSync();

	drawTabBars(false);
}
	setTimeout(fixDivPostion, 200);
}

function scrollSync()
{
var oFull = document.getElementById("tab_full");
var winlen =oFull.offsetWidth;	
if(_isfirstload || _oldfultablen!=winlen)
{
	_oldfultablen = winlen;
	winlen =winlen - _g_titleColWidth;
	 _onelen = winlen/_topnum-2; //每列宽度
	 var bjlen = document.getElementById("bjtd").offsetWidth;
	 var cclen = document.getElementById("cctd").offsetWidth;
	 var cslen = document.getElementById("cstd").offsetWidth;
	 var tdlen = bjlen+cclen+cslen;
	if(_onelen<tdlen) 
		_onelen = tdlen;
	//if(_onelen<61)
	//	_onelen = 61;
	genTopTable();
	genMainTable();
	_g_oldSortRow = 0;
	selectRow(drawrow);
	_g_arr_oldColsWidth[0] = 0;		// 强制绘图
	_isfirstload = false;
}
	var ss = new Array("top", "left", "main");
	var o = null;
	var o2 = null;
	var i=0;
//try{
	for (var i=0; i<ss.length; i++)
	{
		o = document.getElementById("td_"+ss[i]);
		o2 = document.getElementById("div_"+ss[i]);
		o2.style.left = o.offsetLeft + 0;
		o2.style.top = o.offsetTop + 0;
		o2.style.width = o.offsetWidth;
		o2.style.height = o.offsetHeight;
	}
	// 滚动位置
	o_main = document.getElementById("div_"+ss[2]);	// main
	o_top = document.getElementById("div_"+ss[0]);	// top
	o_left = document.getElementById("div_"+ss[1]);	// left

	o_top.scrollLeft = o_main.scrollLeft;
	o_left.scrollTop = o_main.scrollTop;
}

// 读取表格内容到内存中
function loadOrginTabContents(idTable)
{
	_g_orginTableID = idTable;	// 设置处理的表格ID

	var oTable = document.getElementById(_g_orginTableID);
	var count=0;
	var i=0;
	var j=0;
	var k=0;

	_g_rowsCount = oTable.rows.length;		// 行数
	_g_colsCount = oTable.rows(0).cells.length;
	// 全部初始化数组，为以后行列变换
	var maxCell = _g_rowsCount;
	if(maxCell < _g_colsCount)
		maxCell = _g_colsCount;
	for (i=0; i < maxCell; i++)
	{
		_g_arr_totalData[i] = new Array();
		_g_arr_totalHtml[i] = new Array();
		_g_arr_subData[i] = new Array();
		_g_arr_subHtml[i] = new Array();
		for (j=0; j < maxCell; j++)
		{
			_g_arr_totalData[i][j] = 0;
			_g_arr_totalHtml[i][j] = "";
			_g_arr_subData[i][j] = new Array();
			_g_arr_subHtml[i][j] = new Array();
			for(k=0; k<3; k++)
			{
				_g_arr_subData[i][j][k] = 0;
				_g_arr_subHtml[i][j][k] = "";
			}
		}
	}

	for (i=0; i < oTable.rows.length; i++)
	{
		/*_g_arr_totalData[i] = new Array();
		_g_arr_totalHtml[i] = new Array();
		_g_arr_subData[i] = new Array();
		_g_arr_subHtml[i] = new Array();
		// 列数
		if(i==0)
			_g_colsCount = oTable.rows(i).cells.length;*/

		for (j=0; j < oTable.rows(i).cells.length; j++)
		{
			/*_g_arr_subData[i][j] = new Array();
			_g_arr_subHtml[i][j] = new Array();*/
			if(i==0 || j==0)	// 标题行、列
			{
				_g_arr_totalData[i][j] = oTable.rows(i).cells(j).innerText;
				_g_arr_totalHtml[i][j] = oTable.rows(i).cells(j).innerHTML;
			}
			else
			{
			
				_g_arr_totalData[i][j] = "";
				_g_arr_totalHtml[i][j] = "";
				var o = oTable.rows(i).cells(j).childNodes(0);	// Table
				if(o.rows.length>0)
				{
					_g_arr_totalData[i][j] = convertInt(o.rows(0).cells(0).innerText);
					_g_arr_totalHtml[i][j] = o.rows(0).cells(0).innerHTML;
				}
				if(o.rows.length>1)
				{
					//_g_arr_subData[i][j] = new Array();
					//_g_arr_subHtml[i][j] = new Array();
					for (var m=0; m<o.rows(1).cells.length; m++)
					{
						_g_arr_subData[i][j][m] = convertInt(o.rows(1).cells(m).innerText);
						_g_arr_subHtml[i][j][m] = o.rows(1).cells(m).innerHTML;
					}
				}
				
			}
		}
	}
}
// 隐藏表格，用于隐藏初始表格
function hideTable(idTable)
{
	var oTable = document.getElementById(idTable);
	oTable.style.display="none";
}
// 转换成整数
function convertInt(str)
{
	var ret = 0;
	ret = parseInt(str);
	if(isNaN(ret) || ret<0)
		ret = 0;
	return ret;
}
// 生成初始页面内容
function genInitContent()
{
	var titl = ""; //左上角标题
	if(_g_arr_totalHtml.length>0 && _g_arr_totalHtml[0].length>0)
		titl = _g_arr_totalHtml[0][0];
	// 生成初始框架表格

	document.body.innerHTML="<table id='tab_full' cellspacing='0' cellpadding='0' style='border:solid 0px #91c7e9; width:100%; height:100%; border-collapse:collapse; background-image:url(../images/treeico/bk.jpg); background-repeat:repeat-x;'><tr><td colspan='2' height='200px;'><div id='idDiv' name='idDiv' style='border: solid 0px #88c3eb;width:100%;height:"+(_g_nBarAreaHeight+4)+"px;'></div></td></tr><tr><th><table cellspacing='0' cellpadding='0' class='tb_left'><th class='th_0'>"+titl+"</th></table></th><td id='td_top' style='vertical-align:top' height='30px'>&nbsp;</td></tr><tr><td id='td_left' style='vertical-align:top' width='"+_g_titleColWidth+"px'>&nbsp;</td><td id='td_main' style='vertical-align:top'>&nbsp;</td></tr></table><div id='div_top' style='position:absolute;width:100px;height:30px; overflow-x:hidden; overflow-y:auto;border:solid 0px #FF0000'></div><div id='div_left'  style='position:absolute;width:100px;height:100px;overflow-y:auto; overflow-x:hidden;border:solid 0px #0000FF'></div><div id='div_main' style='position:absolute;width:100px;height:100px; overflow:scroll;background:red;border:solid 0px #008000' onscroll='scrollSync()'></div>";
	
	var i=0;
	var j=0;
	var s = "";

	// 生成行标题
	/*s = "<table cellspacing='0' cellpadding='0' class='tb_left' id='tab_left'>";
	for(i=1; i<_g_rowsCount; i++)
	{
		s+="<tr><th class='th_n'>"+_g_arr_totalHtml[i][0]+"</th></tr>"
	}
	s+="</table>";
	document.getElementById("div_left").innerHTML = s;*/
	genLeftTable();

	var oFull = document.getElementById("tab_full");
	var winlen =oFull.offsetWidth;	
	_oldfultablen = winlen;
	winlen =winlen - _g_titleColWidth;
	 _onelen = winlen/_topnum-2; //每列宽度

	//alert(_onelen);
	if(_onelen<61)
		_onelen = 61;
	// 生成列标题
	/*s = "<table cellspacing='0' cellpadding='0' class='tb_top' id='tab_top' style='table-layout: fixed'><tr>";
	for(j=1; j<_g_colsCount; j++)
	{
		s+="<th>"+_g_arr_totalHtml[0][j]+"</th>"
	}
	s+="</tr></table>";
	document.getElementById("div_top").innerHTML = s;*/
	genTopTable();

	// 生成内容
	/*s = "<table cellspacing='0' cellpadding='0' class='tb_out' id='tab_main' style='table-layout: fixed'>";
	for(i=1; i<_g_rowsCount; i++)
	{
		s+="<tr>";
		for(j=1; j<_g_colsCount; j++)
		{
			s+="<td><table cellspacing='0' cellpadding='0' class='tb_in'><tr><td colspan='3' class='td_u'>"+_g_arr_totalData[i][j]+"</td></tr><tr><td>"+_g_arr_subData[i][j][0]+"</td><td class='td_r'>"+_g_arr_subData[i][j][1]+"</td><td class='td_r'>"+_g_arr_subData[i][j][2]+"</td></tr></table></td>";
		}
		s+="</tr>";
	}
	s+="</table>";
	document.getElementById("div_main").innerHTML = s;*/
	genMainTable();
}

// 生成数据表
function genMainTable()
{
	var oLeft = document.getElementById("tab_left");
	var s = [];
	s.push("<table cellspacing='0' cellpadding='0' class='tb_out' id='tab_main' style='table-layout: fixed'>");
	for(var i=1; i<_g_rowsCount; i++)
	{
		// 判断left是否隐藏
		if(oLeft.rows(i-1).style.display=="none")
			s.push("<tr style='display:none'>");
		else
			s.push("<tr>");
		for(var j=1; j<_g_colsCount; j++)
		{
			//s+="<td><table cellspacing='0' cellpadding='0' class='tb_in'><tr><td colspan='3' class='td_u'>"+_g_arr_totalData[i][j]+"</td></tr><tr><td>"+_g_arr_subData[i][j][0]+"</td><td class='td_r'>"+_g_arr_subData[i][j][1]+"</td><td class='td_r'>"+_g_arr_subData[i][j][2]+"</td></tr></table></td>";
			//_g_arr_subHtml
			//超时超过30% 显示黄色背景
			var _zstmp = convertInt(_g_arr_totalData[i][j]); //总数
			var _cstmp = convertInt(_g_arr_subData[i][j][2]);// 超时
			var bjtd="";var cctd="";var cstd="";
			if(j==1)
			{
				bjtd="bjtd";cctd="cctd";cstd="cstd";
			}
			else
			{
				bjtd="bjtd1";cctd="cctd1";cstd="cstd1";
			}
			if(_zstmp>0 && (_cstmp/_zstmp>0.3))
			{
				s.push("<td><table cellspacing='0' cellpadding='0' class='tb_in'><tr><td colspan='3' class='td_u' style='width:"+_onelen+"px'>"+_g_arr_totalHtml[i][j]+"</td></tr><tr><td class='td_l' id='"+bjtd+"'>"+_g_arr_subHtml[i][j][0]+"</td><td class='td_m' id='"+cctd+"'>"+_g_arr_subHtml[i][j][1]+"</td><td class='td_rr' id='"+cstd+"'>"+_g_arr_subHtml[i][j][2]+"</td></tr></table></td>");
			}
			else
			{
				
				s.push("<td style='width:"+_onelen+"px'><table cellspacing='0' cellpadding='0' class='tb_in'><tr><td colspan='3' class='td_u' style='width:"+_onelen+"px'>"+_g_arr_totalHtml[i][j]+"</td></tr><tr><td class='td_l' id='"+bjtd+"'>"+_g_arr_subHtml[i][j][0]+"</td><td class='td_m' id='"+cctd+"'>"+_g_arr_subHtml[i][j][1]+"</td><td class='td_r' id='"+cstd+"'>"+_g_arr_subHtml[i][j][2]+"</td></tr></table></td>");
			}
			
		}
		s.push("</tr>");
	}
	s.push("</table>");
	document.getElementById("div_main").innerHTML = s.join("");
	
}
function genMainTable1()
{
	var oLeft = document.getElementById("tab_left");
	var s = "<table cellspacing='0' cellpadding='0' class='tb_out' id='tab_main' style='table-layout: fixed'>";
	for(var i=1; i<_g_rowsCount; i++)
	{
		// 判断left是否隐藏
		//if(oLeft.rows(i-1).style.display=="none")
			s+="<tr style='display:none'>";
		//else
			//s+="<tr>";
		for(var j=1; j<_g_colsCount; j++)
		{
			//s+="<td><table cellspacing='0' cellpadding='0' class='tb_in'><tr><td colspan='3' class='td_u'>"+_g_arr_totalData[i][j]+"</td></tr><tr><td>"+_g_arr_subData[i][j][0]+"</td><td class='td_r'>"+_g_arr_subData[i][j][1]+"</td><td class='td_r'>"+_g_arr_subData[i][j][2]+"</td></tr></table></td>";
			//_g_arr_subHtml
			//超时超过30% 显示黄色背景
			var _zstmp = convertInt(_g_arr_totalData[i][j]); //总数
			var _cstmp = convertInt(_g_arr_subData[i][j][2]);// 超时
			var bjtd="";var cctd="";var cstd="";
			if(j==1)
			{
				bjtd="bjtd";cctd="cctd";cstd="cstd";
			}
			else
			{
				bjtd="bjtd1";cctd="cctd1";cstd="cstd1";
			}
			if(_zstmp>0 && (_cstmp/_zstmp>0.3))
			{
				s+="<td style='width:"+_onelen+"px'><table cellspacing='0' cellpadding='0' class='tb_in'><tr><td colspan='3' class='td_u' style='width:"+_onelen+"px'>"+_g_arr_totalHtml[i][j]+"</td></tr><tr><td class='td_l' id='"+bjtd+"'>"+_g_arr_subHtml[i][j][0]+"</td><td class='td_m' id='"+cctd+"'>"+_g_arr_subHtml[i][j][1]+"</td><td class='td_rr' id='"+cstd+"'>"+_g_arr_subHtml[i][j][2]+"</td></tr></table></td>";
			}
			else
			{
				
				s+="<td style='width:"+_onelen+"px'><table cellspacing='0' cellpadding='0' class='tb_in'><tr><td colspan='3' class='td_u' style='width:"+_onelen+"px'>"+_g_arr_totalHtml[i][j]+"</td></tr><tr><td class='td_l' id='"+bjtd+"'>"+_g_arr_subHtml[i][j][0]+"</td><td class='td_m' id='"+cctd+"'>"+_g_arr_subHtml[i][j][1]+"</td><td class='td_r' id='"+cstd+"'>"+_g_arr_subHtml[i][j][2]+"</td></tr></table></td>";
			}
			
		}
		s+="</tr>";
	}
	s+="</table>";
	document.getElementById("div_main").innerHTML = s;
	
}
// 生成列标题
function genTopTable()
{
	var s=[];
	s.push("<table cellspacing='0' cellpadding='0' class='tb_top' id='tab_top' style='table-layout: fixed'><tr>");
	for(var j=1; j<_g_colsCount; j++)
	{
		s.push("<th style='width:"+_onelen+"px'>"+_g_arr_totalHtml[0][j]+"</th>");
	}
	s.push("</tr></table>");
	document.getElementById("div_top").innerHTML = s.join("");

}
// 生成左边列表格
function genLeftTable()
{
	var lv_str = "";
	var lv = 0;
	var str = "";
	var s=[];
	s.push("<table cellspacing='0' cellpadding='0' class='tb_left' id='tab_left' style='display:none'>");
	for(var i=1; i<_g_rowsCount; i++)
	{
		str = _g_arr_totalHtml[i][0];
		if(_g_isSwitched)
		{
			str = "<img src='../images/treeico/blank.gif'><span onClick=\"sortTableRow("+i+")\" style='cursor:hand'>"+str+"</span>";
		}
		else
		{
			lv_str = "";
			lv = getSubLevel(i-1, 0);
			for(var j=0; j<lv; j++)
				lv_str += "<img src='../images/treeico/blank.gif'>";

			if(_g_arrRowIsParent[i-1]==1)
				str = lv_str+"<img id='pm_"+i+"' src='../images/treeico/minus.gif' style='cursor:hand' onClick=\"showHide(this,"+(i-1)+")\"><span onClick=\"sortTableRow("+i+")\" style='cursor:hand' title='点击排序'>"+str+"</span>";
			else if(_g_arrRowIsParent[i-1]==2)
			{
				lv_str += "<img src='../images/treeico/blank.gif'>";
				str = lv_str+"<span onClick=\"sortTableRow("+i+")\" style='cursor:hand' title='点击排序'>"+str+"</span>";
			}
			else
			{
				//if(lv_str=="")
					lv_str += "<img src='../images/treeico/blank.gif'>";
				str = lv_str+"<span onClick=\"sortTableRow("+i+")\" style='cursor:hand' title='点击排序'>"+str+"</span>";
			}
		}
		s.push("<tr><th class='th_n'>"+str+"</th></tr>");
	}
	s.push("</table>");
	document.getElementById("div_left").innerHTML = s.join("");
}

// 选中某行
function selectRow(row)
{
	drawrow = row;
	var oLeft = document.getElementById("tab_left");
	var oMain = document.getElementById("tab_main");
	if(row != _g_oldSortRow)
	{
		oMain.rows(row-1).className="tr_z";
		oLeft.rows(row-1).cells[0].className="th_s";
		//oMain.rows(row).cells[0].className="td_h0";
		if(_g_oldSortRow > 0)
		{
			oMain.rows(_g_oldSortRow-1).className="";
			oLeft.rows(_g_oldSortRow-1).cells[0].className="th_n";
			//oMain.rows(_g_oldSortRow).cells[0].className="td_h";
		}
		_g_oldSortRow = row;
	}
}

// 初始化处理表格
function parseCjgx()
{
	var str = "";
	_g_arr_ccgx= _g_ccgx.split(",");
	_g_ccgxCount = Math.floor(_g_arr_ccgx.length / 2);

	// 初始化列宽度
	//for (var j=0; j < _g_colsCount; j++)
	//	_g_arr_oldColsWidth[j] = 0;

	for (var i=0; i < _g_rowsCount; i++)
		_g_arrRowIsParent[i] = 0;
	var p_row=0, s_row=0;
	for(var i=0; i<_g_ccgxCount; i++)
	{
		p_row = convertInt(_g_arr_ccgx[i*2]);
		s_row = convertInt(_g_arr_ccgx[i*2+1]);
		if(p_row>0)
			_g_arrRowIsParent[p_row] = 1;
		if(s_row>0)
			_g_arrRowIsParent[s_row] = 2;
	}
}

// 获得层次数量
function getSubLevel(row, lv)
{
	var row_p = 0, row_s = 0;
	var new_lv = lv;
	// 最大20级，防止死循环
	if(lv>=20)
		return new_lv;
	for(var i=0; i<_g_ccgxCount; i++)
	{
		row_p = convertInt(_g_arr_ccgx[i*2]);
		row_s = convertInt(_g_arr_ccgx[i*2+1]);
		if(row_s==row)
		{
			new_lv = getSubLevel(row_p, lv+1);
			break;
		}
	}
	return new_lv;
}

// 显示/隐藏
function showHide(obj, row)
{
	var oTable = document.getElementById("tab_left");
	var bIsExpand = 0;	// 收缩
	if(obj.src.indexOf("minus.gif") >= 0)
	{
		// 收缩
		obj.src = "../images/treeico/plus.gif";
	}
	else
	{
		// 展开
		obj.src = "../images/treeico/minus.gif";
		bIsExpand = 1;
	}
	expandSub(row, bIsExpand);
}

// 收缩/展开下级
function expandSub(row, bIsExpand)
{
	var oLeft = document.getElementById("tab_left");
	var oMain = document.getElementById("tab_main");
	var row_p = 0, row_s = 0;
	for(var i=0; i<_g_ccgxCount; i++)
	{
		row_p = convertInt(_g_arr_ccgx[i*2]);
		row_s = convertInt(_g_arr_ccgx[i*2+1]);
		if(row_p==row)
		{
			if(bIsExpand==1)
			{
				oLeft.rows(row_s).style.display="block";
				oMain.rows(row_s).style.display="block";
			}
			else
			{
				oLeft.rows(row_s).style.display="none";
				oMain.rows(row_s).style.display="none";
			}
			if(bIsExpand==0 || (oLeft.rows(row_s).cells(0).innerHTML.indexOf('minus.gif')>0 && bIsExpand==1) )
			{
				expandSub(row_s, bIsExpand);
			}
		}
	}
}

// 排序


function sortTableRow(row)
{
var starttime = new Date();
	//var arr_total = getRowNumber(idTable, row);
	//var temp = 0;
_g_isProcing = true;
	var bIsNeedUpdate = false;
	if(row != _g_oldSortRow)
	{
		
		selectRow(row);
		//_g_oldSortRow = row;
		_g_oldSortDirect = 0;  //默认就是降序
		bIsNeedUpdate = false;
	}
	else
	{
		_g_oldSortDirect = 1-_g_oldSortDirect;
	}
	/**
	if(!_g_isSwitched)
	{
		for(var i=1; i<_g_colsCount-1; i++)
			for(var j=i+1; j<_g_colsCount; j++)
				if( (_g_oldSortDirect==0 && _g_arr_totalData[row][i] > _g_arr_totalData[row][j]) || (_g_oldSortDirect==1 && _g_arr_totalData[row][i] < _g_arr_totalData[row][j]) )
				{
					switchCol(i, j);
					bIsNeedUpdate = true;
				}
	}
	*/
	//排序， 先按超时排。超时数相等 按总数排
	if(!_g_isSwitched)
	{
		for(var i=1; i<_g_colsCount-1; i++)
		{
			for(var j=i+1; j<_g_colsCount; j++)
			{
				if(_g_arr_subData[row][i][0]==_g_arr_subData[row][j][0]) //超时数相等
				{
					if( (_g_oldSortDirect==0 && _g_arr_totalData[row][i] > _g_arr_totalData[row][j]) || (_g_oldSortDirect==1 && _g_arr_totalData[row][i] < _g_arr_totalData[row][j]) )
					{
						switchCol(i, j);
						bIsNeedUpdate = true;
					}
				}
				else
				{
					if( (_g_oldSortDirect==0 && _g_arr_subData[row][i][0] > _g_arr_subData[row][j][0]) || (_g_oldSortDirect==1 && _g_arr_subData[row][i][0] < _g_arr_subData[row][j][0]) )
					{
						switchCol(i, j);
						bIsNeedUpdate = true;
					}
				}
				
			}	
		}	
	}
	if(bIsNeedUpdate)
	{
		genTopTable()
		genMainTable();
		_g_oldSortRow = 0;
		selectRow(row);
		_g_arr_oldColsWidth[0] = 0;		// 强制绘图
		drawTabBars(true);
	}
	var  endtime =  new Date();
	var time_ = ((endtime.getTime()-starttime.getTime())/1000);
	window.status = "排序耗时"+time_+"秒";
_g_isProcing = false;
}


// 列互换
function switchCol(col1, col2)
{
	var temp = 0;
	for(var i=0; i<_g_rowsCount; i++)
	{
		temp = _g_arr_totalData[i][col1];
		 _g_arr_totalData[i][col1] =  _g_arr_totalData[i][col2];
		 _g_arr_totalData[i][col2] = temp;

		temp = _g_arr_totalHtml[i][col1];
		 _g_arr_totalHtml[i][col1] =  _g_arr_totalHtml[i][col2];
		 _g_arr_totalHtml[i][col2] = temp;

		for(var k=0; k<_g_arr_subData[i][col1].length; k++)
		{
			temp = _g_arr_subData[i][col1][k];
			 _g_arr_subData[i][col1][k] =  _g_arr_subData[i][col2][k];
			 _g_arr_subData[i][col2][k] = temp;
			temp = _g_arr_subHtml[i][col1][k];
			 _g_arr_subHtml[i][col1][k] =  _g_arr_subHtml[i][col2][k];
			 _g_arr_subHtml[i][col2][k] = temp;
		}
	}
}

// 获得表格列的位置
function getCellsPlace()
{
	var oTable = document.getElementById("tab_top");
	var oFull = document.getElementById("tab_full");
	// 外围表格
	_g_arr_offsetData[0] = new Array();
	_g_arr_offsetData[0][0] = oFull.offsetLeft;
	_g_arr_offsetData[0][1] = oFull.offsetTop;
	_g_arr_offsetData[0][2] = oFull.offsetWidth;
	_g_arr_offsetData[0][3] = oFull.offsetHeight;

	var count=0;
	for (var j=0; j < oTable.rows(0).cells.length; j++)
	{
		_g_arr_offsetData[j+1] = new Array();
		_g_arr_offsetData[j+1][0] = oTable.rows(0).cells(j).offsetLeft;
		_g_arr_offsetData[j+1][1] = oTable.rows(0).cells(j).offsetTop;
		_g_arr_offsetData[j+1][2] = oTable.rows(0).cells(j).offsetWidth;
		_g_arr_offsetData[j+1][3] = oTable.rows(0).cells(j).offsetHeight;
	}
}

// 绘背景
function drawBarBackground()
{
	var nLeft = _g_arr_offsetData[0][0]+70;
	var nWidth = _g_arr_offsetData[0][2]-80;
	var nTop = 0;
	for(var i=1; i<6; i++)
	{
		nTop = _g_nBarAreaHeight - i*(_g_nBarMaxHeight/5);
		_g_barContent+="<v:Textbox style='POSITION:absolute;Z-INDEX:3003;LEFT:"+(nLeft-40)+";TOP:"+(nTop-8)+";FONT-SIZE:12;COLOR:#000000' inset='5pt,5pt,5pt,5pt' print='t'>"+(_g_maxValue/5*i)+"</v:Textbox>";
		//nTop -= 61;
		nTop -= 5;
		_g_barContent+="<v:line style='POSITION:absolute;Z-INDEX:3002;' strokecolor='#f0f0f0' from='"+(nLeft-0)+"px,"+nTop+"px' to='"+(nLeft+nWidth-2)+"px,"+nTop+"px'/>";
	}
	_g_barContent+="<v:rect style='width:"+(nWidth)+";height:"+(_g_nBarMaxHeight+20)+";Z-INDEX:3001;LEFT:"+(nLeft)+";TOP:"+(_g_nBarAreaHeight-_g_nBarMaxHeight-20)+";POSITION:absolute;' strokecolor='#d0d0d0' filled='t' fillcolor='#FFFFFF'/>";
	
	//yaha
	_g_barContent+="<v:RoundRect id='sssd' fillcolor='rgb(242, 253, 219)' style='display:none;position:absolute;left:100px;top:200px;width:130;height:86px;z-index:99999'>";
	_g_barContent+="<v:shadow on='T' type='single' color='#b3b3b3' offset='5px,5px'/>";
	_g_barContent+="<v:TextBox id='txt_a' inset='5pt,5pt,5pt,5pt' style='font-size:12px;'></v:TextBox>";
	_g_barContent+="</v:RoundRect>";
	
}
// 圆整最大值(5格)，最小值10
function roundMaxValue()
{
	var breakValue = Math.ceil(_g_maxValue / 5.0);
	var item = 1;
	if(breakValue<=2)
		breakValue = 2;
	if(breakValue<=10)
	{
		_g_maxValue = breakValue * 5;
		return;
	}
	// 整理 breakValue在10-100之间
	while(1)
	{
		if(breakValue>100)
		{
			breakValue = Math.ceil(breakValue / 10.0);
			item *= 10;
		}
		else
			break;
	}
	if(breakValue>10 && breakValue<20)
		breakValue =  Math.ceil(breakValue / 5.0) * 5;
	if(breakValue>20 && breakValue<50)
		breakValue =  Math.ceil(breakValue / 10.0) * 10;
	if(breakValue>50 && breakValue<=100)
		breakValue =  Math.ceil(breakValue / 20.0) * 20;
	_g_maxValue = item * breakValue * 5;
}

function addBar(left,top,height,colorType, percent,clickcol)
{
	var _g_textColor = "#FFFFFF";
	if(height<8)
		_g_textColor = "#000000";

	var o_main = document.getElementById("div_main");
	left = left - o_main.scrollLeft;
	if(left<-40 || left>_g_arr_offsetData[0][2]-8-_g_nBarWidth-_g_titleColWidth)
		return;
	left = left + _g_titleColWidth;

	if(height>2)
	{
		_g_barContent+="<v:rect title='"+percent+"' onclick='lj("+clickcol+","+colorType+","+drawrow+")' style='cursor: pointer;width:"+_g_nBarWidth+";height:"+(height-2)+";Z-INDEX:3008;LEFT:"+(left+1)+";TOP:"+(top+1)+";POSITION:absolute;' strokecolor='"+_g_LineColor[colorType]+"' filled='t' fillcolor='"+_g_FillColor[colorType]+"'>"+
		"<v:fill type='gradient' opacity='0.8' color2='"+_g_FillColor2[colorType]+"' angle='90' focus='50%' /></v:rect>";
	}
	if(height>0)
	{
		_g_barContent+="<v:rect title='"+percent+"' onclick='lj("+clickcol+","+colorType+","+drawrow+")' style='cursor: pointer;width:"+(_g_nBarWidth+2)+";height:"+(height-1)+";Z-INDEX:3009;LEFT:"+left+";TOP:"+top+";POSITION:absolute;' strokecolor='"+_g_FillColor[colorType]+"' filled='f'></v:rect>";
	}
	if(height>=_g_minHighDrawText)	// 只有8点高度才写百分比
	{
		//_g_barContent+="<v:Textbox style='POSITION:absolute;Z-INDEX:3010;LEFT:"+(left+6)+";TOP:"+(top+height/2-6)+";FONT-SIZE:12;COLOR:"+_g_textColor+"' inset='5pt,5pt,5pt,5pt' print='t'>"+percent+"</v:Textbox>";
		//不显示百分比
		//_g_barContent+="<v:Textbox style='POSITION:absolute;Z-INDEX:3010;LEFT:"+left+";TOP:"+(top+height/2-6)+";FONT-SIZE:12;COLOR:"+_g_textColor+";width:"+(_g_nBarWidth+3)+";text-align:center;' inset='5pt,5pt,5pt,5pt' print='t'>"+percent+"</v:Textbox>";
	}
}

function addText(left,top,str,title,clickcol)
{
	var o_main = document.getElementById("div_main");
	left = left - o_main.scrollLeft;
	if(left<-40 || left>_g_arr_offsetData[0][2]-8-_g_nBarWidth-_g_titleColWidth)
		return;
	left = left + _g_titleColWidth;
	
	_g_barContent+="<v:Textbox onmouseout='mouse_out()' onmouseover='mouse_over("+left+","+top+",\""+title+"\")' title='' onclick='lj("+clickcol+",9,"+drawrow+")' style='POSITION:absolute;cursor: pointer;Z-INDEX:3010;LEFT:"+left+";TOP:"+top+";FONT-SIZE:12;COLOR:#000000;width:"+(_g_nBarWidth+3)+";text-align:center;' inset='5pt,5pt,5pt,5pt' print='t'>"+str+"</v:Textbox>";
	//周二
}

function mouse_over(l,t,vv){
	sssd.style.display='block';
	
	var temp_l = l+30;
	var temp_t = t+20;
	if(temp_t>140)
		temp_t = 110;
	if(temp_l>840)
		temp_l = l-140;
	sssd.style.left=temp_l;
	sssd.style.top=temp_t;
	
	var vv_temp = vv.split("-");
	
	txt_a.innerHTML="<div style='height:22px;line-height:22px;font-size:12px'>"+vv_temp[0]+"</div>"+
					"<div style='height:22px;line-height:22px;font-size:12px'>"+vv_temp[1]+"</div>"+
					"<div style='height:22px;line-height:22px;font-size:12px'>"+vv_temp[2]+"</div>";
}
function mouse_out(){
	sssd.style.display='none';
}

// 绘制图形
function drawTabBars(bUrgeRefresh)
{
	var i=0, j=0, k=0;
	var b = 0, h = 0, p=0;
	var arr_totle = new Array();
	_g_maxValue = 0;

	// 清空原图
	_g_barContent = "";

	var oMain = document.getElementById("div_main");
	var oFull = document.getElementById("tab_full");
	if(!bUrgeRefresh && _g_arr_oldColsWidth[0] == oFull.offsetWidth && _g_arr_oldColsWidth[1] == oMain.scrollLeft)
		return;
	// 存储宽度
	_g_arr_oldColsWidth[0] = oFull.offsetWidth;	// tab_full.width
	_g_arr_oldColsWidth[1] = oMain.scrollLeft;	// div_main.scrollLeft
	_g_bUrgeRefresh = false;

	// 获得表格列位置
	getCellsPlace();

	var _zs = 0; //总数
	var _bjs = 0; //办结数
	var _css= 0; //超时数
	var _ccs = 0; //查处数

// 当前选中行_g_oldSortRow

	// 计算最大高度
	var oTable = document.getElementById("tab_top");
	for (j=1; j < _g_colsCount; j++)
	{
		arr_totle[j] = 0;
		for(k=0; k<_g_arr_subData[_g_oldSortRow][j].length; k++)
			arr_totle[j] += _g_arr_subData[_g_oldSortRow][j][k];
		if(_g_maxValue < arr_totle[j])
			_g_maxValue = arr_totle[j];
	
		_zs += arr_totle[j];
		_bjs += _g_arr_subData[_g_oldSortRow][j][0];
		_ccs += _g_arr_subData[_g_oldSortRow][j][1];
		_css += _g_arr_subData[_g_oldSortRow][j][2];			
	}
	//gtjj
	if(parent.document.getElementById("zs"))
	{
		parent.document.getElementById("zs").innerText = "("+_zs+")";
		parent.document.getElementById("bjs").innerText = "("+_bjs+")";
		parent.document.getElementById("ccs").innerText = "("+_ccs+")";
		parent.document.getElementById("css").innerText = "("+_css+")";
		//document.getElementById("zs").innerText = "("+_zs+")";
		//document.getElementById("bjs").innerText = "("+_bjs+")";
		//document.getElementById("ccs").innerText = "("+_ccs+")";
		//document.getElementById("css").innerText = "("+_css+")";
	}
	var showval = 0;var s1 = 0;var s2 =0;var s3=0;
	// 圆整最大值
	roundMaxValue();
	// 绘制背景
	drawBarBackground();

	// 绘图
	for (j=1; j < _g_colsCount; j++)
	{
		var xxx = _g_arr_offsetData[j][0]+_g_arr_offsetData[j][2]/2 - _g_nBarWidth/2+_g_nBarLeftJust;
		b = _g_nBarAreaHeight;
		for(k=0; k<_g_arr_subData[_g_oldSortRow][j].length; k++)
		{
			if(arr_totle[j]<=0)
			{
				arr_totle[j] = 0;
				p = 0;showval=0;
			}
			else
			{
				p = _g_arr_subData[_g_oldSortRow][j][k] / arr_totle[j];
				showval = _g_arr_subData[_g_oldSortRow][j][k];
			}	
			h = Math.floor(arr_totle[j] / _g_maxValue * _g_nBarMaxHeight * p + 0.5);
			b-=h;
			// zlf addBar(xxx,b, h,k,""+Math.floor(p*100+0.5)+"%");
			addBar(xxx-5,b, h,k,showval,j);
		}
		if(arr_totle[j]<=0)
		{
			s1 = 0;s2 =0;s3=0;
		}	
		else	
		{
			s1 = _g_arr_subData[_g_oldSortRow][j][0];
			s2 = _g_arr_subData[_g_oldSortRow][j][1];
			s3 = _g_arr_subData[_g_oldSortRow][j][2];
		}
		// zlf  addText(xxx,b-14, ""+arr_totle[j]);
		var title = "超时未处置："+s3 +"条-处置中："+s2+"条-已结案："+ s1+"条";
		addText(xxx-5,b-14, ""+arr_totle[j],title,j);
	}
	idDiv.innerHTML = _g_barContent;
}



// 切换行列
function switchRowCol()
{
	var i=0;
	var j=0;
	var k=0;
	var temp = "";
	var maxCell = _g_rowsCount;
	if(maxCell < _g_colsCount)
		maxCell = _g_colsCount;

	for(i=1; i<maxCell; i++)
	{
		for(j=0; j<i; j++)
		{
			// _g_arr_totalData
			temp = _g_arr_totalData[i][j];
			_g_arr_totalData[i][j] = _g_arr_totalData[j][i];
			_g_arr_totalData[j][i] = temp;
			// _g_arr_totalHtml
			temp = _g_arr_totalHtml[i][j];
			_g_arr_totalHtml[i][j] = _g_arr_totalHtml[j][i];
			_g_arr_totalHtml[j][i] = temp;
			// _g_arr_subData
			for(k=0; k<_g_arr_subData[i][j].length; k++)
			{
				temp = _g_arr_subData[i][j][k];
				_g_arr_subData[i][j][k] = _g_arr_subData[j][i][k];
				_g_arr_subData[j][i][k] = temp;
				temp = _g_arr_subHtml[i][j][k];
				_g_arr_subHtml[i][j][k] = _g_arr_subHtml[j][i][k];
				_g_arr_subHtml[j][i][k] = temp;
				
			}
		}
	}
	temp = _g_rowsCount;
	_g_rowsCount = _g_colsCount;
	_g_colsCount = temp;
	// 置标志
	_g_isSwitched = !_g_isSwitched;

	genLeftTable();
	genTopTable();
	genMainTable();
	_g_oldSortRow = 0;
	selectRow(1);
	//drawTabBars(true);
	_g_arr_oldColsWidth[0] = 0;		// 强制绘图
}

// 处理表格
function parseTable(idTable)
{
	var starttime = new Date();
	loadOrginTabContents(idTable);
	parseCjgx();	// 处理层次关系
	hideTable(_g_orginTableID);
	genInitContent();
	selectRow(1);
	drawTabBars();
	_isfirstload = true;
	setTimeout(fixDivPostion, 200);
	var  endtime =  new Date();
	var time_ = ((endtime.getTime()-starttime.getTime())/1000);
	window.status = "加载耗时"+time_+"秒";
}
// clickcol 点击的第几列数据 bz 是点击的是超时，查处，还是办结 数据 ，9 是点击上面总数 clickrow 点击的是第几行
function lj(clickcol,bz,clickrow)
{
clickcol = convertInt(clickcol);
bz = convertInt(bz);
clickrow = convertInt(clickrow);
///alert(clickcol+"/"+bz+"/"+clickrow);
clickcol=clickcol-1;
clickrow=clickrow-1;   //现在选择的div 是从0，0开始的
	var oTable = document.getElementById("tab_main");
	var o = oTable.rows(clickrow).cells(clickcol).childNodes(0);	// Table
	var url = "";
	if(bz==9)
	{
		url = o.rows(0).innerHTML;
	}
	else
	{
		url =	o.rows(1).cells(bz).outerHTML;
	}	


	var l = url.indexOf("onclick");
	url = url.substr(l+7);
	l = url.indexOf("=")
	url = url.substr(l+1);
	l = url.indexOf('"');
	url = url.substr(l+1);
	l = url.indexOf('"');
	url = url.substr(0,l);

	eval(url);
	
}