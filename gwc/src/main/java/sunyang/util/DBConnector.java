package sunyang.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {
	public static Connection getConnection() {
		String user = "root";
		String psw = "20050528";
		String url = "jdbc:mysql://localhost:3306/shoppingcart?user=" + user
				+ "&password=" + psw
				+ "&useUnicode=true&characterEncoding=GBK";
		Connection conn = null;
		try {
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			conn = DriverManager.getConnection(url);
			return conn;
		} catch (Exception e) {
			System.out.println("连接数据库失败");
			e.printStackTrace();
		}
		return null;
	}
}
