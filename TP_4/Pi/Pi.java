package TP_4.Pi;

import TP_4.Pi.WriteCSV;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.io.IOException;

/**
 * Approximates PI using the Monte Carlo method. Demonstrates
 * use of Callables, Futures, and thread pools.
 */
public class Pi {
	public static void main(String[] args) throws Exception {
		if (args.length != 3) {
			System.err.println("Usage: java Pi <nThrows> <nProcess> <outputFileName>");
			System.exit(1);
		}
		int nThrows = Integer.parseInt(args[0]);
		int nProcess = Integer.parseInt(args[1]);

		long iterationsPerWorker = nThrows / nProcess;

		long total = new Master().doRun(nThrows, nProcess, iterationsPerWorker, String.valueOf(args[2]));

		System.out.println("Total from Master = " + total);
	}
}

/**
 * Creates workers to run the Monte Carlo simulation
 * and aggregates the results.
 */
class Master {
	public long doRun(int totalIterations, int numWorkers, long iterationsPerWorker, String fileName) throws InterruptedException, ExecutionException {

		long startTime = System.currentTimeMillis();

		// Create a collection of tasks
		List<Callable<Long>> tasks = new ArrayList<>();
		for (int i = 0; i < numWorkers; ++i) {
			tasks.add(new Worker(iterationsPerWorker));
		}

		// Run them and receive a collection of Futures
		ExecutorService exec = Executors.newFixedThreadPool(numWorkers);
		List<Future<Long>> results = exec.invokeAll(tasks);
		long totalInCircle = 0;

		// Assemble the results.
		for (Future<Long> f : results) {
			// Call to get() is an implicit barrier.  This will block
			// until result from corresponding worker is ready.
			totalInCircle += f.get();
		}

		// Correct calculation of Pi
		double pi = 4.0 * totalInCircle / totalIterations;

		long stopTime = System.currentTimeMillis();

		System.out.println("Ntot: " + totalIterations);
		System.out.println("Available processors: " + numWorkers);
		System.out.println("Time Duration (ms): " + (stopTime - startTime));
		System.out.println("Pi value : " + pi);
		System.out.println("Error: " + (Math.abs((pi - Math.PI)) / Math.PI));

		try {
			WriteCSV.write(totalIterations, numWorkers, stopTime - startTime, pi, Math.abs((pi - Math.PI)) / Math.PI, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		exec.shutdown();
		return totalInCircle;
	}
}

/**
 * Task for running the Monte Carlo simulation.
 */
class Worker implements Callable<Long> {
	private final long numIterations;

	public Worker(long num) {
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