<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%  
    String path = request.getContextPath();  
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>订阅到fesa</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <body> 
  <a href="../index_backstage.jsp">返回首页</a>
  <s:form action="action_subscribe" namespace="/manage" enctype="multipart/form-data">
  <center>
  <h2>订阅到fesa</h2>
  <table border="1">
    <tr>
      <td>用户ID：</td>
      <td>
         <s:select list="userids" name="userid"></s:select>
      </td>
    </tr>
    <tr>
      <td>类型:</td>
      <td>
         <s:textfield name="titlename"></s:textfield> 
      </td>
    </tr>
    <tr>
      <td>名称:</td>
      <td><s:textfield name="customfed.feedName"/></td> 
    </tr>
    <tr>
      <td>地址:</td>
      <td><s:textfield name="customfed.feedAddress"></s:textfield></td>
    </tr>
    <tr>
      <td>描述:</td>
      <td><s:textfield name="customfed.feedDescription"></s:textfield></td>
    </tr>
    <tr>
      <td>图片:</td>
      <td>
          <s:file name="image"></s:file>
      </td>
    </tr>
    <tr>
      <td>数量:</td>
      <td><s:textfield name="customfed.feedCount"></s:textfield></td>
    </tr>
    <tr>
       <td>备注:</td>
      <td><s:textfield name="customfed.feedRemark"></s:textfield></td>
    </tr>
    <tr style="text-align: center;">
      <td colspan="2">
      <input type="submit" value="提交"/>
      </td>
    </tr>
  </table>
  </center>
</s:form>
</body>
</html>
