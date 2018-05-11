/*
Copyright 2012, KISSY UI Library v1.40dev
MIT Licensed
build time: Nov 14 21:54
*/
KISSY.add("suggest",function(e,d,i,n,k){function j(a,b,c){if(!(this instanceof j))return new j(a,b,c);this.textInput=d.get(a);this.config=c=e.merge(x,c);if("string"==typeof b){if(b+=-1===b.indexOf("?")?"?":"&",this.dataSource=b+c.callbackName+"="+(a=c.callbackFn),2===c.dataType&&(this.config.dataType=0),a!==s)b=a,c=b.split("."),a=c.length,1<a?(b=b.replace(/^(.*)\..+$/,"$1"),b=e.namespace(b,!0),b[c[a-1]]=q):o[b]=q}else this.dataSource=b,this.config.dataType=2;this.queryParams=this.query=g;this._dataCache=
{};this._init();return 0}function t(a,b){1===b.nodeType?(d.html(a,g),a.appendChild(b)):d.html(a,b)}function q(a){j.focusInstance&&e.later(function(){j.focusInstance._handleResponse(a)},0)}var o=e.Env.host,y=i.Target,l=o.document,m,u=d.get("head"),p=n.ie,v=9<=p,s="KISSY.Suggest.callback",g="",w=parseInt,z=/^(?:input|button|a)$/i,x={containerCls:g,resultFormat:"%result%",closeBtnText:"\u5173\u95ed",shim:6===p,submitOnSelect:!0,offset:-1,charset:"utf-8",callbackName:"callback",callbackFn:s,queryName:"q",dataType:0};
e.augment(j,y,{_init:function(){m=l.body;this._initTextInput();this._initContainer();this.config.shim&&this._initShim();this._initStyle();this._initEvent()},_initTextInput:function(){var a=this,b=a.textInput,c=!1,f=0;d.attr(b,"autocomplete","off");a.config.autoFocus&&b.focus();i.on(b,"keydown",function(d){var h=d.keyCode;if((35==h||36==h)&&!b.value)d.halt();else{if(27===h)a.hide(),b.value=a.query;else if(32<h&&41>h)if(b.value){if(40===h||38===h)0===f++?(a._isRunning&&a.stop(),c=!0,a._selectItem(40===
h)):3==f&&(f=0),d.preventDefault()}else b.blur();else if(13===h){b.blur();if(c&&b.value==a._getSelectedItemKey()&&!1===a.fire("itemSelect"))return;a._submitForm()}else a._isRunning||a.start(),c=!1;n.chrome&&(a._keyTimer&&a._keyTimer.cancel(),a._keyTimer=e.later(function(){a._keyTimer=k},500))}});i.on(b,"keyup",function(){f=0});i.on(b,"blur",function(){a.stop();e.later(function(){a._focusing||a.hide()},0)})},_initContainer:function(){var a=this.config.containerCls,a=d.create("<div>",{"class":"ks-suggest-container"+
(a?" "+a:g),style:"position:absolute;visibility:hidden"}),b=d.create("<div>",{"class":"ks-suggest-content"}),c=d.create("<div>",{"class":"ks-suggest-footer"});a.appendChild(b);a.appendChild(c);m.insertBefore(a,m.firstChild);this.container=a;this.content=b;this.footer=c;this._initContainerEvent()},_setContainerRegion:function(){var a=this.config,b=this.textInput,c=d.offset(b),f=this.container;d.offset(f,{left:c.left,top:c.top+b.offsetHeight+a.offset});d.width(f,a.containerWidth||b.offsetWidth-2)},
_initContainerEvent:function(){var a=this,b=a.textInput,c=a.container,f=a.content,r=a.footer,h,g;i.on(f,"mousemove",function(b){a._keyTimer||(b=b.target,"LI"!==b.nodeName&&(b=d.parent(b,"li")),d.contains(f,b)&&b!==a.selectedItem&&(a._removeSelectedItem(),a._setSelectedItem(b)))});i.on(f,"mousedown",function(a){a=a.target;"LI"!==a.nodeName&&(a=d.parent(a,"li"));h=a});i.on(c,"mousedown",function(a){z.test(a.target.nodeName)||(n.ie&&9>n.ie&&(b.onbeforedeactivate=function(){o.event.returnValue=!1;b.onbeforedeactivate=
null}),a.preventDefault())});i.on(f,"mouseup",function(c){var e=c.target;if(!(2<c.which)&&("LI"!==e.nodeName&&(e=d.parent(e,"li")),e==h&&d.contains(f,e)&&(a._updateInputFromSelectItem(e),!1!==a.fire("itemSelect")))){try{b.blur()}catch(g){}a._submitForm()}});i.on(r,"focusin",function(){a._focusing=!0;a._removeSelectedItem();g=!1});i.on(r,"focusout",function(){a._focusing=!1;e.later(function(){g?a.hide():a._focusing||a.textInput.focus()},0)});i.on(a.container,"mouseleave",function(){g=!0});i.on(r,"click",
function(b){d.hasClass(b.target,"ks-suggest-closebtn")&&a.hide()})},_submitForm:function(){if(this.config.submitOnSelect){var a=this.textInput.form;if(a&&!1!==this.fire("beforeSubmit",{form:a})){if(l.createEvent){var b=l.createEvent("MouseEvents");b.initEvent("submit",!0,!1);a.dispatchEvent(b)}else l.createEventObject&&a.fireEvent("onsubmit");a.submit()}}},_initShim:function(){var a=d.create("<iframe>",{src:"about:blank","class":"ks-suggest-shim",style:"position:absolute;visibility:hidden;border:none"});
this.container.shim=a;m.insertBefore(a,m.firstChild)},_setShimRegion:function(){var a=this.container,b=a.style,c=a.shim;c&&d.css(c,{left:w(b.left)-2,top:b.top,width:w(b.width)+2,height:d.height(a)-2})},_initStyle:function(){d.get("#ks-suggest-style")||d.addStyleSheet(".ks-suggest-container{background:white;border:1px solid #999;z-index:99999}.ks-suggest-shim{z-index:99998}.ks-suggest-container li{color:#404040;padding:1px 0 2px;font-size:12px;line-height:18px;float:left;width:100%}.ks-suggest-container .ks-selected{background-color:#39F;cursor:default}.ks-suggest-key{float:left;text-align:left;padding-left:5px}.ks-suggest-result{float:right;text-align:right;padding-right:5px;color:green}.ks-suggest-container .ks-selected span{color:#FFF;cursor:default}.ks-suggest-footer{padding:0 5px 5px}.ks-suggest-closebtn{float:right}.ks-suggest-container li,.ks-suggest-footer{overflow:hidden;zoom:1;clear:both}.ks-suggest-container{*margin-left:2px;_margin-left:-2px;_margin-top:-3px}",
"ks-suggest-style")},_initEvent:function(){var a=this;i.on(o,"resize",function(){a._setContainerRegion();a._setShimRegion()})},start:function(){var a=this;!1!==a.fire("beforeStart")&&(j.focusInstance=a,a._timer=e.later(function(){a._updateContent();a._timer=e.later(arguments.callee,200)},200),a._isRunning=!0)},stop:function(){j.focusInstance=k;this._timer&&this._timer.cancel();this._isRunning=!1},show:function(){if(!this.isVisible()){var a=this.container,b=a.shim;this._setContainerRegion();a.style.visibility=
g;b&&(this._setShimRegion(),b.style.visibility=g)}},hide:function(){if(this.isVisible()){var a=this.container,b=a.shim;b&&(b.style.visibility="hidden");a.style.visibility="hidden"}},isVisible:function(){return"hidden"!=this.container.style.visibility},_updateContent:function(){var a=this.textInput;if(a.value!=this.query)if(a=this.query=a.value,e.trim(a))switch(this.config.dataType){case 0:this._dataCache[a]!==k?(this._fillContainer(this._dataCache[a]),this._displayContainer()):this._requestData();
break;case 1:this._requestData();break;case 2:this._handleResponse(this.dataSource[a])}else this._fillContainer(),this.hide()},_requestData:function(){var a=this,b=a.config,c;if(!p||v)a.dataScript=k;if(!a.dataScript&&(c=l.createElement("script"),c.charset=b.charset,c.async=!0,u.insertBefore(c,u.firstChild),a.dataScript=c,!p||v)){var f=e.now();a._latestScriptTime=f;d.attr(c,"data-time",f);i.on(c,"load",function(){a._scriptDataIsOut=d.attr(c,"data-time")!=a._latestScriptTime})}a.queryParams=b.queryName+
"="+encodeURIComponent(a.query);!1!==a.fire("beforeDataRequest")&&(a.dataScript.src=a.dataSource+"&"+a.queryParams)},_handleResponse:function(a){var b=g;this._scriptDataIsOut||(this.returnedData=a,!1!==this.fire("dataReturn",{data:a})&&(b=this.config.contentRenderer?this.config.contentRenderer(a):this._renderContent(a),this._fillContainer(b),!1!==this.fire("beforeShow")&&(this.config.dataType||(this._dataCache[this.query]=d.html(this.content)),this._displayContainer())))},_renderContent:function(){var a,
b=g,c,f,e,h;a=this._formatData(this.returnedData);if(0<(c=a.length)){f=d.create("<ol>");for(b=0;b<c;++b)e=a[b],e=this._formatItem(h=e.key,e.result),d.attr(e,"key",h),d.addClass(e,b%2?"ks-even":"ks-odd"),f.appendChild(e);b=f}return b},_formatData:function(a){var b=[],c,d,g,h=0;if(!a)return b;e.isArray(a.result)&&(a=a.result);if(!(c=a.length))return b;for(g=0;g<c;++g)d=a[g],"string"==typeof d?b[h++]={key:d}:e.isArray(d)&&1<d.length&&(b[h++]={key:d[0],result:d[1]});return b},_formatItem:function(a,b){var c=
d.create("<li>"),f;c.appendChild(d.create("<span>",{"class":"ks-suggest-key",html:a}));b&&(f=this.config.resultFormat.replace("%result%",b),e.trim(f)&&c.appendChild(d.create("<span>",{"class":"ks-suggest-result",html:f})));return c},_fillContainer:function(a,b){this._fillContent(a||g);this._fillFooter(b||g);this.isVisible()&&this._setShimRegion()},_fillContent:function(a){t(this.content,a);this.selectedItem=k},_fillFooter:function(a){var b=this.config,c=this.footer;t(c,a);b.closeBtn&&c.appendChild(d.create("<a>",
{"class":"ks-suggest-closebtn",text:b.closeBtnText,href:"javascript: void(0)",target:"_self"}));this.fire("updateFooter",{footer:c,query:this.query});d.css(c,"display",d.text(c)?g:"none")},_displayContainer:function(){e.trim(d.text(this.container))?this.show():this.hide()},_selectItem:function(a){var b=d.query("li",this.container);if(0!==b.length)if(this.isVisible()){if(this.selectedItem?(a=b[e.indexOf(this.selectedItem,b)+(a?1:-1)],a||(this.textInput.value=this.query)):a=b[a?0:b.length-1],this._removeSelectedItem(),
a)this._setSelectedItem(a),this._updateInputFromSelectItem()}else this.show()},_removeSelectedItem:function(){d.removeClass(this.selectedItem,"ks-selected");this.selectedItem=k},_setSelectedItem:function(a){d.addClass(a,"ks-selected");this.selectedItem=a;this.textInput.focus()},_getSelectedItemKey:function(){return!this.selectedItem?g:d.attr(this.selectedItem,"key")},_updateInputFromSelectItem:function(){this.textInput.value=this._getSelectedItemKey(this.selectedItem)||this.query}});j.version=1.1;
j.callback=q;return e.Suggest=j},{requires:["dom","event","ua"]});
