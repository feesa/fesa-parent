<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%  
    String path = request.getContextPath();  
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>feed编辑</title> 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <body> 
<s:form action="action_edit" namespace="/manage" enctype="multipart/form-data">
  <center>
  <h2>feed编辑</h2>
  <table border="1">
    <tr>
      <td>类型:</td>
      <td> <s:select list="basespecies" name="editfeed.feedSpecies"></s:select></td>
    </tr>
    <tr>
      <td>名称:</td>
      <td><s:textfield name="editfeed.feedName"/></td> 
    </tr>
    <tr>
      <td>地址:</td>
      <td><s:textfield name="editfeed.feedAddress"></s:textfield></td>
    </tr>
    <tr>
      <td>描述:</td>
      <td><s:textfield name="editfeed.feedDescription"></s:textfield></td>
    </tr>
    <tr>
      <td>图片:</td>
      <td>
          <s:file name="image"></s:file>
      </td>
    </tr>
    <tr>
      <td>数量:</td>
      <td><s:textfield name="editfeed.feedCount"></s:textfield></td>
    </tr>
    <tr>
       <td>备注:</td>
      <td><s:textfield name="editfeed.feedRemark"></s:textfield></td>
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
