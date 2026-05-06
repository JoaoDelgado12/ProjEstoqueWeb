package Dao;

import connection.ConnectionFactory;
import java.sql.PreparedStatement;
import Model.CadastroUsuarioModel;
import util.SenhaHash;

/**
 *
 * @author Master
 */

public class CadastrosUserDAO2 {
    public boolean cadastrar(CadastroUsuarioModel user){
        String sql = "INSERT INTO cadastro_usuario" + 
            "(nome, sobrenome, matricula, dta_nascimento, sexo, cpf, cep, endereco, estado, bairro, "
                + " cidade, numero, complemento, usuario, senha, funcao, email, telefone)" + 
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        try(var con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){
            
            String senhaHash = SenhaHash.gerarHash(user.getSenha());
                    
                    
            stmt.setString(14, user.getUsuario());
            stmt.setString(15, senhaHash);
            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getSobrenome());
            stmt.setString(3, user.getMatricula());
            stmt.setString(6, user.getCpf());
            stmt.setString(5, user.getSexo());
            stmt.setString(17, user.getEmail());
            stmt.setString(18, user.getTelefone());
            stmt.setString(4, user.getDtaNascimento());
            stmt.setString(16, user.getFuncao());
            stmt.setString(7, user.getCep());
            stmt.setString(8, user.getEndereco());
            stmt.setString(12, user.getNumero());
            stmt.setString(10, user.getBairro());
            stmt.setString(11, user.getCidade());
            stmt.setString(9, user.getEstado());
            stmt.setString(13, user.getComplemento());
            
            stmt.executeUpdate();
            stmt.close();
            
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
