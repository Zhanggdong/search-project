var _g_orginTableID = "";	// 初始表格ID
var _g_orginDivID = "";		// 上一次处理的目标层ID
var _g_ploy_title = "";

var _g_arr_data = new Array();
var _g_rowsCount = 0;
var _g_colsCount = 0;

var _g_polyWidth = 958;
var _g_polyHeight = 130;
var _g_polyLeft = 2;
var _g_ployTop = 4;

var _g_max_y = 0;	// y轴
var _g_max_x = 0;	// x轴

var _g_PloyContent = "";// 内容
var _g_divTop = 0;
var _g_blank_x = 0;		// 间隔
var _g_left_f = 0;		// 左边开始位置

var _tit_marginLeft = _g_polyWidth/2;
var _tit_marginTop = 5;
var _tit_width = 200;

var _bj_mar_left = 590;

var _$ = document;

// 读取表格内容到内存中
function loadTabContents(idTable){
	_g_orginTableID = idTable;	// 设置处理的表格ID
	var oTable = _$.getElementById(_g_orginTableID);
	var i=0, j=0;
	var temp = 0;
	var temp2 = 0;
	_g_rowsCount = oTable.rows.length;		// 行数	
	_g_colsCount = oTable.rows(0).cells.length;	// 列数
	
	for (i=0; i <_g_rowsCount; i++){
		_g_arr_data[i] = new Array();
		for(j=0; j<_g_colsCount; j++){
			_g_arr_data[i][j] = oTable.rows(i).cells(j).innerText;
		}
	}
	_g_max_x = Math.floor((_g_colsCount-2) / 2);
	// 计算最大值
	for (i=1; i <_g_rowsCount; i++){
		for(j=0; j<_g_max_x; j++){
			temp = convertInt(_g_arr_data[i][j*2+2]);
			if(temp>_g_max_y)
				_g_max_y = temp;
		}
	}
	oTable.display='none';
}
// 转换成整数
function convertInt(str){
	var ret = 0;
	ret = parseInt(str);
	if(isNaN(ret) || ret<0)
		ret = 0;
	return ret;
}
// 转换成浮点数
function convertFloat(str){
	var ret = 0.0;
	ret = parseFloat(str);
	if(isNaN(ret))
		ret = 0.0;
	return ret;
}
function convertPercent(p){
	var m = 0;
	try{
		m = Math.floor(p*10000+0.5) / 100;
	}catch(e){
		m = 0;
	}
	return m;
}
function drawBarBackground(){
	var nLeft = _g_polyLeft;
	var nWidth = _g_polyWidth;
	var nTop = _g_ployTop+_g_polyHeight;
	var i=0;	
	
	//_g_PloyContent+="<v:RoundRect opacity = '0.1' id='ssdaaa' arcsize='0.3' style='font-size:12px;position:absolute;top:2px;left:380px;";
	//_g_PloyContent+="width:338px;height:24px;line-height:15px;z-index:1' stroked='t' strokecolor='#96c2f1' StrokeWeight='1' fillcolor='#eff7ff'>";
	//_g_PloyContent+="<v:shadow on='t' type='single' color='#ccc' offset='4px,4px'/>";
	//_g_PloyContent+="<v:TextBox id='to_tit_v' style='font-size:11px;COLOR:#f81ad2;'><span id='to_tit_v' style='z-index:9999999;color:#000000'></span></v:TextBox>";
	//_g_PloyContent+="</v:RoundRect>";
	
	//代替了浏览器自带的tips
	_g_PloyContent+="<v:RoundRect opacity = '0.1' id='sd' arcsize='0.1' style='font-size:12px;position:absolute;display:none;";
	_g_PloyContent+="width:110px;height:23px;line-height:23px;z-index:999999' stroked='t' StrokeWeight='2' fillcolor='#ffffff'>";
	//_g_PloyContent+="<v:shadow on='t' type='single' color='#ccc' offset='5px,5px'/>";
	_g_PloyContent+="<v:TextBox style='font-size:11px;COLOR:#f81ad2;'></v:TextBox>";
	_g_PloyContent+="</v:RoundRect>";
	
	
	//办结率
	//_g_PloyContent+="<v:RoundRect opacity = '0.1' id='ssd' arcsize='0.1' style='display:none；font-size:12px;position:absolute;top:2px;left:380px;";
	//_g_PloyContent+="width:114px;height:23px;line-height:15px;z-index:99999' stroked='t' strokecolor='#96c2f1' StrokeWeight='1' fillcolor='#eff7ff'>";
	//_g_PloyContent+="<v:shadow on='t' type='single' color='#ccc' offset='4px,4px'/>";
	//_g_PloyContent+="<v:TextBox style='font-size:11px;COLOR:#f81ad2;'><span id='to_tit_v' style='color:#000000'></span></v:TextBox>";
	//_g_PloyContent+="</v:RoundRect>";
	
	//头部的title	
	_tit_width = _$.getElementById("to_tit").value.length*14;
	_g_PloyContent+="<v:Textbox style='POSITION: absolute;top:"+_tit_marginTop+";left:"+_tit_marginLeft+";Z-INDEX:3003;FONT-SIZE:12;COLOR:#336699;width:"+_tit_width+";heigth:16;line-height:16px;text-align:left' inset='5pt,5pt,5pt,5pt' print='t'><span id='to_tit_v'></span></v:Textbox>";	
	//头部的图标
	for( var i=1;i<_g_rowsCount;i++){
		var tp_val = i;
		if(i>1)
			_bj_mar_left = _bj_mar_left+115;
		_g_PloyContent+="<v:oval onclick='hideLine("+tp_val+")' style='cursor:hand;Z-INDEX: 9058; POSITION: absolute; WIDTH: 12px; HEIGHT: 12px;top:7px;left:"+_bj_mar_left+"px' coordsize = '21600,21600' strokecolor = '"+_g_arr_data[i][1]+"' fillcolor='"+_g_arr_data[i][1]+"' strokeweight = '1pt'>";
		_g_PloyContent+="<v:shadow on='t' type='single' color='#ccc' offset='3px,3px'/>";
		_g_PloyContent+="<v:fill type = 'gradient' opacity = '1' angle = '135' method = 'sigma' colors = '39321f'>";
		_g_PloyContent+="<v:Textbox style='padding-left:15px;Z-INDEX:3003;FONT-SIZE:12;COLOR:#000000;width:225;heigth:16;line-height:17px;text-align:left' inset='5pt,5pt,5pt,5pt' print='t'>&nbsp;"+_g_arr_data[i][0]+"</v:Textbox>";
		_g_PloyContent+="</v:fill>";
		_g_PloyContent+="</v:oval>";
	}
	for(i=0; i<=5; i++){
		_g_PloyContent+="<v:Textbox style='POSITION:absolute;Z-INDEX:3004;LEFT:"+(nLeft)+";TOP:"+(nTop+_g_divTop-4)+";FONT-SIZE:12;COLOR:#808080;width:30;text-align:right' inset='5pt,5pt,5pt,5pt' print='t'>"+(_g_max_y/5*i)+"</v:Textbox>";
		_g_PloyContent+="<v:line style='POSITION:absolute;Z-INDEX:3002;' strokecolor='#cccccc' from='"+(nLeft+37)+"px,"+nTop+"px' to='"+(nLeft+nWidth+5)+"px,"+nTop+"px'/>";
		nTop -= Math.floor(_g_polyHeight/5);
	}
	_g_left_f = nLeft+40;
	_g_blank_x = Math.floor( (_g_polyWidth-_g_left_f-10) / (_g_max_x-1));
	nTop = _g_ployTop+_g_polyHeight;
	for(i=0; i<_g_max_x; i++){
		_g_PloyContent+="<v:line style='POSITION:absolute;Z-INDEX:3002;' strokecolor='#5c9cc0' from='"+(_g_left_f+_g_blank_x*i)+"px,"+nTop+"px' to='"+(_g_left_f+_g_blank_x*i)+"px,"+(nTop-5)+"px'/>";
		_g_PloyContent+="<v:Textbox style='POSITION:absolute;Z-INDEX:3003;LEFT:"+(_g_left_f+_g_blank_x*i-10)+"px;TOP:"+(nTop+_g_divTop+5)+"px;FONT-SIZE:12;COLOR:#808080;width:25;text-align:center' inset='5pt,5pt,5pt,5pt' print='t'>"+_g_arr_data[0][i*2+2]+"</v:Textbox>";
	}
}

function drawPolyLine(){
	var i=0, j=0;
	var h = 0, h0 = 0;
	var nTop = _g_ployTop+_g_polyHeight;
	var R = 4;
	for(i=1; i<_g_rowsCount; i++){
		for(j=0; j<_g_max_x; j++){
			h = Math.floor( convertInt(_g_arr_data[i][j*2+2]) / _g_max_y * _g_polyHeight + 0.5);
			// 画点
			//加上了渐变效果
			_g_PloyContent+="<v:oval id='a"+i+"yy"+j+"'onmouseout='moveout("+(_g_left_f+_g_blank_x*j-R)+","+(nTop+_g_divTop-h-R)+","+i+","+j+")' onmouseover='moveup("+(_g_left_f+_g_blank_x*j-R)+","+(nTop+_g_divTop-h-R)+","+i+","+j+")' style='cursor:hand;POSITION:absolute; WIDTH: 20px; HEIGHT:20px;Z-INDEX:3003;LEFT:"+(_g_left_f+_g_blank_x*j-R)+"px;TOP:"+(nTop+_g_divTop-h-R)+"px;width:"+(R*2)+";height:"+(R*2)+";'fillcolor='"+_g_arr_data[i][1]+"' coordsize = '21600,21600' strokecolor ='"+_g_arr_data[i][1]+"' strokeweight = '1pt'>";
			_g_PloyContent+="<v:fill type = 'gradient' opacity ='1' angle = '135' method = 'sigma' colors = '39321f'></v:fill></v:oval>";
			// 画线
			if(j>0)
				_g_PloyContent+="<v:line id='a"+i+"ll"+(j-1)+"' style='POSITION:absolute;Z-INDEX:3002;'from='"+(_g_left_f+_g_blank_x*(j-1))+"px,"+(nTop-h0)+"px' to='"+(_g_left_f+_g_blank_x*j)+"px,"+(nTop-h)+"px' strokecolor='"+_g_arr_data[i][1]+"' strokeweight='2px'></v:line>";
			h0 = h;
		}
	}
}

// 圆整最大值(5格)，最小值10
function roundMaxValue(){
	var breakValue = Math.ceil(_g_max_y / 5.0);
	var item = 1;
	if(breakValue<=2)
		breakValue = 2;
	if(breakValue<=10){
		_g_max_y = breakValue * 5;
		return;
	}
	// 整理 breakValue在10-100之间
	while(1){
		if(breakValue>100){
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
	_g_max_y = item * breakValue * 5;
}

// 绘制图形
function drawPloy(idDiv){
	_g_PloyContent = "";
	_g_divTop = _$.getElementById("divPloy").offsetTop;
	// 圆整最大值
	roundMaxValue();
	// 绘制背景
	//_bj_mar_left=_g_polyWidth-(_g_rowsCount-1)*75;
	_tit_marginLeft = _g_polyWidth-(_g_rowsCount-1)*75-_tit_width-100;
	drawBarBackground();
	// 绘图
	drawPolyLine();
	_$.getElementById(idDiv).innerHTML = _g_PloyContent;
	_$.getElementById("to_tit_v").innerHTML = _$.getElementById("to_tit").value;
	
}
//mouseover事件
function moveup(a1,b2,e5,f6){
	var temp_data;	
	var sd_height;
	if(f6==0)
		temp_data=_g_arr_data[e5][f6+3];
	else
		temp_data=_g_arr_data[e5][(f6*2)+3];		
	var temp_data_br = temp_data.split("-");	
	sd_height=convertInt(23*temp_data_br.length);
	sd.style.height = sd_height;
	sd.style.fontsize = '12px';
	sd.innerHTML="&nbsp;"+temp_data.replace(/-/g,"<br/>&nbsp;");
	sd.StrokeColor=_g_arr_data[e5][1];
	for(var t=1;t<_g_rowsCount;t++){
		if(e5==t){			
			for(var s=0; s<_g_max_x; s++)
				_$.getElementById("a"+t+"yy"+s).style.zIndex='89999';							
			for(var s=0; s<_g_max_x-1; s++){
				var a1_ll_temp = _$.getElementById("a"+t+"ll"+s);				
				a1_ll_temp.strokeweight=3;		
				a1_ll_temp.style.zIndex='89998';
			}
		}else{			
			for(var s=0; s<_g_max_x; s++)
				_$.getElementById("a"+t+"yy"+s).style.zIndex=89997-t;							
			for(var s=0; s<_g_max_x-1; s++){
				var a1_ll_temp = _$.getElementById("a"+t+"ll"+s);				
				a1_ll_temp.strokeweight='1.5pt';		
				a1_ll_temp.style.zIndex=89996-t;
			}
		}
	}
	var _a_yy_ = _$.getElementById("a"+e5+"yy"+f6);
	_a_yy_.style.width='15px';
	_a_yy_.style.height='15px';
	_a_yy_.style.top=b2-3;
	_a_yy_.style.left=a1-3;
	_a_yy_.strokeweight=0;	
	var mouse_left = a1+20;
	var mouse_top = b2+20;	
	var s_obj = sd.style;
	if(mouse_top+sd_height>_g_polyHeight){
		mouse_top = mouse_top-sd_height;
		if(mouse_top<sd_height)
			mouse_top = sd_height/2;
	}			
	if(mouse_left+convertInt(sd.style.width)+40>_g_polyWidth){
		mouse_left = mouse_left-convertInt(sd.style.width)-40;
	}
	s_obj.top = mouse_top;
	s_obj.left = mouse_left;
	s_obj.display='block';
}
//mouseout事件
function moveout(a1,b2,e5,f6){
	sd.style.display='none';
	var _a_yy2_ = _$.getElementById("a"+e5+"yy"+f6).style;
	_a_yy2_.width='8px';
	_a_yy2_.height='8px';
	_a_yy2_.top=b2;
	_a_yy2_.left=a1;
	for(var t=1;t<_g_rowsCount;t++){
		for(var s=0; s<_g_max_x-1; s++)
			_$.getElementById("a"+t+"ll"+s).strokeweight='1.5pt';		
	}
}
//click事件
var s_temp = 1;	
function hideLine(tempa){	
	var ss = 'none';
	if(s_temp==2){
		ss = 'block';s_temp=1;
	}else
		s_temp=2;	
	for(var t=1;t<_g_rowsCount;t++){
		for(var s=0; s<_g_max_x-1; s++){
			if(tempa==t){
				_$.getElementById("a"+t+"ll"+s).style.display=ss;
				_$.getElementById("a"+t+"yy"+s).style.display=ss;
				_$.getElementById("a"+t+"ll"+s).style.zIndex=99998;
				_$.getElementById("a"+t+"yy"+s).style.zIndex=99999;
				if(s==_g_max_x-2){
					_$.getElementById("a"+t+"yy"+(s+1)).style.display=ss;
					_$.getElementById("a"+t+"yy"+(s+1)).style.zIndex=99999;
				}
			}else{
				_$.getElementById("a"+t+"ll"+s).style.zIndex=9998;
				_$.getElementById("a"+t+"yy"+s).style.zIndex=9999;
				_$.getElementById("a"+t+"yy"+(s+1)).style.zIndex=9999;
			}
		}
	}
}