package com.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GoodsDAO {
	private Connection c;

	public GoodsDAO() {
		c = DBConnector.getConnection();
	}

	public List<Goods> findRandom(int count) throws Exception {
		List<Goods> lg = new ArrayList<>();
		String findRandomSql = "SELECT * FROM goods ORDER BY RAND() LIMIT ?";
		PreparedStatement ps = c.prepareStatement(findRandomSql);
		ps.setInt(1, count);
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
			g.setTaste(rs.getString("taste")); // 新增
			g.setDietHabit(rs.getString("diet_habit")); // 新增
			g.setHealthRequirement(rs.getString("health_requirement")); // 新增
			lg.add(g);
		}
		return lg;
	}

	public List<Goods> findAllPage(Integer start, Integer rowPerPage) throws Exception {
		List<Goods> lg = new ArrayList<Goods>();
		String findAllSql = "SELECT * FROM goods ORDER BY id LIMIT ?, ?";
		PreparedStatement ps = c.prepareStatement(findAllSql);
		ps.setInt(1, start);
		ps.setInt(2, rowPerPage);
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
			g.setTaste(rs.getString("taste")); // 新增
			g.setDietHabit(rs.getString("diet_habit")); // 新增
			g.setHealthRequirement(rs.getString("health_requirement")); // 新增
			lg.add(g);
		}
		return lg;
	}


	public Goods findById(Integer id) throws Exception {
		Goods g = new Goods();
		String findByIdSql = "SELECT * FROM goods WHERE id=?";
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
			g.setTaste(rs.getString("taste")); // 新增
			g.setDietHabit(rs.getString("diet_habit")); // 新增
			g.setHealthRequirement(rs.getString("health_requirement")); // 新增
		}
		return g;
	}


	public List<Goods> findByType(String type) throws Exception {
		List<Goods> lg = new ArrayList<Goods>();
		String findByTypeSql = "SELECT * FROM goods WHERE type=?";
		PreparedStatement ps = c.prepareStatement(findByTypeSql);
		ps.setString(1, type);
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
			g.setTaste(rs.getString("taste")); // 新增
			g.setDietHabit(rs.getString("diet_habit")); // 新增
			g.setHealthRequirement(rs.getString("health_requirement")); // 新增
			lg.add(g);
		}
		return lg;
	}

}
