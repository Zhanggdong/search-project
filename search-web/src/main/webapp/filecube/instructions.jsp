<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
  pageContext.setAttribute("ctx", path);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>智能搜索说明书</title>
  <style>
        body{  padding: 0;  margin: 0; font-family: "宋体", "arial";}
        .all{margin: 30px 10px;}
        .all .content span{line-height: 28px;}
        .banner{height:40px;line-height:40px;width:100%;background-color: #dff3ff;}
        .banner>span{font-size: 1.5em;font-weight: bold;margin: 0 20px;}
        .detail_rule>div:first-child{font-weight: bold;float: left;}
        .detail_rule>div:last-child{width:100%;float:left;margin-left: 15px;}
        .content .rule_img>img{display: inherit;}
    </style>
</head>
<body>
<div class="all">
    <div class="banner">
        <span>规则说明</span>
    </div>
    <div class="content">
        <div class="rules">
            <span>
               智慧检索的各种规则。（如入库原则、分词原则、查找原则、排序原则、搜索范围原则）
            </span>
        </div>
        <!-- 1-->
        <div class="detail_rule">
            <div>
               <span> ①入库原则：</span>
            </div>
            <div>
                <span>经过个人的件（发文，收文，阅件）用排序号的方式入库，已经入回收站的文件不入检索库。</span>
            </div>
        </div>

        <!--2-->
        <div class="detail_rule">
            <div>
                <span> ②分词原则：</span>
            </div>
            <div>
                <span>智慧检索配置的了IKAnalyzer中文分词器他的分词原则如下：例如输入“我爱中华人民共和国”时会匹配成两部分如</br>
				“我爱中华人民共和国”整体和分成单个词语“我 | 爱 | 中华人民共和国 | 中华人民 | 中华 | 华人 | 人民共和国 ”（以竖杠划分为一个词）。</span>
            </div>
        </div>
		 <!--3-->
        <div class="detail_rule">
            <div>
                <span> ③查找原则：</span>
            </div>
            <div>
                <span>查询全部：在对数据进行全部查询时，它遵循的是并行查询，（例如输入”移动测试”）</br>    
                      他会把文件标题、意见、办文编号、来文单位、正文标题、附件标题,等关键词内容中 </br>  
                      包含有“移动，测试，移动测试”的相关公文都查询出来。</br>
                      按其他条件查询：所遵循的过滤查询。例如（输入“移动测试”时当我们点击按“文 </br>
                      件标题”查询他只会查询出”标题“中所含有“移动，测试，移动测试”的相关公文）按其他条件也遵循这一原则。</span>
            </div>
        </div>
			
        <!--4-->
		<div class="detail_rule">
            <div>
               <span> ④排序原则：</span>
            </div>
            <div>
                <span>所有查询结果返回时，按公文创建时间的先后顺序排列。</span>
            </div>
        </div>
		    <!--5-->
		<div class="detail_rule">
            <div>
               <span> ⑤搜索范围原则：</span>
            </div>
            <div>
                <span> 查询全部：在查询全部时，暂定为在“文件标题”、“意见”、“办文编号”、“来文单位”、</br>
                      “正文标题”、“附件标题”这些内容中查找与关键字相匹配的公文，只要一项符合都会被 </br>
                      查询出来。</br>
                      按其他条件查询：例如当我们选择按“标题”查询时，关键字只在文件标题这一字段中</br>
                      进行检索，（“意见”、“办文编号”、“来文单位”、“正文标题”、“附件标题”）也都遵循这</br> 
                      一查询方法。</br></span>
            </div>
        </div>
        <!--6-->
        <div class="detail_rule">
            <div>
                <span> ⑥查询案例：</span>
            </div>
            <div>
                <span>1.在输入关键字时可以在中间加上空格（不加的话默认使用配置中分词器进行分词），例如输入“测试综合办”，</br>它返回包含
                                        关键字  “测试综合办”和“测试”以及“综合办”的结果。
                
                </span>
            </div>
        </div>
        <div class="rule_img">
            <span></span>
            <img src="${ctx }/filecube/images/instructions.png"/>
        </div>


    </div>
</div>
</body>
</html>