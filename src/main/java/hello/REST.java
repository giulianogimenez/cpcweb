package hello;

import static spark.Spark.get;
import static spark.Spark.post;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

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
		System.out.println("listarProcuraEstabelecimento");
		get("/estabelecimento", new Route() {
			@Override
            public Object handle(final Request request, final Response response){
	        	
	        	response.header("Access-Control-Allow-Origin", "*");
	        	
	            try {
	            	List<Estabelecimento> estabelecimentosList = model.listEstabelecimentos();
	            	if(!estabelecimentosList.isEmpty()){
	            		System.out.println("listarProcuraEstabelecimento END");
//		             	return new Gson().toJson(estabelecimentosList);
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
	            			jsonArray.put(jsonResult);
	            		}
	            		return jsonArray;
	            	} 
	             	
        		} catch (JSONException e) {	
        			e.printStackTrace();
        		}
	            System.out.println("listarProcuraEstabelecimento END");
	            return new JSONArray();
	         }
		});
	}
	
	public void listarPrecos(){
		System.out.println("listarPrecos");
		get("/precos/:estabelecimento", new Route() {
			@Override
            public Object handle(final Request request, final Response response){
	        	
	        	response.header("Access-Control-Allow-Origin", "*");
	        	
	        	try {
	            	List<Preco> precosList = model.searchPreco(request.params(":estabelecimento"));
	            	
	            	if(!precosList.isEmpty()){
	            		JSONArray jsonArray = new JSONArray();
	            		for(Preco p: precosList){
	            			JSONObject jsonResult = new JSONObject();
	            			jsonResult.put("valor",p.getValor());
	            			jsonResult.put("tipoCombustivel", p.getTipoCombustivel());
	            			jsonArray.put(jsonResult);
	            		}
	            		System.out.println("listarPrecos END");
	            		return jsonArray;
	            	} 
	             	
        		} catch (JSONException e) {	
        			e.printStackTrace();
        		}
	        	System.out.println("listarPrecos END");
	            return new JSONArray();
	         }
		});
	}
	
	public void addEstabelecimento(){
		System.out.println("addEstabelecimento");
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
	        	estab.setLongi(json.getDouble("lng"));
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
	         	        
	         	       System.out.println("addEstabelecimento END");
	         	        return jsonResult;
                   	} 
        		}catch (JSONException e){
        			e.printStackTrace();
        		}
    			
    			jsonObj.put("status", 0);
     	        jsonResult.put(jsonObj);
     	       System.out.println("addEstabelecimento END");
     	       
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

	@Override
	public String render(Object model) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
//	
//	public void loginPsychologist(){
//		
//		post("/login/psychologist", new Route() {
//			@Override
//            public Object handle(final Request request, final Response response){
//	        	
//	           response.header("Access-Control-Allow-Origin", "*");
//
//	        	
//	        	
//	        	
//	           JSONObject json = new JSONObject(request.body());
//	        	
//	           String userName = json.getString("userName");
//	           
//	           String password = json.getString("password");
//	           	
//	           
//         	    
//	           try {
//	        	   Psychologist psychologist = model.loginPsychologist(userName, password);
//	            	
//	            	if(psychologist != null){
//	            		
//	            		JSONArray jsonResult = new JSONArray();
//		         	    JSONObject jsonObj = new JSONObject();
//
//		        		jsonObj.put("status", 1);
//		        		jsonObj.put("userName", psychologist.getUserName());
//		        		
//		        		
//		             	jsonResult.put(jsonObj);
//		             	
//		             	return jsonResult;
//	            		
//	            	} else {
//	            		
//	            		
//	            		
//	            	}
//	            	
//	            	
//	             	
//	        		} catch (JSONException e) {
//	        				
//	        			//e.printStackTrace();
//
//	        		}
//	         	    	
//	
//	            JSONArray jsonResult = new JSONArray();
//        	    JSONObject jsonObj = new JSONObject();
//
//        	    jsonObj.put("status", 0);
//       		
//       		
//            	jsonResult.put(jsonObj);
//            	
//            	return jsonResult;
//         	   
//         	   
//	        	
//		   }
//		});     
//
//         
//	}
//	
//	
//	public void loginADM(){
//		
//		post("/login/adm", new Route() {
//			@Override
//            public Object handle(final Request request, final Response response){
//	        	
//	           response.header("Access-Control-Allow-Origin", "*");
//
//	        	
//	        	
//	        	
//	           JSONObject json = new JSONObject(request.body());
//	        	
//	           String userName = json.getString("userName");
//	           
//	           String password = json.getString("password");
//	           	
//	           
//         	    
//	           try {
//	        	   ADM adm = model.loginADM(userName, password);
//	            	
//	            	if(adm != null){
//	            		
//	            		JSONArray jsonResult = new JSONArray();
//		         	    JSONObject jsonObj = new JSONObject();
//
//		        		jsonObj.put("status", 1);
//		        		jsonObj.put("userName", adm.getUserName());
//		        		
//		        		
//		             	jsonResult.put(jsonObj);
//		             	
//		             	return jsonResult;
//	            		
//	            	} else {
//	            		
//	            		
//	            		
//	            	}
//	            	
//	            	
//	             	
//	        		} catch (JSONException e) {
//	        				
//	        			//e.printStackTrace();
//
//	        		}
//	         	    	
//	
//	            JSONArray jsonResult = new JSONArray();
//        	    JSONObject jsonObj = new JSONObject();
//
//        	    jsonObj.put("status", 0);
//       		
//       		
//            	jsonResult.put(jsonObj);
//            	
//            	return jsonResult;
//         	   
//         	   
//	        	
//		   }
//		});     
//
//         
//	}
//	
//	public void getStudentCompetencies(){
//		
//		
//	
//		get("/competencies/:ra", new Route() {
//			@Override
//            public Object handle(final Request request, final Response response){
//	        	
//	        	 
//	        	 
//	        	response.header("Access-Control-Allow-Origin", "*");
//	        	 
//	            Integer ra = Integer.parseInt(request.params(":ra"));
//	        	
//	            
//	            try {
//	            	Student student = model.searchStudentbyRA(ra);
//	            	
//	            	JSONArray jsonResult = new JSONArray();
//	         	    JSONObject jsonObj = new JSONObject();
//
//	         	    //jsonObj.put("userName", student.getUserName());
//	         	    //jsonObj.put("password", student.getPassword());
//	        		jsonObj.put("name", student.getName());
//	        		jsonObj.put("ra", student.getRa());
//	        		//jsonObj.put("institution", student.getInstitution());
//	        		//jsonObj.put("course", student.getCourse());
//	        		//jsonObj.put("year", student.getYear());
//	        		//jsonObj.put("period", student.getPeriod());
//	        		jsonObj.put("competencies", student.getCompetencies());
//	        		//jsonObj.put("question", student.getQuestion());
//	        		//jsonObj.put("completed", student.getCompleted());
//	        		//jsonObj.put("psychologistComment", student.getPsychologistComment());
//	        		//jsonObj.put("statusComment", student.getStatusComment());
//	        		
//	             	jsonResult.put(jsonObj);
//	             	
//	             	return jsonResult;
//	             	
//	        		} catch (JSONException e) {
//	        				
//	        			e.printStackTrace();
//	        		}
//	         	    	
//	
//	     	    return null;
//	     	     
//	         }
//	         
//	      });
//
//	         
//	}
//	
//	public void getStudentsQuestionbyRA(){
//		
//		get("/user/:ra", new Route() {
//			@Override
//            public Object handle(final Request request, final Response response){
//	        	
//	        	 
//	        	 
//	        	response.header("Access-Control-Allow-Origin", "*");
//	        	 
//	        	Integer ra = Integer.parseInt(request.params(":ra"));
//	            
//	            try {
//	            	Student student = model.searchStudentbyRA(ra);
//	            	
//	            	JSONArray jsonResult = new JSONArray();
//	         	    JSONObject jsonObj = new JSONObject();
//
//	        		jsonObj.put("question", student.getQuestion());
//	        		jsonObj.put("completed", student.getCompleted());
//	        		jsonObj.put("statusComment", student.getStatusComment());
//	        		jsonObj.put("psychologistComment", student.getPsychologistComment());
//	        		
//	             	jsonResult.put(jsonObj);
//	             	
//	             	return jsonResult;
//	             	
//	        		} catch (JSONException e) {
//	        				
//	        			e.printStackTrace();
//	        		}
//	         	    	
//	
//	     	    return null;
//	     	     
//	         }
//	         
//	      });
//		
//		
//	}
//	
//	public void getQuestionByNumber(){
//		
//		get("/questions/:number", new Route() {
//			@Override
//            public Object handle(final Request request, final Response response) throws UnsupportedEncodingException{
//	        	
//	        	response.header("Access-Control-Allow-Origin", "*");
//	        	 
//	            Integer number = Integer.parseInt(request.params(":number"));
//	        	
//	            
//	            try {
//	            	Question question = model.searchQuestionByCode(number);
//	            	
//	            	JSONArray jsonResult = new JSONArray();
//	         	    JSONObject jsonObjQuestion = new JSONObject();
//	         	    
//	         	    jsonObjQuestion.put("number", question.getNumber());
//	        		jsonObjQuestion.put("introduction", java.net.URLDecoder.decode(question.getIntroduction(), "UTF-8"));
//	        		jsonObjQuestion.put("introductionMediaType", question.getIntroductionMediaType());
//	        		jsonObjQuestion.put("question", question.getQuestion());
//	        		jsonObjQuestion.put("answer", question.getAnswers());
//	        		
//	        		jsonResult.put(jsonObjQuestion);
//
//	        		
//	        		
//	             	return jsonResult;
//	             	
//	        		} catch (JSONException e) {
//	        				
//	        			e.printStackTrace();
//	        		}
//	         	    	
//	
//	     	    return null;
//	     	     
//	         }
//	         
//	      });
//		
//	}
//	
//	
//	
//	public void getStudentsbyInstitutionCourseYearPeriod(){
//		
//		get("/students/:institution/:course/:year/:period", new Route() {
//			@Override
//            public Object handle(final Request request, final Response response){
//	        	
//	        	response.header("Access-Control-Allow-Origin", "*");
//	        	 
//	        	
//	        	
//	        	String institution = request.params(":institution");
//	        	String course = request.params(":course");
//	        	Integer year = Integer.parseInt(request.params(":year"));
//	            Integer period = Integer.parseInt(request.params(":period"));
//	            
//	            
//	            try {
//	            	List<Student> students = model.searchStudentsByInstitutionCourseYearPeriod(institution, course, year, period);
//	            	
//	            	JSONArray jsonResult = new JSONArray();
//	         	    
//
//	         	    for(Student student:students){
//	         	    	JSONObject jsonObj = new JSONObject();
//	         	    	jsonObj.put("name", student.getName());
//	         	    	jsonObj.put("username", student.getUserName());
//	         	    	jsonObj.put("ra", student.getRa());
//	         	    	if(student.getCompleted()==false){
//	         	    		jsonObj.put("status", 1);
//	         	    	} else if(student.getCompleted()==true && student.getStatusComment()==false){
//	         	    		jsonObj.put("status", 2);
//	         	    	} else {
//	         	    		jsonObj.put("status", 3);
//	         	    	}
//	         	    	jsonResult.put(jsonObj);
//	         	    	
//	         	    }
//
//	             	
//	             	return jsonResult;
//	             	
//	        		} catch (JSONException e) {
//	        				
//	        			e.printStackTrace();
//	        		}
//	         	    	
//	
//	     	    return null;
//	     	     
//	         }
//	         
//	      });
//
//	         
//	}
//	
//	
//	public void setAnswerbyCode(){
//		
//		get("/answer/:ra/:questionNumber/:answerCode", new Route() {
//			@Override
//            public Object handle(final Request request, final Response response){
//	        	
//	        	response.header("Access-Control-Allow-Origin", "*");
//	        	 
//	        	
//	        	
//	        	Integer ra = Integer.parseInt(request.params(":ra"));
//	        	Integer questionNumber = Integer.parseInt(request.params(":questionNumber"));
//	        	Integer answerCode = Integer.parseInt(request.params(":answerCode"));
//	            
//	            
//	            
//	            try {
//	            	
//	            	int status = model.recordAnswer(ra, questionNumber, answerCode);
//	            	
//	            	if(status == 0){
//	            		JSONArray jsonResult = new JSONArray();
//		         	    
//		         	    JSONObject jsonObj = new JSONObject();
//		         	    	
//		         	    jsonObj.put("status", "ok");
//		         	    jsonResult.put(jsonObj);
//		
//		             	return jsonResult;
//		             	
//	            	} else {
//	            		
//	            		JSONArray jsonResult = new JSONArray();
//		         	    
//		         	    JSONObject jsonObj = new JSONObject();
//		         	    	
//		         	    jsonObj.put("status", "fim");
//		         	    jsonResult.put(jsonObj);
//		
//		             	return jsonResult;
//	            		
//	            	}
//	            	
//	            	
//	             	
//	        		} catch (JSONException e) {
//	        				
//	        			e.printStackTrace();
//	        		}
//	         	    	
//	
//	     	    return null;
//	     	     
//	         }
//	         
//	      });
//
//	         
//	}
//	
//	
//	public void getAllInstitutions(){
//		
//		get("/institutions", new Route() {
//			@Override
//            public Object handle(final Request request, final Response response){
//	        	
//	        	response.header("Access-Control-Allow-Origin", "*");
//
//	        	JSONArray jsonResult = new JSONArray();
//         	    
//         	    
//	        	
//	            try {
//	            	
//	            	List<Institution> institutions = model.getAllInstitutions();
//	            	
//	            	for(Institution institution:institutions){
//	            		
//	            		JSONObject jsonObj = new JSONObject();
//	            		jsonObj.put("institutionName", institution.getInstitutionName());
//	            		jsonResult.put(jsonObj);
//	            		
//	            	}
//	            	
//	            	return jsonResult;
//	            	
//	            	
//	             	
//	        		} catch (JSONException e) {
//	        				
//	        			e.printStackTrace();
//	        		}
//	         	    	
//	
//	     	    return null;
//	     	     
//	         }
//	         
//	      });
//
//	         
//	}
//	
//	
//	public void getCourses(){
//		
//		get("/courses/:institution", new Route() {
//			@Override
//            public Object handle(final Request request, final Response response){
//	        	
//	        	response.header("Access-Control-Allow-Origin", "*");
//
//	        	
//	        	String institutionName = request.params(":institution");
//	        	
//	        	JSONArray jsonResult = new JSONArray();
//         	    
//         	    
//	        	
//	            try {
//	            	
//	            	List<Course> courses = model.getCourses(institutionName);
//	            	
//	            	for(Course course:courses){
//	            		
//	            		JSONObject jsonObj = new JSONObject();
//	            		jsonObj.put("courseName", course.getCourseName());
//	            		jsonResult.put(jsonObj);
//	            		
//	            	}
//	            	
//	            	return jsonResult;
//	            	
//	            	
//	             	
//	        		} catch (JSONException e) {
//	        				
//	        			e.printStackTrace();
//	        		}
//	         	    	
//	
//	     	    return null;
//	     	     
//	         }
//	         
//	      });
//
//	}
//	
//	
//	public void setComments(){
//			
//			post("/comment/", new Route() {
//				@Override
//	            public Object handle(final Request request, final Response response){
//		        	
//		           response.header("Access-Control-Allow-Origin", "*");
//
//		        	
//		        	
//		        	
//		           JSONObject json = new JSONObject(request.body());
//		        	
//		           String comment = json.getString("comment");
//		        	
//		           int ra = Integer.parseInt(json.getString("ra"));
//		        	
//		           
//	         	    
//	         	   try {
//		            	
//		            	boolean status = model.setComment(ra, comment);;
//		            	
//		            	
//		            	
//		            	if(status==true){
//		            		
//		            		
//		            		
//		            		JSONArray jsonResult = new JSONArray();
//		 	         	    JSONObject jsonObj = new JSONObject();
//		     
//			        		jsonObj.put("status", 1);
//			        		
//			        		
//			             	jsonResult.put(jsonObj);
//			             	
//			             	
//			             	
//			             	return jsonResult;
//		            		
//		            	}
//		            	
//		            	
//		             	
//		        		} catch (JSONException e) {
//		        				
//		        			e.printStackTrace();
//		        		}
//	         	    
//	         	    JSONArray jsonResult = new JSONArray();
//	         	    JSONObject jsonObj = new JSONObject();
//	         	   	
//	         	    jsonObj.put("status", 0);
//	        		
//	        		
//	             	jsonResult.put(jsonObj);
//	             	
//	             	return jsonResult;
//	         	   
//	         	   
//		        	
//			   }
//			});     
//
//	         
//	}
//	
//	
//	public void getAllCompetencies(){
//		
//		get("/competencies", new Route() {
//			@Override
//            public Object handle(final Request request, final Response response){
//	        	
//	        	response.header("Access-Control-Allow-Origin", "*");
//
//	        	JSONArray jsonResult = new JSONArray();
//         	    
//         	    
//	        	
//	            try {
//	            	
//	            	List<Competency> competencies = model.getAllCompetencies();
//	            	
//	            	for(Competency competency:competencies){
//	            		
//	            		JSONObject jsonObj = new JSONObject();
//	            		jsonObj.put("competency", competency.getName());
//	            		jsonResult.put(jsonObj);
//	            		
//	            	}
//	            	
//	            	return jsonResult;
//	            	
//	            	
//	             	
//	        		} catch (JSONException e) {
//	        				
//	        			e.printStackTrace();
//	        		}
//	         	    	
//	
//	     	    return null;
//	     	     
//	         }
//	         
//	      });
//
//	         
//	}
//	
//	
//	public void setQuestion(){
//		
//		post("/question/", new Route() {
//			@Override
//            public Object handle(final Request request, final Response response) throws UnsupportedEncodingException{
//	        	
//	           response.header("Access-Control-Allow-Origin", "*");
//
//	           Gson gson = new Gson();
//	           
//	           String data = request.body();
//	           
//	           byte text[] = data.getBytes("ISO-8859-1");
//	           String value = new String(text, "UTF-8");
//	           
//	           
//	        	
//	           Question question = gson.fromJson(value, Question.class);
//	           
//	           
//	           
//         	    
//         	   try {
//	            	
//	            		model.addQuestion(question);
//	            		
//	            		
//	            		
//	            		JSONArray jsonResult = new JSONArray();
//	 	         	    JSONObject jsonObj = new JSONObject();
//	     
//		        		jsonObj.put("status", 1);
//		        		
//		        		
//		             	jsonResult.put(jsonObj);
//		             	
//		             	
//		             	
//		             	return jsonResult;
//	            		
//	            	
//	            	
//	            	
//	             	
//	        		} catch (JSONException e) {
//	        				
//	        			e.printStackTrace();
//	        		}
//         	    
//         	    JSONArray jsonResult = new JSONArray();
//         	    JSONObject jsonObj = new JSONObject();
//         	   	
//         	    jsonObj.put("status", 0);
//        		
//        		
//             	jsonResult.put(jsonObj);
//             	
//             	return jsonResult;
//         	   
//         	   
//	        	
//		   }
//		});     
//
//         
//    }
//	
//	public void getAllQuestions(){
//		
//		
//		
//		get("/questions", new Route() {
//			@Override
//            public Object handle(final Request request, final Response response){
//	        	
//	        	response.header("Access-Control-Allow-Origin", "*");
//
//	        	JSONArray jsonResult = new JSONArray();
//         	    
//         	    
//	        	
//	            try {
//	            	
//	            	List<Question> questions = model.getAllQuestions();
//	            	
//	            	for(Question question:questions){
//	            		
//	            		JSONObject jsonObj = new JSONObject();
//	            		jsonObj.put("number", question.getNumber());
//	            		jsonObj.put("question", question.getQuestion());
//	            		jsonResult.put(jsonObj);
//	            		
//	            	}
//	            	
//	            	return jsonResult;
//	            	
//	            	
//	             	
//	        		} catch (JSONException e) {
//	        				
//	        			e.printStackTrace();
//	        		}
//	         	    	
//	
//	     	    return null;
//	     	     
//	         }
//	         
//	      });
//		
//		
//	}
//	
//	public void deleteQuestion(){
//		
//		post("/question/delete", new Route() {
//			@Override
//            public Object handle(final Request request, final Response response){
//	        	
//	           response.header("Access-Control-Allow-Origin", "*");
//
//	           JSONObject json = new JSONObject(request.body());
//	        	
//	           
//	        	
//	           int number = Integer.parseInt(json.getString("number"));
//	        	
//	           
//	           
//	           
//	           
//         	    
//         	   try {
//	            	
//	            		model.deleteQuestion(number);
//	            		
//	            		
//	            		
//	            		JSONArray jsonResult = new JSONArray();
//	 	         	    JSONObject jsonObj = new JSONObject();
//	     
//		        		jsonObj.put("status", 1);
//		        		
//		        		
//		             	jsonResult.put(jsonObj);
//		             	
//		             	
//		             	
//		             	return jsonResult;
//	            		
//	            	
//	            	
//	            	
//	             	
//	        		} catch (JSONException e) {
//	        				
//	        			e.printStackTrace();
//	        		}
//         	    
//         	    JSONArray jsonResult = new JSONArray();
//         	    JSONObject jsonObj = new JSONObject();
//         	   	
//         	    jsonObj.put("status", 0);
//        		
//        		
//             	jsonResult.put(jsonObj);
//             	
//             	return jsonResult;
//         	   
//         	   
//	        	
//		   }
//		});     
//
//         
//    }
//	
//	public void setStudent(){
//		
//		post("/student", new Route() {
//			@Override
//            public Object handle(final Request request, final Response response){
//	        	
//	           response.header("Access-Control-Allow-Origin", "*");
//
//	           Gson gson = new Gson();
//	           
//	           String json = request.body();
//	           
//	           
//	           
//	           Student student = gson.fromJson(json, Student.class);
//	           
//	           
//	           
//         	    
//         	   try {
//	            	
//	            		boolean status = model.addStudent(student);
//	            		
//	            		if(status){
//	            			
//	            			JSONArray jsonResult = new JSONArray();
//		 	         	    JSONObject jsonObj = new JSONObject();
//		     
//			        		jsonObj.put("status", 1);
//			        		
//			        		
//			             	jsonResult.put(jsonObj);
//			             	
//			             	
//			             	
//			             	return jsonResult;
//	            		}
//	            		
//	            		
//	            		
//	            	
//	            	
//	            	
//	             	
//	        		} catch (JSONException e) {
//	        				
//	        			e.printStackTrace();
//	        		}
//         	    
//         	    JSONArray jsonResult = new JSONArray();
//         	    JSONObject jsonObj = new JSONObject();
//         	   	
//         	    jsonObj.put("status", 0);
//        		
//        		
//             	jsonResult.put(jsonObj);
//             	
//             	return jsonResult;
//         	   
//         	   
//	        	
//		   }
//		});     
//	}
//	
//	
//	
//	
//	public void setNewCompetency(){
//		
//		post("/newcompetency", new Route() {
//			@Override
//            public Object handle(final Request request, final Response response) throws UnsupportedEncodingException{
//	        	
//	           response.header("Access-Control-Allow-Origin", "*");
//
//	           String data = request.body();
//	           byte text[] = data.getBytes("ISO-8859-1");
//	           String value = new String(text, "UTF-8");
//	        	
//	           
//	           
//	           JSONObject json = new JSONObject(value);
//	        	
//	           String newCompetency = json.getString("competency");
//	           
//	           
//	           	
//	           
//         	    
//	           try {
//	        	   
//	        	   boolean status = model.addCompetency(new Competency(newCompetency));
//           		
//           			if(status){
//           			
//           				JSONArray jsonResult = new JSONArray();
//	 	         	    JSONObject jsonObj = new JSONObject();
//	     
//		        		jsonObj.put("status", 1);
//		        		
//		        		
//		             	jsonResult.put(jsonObj);
//		             	
//		             	
//		             	
//		             	return jsonResult;
//           			}
//	            	
//	            	
//	             	
//	        		} catch (JSONException e) {
//	        				
//	        			//e.printStackTrace();
//
//	        		}
//	         	    	
//	
//	            JSONArray jsonResult = new JSONArray();
//        	    JSONObject jsonObj = new JSONObject();
//
//        	    jsonObj.put("status", 0);
//       		
//       		
//            	jsonResult.put(jsonObj);
//            	
//            	return jsonResult;
//         	   
//         	   
//	        	
//		   }
//		});     
//
//         
//	}
//	
//	public void deleteCompetency(){
//		
//		post("/competency/delete", new Route() {
//			@Override
//            public Object handle(final Request request, final Response response) throws UnsupportedEncodingException{
//	        	
//	           response.header("Access-Control-Allow-Origin", "*");
//
//	           String data = request.body();
//	           
//	           byte text[] = data.getBytes("ISO-8859-1");
//	           String value = new String(text, "UTF-8");
//	           
//	           JSONObject json = new JSONObject(value);
//	        	
//	           
//	        	
//	           String competencyName = json.getString("competency").replaceAll("%20", " ");
//	        
//	           
//	           
//         	    
//         	   try {
//	            	
//	            		model.deleteCompetency(competencyName);
//	            		
//	            		
//	            		
//	            		JSONArray jsonResult = new JSONArray();
//	 	         	    JSONObject jsonObj = new JSONObject();
//	     
//		        		jsonObj.put("status", 1);
//		        		
//		        		
//		             	jsonResult.put(jsonObj);
//		             	
//		             	
//		             	
//		             	return jsonResult;
//	            		
//	            	
//	            	
//	            	
//	             	
//	        		} catch (JSONException e) {
//	        				
//	        			e.printStackTrace();
//	        		}
//         	    
//         	    JSONArray jsonResult = new JSONArray();
//         	    JSONObject jsonObj = new JSONObject();
//         	   	
//         	    jsonObj.put("status", 0);
//        		
//        		
//             	jsonResult.put(jsonObj);
//             	
//             	return jsonResult;
//         	   
//         	   
//	        	
//		   }
//		});     
//		
//	}
//	
//	public void setNewCourse(){
//			
//			post("/newcourse", new Route() {
//				@Override
//	            public Object handle(final Request request, final Response response) throws UnsupportedEncodingException{
//		        	
//		           response.header("Access-Control-Allow-Origin", "*");
//
//		           String data = request.body();
//		           byte text[] = data.getBytes("ISO-8859-1");
//		           String value = new String(text, "UTF-8");
//		        	
//		           
//		           
//		           JSONObject json = new JSONObject(value);
//		        	
//		           String newCourse = json.getString("course");
//		           String institution = json.getString("institution");
//		           
//		           	
//		           
//	         	    
//		           try {
//		        	   
//		        	   boolean status = model.addCourse(institution, newCourse);
//	           		
//	           			if(status){
//	           			
//	           				JSONArray jsonResult = new JSONArray();
//		 	         	    JSONObject jsonObj = new JSONObject();
//		     
//			        		jsonObj.put("status", 1);
//			        		
//			        		
//			             	jsonResult.put(jsonObj);
//			             	
//			             	
//			             	
//			             	return jsonResult;
//	           			}
//		            	
//		            	
//		             	
//		        		} catch (JSONException e) {
//		        				
//		        			//e.printStackTrace();
//
//		        		}
//		         	    	
//		
//		            JSONArray jsonResult = new JSONArray();
//	        	    JSONObject jsonObj = new JSONObject();
//
//	        	    jsonObj.put("status", 0);
//	       		
//	       		
//	            	jsonResult.put(jsonObj);
//	            	
//	            	return jsonResult;
//	         	   
//	         	   
//		        	
//			   }
//			});     
//
//	         
//		}
//		
//		public void deleteCourse(){
//			
//			post("/course/delete", new Route() {
//				@Override
//	            public Object handle(final Request request, final Response response) throws UnsupportedEncodingException{
//		        	
//		           response.header("Access-Control-Allow-Origin", "*");
//
//		           String data = request.body();
//		           
//		           byte text[] = data.getBytes("ISO-8859-1");
//		           String value = new String(text, "UTF-8");
//		           
//		           JSONObject json = new JSONObject(value);
//		        	
//		           
//		        	
//		           String courseName = json.getString("course");
//		           String institution = json.getString("institution");
//		           
//		           
//	         	    
//	         	   try {
//		            	
//		            		model.deleteCourse(courseName, institution);
//		            		
//		            		
//		            		
//		            		JSONArray jsonResult = new JSONArray();
//		 	         	    JSONObject jsonObj = new JSONObject();
//		     
//			        		jsonObj.put("status", 1);
//			        		
//			        		
//			             	jsonResult.put(jsonObj);
//			             	
//			             	
//			             	
//			             	return jsonResult;
//		            		
//		            	
//		            	
//		            	
//		             	
//		        		} catch (JSONException e) {
//		        				
//		        			e.printStackTrace();
//		        		}
//	         	    
//	         	    JSONArray jsonResult = new JSONArray();
//	         	    JSONObject jsonObj = new JSONObject();
//	         	   	
//	         	    jsonObj.put("status", 0);
//	        		
//	        		
//	             	jsonResult.put(jsonObj);
//	             	
//	             	return jsonResult;
//	         	   
//	         	   
//		        	
//			   }
//			});     
//
//         
//    }
//		
//		
//		public void setPsychologist(){
//			
//			post("/psychologist", new Route() {
//				@Override
//	            public Object handle(final Request request, final Response response){
//		        	
//		           response.header("Access-Control-Allow-Origin", "*");
//
//		           Gson gson = new Gson();
//		           
//		           String json = request.body();
//		           
//		           
//		           
//		           Psychologist psychologist = gson.fromJson(json, Psychologist.class);
//		           
//		           
//		           
//	         	    
//	         	   try {
//		            	
//		            		boolean status = model.addPsychologist(psychologist);
//		            		
//		            		if(status){
//		            			
//		            			JSONArray jsonResult = new JSONArray();
//			 	         	    JSONObject jsonObj = new JSONObject();
//			     
//				        		jsonObj.put("status", 1);
//				        		
//				        		
//				             	jsonResult.put(jsonObj);
//				             	
//				             	
//				             	
//				             	return jsonResult;
//		            		}
//		            		
//		            		
//		            		
//		            	
//		            	
//		            	
//		             	
//		        		} catch (JSONException e) {
//		        				
//		        			e.printStackTrace();
//		        		}
//	         	    
//	         	    JSONArray jsonResult = new JSONArray();
//	         	    JSONObject jsonObj = new JSONObject();
//	         	   	
//	         	    jsonObj.put("status", 0);
//	        		
//	        		
//	             	jsonResult.put(jsonObj);
//	             	
//	             	return jsonResult;
//	         	   
//	         	   
//		        	
//			   }
//			});     
//		}
//		
//		public void setADM(){
//			
//			post("/adm", new Route() {
//				@Override
//	            public Object handle(final Request request, final Response response){
//		        	
//		           response.header("Access-Control-Allow-Origin", "*");
//
//		           Gson gson = new Gson();
//		           
//		           String json = request.body();
//		           
//		           
//		           
//		           ADM adm = gson.fromJson(json, ADM.class);
//		           
//		           
//		           
//	         	    
//	         	   try {
//		            	
//		            		boolean status = model.addADM(adm);
//		            		
//		            		if(status){
//		            			
//		            			JSONArray jsonResult = new JSONArray();
//			 	         	    JSONObject jsonObj = new JSONObject();
//			     
//				        		jsonObj.put("status", 1);
//				        		
//				        		
//				             	jsonResult.put(jsonObj);
//				             	
//				             	
//				             	
//				             	return jsonResult;
//		            		}
//		            		
//		            		
//		            		
//		            	
//		            	
//		            	
//		             	
//		        		} catch (JSONException e) {
//		        				
//		        			e.printStackTrace();
//		        		}
//	         	    
//	         	    JSONArray jsonResult = new JSONArray();
//	         	    JSONObject jsonObj = new JSONObject();
//	         	   	
//	         	    jsonObj.put("status", 0);
//	        		
//	        		
//	             	jsonResult.put(jsonObj);
//	             	
//	             	return jsonResult;
//	         	   
//	         	   
//		        	
//			   }
//			});     
//		}
		

	
}
