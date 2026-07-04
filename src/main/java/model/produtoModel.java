package model;

public class produtoModel {
	private String nome;
	private String quantidadeAtual;
	private String quantidadeMinima;
	private String precoVenda;
	private String id;
	
	void produtoMolde(String nome ,String quantidadeAtual ,String quantidadeMinima,
			String precoVenda ,String id) {
		this.nome = nome;
		this.quantidadeAtual = quantidadeAtual;
		this.quantidadeMinima = quantidadeMinima;
		this.precoVenda = precoVenda;
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getQuantidadeAtual() {
		return quantidadeAtual;
	}
	public void setQuantidadeAtual(String quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}
	public String getQuantidadeMinima() {
		return quantidadeMinima;
	}
	public void setQuantidadeMinima(String quantidadeMinima) {
		this.quantidadeMinima = quantidadeMinima;
	}
	public String getPrecoVenda() {
		return precoVenda;
	}
	public void setPrecoVenda(String precoVenda) {
		this.precoVenda = precoVenda;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
