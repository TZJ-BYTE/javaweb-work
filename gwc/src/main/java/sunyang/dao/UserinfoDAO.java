package sunyang.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sunyang.domain.Userinfo;
import sunyang.util.DBConnector;

public class UserinfoDAO {
	private Connection c;

	public UserinfoDAO() {
		c = DBConnector.getConnection();
	}

	public List<Userinfo> findByUsername(String username) throws Exception {
		List<Userinfo> l = new ArrayList<Userinfo>();
		String findByNameSql = "select * from userinfo where username=?";
		PreparedStatement ps = c.prepareStatement(findByNameSql);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Userinfo u=new Userinfo();
			u.setId(rs.getInt("id"));
			u.setPassword(rs.getString("password"));
			u.setUsername(rs.getString("username"));
			u.setEmail(rs.getString("email"));
			u.setRealname(rs.getString("realname"));
			u.setSex(rs.getInt("sex"));
			u.setTel(rs.getString("tel"));
			l.add(u);
		}
		return l;
	}

	public boolean save(Userinfo u) throws Exception {
		
		String saveSql = "insert into userinfo (username,password,sex,realname,tel,email) values(?,?,?,?,?,?)";
		PreparedStatement ps;
		try {
			ps = c.prepareStatement(saveSql);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setInt(3, u.getSex());
			ps.setString(4, u.getRealname());
			ps.setString(5, u.getTel());
			ps.setString(6, u.getEmail());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL异常");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
