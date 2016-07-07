package com.pierre.test;

public class StressTest implements Runnable {
	public static int NUMBER_OF_VUSERS = 2;
	public static int PAUSE_BETWEEN_TESTS_IN_MILLISECONDS = 10;
	public static int NUMBER_OF_CYCLES = 2;

	public static void main(String[] args) {
		Thread[] threads = new Thread[NUMBER_OF_VUSERS];
		for (int i = 0 ; i < NUMBER_OF_VUSERS; i++) {
			StressTest stressTest = new StressTest(Integer.toString(i));
			Thread thread = new Thread(stressTest);
			threads[i] = thread;
			thread.start();
		}
	}

	private String name;


	public StressTest (String name) {
		this.name = name;
	}

	public void run() {
		for (int i = 0 ; i < NUMBER_OF_CYCLES; i++) {
			try {
				HelloWorldClient helloWorldClient = new HelloWorldClient();
				helloWorldClient.runTest();
				System.out.println("executing request # " + i + " for tester " + name);
				Thread.sleep(PAUSE_BETWEEN_TESTS_IN_MILLISECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
} 