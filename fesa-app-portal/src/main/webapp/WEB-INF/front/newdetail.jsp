<%@page import="com.fs.commons.app.pojo.BaseFeedPojo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>FS 阅读</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet"
	type="text/css">
<link href="<%=basePath%>css/jumbotron-narrow.css" rel="stylesheet">
<style>
.body {
	padding-top: 70px
}
code{
    white-space:normal;
    color:black;
    font-family: Microsoft Yahei;
}
.art-summary{
 font-family: Microsoft Yahei;
}
.art-summary img{
    max-width: 600px;
    margin: 10px auto;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.4);
    display: block;
}
.art-detail{
font-family: Microsoft Yahei;
}
.art-detail img{
    max-width: 600px;
    margin: 10px auto;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.4);
    display: block;
}
</style>
<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bootstrap.min.js"></script>
</head>
<body>
    <jsp:include page="../templet/head.jsp"></jsp:include>
	<div id="nav_main" class="navbar navbar-default navbar-static-top" role="navigation" style="margin-top: 0px;z-index:999">
		<div class="container" id="doc">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="main">FeSa</a>
			</div>
			<jsp:include page="../templet/navbar.jsp"></jsp:include>
		</div>
	</div>
	<div class="container">
		<div class="row" style="margin-top: 8px" id="rowinfo">
			<div class="col-md-9">
				<div id="contentinfos">
					<div class="well">
						<h3 id="new_title" style="text-align: center;"></h3>
						<hr style="height: 1px; border: none; border-top: 1px solid #555555;">
						<span id="new_content" class="art-detail"></span>
						<hr>
						<span id="new_time" style="margin-left: 20px"></span> 
						<span style="margin-left: 20px; font-weight: bolder;">
							<a id="new_url" href="" target="_blank">查看原文</a> 
						</span>
						<span id="new_site" style="float:right;font-weight:normal;"></span>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="panel panel-info">
					<div class="panel-heading">热门频道</div>
					<div class="panel-body" style="padding: 0px"></div>
					<ul class="list-group">
						<c:forEach var="spec" items="${list_species}">
							<li class="list-group-item" onclick="sect"><a
								href="./feedmanage?chanel=${spec.id}">${spec.speciesName}</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../templet/bottom.jsp"></jsp:include>
	<input type="hidden" id="newid" value="${newid}" />
</body>
<script type="text/javascript">
	$(function() {
		$("#rowinfo").css("min-height", "460px");
		$.ajax({
			type:'get',
			url:'news/getNewsById',
			datatype:'json',
			data:{
				pid:$("#newid").val()
			},
			success:function(datar){
				if(datar.datas!=null){
					var item=datar.datas;
					$("#new_title").text(item.title);
					$("#new_site").text(item.source);
					$("#new_time").text(item.time);
					$("#new_url").attr("href",item.url);
					$("#new_content").html(item.context);
					$("iframe").hide();
				}
			},error:function(datar){}
		});
	});
</script>
</html>