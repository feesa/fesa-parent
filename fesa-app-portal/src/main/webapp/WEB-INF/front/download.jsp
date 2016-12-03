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
<title>FS app下载</title>
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
    <jsp:include page="../templet/head.jsp"></jsp:include>
	<div id="nav_main" class="navbar navbar-default navbar-static-top" role="navigation" style="margin-top: 0px;z-index:999">
		<div class="container">
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
		<div class="row" style="margin-top: 8px;text-align:center;"id="rowinfo">
			<img src="<%=basePath%>images/phone.jpg">
		</div>
	</div>
	<jsp:include page="../templet/bottom.jsp"></jsp:include>
</body>
<script type="text/javascript">
	$(function() {
		$("#rowinfo").css("min-height", "568px");
	});
</script>
</html>