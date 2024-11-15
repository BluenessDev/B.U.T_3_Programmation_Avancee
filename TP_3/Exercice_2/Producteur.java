package TP_3.Exercice_2;

class Producteur implements Runnable {
    private final TamponCirculaire tampon;

    public Producteur(TamponCirculaire tampon) {
        this.tampon = tampon;
    }

    @Override
    public void run() {
        try {
            String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ*";
            for (char lettre : alphabet.toCharArray()) {
                Thread.sleep((long) (Math.random() * 500)); // Simulation de production
                tampon.produire(lettre);
                System.out.println("Produit: " + lettre);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
