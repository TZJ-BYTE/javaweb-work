package com.demo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            case 4:
                checkout(request, response);
                break;
        }
    }

    public void add(HttpServletRequest request, HttpServletResponse response) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        try {
            Integer exitNum = 0;

            List<Shoppingcart> lsc = (List<Shoppingcart>) request.getSession().getAttribute("Shoppingcart");
            if (lsc == null) {
                lsc = new ArrayList<>();
            }
            for (int i = 0; i < lsc.size(); i++) {
                if (lsc.get(i).getId() == id) {
                    exitNum = lsc.get(i).getNumber();
                    lsc.remove(i);
                    break;
                }
            }
            Shoppingcart sc = new Shoppingcart();
            Goods g = gd.findById(id);
            sc.setGoodsName(g.getGoodsname());
            sc.setNumber(exitNum + 1);
            sc.setPrice(g.getPrice());
            sc.setId(g.getId());
            lsc.add(sc);
            request.getSession().setAttribute("Shoppingcart", lsc);

            // 设置响应头为 application/json
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"message\": \"商品添加成功\"}");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"message\": \"商品添加失败\"}");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void changeNum(HttpServletRequest request, HttpServletResponse response) {
        List<Shoppingcart> lsc = (List<Shoppingcart>) request.getSession().getAttribute("Shoppingcart");
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

    public void clearCart(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getSession().setAttribute("Shoppingcart", new ArrayList<Shoppingcart>());
            RequestDispatcher rd = request.getRequestDispatcher("cleared.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeCart(HttpServletRequest request, HttpServletResponse response) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        List<Shoppingcart> lsc = (List<Shoppingcart>) request.getSession().getAttribute("Shoppingcart");
        for (int i = 0; i < lsc.size(); i++) {
            if (lsc.get(i).getId() == id) {
                lsc.remove(i);
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

    public void checkout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Userinfo u = (Userinfo) session.getAttribute("userinfo");
        List<Shoppingcart> lsc = (List<Shoppingcart>) session.getAttribute("Shoppingcart");

        if (u == null || lsc == null || lsc.isEmpty()) {
            response.sendRedirect("selectSC.jsp");
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // 获取数据库连接
            conn = DBConnector.getConnection();

            // 准备SQL语句
            String sql = "INSERT INTO user_purchases (user_id, goods_id, goods_name, purchase_time) VALUES (?, ?, ?, NOW())";
            pstmt = conn.prepareStatement(sql);

            // 遍历购物车并插入数据
            for (Shoppingcart sc : lsc) {
                pstmt.setInt(1, u.getId());
                pstmt.setInt(2, sc.getId()); // 假设Shoppingcart类中有getId方法
                pstmt.setString(3, sc.getGoodsName());
                pstmt.executeUpdate();
            }

            // 清空购物车
            session.setAttribute("Shoppingcart", new ArrayList<Shoppingcart>());

            // 重定向到成功页面或其他页面
            response.sendRedirect("checkoutSuccess.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("checkoutError.jsp");
        } finally {
            // 关闭资源
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
