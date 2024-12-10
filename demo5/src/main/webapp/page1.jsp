<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Redirect Example</title>
</head>
<body>
<h1>This is the redirect.jsp page</h1>
<%
    // 重定向逻辑
    response.sendRedirect("target.jsp");
%>
</body>
</html>
