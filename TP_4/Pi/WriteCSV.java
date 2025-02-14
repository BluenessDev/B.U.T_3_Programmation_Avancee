package TP_4.Pi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteCSV {

    public static void write(long nTotal, int nWorkers, long duration, double approxiPi, double errorRange, String fileName) throws IOException {
        BufferedWriter writer = null;
        try {
            File file = new File(fileName + ".csv");
            writer = new BufferedWriter(new FileWriter(file, true));

            if (file.length() == 0) {
                writer.write("totalIterations;numWorkers;Duree;Pi;Error\n");
            }

            String resultLine = String.format("%d;%d;%d;%.10f;%.10f\n", nTotal, nWorkers, duration, approxiPi, errorRange);

            writer.write(resultLine);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
