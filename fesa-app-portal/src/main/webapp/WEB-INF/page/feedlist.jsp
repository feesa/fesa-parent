<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>feed列表</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bootstrap.min.js"></script>
<script type="text/javascript">
	function btn_edit() {
		window.location.href = "./action_editUI";
	}
	function btn_delete() {

	}
	function createall() {
		$.ajax({
			type : 'post',
			url : 'createall',
			datatype : 'json',
			success : function(data) {
				if (eval("(" + data + ")").createResult == "success")
					alert("生产成功");
				else
					alert("登录失败");
			}
		});
	}
</script>
</head>
<body>
	<a href="../index_backstage.jsp">返回首页</a>
	<input type="button" value="全部生成" onclick="createall()" />
	<center>
		<h3>Feed列表</h3>
		<table border="1" style="width: 1300px">
			<tr>
				<td>序号</td>
				<td>类型</td>
				<td>名称</td>
				<td>Feed地址</td>
				<td>描述</td>
				<td>图片</td>
				<td>数量</td>
				<td>是否发布</td>
				<td>备注</td>
				<td>操作</td>
			</tr>
			<s:iterator value="basefeeds" id="f">
				<tr>
					<td><s:property value="id" /></td>
					<td><s:property value="feedSpecies" /></td>
					<td><s:property value="feedName" /></td>
					<td><s:property value="feedAddress" /></td>
					<td><c:out value="${fn:substring(f.feedDescription,0,10)}" /></td>
					<td><input type="image" style="width: 50px; height: 20px"
						src="<%=basePath%><s:property value='feedPhoto' />" /></td>
					<td><s:property value="feedCount" /></td>
					<td><s:property value="(feedStatus==1?'已发布':'未发布')" /></td>
					<td><s:property value="feedRemark" /></td>
					<td><s:a href="action_editUI?fedid=%{#f.Id}">编辑</s:a> <s:a
							href="action_releaseUI?fedid=%{#f.Id}">发布</s:a> <s:a
							href="action_deleteUI?fedid=%{#f.Id}">删除</s:a> <s:a
							href="javascript:createinfos(%{#f.Id})">生成</s:a></td>
				</tr>
			</s:iterator>
		</table>
	</center>
</body>
<script type="text/javascript">
	function createinfos(fid) {
		$.ajax({
			type : 'post',
			url : 'createinfo',
			datatype : 'json',
			data : 'fedid=' + fid,
			success : function(data) {
				if (eval("(" + data + ")").createResult == "success")
					alert("生产成功");
				else
					alert("登录失败");
			}
		});
	}
</script>
</html>
