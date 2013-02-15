<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>  

<!-- 这里的uri用户可以随便指定，只要与web.xml配置的taglib-uri一致就可以-->  
<%@ taglib prefix="math" uri="http://com.jamesli.fn/math-function-tablib"%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    Test My El Function<br>
    12.34 + 20.0983 = ${math:add(12.34,20.0983)}<br>
  </body>
</html>
