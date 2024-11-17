package TP_3.Exercice_2;

import javax.swing.*;
import java.awt.*;

public class InterfaceBAL {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Simulateur Tampon Circulaire - Producteur et Consommateur");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JTextArea affichage = new JTextArea();
        affichage.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(affichage);

        JButton startButton = new JButton("Démarrer");
        JButton stopButton = new JButton("Arrêter");
        stopButton.setEnabled(false);

        JPanel panel = new JPanel();
        panel.add(startButton);
        panel.add(stopButton);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);

        final int tailleTampon = 5;
        TamponCirculaire tampon = new TamponCirculaire(tailleTampon, affichage);

        Thread[] threads = new Thread[2];

        startButton.addActionListener(e -> {
            threads[0] = new Thread(new Producteur(tampon));
            threads[1] = new Thread(new Consommateur(tampon));

            threads[0].start();
            threads[1].start();

            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        });

        stopButton.addActionListener(e -> {
            for (Thread thread : threads) {
                if (thread != null) thread.interrupt();
            }
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        });

        frame.setVisible(true);
    }
}
