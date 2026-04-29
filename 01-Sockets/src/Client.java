import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    Servidor servidor = new Servidor();
    
    private final int PORT = servidor.PORT;
    private final String HOST = servidor.HOST;

    private Socket socket;
    private PrintWriter out;

    private void connecta() throws UnknownHostException, IOException {
        socket = new Socket(HOST, PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        System.out.printf("Connectat a servidor en %s:%d%n", HOST, PORT);
    }

    private void tanca() throws IOException {
        socket.close();
        out.close();
        System.out.println("Client tancat");
    }

    private void envia(String msg) {
        out.println(msg);
        System.out.println("Enviat al servidor: " + msg);
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        Client client = new Client();
        client.connecta();
        client.envia("Prova d'enviament 1");
        client.envia("Prova d'enviament 2");
        client.envia("Adeu!");

        System.out.println("Prem Enter per tancar el client...");
        System.in.read();
        client.tanca();
    }
}