package uk.co.jemos.podam.test.unit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.test.dto.GenericPojo;

public class ThreadSafetyTest {

	private final static int THREAD_NUMBER = 10;

	private final static int ITERATION_NUMBER = 100;

	private final static PodamFactory factory = new PodamFactoryImpl();

	public static class WorkerThread implements Runnable {

		String name;

		public WorkerThread(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			for (int i = 0; i < ITERATION_NUMBER; i++) {
				Object object;
				object = factory.manufacturePojo(GenericPojo.class, String.class, Integer.class);
				Assert.assertNotNull(object);
				object = factory.manufacturePojo(GenericPojo.class, String.class, Long.class);
				Assert.assertNotNull(object);
				object = factory.manufacturePojo(GenericPojo.class, String.class, String.class);
				Assert.assertNotNull(object);
				factory.getStrategy().clearMemoizationCache();
			}
		}
	}

	@Test
	public void testThreads() throws Exception {

		ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMBER);
		Future<?>[] results = new Future[THREAD_NUMBER];
		for (int i = 0; i < THREAD_NUMBER; i++) {
			Runnable worker = new WorkerThread("Thread " + i);
			results[i] = executor.submit(worker);
		}

		executor.shutdown();

		while (!executor.isTerminated()) {
		}

		for (int i = 0; i < THREAD_NUMBER; i++) {
			/* This will throw ExecutionException in case of
			 * error happened during thread execution */
			results[i].get();
		}
	}

}
