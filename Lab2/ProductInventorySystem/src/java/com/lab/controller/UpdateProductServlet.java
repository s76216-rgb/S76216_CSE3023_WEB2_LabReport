package com.lab.controller;

import com.lab.dao.ProductDAO;
import com.lab.model.Product;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateProductServlet")
public class UpdateProductServlet extends HttpServlet {
    private ProductDAO productDAO;

    public void init() {
        productDAO = new ProductDAO();
    }

    // Step A: Show the Edit Form
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        Product existingProduct = productDAO.selectProduct(id);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (existingProduct != null) {
            out.println("<h2>Edit Product</h2>");
            out.println("<form action='UpdateProductServlet' method='POST'>");
            out.println("<input type='hidden' name='id' value='" + existingProduct.getId() + "'>");
            out.println("Name: <input type='text' name='name' value='" + existingProduct.getName() + "'><br>");
            out.println("Category: <input type='text' name='category' value='" + existingProduct.getCategory() + "'><br>");
            out.println("Price: <input type='text' name='price' value='" + existingProduct.getPrice() + "'><br>");
            out.println("Quantity: <input type='text' name='quantity' value='" + existingProduct.getQuantity() + "'><br>");
            out.println("<input type='submit' value='Update Product'>");
            out.println("</form>");
        }
    }

    // Step B: Save the changes
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        Product product = new Product(id, name, category, price, quantity);
        productDAO.updateProduct(product);

        response.sendRedirect("ViewProductServlet");
    }
}