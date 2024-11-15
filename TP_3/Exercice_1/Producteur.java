package TP_3.Exercice_1;

public class Producteur implements Runnable {
    private final BAL bal;
    private final int nbMessages;

    public Producteur(BAL bal, int nbMessages) {
        this.bal = bal;
        this.nbMessages = nbMessages;
    }

    @Override
    public void run() {
        try {
            String alphabet = "ABCDEFGH";
            for (int i = 0; i < alphabet.length(); i++) {
                char lettre = alphabet.charAt(i);
                bal.write(i % nbMessages, String.valueOf(lettre));
                System.out.println("Produit: " + lettre + " dans le buffer " + (i % nbMessages));
                Thread.sleep(500); // Simulation de temps de production
            }
            bal.write(0, "Q"); // Indique la fin
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
