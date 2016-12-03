<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>分析引擎</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet"
	type="text/css">
<link href="<%=basePath%>css/jumbotron-narrow.css" rel="stylesheet">
<style type="text/css">
.body {
	padding-top: 70px
}
</style>
<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bootstrap.min.js"></script>
</head>
<body>
<span>分析引擎</span>
<button onClick="classifyPage()">新闻分类</button>
<span id="classifyresult"></span>
<script type="text/javascript">
	function classifyPage(){
		$("#classifyresult").text("");
		$.ajax({
			type:'GET',
			url:'/analysis/classifyPage',
			data:{
				pagesize:20
			},
			datatype:'json',
			success:function(d){
				$("#classifyresult").text(d.datas);
			},error:function(e){
				
			}
		});
	}
</script>
</body>
</html>