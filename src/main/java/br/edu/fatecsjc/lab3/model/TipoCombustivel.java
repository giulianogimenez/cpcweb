package br.edu.fatecsjc.lab3.model;

public enum TipoCombustivel {
	ETANOL("Etanol"),
	GASOLINA("Gasolina comum"),
	GASOLINA_ADITIVADA("Gasolina Aditivada");
	
	private String nome;
	private TipoCombustivel(String nome) {
		this.nome = nome;
	}
}
