package login;

import connection.ConnectionFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet; //para trabalhar com os protocolos http
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *
 * @author Master
 */
@WebServlet("/login")
public class Login extends HttpServlet{
    
    private static final long serialVersionUID = 1L; //PARA QUE ISSO MESMO ?
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String usuario = request.getParameter("user");
        String senha = request.getParameter("passw");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try (var con = ConnectionFactory.getConnection()){
            String sql = "SELECT * FROM users WHERE username= ? AND psw= ?";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, senha);
            
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                response.sendRedirect("pages/menu.html");
            }else {
                out.println("<h2> deu merda </h2>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h2>Erro ao conectar ao banco de dados.</h2>");
        }
  
    }
}