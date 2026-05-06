package Model;


public class CadastroUsuarioModel {

    private String nome;
    private String sobrenome;
    private String matricula;
    private String DtaNascimento;
    private String sexo;
    private String cpf;
    private String cep;
    private String endereco;
    private String estado;
    private String bairro;
    private String cidade;
    private String numero;
    private String complemento;
    private String usuario;
    private String senha;
    private String funcao;
    private String email;
    private String telefone;
    private int id;

    // Construtor completo
    public CadastroUsuarioModel(String nome, String sobrenome, String matricula, String DtaNascimento, String sexo, String cpf,
                                 String cep, String endereco, String estado, String bairro, String cidade, String numero,
                                 String complemento, String usuario, String senha, String funcao, String email,
                                 String telefone) {

        this.nome = nome;
        this.sobrenome = sobrenome;
        this.matricula = matricula;
        this.DtaNascimento = DtaNascimento;
        this.sexo = sexo;
        this.cpf = cpf;
        this.cep = cep;
        this.endereco = endereco;
        this.estado = estado;
        this.bairro = bairro;
        this.cidade = cidade;
        this.numero = numero;
        this.complemento = complemento;
        this.usuario = usuario;
        this.senha = senha;
        this.funcao = funcao;
        this.email = email;
        this.telefone = telefone;
    }


    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSobrenome() { return sobrenome; }
    public void setSobrenome(String sobrenome) { this.sobrenome = sobrenome; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getDtaNascimento() { return DtaNascimento; }
    public void setDtaNascimento(String dtaNascimento) { DtaNascimento = dtaNascimento; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getFuncao() { return funcao; }
    public void setFuncao(String funcao) { this.funcao = funcao; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
}


