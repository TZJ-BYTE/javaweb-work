<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.*" import="sunyang.domain.*"
	import="sunyang.util.*"%>
<html>
	<head>
		<title>您的账单</title>
		<%
			List<Shoppingcart> lsc = (List<Shoppingcart>) session
					.getAttribute("Shoppingcart");
			Userinfo u = (Userinfo) session.getAttribute("userinfo");
		%>
		<script type="text/javascript">
			function check(){
				var temp = document.getElementById("postcode");
				var postcodeStr = /^([0-9]){6}$/;
				if(!postcodeStr.test(temp.value)){
					alert('请输入正确的邮编！');
					temp.focus();
					return false;
				}
				
				if(document.getElementById("address").value==""){
					alert('请输入地址');
					document.getElementById("address").focus();
					return false;
				}
			}
		</script>
	</head>
	<link type="text/css" rel="stylesheet" href="CSS/style.css">
	<body>
		<h1 align="center">正超电子商城</h1>
	    <h2 align="center">您的账单</h2>
		<form action="account.do?flag=1" method="post">
			<div align="center">
				<table border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
					<tr>
						<td>
							&nbsp&nbsp订单号：
							<input type="text" name="accountcode" readonly
								value="<%=request.getAttribute("accountCode")%>" />
						</td>
					</tr>
					<tr>
						<td>
							&nbsp&nbsp用户名：
							<input type="text" name="username" readonly
								value="<%=u.getUsername()%>" />
						</td>
					</tr>
					<tr>
						<td>
							真实姓名：
							<input type="text" name="realname" readonly
								value="<%=u.getRealname()%>" />
						</td>
					</tr>
					<tr>
						<td>
							&nbsp&nbsp&nbspEmail：
							<input type="text" name="Email" readonly
								value="<%=u.getEmail()%>" />
						</td>
					</tr>
					<tr>
						<td>
							用户地址：
							<input type="text" name="address" />
						</td>
					</tr>
					<tr>
						<td>
							&nbsp&nbsp&nbsp&nbsp邮编：
							<input type="text" name="postcode" />
						</td>
					</tr>
					<tr>
						<td>
							联系电话：
							<input type="text" name="tel" readonly value="<%=u.getTel()%>" />
						</td>
					</tr>
					<tr>
						<td>
							<input type="submit" value="结账" onClick="return check()">
							<input type="button" value="返回"
								onClick="javascript:history.go(-1)">
						</td>
					</tr>
			  </table>
			</div>
		</form>
	</body>
</html>
