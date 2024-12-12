package service;

import model.Purchases;
import model.Goods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchasesDAO {
    private Connection c;

    public PurchasesDAO() {
        c = DBConnector.getConnection(); // 假设 DBConnector 是一个用于获取数据库连接的类
    }

    // 插入购买记录
    public void addPurchase(Purchases purchase) throws SQLException {
        String sql = "INSERT INTO user_purchases (user_id, goods_id, goods_name, purchase_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, purchase.getUserId());
            ps.setInt(2, purchase.getGoodsId());
            ps.setString(3, purchase.getGoodsName());
            ps.setTimestamp(4, new java.sql.Timestamp(purchase.getPurchaseTime().getTime()));
            ps.executeUpdate();
        }
    }

    // 根据用户 ID 获取购买记录
    public List<Purchases> getPurchasesByUserId(int userId) throws SQLException {
        List<Purchases> purchasesList = new ArrayList<>();
        String sql = "SELECT * FROM user_purchases WHERE user_id = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Purchases purchase = new Purchases();
                    purchase.setId(rs.getInt("id"));
                    purchase.setUserId(rs.getInt("user_id"));
                    purchase.setGoodsId(rs.getInt("goods_id"));
                    purchase.setGoodsName(rs.getString("goods_name"));
                    purchase.setPurchaseTime(rs.getTimestamp("purchase_time"));
                    purchasesList.add(purchase);
                }
            }
        }
        return purchasesList;
    }

    // 获取最常购买的商品
    public List<Goods> getMostPurchasedByUser(int userId) throws SQLException {
        List<Goods> mostPurchasedGoodsList = new ArrayList<>();
        String sql = "SELECT goods.*, COUNT(user_purchases.goods_id) as purchase_count " +
                "FROM user_purchases " +
                "JOIN goods ON user_purchases.goods_id = goods.id " +
                "WHERE user_purchases.user_id = ? " + // 使用问号作为参数占位符
                "GROUP BY goods.taste, goods.diet_habit, goods.health_requirement " +
                "ORDER BY purchase_count DESC " +
                "LIMIT 3";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Goods mostPurchasedGoods = new Goods();
                    mostPurchasedGoods.setId(rs.getInt("id"));
                    mostPurchasedGoods.setGoodsname(rs.getString("goodsname"));
                    mostPurchasedGoods.setPrice(rs.getFloat("price"));
                    mostPurchasedGoods.setTaste(rs.getString("taste"));
                    mostPurchasedGoods.setDietHabit(rs.getString("diet_habit"));
                    mostPurchasedGoods.setHealthRequirement(rs.getString("health_requirement"));
                    mostPurchasedGoodsList.add(mostPurchasedGoods);
                }
            }
        }
        return mostPurchasedGoodsList;
    }


}
