package TP_2;

import static java.lang.Thread.sleep;

class NewAffichage extends Thread {
    String texte;

    static SemaphoreBinaire sem = new SemaphoreBinaire(1);

    public NewAffichage (String txt){texte=txt;}

    public void run(){

        System.out.println("\n J'entre dans la section critique :" + texte + "\n");
        sem.syncWait();
        for (int i=0; i<texte.length(); i++){
            System.out.print(texte.charAt(i));
            try {sleep(100);} catch(InterruptedException e){};
        }
        sem.syncSignal();
        System.out.println("\n Je sors de la section critique" + texte + "\n");
    }
}
