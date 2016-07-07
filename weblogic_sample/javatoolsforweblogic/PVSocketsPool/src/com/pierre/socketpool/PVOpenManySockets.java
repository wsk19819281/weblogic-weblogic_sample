package com.pierre.socketpool;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class PVOpenManySockets {
	private static final int NUMBER_OF_SOCKETS = 60;

	public static void main(String[] args) throws UnknownHostException, IOException {
		PVOpenManySockets manySockets = new PVOpenManySockets();
		manySockets.run();
	}

	private void run() throws UnknownHostException, IOException {
		Socket[] sockets = new Socket[NUMBER_OF_SOCKETS];
		for (int i = 0; i < NUMBER_OF_SOCKETS; i++) {
			try {
				Socket socket = new Socket("localhost", 7777);
				sockets[i] = socket;
			}
			catch (Exception e) {
				System.out.println("exception at i=" + i);
			}
		}
		
		for (int i = 0; i < NUMBER_OF_SOCKETS; i++) {
			if (sockets[i] != null) {
				sockets[i].close();
			}
		}
		
	}
}
