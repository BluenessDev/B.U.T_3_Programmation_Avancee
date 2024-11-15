package TP_3.Exercice_1;

public class Main {
    public static void main(String[] args) {
        int nbMessages = 5; // Nombre de buffers disponibles
        int tailleMessage = 10; // Taille maximale d'un message
        BAL bal = new BAL(nbMessages, tailleMessage);

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
