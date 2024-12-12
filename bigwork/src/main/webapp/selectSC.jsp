<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="model.Userinfo" %>
<%@ page import="model.Shoppingcart" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>我的购物车</title>
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
        .product-list {
            list-style-type: none;
            padding: 0;
        }
        .product-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 0;
            border-bottom: 1px solid #ddd;
        }
        .product-item:last-child {
            border-bottom: none;
        }
        .product-details {
            display: flex;
            align-items: center;
        }
        .product-name {
            margin-right: 20px;
        }
        .product-price {
            margin-right: 20px;
        }
        .product-quantity {
            margin-right: 20px;
        }
        .product-subtotal {
            margin-right: 20px;
        }
        .pagination {
            text-align: center;
            margin-top: 20px;
        }
        .pagination a {
            margin: 0 5px;
            text-decoration: none;
            color: #333;
        }
        .pagination a.active {
            font-weight: bold;
        }
        .total-cost {
            margin-top: 20px;
            text-align: right;
        }
        .actions {
            margin-top: 20px;
            text-align: right;
        }
        .actions a {
            margin-left: 10px;
            text-decoration: none;
            color: #333;
        }
        /* 商品背景颜色 */
        .bg-color-1 { background-color: #f9f9f9; }
        .bg-color-2 { background-color: #e9ecef; }
        .bg-color-3 { background-color: #dee2e6; }
        .bg-color-4 { background-color: #ced4da; }
        .bg-color-5 { background-color: #adb5bd; }
    </style>
</head>
<body>
<header>
    <h1>赛博坦美食店</h1>
    <h2>我的购物车</h2>
</header>
<div class="container">
    <%
        List<Shoppingcart> lsc = (List<Shoppingcart>) session.getAttribute("Shoppingcart");
        Userinfo u = (Userinfo) session.getAttribute("userinfo");
        Integer currentPage = 0;
        if (request.getParameter("page") == null) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }
        Integer allPage = 0;
        if (lsc.size() % 10 == 0 && lsc.size() != 0)
            allPage = lsc.size() / 10;
        else
            allPage = lsc.size() / 10 + 1;
        Integer start = (currentPage - 1) * 10;
        Integer end = 0;
        if (lsc.size() - start < 10) {
            end = (currentPage - 1) * 10 + lsc.size() - start;
        } else {
            end = (currentPage - 1) * 10 + 10;
        }
    %>
    <p>您好，<%=u.getRealname()%>，
        <%
            if (lsc.size() == 0) {
        %>
        您尚未购买任何商品，【<a href="goods.do?flag=0">点击这里进行购物</a>】
        <%
        } else {
        %>
        您的购物车中商品如下：
        <%
            }
        %>
    </p>
    <form action="cart.do?flag=2" method="post">
        <ul class="product-list">
            <%
                String[] colors = {"bg-color-1", "bg-color-2", "bg-color-3", "bg-color-4", "bg-color-5"};
                for (int i = start; i < end; i++) {
                    Shoppingcart sc = lsc.get(i);
                    String colorClass = colors[(i - start) % colors.length];
            %>
            <li class="product-item <%=colorClass%>">
                <div class="product-details">
                    <span class="product-name"><%=sc.getGoodsName()%></span>
                    <span class="product-price">价格：<%=sc.getPrice()%></span>
                    <span class="product-subtotal">小计：<%=sc.getNumber() * sc.getPrice()%></span>
                </div>
                <a href="cart.do?flag=1&id=<%=sc.getId()%>">删除商品</a>
            </li>
            <%
                }
            %>
        </ul>
        <div class="total-cost">
            <%
                float allCost = 0;
                for (Shoppingcart sc : lsc) {
                    allCost += sc.getNumber() * sc.getPrice();
                }
            %>
            消费总金额：<%=allCost%>
        </div>
        <div class="actions">
            <form action="checkout" method="post">
                <button type="submit">结账</button>
            </form>
            <a href="goods.do?flag=0">返回继续购物</a>
        </div>
    </form>
    <div class="pagination">
        <a href="selectSC.jsp?page=1">首页</a>
        <%
            for (int i = 1; i <= allPage; i++) {
                if (i == currentPage) {
        %>
        <%=i%>
        <%
        } else {
        %>
        <a href="selectSC.jsp?page=<%=i%>"><%=i%></a>
        <%
                }
            }
        %>
        <a href="selectSC.jsp?page=<%=allPage%>">尾页</a>
        <span>第<%=currentPage%>页</span>
        <span>共<%=allPage%>页</span>
    </div>
</div>
</body>
</html>
