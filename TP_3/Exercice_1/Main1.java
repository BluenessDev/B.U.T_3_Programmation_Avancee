package TP_3.Exercice_1;

public class Main1 {
    public static void main(String[] args) {
        int nbMessages = 5; // Nombre de buffers disponibles
        BAL bal = new BAL(nbMessages);

        Thread producteur = new Thread(new Producteur(bal, nbMessages));
        Thread consommateur = new Thread(new Consommateur(bal, nbMessages));

        producteur.start();
        consommateur.start();

        try {
            producteur.join();
            consommateur.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Fin du programme.");
    }
}
