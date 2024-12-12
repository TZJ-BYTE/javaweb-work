package controller;

import model.Goods;
import model.Userinfo;
import service.DBConnector;
import service.GoodsDAO;
import service.PurchasesDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class GoodsController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private GoodsDAO gd;
	private PurchasesDAO pd;
	private Connection c;

	public GoodsController() {
		gd = new GoodsDAO();
		pd = new PurchasesDAO();
		c = DBConnector.getConnection();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		findAll(request, response);
	}

	public void findAll(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Userinfo u = (Userinfo) session.getAttribute("userinfo");

		try {
			int userId =u.getId();
			int totalCount = 20; // 总共需要随机选取的商品数量

			// 获取最常购买的商品列表
			List<Goods> mostPurchasedGoods = pd.getMostPurchasedByUser(userId);
			// 创建一个列表来存储最终选择的商品
			List<Goods> selectedGoods = new ArrayList<>();

			// 检查是否有最常购买的商品
			if (!mostPurchasedGoods.isEmpty()) {
				// 计算每种商品应该选择的数量
				int firstPlaceCount = (int) Math.ceil(totalCount * 0.4); // 20% for the first place
				int secondPlaceCount = (int) Math.ceil(totalCount * 0.3); // 30% for the second place
				int thirdPlaceCount = (int) Math.ceil(totalCount * 0.2); // 40% for the third place

				// 获取每种商品的 taste, diet_habit, health_requirement
				Goods firstPlace = mostPurchasedGoods.get(0);
				Goods secondPlace = mostPurchasedGoods.size() > 1 ? mostPurchasedGoods.get(1) : null;
				Goods thirdPlace = mostPurchasedGoods.size() > 2 ? mostPurchasedGoods.get(2) : null;

				// 添加第一、第二、第三位的商品
				addRandomGoodsByCriteria(selectedGoods, firstPlace, firstPlaceCount);
				addRandomGoodsByCriteria(selectedGoods, secondPlace, secondPlaceCount);
				addRandomGoodsByCriteria(selectedGoods, thirdPlace, thirdPlaceCount);
			}

			// 剩余的商品随机选择
			int remainingCount = totalCount - selectedGoods.size();
			if (remainingCount > 0) {
				List<Goods> randomGoods = gd.findRandom(remainingCount);
				selectedGoods.addAll(randomGoods);
			}

			// 打乱最终选择的商品顺序
			Collections.shuffle(selectedGoods);

			// 将随机商品列表设置到请求属性中
			request.setAttribute("findAllGoods", selectedGoods);

			// 转发到 homepage.jsp
			RequestDispatcher rd = request.getRequestDispatcher("homepage.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addRandomGoodsByCriteria(List<Goods> selectedGoods, Goods criteria, int count) throws Exception {
		if (criteria == null) {
			return;
		}
		String sql = "SELECT * FROM goods WHERE taste = ? AND diet_habit = ? AND health_requirement = ? ORDER BY RAND() LIMIT ?";
		try (PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, criteria.getTaste());
			ps.setString(2, criteria.getDietHabit());
			ps.setString(3, criteria.getHealthRequirement());
			ps.setInt(4, count);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Goods g = new Goods();
					g.setId(rs.getInt("id"));
					g.setGoodsname(rs.getString("goodsname"));
					g.setPrice(rs.getFloat("price"));
					g.setTaste(rs.getString("taste"));
					g.setDietHabit(rs.getString("diet_habit"));
					g.setHealthRequirement(rs.getString("health_requirement"));
					selectedGoods.add(g);
				}
			}
		}
	}

}
