<%@ page contentType="text/html; charset=gb2312" import="java.sql.*" errorPage="" %>
<%@page import="com.JDBConnection"%>
<%
JDBConnection connection=new JDBConnection();	//ʵ����JDBC���ȡ���ݿ�����
String sql="select * from users";				//���ö�users���ݱ�ȫ����ѯ��SQL���
ResultSet rs=connection.executeQuery(sql);		//ִ�в�ѯ��SQL���
%>
<link href="CSS/style.css" type="text/css" rel="stylesheet" />
<h3 align="center">��Tomcat���������������Ӳ���ȡ��ѯ�Ľ��</h3>
<table width="496" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
<%try{ %>
  <tr align="center">
    <td height="30">�Զ����</td>
    <td>�û���</td>
    <td>��������</td>
    <td>�Ա�</td>
    <td>��ͥסַ</td>
    <td>�绰</td>
  </tr>
 <% while(rs.next()){ %>							<!--ѭ����ʾ���ݱ����Ϣ-->
  <tr align="center">
    <td height="30" bgcolor="#FFFFFF"><%=rs.getString(1)%></td>
    <td bgcolor="#FFFFFF"><%=rs.getString(2)%></td>
    <td bgcolor="#FFFFFF"><%=rs.getString(3)%></td>
    <td bgcolor="#FFFFFF"><%=rs.getString(4)%></td>
    <td bgcolor="#FFFFFF"><%=rs.getString(5)%></td>
    <td bgcolor="#FFFFFF"><%=rs.getString(6)%></td>
  </tr>
  <%}}catch(Exception e){} %>
</table>
