package Dao;

import Model.UserModel;
import connection.ConnectionFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import util.SenhaHash;

/**
 *
 * @author Master
 */
public class UserDAO {
    
    public boolean validarLogin(UserModel usermodel){
        String sql = "SELECT * FROM perfilUsuario WHERE usuario = ?";
        
        try (var con = ConnectionFactory.getConnection(); 
    		PreparedStatement stmt = con.prepareStatement(sql)){
            
            stmt.setString(1, usermodel.getUsuario());
            
            try(ResultSet rs = stmt.executeQuery()){
            
	            if(rs.next()){
	                String hashBanco = rs.getString("senha");
	                stmt.close();
	                return true;
	                // return SenhaHash.verificarSenha(usermodel.getSenha(),hashBanco);
	            }
            }
            stmt.close();
            return false;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
