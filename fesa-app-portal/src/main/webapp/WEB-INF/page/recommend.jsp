<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.fs.commons.app.pojo.RecommendSpeciesPojo"%>
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
    function btn_add(){
    	var temp={'name':$("#txt_name").val(),'icon':$("#txt_icon").val(),'restatus':$("#txt_restatus").val(),'remark':$("#txt_remark").val()};
    	$.ajax({
			type : 'post',
			url : 'manage/addrecommend',
			datatype : 'json',
			data : {'para':JSON.stringify(temp)},
			success : function(datas) {
				//alert(datas);
				document.location.reload();
			}
		});
    }
	function btn_update(i,t){
		var t_name='#'+i+' .cname';
		var t_icon='#'+i+' .cicon';
		var t_status='#'+i+' .crestatus';
		var t_remark='#'+i+' .cremark';;
		var temp={'id':i,'name':$(t_name).val()==null?'':$(t_name).val(),'icon':$(t_icon).val()==null?'':$(t_icon).val(),'restatus':$(t_status).val()==null?false:$(t_status).val(),'remark':$(t_remark).val()==null?'':$(t_remark).val()};
		$.ajax({
			type : 'post',
			url : 'manage/updaterecommend',
			datatype : 'json',
			data : {'para':JSON.stringify(temp)},
			success : function(datas) {
				//alert(datas);
				document.location.reload();
			}
		});
	}
	function btn_delete(i) {
		if (confirm("确定要删除吗？")) {
			$.ajax({
				type : 'post',
				url : 'manage/removerecommend',
				datatype : 'json',
				data : {'reid':i},
				success : function(datas) {
					//alert(datas);
					document.location.reload();
				}
			});
		}
	}
	function btn_showdetail(t){
		this.location.href="manage/recommenddetail?add_speid="+t;
	}
	function exist_basefeed(){
		var _val=$("#detail_id").val();
		if(_val==""){
			$.ajax({
				type : 'get',
				url : 'manage/verbasefeed',
				datatype : 'json',
				data : {'feedname':$("#detail_name").val()},
				success : function(datas) {
					var tt=eval("("+datas+")").result;
					if(tt!=null){
						$("#detail_id").val(tt.id);
						$("#detail_photo").val(tt.feedPhoto);
						$("#detail_remark").val(tt.feedDescription);
					}else
						alert("不存在");
				}
			});
		}else{
			var add_temp={"speid":$("#tag_spe").text(),"feedid":$("#detail_id").val(),"feedname":$("#detail_name").val(),"feedphoto":$("#detail_photo").val(),"remark":$("#detail_remark").val()};
			$.ajax({
				type : 'get',
				url : 'manage/adddetailfeed',
				datatype : 'json',
				data : {'para':JSON.stringify(add_temp)},
				success : function(datas) {
					document.location.reload();
				}
			});
		}
	}
	function detail_delete(t){
		$.ajax({
			type : 'post',
			url : 'manage/removedetail',
			datatype : 'json',
			data : {'para':t},
			success : function(datas) {
				document.location.reload();
			}
		});
	}
</script>
</head>
<body style="padding: 200px;padding-top:20px">
    <h2 style="text-align:center;color:brown">推荐类型维护</h2>
    <p style="max-height:300px;overflow:auto" >
	<table>
	  <thead>
	     <tr>
	       <th>名称</th>
	       <th>图标</th>
	       <th>状态</th>
	       <th>备注</th>
	       <th>操作</th>
	     </tr>
	  </thead>
	  <tbody>
	   <c:forEach var="commend" items="${list_recomms}" >
	    <tr id="${commend.id}">
	      <td><input class="cname" type="text" value="${commend.name}" /></td>
	      <td><input class="cicon" type="text" value="${commend.icon}" /></td>
	      <td><input class="crestatus" type="text" value="${commend.restatus}" /></td>
	      <td><input class="cremark" type="text" value="${commend.remark}" /></td>
	      <td>
	        <button onclick="btn_update('${commend.id}',this)">更新</button>
	        <button onclick="btn_showdetail('${commend.id}')">详细</button>
	        <button onclick="btn_delete('${commend.id}')">删除</button>
	      </td>
	    </tr>
	   </c:forEach>
	    <tr>
	      <td><input type="text" id="txt_name" /></td>
	      <td><input type="text" id="txt_icon" /></td>
	      <td><input type="text" id="txt_restatus" /></td>
	      <td><input type="text" id="txt_remark" /></td>
	      <td><button onclick="btn_add()">新增</button></td>
	    </tr>
	  </tbody>
	</table>
	</p>
	<p>当前speid:<span id="tag_spe">${add_speid}</span></p>
	<p style="max-height:200px;overflow:auto" >
	<table>
		<thead>
			<tr>
				<th>标识</th>
				<th>名称</th>
				<th>图片</th>
				<th>描述</th>
				<th>操作</th> 
			</tr>
		</thead>
		<tbody>
		 <c:forEach var="detail" items="${list_details}">
			<tr>
			   <td><input type="text" value="${detail.feedid}" disabled="disabled" /></td>
			   <td><input type="text" value="${detail.feedname}" disabled="disabled" /></td>
			   <td><input type="text" value="${detail.feedphoto}" disabled="disabled" /></td>
			   <td><input type="text" value="${detail.remark}" disabled="disabled" /></td>
			   <td>
			      <button onclick="detail_delete('${detail.id}')">删除</button>
			   </td>
			</tr>
		 </c:forEach>
			 <tr>
		      <td><input type="text" id="detail_id" disabled="disabled" /></td>
		      <td><input type="text" id="detail_name" /></td>
		      <td><input type="text" id="detail_photo" disabled="disabled" /></td>
		      <td><input type="text" id="detail_remark"  /></td>
		      <td><button onclick="exist_basefeed()">验证/提交</button></td>
		    </tr>
		</tbody>
	</table>
	</p>
</body>
</html>
