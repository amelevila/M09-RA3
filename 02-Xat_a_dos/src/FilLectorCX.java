import java.io.IOException;
import java.io.ObjectInputStream;

public class FilLectorCX extends Thread {
    private ObjectInputStream ois;

    public FilLectorCX(ObjectInputStream ois) {
        this.ois = ois;
    }

    @Override
    public void run() {
        System.out.println("Fil de lectura iniciat");
        try {
            String missatge;
            while (true) {
                missatge = (String) ois.readObject();
                System.out.println("Rebut: " + missatge);
                if (missatge.equals(ServidorXat.MSG_SORTIR)) break;
            }
        } catch (IOException e) {
            System.out.println("El servidor ha tancat la connexió.");
        } catch (ClassNotFoundException e) {
            System.out.println("Error de classe: " + e.getMessage());
        }
    }
}
