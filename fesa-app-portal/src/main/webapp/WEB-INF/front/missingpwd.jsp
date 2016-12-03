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
<title>FS 忘记密码</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet"
	type="text/css">
<link href="<%=basePath%>css/jumbotron-narrow.css" rel="stylesheet"
	type="text/css">
<style>
.body {
	padding-top: 70px
}
</style>
<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bootstrap.min.js"></script>
</head>
<body class="">
    <jsp:include page="../templet/head.jsp"></jsp:include>
	<div class="navbar navbar-default navbar-static-top" role="navigation" style="margin-top: 0px;z-index:999">
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
		<div class="row" style="margin-top: 8px">
			<div class="col-md-5" style="margin-left: 300px">
				<div class="well">
					<form>
						<div class="form-group">
							<span>注册邮箱:</span> <input class="form-control" id="InputEmail"
								placeholder="Enter email" type="email">
						</div>
						<a class="btn btn-primary" onclick="exitmail()"
							style="width: 100%;">找回密码</a>
					</form>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../templet/bottom.jsp"></jsp:include>
</body>
<script type="text/javascript">
	function sendmail() {
		$.ajax({
			type : 'post',
			url : 'user/findpwd',
			datatype : 'json',
			data : {
				tomail : $("#InputEmail").val()
			},
			success : function(res) {
				alert(res.message);
				if (res.datas) {
					window.location.href = "main";
				}else{
					alert(res.message);
				}
			}
		});
	}
	function exitmail() {
		$.ajax({
			type : 'post',
			url : 'user/existemail',
			datatype : 'json',
			data : 'email=' + $("#InputEmail").val(),
			success : function(res) {
				if (res.datas) {
					btnmissing();
				} else {
					alert("邮件不存在，请重新填写！");
				}
			}
		});
	}
	function btnmissing() {
		sendmail();
		window.location.href = "main";
	}
</script>
</html>