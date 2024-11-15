package TP_3.Exercice_1;

public class Consommateur implements Runnable {
    private final BAL bal;
    private final int nbMessages;

    public Consommateur(BAL bal, int nbMessages) {
        this.bal = bal;
        this.nbMessages = nbMessages;
    }

    @Override
    public void run() {
        try {
            while (true) {
                for (int i = 0; i < nbMessages; i++) {
                    String lettre = bal.read(i);
                    System.out.println("Consommé: " + lettre + " du buffer " + i);
                    if (lettre.equals("Q")) {
                        return; // Arrêter si 'Q' est lu
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
