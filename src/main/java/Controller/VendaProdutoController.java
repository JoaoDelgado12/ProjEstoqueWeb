package Controller;

import java.io.IOException;
import java.util.List;

import model.VendaProdutoModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Dao.VendaProdutoDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/venda")
public class VendaProdutoController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		System.err.println("CHEGOU NO ENDPOINT");
	    request.setCharacterEncoding("UTF-8");
		
	    try {
	    	response.setCharacterEncoding("UTF-8");
		    response.setContentType("application/json");
	    	
		    String jsonRecebido = request.getParameter("dadosCarrinho");
		    
		    
		    List<VendaProdutoModel> itensCarrinho = new Gson().fromJson(
		    	    jsonRecebido, 
		    	    new TypeToken<List<VendaProdutoModel>>(){}.getType()
		    	);
		    
		    double precoTotal = 0;
		    double desconto = 0;
		    for(VendaProdutoModel item : itensCarrinho) {
		    	precoTotal += Double.parseDouble(item.getDesconto());
		    	desconto += Double.parseDouble(item.getDesconto());
		    }
		    System.err.println("CONSEGUIU RECEBER");
		    VendaProdutoDAO.registrarVenda(itensCarrinho, precoTotal, desconto );
		    
		    response.setStatus(HttpServletResponse.SC_OK);
		    System.err.println("MANDOU O OK");
		    
	    }catch(Exception e) {
	    	System.err.println("DEU ALGUM ERRO GENERICO");
	    	response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
	    	new Error("VendaProdutoController: problema genérico");
	    }
		
		
	}
}
