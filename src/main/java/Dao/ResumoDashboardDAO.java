package Dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import connection.ConnectionFactory;

public class ResumoDashboardDAO {
    
    private String resultado;
    
    public boolean calcular() {
        String sqlEntrada = "SELECT SUM(precoTotal) AS SomaPreco FROM Venda"; 
        String sqlSaida = "SELECT SUM(precoCompraTotal) AS SomaPreco FROM HistEstoque"; 
        
        try (var con = ConnectionFactory.getConnection()) {
            
            BigDecimal entrada = BigDecimal.ZERO;
            try (PreparedStatement stmt = con.prepareStatement(sqlEntrada);
                 ResultSet result = stmt.executeQuery()) {
                 
                while (result.next()) {
                    BigDecimal valor = result.getBigDecimal("SomaPreco");
                    if (valor != null) {
                        entrada = valor;
                    }
                }
            }
            
            BigDecimal saida = BigDecimal.ZERO;
            try (PreparedStatement stmt = con.prepareStatement(sqlSaida);
                 ResultSet result = stmt.executeQuery()) {
                
                while (result.next()) {
                    BigDecimal valor = result.getBigDecimal("SomaPreco");
                    if (valor != null){
                        saida = valor;
                    }
                }
            }
            

            BigDecimal total = entrada.subtract(saida); //para manter a precisão
            
            
            Map<String, BigDecimal> tabela = new HashMap<>();
            tabela.put("entrada", entrada);
            tabela.put("saida", saida);
            tabela.put("total", total);
            
            Gson gson= new Gson();
            
            setResultado(gson.toJson(tabela));
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
}