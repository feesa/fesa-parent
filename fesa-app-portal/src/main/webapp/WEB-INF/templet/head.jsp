<%@page import="com.fs.commons.app.pojo.UserInfoPojo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	UserInfoPojo user_info = (UserInfoPojo) session.getValue("user");
%>
<!DOCTYPE html>
<div id="nav_top" class="navbar navbar-static-top" role="navigation"
	style="width: 100%;background: url(<%=basePath%>images/top_bg.jpg) repeat-x 0 100%">
	<div class="container-fluid">
		<div class="navbar-header">
		     <a class="navbar-brand" style="padding-top:3px" href="main" > 
		        <img alt="feisa" style="margin-left: 190px" src="<%=basePath%>images/Logo.png">
		     </a>
		</div>
		<div class="collapse navbar-collapse navbar-ex1-collapse nav1">
			<ul class="nav navbar-nav " style="line-height: 90px;">
	            <li class="big_li"><a class="big_a" href="javascript:void(0)" >博客</a></li>
	            <li class="big_li"><a class="big_a" href="././FSrss.xml" >RSS</a></li>
	            <li class="big_li"><a class="big_a" href="javascript:void(0)" >论坛</a></li>
	        </ul>
			<form id="form_0" class="navbar-form navbar-right" style="padding-top:15px;">
				<div class="form-group">
					<input type="text" id="InputName0" class="form-control" placeholder="用户名">
					<input type="text" id="InputPassword0" class="form-control" placeholder="密码">
				</div>
				<button type="button" class="btn btn-default" onclick="btnLogin0()">登录</button>
				<a href="javascript:btnRegister0()">注册</a>
				<a href="javascript:btnReset0()">忘记密码？</a>
				<div class="alert alert-danger" id="msg0" style="position:fixed; right: 0px; top:70px;width:400px;height:50px;display:none;" role="alert">
					<strong>提醒!</strong><span style="margin-left:5px">用户名不正确</span>
				</div>
			</form>
			<form id="form_1" class="navbar-form navbar-right" style="padding-top:15px;">
				<div class="btn-group">
					<button type="button" class="btn btn-success"><span id="user_setting">用户</span></button>
					<button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown">
						<span class="caret"></span> 
						<span class="sr-only">Toggle Dropdown</span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<li><a href="#">个人资料</a></li>
						<li><a href="#">修改密码</a></li>
						<li class="divider"></li>
						<li><a href="javascript:loginout()">退出</a></li>
					</ul>
				</div>
			</form>
		</div>
	</div>
</div>
<style type="text/css">
body{
	background-image:url("<%=basePath%>images/bg.jpg");
	font-family: 微软雅黑;
}
.big_li{
   font-size: large; 
   padding: 15px;
}
.big_li_l{
   border-right: #746161 1px solid;
}
.big_a{
   color: rgb(70, 182, 60);
}
.big_a:hover,.big_a:focus{
   color:rgb(155, 233, 125) !important;
   background-color:rgba(0, 0, 0, 0) !important;
}
</style>
<script type="text/javascript" src="<%=basePath%>js/respond.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		if ($("#navi-l").offset() != null) {
			var _defautlTop = $("#navi-l").offset().top;
			$(window).scroll(function() {
				if ($(this).scrollTop() > _defautlTop) {
					$('#doc').addClass('headerFix');
					$("#nav_main").addClass("navbar-fixed-top");
					$("#nav_main").removeClass("navbar-static-top");
				} else {
					$('#doc').removeClass('headerFix');
					$("#nav_main").addClass("navbar-static-top");
					$("#nav_main").removeClass("navbar-fixed-top");
				}
			});
			var locurl=window.location.href.split("/");
			var loctxt=locurl[locurl.length-1];
			if(loctxt=="main"){
				$("#navi-l li a[href='main']").parent().addClass("active");
			}else if(loctxt=="person"){
				$("#navi-l li a[href='person']").parent().addClass("active");
			}else if(loctxt=="feedmanage"){
				$("#navi-l li a[href='feedmanage']").parent().addClass("active");
			}else if(loctxt=="reader"){
				$("#navi-l li a[href='reader']").parent().addClass("active");
			}else if(loctxt=="download"){
				$("#navi-l li a[href='download']").parent().addClass("active");
			}else if(loctxt=="about"){
				$("#navi-l li a[href='about']").parent().addClass("active");
			}else{
				$("#navi-l li").removeClass("active");
			}
		}
		var u='<%=user_info%>';
	    if(u!='null'){
	    	changestatus("1",u.split('password')[0].split(':')[1]);
	    }else
	    	changestatus("0","用户");
	});
	function showmsg(s) {
		var message = $('#msg0');
		message.text(s);
		message.toggle(600);
		setTimeout(function() {
			message.toggle(600);
		}, 1500);
	}
	function changestatus(t, m) {
		switch (t) {
		case "0":
			$("#form_0").show();
			$("#form_1").hide();
			break;
		case "1":
			$("#form_0").hide();
			$("#form_1").show();
			break;
		}
		$("#user_setting").text(m);
	}
	function btnLogin0() {
		var userName = $('#InputName0').val();
		var userPass = $('#InputPassword0').val();
		if (userName == "") {
			showmsg("请输入用户名!");
			return;
		} else if (userPass == "") {
			showmsg("请输入密码!");
			return;
		}
		$.ajax({
			type : 'post',
			url : 'user/login',
			data:{
				username:userName,
				password:userPass
			},
			success : function(res) {
				if (res.datas){
					changestatus("1",userName);
					window.location.href = "main";
				}
				else
					showmsg("登录失败");
			}
		});
	}
	function btnRegister0() {
		window.location.href = "register";
	}
	function btnReset0() {
		window.location.href = "missingpwd";
	}
	function loginout() {
		$.ajax({
			type : 'post',
			url : 'user/logout',
			datatype : 'json',
			success : function(res) {
				if (res.datas) {
					changestatus("0","用户");
					window.location.href = "main";
				} else {
					showmsg("对不起,退出失败.出现异常!我们带给你的不便请谅解.");
				}
			}
		});
	}
</script>