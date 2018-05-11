<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>文件评论</title>
<script type="text/javascript" src="../commons/javascript/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
   //修改评论tab title   
   $(function(){
      if (window.PIE) {
         $('.shadow').each(function() { 
            PIE.attach(this); 
         });
      }
      $('#addCommentary').click(function(){
         var content = $.trim($('#commentaryContent').val());
         if(content != ""){
           $.get('addcomment',{
             "fileGUID":'${param.fileGUID}',
             "content":content
          },function(data){
             if(data){
               var html ='<div style="border-bottom: 1px dashed #E5E5E5;display:none;padding-left: 10px;" class="commentaries" id=' + data.uuid + ' >';
               html += '<img src="../filecube/images/view/commentary.png" style="vertical-align: middle;"/> ';
               html += '<span style="padding-left: 5px;padding-top: 10px;">' + data.userName + ' &nbsp;';
               html += '发表时间：' + data.createDate + '&nbsp;&nbsp;'
               html += ' <a href=javascript:deletecomment("' + data.uuid + '"); >删除</a></span>'
               html += '<div style="margin-left: 40px;margin-top: 20px;margin-right: 40px;margin-bottom: 20px;">'+data.content+'</div>';
               html += '</div>';
               $('#commentariesDiv').prepend(html);   
               $('#' + data.uuid).show('slow');
               $('#commentaryContent').val('');                
             }else{
               alert('错误','服务器出现错误请稍后再试！');
             }
           });
         }
      });   
      
      $('#addCommentary').hover(
         function(){
            this.src = '../filecube/images/icons/pinglun_hover.png';
         },function(){
            this.src = '../filecube/images/icons/pinglun.png';
         }
      );
      //已发评论的鼠标移上 变色 
      $('.commentaries').hover(
         function(){          
            $(this).css('background-color','#C0C0C0');
         },
         function(){
            var myTag = parseInt($(this).attr('mytag'));
            if(myTag % 2 == 1){
               $(this).css('background-color','#FAFDFC');
            }else{
               $(this).css('background-color','');
            }  
         }
      );
   });
   //删除评论
   function deletecomment(commentGUID){
     $.get('delete?commentGUID='+commentGUID,function(data){
       if(data){
         $('#' + commentGUID).hide('slow');
         alert('删除成功！');
       }else{
         alert('删除失败！');
       }
     });
   }
</script>
<style type="text/css">
html {
	font-size: 12px;
}

a:link {
	color: blue;
	text-decoration: none;
}

.shadow {
	min-height: 650px;
	width: 900px;
	-moz-border-radius: 10px;
	-webkit-border-radius: 10px;
	border-radius: 15px;
	background: none repeat scroll 0 0 #F5F5F5;;
	-moz-box-shadow: 0px 5px 15px #9C9C9C;
	-webkit-box-shadow: 0px 5px 15px #9C9C9C;
	box-shadow: 0px 5px 15px #9C9C9C;
}

textarea {
	font-size: 14px;
	background: none repeat scroll 0 0 #FFFFFF;
	border: 1px solid #CCCCCC;
	border-radius: 5px 5px 5px 5px;
	box-shadow: 1px 2px 3px #EEEEEE inset, 0 1px 0 #FFFFFF;
	font-size: 12px;
	height: 100px;
	outline: 0 none;
	padding: 5px 5px;
	resize: none;
}
</style>
</head>
<body>
   <center>
      <div style="width: 1000px; margin-top: 10px; margin-bottom: 10px; margin-right: auto; margin-left: auto; position: relative;">
         <div class="shadow">
            <br />
            <table style="width: 100%; text-align: left;">
               <tr>
                  <td colspan="2" width="100%"></td>
               </tr>
               <tr>
                  <td colspan="2" width="100%"><span style="color: #F7A600; font-size: 20px; line-height: 22px; font-style: italic; margin-left: 10px;"> 请输入评论内容： </span>
                     <div style="margin-top: 5px; width: 100%; text-align: center;">
                        <textarea id="commentaryContent" rows="8" style="width: 97%;"></textarea>
                     </div></td>
               </tr>
               <tr align="right">
                  <td></td>
                  <td>
                     <div style="float: right; margin-top: 5px; margin-right: 10px;">
                        <img id="addCommentary" src="../filecube/images/icons/pinglun.png" style="cursor: pointer;" />
                     </div>
                  </td>
               </tr>
            </table>
            <br />
            <div style="margin-right: auto; margin-left: auto; text-align: left; position: relative; width: 100%; padding-bottom: 20px;" id="commentariesDiv">
               <c:if test="${not empty requestScope.commentList  }">
                  <c:forEach items="${requestScope.commentList}" var="comment" varStatus="stat">
                     <div style="width: 100%;border-bottom: 1px dashed #E5E5E5;${(stat.index+1) % 2 == 1 ?'background-color: #FAFDFC;':''}" class="commentaries" id='${comment.UUID }'>
                        <br />
                        <div style="padding-left: 10px; width: 100%;">
                           <img src="../filecube/images/view/commentary.png" style="vertical-align: middle;" />${comment.userName } &nbsp; 发表时间：
                           <fmt:formatDate value="${comment.createDate }" pattern="yyyy-MM-dd HH:mm:ss" />
                           &nbsp;&nbsp;
                           <c:if test="${comment.userGUID eq sessionScope.riseUser.userGUID }">
                              <a href="javascript:deletecomment('${comment.UUID}');">删除</a>
                           </c:if>
                        </div>
                        <div style="margin-left: 40px; margin-top: 20px; width: 100%; margin-right: 40px; margin-bottom: 20px;">
                           <c:out value="${comment.content }" />
                        </div>
                     </div>
                  </c:forEach>
               </c:if>
            </div>
         </div>
      </div>
   </center>
</body>
</html>