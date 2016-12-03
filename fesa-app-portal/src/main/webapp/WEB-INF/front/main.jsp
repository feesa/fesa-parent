<%@page import="com.fs.commons.app.pojo.UserInfoPojo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	UserInfoPojo user_temp = (UserInfoPojo) session.getValue("user");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/myJSTLFunction.tld" prefix="myfn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>FS 推荐</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet"
	type="text/css">
<link href="<%=basePath%>css/jumbotron-narrow.css" rel="stylesheet"
	type="text/css">
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
	<div id="nav_main" class="navbar navbar-default navbar-static-top"
		role="navigation" style="margin-top: 0px; z-index: 999">
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
		<div class="row" style="margin-top: 5px">
			<div id="group_class" class="btn-group btn-group-lg"></div>
		</div>
	</div>
	<div class="container">
		<div class="row" style="display:none">
			<div class="col-md-12" style="padding-left:0px;padding-right:0px">
				<a class="btn btn-default btn-lg" style="width: 100%" href="javascript:getnew()">加载更多</a>
			</div>
		</div>
		<div id="id_tj" class="row" style="margin-top: 10px"></div>
		<div class="row">
			<div class="col-md-12" style="padding-left:0px;padding-right:0px">
				<a class="btn btn-default btn-lg" style="width: 100%" href="javascript:scroll(0,0)">没有内容了(回到顶端)</a>
			</div>
		</div>
	</div>
	<input type="hidden" id="pid" value="0"  />
	<input type="hidden" id="ptime" value="0"  />
	<input type="hidden" id="nid" value="0"  />
	<input type="hidden" id="ntime" value="0"  />
	<jsp:include page="../templet/bottom.jsp"></jsp:include>
	<script type="text/javascript">
	var isloading=false;
	var isgetting=false;
	var categoryid=0;
	$(function() {
		getnewcategory();
		getmore();
		$(window).scroll(function() {
			if ($(this).scrollTop() > document.body.scrollHeight-920) {
				if(!isloading){
					isloading=true;
					getmore();
				}
			}
		});
	});
	function getnewcategory(){
		$.ajax({
			type:'get',
			url:'news/getNewsCategory',
			success:function(datas){
				if(datas.datas.length>0){
					$(datas.datas).each(function(i,item){
						var str="<button type=\"button\" class=\"btn btn-default\" categoryid=\""+item.classifytype+"\" onClick=\"getcategorynews(this)\">"+item.classifyname+"</button>";
						$("#group_class").append(str);
					});
				}
			},error:function(e){}
		});
	}
	function getcategorynews(t){
		categoryid=$(t).attr("categoryid");
		$("#pid").val("0"),
		$("#ptime").val("0"),
		$("#nid").val("0");
		$("#ntime").val("0");
		$("#id_tj").html("");
		getmore();
	}
	function getnew() {
		if(!isgetting){
			isgetting=true;
			$.ajax({
				type : 'get',
				url : 'news/getNewsDataForPage',
				datatype : 'json',
				data : {
					pid:$("#nid").val(),
					ptimestamp:$("#ntime").val(),
					ptype:'loadnew'
				},
				success : function(datas) {
					if (datas.datas.length>0) {
						var items = datas.datas;
						var strhtml = "";
						var _time="";
						var _id="";
						$("#nid").val(datas.datas[0].id);
						$("#ntime").val(datas.datas[0].timestamp);
						$(items).each(function(i, item) {
							_time=item.timestamp;
							_id=item.id;
							strhtml += '<div class="col-md-4" style="padding-left:0px;padding-right:0px">';
							strhtml += '<div class="well">';
							strhtml += '<h3>';
							strhtml += '<a href="newdetail?newid='+ item.id+ '" target="_blank" style="color: black;">';
							strhtml += item.title;
							strhtml += '</a>';
							strhtml += '</h3>';
							strhtml += '<p style="text-align:center">';
							strhtml += '<img src="'+ item.image+ '" style="max-width:293px" />';
							strhtml += '</p>';
							strhtml += '<span>';
							strhtml += item.time;
							strhtml += '</span>';
							strhtml += '<span style="font-weight:normal;float:right;">';
							strhtml += item.source;
							strhtml += '</span>';
							strhtml += '</div>';
							strhtml += '</div>';
						});
						$('#id_tj').prepend(strhtml);
						isgetting=false;
					}
				}
			});	
		}
	}
	function getmore() {
		$.ajax({
			type : 'get',
			url : 'news/getNewsDataForPage',
			datatype : 'json',
			data : {
				pid:$("#pid").val(),
				ptimestamp:$("#ptime").val(),
				ptype:'loadmore',
				cateid:categoryid
			},
			success : function(datas) {
				if (datas.datas.length>0) {
					var items = datas.datas;
					var strhtml = "";
					var _time="";
					var _id="";
					$(items).each(function(i, item) {
						if($("#nid").val()==0||$("#ntime").val()==0){
							$("#nid").val(item.id);
							$("#ntime").val(item.timestamp);
						}
						_time=item.timestamp;
						_id=item.id;
						strhtml += '<div class="col-md-4" style="padding-left:0px;padding-right:0px">';
						strhtml += '<div class="well">';
						strhtml += '<h3>';
						strhtml += '<a href="newdetail?newid='+ item.id+ '" target="_blank" style="color: black;">';
						strhtml += item.title;
						strhtml += '</a>';
						strhtml += '</h3>';
						strhtml += '<p style="text-align:center">';
						strhtml += '<img src="'+ item.image+ '" style="max-width:293px" />';
						strhtml += '</p>';
						strhtml += '<span>';
						strhtml += item.time;
						strhtml += '</span>';
						strhtml += '<span style="font-weight:normal;float:right;">';
						strhtml += item.source;
						strhtml += '</span>';
						strhtml += '</div>';
						strhtml += '</div>';
					});
					$("#id_tj").append(strhtml);
					$("#ptime").val(_time);
					$("#pid").val(_id);
					isloading=false;
				}
			}
		});
	}
	</script>
</body>
</html>