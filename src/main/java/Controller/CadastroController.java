
package Controller;

import Dao.CadastrosUserDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CadastroUsuarioModel;

import java.io.IOException;

@WebServlet("/api/cadastro/usuario")
public class CadastroController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        CadastroUsuarioModel cadastroUser = new CadastroUsuarioModel(
        request.getParameter("nameFirst"),
        request.getParameter("sobrenome"),
        request.getParameter("matricula"),
        request.getParameter("dtaNascimento"),
        request.getParameter("sexo"),
        request.getParameter("cpf"),
        request.getParameter("cep"),
        request.getParameter("logradouro"),
        request.getParameter("estado"),
        request.getParameter("bairro"),
        request.getParameter("cidade"),
        request.getParameter("numero"),
        request.getParameter("complemento"),
        request.getParameter("usuario"),
        request.getParameter("senha"),
        request.getParameter("funcao"),
        request.getParameter("email"),
        request.getParameter("telefone"),
        request.getParameter("grupoAcesso")
        );
        
      
        CadastrosUserDAO cadastroDao = new CadastrosUserDAO();
        
        if(cadastroDao.cadastrar(cadastroUser)){
            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect(request.getContextPath() + "/pages/menu.html");
        }else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.sendRedirect(request.getContextPath() + "/pages/menu.html");
        }
    }
}
