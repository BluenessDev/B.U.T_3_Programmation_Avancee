package TP_4.Assignement;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import TP_4.Assignement.WriteCSV;

class PiMonteCarlo {
	AtomicInteger nAtomSuccess;
	int nThrows;
	double value;
	int nProcessors;

	class MonteCarlo implements Runnable {
		@Override
		public void run() {
			double x = Math.random();
			double y = Math.random();
			if (x * x + y * y <= 1)
				nAtomSuccess.incrementAndGet();
		}
	}

	public PiMonteCarlo(int nThrows, int nProcessors) {
		this.nAtomSuccess = new AtomicInteger(0);
		this.nThrows = nThrows;
		this.nProcessors = nProcessors;
		this.value = 0;
	}

	public double getPi() {
		ExecutorService executor = Executors.newWorkStealingPool(nProcessors);
		for (int i = 1; i <= nThrows; i++) {
			Runnable worker = new MonteCarlo();
			executor.execute(worker);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {}
		value = 4.0 * nAtomSuccess.get() / nThrows;
		return value;
	}
}

public class Assignment102 {
	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			System.out.println("Usage: java TP_4.Assignment102 <nThrows> <nProcess> <outputFileName>");
			return;
		}
		int nThrows= Integer.parseInt(args[0]);
		int nProcess = Integer.parseInt(args[1]);
		PiMonteCarlo PiVal = new PiMonteCarlo(nThrows, nProcess);
		long startTime = System.currentTimeMillis();
		double value = PiVal.getPi();
		long stopTime = System.currentTimeMillis();




		System.out.println("\nPi : " + value);
		System.out.println("Error: " + (value - Math.PI)+"\n");
		System.out.println("Error: " + (value - Math.PI) / Math.PI * 100 + " %");
		System.out.println("Ntot : " + nThrows);
		System.out.println("Available processors: " + nProcess);
		System.out.println("Time Duration (ms): " + (stopTime - startTime));

		// Write results to file
		WriteCSV writeToFile = new WriteCSV();
		writeToFile.write(nThrows,nProcess,(stopTime - startTime),value,(value - Math.PI),args[2]);
	}
}