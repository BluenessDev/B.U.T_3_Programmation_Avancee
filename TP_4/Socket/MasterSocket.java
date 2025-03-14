package TP_4.Socket;

import java.io.*;
import java.net.*;

public class MasterSocket {
    static int maxServer = 8;
    static final int[] tab_port = {25545,25546,25547,25548,25549,25550,25551,25552};
    static BufferedReader[] reader = new BufferedReader[maxServer];
    static PrintWriter[] writer = new PrintWriter[maxServer];
    static Socket[] sockets = new Socket[maxServer];
    static String ip = "127.0.0.1";

    public static void main(String[] args) throws Exception {
        if(args.length < 3){
            System.err.println("Usage: java assignments.MasterSocket <iterations> <numWorkers> <forte|faible>");
            return;
        }
        long iterations = Long.parseLong(args[0]);
        int numWorkers = Integer.parseInt(args[1]);
        String scaling = args[2]; // "forte" ou "faible"

        long iterationsPerWorker = iterations / numWorkers;
        long totalIterations = iterationsPerWorker * numWorkers;

        // Connexion aux workers
        for(int i = 0; i < numWorkers; i++) {
            sockets[i] = new Socket(ip, tab_port[i]);
            System.out.println("Connecté au worker sur le port " + tab_port[i]);
            reader[i] = new BufferedReader(new InputStreamReader(sockets[i].getInputStream()));
            writer[i] = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sockets[i].getOutputStream())), true);
        }

        String message_to_send = String.valueOf(iterationsPerWorker);
        long startTime = System.currentTimeMillis();

        // Envoi de la demande à chaque worker
        for(int i = 0; i < numWorkers; i++){
            writer[i].println(message_to_send);
        }

        long totalInside = 0;
        // Récupération des résultats
        for(int i = 0; i < numWorkers; i++){
            String response = reader[i].readLine();
            System.out.println("Réponse du worker : " + response);
            totalInside += Integer.parseInt(response);
        }

        double pi = 4.0 * totalInside /totalIterations;
        long stopTime = System.currentTimeMillis();
        long duration = stopTime - startTime;
        double error = Math.abs(pi - Math.PI) / Math.PI;

        System.out.println("Valeur approchée de Pi : " + pi);
        System.out.println("Erreur relative : " + error);
        System.out.println("Itérations totales : " + totalIterations);
        System.out.println("Nombre de workers : " + numWorkers);
        System.out.println("Durée (ms) : " + duration);

        String outFile = String.valueOf(args[3]);

        // Écriture des résultats dans le fichier choisi

        try {
			WriteCSV.write(totalIterations, numWorkers, duration, pi, error, outFile);
		} catch (IOException e) {
			e.printStackTrace();
        }

        // Fermeture des connexions et envoi du signal de fin aux workers
        for(int i = 0; i < numWorkers; i++){
            writer[i].println("END");
            reader[i].close();
            writer[i].close();
            sockets[i].close();
        }
    }
}
