package hello;

import static spark.Spark.get;
import static spark.Spark.post;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.edu.fatecsjc.lab3.model.Bandeira;
import br.edu.fatecsjc.lab3.model.Estabelecimento;
import br.edu.fatecsjc.lab3.model.Preco;
import br.edu.fatecsjc.lab3.model.TipoCombustivel;
import spark.Request;
import spark.Response;
import spark.ResponseTransformer;
import spark.Route;

public class REST implements ResponseTransformer{
	
	private Model model;
	
	public REST(Model store){
		this.model = store;
	}
	
	public void listarProcuraEstabelecimento(){
		get("/estabelecimento", new Route() {
			@Override
            public Object handle(final Request request, final Response response){
	        	
	        	response.header("Access-Control-Allow-Origin", "*");
	        	
	            try {
	            	List<Estabelecimento> estabelecimentosList = model.listEstabelecimentos();
	            	if(!estabelecimentosList.isEmpty()){
	            		JSONArray jsonArray= new JSONArray();
	            		for(Estabelecimento estab: estabelecimentosList){
	            			JSONObject jsonResult = new JSONObject();
	            			jsonResult.put("nome",estab.getNome());
	            			jsonResult.put("bandeira",estab.getBandeira());
	            			jsonResult.put("endereco",estab.getEndereco());
	            			jsonResult.put("lat",estab.getLat());
	            			jsonResult.put("longi",estab.getLongi());
	            			jsonResult.put("conveniencia",estab.getConveniencia());
	            			jsonResult.put("alimentacao",estab.getAlimentacao());
	            			jsonResult.put("trocaOleo",estab.getTrocaOleo());
	            			jsonResult.put("lavaRapido",estab.getLavaRapido());
	            			jsonResult.put("mecanico",estab.getMecanico());
	            			jsonResult.put("borracheiro",estab.getBorracheiro());
	            			jsonResult.put("caixaEletronico",estab.getCaixaEletronico());
	            			jsonResult.put("semParar",estab.getSemParar());
	            			jsonResult.put("viaFacil",estab.getViaFacil());
	            			jsonResult.put("precos", REST.this.toString(estab.getPrecos()));
	            			jsonArray.put(jsonResult);
	            		}
	            		return jsonArray;
	            	} 
	             	
        		} catch (JSONException e) {	
        			e.printStackTrace();
        		}
	            return new JSONArray();
	         }
		});
	}
	
	public void listarPrecos(){
		get("/precos/:estabelecimento", new Route() {
			@Override
            public Object handle(final Request request, final Response response){
	        	
	        	response.header("Access-Control-Allow-Origin", "*");
	        	
	        	try {
	            	List<Preco> precosList = model.searchPreco(request.params(":estabelecimento"));
	            	if(!precosList.isEmpty()){
	            		JSONArray jsonResult = new JSONArray();
	            		for(Preco p: precosList){
	            			JSONObject jsonObject = new JSONObject();
	            			jsonObject.put("valor",p.getValor());
	            			jsonObject.put("tipoCombustivel", p.getTipoCombustivel().toString());
	            			jsonResult.put(jsonObject);
	            		}
	            		return jsonResult;
	            	} 
	             	
        		} catch (JSONException e) {	
        			e.printStackTrace();
        		}
	            return new JSONArray();
	         }
		});
	}
	
	public void buscarEstabelecimento(){
		get("/estabelecimento/:estabelecimento", new Route() {
			@Override
            public Object handle(final Request request, final Response response){
	        	
	        	response.header("Access-Control-Allow-Origin", "*");
	        	
	        	try {
	        		Estabelecimento estab = model.buscarEstabelecimento(request.params(":estabelecimento"));
	        		if(estab != null) {
	        			JSONArray jsonArray = new JSONArray();
		        		JSONObject jsonResult = new JSONObject();
	        			jsonResult.put("nome",estab.getNome());
	        			jsonResult.put("bandeira",estab.getBandeira());
	        			jsonResult.put("endereco",estab.getEndereco());
	        			jsonResult.put("lat",estab.getLat());
	        			jsonResult.put("longi",estab.getLongi());
	        			jsonResult.put("conveniencia",estab.getConveniencia());
	        			jsonResult.put("alimentacao",estab.getAlimentacao());
	        			jsonResult.put("trocaOleo",estab.getTrocaOleo());
	        			jsonResult.put("lavaRapido",estab.getLavaRapido());
	        			jsonResult.put("mecanico",estab.getMecanico());
	        			jsonResult.put("borracheiro",estab.getBorracheiro());
	        			jsonResult.put("caixaEletronico",estab.getCaixaEletronico());
	        			jsonResult.put("semParar",estab.getSemParar());
	        			jsonResult.put("viaFacil",estab.getViaFacil());
	        			jsonResult.put("precos", REST.this.toString(estab.getPrecos()));
	        			jsonArray.put(jsonResult);
	        			return jsonArray;
	        		}
	             	
        		} catch (JSONException e) {	
        			e.printStackTrace();
        		}
	            return new JSONArray();
	         }
		});
	}
	
	public void addEstabelecimento(){
		post("/estabelecimento", new Route() {
			@Override
            public Object handle(final Request request, final Response response){
	        	
	        	response.header("Access-Control-Allow-Origin", "*");
	        	
	        	JSONObject json = new JSONObject(convertJSONString(request.body()));
	        	JSONArray jsonResult = new JSONArray();
    			JSONObject jsonObj = new JSONObject();
	        	
	        	Estabelecimento estab = new Estabelecimento();
	        	
	        	estab.setNome(json.getString("nome"));
	        	estab.setBandeira(convertToBandeira(json.getString("bandeira")));
	        	estab.setEndereco(json.getString("endereco"));
	        	estab.setLat(json.getDouble("lat"));
	        	estab.setLongi(json.getDouble("longi"));
	        	estab.setConveniencia(json.getBoolean("conveniencia"));
	        	estab.setAlimentacao(json.getBoolean("alimentacao"));
	        	estab.setTrocaOleo(json.getBoolean("trocaOleo"));  
	        	estab.setLavaRapido(json.getBoolean("lavaRapido"));
	        	estab.setMecanico(json.getBoolean("mecanico"));
	        	estab.setBorracheiro(json.getBoolean("borracheiro"));
	        	estab.setCaixaEletronico(json.getBoolean("caixaEletronico"));
	        	estab.setSemParar(json.getBoolean("semParar"));
	        	estab.setViaFacil(json.getBoolean("viaFacil"));
	        	
	        	Preco gasolina = new Preco();
	        	if(json.getDouble("precoGasolina") != 0f) {
		        	gasolina.setDataAtualizacao(LocalDateTime.now());
		        	gasolina.setEstabelecimento(estab);
		        	gasolina.setTipoCombustivel(TipoCombustivel.GASOLINA);
		        	gasolina.setAtivo(true);
		        	gasolina.setValor(Float.valueOf(Double.valueOf(json.getDouble("precoGasolina")).toString()));
	        	}
	        	Preco etanol = new Preco();
	        	if(json.getDouble("etanol") != 0f) {
		        	etanol.setDataAtualizacao(LocalDateTime.now());
		        	etanol.setEstabelecimento(estab);
		        	etanol.setTipoCombustivel(TipoCombustivel.ETANOL);
		        	etanol.setAtivo(true);
		        	etanol.setValor(Float.valueOf(Double.valueOf(json.getDouble("etanol")).toString()));
	        	}
	        	try {
            		if(model.addEstabelecimento(estab)){
            			if(gasolina.getEstabelecimento() != null)
            				model.addPreco(gasolina);
            			if(etanol.getEstabelecimento() != null)
            				model.addPreco(etanol);

            			jsonObj.put("status", 1);
	         	        jsonResult.put(jsonObj);
	         	        
	         	        return jsonResult;
                   	} 
        		}catch (JSONException e){
        			e.printStackTrace();
        		}
    			
    			jsonObj.put("status", 0);
     	        jsonResult.put(jsonObj);
     	       
     	        return jsonResult;
	         }
		});
	}
	
	private Bandeira convertToBandeira(String bandeira){
		bandeira = bandeira.toUpperCase();
		if(bandeira.equals("BR"))
			return Bandeira.BR;
		if(bandeira.equals("SHELL"))
			return Bandeira.SHELL;
		if(bandeira.equals("IPIRANGA"))
			return Bandeira.IPIRANGA;
		return null;
	}
	
	private String convertJSONString(String str){
		
		Charset utf8charset = Charset.forName("UTF-8");
		Charset iso88591charset = Charset.forName("ISO-8859-1");
	
		ByteBuffer inputBuffer = ByteBuffer.wrap(str.getBytes());
	
		// decode UTF-8
		CharBuffer data = utf8charset.decode(inputBuffer);
	
		// encode ISO-8559-1
		ByteBuffer outputBuffer = iso88591charset.encode(data);
		byte[] outputData = outputBuffer.array();
		
		str = new String(outputData);
		
		return str;
	}
	
	private List<String> toString(List<Preco> listPreco){
		List<String> ret = new ArrayList<String>();
		for(Preco preco: listPreco){
			ret.add(preco.getTipoCombustivel().toString() + ":  R$ " + String.format("%.3f",preco.getValor()));
		}
		return ret;
	}

	@Override
	public String render(Object model) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}