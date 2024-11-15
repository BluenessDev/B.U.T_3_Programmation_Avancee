package TP_3.Exercice_1;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class BAL {
    private final Buffer[] buffer;
    private final ByteBuffer espaceMemoire;
    private final boolean[] etat;

    public BAL(int nbMessages, int tailleMessage) {
        buffer = new Buffer[nbMessages];
        espaceMemoire = ByteBuffer.allocate(nbMessages * tailleMessage);
        etat = new boolean[nbMessages];
        for (int i = 0; i < nbMessages; i++) {
            ByteBuffer slice = espaceMemoire.slice();
            slice.limit(tailleMessage);
            buffer[i] = slice;
            espaceMemoire.position(espaceMemoire.position() + tailleMessage);
        }
    }

    public synchronized void write(int i, String chLettreDeposee) throws InterruptedException {
        while (etat[i]) {
            wait();
        }
        ByteBuffer byteBuffer = (ByteBuffer) buffer[i];
        byteBuffer.clear();
        byteBuffer.put(chLettreDeposee.getBytes());
        byteBuffer.flip();
        etat[i] = true;
        System.out.println("Écrit dans le buffer " + i + ": " + chLettreDeposee); // Log de debug
        notifyAll();
    }


    public synchronized String read(int i) throws InterruptedException {
        while (!etat[i]) {
            wait();
        }
        ByteBuffer byteBuffer = (ByteBuffer) buffer[i];
        byteBuffer.flip();
        byte[] data = new byte[byteBuffer.remaining()];
        byteBuffer.get(data);
        byteBuffer.clear();
        etat[i] = false;
        String lettre = new String(data);
        notifyAll();
        return lettre;
    }

}
