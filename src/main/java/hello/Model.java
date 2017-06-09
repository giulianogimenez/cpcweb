package hello;

import java.util.ArrayList;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

import br.edu.fatecsjc.lab3.model.Estabelecimento;
import br.edu.fatecsjc.lab3.model.Preco;

public class Model{
	
	ObjectContainer estabelecimentos = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "bd/estabelecimentos.db4o");
	ObjectContainer precos = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "bd/precos.db4o");
	private static Model model;
	
	public Model(){
		
	}
	
	public static Model getInstance(){
		if(model == null){
			model = new Model();
		}
		return model;
	}
	
	public boolean addEstabelecimento(Estabelecimento estabelecimento){
		
		if(isEstabelecimentoAvailable(estabelecimento.getNome())){
			Query query = estabelecimentos.query();
			query.constrain(Estabelecimento.class);
			estabelecimentos.store(estabelecimento);
		    estabelecimentos.commit();
			return true;
		} else {
			Query query = estabelecimentos.query();
			query.constrain(Estabelecimento.class);
			Estabelecimento estabelecimentoUpdate = this.buscarEstabelecimento(estabelecimento.getNome());
			estabelecimentoUpdate.setAlimentacao(estabelecimento.getAlimentacao());
			estabelecimentoUpdate.setBandeira(estabelecimento.getBandeira());
			estabelecimentoUpdate.setBorracheiro(estabelecimento.getBorracheiro());
			estabelecimentoUpdate.setCaixaEletronico(estabelecimento.getCaixaEletronico());
			estabelecimentoUpdate.setConveniencia(estabelecimento.getConveniencia());
			estabelecimentoUpdate.setEndereco(estabelecimento.getEndereco());
			estabelecimentoUpdate.setLat(estabelecimento.getLat());
			estabelecimentoUpdate.setLongi(estabelecimento.getLongi());
			estabelecimentoUpdate.setNome(estabelecimento.getNome());
			estabelecimentoUpdate.setEndereco(estabelecimento.getEndereco());
			estabelecimentos.store(estabelecimentoUpdate);
		    estabelecimentos.commit();
		    return true;
		}
	}
	
	public boolean isEstabelecimentoAvailable(String nome){
		Query query = estabelecimentos.query();
		query.constrain(Estabelecimento.class);
	    ObjectSet<Estabelecimento> allEstabelecimento = query.execute();
	    
	    for(Estabelecimento student : allEstabelecimento){
	    	if(student.getNome().equals(nome)) return false;
	    }
	    return true;
	}
		
	public List<Estabelecimento> listEstabelecimentos(){
		
		Query query = estabelecimentos.query();
		query.constrain(Estabelecimento.class);
	    ObjectSet<Estabelecimento> allEstabelecimentos = query.execute();
		List<Estabelecimento> estabelecimentosList = new ArrayList<Estabelecimento>();
		for (Estabelecimento estabelecimento : allEstabelecimentos) {
			estabelecimento.setPrecos(searchPreco(estabelecimento.getNome()));
			estabelecimentosList.add(estabelecimento);
		}
		return estabelecimentosList;
	}
	
	public Estabelecimento buscarEstabelecimento(String nome) {
		Query query = estabelecimentos.query();
		query.constrain(Estabelecimento.class);
	    ObjectSet<Estabelecimento> allEstabelecimentos = query.execute();
		List<Estabelecimento> estabelecimentosList = new ArrayList<Estabelecimento>();
		for (Estabelecimento estabelecimento : allEstabelecimentos) {
			estabelecimento.setPrecos(searchPreco(estabelecimento.getNome()));
			estabelecimentosList.add(estabelecimento);
			if(estabelecimento.getNome().equals(nome)) {
				return estabelecimento;
			}
		}
		return null;
	}
	
	public void deleteEstabelecimento(String nome){
		Query query = estabelecimentos.query();
		query.constrain(Estabelecimento.class);
		List<Estabelecimento> allEstabelecimentos = query.execute();
		
		for(Estabelecimento estabelecimento : allEstabelecimentos){
			if(estabelecimento.getNome().toLowerCase().equals(nome.toLowerCase())){
				estabelecimentos.delete(estabelecimento);
				estabelecimentos.commit();
				break;
			}
		}
	}
	
	public boolean addPreco(Preco preco){
		if(isEstabelecimentoAvailable(preco.getEstabelecimento().getNome())){
			addEstabelecimento(preco.getEstabelecimento());
		}
		Query query = precos.query();
		query.constrain(Preco.class);
		ObjectSet<Preco> allPrecos = query.execute();
		
		for(Preco p:allPrecos){
			if(p.getEstabelecimento().getNome().equals(preco.getEstabelecimento().getNome()) && p.getAtivo() && p.getTipoCombustivel().equals(preco.getTipoCombustivel())){
				p.setAtivo(false);
				precos.store(p);
				precos.commit();
			}
		}	
		
		precos.store(preco);
		precos.commit();
		return true;
	}
		
	public List<Preco> searchPreco(String estabelecimento) {
		Query query = precos.query();
		query.constrain(Preco.class);
	    ObjectSet<Preco> allPrecos = query.execute();
	    List<Preco> precosList = new ArrayList<Preco>();
	    for (Preco preco : allPrecos) {
	    	
			if(preco.getEstabelecimento().getNome().equals(estabelecimento) && preco.getAtivo() ) {
				precosList.add(preco);
			}
		}
	    return precosList;
	}
	
}
