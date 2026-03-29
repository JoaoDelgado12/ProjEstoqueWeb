/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import connection.ConnectionFactory;
import java.sql.PreparedStatement;

/**
 *
 * @author Master
 */
public class CadastrosUserDAO {
    public boolean cadastrar(CadastroUsuarioModel user){
        String sql = "INSERT INTO users " +
                "(nome, sobrenome, matricula, cpf, sexo, dtaNascimento, email, telefone, funcao,"
                + "cep) "
                + "VALUE (?,?,?,?,?,?,?,?)"; 
        try(var con = ConnectionFactory.getConnection()){
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getSobrenome());
            stmt.setString(3, user.getMatricula());
            stmt.setString(4, user.getCpf());
            stmt.setString(5, user.getSexo());
            stmt.setString(6, user.getDtaNascimento());
            stmt.setString(7, user.getEmail());
            stmt.setLong(8, user.getTelefone());
            stmt.setString(9, user.getFuncao());
            stmt.setLong(9, user.getCep());
            
            stmt.executeUpdate();
            
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
