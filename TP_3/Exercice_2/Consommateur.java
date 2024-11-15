package TP_3.Exercice_2;

class Consommateur implements Runnable {
    private final TamponCirculaire tampon;

    public Consommateur(TamponCirculaire tampon) {
        this.tampon = tampon;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep((long) (Math.random() * 500)); // Simulation de consommation
                char lettre = tampon.consommer();
                System.out.println("Consommé: " + lettre);
                if (lettre == '*') { // Arrêt lorsque '*' est consommé
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
