<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="service.PageList" %>
<%@ page import="model.Userinfo" %>
<%@ page import="model.Goods" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%
        List<Goods> l = (List<Goods>) request.getAttribute("findAllGoods");
        Userinfo u = (Userinfo) session.getAttribute("userinfo");
        PageList pl = (PageList) request.getAttribute("page");
    %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>查看商品</title>
    <link type="text/css" rel="stylesheet" href="CSS/style.css">
    <style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f9;
        margin: 0;
        padding: 0;
    }
    .bg {
        position: fixed; /* 固定背景图片 */
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-image: url('images/bg.jpg'); /* 背景图片 */
        background-size: cover;
        background-attachment: fixed; /* 固定背景图片 */
        opacity: 0.8;
        z-index: -1;
    }
    .container {
        max-width: 1200px;
        margin: 0 auto;
        padding: 20px;
        position: relative; /* 确保子元素可以相对于容器定位 */
        z-index: 1; /* 确保容器及其内容在背景之上 */
    }
    h1, h2 {
        text-align: center;
        color: #fff; /* 更改字体颜色为白色 */
        background-color: rgba(0, 0, 0, 0.6); /* 半透明黑色背景 */
        padding: 10px; /* 内边距 */
        border-radius: 5px; /* 圆角 */
        margin-bottom: 20px; /* 增加底部外边距 */
    }
    .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
    }
    .header a {
        color: #007bff;
        text-decoration: none;
        font-size: 14px;
        margin-right: 10px; /* 添加间距 */
    }
    .header a:hover {
        text-decoration: underline;
    }
    .header button {
        background-color: #007bff; /* 设置按钮的背景色 */
        color: #fff; /* 设置按钮的文字颜色 */
        border: none; /* 移除按钮的边框 */
        padding: 5px 10px; /* 设置按钮的内边距 */
        margin-left: 10px; /* 添加间距 */
        border-radius: 5px; /* 设置按钮的圆角 */
        transition: background-color 0.3s; /* 平滑过渡效果 */
        font-size: 12px; /* 减小字体大小 */
    }
    .header button:hover {
        background-color: #0056b3; /* 鼠标悬停时的颜色 */
    }
    .product-list {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
        gap: 20px;
    }
    .product-item {
        background-color: #fff;
        border-radius: 5px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        padding: 15px;
        text-align: center;
    }
    .product-item a {
        color: #007bff;
        text-decoration: none;
    }
    .product-item a:hover {
        text-decoration: underline;
    }

    .pagination button {
        background-color: #007bff; /* 设置按钮的背景色 */
        color: #fff; /* 设置按钮的文字颜色 */
        border: none; /* 移除按钮的边框 */
        padding: 5px 10px; /* 设置按钮的内边距 */
        margin: 0 2px; /* 设置按钮的外边距 */
        border-radius: 5px; /* 设置按钮的圆角 */
        transition: background-color 0.3s; /* 平滑过渡效果 */
    }

    .pagination button:hover {
        background-color: #0056b3; /* 鼠标悬停时的颜色 */
    }

    .pagination a, .pagination span {
        color: #007bff;
        text-decoration: none;
        margin: 0 5px;
        padding: 5px 10px;
        border: 1px solid #007bff;
        border-radius: 3px;
    }
    .pagination a:hover {
        background-color: #007bff;
        color: #fff;
    }
    .pagination span {
        cursor: default;
        background-color: #007bff;
        color: #fff;
    }

    .pagination .refresh-button {
        background-color: #007bff; /* 设置按钮的背景色 */
        color: #fff; /* 设置按钮的文字颜色 */
        border: none; /* 移除按钮的边框 */
        padding: 5px 10px; /* 设置按钮的内边距 */
        margin: 0 5px; /* 设置按钮的外边距 */
        border-radius: 5px; /* 设置按钮的圆角 */
        transition: background-color 0.3s; /* 平滑过渡效果 */
        font-size: 12px; /* 减小字体大小 */
    }
    .pagination .refresh-button:hover {
        background-color: #0056b3; /* 鼠标悬停时的颜色 */
    }

    .btn {
        background-color: #007bff; /* 设置按钮的背景色 */
        color: #fff; /* 设置按钮的文字颜色 */
        border: none; /* 移除按钮的边框 */
        padding: 5px 10px; /* 设置按钮的内边距 */
        border-radius: 5px; /* 设置按钮的圆角 */
        transition: background-color 0.3s; /* 平滑过渡效果 */
        font-size: 12px; /* 减小字体大小 */
        cursor: pointer; /* 添加鼠标指针样式 */
    }
    .btn:hover {
        background-color: #0056b3; /* 鼠标悬停时的颜色 */
    }
</style>
<script>
    function nextPage() {
        var currentPage = parseInt(document.getElementById("currentPage").value);
        var maxPage = parseInt(document.getElementById("maxPage").value);
        if (currentPage < maxPage) {
            currentPage++;
        } else {
            currentPage = 1;
        }
        window.location.href = "goods.do?flag=0&start=" + currentPage;
    }

    // 添加 refreshPage 函数
    function refreshPage() {
        window.location.href = "goods.do?flag=0"; // 重新加载页面并调用 findAll 方法
    }

    // addToCart 函数
    function addToCart(goodsId) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "cart.do?flag=0&id=" + goodsId, true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                alert(response.message); // 显示添加成功的消息
            }
        };

        xhr.send();
    }
</script>

</head>
<body>
<div class="bg"></div>
    <div class="container">
        <h1>赛博坦美食店</h1>
        <div class="header">
            <div>
                <span>你好, <%=u.getRealname()%>。</span>
            </div>
            <div>
                <button class="btn" onclick="location.href='selectSC.jsp'">查看购物车</button>
                <button onclick="refreshPage()" class="refresh-button">刷新</button>
            </div>
        </div>
        <div class="product-list">
            <%
                for (Goods g : (List<Goods>) request.getAttribute("findAllGoods")) {
            %>
            <div class="product-item">
                <p><%=g.getGoodsname()%></p>
                <p>价格: <%=g.getPrice()%></p>
                <p>口味: <%=g.getTaste()%></p>
                <p>饮食习惯: <%=g.getDietHabit()%></p>
                <p>健康要求: <%=g.getHealthRequirement()%></p>
                <!-- 购买按钮 -->
                <form onsubmit="event.preventDefault(); addToCart(<%=g.getId()%>);">
                    <input type="submit" value="购买" class="btn" />
                </form>
            </div>

            <%
                }
            %>
        </div>
    </div>
</body>
</html>
