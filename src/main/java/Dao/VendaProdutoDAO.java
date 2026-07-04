package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import connection.ConnectionFactory;
import model.VendaProdutoModel;

public class VendaProdutoDAO {
	public static void registrarVenda(List<VendaProdutoModel> Carrinho, double precoTotal, double desconto) throws SQLException {
		String sqlIDProduto= "SELECT id FROM Produto WHERE nome = ?";
		String sqlVenda = "INSERT INTO Venda (precoTotal, desconto, dataVenda) VALUES (?, ?, ?)";
        String sqlItensVenda = "INSERT INTO ItensVenda (quantidade, produto, venda, "
        		+ " precoTotal, desconto, dataVenda) VALUES (?, ?, ?,?, ?, ?)";
        String sqlUpdate = "UPDATE Produto set quantidade = quantidade - ? WHERE nome = ? ";
        var con = ConnectionFactory.getConnection();
        
        System.err.println("Fez conexao com Banco");
   try (PreparedStatement stmtIDProduto = con.prepareStatement(sqlIDProduto);
	   PreparedStatement stmtVenda = con.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS);
       PreparedStatement stmtItensVenda = con.prepareStatement(sqlItensVenda);
	   PreparedStatement stmtUpdate = con.prepareStatement(sqlUpdate)) {
	   
	   System.err.println("Fez os Stmt");
	   
	   stmtVenda.setDouble(1, precoTotal);;
	   stmtVenda.setDouble(2, desconto);
       
       LocalDate dataAtual = LocalDate.now();
       stmtVenda.setDate(3, java.sql.Date.valueOf(dataAtual));
       
       stmtVenda.executeUpdate();
	       
       ResultSet rs  = stmtVenda.getGeneratedKeys();
   	   rs.next();
 	   int idVenda = rs.getInt(1);
 	   rs.close();
 	   
 	  System.err.println("Finalizou o VENDA");
 	   
 	   
 	   
	  for (VendaProdutoModel item : Carrinho) {
		   stmtIDProduto.setString(1, item.getNome());
	 	   
	 	   ResultSet rsIDProduto = stmtIDProduto.executeQuery();
	 	   rsIDProduto.next();
	 	   int idProduto = rsIDProduto.getInt(1);
	 	  rsIDProduto.close();
	 	  System.err.println("fINALIZOU O PRODUTO");
		  
		  
		   stmtItensVenda.setInt(1, Integer.parseInt(item.getQuantidade()));
		   stmtItensVenda.setInt(2, idProduto);
		   stmtItensVenda.setInt(3, idVenda);
		   stmtItensVenda.setDouble(4, Double.parseDouble(item.getTotal()));
		   stmtItensVenda.setDouble(5, Double.parseDouble(item.getDesconto()));
		   stmtItensVenda.setDate(6, java.sql.Date.valueOf(LocalDate.now()));
		   stmtItensVenda.addBatch();
		   System.err.println("FINALIZOU O ITENSVENDAS");
		   
		   stmtUpdate.setInt(1, Integer.parseInt(item.getQuantidade()));
		   stmtUpdate.setString(2, item.getNome());
		   stmtUpdate.addBatch(); 
		   System.err.println("FINALIZOU O atualizacao PRODUTO");
	  }
	  stmtItensVenda.executeBatch();
      stmtUpdate.executeBatch();

      con.commit();
     
      System.err.println("EXECUTOU O BATCH");
	  
	   }catch (SQLException esql) {
	          try {
	        	  System.err.println("VAI FAZER O ROLLBACK");
	        	  con.rollback(); 
		          con.close();
	          }catch(SQLException eroll) {
	        	  throw new Error("VENDA PRODUTO: erro no rollback");
	          }
	          
	   }catch(Exception e) {
     	  throw new Error("VENDA PRODUTO: erro no generico");
       }
	}
}

