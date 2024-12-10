<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="sunyang.domain.*"%>
<html>
	<head>
		<%
			Goods g = (Goods) request.getAttribute("good");
			Userinfo u = (Userinfo) session.getAttribute("userinfo");
		%>
		<title>单查商品</title>
	    <style type="text/css">
<!--
.STYLE1 {font-size: 12px}
-->
        </style>
</head>
<link type="text/css" rel="stylesheet" href="CSS/style.css">
	<body>
	<h1 align="center">正超电子商城</h1>
	    <h2 align="center">单查商品</h2>
		<div align="left" class="STYLE1"> 
		  <div align="center">你好,<%=u.getRealname()%>。		</div>
		</div>
		<form action="cart.do?flag=0&id=<%=g.getId()%>" method="post">
		  <div>
			  <table border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
			    <tr>
			      <td rowspan="5">
			        <img src="goodsPhoto/<%=g.getPic()%>" />			        </td>
				    <td bordercolor="#FFFFFF" bgcolor="#FFFFFF">
					    &nbsp&nbsp商品名：
					    <%=g.getGoodsname()%>			        </td>
			    </tr>
			    <tr>
			      <td bordercolor="#FFFFFF" bgcolor="#FFFFFF">
			        &nbsp&nbsp&nbsp&nbsp价格：
			        <%=g.getPrice()%>			        </td>
			    </tr>
			    <tr>
			      <td bordercolor="#FFFFFF" bgcolor="#FFFFFF">
			        生产厂家：
			        <%=g.getFactory()%>			        </td>
			    </tr>
			    <tr>
			      <td bordercolor="#FFFFFF" bgcolor="#FFFFFF">
			        生产日期：
			        <%=g.getOutdate()%>			        </td>
			    </tr>
			    <tr>
			      <td bordercolor="#FFFFFF" bgcolor="#FFFFFF">
			        商品简介：
			        <%=g.getIntroduction()%>			        </td>
			    </tr>
			    <tr>
			      <td align="right">
			        <input type="submit" value="加入购物车" />			        </td>
				    <td>
					    <input type="button" value="返回继续购物" onClick="javascript:history.go(-1)"/>			        </td>
			    </tr>
            </table>
		  </div>
		</form>
	</body>
</html>
