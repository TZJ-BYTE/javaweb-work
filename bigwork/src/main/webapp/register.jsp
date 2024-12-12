<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>注册 - 赛博坦美食店</title>
    <link rel="stylesheet" href="js/myCss.css">
    <script src="js/jquery.min.js"></script>
    <script src="js/myJs.js"></script>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            color: #333;
        }
        .bg {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-image: url('images/bg.jpg'); /* 背景图片 */
            background-size: cover;
            opacity: 0.8;
            z-index: -1;
        }
        .container {
            position: absolute;
            top: 50px; /* 调整顶部距离 */
            left: 50px; /* 调整左侧距离 */
        }
        .registerbox {
            background-color: rgba(255, 255, 255, 0.9);
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            transition: all 0.3s ease;
        }
        .registerbox:hover {
            transform: scale(1.02);
        }
        .registerbox .text-center {
            background-color: #269abc;
            color: #fff;
            padding: 20px;
            border-bottom: 2px solid #269abc;
        }
        .registerbox a {
            color: #269abc;
            text-decoration: none;
            padding: 0 10px;
        }
        .registerbox a:hover {
            text-decoration: underline;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .input {
            width: 90%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
        }
        .input:focus {
            border-color: #269abc;
            outline: none;
        }
        .button {
            background-color: #269abc;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 18px;
            transition: background-color 0.3s ease;
        }
        .button:hover {
            background-color: #1e7b9c;
        }
        .field-icon-right {
            position: relative;
        }
        .field-icon-right .icon {
            position: absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
            color: #aaa;
        }
        .field-icon-right input {
            padding-right: 30px;
        }
        .validation-code img {
            cursor: pointer;
        }
    </style>
    <script type="text/javascript">
        let alert_msg = '${alert_msg}';
        if (alert_msg != null && alert_msg.trim() != '') {
            window.alert(alert_msg);
        }
    </script>
</head>
<body>
<div class="bg"></div>
<div class="container">
    <div class="registerbox">
        <div class="text-center" style="font-size: 30px;font-weight: 700;">赛博坦美食店</div>
        <a href="login.jsp" style="font-size: 24px;">登录</a>&nbsp;&nbsp;<a style="font-size: 24px;">注册</a>
        <div class="panel-body" style="padding: 30px;">
            <form action="user.do?flag=0" method="post" onsubmit="return validateForm()">
                <div class="form-group">
                    <div class="field field-icon-right">
                        <input type="text" class="input input-big" name="username" id="username" placeholder="用户名" />
                        <span class="icon icon-user margin-small"></span>
                    </div>
                </div>
                <div class="form-group">
                    <div class="field field-icon-right">
                        <input type="text" class="input input-big" name="realname" id="realname" placeholder="真实姓名" />
                        <span class="icon icon-user margin-small"></span>
                    </div>
                </div>
                <div class="form-group">
                    <div class="field field-icon-right">
                        <input type="password" class="input input-big" name="password" id="password" placeholder="密码" />
                        <span class="icon icon-key margin-small"></span>
                    </div>
                </div>
                <div class="form-group">
                    <div class="field field-icon-right">
                        <input type="password" class="input input-big" name="confirmPassword" id="confirmPassword" placeholder="确认密码" />
                        <span class="icon icon-key margin-small"></span>
                    </div>
                </div>
                <div class="form-group validation-code">
                    <div class="field field-icon-right">
                        <input type="text" class="input input-big" name="safecode" id="safecode" placeholder="请输入验证码" style="width: 180px; float: left;" />
                        <img id="safecodeing" src="safecode" onclick="refresh()" style="height: 44px; width: 150px; float: right; border-radius: 4px;" />
                    </div>
                </div>
                <div style="padding: 30px;">
                    <input type="submit" class="button button-block bg-main text-big input-big" value="注册">
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    function validateForm() {
        var username = document.getElementById("username").value;
        var realName = document.getElementById("realName").value;
        var password = document.getElementById("password").value;
        var confirmPassword = document.getElementById("confirmPassword").value;
        if (username == "") {
            alert("用户名不能为空");
            return false;
        }
        if (realName == "") {
            alert("真实姓名不能为空");
            return false;
        }
        if (password == "") {
            alert("密码不能为空");
            return false;
        }
        if (confirmPassword == "") {
            alert("确认密码不能为空");
            return false;
        }
        if (password !== confirmPassword) {
            alert("两次输入的密码不一致");
            return false;
        }
        return true;
    }

</script>
</body>
</html>
