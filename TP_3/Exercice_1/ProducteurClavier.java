package TP_3.Exercice_1;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class ProducteurClavier implements Runnable {
    private final BAL bal;
    private final int nbMessages;

    public ProducteurClavier(BAL bal, int nbMessages) {
        this.bal = bal;
        this.nbMessages = nbMessages;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                sleep((long) (Math.random() * 500));
                System.out.print("Entrez une lettre (ou 'Q' pour quitter) : ");
                String lettre = scanner.nextLine();
                if (lettre.length() != 1) {
                    System.out.println("Veuillez entrer une seule lettre.");
                    continue;
                }
                bal.write(lettre); // Écriture dans le buffer
                System.out.println("Produit: " + lettre + " dans le buffer");
                if ("Q".equals(lettre)) { // Arrêt si 'Q' est saisi
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            scanner.close();
        }
    }
}
