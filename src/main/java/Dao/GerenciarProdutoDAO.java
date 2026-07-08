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

public class GerenciarProdutoDAO {
	private String produto;
	public int exibir(String nomeProduto) {
		int statusQuery = 1;
		String sql = "SELECT * FROM Produto WHERE nome = ?";
		
		try(var con = ConnectionFactory.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);){
			stmt.setString(1, nomeProduto);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			
			List<Map<String, String>> listDescricao = new ArrayList<>();
 			Map<String, String> descricao = new HashMap<>();
			if(rs.next()) {
				for(int i=1; i <= rsMetaData.getColumnCount(); i++) {
					descricao.put(rsMetaData.getColumnClassName(i), rs.getString(i));
				}
				listDescricao.add(descricao);
			}
			
			produto = new Gson().toJson(listDescricao);
			
			rs.close();
			return statusQuery;
		}catch(SQLException esql) {
			throw new Error("ERRO no SQL em GerenciarProduto");
		}catch(Exception e) {
			throw new Error("ERRO generico em GerenciarProduto");
		}

	}
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}
}
	
	
	
	
	

	
	
	
	
	

