import java.io.IOException;
import java.io.ObjectInputStream;

public class FilServidorXat extends Thread {
    private ObjectInputStream ois;

    public FilServidorXat(ObjectInputStream ois) {
        this.ois = ois;
    }

    @Override
    public void run() {
        try {
            String missatge;
            do {
                missatge = (String) ois.readObject();
                System.out.println("Rebut: " + missatge);
            } while (!missatge.equals(ServidorXat.MSG_SORTIR));
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connexió tancada.");
        }
        System.out.println("Fil de xat finalitzat.");
    }
}
