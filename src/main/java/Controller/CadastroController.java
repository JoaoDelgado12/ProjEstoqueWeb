
package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Master
 */
@WebServlet("/Cadastro")
public class CadastroController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        CadastroUsuarioModel user = new CadastroUsuarioModel();        
        
        user.setNome(request.getParameter("nameFirst"));
        user.setSobrenome(request.getParameter("sobrenome"));
        user.setMatricula(request.getParameter("Matricula"));
        user.setCpf(request.getParameter("cpf"));
        user.setSexo(request.getParameter("sexo"));
        user.setDtaNascimento(request.getParameter("dtaNascimento"));
        user.setEmail(request.getParameter("email"));
        user.setTelefone(request.getParameter("telefone"));
        user.setUser(request.getParameter("user"));
        user.setpassword(request.getParameter("password"));
        user.setFuncao(request.getParameter("funcao"));
        
        CadastroUsersDAO dao = new CadastroUsersDAO();
        
        if(dao.cadastrar(user)){
            response.sendRedirect("pages/dashboard.html");
        }else{
            response.sendRedirect("pages/cadastro.html");
        }
    }
}
