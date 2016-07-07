package com.pierre.socketpool;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;



public class PVClient implements Runnable {
	private static final int NUMBER_OF_THREADS = 10;
	private static final int REPETITIONS = 100;

	public static void main(String[] args) throws InterruptedException {
		long now1 = System.currentTimeMillis();
		PVClient client = new PVClient();
		Thread[] threads = new Thread[NUMBER_OF_THREADS];
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			threads[i] = new Thread(client);
			threads[i].start();
		}
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			threads[i].join();
		}
		
		long now2 = System.currentTimeMillis();
		System.out.println("time elapsed = " + (now2 - now1));
	}

	public void run() {
		for (int i = 0; i < REPETITIONS; i++) {
			Socket socket;
			try {
				socket = new Socket("localhost", 7777);
				OutputStream os = socket.getOutputStream();
				byte[] b = "CIAO".getBytes();
				os.write(b);
				os.flush();
				socket.close();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
