/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Master
 */
public record UserModel(String usuario, String senha, int id) { 
    public UserModel(String usuario, String senha) {
        this(usuario, senha, -1); // OBRIGATÓRIO chamar o construtor principal aqui
    }
}
