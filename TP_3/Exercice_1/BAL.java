package TP_3.Exercice_1;

import java.util.ArrayList;

public class BAL {
    private final ArrayList<String> buffer;
    private final ArrayList<Boolean> etat;
    private final int taille;

    public BAL(int nbMessages) {
        this.taille = nbMessages;
        this.buffer = new ArrayList<>();
        this.etat = new ArrayList<>();

        for (int i = 0; i < nbMessages; i++) {
            buffer.add("");
            etat.add(false);
        }
    }

    public synchronized void write(int index, String lettre) throws InterruptedException {
        while (etat.get(index)) {
            wait();
        }
        buffer.set(index, lettre);
        etat.set(index, true);
        notifyAll();
    }

    public synchronized String read(int index) throws InterruptedException {
        while (!etat.get(index)) {
            wait();
        }
        String lettre = buffer.get(index);
        buffer.set(index, "");
        etat.set(index, false);
        notifyAll();
        return lettre;
    }
}
