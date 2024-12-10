package org.example.demo5;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JDBConnection {
	
	private Connection con = null;
	private DataSource ds;

	// ͨ�����췽���������ݿ�����
	public JDBConnection() {
		try {
			Context ctx = new InitialContext();
			ctx = (Context) ctx.lookup("java:comp/env");
			ds = (DataSource) ctx.lookup("TestJNDI");
			con = ds.getConnection();
		} catch (Exception e) {
			System.out.println("------ ��ͨ��JNDI�������Դʱ�׳��쳣���������£�");
			e.printStackTrace();
		}
	}

	// �����ݿ�Ĳ�ѯ����
	public ResultSet executeQuery(String sql) {
		ResultSet rs;
		try {
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			return null;
		}
		return rs;
	}
	
	public void closeConnection(){
		if(null!=con){
			try {
				con.close();
			} catch (SQLException e) {		
				e.printStackTrace();
			}
		}
	}
	

}
