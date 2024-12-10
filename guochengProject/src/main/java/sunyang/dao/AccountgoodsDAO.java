package sunyang.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import sunyang.domain.Accountgoods;
import sunyang.util.DBConnector;

public class AccountgoodsDAO {
	private Connection c;

	public AccountgoodsDAO() {
		c = DBConnector.getConnection();
	}

	public boolean save(Accountgoods ag) {
		String saveSql = "insert into accountgoods (goodsid,accountcode,num) values(?,?,?)";
		PreparedStatement ps;
		try {
			ps = c.prepareStatement(saveSql);
			ps.setInt(1, ag.getGoodsId());
			ps.setString(2, ag.getAccountcode());
			ps.setInt(3, ag.getNum());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL异常");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
