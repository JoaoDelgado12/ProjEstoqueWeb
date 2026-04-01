package Dao;

import connection.ConnectionFactory;
import java.sql.PreparedStatement;
import Model.CadastroUsuarioModel;

/**
 *
 * @author Master
 */
public class CadastrosUserDAO {
    public boolean cadastrar(CadastroUsuarioModel user){
        String sql = "INSERT INTO users" + 
            "(username, pass, nome, sobrenome, matricula, cpf, sexo, email,"
                + " telefone, DtaNascimento, funcao, cep, endereco, numero, bairro, cidade, estado, complemento)" + 
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,)";
        try(var con = ConnectionFactory.getConnection()){
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(3, user.nome());
            stmt.setString(4, user.sobrenome());
            stmt.setString(5, user.matricula());
            stmt.setString(6, user.cpf());
            stmt.setString(7, user.sexo());
            stmt.setString(10, user.DtaNascimento());
            stmt.setString(8, user.email());
            stmt.setString(9, user.telefone());
            stmt.setString(11, user.funcao());
            stmt.setString(12, user.cep());
            stmt.setString(13, user.endereco());
            stmt.setString(14, user.numero());
            stmt.setString(15, user.bairro());
            stmt.setString(16, user.cidade());
            stmt.setString(17, user.estado());
            stmt.setString(18, user.complemento());
            stmt.setString(1, user.usuario());
            stmt.setString(2, user.senha());
            
            
            
            stmt.executeUpdate();
            
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
