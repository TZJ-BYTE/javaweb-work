<%@ page contentType="text/html; charset=gb2312" import="java.sql.*" errorPage="" %>
<%@page import="com.JDBConnection"%>
<%
JDBConnection connection=new JDBConnection();	//实例化JDBC类获取数据库连接
String sql="select * from users";				//设置对users数据表全部查询的SQL语句
ResultSet rs=connection.executeQuery(sql);		//执行查询的SQL语句
%>
<link href="CSS/style.css" type="text/css" rel="stylesheet" />
<h3 align="center">在Tomcat服务器上配置连接并获取查询的结果</h3>
<table width="496" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#CCCCCC">
<%try{ %>
  <tr align="center">
    <td height="30">自动编号</td>
    <td>用户名</td>
    <td>出生日期</td>
    <td>性别</td>
    <td>家庭住址</td>
    <td>电话</td>
  </tr>
 <% while(rs.next()){ %>							<!--循环显示数据表的信息-->
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
