package com.lab.controller;

import com.lab.dao.UserDAO;
import com.lab.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateServlet", urlPatterns = {"/UpdateServlet"})
public class UpdateServlet extends HttpServlet {
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    // Paparkan borang dengan data sedia ada
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        int id = Integer.parseInt(request.getParameter("id"));

        User existingUser = userDAO.selectUser(id); // Panggil DAO

        if (existingUser != null) {
            out.println("<h2>Update User (Using DAO)</h2>");
            out.println("<form action='UpdateServlet' method='POST'>");
            out.println("<input type='hidden' name='id' value='" + existingUser.getId() + "'>");
            out.println("Username: <input type='text' name='username' value='" 
                    + existingUser.getUsername() + "' required><br><br>");
            out.println("Password: <input type='text' name='password' value='" 
                    + existingUser.getPassword() + "' required><br><br>");

            String currentRole = existingUser.getRoles();
            out.println("Role: <select name='roles'>");
            out.println("<option value='Admin' " + (currentRole.equals("Admin") ? "selected" : "") + ">Admin</option>");
            out.println("<option value='Staff' " + (currentRole.equals("Staff") ? "selected" : "") + ">Staff</option>");
            out.println("<option value='Student' " + (currentRole.equals("Student") ? "selected" : "") + ">Student</option>");
            out.println("</select><br><br>");

            out.println("<input type='submit' value='Update User'>");
            out.println("</form>");
        }
    }

    // Simpan data yang telah dikemaskini
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String roles = request.getParameter("roles");

        User user = new User(id, username, password, roles);
        userDAO.updateUser(user); // Panggil DAO

        response.sendRedirect("ViewServlet");
    }
}


