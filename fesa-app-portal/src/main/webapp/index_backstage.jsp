<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>FeSa阅读</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet"
	type="text/css">
<link href="<%=basePath%>css/jumbotron-narrow.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bootstrap.min.js"></script>
<script type="text/javascript">
	function go(i) {
		switch (i) {
		case 0:
			this.location.href = "manage/action_addUI";
			break;
		case 1:
			this.location.href = "manage/action_list";
			break;
		case 2:
			this.location.href = "manage/action_subscribeUI";
			break;
		case 3:
			this.location.href = "manage/action_sublist";
			break;
		case 4:
			this.location.href = "manage/recommend";
			break;
		}
	}
</script>
</head>
<body style="padding: 200px">
	<div class="container">
		<p
			style="text-align: center; font-size: large; color: blue; font-weight: bold; font-family: KaiTi">
			<span>管理员控制面板</span>
		</p>
		<div class="well" style="text-align: center;">
			<div class="btn-group btn-group-lg">
				<button type="button" class="btn btn-default" onclick="go(0)">添加feed</button>
				<button type="button" class="btn btn-default" onclick="go(1)">feed浏览</button>
				<button type="button" class="btn btn-default" onclick="go(2)">订阅到fesa</button>
				<button type="button" class="btn btn-default" onclick="go(3)">浏览订阅</button>
				<button type="button" class="btn btn-default" onclick="go(4)">推荐管理</button>
			</div>
		</div>
	</div>
</body>
</html>
