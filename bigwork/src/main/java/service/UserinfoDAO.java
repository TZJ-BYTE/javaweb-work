package service;



import model.Userinfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserinfoDAO {
	private Connection c;

	public UserinfoDAO() {
		c = DBConnector.getConnection();
	}

	/**
	 * 根据用户名查询用户信息
	 *
	 * @param username 用户名，用于查询用户信息
	 * @return 返回一个Userinfo对象的列表，包含所有匹配的用户信息
	 * @throws Exception 如果数据库操作失败，抛出此异常
	 */
	public List<Userinfo> findByUsername(String username) throws Exception {
	    // 创建一个空的Userinfo列表，用于存储查询结果
	    List<Userinfo> l = new ArrayList<Userinfo>();

	    // SQL查询语句，用于从userinfo表中根据用户名查询所有列
	    String findByNameSql = "select * from userinfo where username=?";

	    // 准备SQL语句，防止SQL注入，提高代码安全性
	    PreparedStatement ps = c.prepareStatement(findByNameSql);

	    // 设置查询参数为传入的username，完成对SQL语句的动态部分赋值
	    ps.setString(1, username);

	    // 执行查询操作，获取结果集
	    ResultSet rs = ps.executeQuery();

	    // 遍历查询结果，将每条记录封装成Userinfo对象
	    while (rs.next()) {
	        // 创建新的Userinfo对象
	        Userinfo u = new Userinfo();

	        // 从结果集中获取各列值，设置Userinfo对象属性
	        u.setId(rs.getInt("id"));
	        u.setPassword(rs.getString("password"));
	        u.setUsername(rs.getString("username"));
	        u.setRealname(rs.getString("realname"));

	        // 将封装好的Userinfo对象添加到列表中
	        l.add(u);
	    }

	    // 返回包含所有查询结果的Userinfo对象列表
	    return l;
	}

	public boolean save(Userinfo u) throws Exception {
		
		String saveSql = "insert into userinfo (username,password,realname) values(?,?,?)";
		PreparedStatement ps;
		try {
			ps = c.prepareStatement(saveSql);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getRealname());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL异常");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
