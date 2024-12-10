<%@ page language="java" pageEncoding="gbk"%>

<html>
	<head>
		<title>用户注册页面</title>
		<script type="text/javascript">
			function check(){
				if(document.getElementById("username").value.length < 6){
					alert("用户名必须大于6位！");
					document.getElementById("username").focus();
					return false;
				}
				if(document.getElementById("password").value.length<6){
					alert("用户密码必须大于6位！");
					document.getElementById("password").focus();
					return false;
				}
				if(document.getElementById("password").value!=document.getElementById("passwordConfirm").value){
					alert("两次输入密码不相同！");
					document.getElementById("passwordConfirm").focus();
					return false;
				}
				if(document.getElementById("realname").value.length<=0){
					document.getElementById("realname").focus();
					alert("请输入用户的真实姓名！");
					return false;
				}
				var temp = document.getElementById("tel");
				var telStr = /^([0-9])+[0-9]*$/;
				if(!telStr.test(temp.value)){
					alert('请输入正确的电话号码！');
					temp.focus();
				return false;
				}
				var temp = document.getElementById("email");
				var emailStr = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
				if(!emailStr.test(temp.value)){
					alert('请输入正确的E_mail!');
					temp.focus();
					return false;
				}
			}
		</script>
	</head>
<link type="text/css" rel="stylesheet" href="CSS/style.css">
	<body>

	<h1 align="center">正超电子商城</h1>
	<h2 align="center">用户注册</h2>
				
		<form action="user.do?flag=0" method="post">	
	<table width="289" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
      <tr>
        <td width="80" height="30" align="right">用户名：</td>
        <td width="196" bgcolor="#FFFFFF"><input type="text" name="username" /></td>
      </tr>
      <tr>
        <td height="30" align="right">密码：</td>
        <td bgcolor="#FFFFFF"><input type="password" name="password" /></td>
      </tr>
      <tr>
        <td height="30" align="right">确认密码：</td>
        <td bgcolor="#FFFFFF"><input type="password" name="passwordConfirm" /></td>
      </tr>
      <tr>
        <td height="30" align="right">性别：</td>
        <td bgcolor="#FFFFFF"><input name="sex" type="radio" class="inputinputinput" value="0" checked="checked" />
				男&nbsp;&nbsp;
				<input name="sex" type="radio" class="inputinputinput" value="1" />
				女
		</td>
      </tr>
      <tr>
        <td height="30" align="right">真实姓名：</td>
        <td bgcolor="#FFFFFF"><input type="text" name="realname" /></td>
      </tr>
      <tr>
        <td height="30" align="right">电话:</td>
        <td bgcolor="#FFFFFF"><input type="text" name="tel" /></td>
      </tr>
      <tr>
        <td height="30" align="right">邮箱：</td>
        <td bgcolor="#FFFFFF"><input type="text" name="email" /></td>
      </tr>
    </table>
	<br>
	<div align="center">
			    <input type="submit" name="submit" value="注册" onClick="return check()"/>
			    <input type="reset" name="submit" value="重置" />
	</div>
	
	</form>
	
	</body>
</html>
