<%@ page language="java" pageEncoding="gbk"%>

<html>
<head>
	<script type="text/javascript">
		/**
		 * ���ύǰ�ļ�麯��
		 * ����û���������Ͱ�ȫ���Ƿ����Ҫ��
		 * ���������Ҫ����ֹ���ύ
		 * @returns {boolean} ��ʾ���Ƿ�����ύ
		 */
		function check(){
			// ����û����Ƿ�Ϊ��
			if(document.getElementById("username").value==""){
				alert("�û�������Ϊ�գ�");
				document.getElementById("username").focus();
				return false;
			}

			// ��������Ƿ�Ϊ��
			if(document.getElementById("password").value==""){
				alert("���벻��Ϊ�գ�");
				document.getElementById("password").focus();
				return false;
			}

			// ��鰲ȫ���Ƿ����Ҫ��4λ���ֺ�Сд��ĸ��
			var temp = document.getElementById("safecode");
			var safecodeStr = /^([a-z0-9]){4}$/;           //������ʽ
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

<form action="user.do?flag=1" method="post" onsubmit="return check()">

	<table width="304" height="89" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
		<tr>
			<td width="72" align="center">�û�����</td>
			<td colspan="2" bgcolor="#FFFFFF"><input type="text" name="username" id="username" /></td>
		</tr>
		<tr>
			<td align="center">��&nbsp;&nbsp;�룺</td>
			<td colspan="2" bgcolor="#FFFFFF"><input type="password" name="password" id="password" /></td>
		</tr>
		<tr>
			<td align="center">��֤�룺</td>
			<td width="162" bgcolor="#FFFFFF"><input type="text" name="safecode" id="safecode" /></td>
			<td width="48" bgcolor="#FFFFFF"><img src="safecode" id="safecodeImg" /></td>
		</tr>
	</table><br>
	<div  align="center">
		<input type="submit" name="submit" value="��¼" />
		<input type="reset" name="submit" value="����" />&nbsp;&nbsp;

		<a href="regist.jsp">ע�����û�</a>
	</div>

</form>

</body>
</html>
