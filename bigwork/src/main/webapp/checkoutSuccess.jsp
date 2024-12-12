<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>结账成功</title>
    <style type="text/css">
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        header {
            background-color: #333;
            color: #fff;
            text-align: center;
            padding: 1em 0;
        }
        .container {
            width: 80%;
            margin: 20px auto;
            background-color: #fff;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .success-message {
            text-align: center;
            margin-top: 50px;
            font-size: 1.5em;
            color: #4CAF50;
        }
        .actions {
            text-align: center;
            margin-top: 20px;
        }
        .actions a {
            margin: 0 10px;
            text-decoration: none;
            color: #333;
        }
    </style>
</head>
<body>
<header>
    <h1>赛博坦美食店</h1>
    <h2>结账成功</h2>
</header>
<div class="container">
    <div class="success-message">
        您的订单已成功提交！感谢您的购买。
    </div>
    <div class="actions">
        <a href="goods.do">继续购物</a>
    </div>
</div>
</body>
</html>
