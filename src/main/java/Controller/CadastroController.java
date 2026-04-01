
package Controller;

import Dao.CadastrosUserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import Model.CadastroUsuarioModel;
/**
 *
 * @author Master
 */
@WebServlet("/Cadastro")
public class CadastroController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        CadastroUsuarioModel user = new CadastroUsuarioModel(
        request.getParameter("nameFirst"),
        request.getParameter("sobrenome"),
        request.getParameter("matricula"),
        request.getParameter("cpf"),
        request.getParameter("sexo"),
        request.getParameter("dtaNascimento"),
        request.getParameter("telefone"),
        request.getParameter("email"),
        request.getParameter("funcao"),
        request.getParameter("usuario"),
        request.getParameter("senha"),
        request.getParameter("cep"),
        request.getParameter("endereco"),
        request.getParameter("numero"),
        request.getParameter("bairro"),
        request.getParameter("cidade"),
        request.getParameter("estado"),
        request.getParameter("complemento"));
        
        CadastrosUserDAO dao = new CadastrosUserDAO();
        
        if(dao.cadastrar(user)){
            response.sendRedirect("pages/dashboard.html");
        }else{
            response.sendRedirect("pages/cadastro.html");
        }
    }
}
