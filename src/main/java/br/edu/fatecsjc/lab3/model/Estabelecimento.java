package br.edu.fatecsjc.lab3.model;

import java.io.Serializable;
import java.util.List;

public class Estabelecimento implements Serializable {
	private String nome;
	private Bandeira bandeira;
	private String endereco;
	private double lat;
	private double longi;
	private Boolean conveniencia;
	private Boolean alimentacao;
	private Boolean trocaOleo;
	private Boolean lavaRapido;
	private Boolean mecanico;
	private Boolean borracheiro;
	private Boolean caixaEletronico;
	private Boolean semParar;
	private Boolean viaFacil;
	private List<Preco> precos;
	
	public Boolean getConveniencia() {
		return conveniencia;
	}

	public void setConveniencia(Boolean conveniencia) {
		this.conveniencia = conveniencia;
	}

	public Boolean getAlimentacao() {
		return alimentacao;
	}

	public void setAlimentacao(Boolean alimentacao) {
		this.alimentacao = alimentacao;
	}

	public Boolean getTrocaOleo() {
		return trocaOleo;
	}

	public void setTrocaOleo(Boolean trocaOleo) {
		this.trocaOleo = trocaOleo;
	}

	public Boolean getLavaRapido() {
		return lavaRapido;
	}

	public void setLavaRapido(Boolean lavaRapido) {
		this.lavaRapido = lavaRapido;
	}

	public Boolean getMecanico() {
		return mecanico;
	}

	public void setMecanico(Boolean mecanico) {
		this.mecanico = mecanico;
	}

	public Boolean getBorracheiro() {
		return borracheiro;
	}

	public void setBorracheiro(Boolean borracheiro) {
		this.borracheiro = borracheiro;
	}

	public Boolean getCaixaEletronico() {
		return caixaEletronico;
	}

	public void setCaixaEletronico(Boolean caixaEletronico) {
		this.caixaEletronico = caixaEletronico;
	}

	public Boolean getSemParar() {
		return semParar;
	}

	public void setSemParar(Boolean semParar) {
		this.semParar = semParar;
	}

	public Boolean getViaFacil() {
		return viaFacil;
	}

	public void setViaFacil(Boolean viaFacil) {
		this.viaFacil = viaFacil;
	}

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
	
	public double getLat() {
		return lat;
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public double getLongi() {
		return longi;
	}
	
	public void setLongi(double longi) {
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
	
	public Estabelecimento(String nome, Bandeira bandeira, String endereco, double lat, double longi,
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

	public List<Preco> getPrecos() {
		return precos;
	}

	public void setPrecos(List<Preco> precos) {
		this.precos = precos;
	}
}
