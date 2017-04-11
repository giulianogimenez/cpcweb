package hello;

import static spark.Spark.*;

import java.util.LinkedList;
import java.util.List;

import br.edu.fatecsjc.lab3.model.Bandeira;
import br.edu.fatecsjc.lab3.model.Estabelecimento;

public class MainServer {

	static Model model;

    public static void main(String[] args) {

    	model = model.getInstance();
    	REST controller = new REST(model); 
		// Get port config of heroku on environment variable
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }
        
        port(port);
        
        initializeModel();
        
		staticFileLocation("/static");

		controller.listarProcuraEstabelecimento();
		controller.addEstabelecimento();
		
    }
	
    public static void initializeModel(){
		
	}
	
}
