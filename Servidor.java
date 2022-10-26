import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.io.FileInputStream;
import java.net.Socket;
import java.util.Scanner;

public class Servidor implements Runnable {
	public Socket cliente;

	public Servidor(Socket cliente) {
		this.cliente = cliente;
	}

	public static void main(String[] args) throws IOException {


		ServerSocket servidor = new ServerSocket(12345);
		System.out.println("Porta 12345 aberta!");

		System.out.println("Aguardando conexão do cliente...");

		while (true) {

			Socket cliente = servidor.accept();

			Servidor tratamento = new Servidor(cliente);

			Thread t = new Thread(tratamento);

			t.start();
		}
	}


	public void run() {
		System.out.println("Nova conexao com o cliente " + this.cliente.getInetAddress().getHostAddress());

		Scanner entrada = null;
		try {
			entrada = new Scanner(cliente.getInputStream());
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		String nomeDoArquivo="gabarito.txt";
		File arq = new File(nomeDoArquivo);
		Scanner inputStream = null;
		try {
			inputStream = new Scanner (arq);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		int errada=0, certa=0; char c; char g;
		while(inputStream.hasNextLine()&&entrada.hasNextLine()){
			String gab=inputStream.nextLine();
			String cli=entrada.nextLine();
			String cliente2[]=cli.split(";");
			String gabarito[]=gab.split(";");
			for (int i=1; i<gabarito.length;i++){
				for (int j=0; j<cliente2[i].length(); j++) {
  					  c = cliente2[i].charAt(j);
					  g = gabarito[i].charAt(j);
					  if(c==g) certa++;
					  else errada++;
}
			}
			
		}

		File arquivo = new File("meu1234.txt");

		PrintWriter fo = null;
		try {
			fo = new PrintWriter(arquivo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

			fo.println(certa);
			
		fo.flush();

		System.out.print("Dados recebidos com sucesso e gravados em: " + arquivo.getAbsolutePath());

		fo.close();
		entrada.close();

			try {
				this.cliente.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
}
