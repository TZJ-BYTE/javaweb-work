<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" import="com.demo.*"%>
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
        margin-bottom: 10px;
    }
    .header a {
        color: #007bff;
        text-decoration: none;
        font-size: 14px;
    }
    .header a:hover {
        text-decoration: underline;
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
    .pagination {
        display: flex;
        justify-content: space-around; /* 分散对齐项目 */
        align-items: center; /* 垂直居中项目 */
        margin-top: 20px;
        position: fixed;
        bottom: 0;
        right: 0;
        width: 100px;
        background-color: #333; /* 添加背景色 */
        padding: 10px; /* 添加内边距 */
        border-radius: 10px 0 0 10px; /* 添加圆角 */
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2); /* 添加阴影效果 */
    }
    .pagination button {
        background-color: #333; /* 设置按钮的背景色 */
        color: #fff; /* 设置按钮的文字颜色 */
        border: none; /* 移除按钮的边框 */
        padding: 5px 10px; /* 设置按钮的内边距 */
        margin: 0 2px; /* 设置按钮的外边距 */
        border-radius: 5px; /* 设置按钮的圆角 */
        transition: background-color 0.3s; /* 平滑过渡效果 */
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
                <a href="selectSC.jsp">查看购物车</a>
            </div>
        </div>
     <div class="product-list">
    <%
        for (Goods g : l) {
    %>
    <div class="product-item">
        <p><%=g.getGoodsname()%></p>
        <p>价格: <%=g.getPrice()%></p>
        <p>生产厂家: <%=g.getFactory()%></p>
        <p>出产日期: <%=g.getOutdate()%></p>
        <p>商品介绍: <%=g.getIntroduction()%></p>
        <!-- 添加到购物车表单 -->
        <form action="cart.do?flag=0&id=<%=g.getId()%>" method="post">
            <input type="submit" value="添加到购物车" />
        </form>
    </div>
    <%
        }
    %>
</div>

        <div class="pagination">
            <input type="hidden" id="currentPage" value="<%=pl.getCurrentPage()%>">
            <input type="hidden" id="maxPage" value="<%=pl.getMaxPage()%>">
            <button onclick="nextPage()">刷新</button>
        </div>
    </div>
</body>
</html>
