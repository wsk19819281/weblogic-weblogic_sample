package com.ictra.mq;

import com.ibm.mq.*;

import java.io.*;
import static com.ictra.mq.MQConstants.*;


public class PutMessage {

	private String qManager = MQ_queueManager;
	private String qName = MQ_queue;
	private String qmessageFile = "c:/pierre/mqfile.txt";


	public static void main(String args[]) throws Exception {
		System.out.println("java.library.path=" + System.getProperty("java.library.path"));
		PutMessage pM = new PutMessage();

		pM.runNow();
	}

	public void runNow() throws Exception {
			System.out.println("Connecting to queue manager: " + qManager);
			MQQueueManager qMgr = MQReader.setup();
			int openOptions = 17;
			System.out.println("Accessing queue: " + qName);
			MQQueue queue = qMgr.accessQueue(qName, openOptions);
			File file = new File(qmessageFile);
			for (int i = 0; i < 1000; i++) {
				String qmessage = getContents(file) + " " + i;
				MQMessage msg = new MQMessage();
				msg.writeString(qmessage);
				MQPutMessageOptions pmo = new MQPutMessageOptions();
				System.out.println("Sending message: " + qmessage);
				queue.put(msg, pmo);
			}
			System.out.println("Closing the queue");
			queue.close();
			System.out.println("Disconnecting from the Queue Manager");
			qMgr.disconnect();
			System.out.println("Done!");
	}

	private String getContents(File aFile) {
		StringBuffer contents;
		contents = new StringBuffer();
		BufferedReader input = null;
		try {
			input = new BufferedReader(new FileReader(aFile));
			for (String line = null; (line = input.readLine()) != null;) {
				contents.append(line);
				//contents.append(System.getProperty("line.separator"));
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (input != null)
					input.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return contents.toString();
	}
}