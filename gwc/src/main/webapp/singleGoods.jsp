<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="sunyang.domain.*"%>
<html>
	<head>
		<%
			Goods g = (Goods) request.getAttribute("good");
			Userinfo u = (Userinfo) session.getAttribute("userinfo");
		%>
		<title>������Ʒ</title>
	    <style type="text/css">
<!--
.STYLE1 {font-size: 12px}
-->
        </style>
</head>
<link type="text/css" rel="stylesheet" href="CSS/style.css">
	<body>
	<h1 align="center">���������̳�</h1>
	    <h2 align="center">������Ʒ</h2>
		<div align="left" class="STYLE1"> 
		  <div align="center">���,<%=u.getRealname()%>��		</div>
		</div>
		<form action="cart.do?flag=0&id=<%=g.getId()%>" method="post">
		  <div>
			  <table border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
			    <tr>
			      <td rowspan="5">
			        <img src="goodsPhoto/<%=g.getPic()%>" />			        </td>
				    <td bordercolor="#FFFFFF" bgcolor="#FFFFFF">
					    &nbsp&nbsp��Ʒ����
					    <%=g.getGoodsname()%>			        </td>
			    </tr>
			    <tr>
			      <td bordercolor="#FFFFFF" bgcolor="#FFFFFF">
			        &nbsp&nbsp&nbsp&nbsp�۸�
			        <%=g.getPrice()%>			        </td>
			    </tr>
			    <tr>
			      <td bordercolor="#FFFFFF" bgcolor="#FFFFFF">
			        �������ң�
			        <%=g.getFactory()%>			        </td>
			    </tr>
			    <tr>
			      <td bordercolor="#FFFFFF" bgcolor="#FFFFFF">
			        �������ڣ�
			        <%=g.getOutdate()%>			        </td>
			    </tr>
			    <tr>
			      <td bordercolor="#FFFFFF" bgcolor="#FFFFFF">
			        ��Ʒ��飺
			        <%=g.getIntroduction()%>			        </td>
			    </tr>
			    <tr>
			      <td align="right">
			        <input type="submit" value="���빺�ﳵ" />			        </td>
				    <td>
					    <input type="button" value="���ؼ�������" onClick="javascript:history.go(-1)"/>			        </td>
			    </tr>
            </table>
		  </div>
		</form>
	</body>
</html>
