<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2024/9/19
  Time: 9:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>注册信息显示</title>
</head>
<body>
<h1>注册信息</h1>
<div id="userInfo"></div>

<script>
    // 从URL中获取传递的信息
    const urlParams = new URLSearchParams(window.location.search);
    const userInfoString = urlParams.get('info');

    // 将JSON字符串转换回对象
    const userInfo = JSON.parse(decodeURIComponent(userInfoString));

    // 创建一个函数来显示用户信息
    function displayUserInfo(userInfo) {
        const userInfoDiv = document.getElementById('userInfo');
        for (const [key, value] of Object.entries(userInfo)) {
            // 特殊处理爱好，因为它是数组
            if (key === 'hobbies') {
                userInfoDiv.innerHTML += `<strong>${key}:</strong>${value.join(', ')}<br>`;
            } else {
                userInfoDiv.innerHTML += `<strong>${key}:</strong>${value}<br>`;
            }
        }
    }

    // 当页面加载完毕后显示用户信息
    window.onload = function() {
        if (userInfo) {
            displayUserInfo(userInfo);
        }
    };
</script>
</body>
</html>

