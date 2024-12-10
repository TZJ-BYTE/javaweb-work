<%@ page import="java.net.URLDecoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户信息展示</title>
</head>
<body>
<h1>用户信息</h1>
<div id="userInfo">
    <%
        // 获取请求的查询字符串
        String queryString = request.getQueryString();
        // 使用URLEncoder来解码查询字符串
        if (queryString != null) {
            String[] params = queryString.split("&");
            for (String param : params) {
                String[] pair = param.split("=");
                if (pair.length == 2) {
                    String key = URLDecoder.decode(pair[0], "UTF-8");
                    String value = URLDecoder.decode(pair[1], "UTF-8");
                    // 特殊处理多选框参数（例如：hobbies[]）
                    if (key.endsWith("[]")) {
                        key = key.substring(0, key.length() - 2);
                        String[] values = request.getParameterValues(key);
                        if (values != null) {
                            out.println("<p>" + key + ": " + String.join(", ", values) + "</p>");
                        }
                    } else {
                        out.println("<p>" + key + ": " + value + "</p>");
                    }
                }
            }

            
        }
    %>
</div>
</body>
</html>
