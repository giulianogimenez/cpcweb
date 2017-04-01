package br.edu.fatecsjc.lab3.model;

import java.io.Serializable;

public class Estabelecimento implements Serializable {
	private String nome;
	private Bandeira bandeira;
	private String endereco;
	private float lat;
	private float longi;
	private boolean conveniencia;
	private boolean alimentacao;
	private boolean trocaOleo;
	private boolean lavaRapido;
	private boolean mecanico;
	private boolean borracheiro;
	private boolean caixaEletronico;
	private boolean semParar;
	private boolean viaFacil;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Bandeira getBandeira() {
		return bandeira;
	}
	
	public void setBandeira(Bandeira bandeira) {
		this.bandeira = bandeira;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public float getLat() {
		return lat;
	}
	
	public void setLat(float lat) {
		this.lat = lat;
	}
	
	public float getLongi() {
		return longi;
	}
	
	public void setLongi(float longi) {
		this.longi = longi;
	}
	
	public boolean isConveniencia() {
		return conveniencia;
	}
	
	public void setConveniencia(boolean conveniencia) {
		this.conveniencia = conveniencia;
	}
	
	public boolean isAlimentacao() {
		return alimentacao;
	}
	
	public void setAlimentacao(boolean alimentacao) {
		this.alimentacao = alimentacao;
	}
	
	public boolean isTrocaOleo() {
		return trocaOleo;
	}
	
	public void setTrocaOleo(boolean trocaOleo) {
		this.trocaOleo = trocaOleo;
	}
	
	public boolean isLavaRapido() {
		return lavaRapido;
	}
	
	public void setLavaRapido(boolean lavaRapido) {
		this.lavaRapido = lavaRapido;
	}
	
	public boolean isMecanico() {
		return mecanico;
	}
	
	public void setMecanico(boolean mecanico) {
		this.mecanico = mecanico;
	}
	
	public boolean isBorracheiro() {
		return borracheiro;
	}
	
	public void setBorracheiro(boolean borracheiro) {
		this.borracheiro = borracheiro;
	}
	
	public boolean isCaixaEletronico() {
		return caixaEletronico;
	}
	
	public void setCaixaEletronico(boolean caixaEletronico) {
		this.caixaEletronico = caixaEletronico;
	}
	
	public boolean isSemParar() {
		return semParar;
	}
	
	public void setSemParar(boolean semParar) {
		this.semParar = semParar;
	}
	
	public boolean isViaFacil() {
		return viaFacil;
	}
	
	public void setViaFacil(boolean viaFacil) {
		this.viaFacil = viaFacil;
	}

	public Estabelecimento() {
		
	}
	
	public Estabelecimento(String nome, Bandeira bandeira, String endereco, float lat, float longi,
			boolean conveniencia, boolean alimentacao, boolean trocaOleo, boolean lavaRapido, boolean mecanico,
			boolean borracheiro, boolean caixaEletronico, boolean semParar, boolean viaFacil) {
		super();
		this.nome = nome;
		this.bandeira = bandeira;
		this.endereco = endereco;
		this.lat = lat;
		this.longi = longi;
		this.conveniencia = conveniencia;
		this.alimentacao = alimentacao;
		this.trocaOleo = trocaOleo;
		this.lavaRapido = lavaRapido;
		this.mecanico = mecanico;
		this.borracheiro = borracheiro;
		this.caixaEletronico = caixaEletronico;
		this.semParar = semParar;
		this.viaFacil = viaFacil;
	}
	
	
	
}
