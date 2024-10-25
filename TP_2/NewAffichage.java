package TP_2;

import static java.lang.Thread.sleep;

class NewAffichage extends Thread {
    String texte;

    public NewAffichage(String txt) {
        texte = txt;
    }

    public void run() {
        for (int i = 0; i < texte.length(); i++) {
            System.out.print(texte.charAt(i));
            try {
                sleep(100); // Pause pour contrôler la vitesse d'affichage
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
