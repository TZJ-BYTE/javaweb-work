<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.*" import="sunyang.domain.*"
	import="sunyang.util.*"%>
<html>
	<head>
		<title>�����˵�</title>
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
					alert('��������ȷ���ʱ࣡');
					temp.focus();
					return false;
				}
				
				if(document.getElementById("address").value==""){
					alert('�������ַ');
					document.getElementById("address").focus();
					return false;
				}
			}
		</script>
	</head>
	<link type="text/css" rel="stylesheet" href="CSS/style.css">
	<body>
		<h1 align="center">���������̳�</h1>
	    <h2 align="center">�����˵�</h2>
		<form action="account.do?flag=1" method="post">
			<div align="center">
				<table border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
					<tr>
						<td>
							&nbsp&nbsp�����ţ�
							<input type="text" name="accountcode" readonly
								value="<%=request.getAttribute("accountCode")%>" />
						</td>
					</tr>
					<tr>
						<td>
							&nbsp&nbsp�û�����
							<input type="text" name="username" readonly
								value="<%=u.getUsername()%>" />
						</td>
					</tr>
					<tr>
						<td>
							��ʵ������
							<input type="text" name="realname" readonly
								value="<%=u.getRealname()%>" />
						</td>
					</tr>
					<tr>
						<td>
							&nbsp&nbsp&nbspEmail��
							<input type="text" name="Email" readonly
								value="<%=u.getEmail()%>" />
						</td>
					</tr>
					<tr>
						<td>
							�û���ַ��
							<input type="text" name="address" />
						</td>
					</tr>
					<tr>
						<td>
							&nbsp&nbsp&nbsp&nbsp�ʱࣺ
							<input type="text" name="postcode" />
						</td>
					</tr>
					<tr>
						<td>
							��ϵ�绰��
							<input type="text" name="tel" readonly value="<%=u.getTel()%>" />
						</td>
					</tr>
					<tr>
						<td>
							<input type="submit" value="����" onClick="return check()">
							<input type="button" value="����"
								onClick="javascript:history.go(-1)">
						</td>
					</tr>
			  </table>
			</div>
		</form>
	</body>
</html>
