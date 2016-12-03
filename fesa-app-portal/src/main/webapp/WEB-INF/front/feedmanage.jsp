<%@page import="com.fs.commons.app.pojo.UserInfoPojo"%>
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
		<div class="row" style="margin-top: 8px"></div>
		<div class="row" id="rowinfo">
			<div class="col-md-2">
				<div class="list-group">
					<c:forEach var="spe" items="${list_species}">
						<a class="list-group-item" href="./feedmanage?chanel=${spe.id}">
							<span class="badge"> <c:out value="${spe.count}"></c:out>
						</span> <c:out value="${spe.speciesName}"></c:out>
						</a>
					</c:forEach>
				</div>
			</div>
			<div class="col-md-10">
				<c:forEach var="fed0" items="${feeds}" varStatus="status0" step="3">
					<div class="row">
						<c:forEach var="fed" items="${feeds}" varStatus="status"
							begin="${status0.index}" end="${status0.index+2}">
							<div class="col-md-4">
								<div class="well">
									<c:set var="flag" value="0"></c:set>
									<c:forEach var="ufed" items="${list_ufeeds}">
										<c:if test="${ufed.feedId.id eq fed.id}">
											<a style="float: right" class="btn btn-warning"
												onclick="canelFeed('${fed.feedSpecies}','${fed.id}')">-取消订阅</a>
											<c:set var="flag" value="1"></c:set>
										</c:if>
									</c:forEach>
									<c:if test="${flag==0}">
										<a style="float: right" class="btn btn-primary"
											onclick="regisFeed('${fed.feedSpecies}','${fed.id}')">+订阅</a>
									</c:if>
									<img alt="" style="float: left" width="50px" height="50px"
										src="<%=basePath%>${fed.feedPhoto}">
									<h5 style="font-weight: bold;height:30px">
										<a href="feeddetail?feedid=${fed.id}"><c:out
												value="${fed.feedName}"></c:out></a>
									</h5>
									<p>
										<c:out value="${fn:substring(fed.feedDescription,0,14)}"></c:out>
									</p>
								</div>
							</div>
						</c:forEach>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<hr>
			</div>
		</div>
	</div>
	<jsp:include page="../templet/bottom.jsp"></jsp:include>
</body>
<script type="text/javascript">
	$(function() {
		$("#rowinfo").css("min-height", "526px");
	});
	function regisFeed(speid, fedid) {
		$.ajax({
			type : 'post',
			url : 'user_addfeed',
			datatype : 'json',
			data : 'speciesId=' + speid + '&feedId=' + fedid,
			success : function(datas) {
				if (eval("(" + datas + ")").addResult == "未登录") {
					alert("请先登陆！");
					window.location.href = "loginIn";
				} else {
					window.location.reload();
				}
			}
		});
	}
	function canelFeed(speid, fedid) {
		$.ajax({
			type : 'post',
			url : 'user_removefeed',
			datatype : 'json',
			data : 'speciesId=' + speid + '&feedId=' + fedid,
			success : function(datas) {
				if (eval("(" + datas + ")").removeResult == "未登录") {
					alert("请先登陆！");
					window.location.href = "loginIn";
				} else {
					window.location.reload();
				}
			}
		});
	}
</script>
</html>