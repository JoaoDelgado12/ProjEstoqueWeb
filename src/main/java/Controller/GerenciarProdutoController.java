package Controller;

import Dao.GerenciarProdutoDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/gerenciar/produto")
public class GerenciarProdutoController extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			
			GerenciarProdutoDAO gerenciarProdutoDAO = new GerenciarProdutoDAO();
			
			int fluxoResponse = gerenciarProdutoDAO.exibir(request.getParameter("nomeProduto"));
			
			if(fluxoResponse == 0){
				response.getWriter().write(gerenciarProdutoDAO.getProduto());
				response.setStatus(HttpServletResponse.SC_OK);
				response.sendRedirect(request.getContextPath() + "pages/gerenciarProduto.html");
			}else if(fluxoResponse == 1) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}		
		}catch(Exception sql){
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw new Error("ERROR: erro generico do produto");
		}
		
	}
}
