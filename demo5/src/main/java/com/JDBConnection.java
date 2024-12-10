package come;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBConnection {

	private Connection con = null;

	public JDBConnection() {
		try {
			// 注册 JDBC 驱动程序
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 数据库 URL，用户名和密码
			String url = "jdbc:mysql://localhost:3306/mydata";
			String user = "root";
			String password = "20050528";

			// 建立连接
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.out.println("------ JDBC 驱动程序未找到，请检查类路径设置。");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("------ 获取数据库连接时发生异常。");
			e.printStackTrace();
		}
	}

	// 执行数据库的查询操作
	public ResultSet executeQuery(String sql) {
		ResultSet rs = null;
		try {
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public void closeConnection() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
