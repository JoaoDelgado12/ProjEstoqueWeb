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
	public  void registrarVenda(List<VendaProdutoModel> Carrinho, double precoTotal, double desconto) throws SQLException {
		String sqlIDProduto= "SELECT id FROM Produto WHERE nome = ?";
		String sqlVenda = "INSERT INTO Venda (precoTotal, desconto, dataVenda) VALUES (?, ?, ?)";
        String sqlItensVenda = "INSERT INTO ItensVenda (quantidade, produto, venda, "
        		+ " precoTotal, desconto, dataVenda) VALUES (?, ?, ?,?, ?, ?)";
        String sqlUpdate = "UPDATE Produto set quantidade = quantidade - ? WHERE nome = ? ";
        var con = ConnectionFactory.getConnection();
        con.setAutoCommit(false);

   try (PreparedStatement stmtIDProduto = con.prepareStatement(sqlIDProduto);
	   PreparedStatement stmtVenda = con.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS);
       PreparedStatement stmtItensVenda = con.prepareStatement(sqlItensVenda);
	   PreparedStatement stmtUpdate = con.prepareStatement(sqlUpdate)) {
	   

	   
	   stmtVenda.setDouble(1, precoTotal);;
	   stmtVenda.setDouble(2, desconto);
       
       LocalDate dataAtual = LocalDate.now();
       stmtVenda.setDate(3, java.sql.Date.valueOf(dataAtual));
       
       stmtVenda.executeUpdate();
	       
       ResultSet rs  = stmtVenda.getGeneratedKeys();
   	   rs.next();
 	   int idVenda = rs.getInt(1);
 	   rs.close();
 	   

 	   
 	   
 	   
	  for (VendaProdutoModel item : Carrinho) {
		   stmtIDProduto.setString(1, item.getNome());
	 	   
	 	   ResultSet rsIDProduto = stmtIDProduto.executeQuery();
	 	   rsIDProduto.next();
	 	   int idProduto = rsIDProduto.getInt(1);
	 	  rsIDProduto.close();
		  
		  
		   stmtItensVenda.setInt(1, Integer.parseInt(item.getQuantidade()));
		   stmtItensVenda.setInt(2, idProduto);
		   stmtItensVenda.setInt(3, idVenda);
		   stmtItensVenda.setDouble(4, Double.parseDouble(item.getTotal()));
		   stmtItensVenda.setDouble(5, Double.parseDouble(item.getDesconto()));
		   stmtItensVenda.setDate(6, java.sql.Date.valueOf(LocalDate.now()));
		   stmtItensVenda.addBatch();
		   
		   stmtUpdate.setInt(1, Integer.parseInt(item.getQuantidade()));
		   stmtUpdate.setString(2, item.getNome());
		   stmtUpdate.addBatch(); 
	  }
	  stmtItensVenda.executeBatch();

      stmtUpdate.executeBatch();


      con.commit();

	  
	   }catch (SQLException esql) {
	          try {

	        	  con.rollback(); 
		          con.close();
	          }catch(SQLException eroll) {
	        	  throw new Error("VENDA PRODUTO: erro no rollback");
	          }
	          
	   }catch(Exception e) {
     	  throw new Error("VENDA PRODUTO: erro no generico");
       }finally {
    	   con.close();
       }
	}
}

