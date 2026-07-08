package model;

public class GerenciarProdutoModel {
	private String nome;
	private String marca;
	private String fornecedor;
	private String precoVendaUni;
	private String quantidade;
	private String quantidadeMin;
	private String localArmazenamento;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}
	public String getPrecoVendaUni() {
		return precoVendaUni;
	}
	public void setPrecoVendaUni(String precoVendaUni) {
		this.precoVendaUni = precoVendaUni;
	}
	public String getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	public String getQuantidadeMin() {
		return quantidadeMin;
	}
	public void setQuantidadeMin(String quantidadeMin) {
		this.quantidadeMin = quantidadeMin;
	}
	public String getLocalArmazenamento() {
		return localArmazenamento;
	}
	public void setLocalArmazenamento(String localArmazenamento) {
		this.localArmazenamento = localArmazenamento;
	}
	
}
