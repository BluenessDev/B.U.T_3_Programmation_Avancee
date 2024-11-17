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
            String alphabet = "ABCDEFGH"; // Lettres à produire
            for (int i = 0; i < alphabet.length(); i++) {
                String lettre = String.valueOf(alphabet.charAt(i));
                bal.write(lettre); // Écriture dans le buffer
                System.out.println("Produit: " + lettre + " dans le buffer");
                Thread.sleep(500); // Simulation de temps de production
            }
            bal.write("Q"); // Écrit la lettre de fin
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
