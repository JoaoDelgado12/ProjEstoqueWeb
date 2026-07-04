package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import connection.ConnectionFactory;

public class ConsultarProdutoDAO {
	private String resultado;
	
	public void consultar() throws SQLException{
		String sql = "SELECT nome, quantidade, quantidadeMin, precoVendaUni FROM Produto";
		
		try(var con = ConnectionFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);
				ResultSet result = stmt.executeQuery()){
			
			List<Map<String, String>> listResult = new ArrayList<>();
			
			ResultSetMetaData metaResult = result.getMetaData();
			
			while(result.next()){
				Map<String, String> mapDados = new HashMap<String, String>();
				
				for(int i=1; i <= metaResult.getColumnCount(); i++) {
					String nomeColuna = metaResult.getColumnName(i);
					String valorColuna = result.getString(i);
					
					mapDados.put(nomeColuna, valorColuna);
				}
				
				listResult.add(mapDados);
			}
			
			Gson gson = new Gson();
			
			setResultado(gson.toJson(listResult));
		}catch(Exception e) {
			e.printStackTrace();
			throw new Error("consultaPRODUTO: PROBLEMA genérico");
		}
		
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	
}
