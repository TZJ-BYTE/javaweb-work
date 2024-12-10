package com.demo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoodsController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private GoodsDAO gd;

	public GoodsController() {
		gd = new GoodsDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		switch (Integer.parseInt(request.getParameter("flag"))) {
		case 0:
			findAll(request, response);
			break;
		case 1:
			findSingle(request, response);
			break;
		case 2:
			findByType(request, response);
			break;
		}
	}

	public void findAll(HttpServletRequest request, HttpServletResponse response) {
		try {
			Integer curPage = 0;
			Integer rowPerPage = 10;
			if (request.getParameter("start") == null) {
				curPage = 1;
			} else {
				curPage = Integer.parseInt(request.getParameter("start"));
			}
			PageList pl = new PageList("goods", curPage, rowPerPage);

			request.setAttribute("page", pl);
			request.setAttribute("findAllGoods", gd.findAllPage(pl.getCurrentCount(), pl.getRowsPerPage()));
			RequestDispatcher rd = request.getRequestDispatcher("homepage.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void findSingle(HttpServletRequest request,
			HttpServletResponse response) {
		Integer id = Integer.parseInt(request.getParameter("id"));
		try {
			request.setAttribute("good", gd.findById(id));
			RequestDispatcher rd = request
					.getRequestDispatcher("singleGoods.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void findByType(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		try {
			List<Goods> goodsList = gd.findByType(type);
			request.setAttribute("goodsList", goodsList);
			RequestDispatcher rd = request.getRequestDispatcher("goodsByType.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
