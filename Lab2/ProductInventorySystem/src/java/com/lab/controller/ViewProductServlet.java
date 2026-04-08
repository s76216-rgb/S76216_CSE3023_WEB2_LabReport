package com.lab.controller;

import com.lab.dao.ProductDAO;
import com.lab.model.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewProductServlet")
public class ViewProductServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        List<Product> listProduct = productDAO.selectAllProducts();

        out.println("<html><head><title>Product Inventory</title></head><body>");
        out.println("<h2>Product Inventory List</h2>");
        out.println("<table border='1' cellpadding='5'>");
        out.println("<tr><th>ID</th><th>Name</th><th>Category</th>"
                + "<th>Price</th><th>Quantity</th><th>Actions</th></tr>");

        for (Product product : listProduct) {
            out.println("<tr>");
            out.println("<td>" + product.getId() + "</td>");
            out.println("<td>" + product.getName() + "</td>");
            out.println("<td>" + product.getCategory() + "</td>");
            out.println("<td>" + String.format("%.2f", product.getPrice()) + "</td>");
            out.println("<td>" + product.getQuantity() + "</td>");
            out.println("<td>");
            out.println("<a href='UpdateProductServlet?id=" + product.getId() + "'>Edit</a> | ");
            out.println("<a href='DeleteProductServlet?id=" + product.getId() + "'>Delete</a>");
            out.println("</td>");
            out.println("</tr>");
        }
        out.println("</table>");
        out.println("<br><a href='add_product.html'>Add New Product</a>");
        out.println("</body></html>");
    }
}