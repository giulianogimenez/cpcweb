package br.edu.fatecsjc.lab3.model;

public enum Bandeira {
	SHELL("Shell"),
	IPIRANGA("Ipiranga"),
	BR("Br");
	
	@SuppressWarnings("unused")
	private String nome;
	
	private Bandeira(String nome) {
		this.nome = nome;
	}
	
}
