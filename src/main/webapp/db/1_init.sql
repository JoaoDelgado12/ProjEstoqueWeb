CREATE SCHEMA IF NOT EXISTS estoque_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE estoque_db ;

CREATE TABLE IF NOT EXISTS funcao(
  funcao VARCHAR(100) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS grupoPermissao(
  grupoPermissao VARCHAR(100) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS perfilUsuario(
  id int AUTO_INCREMENT PRIMARY KEY,
  usuario VARCHAR(100) UNIQUE, 
  senha VARCHAR(100),
  funcao VARCHAR(100),
  CONSTRAINT fk_funcao_perfil FOREIGN KEY (funcao) REFERENCES funcao(funcao) on update cascade on delete restrict,
  grupoPermissao VARCHAR(100),
  CONSTRAINT fk_grupoPermissao_perfil FOREIGN KEY (grupoPermissao) REFERENCES grupoPermissao(grupoPermissao) on update cascade on delete restrict
);


CREATE TABLE IF NOT EXISTS estado(
  estado VARCHAR(15) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS  cidade(
  cidade VARCHAR(50) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS cepUsuario(
  id INT AUTO_INCREMENT PRIMARY KEY,
  idPerfil INT,
  CONSTRAINT fk_idCepUsuario_perfilUsuario FOREIGN KEY (idPerfil) REFERENCES perfilUsuario(id) on update cascade on delete cascade,
  cep VARCHAR(11),
  estado VARCHAR(15),
  CONSTRAINT fk_estado_cepUsuario FOREIGN KEY (estado) REFERENCES estado(estado) on update cascade on delete cascade,
  cidade VARCHAR(50),
  CONSTRAINT fk_cidade_cepUsuario FOREIGN KEY (cidade) REFERENCES cidade(cidade) on update cascade on delete cascade,
  logradouro VARCHAR(50),
  numero int,
  bairro VARCHAR(50),
  complemento VARCHAR(150)
);

CREATE TABLE IF NOT EXISTS  dadosUsuario(
  id int AUTO_INCREMENT PRIMARY KEY,
  idPerfil INT,
  CONSTRAINT fk_idDadosUsuario_perfilUsuario FOREIGN KEY (idPerfil) REFERENCES perfilUsuario(id) on delete cascade,
  nome VARCHAR(100), 
  sobrenome VARCHAR(100),
  dtaNascimento DATE,
  sexo VARCHAR(10),
  cpf VARCHAR(11) UNIQUE,
  email VARCHAR(120),
  telefone VARCHAR(13)
);



CREATE TABLE IF NOT EXISTS Fornecedor(
	nome VARCHAR(120) PRIMARY KEY,
	telefone VARCHAR(13)
);

CREATE TABLE IF NOT EXISTS Marca(
	marca VARCHAR(120) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS DataPrecoVenda(
	id INT AUTO_INCREMENT PRIMARY KEY,
	dataPrecoVenda DATE,
	precoVendaUni DECIMAL(10,2)
);

CREATE TABLE IF NOT EXISTS Produto(
	id INT AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(120) UNIQUE,
	marca VARCHAR(120),
	CONSTRAINT fk_marca_Produto FOREIGN KEY (marca) REFERENCES Marca(marca) on update cascade on delete restrict,
	fornecedor VARCHAR(120),
	CONSTRAINT fk_fornecedor_Produto FOREIGN KEY (fornecedor) REFERENCES Fornecedor(nome) on update cascade,
	precoVendaUni DECIMAL(10,2),
	quantidade INT,
	quantidadeMin INT,
	localArmazenamento VARCHAR(120)
);

CREATE TABLE IF NOT EXISTS HistEstoque(
	id INT AUTO_INCREMENT PRIMARY KEY,
	produto INT,
	CONSTRAINT fk_produto_HistEstoque FOREIGN KEY (produto) REFERENCES Produto(id) on update cascade,
	quantidade INT,
	dataPrecoCompra DATE,
	precoCompraUni DECIMAL(10,2),
	precoCompraTotal DECIMAL(10,2)
);

CREATE TABLE IF NOT EXISTS Venda(
	id int AUTO_INCREMENT PRIMARY KEY,
	precoTotal DECIMAL(10,2),
	desconto DECIMAL(10,2),
	dataVenda DATE
);


CREATE TABLE IF NOT EXISTS ItensVenda(
	id INT AUTO_INCREMENT PRIMARY KEY,
	quantidade INT,
	dataVenda DATE,
	precoTotal DECIMAL(10,2),
	desconto Decimal(10,2),
	produto INT,
	CONSTRAINT fk_produto_ItensVenda FOREIGN KEY (produto) REFERENCES Produto(id) on update cascade,
	venda int,
	CONSTRAINT fk_venda_ItensVenda FOREIGN KEY (venda) REFERENCES Venda(id) on update cascade on delete cascade
);



