package TP_4;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Approximates PI using the Monte Carlo method. Demonstrates
 * use of Callables, Futures, and thread pools.
 */
public class Pi {
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Entrer le nombre total d'itérations: ");
		int totalCount = scanner.nextInt();

		System.out.print("Entrer le nombres de worker: ");
		int numWorkers = scanner.nextInt();

		Master master = new Master();

		// Measure time with a single processor
		long singleWorkerTime = master.doRun(totalCount, 1);
		System.out.println("Time with 1 processor: " + singleWorkerTime + "ms");

		// Measure time with multiple processors
		long multiWorkersTime = master.doRun(totalCount, numWorkers);
		System.out.println("Time with " + numWorkers + " processors: " + multiWorkersTime + "ms");

		// Calculate speedup
		double speedup = (double) singleWorkerTime / multiWorkersTime;
		System.out.println("Speedup: " + speedup);

		// Write results to file
		try (FileWriter fileWriter = new FileWriter("out_Pi.java_MBP.txt", true);
			 PrintWriter printWriter = new PrintWriter(fileWriter)) {
			printWriter.printf("Speedup: %.2f, Tps 1 worker: %dms, Tps avec %d worker: %dms%n",
					speedup, singleWorkerTime, numWorkers, multiWorkersTime);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

/**
 * Creates workers to run the Monte Carlo simulation
 * and aggregates the results.
 */
class Master {
	public long doRun(int totalCount, int numWorkers) throws InterruptedException, ExecutionException {
		long startTime = System.currentTimeMillis();

		// Create a collection of tasks
		List<Callable<Long>> tasks = new ArrayList<>();
		for (int i = 0; i < numWorkers; ++i) {
			tasks.add(new Worker(totalCount));
		}

		// Run them and receive a collection of Futures
		ExecutorService exec = Executors.newFixedThreadPool(numWorkers);
		List<Future<Long>> results = exec.invokeAll(tasks);
		long total = 0;

		// Assemble the results.
		for (Future<Long> f : results) {
			// Call to get() is an implicit barrier. This will block
			// until result from corresponding worker is ready.
			total += f.get();
		}
		double pi = 4.0 * total / totalCount / numWorkers;

		long stopTime = System.currentTimeMillis();

		double errRelative = (Math.abs(pi - Math.PI)) / Math.PI;
		long duration = stopTime - startTime;

		System.out.println("Err relative: " + errRelative + "\n");
		System.out.println("Ntot: " + totalCount * numWorkers);
		System.out.println("Available processors: " + numWorkers);
		System.out.println("Time Duration (ms): " + duration + "\n");

		try (FileWriter fileWriter = new FileWriter("out_Pi.java_MBP.txt", true);
			 PrintWriter printWriter = new PrintWriter(fileWriter)) {
			printWriter.printf("Err relative: %.6f, nThrows: %d, nbProcessus: %d, tps: %dms%n", errRelative, totalCount, numWorkers, duration);
		} catch (IOException e) {
			e.printStackTrace();
		}

		exec.shutdown();
		return duration;
	}
}

/**
 * Task for running the Monte Carlo simulation.
 */
class Worker implements Callable<Long> {
	private final int numIterations;

	public Worker(int num) {
		this.numIterations = num;
	}

	@Override
	public Long call() {
		long circleCount = 0;
		Random prng = new Random();
		for (int j = 0; j < numIterations; j++) {
			double x = prng.nextDouble();
			double y = prng.nextDouble();
			if ((x * x + y * y) < 1) ++circleCount;
		}
		return circleCount;
	}
}