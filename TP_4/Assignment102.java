package TP_4;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

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
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Nombre de lancers : ");
		int nThrows = scanner.nextInt();

		int availableProcessors = Runtime.getRuntime().availableProcessors();
		int nProcessors;
		do {
			System.out.print("Nombre de processeurs (max " + availableProcessors + "): ");
			nProcessors = scanner.nextInt();
			if (nProcessors > availableProcessors) {
				System.out.println("Err: Le nombre de processeur ne peut pas dépasser " + availableProcessors + ".");
			}
		} while (nProcessors > availableProcessors);

		// Measure time with a single processor
		PiMonteCarlo singleProcessorPi = new PiMonteCarlo(nThrows, 1);
		long singleProcessorStartTime = System.currentTimeMillis();
		singleProcessorPi.getPi();
		long singleProcessorStopTime = System.currentTimeMillis();
		long singleProcessorDuration = singleProcessorStopTime - singleProcessorStartTime;

		// Measure time with multiple processors
		PiMonteCarlo multiProcessorPi = new PiMonteCarlo(nThrows, nProcessors);
		long multiProcessorStartTime = System.currentTimeMillis();
		double value = multiProcessorPi.getPi();
		long multiProcessorStopTime = System.currentTimeMillis();
		long multiProcessorDuration = multiProcessorStopTime - multiProcessorStartTime;

		// Calculate speedup
		double speedup = (double) singleProcessorDuration / multiProcessorDuration;

		double errRelative = (Math.abs(value - Math.PI)) / Math.PI;

		System.out.println("Err relative: " + errRelative);
		System.out.println("nThrows: " + nThrows);
		System.out.println("nbProcessus: " + nProcessors);
		System.out.println("tps: " + multiProcessorDuration + "ms");
		System.out.println("Speedup: " + speedup);

		try (FileWriter fileWriter = new FileWriter("out_assignement102_MBP.txt", true);
			 PrintWriter printWriter = new PrintWriter(fileWriter)) {
			printWriter.printf("Err relative: %.6f, nThrows: %d, nbProcessus: %d, tps: %dms, Speedup: %.2f%n",
					errRelative, nThrows, nProcessors, multiProcessorDuration, speedup);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}