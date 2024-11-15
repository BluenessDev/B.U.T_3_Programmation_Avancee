package TP_3.Exercice_2;

import javax.swing.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class TamponCirculaire {
    private final char[] tampon;
    private int tete = 0, queue = 0, nbLettres = 0, taille;
    private final Lock lock = new ReentrantLock();
    private final Condition nonVide = lock.newCondition();
    private final Condition nonPlein = lock.newCondition();

    private final JTextArea affichage;

    public TamponCirculaire(int taille, JTextArea affichage) {
        this.taille = taille;
        this.tampon = new char[taille];
        this.affichage = affichage;
    }

    public void produire(char lettre) throws InterruptedException {
        lock.lock();
        try {
            while (nbLettres == taille) nonPlein.await();
            tampon[queue] = lettre;
            queue = (queue + 1) % taille;
            nbLettres++;
            afficherEtat("Produit: " + lettre);
            nonVide.signal();
        } finally {
            lock.unlock();
        }
    }

    public char consommer() throws InterruptedException {
        lock.lock();
        try {
            while (nbLettres == 0) nonVide.await();
            char lettre = tampon[tete];
            tete = (tete + 1) % taille;
            nbLettres--;
            afficherEtat("Consommé: " + lettre);
            nonPlein.signal();
            return lettre;
        } finally {
            lock.unlock();
        }
    }

    private void afficherEtat(String action) {
        SwingUtilities.invokeLater(() -> {
            StringBuilder etat = new StringBuilder(action + "\nÉtat du tampon: [");
            for (int i = 0; i < taille; i++) {
                if (i == tete && i == queue && nbLettres != 0) etat.append("TQ ");
                else if (i == tete) etat.append("T ");
                else if (i == queue) etat.append("Q ");
                etat.append(tampon[i] == '\u0000' ? "_ " : tampon[i] + " ");
            }
            etat.append("]\n");
            affichage.append(etat.toString());
        });
    }
}

