<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<jsp:useBean id="testJDBC" class="com.TestJDBC"></jsp:useBean>
<html>
  <head>
    <title>Ӧ��JDBC����</title>
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
        <td align="center"><font size="5"><b>ת�������</b></font></td>
      </tr>
<form action="clue_on.jsp" method="post">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td align="center"><table width="30%" border="1" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
          <tr>
            <td rowspan="2">ת����</td>
            <td bgcolor="#FFFFFF">�˺ţ�
              <input name="outAccounts" value="654321" type="text" size="8" maxlength="6"></td>
          </tr>
          <tr>
            <td bgcolor="#FFFFFF">���룺
              <input name="outPassword" value="6521" type="text" size="8" maxlength="4"></td>
          </tr>
          <tr>
            <td colspan="2" bgcolor="#FFFFFF">&nbsp;</td>
          </tr>
          <tr>
            <td rowspan="2">ת�뷽</td>
            <td bgcolor="#FFFFFF">�ʺţ�
              <input name="inAccounts" value="123456" type="text" size="8" maxlength="6"></td>
          </tr>
          <tr>
            <td bgcolor="#FFFFFF">�û���              
              <input name="inName" value="��ϿƼ�" type="text" size="8" maxlength="20"></td>
          </tr>
          <tr>
            <td colspan="2" bgcolor="#FFFFFF">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2" bgcolor="#FFFFFF">ת�ʽ�
              <input name="operate_money" type="text" size="8" maxlength="8">
              Ԫ</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr align="center">
        <td><input type="reset" name="Submit2" value="����">&nbsp;&nbsp;
              <input type="submit" name="Submit3" value="�ύ"></td>
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
