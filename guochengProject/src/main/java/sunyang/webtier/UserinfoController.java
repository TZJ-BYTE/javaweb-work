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
/**
 * UserinfoController类用于处理用户信息相关的HTTP请求，包括用户注册、登录和登出功能
 * 继承自HttpServlet类
 */
public class UserinfoController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserinfoDAO ud; // 用户信息数据访问对象

    /**
     * 构造方法，初始化UserinfoDAO实例
     */
    public UserinfoController() {
        ud = new UserinfoDAO();
    }

    /**
     * 处理GET请求方法，将请求转发给doPost方法处理
     *
     * @param request  HttpServletRequest对象，包含请求数据
     * @param response HttpServletResponse对象，用于发送响应
     * @throws ServletException 如果Servlet遇到异常
     * @throws IOException      如果发生I/O异常
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * 处理POST请求方法，根据请求参数中的flag值决定执行的操作
     *
     * @param request  HttpServletRequest对象，包含请求数据
     * @param response HttpServletResponse对象，用于发送响应
     * @throws ServletException 如果Servlet遇到异常
     * @throws IOException      如果发生I/O异常
     */
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

    /**
     * 用户注册方法，处理用户注册请求
     *
     * @param request  HttpServletRequest对象，包含请求数据
     * @param response HttpServletResponse对象，用于发送响应
     */
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

    /**
     * 用户登录方法，处理用户登录请求
     *
     * @param request  HttpServletRequest对象，包含请求数据
     * @param response HttpServletResponse对象，用于发送响应
     */
    public void login(HttpServletRequest request, HttpServletResponse response) {

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

    /**
     * 用户登出方法，处理用户登出请求
     *
     * @param request  HttpServletRequest对象，包含请求数据
     * @param response HttpServletResponse对象，用于发送响应
     * @throws ServletException 如果Servlet遇到异常
     * @throws IOException      如果发生I/O异常
     */
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("userinfo");
        request.getSession().removeAttribute("Shoppingcart");
        RequestDispatcher rd = request.getRequestDispatcher("logout.jsp");
        rd.forward(request, response);
    }
}
