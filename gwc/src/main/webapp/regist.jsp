<%@ page language="java" pageEncoding="gbk"%>

<html>
	<head>
		<title>�û�ע��ҳ��</title>
		<script type="text/javascript">
			function check(){
				if(document.getElementById("username").value.length < 6){
					alert("�û����������6λ��");
					document.getElementById("username").focus();
					return false;
				}
				if(document.getElementById("password").value.length<6){
					alert("�û�����������6λ��");
					document.getElementById("password").focus();
					return false;
				}
				if(document.getElementById("password").value!=document.getElementById("passwordConfirm").value){
					alert("�����������벻��ͬ��");
					document.getElementById("passwordConfirm").focus();
					return false;
				}
				if(document.getElementById("realname").value.length<=0){
					document.getElementById("realname").focus();
					alert("�������û�����ʵ������");
					return false;
				}
				var temp = document.getElementById("tel");
				var telStr = /^([0-9])+[0-9]*$/;
				if(!telStr.test(temp.value)){
					alert('��������ȷ�ĵ绰���룡');
					temp.focus();
				return false;
				}
				var temp = document.getElementById("email");
				var emailStr = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
				if(!emailStr.test(temp.value)){
					alert('��������ȷ��E_mail!');
					temp.focus();
					return false;
				}
			}
		</script>
	</head>
<link type="text/css" rel="stylesheet" href="CSS/style.css">
	<body>

	<h1 align="center">���������̳�</h1>
	<h2 align="center">�û�ע��</h2>
				
		<form action="user.do?flag=0" method="post">	
	<table width="289" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
      <tr>
        <td width="80" height="30" align="right">�û�����</td>
        <td width="196" bgcolor="#FFFFFF"><input type="text" name="username" /></td>
      </tr>
      <tr>
        <td height="30" align="right">���룺</td>
        <td bgcolor="#FFFFFF"><input type="password" name="password" /></td>
      </tr>
      <tr>
        <td height="30" align="right">ȷ�����룺</td>
        <td bgcolor="#FFFFFF"><input type="password" name="passwordConfirm" /></td>
      </tr>
      <tr>
        <td height="30" align="right">�Ա�</td>
        <td bgcolor="#FFFFFF"><input name="sex" type="radio" class="inputinputinput" value="0" checked="checked" />
				��&nbsp;&nbsp;
				<input name="sex" type="radio" class="inputinputinput" value="1" />
				Ů
		</td>
      </tr>
      <tr>
        <td height="30" align="right">��ʵ������</td>
        <td bgcolor="#FFFFFF"><input type="text" name="realname" /></td>
      </tr>
      <tr>
        <td height="30" align="right">�绰:</td>
        <td bgcolor="#FFFFFF"><input type="text" name="tel" /></td>
      </tr>
      <tr>
        <td height="30" align="right">���䣺</td>
        <td bgcolor="#FFFFFF"><input type="text" name="email" /></td>
      </tr>
    </table>
	<br>
	<div align="center">
			    <input type="submit" name="submit" value="ע��" onClick="return check()"/>
			    <input type="reset" name="submit" value="����" />
	</div>
	
	</form>
	
	</body>
</html>
