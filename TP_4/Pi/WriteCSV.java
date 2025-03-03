package TP_4.Pi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteCSV {

    public static void write(long nTotal, int nWorkers, long duration, double approxiPi, double errorRange, String fileName) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(new File(fileName + ".csv"), true));

            File file = new File(fileName + ".csv");
            if (file.length() == 0) {
                writer.write("nTotal,nWorkers,duration,approxPi,errorRange\n");
            }

            String resultLine = String.format("%d,%d,%d,%.10f,%.10f\n", nTotal, nWorkers, duration, approxiPi, errorRange);

            writer.write(resultLine);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
