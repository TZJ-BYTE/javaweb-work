package sunyang.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import sunyang.domain.Account;
import sunyang.util.DBConnector;

public class AccountDAO {
	private Connection c;

	public AccountDAO() {
		c = DBConnector.getConnection();
	}

	public boolean save(Account a) {
		String saveSql = "insert into account (userid,address,postcode,accountcode,accountdate,execute) values(?,?,?,?,?,?)";
		PreparedStatement ps;
		try {
			ps = c.prepareStatement(saveSql);
			ps.setInt(1, a.getUserid());
			ps.setString(2, a.getAddress());
			ps.setString(3, a.getPostcode());
			ps.setString(4, a.getAccountcode());
			ps.setString(5,a.getAccountdate());
			ps.setInt(6, a.getExecute());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL异常");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
