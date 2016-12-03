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
<title>FS 个人中心</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet"
	type="text/css">
<style type="text/css">
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
<link href="<%=basePath%>css/jumbotron-narrow.css" rel="stylesheet">
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
					<c:forEach var="fed" items="${list_feed}">
						<div class="well">
							<h3>
								<a href="javascript:"
									onclick="showdetail(event,'${fed.feedId.id}','${fed.id}')"
									style="color: black;font-family:You Yuan"> <c:out value="${fed.title}"></c:out></a>
							</h3>
							<hr
								style="height: 1px; border: none; border-top: 1px solid #555555;">
							<span class="art-summary"> ${fed.summary} </span> <span
								class="art-detail" style="display: none;"> 
						    <c:if test="${fed.description!=null}">
							   <code>${fed.description}</code> 
		                    </c:if>
							<c:if test="${fed.description==null&&fed.content!=null}">
					    	   <code>${fed.content}</code> 
							</c:if>
							</span>
							<hr>
							<span><c:out value="${fed.author}"></c:out> </span> <span
								style="margin-left: 20px"> <!-- <c:out value="${fed.publishedDate}"></c:out>  -->
								<fmt:formatDate value="${fed.publishedDate}"
									pattern="yyyy年MM月dd日 HH时mm分" />
							</span> <span style="margin-left: 20px; font-weight: bolder;"> <a
								href="${fed.uri}" target="_blank">查看原文</a> <a href="javascript:"
								style="float: right;" onclick="feeddetail('${fed.feedId.id}')"><c:out
										value="${fed.feedId.feedName}"></c:out> </a>
							</span>
						</div>
					</c:forEach>
				</div>
				<a class="btn btn-default btn-lg" style="width: 100%"
					onclick="btn_getmore('${specid}','${feedid}','${maxindex}')"
					id="loadid">加载更多</a> <input type="hidden" value="8" id="hidvalue" />
			</div>
			<div class="col-md-3">
				<img style="width: 1024; height: 600"
					src="<%=basePath%>images/info.jpg"
					class="img-responsive img-rounded">
				<hr>
				<div class="panel panel-success">
					<div class="panel-heading">所有订阅</div>
					<div class="panel-body" style="padding: 0px"></div>
					<ul class="list-group">
						<c:forEach var="ufed" items="${list_userfeed}">
							<li class="list-group-item"><a
								href="javascript:changechannel('${ufed.feedId.id}')">${ufed.feedId.feedName}</a></li>
						</c:forEach>
					</ul>
					<a class="btn btn-primary btn-lg" onclick="addfeed()" style="width: 100%"> + 添加</a>
				</div>
				<hr>
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
</body>
<script type="text/javascript">
	$(function() {
		$("#rowinfo").css("min-height", "568px");
	});
	function showdetail(e, fid, deid) {
		var artDetail = $(e.srcElement).parent().parent().children(
				'.art-detail');
		var artSummary = $(e.srcElement).parent().parent().children(
				'.art-summary');
		if ($(artDetail).css("display") == "none") {
			$(artDetail).css("display", "normal");
			$(artSummary).css("display", "none");
			personRead(fid, deid);
		} else {
			$(artDetail).css("display", "none");
			$(artSummary).css("display", "normal");
		}
	}
	function feeddetail(fedid) {
		window.location.href = "feeddetail?feedid=" + fedid;
	}
	function btn_getmore(spid, fedid, maxindx) {
		var pageindex = $("#hidvalue").val();
		$
				.ajax({
					type : 'post',
					url : 'getmoreinfo',
					datatype : 'json',
					data : 'specid=' + spid + '&feedid=' + fedid + "&maxindex="
							+ pageindex,
					success : function(datas) {
						if (eval("(" + datas + ")").getmoreResult != null) {
							var items = eval("(" + datas + ")").getmoreResult;
							var strhtml = "";
							$(items)
									.each(
											function(i, item) {
												var cont_temp = "";
												var time = "";
												if(item.description.length!=0)
													cont_temp=item.description;
												else if(item.description.length==0&&item.content.length!=0)
													cont_temp = item.content;
												else
													cont_temp = "";
												time = (parseInt(item.publishedDate.year) + 1900)
														+ "年"
														+ (parseInt(item.publishedDate.month) + 1)
														+ "月"
														+ parseInt(item.publishedDate.date)
														+ "日  "
														+ item.publishedDate.hours
														+ "时"
														+ item.publishedDate.minutes
														+ "分";
												var temphtml = '<div class="well">'
														+ '<h3>'
														+ '<a href="javascript:" onclick="showdetail(event,'
														+ item.feedId.id
														+ ','
														+ item.id
														+ ')" style="color: black;font-family:You Yuan">'
														+ item.title
														+ '</a>'
														+ '</h3>'
														+ '<hr style="height: 1px; border: none; border-top: 1px solid #555555;">'
														+ '<span class="art-summary">'
														+ item.summary
														+ '</span> <span class="art-detail" style="display: none;">'
														+ cont_temp
														+ '</span>'
														+ '<hr>'
														+ '<span>'
														+ item.author
														+ '</span> <span style="margin-left: 20px">'
														+ time
														+ '</span> <span style="margin-left: 20px; font-weight: bolder;"><a href="'+item.uri+'" target="_blank">查看原文</a> </span></div>';
												strhtml += temphtml;
												temphtml = "";
											});
							$("#contentinfos").append(strhtml);
							$("#hidvalue").val(parseInt(pageindex) + 8);
						} else {
							alert("没有内容了！");
							$("#loadid").text("已加载所有内容");
						}
					}
				});
	}
	function personRead(fid, deid) {
		$.ajax({
			type : 'post',
			url : 'personread',
			datatype : 'json',
			data : 'parafid=' + fid + '&detailid=' + deid,
			success : function(datas) {
				if (eval("(" + datas + ")").readResult) {
					//暂不处理
				} else {
					//暂不处理
				}
			}
		});
	}
	function changechannel(t) {
		window.location.href = "./person?parafid=" + t;
	}
	function addfeed(){
		window.location.href="feedmanage";
	}
</script>
</html>