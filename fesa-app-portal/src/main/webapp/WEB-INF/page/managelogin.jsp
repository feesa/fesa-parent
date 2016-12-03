<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>管理员登录</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
</head>

<body>
	<a href="../main">进入FS首页</a>
	<br>
	<center style="font-size: 20px">
		<s:property value="message" />
		<table style="margin-top: 150px">
			<tr>
				<td colspan="2"
					style="font-weight: bold; font-size: large; text-align: center;">管理员登录</td>
			</tr>
			<tr>
				<td>账号:</td>
				<td><s:textfield id="mname" name="m_user" /></td>
			</tr>
			<tr>
				<td>口令:</td>
				<td><s:textfield id="mpwd" name="m_pwd" /></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><input
					type="button" value="登录" onclick="login()" /></td>
			</tr>
		</table>
	</center>
</body>
<script type="text/javascript">
	function login() {
		$.ajax({
			type : 'post',
			url : 'loginmanage?m_user=' + $("#mname").val() + '&&m_pwd='
					+ $("#mpwd").val(),
			datatype : 'json',
			success : function(data) {
				if (eval("(" + data + ")").AminResult == "success"){
					alert("登录成功");
					this.href.location="../index_backstage.jsp";
				}
				else
				    alert("登录失败");
			}
		});
	}
</script>
</html>
