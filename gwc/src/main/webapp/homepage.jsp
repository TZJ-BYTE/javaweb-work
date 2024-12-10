<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.*" import="sunyang.domain.*"
	import="sunyang.util.*"%>
<html>
	<head>
		<%
			List<Goods> l = (List<Goods>) request.getAttribute("findAllGoods");
			Userinfo u = (Userinfo) session.getAttribute("userinfo");
			PageList pl = (PageList) request.getAttribute("page");
		%>
		<title>查看商品</title>
		<style type="text/css">
<!--
.STYLE1 {
	font-size: 12px
}
-->
</style>
	</head>
<link type="text/css" rel="stylesheet" href="CSS/style.css">
	<body>
		<h1 align="center">正超电子商城</h1>
	<h2 align="center">查看商品</h2>
	
	
	
		<div align="center">
			<table width="600" height="30">
				<tr>
					<td width="456">
						<span class="STYLE1"> 你好,<%=u.getRealname()%>。 </span>
				  </td>
					<td width="56">
						<a href="user.do?flag=2" class="STYLE1">退出登录</a>					</td>
					<td width="72" align="right">
						<a href="selectSC.jsp" class="STYLE1">查看购物车</a>					</td>
				</tr>
			</table>
			<table width="600" border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
				<tr>
					<td height="25">
						<div align="center">商品名					</div></td>
					<td>
						<div align="center">价格					</div></td>
					<td>
						<div align="center">生产厂家					</div></td>
					<td>
						<div align="center">出产日期					</div></td>
					<td>
						<div align="center">商品介绍					</div></td>
				</tr>
				<%
					for (Goods g : l) {
				%>
				<tr>
					<td height="25" bgcolor="#FFFFFF"><a href="goods.do?flag=1&id=<%=g.getId()%>"><%=g.getGoodsname()%></a></td>
					<td bgcolor="#FFFFFF">
					<%=g.getPrice()%>					</td>
					<td bgcolor="#FFFFFF">
					<%=g.getFactory()%>					</td>
					<td bgcolor="#FFFFFF">
					<%=g.getOutdate()%>					</td>
					<td bgcolor="#FFFFFF">
					<%=g.getIntroduction()%>					</td>
				</tr>
				<%
					}
				%>
		  </table>
		</div>
		<div id="pageList">
			<div align="center">
				<ul>
					<span class="STYLE1"><a href="goods.do?flag=0&start=1">首页</a>
						<%
							for (int i = 1; i <= pl.getMaxPage(); i++) {
								if (pl.getCurrentPage() == i) {
						%><%=i%> <%
 	} else {
 %> <a href="goods.do?flag=0&start=<%=i%>"><%=i%></a> <%
 	}
 	}
 %> <a href="goods.do?flag=0&start=<%=pl.getMaxPage()%>">尾页</a> </span>
					<li class="STYLE1">
						第<%=pl.getCurrentPage()%>页
					</li>
					<li class="STYLE1">
						共<%=pl.getMaxPage()%>页
					</li>
				</ul>
			</div>
		</div>

	</body>
</html>
