package TrabalhoSD;

import java.io.*;
import java.net.Socket;


public class Cliente extends Thread {
    public static void main(String args[]) {
        try {
            Socket conexao = new Socket("localhost", 12345);

            Thread t = new Cliente(conexao);
            t.start();

        } catch (IOException e) {
            System.out.println("IOException" + e);
        }
    }

    private Socket conexao;

    public Cliente(Socket s) {
        conexao = s;
    }

    public void run() {
        try {

            System.out.println("O cliente se conectou ao servidor!");

            DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
            Testador testador = new Testador();
            String t = "D:/Prova.txt";

            t = testador.Read("C:/Prova.txt");
            int v = testador.QtdLinhas("C:/Prova.txt");
            saida.writeUTF(t);
            saida.writeInt(v);

            saida.close();

        } catch (IOException e) {
            System.out.println("IOException" + e);
        } finally {
            try {
                conexao.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
