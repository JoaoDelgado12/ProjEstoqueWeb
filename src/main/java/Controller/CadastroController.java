
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
@WebServlet("/cadastro")
public class CadastroController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        CadastroUsuarioModel cadastroUser = new CadastroUsuarioModel(
        request.getParameter("nameFirst"),
        request.getParameter("sobrenome"),
        request.getParameter("matricula"),
        request.getParameter("dtaNascimento"),
        request.getParameter("sexo"),
        request.getParameter("cpf"),
        request.getParameter("cep"),
        request.getParameter("endereco"),
        request.getParameter("estado"),
        request.getParameter("bairro"),
        request.getParameter("cidade"),
        request.getParameter("numero"),
        request.getParameter("complemento"),
        request.getParameter("usuario"),
        request.getParameter("senha"),
        request.getParameter("funcao"),
        request.getParameter("email"),
        request.getParameter("telefone")
        );
        
      
        CadastrosUserDAO cadastroDao = new CadastrosUserDAO();
        
        if(cadastroDao.cadastrar(cadastroUser)){
            response.sendRedirect(request.getContextPath() + "/pages/menu.html");
        }else{
            response.sendRedirect(request.getContextPath() + "/pages/cadastro.html");
        }
    }
}
