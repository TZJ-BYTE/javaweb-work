package sunyang.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import sunyang.domain.Goods;
import sunyang.util.DBConnector;

public class GoodsDAO {
	private Connection c;

	public GoodsDAO() {
		c = DBConnector.getConnection();
	}

	public List<Goods> findAllPage(Integer start,Integer rowPerPage) throws Exception {
		List<Goods> lg = new ArrayList<Goods>();
		String findAllSql = "select * from goods order by id limit "+start+","+rowPerPage;
		PreparedStatement ps = c.prepareStatement(findAllSql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Goods g = new Goods();
			g.setId(rs.getInt("id"));
			g.setGoodsname(rs.getString("goodsname"));
			g.setPrice(rs.getFloat("price"));
			g.setFactory(rs.getString("factory"));
			g.setOutdate(rs.getDate("outdate"));
			g.setPic(rs.getString("pic"));
			g.setIntroduction(rs.getString("introduction"));
			lg.add(g);
		}
		return lg;
	}

	public Goods findById(Integer id) throws Exception {
		Goods g = new Goods();
		String findByIdSql = "select * from goods where id=?";
		PreparedStatement ps = c.prepareStatement(findByIdSql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			g.setId(rs.getInt("id"));
			g.setGoodsname(rs.getString("goodsname"));
			g.setPrice(rs.getFloat("price"));
			g.setFactory(rs.getString("factory"));
			g.setOutdate(rs.getDate("outdate"));
			g.setPic(rs.getString("pic"));
			g.setIntroduction(rs.getString("introduction"));
		}
		return g;
	}
}
