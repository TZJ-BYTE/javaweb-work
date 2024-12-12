<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>错误页面</title>
    <script type="text/javascript">
        function goBack() {
            window.history.back();
        }
    </script>
</head>
<body>
    <h1>错误信息</h1>
    <p><%= request.getAttribute("errors") %></p>
    <button onclick="goBack()">返回上一页</button>
</body>
</html>
