/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Master
 */
public record UserModel(String nome, String senha, int id) { 
    public UserModel(String nome, String senha) {
        this(nome, senha, -1); // OBRIGATÓRIO chamar o construtor principal aqui
    }
}
