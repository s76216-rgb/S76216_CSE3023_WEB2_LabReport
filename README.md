# S76216_CSE3023_WEB2_LabReport
Lab Reports for web-based application development


package com.labtest.DAO;
import java.sql.DriverManager;
import java.sql.Connection;
import com.labtest.Model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductDAO {
    
    private static String url = "jdbc:mysql://localhost:3306/productdb";
    private static String username="root";
    private static String password = "admin";
    
    public static Connection getConnection(){
        Connection conn = null;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }
    
    public void insertProduct(Product product){
        String sql = "INSERT INTO product (name, price, quantity) VALUES (?,?,?)";
        
        try(
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
                ){       
        pstmt.setString(1, product.getName());
        pstmt.setDouble(2,product.getPrice());
        pstmt.setInt(3,product.getQuantity());
        
        pstmt.executeUpdate();
    } catch (Exception e){
            e.printStackTrace();
         
}
    }
   
    public List<Product> getProduct(){
       
        List<Product> products = new ArrayList<>();
        
        String sql = "SELECT * FROM product";
        
        try(
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
                ){
        while (rs.next()){
            Product product = new Product(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getDouble("price"),
            rs.getInt("quantity")
            );
        products.add(product);
        }
    } catch (Exception e){
        e.printStackTrace();
    }
        return products;
    }
    
    
    public void updateProduct(Product product){
        String sql = "UPDATE product SET name=?, price=?, quantity=? WHERE id=?";
        
        try(
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ){
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setInt(4, product.getID());
            
            pstmt.executeUpdate();
            
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void deleteProduct(int id){
        String sql = "DELETE FROM product WHERE id=?";
        
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ){
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }





        package com.labtest.Controller;

import com.labtest.DAO.ProductDAO;
import com.labtest.Model.Product;
import java.io.IOException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/ProductsServlet")
public class ProductsServlet extends HttpServlet {

    ProductDAO dao = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<Product> products = dao.getProduct();

        request.setAttribute("listProduct", products);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("productList.jsp");

        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        Product product =
                new Product(name, price, quantity);

        dao.insertProduct(product);

        response.sendRedirect("ProductServlet");

    }

}







<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product List</title>
    </head>
    <body>
        <table border="1">
            <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
            </tr>
            
            <c:forEach var="product" items="${listProduct}">
                <tr>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>${product.price}</td>
                    <td>${product.quantity}</td>
                    
                </tr>
            </c:forEach>
        </table>
    </body>
</html>






<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert Product</title>
    </head>
    <body>
        <form action="ProductServlet" method="post">
            Name
            <input type="text" name="name"><br><br>
            
           Price
           <input type="text" name="price"><br><br>
           
           Quantity
           <input type="text" name="quantity"><br><br>
           
           <input type="submit" value="Save">
        </form>
    </body>
</html>
    }
}






package com.labtest.Model;

public class Product {
    
    private int id;
    private String name;
    private double price;
    private int quantity;
    
    public Product(){
    }
    
    public Product(String name, double price, int quantity){
        this.name=name;
        this.price=price;
        this.quantity=quantity;
    }
    
    public Product(int id, String name, double price, int quantity){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity= quantity;
    }
    
    public int getID(){
        return id;
    }
    
    public void setID(int id){
        this.id=id;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(){
        this.name = name;
    }
    
    public double getPrice(){
        return price;
    }
    
    public void setPrice(){
        this.price=price;
    }
    
    public int getQuantity(){
        return quantity;
    }
    
    public void setQuantity(int quantity){
        this.quantity= quantity;
    }
    
    
}
