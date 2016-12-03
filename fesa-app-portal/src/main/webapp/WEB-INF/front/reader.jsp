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
<title>FS 自定义阅读</title>
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
code{
    white-space:normal;
    color:black;
}
#detailinfo img{
    max-width: 600px;
    margin: 10px auto;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.4);
    display: block;
}
.prenew {
	height: 70px;
	overflow: hidden;
	margin: 0px;
	padding: 0px;
	background-color: #f5f5f5;
	border: 1px solid #ccc;
	border-radius: 4px
}

.prenewcontent {
	margin: 0px;
	padding: 0px;
	background-color: #f5f5f5;
	border: 1px solid #ccc;
	border-radius: 4px;
	overflow-y: auto;
	/*  margin-top: 24px */
}
</style>
<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bootstrap.min.js"></script>
</head>
<body>
	<jsp:include page="../templet/head.jsp"></jsp:include>
	<div class="navbar navbar-default navbar-static-top"
		role="navigation" style="margin-top: 0px;z-index:999">
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
    <div class="row" style="height:60px;padding:15px">
        <div class="col-md-2">
        </div>
		<div class="col-md-3">
			<div class="input-group">
				<span class="input-group-addon">RSS</span> <input type="text"
					class="form-control" id="txtaddress" style="width:200px" placeholder="输入你要订阅的feed地址">
				<button type="button" class="btn btn-default" onclick="subscribe()">订阅</button>
			</div>
		</div>
		<div class="col-md-5">
		<form action="reader" method="post" enctype="multipart/form-data">
		<s:file name="opmlfile" style="width:180px;float:left;margin:6px"></s:file>
		<button type="submit" class="btn btn-default"
				style="width:auto;">
	    	<span class="glyphicon glyphicon-import"></span>导入OPML
	    </button>
	    <button type="button" class="btn btn-default"
				style="width:auto;" onclick="btn_export()">
		    <span class="glyphicon glyphicon-export"></span>
		           <a href="FileDownload">导出OPML</a> 
	    </button>
	    </form>
	    </div>
	    <div class="col-md-2">
	      <button type="button" id="btnscreen" class="btn btn-default"
				style="width:82px;position: absolute;" onclick="fullscreen()">
		    <span class="glyphicon glyphicon-fullscreen"></span>
		    <span id="spanscre">全屏</span>
	      </button>
	    <!--   <input type="button" id="btnscreen" value="全屏"
					style="position: absolute; width: 42px; margin-top: -1px; font-weight: bold;"
					 onclick="fullscreen()" /> -->
	    </div>
    </div>
	<div class="panel-group" id="accordion"
		style="width: 220px; float: left;">
		<div>
			<button type="button" class="btn btn-default"
				style="width: 100%; text-align: left;">
				<span class="glyphicon glyphicon-home"></span> 所有订阅
			</button>
		</div>
		<c:forEach items="${listspe}" var="spe">
			<div class="panel panel-default" style="margin-top: 0px">
				<div class="panel-heading" style="cursor: pointer;" onclick="panclick('#collapseTwo${spe}')">
					<h4 class="panel-title">
					    <c:out value="${spe}"></c:out>
						<%-- <a data-toggle="collapse" data-parent="#accordion" 
						   href="#collapseTwo${spe}">  
						</a> --%>
					</h4>
				</div>
				<div id="collapseTwo${spe}" class="panel-collapse collapse">
					<div class="panel-body" style="padding: 0px; padding-left: 15px;">
						<c:forEach items="${list_species}" var="listspe">
							<c:if test="${listspe.speciesName==spe}">
								<div style="cursor: pointer;"
									onclick="changechnel('${listspe.feedId.id}','${listspe.feedId.feedName}')">
									<span class="glyphicon glyphicon-record"></span> <span>
										<c:out value="${listspe.feedId.feedName}"></c:out>
									</span> <span>(20)</span>
								</div>
							</c:if>
						</c:forEach>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
	<div>
		<div id="sumdiv" style="width: 380px; float: left; max-height: 450px; overflow-y: auto;">
			<div>
				<button type="button" class="btn btn-default"
					style="width: 100%; text-align: left;">
					<span class="glyphicon glyphicon-tags"> </span>&nbsp;&nbsp;<span
						id="channelTitle">频道</span>
				</button>
			</div>
			<div id="detailcontent" class="highlight"
				style="cursor: pointer !important">
				<c:forEach items="${list_details}" var="det">
					<div class="prenew" onclick="summarypoint('${det.id}')">
						<span
							style="font-weight: bold; font-size: 12px; line-height: 16px">
							<c:out value="${det.title}"></c:out>
						</span><br />
						<div style="font-size: 11px; line-height: 16px">
							<span
								style="color: #4a8db8; margin-right: 4px; font-weight: bold;">
								<fmt:formatDate value="2014-3-4"
									pattern="yyyy年MM月dd日 HH时mm分" />
								<%-- <c:out value="${det.publishedDate}"></c:out> --%>
							</span> 
							<span> <c:out value="${det.summary}"></c:out></span>
						</div>
					</div>
				</c:forEach>
			</div>
			<button type="button" style="width:100%" class="btn btn-default" onclick="showmore()">显示更多</button>
			<input type="hidden" id="hidfedid" value="${fedid}"/>
			<input type="hidden" id="hidpage" value="${pageindex}" />
		</div>
		<div style="width: 100%; height: 450px;">
			<div id="detailinfo" class="highlight"
				style="max-height: 450px; overflow: scroll;">
				<input type="button" id="btnscreenclo" value="退出全屏"
					style="position: absolute; width:100%; margin-top: -1px; font-weight: bold;"
					 onclick="fullscreen()" />
				<div class="prenewcontent"></div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var feeddetails = null;
	$(document).on('webkitfullscreenchange mozfullscreenchange msfullscreenchange fullscreenchange',
					function() {
						if (!document.fullscreenElement
								&& !document.mozFullScreenElement
								&& !document.webkitFullscreenElement
								&& !document.msFullscreenElement) {
							var tagele = $("#detailinfo")[0];
							$(tagele).css("max-height", "450px");
							$("#btnscreen").css("width", "82px");
							$("#spanscre").text("全屏");
						} else {
							//全屏
						}
					});
	$(function(){
		changechnel('${list_species[0].feedId.id}','${list_species[0].feedId.feedName}');
		$("#btnscreenclo").hide();
		if('${message}'!="")
			alert('${message}');
	});
	function changechnel(t, title) {
		$("#channelTitle").text(title);
		$.ajax({
			type : 'post',
			url : 'getcustomfeeddetail',
			datatype : 'json',
			data : 'fedid=' + t+'&pageindex=0',
			success : function(datas) {
				if (eval("(" + datas + ")").getdetailResult.length > 0) {
					$("#hidpage").val(20);
					$("#hidfedid").val(t);
					var details = eval("(" + datas + ")").getdetailResult;
					feeddetails = details;
					var strhtml = "";
					$(details).each(
							function(i, item) {
								var time = "";
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
								var temphtml = '<div class="prenew" onclick="summarypoint('
										+ item.id + ')">';
								temphtml += '<span style="font-weight: bold; font-size: 12px; line-height: 16px">';
								temphtml += item.title;
								temphtml += '</span><br />';
								temphtml += '<div style="font-size: 11px;line-height:16px">';
								temphtml += '<span style="color:#4a8db8;margin-right:4px;font-weight:bold;">';
								temphtml += time;
								temphtml += '</span>';
								temphtml += '<span>';
								temphtml += item.summary;
								temphtml += '</span>';
								temphtml += '</div></div>';
								strhtml += temphtml;
								temphtml = '';
									});
					$("#detailcontent").html(strhtml);
					$("#sumdiv")[0].scrollTop=0;
					summarypoint(details[0].id);
					$("#detailinfo")[0].scrollTop=0;
				}
			}
		});
	}
	function summarypoint(t) {
		var strhtml = "";
		$(feeddetails).each(function(i, item) {
			if (item.id == t) {
				if (item.content.length > 0)
					strhtml = item.content;
				else
					strhtml = item.description;
			}
		});
		$(".prenewcontent").html(strhtml);
		$("#detailinfo")[0].scrollTop=0;
	}
	function fullscreen() {
		var tagele = $("#detailinfo")[0];
		if ($("#spanscre").text()== "全屏") {
			$(tagele).css("max-height", "800px");
			$("#btnscreen").css("width", "100%");
			launchFullScreen(tagele);
			$("#spanscre").text("退出全屏");
			$("#btnscreenclo").show();
		} else {
			$(tagele).css("max-height", "450px");
			$("#btnscreen").css("width", "82px");
			cancelFullScreen();
			$("#spanscre").text("全屏");
			$("#btnscreenclo").hide();
		}
	}
	function launchFullScreen(element) {
		if (element.requestFullscreen) {
			element.requestFullscreen();
		} else if (element.msRequestFullscreen) {
			element.msRequestFullscreen();
		} else if (element.mozRequestFullScreen) {
			element.mozRequestFullScreen();
		} else if (element.webkitRequestFullscreen) {
			element.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT);
		}
	}
	function cancelFullScreen() {
		if (document.exitFullscreen) {
			document.exitFullscreen();
		} else if (document.msExitFullscreen) {
			document.msExitFullscreen();
		} else if (document.mozCancelFullScreen) {
			document.mozCancelFullScreen();
		} else if (document.webkitExitFullscreen) {
			document.webkitExitFullscreen();
		}
	}
	function panclick(t){
		var coldiv=$(t);
		coldiv.toggle(500);
	}
	function subscribe(){
		$.ajax({
			type : 'post',
			url : 'setcustomfeed',
			datatype : 'json',
			data : 'feedaddress=' + $("#txtaddress").val(),
			success : function(datas) {
				if (eval("(" + datas + ")").setcusfedResult) {
					alert("订阅成功");
				}else{
					alert("订阅失败");
				}
			}
		});
	}
	function showmore(){
		$.ajax({
			type : 'post',
			url : 'getcustomfeeddetail',
			datatype : 'json',
			data : 'fedid=' + $("#hidfedid").val()+'&pageindex='+$("#hidpage").val(),
			success : function(datas) {
				if (eval("(" + datas + ")").getdetailResult.length > 0) {
					var details = eval("(" + datas + ")").getdetailResult;
					feeddetails = details;
					$("#hidpage").val(parseInt($("#hidpage").val())+20);
					var strhtml = "";
					$(details).each(
							function(i, item) {
								var time = "";
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
								var temphtml = '<div class="prenew" onclick="summarypoint('
										+ item.id + ')">';
								temphtml += '<span style="font-weight: bold; font-size: 12px; line-height: 16px">';
								temphtml += item.title;
								temphtml += '</span><br />';
								temphtml += '<div style="font-size: 11px;line-height:16px">';
								temphtml += '<span style="color:#4a8db8;margin-right:4px;font-weight:bold;">';
								temphtml += time;
								temphtml += '</span>';
								temphtml += '<span>';
								temphtml += item.summary;
								temphtml += '</span>';
								temphtml += '</div></div>';
								strhtml += temphtml;
								temphtml = '';
									});
					$("#detailcontent").html(strhtml);
					$("#sumdiv")[0].scrollTop=0;
					summarypoint(details[0].id);
					$("#detailinfo")[0].scrollTop=0;
				}
			}
		});
	}
</script>
</html>