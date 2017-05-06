package test;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import br.edu.fatecsjc.lab3.model.Estabelecimento;
import hello.*;

public class Teste {

	public static void main(String[] args) throws UnsupportedEncodingException {
		
		for(Estabelecimento e : new Model().listEstabelecimentos()){
			String str = e.getNome();

			Charset utf8charset = Charset.forName("UTF-8");
			Charset iso88591charset = Charset.forName("ISO-8859-1");

			ByteBuffer inputBuffer = ByteBuffer.wrap(str.getBytes());

			// decode UTF-8
			CharBuffer data = utf8charset.decode(inputBuffer);

			// encode ISO-8559-1
			ByteBuffer outputBuffer = iso88591charset.encode(data);
			byte[] outputData = outputBuffer.array();
			
			str = new String(outputData);
			
			System.out.println(str);
		}
	}
}