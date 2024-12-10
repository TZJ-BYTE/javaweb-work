package sunyang.webtier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sunyang.dao.UserinfoDAO;
import sunyang.domain.Shoppingcart;
import sunyang.domain.Userinfo;
import sunyang.util.*;

public class UserinfoController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UserinfoDAO ud;

	public UserinfoController() {
		ud = new UserinfoDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		switch (Integer.parseInt(request.getParameter("flag"))) {
			case 0:
				regist(request, response);
				break;
			case 1:
				login(request, response);
				break;
			case 2:
				logout(request,response);
				break;
		}
	}

	public void regist(HttpServletRequest request, HttpServletResponse response) {
		try {
			Chinese toc = new Chinese();
			String name = request.getParameter("username");
			String psw = request.getParameter("password");
			Integer sex = Integer.parseInt(request.getParameter("sex"));
			String realname = toc.toChinese(request.getParameter("realname"));
			String tel = request.getParameter("tel");
			String email = request.getParameter("email");
			Userinfo u = new Userinfo();
			u.setUsername(name);
			u.setPassword(psw);
			u.setSex(sex);
			u.setRealname(realname);
			u.setTel(tel);
			u.setEmail(email);
			List<Userinfo> lu = ud.findByUsername(name);
			if (lu.size() == 0) {
				if (ud.save(u)) {
					RequestDispatcher rd = request
							.getRequestDispatcher("registSuccess.jsp");
					rd.forward(request, response);
				} else {
					request.setAttribute("errors", "用户注册失败！");
					RequestDispatcher rd = request
							.getRequestDispatcher("errors.jsp");
					rd.forward(request, response);
				}
			} else {
				request.setAttribute("errors", "用户名已经存在！");
				RequestDispatcher rd = request
						.getRequestDispatcher("errors.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void login(HttpServletRequest request, HttpServletResponse response) {


			String rand = (String) request.getSession().getAttribute("rand");
			// ...
			System.out.println("验证码是: " + rand);
			// ...


		String name = request.getParameter("username");
		String psw = request.getParameter("password");
		try {

			List<Userinfo> lu = ud.findByUsername(name);
			if (!request.getParameter("safecode").equals(
					request.getSession().getAttribute("rand"))) {
				request.setAttribute("errors", "验证码错误！");
				RequestDispatcher rd = request
						.getRequestDispatcher("errors.jsp");
				rd.forward(request, response);
			} else if (lu.size() > 0 && lu.get(0).getPassword().equals(psw)) {
				request.getSession().setAttribute("userinfo", lu.get(0));
				List<Shoppingcart> lsc = new ArrayList<Shoppingcart>();
				request.getSession().setAttribute("Shoppingcart", lsc);
				RequestDispatcher rd = request
						.getRequestDispatcher("goods.do?flag=0");
				rd.forward(request, response);
			} else {
				request.setAttribute("errors", "用户名或密码错误!");
				RequestDispatcher rd = request
						.getRequestDispatcher("errors.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("userinfo");
		request.getSession().removeAttribute("Shoppingcart");
		RequestDispatcher rd = request.getRequestDispatcher("logout.jsp");
		rd.forward(request, response);
	}
}