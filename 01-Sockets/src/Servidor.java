import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public final int PORT = 7777;
    public final String HOST = "localhost";

    private ServerSocket srvSocket;
    private Socket clientSocket;

    private void connecta() throws IOException {
        System.out.printf("Servidor en marxa a %s:%d%n", HOST, PORT);
        srvSocket = new ServerSocket(PORT);
        System.out.printf("Esperant connexions a %s:%d%n", HOST, PORT);
        clientSocket = srvSocket.accept();
        System.out.println("Client connectat: /127.0.0.1");
    }

    private void repDades() throws IOException {
        BufferedReader entrada = new BufferedReader(
            new InputStreamReader(clientSocket.getInputStream())
        );

        String linia;
        while ((linia = entrada.readLine()) != null) {
            System.out.println("Rebut: " + linia);
        }

        entrada.close();
    }

    private void tanca() throws IOException {
        srvSocket.close();
        clientSocket.close();
        System.out.println("Servidor tancat.");
    } 

    public static void main(String[] args) throws IOException {
        Servidor servidor = new Servidor();
        servidor.connecta();
        servidor.repDades();
        servidor.tanca();
    }
}