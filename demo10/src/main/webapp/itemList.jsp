<%@ page language="java" pageEncoding="GBk"%>
<html>
	<head>
		<title>商品页面</title>
	</head>
	<body  >
		<center><h2>选购商品</h2></center>
		<form action="buyItem" method="post">
		<table border=1 align="center" width=250 bgcolor="#WFFFFF">
		<tr>		
		<td width=15><input type="checkbox" name="item" value="0"></td>
		<td width=200>图书</td>
		</tr>
		<tr>		
		<td ><input type="checkbox" name="item" value="1"></td>
		<td>化妆品</td>
		</tr>
		<tr>
		<td><input type="checkbox" name="item" value="2"></td>
		<td>衣服</td>
		</tr>
		<tr>
		<td colspan=2 align="center"><input type="submit" name="sumit" value="加入购物车"></td>
		</tr>
		</table>				
		</form>
	</body>
</html>