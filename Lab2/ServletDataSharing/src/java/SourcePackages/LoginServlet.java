
package SourcePackages;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse
response) throws ServletException, IOException {
        
    
    String user = request.getParameter("username");
    String pass = request.getParameter("password");
    
    // Semakan kata laluan (Authentication)
    if (user.equals("Ahmad") && pass.equals("4567")) {
        
    
    // Data Sharing: Menyimpan maklumat tambahan ke dalam 'request'
    request.setAttribute("accountType", "Premium Student");
    request.setAttribute("email", "ahmad@siswa.edu.my");
    
    // Request Forwarding ke AccountServlet
    RequestDispatcher rd = request.getRequestDispatcher
    ("AccountServlet");
    rd.forward(request, response);
    
  } else if (user.equals("Siti") && pass.equals("1234")) {
     // Contoh untuk pengguna lain
     request.setAttribute("accountType", "Standard Student");
     request.setAttribute("email", "siti@siswa.edu.my");
     RequestDispatcher rd = request.getRequestDispatcher
     ("AccountServlet");
     rd.forward(request, response);

} else {
    // Jika login gagal
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println("<h3 style='color:red'>Login Failed! Invalid credentials.</h3>");
    out.println("<a href='login.html'>Try Again</a>");
 }
}

    

 
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
