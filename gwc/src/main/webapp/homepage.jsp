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
		<title>�鿴��Ʒ</title>
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
		<h1 align="center">���������̳�</h1>
	<h2 align="center">�鿴��Ʒ</h2>
	
	
	
		<div align="center">
			<table width="600" height="30">
				<tr>
					<td width="456">
						<span class="STYLE1"> ���,<%=u.getRealname()%>�� </span>
				  </td>
					<td width="56">
						<a href="user.do?flag=2" class="STYLE1">�˳���¼</a>					</td>
					<td width="72" align="right">
						<a href="selectSC.jsp" class="STYLE1">�鿴���ﳵ</a>					</td>
				</tr>
			</table>
			<table width="600" border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
				<tr>
					<td height="25">
						<div align="center">��Ʒ��					</div></td>
					<td>
						<div align="center">�۸�					</div></td>
					<td>
						<div align="center">��������					</div></td>
					<td>
						<div align="center">��������					</div></td>
					<td>
						<div align="center">��Ʒ����					</div></td>
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
					<span class="STYLE1"><a href="goods.do?flag=0&start=1">��ҳ</a>
						<%
							for (int i = 1; i <= pl.getMaxPage(); i++) {
								if (pl.getCurrentPage() == i) {
						%><%=i%> <%
 	} else {
 %> <a href="goods.do?flag=0&start=<%=i%>"><%=i%></a> <%
 	}
 	}
 %> <a href="goods.do?flag=0&start=<%=pl.getMaxPage()%>">βҳ</a> </span>
					<li class="STYLE1">
						��<%=pl.getCurrentPage()%>ҳ
					</li>
					<li class="STYLE1">
						��<%=pl.getMaxPage()%>ҳ
					</li>
				</ul>
			</div>
		</div>

	</body>
</html>
