<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<jsp:useBean id="testJDBC" class="com.TestJDBC"></jsp:useBean>
<html>
  <head>
    <title>应用JDBC事务</title>
  </head>  
  <link href="CSS/style.css" type="text/css" rel="stylesheet">
<body topmargin="0">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="11%"></td>
    <td width="78%"></td>
    <td width="11%"></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td bgcolor="#CCFFFF"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td align="center"><font size="5"><b>转账申请表</b></font></td>
      </tr>
<form action="clue_on.jsp" method="post">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td align="center"><table width="30%" border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
          <tr>
            <td rowspan="2">转出方</td>
            <td bgcolor="#FFFFFF">账号：
              <input name="outAccounts" value="654321" type="text" size="8" maxlength="6"></td>
          </tr>
          <tr>
            <td bgcolor="#FFFFFF">密码：
              <input name="outPassword" value="6521" type="text" size="8" maxlength="4"></td>
          </tr>
          <tr>
            <td colspan="2" bgcolor="#FFFFFF">&nbsp;</td>
          </tr>
          <tr>
            <td rowspan="2">转入方</td>
            <td bgcolor="#FFFFFF">帐号：
              <input name="inAccounts" value="123456" type="text" size="8" maxlength="6"></td>
          </tr>
          <tr>
            <td bgcolor="#FFFFFF">用户：              
              <input name="inName" value="倾诚科技" type="text" size="8" maxlength="20"></td>
          </tr>
          <tr>
            <td colspan="2" bgcolor="#FFFFFF">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2" bgcolor="#FFFFFF">转帐金额：
              <input name="operate_money" type="text" size="8" maxlength="8">
              元</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr align="center">
        <td><input type="reset" name="Submit2" value="重置">&nbsp;&nbsp;
              <input type="submit" name="Submit3" value="提交"></td>
      </tr>
</form>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table></td>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
  
</html>
