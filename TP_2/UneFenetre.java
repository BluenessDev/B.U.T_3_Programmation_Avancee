package TP_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class UneFenetre extends JFrame {
    TP_2.UnMobile sonMobile1, sonMobile2, sonMobile3, sonMobile4;
    private final int LARG = 800, HAUT = 150;
    private Thread telThread1, telThread2, telThread3, telThread4;
    private boolean isRunning1 = true, isRunning2 = true, isRunning3 = true, isRunning4 = true;

    public UneFenetre() {
        super("Mobile");
        Container telConteneur = getContentPane();
        telConteneur.setLayout(new GridLayout(4, 1));

        sonMobile1 = new UnMobile(LARG, HAUT);
        JButton sonBouton1 = new JButton("Start/Stop");
        sonBouton1.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 if (isRunning1) {
                     telThread1.suspend();
                 } else {
                     telThread1.resume();
                 }
                 isRunning1 = !isRunning1;
             }
        });

        sonMobile2 = new UnMobile(LARG, HAUT);
        JButton sonBouton2 = new JButton("Start/Stop");
        sonBouton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning2) {
                    telThread2.suspend();
                } else {
                    telThread2.resume();
                }
                isRunning2 = !isRunning2;
            }
        });

        sonMobile3 = new UnMobile(LARG, HAUT);
        sonMobile4 = new UnMobile(LARG, HAUT);

        //telConteneur.add(sonBouton1);
        telConteneur.add(sonMobile1);
        //telConteneur.add(sonBouton2);
        telConteneur.add(sonMobile2);
        telConteneur.add(sonMobile3);
        telConteneur.add(sonMobile4);

        telThread1 = new Thread(sonMobile1);
        telThread2 = new Thread(sonMobile2);
        telThread3 = new Thread(sonMobile3);
        telThread4 = new Thread(sonMobile4);
        telThread1.start();
        telThread2.start();
        telThread3.start();
        telThread4.start();

        setSize(LARG, HAUT);
        setVisible(true);
    }
}