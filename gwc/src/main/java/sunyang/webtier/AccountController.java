package sunyang.webtier;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sunyang.dao.AccountDAO;
import sunyang.dao.AccountgoodsDAO;
import sunyang.domain.Account;
import sunyang.domain.Accountgoods;
import sunyang.domain.Shoppingcart;
import sunyang.domain.Userinfo;
import sunyang.util.Chinese;
import sunyang.util.CodeCreator;

public class AccountController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private AccountDAO ad;
	private AccountgoodsDAO agd;

	public AccountController() {
		ad = new AccountDAO();
		agd = new AccountgoodsDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		switch (Integer.parseInt(request.getParameter("flag"))) {
			case 0:
				getAccount(request, response);
				break;
			case 1:
				saveAccount(request, response);
				break;
		}
	}

	public void getAccount(HttpServletRequest request,
						   HttpServletResponse response) throws ServletException, IOException {
		Userinfo u = (Userinfo) request.getSession().getAttribute("userinfo");
		request.setAttribute("accountCode", new CodeCreator()
				.createAccountcode(u.getId()));
		RequestDispatcher rd = request.getRequestDispatcher("account.jsp");
		rd.forward(request, response);
	}

	public void saveAccount(HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
		Userinfo u = (Userinfo) request.getSession().getAttribute("userinfo");
		String accountcode = (String) request.getParameter("accountcode");
		String address = (String) request.getParameter("address");
		String postcode = (String) request.getParameter("postcode");
		Account a = new Account();
		a.setUserid(u.getId());
		a.setAccountcode(accountcode);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String d = sdf.format(new Date()).toString();
		a.setAccountdate(d.toString());
		Chinese c = new Chinese();
		a.setAddress(c.toChinese(address));
		a.setExecute(0);
		a.setPostcode(postcode);
		if (ad.save(a)) {
			List<Shoppingcart> lsc = (List<Shoppingcart>) request.getSession()
					.getAttribute("Shoppingcart");
			for (Shoppingcart sc : lsc) {
				Accountgoods ag = new Accountgoods();
				ag.setGoodsId(sc.getId());
				ag.setNum(sc.getNumber());
				ag.setAccountcode(a.getAccountcode());
				if (!agd.save(ag)) {
					request.setAttribute("errors", "对商品：“" + sc.getGoodsName()
							+ "”结账出错!");
					RequestDispatcher rd = request
							.getRequestDispatcher("errors.jsp");
					rd.forward(request, response);
				}
			}
			request.getSession().setAttribute("Shoppingcart",
					new ArrayList<Shoppingcart>());
			request.getSession().removeAttribute("userinfo");
			RequestDispatcher rd = request
					.getRequestDispatcher("accountOver.jsp");
			rd.forward(request, response);
		} else {
			request.setAttribute("errors", "结账出错!");
			RequestDispatcher rd = request.getRequestDispatcher("errors.jsp");
			rd.forward(request, response);
		}
	}
}