/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Master
 */
public record CadastroProdutoModel(String nome,
 String sobrenome,
 String matricula,
 String sexo,
 String cpf,
 String cep,
 String endereco,
 String estado,
 String bairro,
 String cidade,
 String numero,
 String complemento,
 String usuario,
 String senha,
 String funcao, int id) {
    public CadastroProdutoModel(String nome,
 String sobrenome,
 String matricula,
 String sexo,
 String cpf,
 String cep,
 String endereco,
 String estado,
 String bairro,
 String cidade,
 String numero,
 String complemento,
 String usuario,
 String senha,
 String funcao){
        this(nome, sobrenome,matricula,sexo,cpf,cep,endereco,
                estado,bairro,cidade,numero,complemento,usuario,senha,funcao, -1);
    }
    
    
}
