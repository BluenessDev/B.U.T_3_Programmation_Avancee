package TP_3.Exercice_2;

import javax.swing.*;

class TamponCirculaire {
    private final char[] tampon;
    private int tete = 0, queue = 0, nbLettres = 0, taille;
    private final JTextArea affichage;

    public TamponCirculaire(int taille, JTextArea affichage) {
        this.taille = taille;
        this.tampon = new char[taille];
        this.affichage = affichage;
    }

    public synchronized void produire(char lettre) throws InterruptedException {
        while (nbLettres == taille) {
            afficherEtat("Boîte aux lettres saturée, en attente...");
            wait();
        }
        tampon[queue] = lettre;
        queue = (queue + 1) % taille;
        nbLettres++;
        afficherEtat("Produit: " + lettre);
        notifyAll();
    }

    public synchronized char consommer() throws InterruptedException {
        while (nbLettres == 0) {
            afficherEtat("Boîte aux lettres vide, en attente...");
            wait();
        }
        char lettre = tampon[tete];
        tete = (tete + 1) % taille;
        nbLettres--;
        afficherEtat("Consommé: " + lettre);
        notifyAll();
        return lettre;
    }

    private void afficherEtat(String action) {
        SwingUtilities.invokeLater(() -> {
            StringBuilder etat = new StringBuilder(action + "\nÉtat du tampon: [");
            for (int i = 0; i < taille; i++) {
                if (i == tete && i == queue && nbLettres != 0) etat.append("TQ ");
                else if (i == tete) etat.append("Tete ");
                else if (i == queue) etat.append("Queue ");
                etat.append(tampon[i] == '\u0000' ? "_ " : tampon[i] + " ");
            }
            etat.append("]\n");
            affichage.append(etat.toString());
        });
    }
}