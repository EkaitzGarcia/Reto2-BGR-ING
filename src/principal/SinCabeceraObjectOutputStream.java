package principal;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class SinCabeceraObjectOutputStream extends ObjectOutputStream{
	
	//Sobrescribimos el método que crea la cabecera 
	protected void writeStreamHeader() throws IOException {
	 reset();	
 }

	//Constructores
public SinCabeceraObjectOutputStream () throws IOException{ 
	super();
	}
	public SinCabeceraObjectOutputStream(OutputStream out) throws IOException{
	super(out);
	}
}
