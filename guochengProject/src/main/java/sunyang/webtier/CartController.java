package sunyang.webtier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sunyang.dao.GoodsDAO;
import sunyang.domain.Goods;
import sunyang.domain.Shoppingcart;

public class CartController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private GoodsDAO gd;

	public CartController() {
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
			add(request, response);
			break;
		case 1:
			changeNum(request, response);
			break;
		case 2:
			clearCart(request, response);
			break;
		case 3:
			removeCart(request, response);
			break;
		}
	}

	public void add(HttpServletRequest request, HttpServletResponse response) {
		Integer id = Integer.parseInt(request.getParameter("id"));
		try {
			Integer exitNum = 0;

			List<Shoppingcart> lsc = (List<Shoppingcart>) request.getSession().getAttribute(
					"Shoppingcart");
			for (int i = 0; i < lsc.size(); i++) {
				if (lsc.get(i).getId() == id) {
					exitNum = lsc.get(0).getNumber();
					lsc.remove(lsc.get(0));
				}
			}
			Shoppingcart sc = new Shoppingcart();
			Goods g = gd.findById(id);
			sc.setGoodsName(g.getGoodsname());
			sc.setNumber(exitNum + 1);
			sc.setPrice(g.getPrice());
			sc.setId(g.getId());
			lsc.add(sc);
			RequestDispatcher rd = request
					.getRequestDispatcher("addSCSuccess.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void changeNum(HttpServletRequest request,
			HttpServletResponse response) {
		List<Shoppingcart> lsc = (List<Shoppingcart>) request.getSession()
				.getAttribute("Shoppingcart");
		try {
			String[] number = request.getParameterValues("num");
			List<Shoppingcart> listSc = new ArrayList<Shoppingcart>();
			for (int i = 0; i < lsc.size(); i++) {
				lsc.get(i).setNumber(Integer.parseInt(number[i]));
				listSc.add(lsc.get(i));
			}
			request.getSession().setAttribute("Shoppingcart", listSc);
			RequestDispatcher rd = request.getRequestDispatcher("selectSC.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearCart(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.getSession().setAttribute("Shoppingcart",
					new ArrayList<Shoppingcart>());
			RequestDispatcher rd = request.getRequestDispatcher("cleared.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeCart(HttpServletRequest request,
			HttpServletResponse response) {
		Integer id = Integer.parseInt(request.getParameter("id"));
		List<Shoppingcart> lsc = (List<Shoppingcart>) request.getSession()
				.getAttribute("Shoppingcart");
		for (int i = 0; i < lsc.size(); i++) {
			if (lsc.get(i).getId() == id) {
				lsc.remove(lsc.get(i));
			}
		}
		request.getSession().setAttribute("Shoppingcart", lsc);
		RequestDispatcher rd = request.getRequestDispatcher("selectSC.jsp");
		try {
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
