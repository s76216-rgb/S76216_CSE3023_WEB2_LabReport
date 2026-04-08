
package com.lab.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
/**
 *
 * @author Ikhwan
 */
@WebServlet(name = "UpdateServlet", urlPatterns = {"/UpdateServlet"})
public class UpdateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cse3023", "root", "admin");

            String sql = "SELECT * FROM users WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String currentRole = rs.getString("roles");
                out.println("<h2>Update User</h2>");
                out.println("<form action='UpdateServlet' method='POST'>");
                // Hidden field to keep track of which ID we are updating 
                out.println("<input type='hidden' name='id' value='" + rs.getInt("id") + "'>");
                out.println("Username: <input type='text' name='username' value='" + rs.getString("username") + "' required><br><br>");
                out.println("Password: <input type='text' name='password' value='" + rs.getString("password") + "' required><br><br>");
                
                out.println("Role: <select name='roles'>");
                out.println("<option value='Admin' " + (currentRole.equals("Admin") ? "selected" : "") + ">Admin</option>");
                out.println("<option value='Staff' " + (currentRole.equals("Staff") ? "selected" : "") + ">Staff</option>");
                out.println("<option value='Student' " + (currentRole.equals("Student") ? "selected" : "") + ">Student</option>");
                out.println("</select><br><br>");
                
                out.println("<input type='submit' value='Update User'>");
                out.println("</form>");
            }
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String roles = request.getParameter("roles");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cse3023", "root", "admin");

            String sql = "UPDATE users SET username=?, password=?, roles=? WHERE id=?"; 
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, roles);
            pstmt.setInt(4, Integer.parseInt(id));
            
            pstmt.executeUpdate();
            conn.close();
            response.sendRedirect("ViewServlet"); 
        } catch (Exception e) { e.printStackTrace(); }
    }
  }



