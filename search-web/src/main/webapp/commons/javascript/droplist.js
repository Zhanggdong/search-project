var _disableSystemContextMenu = false;	// 是否隐藏右键菜单
var _bShowListOnFocus = true;			// 是否直接显示下拉菜单

var _dropdown_parentwindow=null;
var _dropdown_parentbox=null;
var _dropdown_box=null;
var _iframebox=null;					// 用于遮盖的iframe
var _dropdown_table=null;
var _dropdown_frame=null;

var _calendarControl=null;
var _tmp_dataset_date=null;

var _activeElement=null;
var _activeEditor=null;
var _dropdown_window=null;

// new add
var _bIsBoxShow = false;				// 是否已经显示下拉菜单
var _nowSelectObject;					// 当前选择的输入框

var _bIfShowFirstCol = false;			// 是否显示代码列
var _arr_sDropData = new Array();		// 下拉列表数据数组, 20
var _arr_hookobj = new Array();			// 添加消息挂接数组, 20
var _arr_bHookEnabled = new Array();	// 是否激活Hook, 20
var _nMaxHookNumber = 0;				// 个数

var _sIEVersion;						// IE版本

function dropctrl_onfocus(obj)
{
	if(!obj) return;
	
	if(_activeElement == obj)
	{
		showDropDownBtn(obj);
		return;
	}
	else
	{
		// 切换到这里
		if(isDropdownBoxVisible())
			hideDropDownBox();
		if(isDropDownBtnVisible())
			hideDropDownBtn();

		_activeElement = obj;
		showDropDownBtn(obj);
		if(_bShowListOnFocus)
			showDropDownBox(obj);
	}
}

function dropctrl_onfocusout(obj) 
{
	if(!obj)
		return;
	/*
	if(isDropdownBoxVisible())
		hideDropDownBox();
	if(isDropDownBtnVisible())
		hideDropDownBtn();
	*/
}

function _document_onpropertychange()
{
	try
	{
		if (window.closed) return;
		if (_sIEVersion<"5.0")
		{
			//alert(constErrUnsupportBrowser);
			return;
		}
	
		if (event.propertyName=="activeElement")
		{
			var aaa = document.activeElement;
			if(!aaa)
				return;
	
			//  点击窗口空白处
			if(aaa == document.body)
			{
				if(isDropdownBoxVisible())
					hideDropDownBox();
				if(isDropDownBtnVisible())
					hideDropDownBtn();
				_activeElement=null;
				return;
			}
			
			for(var i=0; i<_nMaxHookNumber; i++)
			{
				if(aaa == _arr_hookobj[i])
				{
					if(_arr_bHookEnabled[i])
						dropctrl_onfocus(aaa);
					return;
				}
			}
			
			// 判断点击其它地方，关闭下拉按钮
			var bbb = aaa;
			var ccc;
			var bIsClickInOthers = true;
			while(true)
			{
				ccc = bbb.id;
				if(ccc && ccc == '_dropdown_btn' || ccc == '_dropbox')
				{
					bIsClickInOthers = false;
					break;
				}
				/*
				// 注：不需要判断，前面已经处理
				ccc = bbb.getAttribute("dropdown");
				if(ccc && (ccc=="date" || ccc=="date"))
				{
					bIsClickInOthers = false;
					break;
				}
				*/
				//break;
				bbb = bbb.parentElement;
				if(!bbb || bbb==document.body)
					break;
			}
			if(bIsClickInOthers)
			{
				if(isDropdownBoxVisible())
					hideDropDownBox();
				if(isDropDownBtnVisible())
					hideDropDownBtn();
				_activeElement=null;
			}
			//_activeElement=document.activeElement;
		}
	}
	catch(e)
	{}
}

function _document_oncontextmenu()
{
	event.returnValue=!_disableSystemContextMenu;
	return;

}

document.onpropertychange=_document_onpropertychange;
//document.onkeydown=_document_onkeydown;
document.oncontextmenu=_document_oncontextmenu;

function formatDateTime(date, mode){
	function getDateString(date){
		var years=date.getFullYear();
		var months=date.getMonth()+1;
		var days=date.getDate();

		if (months<10) months="0"+months;
		if (days<10) days="0"+days;

		return years+"-"+months+"-"+days;
	}

	function getTimeString(date){
		var hours=date.getHours();
		var minutes=date.getMinutes();
		var seconds=date.getSeconds();

		if (hours<10) hours="0"+hours;
		if (minutes<10) minutes="0"+minutes;
		if (seconds<10) seconds="0"+seconds;

		return hours+":"+minutes+":"+seconds;
	}

	if (typeof(date)=="object" && !isNaN(date)){
		if (!mode) mode="timestamp";
		switch (mode){
			case "date":{
				return getDateString(date);
				break;
			}
			case "time":{
				return getTimeString(date);
				break;
			}
			case "timestamp":{
				return getDateString(date)+" "+getTimeString(date);
				break;
			}
			default:{
				return getDateString(date)+" "+getTimeString(date);
				break;
			}
		}
	}
	else
		return "";
}
function _dropdown_btn_onmousedown(button){
	var obj=button.editor;
	if (!isDropdownBoxVisible())
	{
		if (obj) showDropDownBox(obj);
	}
	else
	{
		hideDropDownBox();
	}
	if (obj) obj.focus();
	
}
function _dropdown_btn_onfocus(button){
	var obj=button.editor;
	if (obj) obj.focus();
}

function isDropdownBoxVisible()
{
	//return _bIsBoxShow;
    if (typeof(_dropdown_box)!="undefined" && _dropdown_box)
		return (_dropdown_box.style.visibility=="visible")
    else
		return false;
}

function getDropDownBtn(){
	if  (typeof(_dropdown_btn)=="undefined")
	{
		obj=document.createElement("<INPUT class=\"dropdown_button\" id=_dropdown_btn type=button tabindex=-1 value=6 hidefocus=true"+
			" style=\"position: absolute; background-color: #FFFFFF; visibility: hidden; z-index: 9999\""+
			" LANGUAGE=javascript onmousedown=\"return _dropdown_btn_onmousedown(this)\" onfocus=\"return _dropdown_btn_onfocus(this)\">");
		obj.style.background = "url(../images/dropdown_button.gif)";
		//obj.style.background-color = "#FFFFFF";
		
		/*
		// 不能通过这种方法进行创建!包含2个对象
		obj=document.createElement("<button class=\"dropdown_button\" id=_dropdown_btn tabindex=-1 hidefocus=true"+
			" style=\"position:absolute;left:513;top:83;right:100;bottom:100;width:31px;height:28px;visibility:hidden;z-index:9999;\""+
			" LANGUAGE=javascript onmousedown=\"return _dropdown_btn_onmousedown(this)\" onfocus=\"return _dropdown_btn_onfocus(this)\">"+
			" <font face=\"Marlett\" size=\"1\">6</font></button>");
		obj.style.background = "url(../images/dropdown_button.gif)";
		*/
		document.body.appendChild(obj);
		return obj;
	}
	else{
		return _dropdown_btn;
	}
}

function isDropDownBtnVisible()
{
	if  (typeof(_dropdown_btn)!="undefined")
		return (_dropdown_btn.style.visibility=="visible")
	else
		return false;
}

function sizeDropDownBtn(_editor)
{
	if (!isDropDownBtnVisible()) return;
	
	var pos=getAbsPosition(_editor);
	
	_dropdown_btn.style.height=_editor.offsetHeight-2;
	_dropdown_btn.style.width=16;
	_dropdown_btn.style.left=pos[0]+_editor.offsetWidth-_dropdown_btn.offsetWidth-1;
	_dropdown_btn.style.top=pos[1]+1;

}

function showDropDownBtn(_editor)
{
	getDropDownBtn();
	if (typeof(_dropdown_btn)=="undefined") return;
	_nowSelectObject = _editor;
	
	if (!isDropDownBtnVisible())
	{
		_dropdown_btn.setAttribute("editor", _editor);
		_dropdown_btn.style.visibility="visible";
		sizeDropDownBtn(_editor);

		_editor.setAttribute("oldwidth", _editor.style.width);
		var oldWidth=_editor.offsetWidth;
		_editor.style.borderRightWidth=18;
		_editor.style.width=oldWidth;
	}
}

function hideDropDownBtn()
{
	if  (typeof(_dropdown_btn)=="undefined") return;

	if (isDropDownBtnVisible())
	{
		var editor=_dropdown_btn.editor;
		if (editor){
			// 注意：会修改原来的style.width属性
			var oldWidth=editor.offsetWidth;
			editor.style.borderRightWidth=1;
			//editor.style.width=oldWidth;
			editor.style.width = editor.getAttribute("oldwidth");
		}
		_dropdown_btn.style.visibility="hidden";
		_dropdown_btn.editor=null;
		
	}
	
}

function getAbsPosition(obj, offsetObj){
	var _offsetObj=(offsetObj)?offsetObj:document.body;
	var x=obj.offsetLeft;
	var y=obj.offsetTop;
	var tmpObj=obj.offsetParent;

	while ((tmpObj!=_offsetObj) && tmpObj){
		x += tmpObj.offsetLeft - tmpObj.scrollLeft + tmpObj.clientLeft;
		y += tmpObj.offsetTop - tmpObj.scrollTop + tmpObj.clientTop;
		tmpObj=tmpObj.offsetParent;
	}
	return ([x, y]);
}

function getDropDownBox(dropdown)
{
	var box=null;
	if(!_dropdown_box)
	{
		box=document.createElement("<DIV id='_dropbox' class=\"dropdown_frame\" style=\"position: absolute; visibility: hidden; z-index: 10000\"></DIV>");
		_iframebox=document.createElement("<iframe id=\"_iframebox\" style=\"position: absolute; visibility: transparent; z-index: 9999\" width=\"100\" height=\"200\"></iframe>");
		document.body.appendChild(box);
		document.body.appendChild(_iframebox);
		dropdown.dropdownbox = box;
		box.dropDown = dropdown;
		_dropdown_box=box;
	}
}

// xxxxxx
// 显示下拉列表或日期选择框
function showDropDownBox(_editor)
{
	if (!isDropdownBoxVisible())
	{
		_bIsBoxShow = true;
		getDropDownBox("");
		_dropdown_box.editor=_editor;
		_dropdown_box.prepared=false;

		with (_dropdown_box)
		{
			style.overflowY="hidden";
			style.visibility="visible";

			var dropDownType=_editor.getAttribute("dropdown");
			switch(dropDownType)
			{
				case "date":	
					createCalendar(_dropdown_box);
					_dropdown_box.onkeydown=_calendar_onkeydown;

					_dropdown_parentwindow=window;
					_dropdown_parentbox=_dropdown_parentwindow._dropdown_box;
					_dropdown_parentwindow._dropdown_window=window;
					//sizeDropDownBox();
					_dropdown_parentbox.prepared=true;
					
					// 移动位置
					dropDownLocate();
					with (_dropdown_box)
					{
						var pos=getAbsPosition(_editor);
				
						//style.height=_editor.offsetHeight-2;
						//style.width=16;
						style.posLeft=pos[0];	//+_editor.offsetWidth-offsetWidth-1;
						style.posTop=pos[1]+_editor.offsetHeight;
					}
					break;
				case "time":
				case "timestamp":
					createCalendar1(_dropdown_box);
					_dropdown_box.onkeydown=_calendar_onkeydown;

					_dropdown_parentwindow=window;
					_dropdown_parentbox=_dropdown_parentwindow._dropdown_box;
					_dropdown_parentwindow._dropdown_window=window;
					//sizeDropDownBox();
					_dropdown_parentbox.prepared=true;
					
					// 移动位置
					dropDownLocate();
					with (_dropdown_box)
					{
						var pos=getAbsPosition(_editor);
				
						//style.height=_editor.offsetHeight-2;
						//style.width=16;
						style.posLeft=pos[0];	//+_editor.offsetWidth-offsetWidth-1;
						style.posTop=pos[1]+_editor.offsetHeight;
					}
					break;
					//树型
				case "tree":
					createTree();
					//_dropdown_parentbox.prepared=false;
					break;
				//case "list":
				default:	// 缺省为list
					createListTable(_dropdown_box);
					
					//_dropdown_table.onkeydown=_dropdown_onkeydown;
					//_initDropDownBox(dropdown.type);

					_dropdown_parentwindow=window;
					_dropdown_parentbox=_dropdown_parentwindow._dropdown_box;
					_dropdown_parentwindow._dropdown_window=window;
					//sizeDropDownBox();
					_dropdown_parentbox.prepared=true;

					// 移动位置
					with (_dropdown_box)
					{
						var pos=getAbsPosition(_editor);
				
						//style.height=_editor.offsetHeight-2;
						//style.height=190;
						style.width=_editor.offsetWidth;
						style.posLeft=pos[0];	//+_editor.offsetWidth-offsetWidth-1;
						style.posTop=pos[1]+_editor.offsetHeight;
						
						//style.overflowY="auto";
					}
					break;
			}
		}
		_editor.dropDownVisible=true;
		if  (typeof(_dropdown_btn)!="undefined") _dropdown_btn.value="5";	// 修改箭头样式
		//hideSelectObj(_dropdown_box);		// 隐藏比层优先级高的对象（有缺陷不使用）

		with (_iframebox)
		{
			var zzz = getAbsPosition(_dropdown_box);
			style.posLeft=_dropdown_box.style.posLeft;
			style.posTop=_dropdown_box.style.posTop;
			style.width=_dropdown_box.offsetWidth;
			style.height=_dropdown_box.offsetHeight;
		}

	}
	
}

function hideDropDownBox()
{
	if (!_dropdown_box) return;
	if (isDropdownBoxVisible())
	{
		_bIsBoxShow = false;
	
		var editor=_dropdown_box.editor;
		var dropdown=_dropdown_box.dropDown;
		{
			_dropdown_box.editor=null;

			_dropdown_window=null;

			for (var i=0; i<_dropdown_box.children.length; i++)		// only 1
				_dropdown_box.children[i].style.visibility="hidden"
			_dropdown_box.removeNode(true);
			_dropdown_box.style.visibility="hidden";
			_dropdown_box=null;
		}

		_iframebox.removeNode(true);	// 清除遮盖的iframe
		editor.dropDownVisible=false;
		if  (typeof(_dropdown_btn)!="undefined") _dropdown_btn.value="6";
		// showSelectObj();				// 显示被隐藏的对象
	}
}

var _calendar_days;

function _calendar_year_onpropertychange()
{
	if (!_calender_year.processing && event.propertyName=="value")
	{
		if (_calender_year.value.length==4)
		{
			_calender_year.processing=true;
			changeCalendarDate(_calender_year.value, _calendarControl.month);
			//changeCalendarDate(getInt(_calender_year.value), _calendarControl.month);
			_calender_year.processing=false;
		}
	}
}

function _calendar_month_onpropertychange()
{
	//if (!_calender_month.processing && _activeElement==_calender_month && event.propertyName=="value")
	if (!_calender_month.processing && event.propertyName=="value")
	{
		if (_calender_month.value.length>0)
		{
			_calender_month.processing=true;
			changeCalendarDate(_calendarControl.year, _calender_month.value-1);
			_calender_month.processing=false;
		}
	}
}

function createCalendar(parent_element)
{
	function calendar(){
	 	var today=new Date()
	 	this.todayDay=today.getDate();
		this.todayMonth=today.getMonth();
		this.todayYear=today.getFullYear();
	 	this.activeCellIndex=0;
	}

	_calendar_days=new Array("日","一","二","三","四","五","六");
	_calendarControl=new calendar();

	var tmpHTML="";
	tmpHTML+="<TABLE id=\"CalendarTable\" class=\"calendar\" width=200px cellspacing=0 cellpadding=1 rule=all>";
	tmpHTML+="<TR class=\"title\" valign=top><TD>";
	tmpHTML+="<TABLE WIDTH=100% CELLSPACING=1 CELLPADDING=0>";
	tmpHTML+="<TR><TD align=right>";
	tmpHTML+="<INPUT type=button class=\"niceButton3\" value=3 title=\""+"上一年"+"\" style=\"FONT-SIZE:8;FONT-FAMILY:webdings;WIDTH:20px;HEIGHT:20px\" onclick=\"changeCalendarDate(_calendarControl.year-1,_calendarControl.month)\">";
	tmpHTML+="</TD><TD width=1>";
	tmpHTML+="<INPUT id=\"_calender_year\" type=text class=editor size=4 maxlength=4 onpropertychange=\"return _calendar_year_onpropertychange()\">";
	tmpHTML+="</TD><TD align=left width=25px>";
	tmpHTML+="<INPUT type=button class=\"niceButton3\" value=4 title=\""+"下一年"+"\" style=\"FONT-SIZE:8;FONT-FAMILY:webdings;WIDTH:20px;HEIGHT:20px\" onclick=\"changeCalendarDate(_calendarControl.year+1,_calendarControl.month)\">";
	tmpHTML+="</TD>";
	tmpHTML+="<TD align=right width=25px>";
	tmpHTML+="<INPUT type=button class=\"niceButton3\" value=3 title=\""+"上个月"+"\" style=\"FONT-SIZE:8;FONT-FAMILY:webdings;WIDTH:20px;HEIGHT:20px\" onclick=\"changeCalendarDate(_calendarControl.preYear,_calendarControl.preMonth)\">";
	tmpHTML+="</TD><TD width=1>";
	tmpHTML+="<INPUT id=\"_calender_month\" type=text class=editor size=2 maxlength=2 onpropertychange=\"return _calendar_month_onpropertychange()\">";
	tmpHTML+="</TD><TD align=left>";
	tmpHTML+="<INPUT type=button class=\"niceButton3\" value=4 title=\""+"下个月"+"\" style=\"FONT-SIZE: 8;FONT-FAMILY:webdings;WIDTH:20px;HEIGHT:20px\" onclick=\"changeCalendarDate(_calendarControl.nextYear,_calendarControl.nextMonth)\">";
	tmpHTML+="</TD></TR>";
	tmpHTML+="</TABLE></TD></TR>";

	tmpHTML+="<TR><TD>";
	tmpHTML+="<TABLE border=1 bordercolor=silver id=\"calendarData\" HEIGHT=100% WIDTH=100% CELLSPACING=0 CELLPADDING=0 style=\"BORDER-COLLAPSE: collapse\"";
	tmpHTML+="onclick=\"_calendar_cell_onclick(event.srcElement)\">";
	tmpHTML+="<TR height=20px style=\"background-image: url(images/table_title.gif)\">";
	for (var i=0;i<=6;i++){
		tmpHTML+="<TD align=center>"+_calendar_days[i]+"</TD>";
	}
	tmpHTML+="</TR>";
	for(var i=0;i<=5;i++){
		tmpHTML+="<TR>";
		for(var j=0;j<=6;j++){
			tmpHTML+="<TD align=center></TD>";
		}
		tmpHTML+="</TR>";
	}
	tmpHTML+="</TABLE></TD></TR>";

	tmpHTML+="<TR class=\"footer\"><TD align=right>";
	tmpHTML+="<INPUT class=\"niceButton3\" type=button id=\"button_today\" value=\""+"今天"+" "+_calendarControl.todayYear+"-"+(_calendarControl.todayMonth+1)+"-"+_calendarControl.todayDay+"\" onclick=\"_calendar_today_onclick()\"";
	tmpHTML+="</TD></TR></TABLE>";
	if (parent_element)
		parent_element.innerHTML=tmpHTML;
	else
		document.body.innerHTML=tmpHTML;

	changeCalendarDate(_calendarControl.todayYear,_calendarControl.todayMonth,_calendarControl.todayDay)
}

function setCalendarDate(date){
	//alert("2:"+date.getHours());
	if(typeof(_calender_hour)=="object")
	{
	//alert("here");
		changeCalendarDate1(date.getFullYear(),date.getMonth(),date.getDate(),date.getHours(),date.getMinutes(),date.getSeconds());	changeCalendarDate1(date.getFullYear(),date.getMonth(),date.getDate(),date.getHours(),date.getMinutes(),date.getSeconds());
	}
	else	
		changeCalendarDate(date.getFullYear(),date.getMonth(),date.getDate());
}

function changeCalendarDate(year, month, day){

	if (_calendarControl.year==year && _calendarControl.month==month && (!day || _calendarControl.day==day)) return;

	if (_calendarControl.year!=year || _calendarControl.month!=month){
		_calendarControl.year=year;
		_calendarControl.month=month;

		if (month==0){
			 _calendarControl.preMonth=11
			 _calendarControl.preYear=_calendarControl.year-1
		}else{
			 _calendarControl.preMonth=_calendarControl.month-1
			 _calendarControl.preYear=_calendarControl.year
		}
		if (month==11){
			_calendarControl.nextMonth=0
			_calendarControl.nextYear=_calendarControl.year+1
		}else{
			_calendarControl.nextMonth=_calendarControl.month+1
			_calendarControl.nextYear=_calendarControl.year

		}
		_calendarControl.startday=(new Date(year,month,1)).getDay()
		if (_calendarControl.startday==0) _calendarControl.startday=7
		var curNumdays=getNumberOfDays(_calendarControl.month,_calendarControl.year)
		var preNumdays=getNumberOfDays(_calendarControl.preMonth,_calendarControl.preYear)
		var nextNumdays=getNumberOfDays(_calendarControl.nextMonth,_calendarControl.nextYear)
		var startDate=preNumdays-_calendarControl.startday+1
		var endDate=42-curNumdays-_calendarControl.startday

		_calender_month.value=(_calendarControl.month+1);
		_calender_year.innerText=_calendarControl.year

		var datenum=0;
		for (var i=startDate;i<=preNumdays;i++){
			var cell = calendarData.cells[datenum+7];
			cell.monthAttribute="pre";
			cell.className="cell_trailing";
			cell.innerText=i;
			datenum++;
		}
		for (var i=1;i<=curNumdays;i++){
			var cell = calendarData.cells[datenum+7];
			cell.monthAttribute="cur";
			if (datenum != _calendarControl.activeCellIndex){
				cell.className="cell_day";
			}
			cell.innerText=i;
			datenum++;
		}
		for (var i=1;i<=endDate;i++){
			var cell = calendarData.cells[datenum+7];
			cell.monthAttribute="next";
			cell.className="cell_trailing";
			cell.innerText=i;
			datenum++;
		}
	}

	if (day) _calendarControl.day=day;
	
	setCalendarActiveCell(calendarData.cells[_calendarControl.day+_calendarControl.startday-1+7]);
}
//设置初始的日期,带时间
function changeCalendarDate1(year, month, day,hour,minute,second){
//alert(_calendarControl.hour+"/"+hour+"/"+_calendarControl.minute+"/"+minute+"/"+_calendarControl.second+"/"+second);
	if (_calendarControl.year==year && _calendarControl.month==month && (!day || _calendarControl.day==day) && _calendarControl.hour==hour && _calendarControl.minute==minute && _calendarControl.second==second ) 
		return;

	if (_calendarControl.year!=year || _calendarControl.month!=month){
		_calendarControl.year=year;
		_calendarControl.month=month;

		if (month==0){
			 _calendarControl.preMonth=11
			 _calendarControl.preYear=_calendarControl.year-1
		}else{
			 _calendarControl.preMonth=_calendarControl.month-1
			 _calendarControl.preYear=_calendarControl.year
		}
		if (month==11){
			_calendarControl.nextMonth=0
			_calendarControl.nextYear=_calendarControl.year+1
		}else{
			_calendarControl.nextMonth=_calendarControl.month+1
			_calendarControl.nextYear=_calendarControl.year

		}
		_calendarControl.startday=(new Date(year,month,1)).getDay()
		if (_calendarControl.startday==0) _calendarControl.startday=7
		var curNumdays=getNumberOfDays(_calendarControl.month,_calendarControl.year)
		var preNumdays=getNumberOfDays(_calendarControl.preMonth,_calendarControl.preYear)
		var nextNumdays=getNumberOfDays(_calendarControl.nextMonth,_calendarControl.nextYear)
		var startDate=preNumdays-_calendarControl.startday+1
		var endDate=42-curNumdays-_calendarControl.startday

		_calender_month.value=(_calendarControl.month+1);
		_calender_year.innerText=_calendarControl.year

		var datenum=0;
		for (var i=startDate;i<=preNumdays;i++){
			var cell = calendarData.cells[datenum+7];
			cell.monthAttribute="pre";
			cell.className="cell_trailing";
			cell.innerText=i;
			datenum++;
		}
		for (var i=1;i<=curNumdays;i++){
			var cell = calendarData.cells[datenum+7];
			cell.monthAttribute="cur";
			if (datenum != _calendarControl.activeCellIndex){
				cell.className="cell_day";
			}
			cell.innerText=i;
			datenum++;
		}
		for (var i=1;i<=endDate;i++){
			var cell = calendarData.cells[datenum+7];
			cell.monthAttribute="next";
			cell.className="cell_trailing";
			cell.innerText=i;
			datenum++;
		}
	}

	if (day) _calendarControl.day=day;
	//时间
	if (hour)  _calendarControl.hour=hour;
	if (minute) _calendarControl.minute=minute;
	if (second) _calendarControl.second=second;
	//alert("3:"+hour+":"+minute+":"+second);
	if (hour<10) hour="0"+hour;
	if (minute<10) minute="0"+minute;
	if (second<10) second="0"+second;
	_calender_hour.value=hour;
	_calender_minute.value=minute;
	_calender_second.value=second;
	_timetd.onkeydown=_time_onkeydown;
	setCalendarActiveCell(calendarData.cells[_calendarControl.day+_calendarControl.startday-1+7]);
}
//让选中的日高亮显示
function setCalendarActiveCell(cell){

	function setActiveCell(cellIndex){
		var cell = calendarData.cells[_calendarControl.activeCellIndex+7];
		if (cell.monthAttribute=="cur"){
			cell.className="cell_day";
		}
		else{
			cell.className="cell_trailing";
		}

		var cell = calendarData.cells[cellIndex+7];
		cell.className="cell_selected";

		_calendarControl.activeCellIndex=cellIndex;
	}

	if (cell.tagName.toLowerCase()!="td") return;
	var _activeCellIndex=cell.parentElement.rowIndex*7+cell.cellIndex-7;

	with(_calendarControl){
		if (activeCellIndex==_activeCellIndex) return;

		var monthAttribute=cell.monthAttribute;
		switch (monthAttribute){
			case "pre":{
				changeCalendarDate(preYear,preMonth,getNumberOfDays(preMonth,preYear)-startday+_activeCellIndex+1);
				setActiveCell(startday+day-1);
				break
			}
			case "cur":{
				changeCalendarDate(year,month,_activeCellIndex-startday+1);
				setActiveCell(_activeCellIndex);
				break
			}
			case "next":{
				changeCalendarDate(nextYear,nextMonth,_activeCellIndex-getNumberOfDays(month,year)-startday+1);
				setActiveCell(startday+day-1);
				break
			}
		}
	}
}

function _calendar_cell_onclick(cell){
	setCalendarActiveCell(cell);
	dropDownSelected();
}

function _calendar_onkeydown()
{
	switch(event.keyCode){
		case 33:{//PgUp
			if (event.ctrlKey){
				changeCalendarDate(_calendarControl.year-1,_calendarControl.month)
			}else{
				changeCalendarDate(_calendarControl.preYear,_calendarControl.preMonth)
			}
			break
		}
		case 34:{//PgDn
			if (event.ctrlKey){
				 changeCalendarDate(_calendarControl.year+1,_calendarControl.month)
			}else{
				 changeCalendarDate(_calendarControl.nextYear,_calendarControl.nextMonth)
			}
			break
		}
		case 35:{//End
		    	var index=getNumberOfDays(_calendarControl.month,_calendarControl.year) +_calendarControl.startday-1
			setCalendarActiveCell(calendarData.cells[index+7+7])
			break
		}
		case 36:{//Home
			setCalendarActiveCell(calendarData.cells[_calendarControl.startday+7+7])
			break
		}
		case 37:{//<--
			var index=_calendarControl.activeCellIndex-1;
			if (index<0) index=0;
			setCalendarActiveCell(calendarData.cells[index+7])
			break
		}
		case 38:{//上箭头
			if (_calendarControl.activeCellIndex<14){
				var day=getNumberOfDays(_calendarControl.preMonth,_calendarControl.preYear)+_calendarControl.day-7;
				setCalendarDate(new Date(_calendarControl.preYear, _calendarControl.preMonth, day));
			}
			else{
				var index=_calendarControl.activeCellIndex-7;
				setCalendarActiveCell(calendarData.cells[index+7]);
			}
			break
		}
		case 39:{//-->
			var index=_calendarControl.activeCellIndex+1;
			if (index>=calendarData.cells.length-7) index=calendarData.cells.length-8;
			setCalendarActiveCell(calendarData.cells[index+7])
			break
		}
		case 40:{//下箭头
			if (_calendarControl.activeCellIndex>41){
				var day=7-(getNumberOfDays(_calendarControl.month,_calendarControl.year)-_calendarControl.day);
				setCalendarDate(new Date(_calendarControl.nextYear, _calendarControl.nextMonth, day));
			}
			else{
				var index=_calendarControl.activeCellIndex+7;
				setCalendarActiveCell(calendarData.cells[index+7]);
			}
			break
		}
	}
}

function _calendar_today_onclick()
{
	changeCalendarDate(_calendarControl.todayYear,_calendarControl.todayMonth,_calendarControl.todayDay)
	var index=_calendarControl.todayDay+_calendarControl.startday-1;
	setCalendarActiveCell(calendarData.cells[index+7]);
	dropDownSelected();
}

function getNumberOfDays(month,year)
{
	var numDays=new Array(31,28,31,30,31,30,31,31,30,31,30,31)
	n=numDays[month]
	if (month==1 && (year%4==0 && year%100!=0 || year%400==0)) n++
	return n
}

// 日期选择结束,最后选中的值的地方
function dropDownSelected()
{
	var now = new Date();
	var _h =now.getHours();
	var _m = now.getMinutes();
	var _s = now.getSeconds();
	if(typeof(_calender_hour)=="object")
	{
		_h = _calender_hour.value; //时
		_m = _calender_minute.value; //分
		_s = _calender_second.value; //秒
		if (_h<10) _h="0"+_h;
		if (_m<10) _m="0"+_m;
		if (_s<10) _s="0"+_s;
	}
	
	//alert(h);
	var seldate =  new Date(_calendarControl.year, _calendarControl.month, _calendarControl.day,_h,_m,_s);
	var dropDownType=_dropdown_box.editor.getAttribute("dropdown");
	hideDropDownBox();
//switch(dropDownType)	
//alert(seldate);
	_nowSelectObject.value=formatDateTime(seldate, dropDownType);		// "date"
	//_nowSelectObject.value=formatDateTime(seldate, "date");
	_nowSelectObject.focus();
}

//打开时间选择
function dropDownLocate()
{
	var editor=_dropdown_parentbox.editor;
	var dropdown=_dropdown_parentbox.dropDown;
	
	var _date=new Date(editor.value.replace(/-/g, "/"));

	//var _date=new Date(editor.value);
	if (!isNaN(_date))
	{
	//alert("1:"+_date);
		setCalendarDate(_date); //设置初始值
	}
		
}
//建立时间选择，带时间的
	function createCalendar1(parent_element)
	{
		function calendar(){
		 	var today=new Date()
		 	this.todayDay=today.getDate();
			this.todayMonth=today.getMonth();
			this.todayYear=today.getFullYear();
		 	this.activeCellIndex=0;
		 	//加时间
		 	this.hour = today.getHours();
		 	this.minute = today.getMinutes();
		 	this.second = today.getSeconds();
		 	//if (this.hour<10) this.hour="0"+this.hour;
			//if (this.minute<10) this.minute="0"+this.minute;
			//if (this.second<10) this.second="0"+this.second;
		}
		
		_calendar_days=new Array("日","一","二","三","四","五","六");
		_calendarControl=new calendar();
	
		var tmpHTML="";
		tmpHTML+="<TABLE id=\"CalendarTable\" class=\"calendar\" width=200px cellspacing=0 cellpadding=1 rule=all>";
		tmpHTML+="<TR class=\"title\" valign=top><TD>";
		tmpHTML+="<TABLE WIDTH=100% CELLSPACING=1 CELLPADDING=0>";
		tmpHTML+="<TR><TD align=right>";
		tmpHTML+="<INPUT type=button class=\"niceButton3\" value=3 title=\""+"上一年"+"\" style=\"FONT-SIZE:8;FONT-FAMILY:webdings;WIDTH:20px;HEIGHT:20px\" onclick=\"changeCalendarDate(_calendarControl.year-1,_calendarControl.month)\">";
		tmpHTML+="</TD><TD width=1>";
		tmpHTML+="<INPUT id=\"_calender_year\" type=text class=editor size=4 maxlength=4 onpropertychange=\"return _calendar_year_onpropertychange()\">";
		tmpHTML+="</TD><TD align=left width=25px>";
		tmpHTML+="<INPUT type=button class=\"niceButton3\" value=4 title=\""+"下一年"+"\" style=\"FONT-SIZE:8;FONT-FAMILY:webdings;WIDTH:20px;HEIGHT:20px\" onclick=\"changeCalendarDate(_calendarControl.year+1,_calendarControl.month)\">";
		tmpHTML+="</TD>";
		tmpHTML+="<TD align=right width=25px>";
		tmpHTML+="<INPUT type=button class=\"niceButton3\" value=3 title=\""+"上个月"+"\" style=\"FONT-SIZE:8;FONT-FAMILY:webdings;WIDTH:20px;HEIGHT:20px\" onclick=\"changeCalendarDate(_calendarControl.preYear,_calendarControl.preMonth)\">";
		tmpHTML+="</TD><TD width=1>";
		tmpHTML+="<INPUT id=\"_calender_month\" type=text class=editor size=2 maxlength=2 onpropertychange=\"return _calendar_month_onpropertychange()\">";
		tmpHTML+="</TD><TD align=left>";
		tmpHTML+="<INPUT type=button class=\"niceButton3\" value=4 title=\""+"下个月"+"\" style=\"FONT-SIZE: 8;FONT-FAMILY:webdings;WIDTH:20px;HEIGHT:20px\" onclick=\"changeCalendarDate(_calendarControl.nextYear,_calendarControl.nextMonth)\">";
		tmpHTML+="</TD></TR>";
		tmpHTML+="</TABLE></TD></TR>";
	
		tmpHTML+="<TR><TD>";
		tmpHTML+="<TABLE border=1 bordercolor=silver id=\"calendarData\" HEIGHT=100% WIDTH=100% CELLSPACING=0 CELLPADDING=0 style=\"BORDER-COLLAPSE: collapse\"";
		tmpHTML+="onclick=\"_calendar_cell_onclick(event.srcElement)\">";
		tmpHTML+="<TR height=20px style=\"background-image: url(images/table_title.gif)\">";
		for (var i=0;i<=6;i++){
			tmpHTML+="<TD align=center>"+_calendar_days[i]+"</TD>";
		}
		tmpHTML+="</TR>";
		for(var i=0;i<=5;i++){
			tmpHTML+="<TR>";
			for(var j=0;j<=6;j++){
				tmpHTML+="<TD align=center></TD>";
			}
			tmpHTML+="</TR>";
		}
		tmpHTML+="</TABLE></TD></TR>";
	
		tmpHTML+="<TR class=\"footer\"><TD align=left id=_timetd>";
		//加时间输入。
		tmpHTML+="<INPUT id=\"_calender_hour\" type=text class=editor size=1.5 maxlength=2 value=\"\" onpropertychange=\"return _calendar_hour_onpropertychange()\">：<INPUT id=\"_calender_minute\" type=text class=editor size=1.5 maxlength=2  value=\"\" onpropertychange=\"return _calendar_minute_onpropertychange()\">：<INPUT id=\"_calender_second\" type=text class=editor size=1.5 maxlength=2  value=\"\" onpropertychange=\"return _calendar_second_onpropertychange()\">";
		tmpHTML+="&nbsp;<INPUT class=\"niceButton3\" type=button style=\"width:95px;\" id=\"button_today\" value=\""+"今天"+" "+_calendarControl.todayYear+"-"+(_calendarControl.todayMonth+1)+"-"+_calendarControl.todayDay+"\" onclick=\"_calendar_today_onclick()\"";
		tmpHTML+="</TD></TR></TABLE>";
		//alert(tmpHTML);
		if (parent_element)
			parent_element.innerHTML=tmpHTML;
		else
			document.body.innerHTML=tmpHTML;
		
		changeCalendarDate1(_calendarControl.todayYear,_calendarControl.todayMonth,_calendarControl.todayDay,_calendarControl.hour,_calendarControl.minute,_calendarControl.second);
	}
	
	//在时间输入的地方输入回车键则直接选中
function _time_onkeydown()
{
if(event.keyCode == 13){
//alert("cr");
		dropDownSelected();
		}
}
// 创建列表
function createListTable(parent_element)
{
	if(!_arr_sDropData)
		return false;
		
	var sDataIndex = parent_element.editor.getAttribute("dataindex");
	if (!sDataIndex)	// 没有设置获得数据
		return false;
	
	// 转换成数字
	var nDataIndex = parseInt(sDataIndex);
	if (isNaN(nDataIndex))
		return false;
	// 检查边界
	if(nDataIndex<0 || nDataIndex >= _arr_sDropData.length)
		return false;
	// 获得下拉数据，数据格式为 "1~aaa;2~bbb;3~ccc;"
	var sDropData = _arr_sDropData[nDataIndex];
	if(!sDropData)
		return;
		
	// 生成表格
	var nBeginIndex=0;
	var nEndIndex = 0;
	var sLineData
	var arrFields;
	var tmpHTML="<table width='100%' class='dropdowntable' cellspacing=0 cellpadding=2 rules=all style='border-width:1px; border-collapse: collapse' border='1'>";
	var nMinIndex = 0;
	
	var sTemp;
	var i;
	var nRows = 0;
	
	var arrayOfStrings = sDropData.split(";")
	for (var j=0; j < arrayOfStrings.length; j++)
	//while(true)
	{
		/*
		// 获得1行数据
		nEndIndex = sDropData.indexOf(";", nBeginIndex);
		if(nEndIndex < 0)
			break;
		sLineData = sDropData.substring(nBeginIndex, nEndIndex);

		// 获得列数据并解码
		arrFields = sLineData.split("~");
		*/
		if(arrayOfStrings[j] == "")
			continue;
		arrFields = arrayOfStrings[j].split("~");
		
		for (var i=0; i<arrFields.length; i++)
			arrFields[i] = decodeSpecialBytes(arrFields[i]);

		tmpHTML+="<tr onmouseover=\"mouse_over(this)\" onmouseout=\"mouse_out(this)\" onmousedown=\"mouse_down(this, \'";
		if(arrFields.length>1)
			sTemp = encodeToHtmlTagSource(arrFields[1]);
		else
			sTemp = encodeToHtmlTagSource(arrFields[0]);
		tmpHTML+=sTemp;
		tmpHTML+="\',\'";
		tmpHTML+=encodeToHtmlTagSource(arrFields[0]);
		tmpHTML+="\')\">";
		
		if(!_bIfShowFirstCol && arrFields.length > 1)
			nMinIndex = 1;
		else
			nMinIndex = 0;
		for (i=nMinIndex; i<arrFields.length; i++)
		{
			sTemp = encodeToHtmlTag(arrFields[i]);
			tmpHTML+="<td nowrap>"+sTemp+"</td>";
		}
		tmpHTML+="</tr>";
		nBeginIndex = nEndIndex + 1;
		nRows++;
	}
	
	tmpHTML+="</table>";

	// 调整下拉大小
	if(nRows<=10)
		_dropdown_box.style.overflowY="hidden";	// 小于10行，全部显示
	else
	{
		_dropdown_box.style.height=190;			// 注：height=190允许9行大小,超过则滚动
		_dropdown_box.style.overflowY="auto";	// 大于10行，限制高度显示
	}

	if (parent_element)
		parent_element.innerHTML=tmpHTML;
	else
		document.body.innerHTML=tmpHTML;
	
	return true;
}

function mouse_over(obj)
{
    obj.style.backgroundColor="#E4F0CC";
}

function mouse_out(obj)
{
    obj.style.backgroundColor="whitesmoke";
}

function mouse_down(obj, val,val_code)
{
    // 用户选择
	hideDropDownBox();
	if(!val)
		_nowSelectObject.value = "";	//"no selected";
	else
	{
		_nowSelectObject.value = val.toString();
		_nowSelectObject.setAttribute("codevalue", val_code);
	}
	_nowSelectObject.focus();
}

//------------------------------------------------------------
// 外部调用API
function setShowCodeCol(bIfShow)
{
	if(typeof(bIfShow) == "boolean")
		_bIfShowFirstCol = bIfShow;
}

function setListDropData(nIndex, sListData)
{
	if(nIndex < 0)		// || nIndex > 19)
		return false;
	
	if(!sListData)
		return false;
		
	_arr_sDropData[nIndex] = sListData;
	return true;
}

function getHookObjectIndex(obj)
{
	var nIndex = -1;
	// 查询对象
	for(var i=0; i<_nMaxHookNumber; i++)
	{
		if(obj == _arr_hookobj[i])
		{
			nIndex = i;
			break;
		}
	}
	return nIndex;
}

function addHookObject(obj, sType, nListIndex)
{
	if(!obj || getHookObjectIndex(obj) >= 0)	// || _nMaxHookNumber>=19 
		return;
	
	_arr_hookobj[_nMaxHookNumber] = obj;
	_arr_bHookEnabled[_nMaxHookNumber] = true;
	_nMaxHookNumber++;
	obj.setAttribute("dropdown", sType);
	//if(sType != "date" && sType != "timestamp")
	if(sType == "list")
		obj.setAttribute("dataindex", nListIndex.toString());
}

function editHookObject(obj, sType, nListIndex)
{
	if(getHookObjectIndex(obj) < 0)
		addHookObject(obj, sType, nListIndex);
	else
	{
		obj.setAttribute("dropdown", sType);
		//if(sType != "date" && sType != "timestamp")
		if(sType == "list")
			obj.setAttribute("dataindex", nListIndex.toString());
	}
}

function disableHookObject(obj)
{
	nIndex = getHookObjectIndex(obj);
	if(nIndex >= 0)
		_arr_bHookEnabled[nIndex] = false;
}

function enableHookObject(obj)
{
	nIndex = getHookObjectIndex(obj);
	if(nIndex >= 0)
		_arr_bHookEnabled[nIndex] = true;
}

function getCodeValue(obj)
{
	if(!obj)
		return "";
	var sCodeValue = obj.getAttribute("codevalue");
	if(!sCodeValue)
		return "";
	return sCodeValue.toString();
}

function initObjectValue(obj, sValue, sCodeValue)
{
	if(getHookObjectIndex(obj) < 0)
		return;
	else
	{
		obj.value =  sValue;
		if(sCodeValue)
			obj.setAttribute("codevalue", sCodeValue);
	}
}

// 初始化列表
function preSelectList(obj, sCodeValue)
{

	if(getHookObjectIndex(obj) < 0)
		return;
		
	var sDataIndex = obj.getAttribute("dataindex");
	if (!sDataIndex)	// 没有设置获得数据
		return false;
	
	// 转换成数字
	var nDataIndex = parseInt(sDataIndex);
	if (isNaN(nDataIndex))
		return false;
	// 检查边界
	if(nDataIndex<0 || nDataIndex >= _arr_sDropData.length)
		return false;
	// 获得下拉数据，数据格式为 "1~aaa;2~bbb;3~ccc;"
	var sDropData = _arr_sDropData[nDataIndex];
	if(!sDropData)
		return;
	var sValue = "";
	var arrayOfStrings = sDropData.split(";")
	var arr_items;
	for (var i=0; i < arrayOfStrings.length; i++)
	{
		if (arrayOfStrings[i]=="")
			break;
		arr_items = arrayOfStrings[i].split("~");
		if(arr_items[0] == sCodeValue)
		{
			if(arr_items.length>1)
				sValue = arr_items[1];
			else
				sValue = arr_items[0];
			break;
		}
	}
	
	obj.value =  sValue;
	if(sCodeValue)
		obj.setAttribute("codevalue", sCodeValue);
}

function disableObjectInput(obj, bDisable)
{
	if(typeof(bDisable) != "boolean")
		return;
	if(getHookObjectIndex(obj) < 0)
		return;
	else
	{
		obj.readOnly = bDisable;
	}
}

// 对字符串中的特殊字符进行编码，包括"~;"
function encodeSpecialBytes(sOrgin)
{
	var sEncodeStr = sOrgin.toString();
	sEncodeStr = sEncodeStr.replace(/`/g, "`z");
	sEncodeStr = sEncodeStr.replace(/~/g, "`x");
	sEncodeStr = sEncodeStr.replace(/;/g, "`y");
	return sEncodeStr;
}

// 对字符串中的特殊字符进行编码，包括"~;"
function decodeSpecialBytes(sEncOrgin)
{
	var sOrginStr = sEncOrgin.toString();
	sOrginStr = sOrginStr.replace(/`y/g, ";");
	sOrginStr = sOrginStr.replace(/`x/g, "~");
	sOrginStr = sOrginStr.replace(/`z/g, "`");
	return sOrginStr;
}

// 转换特殊文字为html标记
function encodeToHtmlTag(sOrgin)
{
/*
& -> &amp;
" -> &quot;
< -> &lt;
> -> &gt;
space -> &nbsp;
*/
	var sEncodeStr = sOrgin.toString();
	sEncodeStr = sEncodeStr.replace(/&/g, "&amp;");
	sEncodeStr = sEncodeStr.replace(/"/g, "&quot;");
	sEncodeStr = sEncodeStr.replace(/</g, "&lt;");
	sEncodeStr = sEncodeStr.replace(/>/g, "&gt;");
	sEncodeStr = sEncodeStr.replace(/ /g, "&nbsp;");
	//sEncodeStr = sEncodeStr.replace(/\r\n/g, "<p>");		// 注：<p>间隔太大，不建议使用这种方式
	sEncodeStr = sEncodeStr.replace(/\n/g, "<br>");
	sEncodeStr = sEncodeStr.replace(/\t/g, "&nbsp;&nbsp;&nbsp;");
	return sEncodeStr;
}

// 转换特殊文字为html标记脚本源代码，和上面区别是\->\\、"->\"、'->\'
function encodeToHtmlTagSource(sOrgin)
{
/*
& -> &amp;
\ -> \\
' -> \'
" -> \"
" -> &quot;
< -> &lt;
> -> &gt;
space -> &nbsp;
*/
	var sEncodeStr = sOrgin.toString();
	sEncodeStr = sEncodeStr.replace(/&/g, "&amp;");
	sEncodeStr = sEncodeStr.replace(/\\/g, "\\\\");
	sEncodeStr = sEncodeStr.replace(/'/g, "\\'");
	sEncodeStr = sEncodeStr.replace(/"/g, "\\\"");
	sEncodeStr = sEncodeStr.replace(/"/g, "&quot;");
	sEncodeStr = sEncodeStr.replace(/</g, "&lt;");
	sEncodeStr = sEncodeStr.replace(/>/g, "&gt;");
	sEncodeStr = sEncodeStr.replace(/ /g, "&nbsp;");
	sEncodeStr = sEncodeStr.replace(/\n/g, "\\n");
	sEncodeStr = sEncodeStr.replace(/\r/g, "\\r");
	sEncodeStr = sEncodeStr.replace(/\t/g, "\\t");
	return sEncodeStr;
}

function getIEVersion(){
	var index=window.clientInformation.userAgent.indexOf("MSIE");
	if (index<0){
		return "";
	}
	else{
		return window.clientInformation.userAgent.substring(index+5, index+8);
	}
}

_sIEVersion = getIEVersion();
if (_sIEVersion<"5.0")
{
	alert("浏览本页面需要 Microsoft Internet Explorer 5.0 或更高版本的浏览器支持!\n请升级或安装IE5.0以上版本浏览器。");
}

//----------------------------------------
// 下面是用隐藏会覆盖层的几种对象，效果不是很好，不采用
function showSelectObj() 
{
	showElement("IFRAME");
	showElement("OBJECT");
	showElement("SELECT");
}

function hideSelectObj(obj)
{
	var ox = parseInt(obj.style.left);
	var oy = parseInt(obj.style.top);
	var ow = obj.offsetWidth;
	var oh = obj.offsetHeight;
	
	hideElement("IFRAME", ox, oy, ow, oh);
	hideElement("OBJECT", ox, oy, ow, oh);
	hideElement("SELECT", ox, oy, ow, oh);
}

function showElement(elmID)
{
  for (i = 0; i < document.all.tags(elmID).length; i++)    
  {
        obj = document.all.tags(elmID)[i];
        if (! obj || ! obj.offsetParent)
            continue;
        obj.style.visibility = "";
    }
}

function hideElement(elmID, ox, oy, ow, oh)
{
    ox = parseInt(ox);
    oy = parseInt(oy);
    ow = parseInt(ow);
    oh = parseInt(oh);
    for (i = 0; i < document.all.tags(elmID).length; i++)    
    {
        obj = document.all.tags(elmID)[i];
        if (! obj || ! obj.offsetParent)
            continue;
        objLeft = obj.offsetLeft;
        objTop = obj.offsetTop;
        objParent = obj.offsetParent;
        while (objParent.tagName.toUpperCase() != "BODY")    
        {
            objLeft += objParent.offsetLeft;
            objTop += objParent.offsetTop;
            objParent = objParent.offsetParent;
        }
		if ( (ox > (objLeft+obj.offsetWidth)) || (objLeft > (ox+ow)) || (objTop > (oy+oh)) || (oy>(objTop+obj.offsetHeight)) )
			obj.style.visibility = "";
		else
			obj.style.visibility = "hidden";
    }
}
//判断小时输入对否
function _calendar_hour_onpropertychange()
{
	var l_value = _calender_hour.value;
	if (isNaN(l_value) || l_value.indexOf("-")>=0 || l_value.indexOf(".")>=0)
	{
		alert("小时只能输入整数");
		_calender_hour.value="";
		_calender_hour.focus();
	}
	if(l_value>23)
	{
		alert("小时不能超过23");
		_calender_hour.value="";
		_calender_hour.focus();
	}
	
}
//判断分钟输入对否
function _calendar_minute_onpropertychange()
{
	var l_value = _calender_minute.value;
	if (isNaN(l_value) || l_value.indexOf("-")>=0 || l_value.indexOf(".")>=0)
	{
		alert("分钟只能输入整数");
		_calender_minute.value="";
		_calender_minute.focus();
	}
	if(l_value>59)
	{
		alert("分钟不能超过59");
		_calender_minute.value="";
		_calender_minute.focus();
	}
	
}
//判断秒输入对否
function _calendar_second_onpropertychange()
{
	var l_value = _calender_second.value;
	if (isNaN(l_value) || l_value.indexOf("-")>=0 || l_value.indexOf(".")>=0)
	{
		alert("秒钟只能输入整数");
		_calender_second.value="";
		_calender_second.focus();
	}
	if(l_value>59)
	{
		alert("秒钟不能超过59");
		_calender_second.value="";
		_calender_second.focus();
	}
	
}
//创建树型类型 modify by zlf 20091215 用代码选择控件来生成树形选择。

function createTree()
{
	var iscreate = false;
	var sFieldName = getCodeValue(frmCondItems.keyFields);
	sFieldName = sFieldName.substring(3);
	if(codetype!="" && codetype.indexOf(";")!=-1)
	{
		var cc = codetype.split(";");
		for(var i=0;cc && cc.length>0 && i<cc.length;i++)
		{
			var ccc = cc[i];
			var cccc = ccc.split("~");
			if(cccc && cccc.length==2 && cccc[0] == sFieldName)
			{
				 SetParam(frmCondItems.treeinput, "CodeType", cccc[1]);
				 updateCodeSelectInput(frmCondItems.treeinput);
				 iscreate = true;
			}
		}
	}
	if(!iscreate)
	{
		alert(sFieldName+"没有用setCodeType方法设置树形代码!");
		return false;
	}
}
//把选中的值赋给inputKey2/ 显示值赋值给inputKey1
function setTreeValue(objName)
{
	var objSelect = document.getElementById(objName);
	var objSelVal = getSelectValue(objSelect);
	var objShowVal = getSelectShowValue(objSelect);
	document.getElementById("inputKey2").value= objSelVal;
	document.getElementById("inputKey1").value= objShowVal;
	//alert(objSelVal);
}