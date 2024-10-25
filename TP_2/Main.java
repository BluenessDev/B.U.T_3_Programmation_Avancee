package TP_2;

import java.io.*;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.lang.String;

public class Main {

    public static void main(String[] args) {
        SemaphoreBinaire sem = new SemaphoreBinaire(1);

        Thread TA = new Thread(() -> {
            sem.syncWait();
            System.out.println("\n TA : j'entre dans la section critique");
            NewAffichage aff = new NewAffichage("AAA");
            aff.start();
            try {
                aff.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("\n TA : je sors de la section critique");
            sem.syncSignal();
        });

        Thread TB = new Thread(() -> {
            sem.syncWait();
            System.out.println("\n TB : j'entre dans la section critique");
            NewAffichage aff = new NewAffichage("BB");
            aff.start();
            try {
                aff.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("\n TB : je sors de la section critique");
            sem.syncSignal();
        });

        Thread TC = new Thread(() -> {
            sem.syncWait();
            System.out.println("\n TC : j'entre dans la section critique");
            NewAffichage aff = new NewAffichage("CCCC");
            aff.start();
            try {
                aff.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("\n TC : je sors de la section critique");
            sem.syncSignal();
        });

        Thread TD = new Thread(() -> {
            sem.syncWait();
            System.out.println("\n TD : j'entre dans la section critique");
            NewAffichage aff = new NewAffichage("DDD");
            aff.start();
            try {
                aff.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("\n TD : je sors de la section critique");
            sem.syncSignal();
        });

        TA.start();
        TB.start();
        TC.start();
        TD.start();
    }

}

