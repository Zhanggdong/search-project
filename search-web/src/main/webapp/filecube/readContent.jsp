<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="net.risesoft.filecube.util.Constant"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
  pageContext.setAttribute("ctx", path);
  pageContext.setAttribute("serverip",Constant.serverip);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>数据读取</title>
<script type="text/javascript" src="${ctx}/commons/javascript/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/jquery/ui/jquery-ui-1.9.1.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/suggest/kissy-min.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/suggest/suggest-min.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/commons/javascript/jquery-plugin/jquery-dateFormat.min.js"></script>
<script type="text/javascript" src="${ctx}/filecube/js/permission.js"></script>
<link rel="stylesheet" href="${ctx}/commons/bootstrap/dist/css/bootstrap.min.css"/>
<script src="${ctx}/commons/bootstrap/dist/js/bootstrap.min.js"></script>
<script type="text/javascript">
	var serverip = "${serverip}";
    var contextPath = "${ctx}";
    var _globalCondition = "${folderLevels}";
    var pageCount;
    $(function(){
    	/* setInterval(function(){
			$("#progress").load(location.href+"#progress>*","");
		},5000); */
    	//1秒后重复执行该函数 
        setInterval('showProgress', 1000);
		$("#submit").unbind("click").click(function(){
			submit();		
    	});
    });
    //提交事件
    function submit(){
    	var zwIsRead = $('#zwIsRead option:selected').val();
    	var fjIsRead = $('#fjIsRead option:selected').val();
    	if(zwIsRead==1&&fjIsRead==1){
    		alert("不能同时读取两个表中的数据");
    		return;
    	}
    	if(zwIsRead==0&&fjIsRead==0){
    		alert("请选择需要读取的数据");
    		return;
    	} 	
    	
    	$.ajax({
    		url:contextPath+"/content/readfile?"+$("#form").serialize()+"&type="+$("#submit").val(),
    		type:"get",
    		cache:false,
    		success:function(data){
    			//alert("本次共查询到"+data.pageCount+"条记录，成功读取"+data.count+"条记录");			
    			$("#progress").css("display","block");
    			pageCount = data.pageCount;
    		}
    	}); 
    	
    }
    
    //显示进度条
    function showProgress(){
    	$("#progress").css("display","block");
    	$.ajax({
    		type : "get",
    		async : false, //同步请求
    		cache:false,
    		url : contextPath+"/content/getQuantity?"+$("#form").serialize()+"&pageCount"+pageCount,
    		//timeout:1000,
    		success:function(data){
    			/* var width = (pageCount-data.count)/pageCount;
    			$("#progress-bar").css("width",width);
    			$("#progress").html();//要刷新的div */
    			alert(data.count);
    		}
    	});
    }
    
</script>
</head>
<body>
<div class="maincontainer"></div>
<form class="form-horizontal" role="form" id="form">
  <fieldset>
      <legend>配置线程池任务</legend>
     <div class="form-group">
        <label class="col-sm-2 control-label" for="ds_host">任务数</label>
        <div class="col-sm-4">
           <input class="form-control" id="tasktotal" type="text" name="tasktotal" placeholder="默认5个任务"/>
        </div>
     </div>
  </fieldset>     
  <fieldset>
       <legend>是否读取正文内容</legend>
      <div class="form-group">
         <label for="disabledSelect"  class="col-sm-2 control-label">读取数据</label>
         <div class="col-sm-10">
            <select name="zwIsRead" id="zwIsRead" class="form-control">
               <option value="0">否</option>
               <option value="1">是</option>
            </select>
         </div>
      </div>
  </fieldset>
     <fieldset>
       <legend>是否读取附件内容</legend>
      <div class="form-group">
         <label for="disabledSelect"  class="col-sm-2 control-label">读取数据</label>
         <div class="col-sm-10">
            <select name="fjIsRead" id="fjIsRead" class="form-control">
               <option value="0">否</option>
               <option value="1">是</option>
            </select>
         </div>
      </div>    
  </fieldset>
  <div id="progress" class="progress" style="display:none">
	<div id="progress-bar" class="progress-bar progress-bar-warning" role="progressbar"
		 aria-valuenow="100%" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
		<span class="sr-only">0% 完成（警告）</span>
	</div>
</div>
   <button  type="button" id="submit" class="btn btn-success">确定</button>
   <button  type="button" id="cancel" class="btn btn-success">取消</button>
 </form>

</body>
</html>
