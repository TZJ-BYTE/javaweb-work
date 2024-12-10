<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.*" import="sunyang.domain.*"%>
<html>
	<head>
		<%
			List<Shoppingcart> lsc = (List<Shoppingcart>) session
					.getAttribute("Shoppingcart");
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
		<title>�ҵĹ��ﳵ</title>
		<script type="text/javascript">
			function numAbove(temp){
				var numStr = /^[1-9]+([0-9]){0,3}$/;
				if(!numStr.test(temp.value)){
					alert('��Ʒ��������ΪС��10000����������');
					temp.focus();
					document.getElementById("submit").disabled=true;
					return false;
				}
				else{
					document.getElementById("submit").disabled=false;
				}
			}
		
		</script>
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
	<h2 align="center">�ҵĹ��ﳵ</h2>
	
		<div align="center">
			<table>
				<tr>
					<td width="608">
						<span class="STYLE1"> ���ã�<%=u.getRealname()%>�� <%
							if (lsc.size() == 0) {
						%> ����δ�����κ���Ʒ���� <a href="goods.do?flag=0">���������й���</a>�� <%
							} else {
						%> ���Ĺ��ﳵ����Ʒ���£� </span>
				  </td>
					<td width="51">
						<a href="user.do?flag=2" class="STYLE1">�˳���¼</a>					</td>
				</tr>
			</table>
		</div>
		<form action="cart.do?flag=1" method="post">
			<div align="center">
				<table width="600" border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
					<tr>
						<td height="25">
							��Ʒ����						</td>
						<td>
							�۸�
						</td>
						<td>
							����
						</td>
						<td>
							�۸�С��
						</td>
					</tr>
					<%
						for (int i = start; i < end; i++) {
								Shoppingcart sc = lsc.get(i);
					%>
					<tr>
						<td height="25" bgcolor="#FFFFFF">
							<%=sc.getGoodsName()%>					  </td>
						<td bgcolor="#FFFFFF">
							<%=sc.getPrice()%>					  </td>
						<td bgcolor="#FFFFFF">
							<input type="text" value="<%=sc.getNumber()%>" name="num"
								onblur="numAbove(this)" />					  </td>
						<td bgcolor="#FFFFFF">
							<%=sc.getNumber() * sc.getPrice()%>					  </td>
						<td bgcolor="#FFFFFF">
							<a href="cart.do?flag=3&id=<%=sc.getId()%>">ɾ����Ʒ</a>						</td>
					</tr>
					<%
						}
					%>
			  </table>
				<table width="600" bgcolor="#999999">
					<tr>
						<td width="102">
							<input type="submit" value="�޸�����" name="submit" />
						</td>
						<td width="189">
							<%
								float allCost = 0;
									for (Shoppingcart sc : lsc) {
										allCost += sc.getNumber() * sc.getPrice();
									}
							%>
							�����ܽ�<%=allCost%>
						</td>
						<td width="108">
							<a href="cart.do?flag=2">��չ��ﳵ</a>
						</td>
						<td width="50">
							<a href="account.do?flag=0">����</a>
						</td>
						<td width="132">
							<a href="goods.do?flag=0">���ؼ�������</a>
						</td>
					</tr>
				</table>
			</div>
			<div>
				<div align="center">
					<ul>
						<span class="STYLE1"><a href="selectSC.jsp?page=1">��ҳ</a> <%
 	for (int i = 1; i <= allPage; i++) {
 			if (i == currentPage) {
 %><%=i%> <%
 	} else {
 %> <a href="selectSC.jsp?page=<%=i%>"><%=i%></a> <%
 	}
 		}
 %> <a href="selectSC.jsp?page=<%=allPage%>">βҳ</a> </span>
						<li class="STYLE1">
							��<%=currentPage%>ҳ
						</li>
						<li class="STYLE1">
							��<%=allPage%>ҳ
						</li>
					</ul>
				</div>
			</div>
		</form>
		<div align="center">
			<span class="STYLE1"> <%
 	}
 %> </span>
		</div>
	</body>
</html>
