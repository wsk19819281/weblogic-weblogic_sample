package com.pierre.socketpool;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PVServer {

	public static void main(String[] args) throws IOException {
		PVServer server = new PVServer();
		server.run();
	}


	/**
	 * Listen forever for incoming connections
	 * @throws IOException 
	 */
	private void run() throws IOException {
		ServerSocket ss = new ServerSocket(7777);
		int count = 0;
		try {
			while (true) {
				Socket socket = ss.accept();
				count++;
				InputStream is = socket.getInputStream();
				byte[] b = new byte[1024];
				while (is.read(b) > 0) {
					//System.out.println(new String(b));
				}
				socket.close();
			}
		}
		catch (Exception e) {
			System.out.println("exception at count = " + count);
		}

	}
}
