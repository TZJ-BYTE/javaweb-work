<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<jsp:useBean id="testJDBC" class="com.TestJDBC"/>
<html>
  <head>
    <title>应用JDBC事务</title>
  </head>
  
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
      <%
      String outAccounts=request.getParameter("outAccounts");
      String outPassword=request.getParameter("outPassword");
      String inAccounts=request.getParameter("inAccounts");
      String inName=request.getParameter("inName");
      inName = new String(inName.trim().getBytes("ISO8859-1"),"GB2312");
      String operate_money=request.getParameter("operate_money");
      int money = new Integer(operate_money);
      boolean isTurn=testJDBC.turn(outAccounts,outPassword,inAccounts,inName,money);
      %>
      <tr>
        <td align="center"><font size="4"><b>转账提示</b></font></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr align="center">
        <td>
        <%
        if(isTurn){
        	out.print("转账成功，成功转入账户"+inAccounts+"人民币"+operate_money+"元！");
        }else{
        	out.print("转账失败，请确认转账信息是否正确！");
        }
        %>        
        </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr align="center">
        <td><a href="index.jsp">返回</a></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table></td>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
  
</html>
