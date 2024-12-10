package org.example.li;


import org.example.li.DatabaseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            conn = DatabaseUtils.getConnection();
            String sql = "SELECT * FROM users WHERE phone = ? AND password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, phone);
            stmt.setString(2, password);

            rs = stmt.executeQuery();
            if (rs.next()) {
                out.write("{\"success\":true}");
            } else {
                out.write("{\"success\":false, \"message\":\"手机号或密码错误\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.write("{\"success\":false, \"message\":\"" + e.getMessage() + "\"}");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
