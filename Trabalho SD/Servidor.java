package TrabalhoSD;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends Thread {

    public static void main(String args[]) {
        ServerSocket s = null;
        try {
            s = new ServerSocket(12345);
            while (true) {

                Socket conexao = s.accept();

                Thread t = new Servidor(conexao);
                t.start();
            }
        } catch (IOException e) {
            System.out.println("IOException " + e);
        }
    }

    private Socket conexao;

    public Servidor(Socket s) {
        conexao = s;
    }

    public void run() {
        try {

            System.out.println("Nova conexão com o cliente " + conexao.getInetAddress().getHostAddress());

            DataInputStream entrada = new DataInputStream(conexao.getInputStream());
            Testador testador = new Testador();

            String t = entrada.readUTF();
            int qtLinhas = entrada.readInt();
            String[][] respostas = testador.LeituraRespostas(t, qtLinhas);

            String gabarito = testador.Read("D:/Gabarito.txt");
            int gabaLinhas = testador.QtdLinhas("D:/Gabarito.txt");
            String[][] gabaritoVetor = testador.LeituraRespostas(gabarito, gabaLinhas);
            testador.VerificarRespostas(respostas, gabaritoVetor);

            entrada.close();
            conexao.close();


        } catch (IOException e) {
            System.out.println("IOException " + e);
        }
    }
}