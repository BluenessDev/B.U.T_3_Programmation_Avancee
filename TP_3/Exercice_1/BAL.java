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

    public synchronized void write(String lettre) throws InterruptedException {
        while (true) {
            for (int i = 0; i < taille; i++) {
                if (!etat.get(i)) {
                    buffer.set(i, lettre);
                    etat.set(i, true);
                    notifyAll();
                    return;
                }
            }
            wait();
        }
    }

    public synchronized String read() throws InterruptedException {
        while (true) {
            for (int i = 0; i < taille; i++) {
                if (etat.get(i)) {
                    String lettre = buffer.get(i);
                    buffer.set(i, "");
                    etat.set(i, false);
                    notifyAll();
                    return lettre;
                }
            }
            wait();
        }
    }
}