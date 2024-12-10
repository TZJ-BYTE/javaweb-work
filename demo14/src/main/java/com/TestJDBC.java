package com;
import java.sql.*;
import java.util.Calendar;

public class TestJDBC {
	private static final String DRIVERCLASS = "com.mysql.cj.jdbc.Driver";
	protected final String url = "jdbc:mysql://localhost:3306/bank_db";
	protected final String username = "root";
	protected final String password = "20050528";
	protected Connection conn;
	protected Statement stmt;
	protected PreparedStatement prpdStmt;
	protected CallableStatement cablStmt;
	protected ResultSet rs;

	static {
		try {
			Class.forName(DRIVERCLASS);
		} catch (ClassNotFoundException e) {
			System.out.println("------ 在加载数据库驱动时抛出异常，内容如下：");
		}
	}

	public boolean openConn(boolean autoCommit) {
		boolean isOpen = true;
		try {
			conn = DriverManager.getConnection(url, username, password);
			if (!autoCommit)
				conn.setAutoCommit(false);
		} catch (SQLException e) {
			isOpen = false;
			System.out.println("------ 在创建数据库连接时抛出异常，内容如下：");
			e.printStackTrace();
		}
		return isOpen;
	}

	public ResultSet executeQuery(String sql) {
		ResultSet rs=null;
		try {
			if (conn == null) {
				openConn(true);
			}
			Statement stmt = conn.createStatement();
			rs=stmt.executeQuery(sql);
			return rs;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean turn(String outAccounts, String outPassword,
						String inAccounts, String inName, int operate_money) {
		boolean isTurn = true;
		String out_name = "";
		int out_total_amount = 0;
		String in_password = "";
		int in_total_amount = 0;

		this.openConn(true);
		try {
			stmt = conn.createStatement();
			rs = stmt
					.executeQuery("select * from tb_money where id = (select max(id) from tb_money where accounts = '"
							+ outAccounts
							+ "' and password = '"
							+ outPassword
							+ "')");
			if (rs.next()) {
				out_name = rs.getString(2);
				out_total_amount = rs.getInt(6);
			}
			rs = stmt
					.executeQuery("select * from tb_money where id = (select max(id) from tb_money where accounts = '"
							+ inAccounts + "' and name = '" + inName + "')");
			if (rs.next()) {
				in_password = rs.getString(4);
				in_total_amount = rs.getInt(6);
			}
		} catch (SQLException e) {
			System.out.println("----- 在获取转帐条件时抛出异常，内容如下：");
			e.printStackTrace();
		}
		this.closeConn();
		if (out_total_amount != 0 && in_total_amount != 0) {
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH) + 1;
			int day = now.get(Calendar.DAY_OF_MONTH);
			int hour = now.get(Calendar.HOUR_OF_DAY);
			int minute = now.get(Calendar.MINUTE);
			String date = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
			this.openConn(false);
			try {
				prpdStmt = conn.prepareStatement("insert into tb_money values(?,?,?,?,?,?)");
				prpdStmt.setString(1, inName);
				prpdStmt.setString(2, inAccounts);
				prpdStmt.setString(3, in_password);
				prpdStmt.setInt(4, operate_money);
				prpdStmt.setInt(5, in_total_amount + operate_money);
				prpdStmt.setString(6, date);
				prpdStmt.executeUpdate();
				prpdStmt.setString(1, out_name);
				prpdStmt.setString(2, outAccounts);
				prpdStmt.setString(3, outPassword);
				prpdStmt.setInt(4, -operate_money);
				prpdStmt.setInt(5, out_total_amount - operate_money);
				prpdStmt.setString(6, date);
				prpdStmt.executeUpdate();
			} catch (SQLException e) {
				isTurn = false;
				System.out.println("------ 在转帐时抛出异常，内容如下：");
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					System.out.println("------ 在回滚数据库事务时抛出异常，内容如下：");
					e1.printStackTrace();
				}
			}
			this.closeConn();
		} else {
			isTurn = false;
		}

		return isTurn;
	}

	public boolean closeConn() {
		boolean isCommit = true;
		try {
			conn.commit();
		} catch (SQLException e) {
			isCommit = false;
			System.out.println("------ 在提交数据库事务时抛出异常，内容如下：");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("------ 在回滚数据库事务时抛出异常，内容如下：");
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("------ 在关闭数据库连接时抛出异常，内容如下：");
				e.printStackTrace();
			}
		}
		return isCommit;
	}

}