/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Master
 */
public record CadastroUsuarioModel ( String nome, String sobrenome, String matricula, String DtaNascimento, String sexo, String cpf, 
String cep, String endereco, String estado, String bairro, String cidade, String numero, String complemento, String usuario, 
String senha,String funcao, String email, String telefone,  int id){
    
    
    public CadastroUsuarioModel(String nome, String sobrenome, String matricula, String DtaNascimento, String sexo, String cpf, 
String cep, String endereco, String estado, String bairro, String cidade, String numero, String complemento, String usuario, 
String senha,String funcao, String email, String telefone){
        this(nome, 
            sobrenome, 
            matricula, 
            DtaNascimento, 
            sexo, 
            cpf, 
            cep, 
            endereco, 
            estado, 
            bairro, 
            cidade, 
            numero, 
            complemento, 
            usuario, 
            senha, 
            funcao, 
            email, 
            telefone,
            -1
        );
    }

}


