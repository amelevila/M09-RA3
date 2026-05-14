import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientXat {
    ServidorXat servidor = new ServidorXat();

    private final int PORT = servidor.PORT;
    private final String HOST = servidor.HOST;

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private void connecta() throws UnknownHostException, IOException {
        socket = new Socket(HOST, PORT);
        System.out.printf("Client connectat a %s:%d%n", HOST, PORT);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
        System.out.println("Flux d'entrada i sortida creat.");
    }

    private void enviarMissatge(String missatge) throws IOException {
        System.out.println("Enviant missatge: " + missatge);
        oos.writeObject(missatge);
        oos.flush();
    }

    private void tancarClient() throws IOException {
        socket.close();
        System.out.println("Client tancat.");
    }

    public static void main(String[] args) throws Exception {
        ClientXat client = new ClientXat();
        client.connecta();

        FilLectorCX fil = new FilLectorCX(client.ois);

        BufferedReader teclat = new BufferedReader(new InputStreamReader(System.in));
        String missatge;
        System.out.print("Missatge ('sortir' per tancar): ");
        fil.start();
        do {
            missatge = teclat.readLine();
            client.enviarMissatge(missatge);
            if (!missatge.equals(ServidorXat.MSG_SORTIR)) {
                System.out.print("Missatge ('sortir' per tancar): ");
            }
        } while (!missatge.equals(ServidorXat.MSG_SORTIR));

        System.out.println("Tancant client...");
        teclat.close();
        client.tancarClient();
    }
}
