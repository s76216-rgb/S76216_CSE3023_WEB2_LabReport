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
    }
}
