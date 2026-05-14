import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorXat {
    public final int PORT = 9999;
    public final String HOST = "localhost";
    public static final String MSG_SORTIR = "sortir";

    private ServerSocket serverSocket;

    private void iniciarServidor() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.printf("Servidor iniciat a %s:%d%n", HOST, PORT);
    }

    private void pararServidor() throws IOException {
        serverSocket.close();
        System.out.println("Servidor aturat.");
    }

    private String getNom(ObjectInputStream ois, ObjectOutputStream oos) throws IOException, ClassNotFoundException {
        oos.writeObject("Escriu el teu nom:");
        oos.flush();
        return (String) ois.readObject();
    }

    public static void main(String[] args) throws Exception {
        ServidorXat servidor = new ServidorXat();
        servidor.iniciarServidor();

        Socket clientSocket = servidor.serverSocket.accept();
        System.out.println("Client connectat: " + clientSocket.getInetAddress());

        ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

        String nom = servidor.getNom(ois, oos);
        System.out.println("Nom rebut: " + nom);

        FilServidorXat fil = new FilServidorXat(ois);
        System.out.println("Fil de xat creat.");
        fil.start();
        System.out.printf("Fil de %s iniciat%n", nom);

        BufferedReader teclat = new BufferedReader(new InputStreamReader(System.in));
        String missatge;
        do {
            System.out.print("Missatge ('sortir' per tancar): ");
            missatge = teclat.readLine();
            oos.writeObject(missatge);
            oos.flush();
        } while (!missatge.equals(MSG_SORTIR));

        fil.join();
        clientSocket.close();
        servidor.pararServidor();
    }
}
