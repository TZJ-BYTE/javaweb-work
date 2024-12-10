<%@ page language="java" pageEncoding="gbk"%>

<html>
<head>
	<script type="text/javascript">
		function check(){
			if(document.getElementById("username").value==""){
				alert("用户名不可为空！");
				document.getElementById("username").focus();
				return false;
			}
			if(document.getElementById("password").value==""){
				alert("密码不可为空！");
				document.getElementById("password").focus();
				return false;
			}
			var temp = document.getElementById("safecode");
			var safecodeStr = /^([a-z0-9]){4}$/;
			if(!safecodeStr.test(temp.value)){
				alert('安全码必须由4位的数字和小写字母组成！');
				temp.focus();
				return false;
			}
		}
	</script>
	<title>正超电子商城—用户登录</title>
	<link  href="CSS/style.css" rel="stylesheet" type="text/css">
</head>

<body>


<h1 align="center">正超电子商城</h1>
<h2 align="center">用户登录</h2>


<form action="user.do?flag=1" method="post">



	<table width="304" height="89" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
		<tr>
			<td width="72" align="center">用户名：</td>
			<td colspan="2" bgcolor="#FFFFFF"><input type="text" name="username" /></td>
		</tr>
		<tr>
			<td align="center">密&nbsp;&nbsp;码：</td>
			<td colspan="2" bgcolor="#FFFFFF"><input type="password" name="password" /></td>
		</tr>
		<tr>
			<td align="center">验证码：</td>
			<td width="162" bgcolor="#FFFFFF"><input type="text" name="safecode" /></td>
			<td width="48" bgcolor="#FFFFFF"><img src="safecode" id="safecode" /></td>
		</tr>
	</table><br>
	<div  align="center">
		<input type="submit" name="submit" onClick="return check()"	value="登录" />
		<input type="reset" name="submit" value="重置" />&nbsp;&nbsp;
		<a href="regist.jsp">注册新用户</a>
	</div>


</form>

</body>
</html>
