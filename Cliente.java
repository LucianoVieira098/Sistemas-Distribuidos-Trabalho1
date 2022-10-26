
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class Cliente implements Runnable {

	private Socket cliente;

	public Cliente(Socket cliente) {
		this.cliente = cliente;
	}

	public static void main(String args[]) throws UnknownHostException, IOException {


		Socket socket = new Socket("127.0.0.1", 12345);


		Cliente c = new Cliente(socket);
		Thread t = new Thread(c);
		t.start();
	}

	public void run() {
		try {
			
			System.out.println("O cliente conectou ao servidor");

			PrintStream saida = new PrintStream(cliente.getOutputStream());

			FileInputStream arquivo=new FileInputStream("abc.txt");    
		  	int i=arquivo.read();  
			while(i!=-1){
				
		    		saida.print((char)i);
				i=arquivo.read();
			} 
  
            		arquivo.close();  


			saida.close();

			cliente.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
