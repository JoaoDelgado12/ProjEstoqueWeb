package Controller;

import java.io.IOException;
import java.sql.SQLException;

import Dao.ConsultarProdutoDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/consulta/produto")
public class ConsultarProdutosController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ConsultarProdutoDAO buscaProduto = new ConsultarProdutoDAO();
		
		
		try{
			buscaProduto.consultar();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			response.getWriter().write(buscaProduto.getResultado());
			response.setStatus(HttpServletResponse.SC_OK);
		}catch(SQLException eSql) {
			System.err.println("consultaProdutos: PROBLEMA no SQL");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}catch(Exception e) {
			System.err.println("consultaProdutos: PROBLEMA genérico");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		
	}
	
}

