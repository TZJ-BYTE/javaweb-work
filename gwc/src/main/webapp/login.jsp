<%@ page language="java" pageEncoding="gbk"%>

<html>
<head>
	<script type="text/javascript">
		function check(){
			if(document.getElementById("username").value==""){
				alert("�û�������Ϊ�գ�");
				document.getElementById("username").focus();
				return false;
			}
			if(document.getElementById("password").value==""){
				alert("���벻��Ϊ�գ�");
				document.getElementById("password").focus();
				return false;
			}
			var temp = document.getElementById("safecode");
			var safecodeStr = /^([a-z0-9]){4}$/;
			if(!safecodeStr.test(temp.value)){
				alert('��ȫ�������4λ�����ֺ�Сд��ĸ��ɣ�');
				temp.focus();
				return false;
			}
		}
	</script>
	<title>���������̳ǡ��û���¼</title>
	<link  href="CSS/style.css" rel="stylesheet" type="text/css">
</head>

<body>


<h1 align="center">���������̳�</h1>
<h2 align="center">�û���¼</h2>


<form action="user.do?flag=1" method="post">



	<table width="304" height="89" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
		<tr>
			<td width="72" align="center">�û�����</td>
			<td colspan="2" bgcolor="#FFFFFF"><input type="text" name="username" /></td>
		</tr>
		<tr>
			<td align="center">��&nbsp;&nbsp;�룺</td>
			<td colspan="2" bgcolor="#FFFFFF"><input type="password" name="password" /></td>
		</tr>
		<tr>
			<td align="center">��֤�룺</td>
			<td width="162" bgcolor="#FFFFFF"><input type="text" name="safecode" /></td>
			<td width="48" bgcolor="#FFFFFF"><img src="safecode" id="safecode" /></td>
		</tr>
	</table><br>
	<div  align="center">
		<input type="submit" name="submit" onClick="return check()"	value="��¼" />
		<input type="reset" name="submit" value="����" />&nbsp;&nbsp;
		<a href="regist.jsp">ע�����û�</a>
	</div>


</form>

</body>
</html>
