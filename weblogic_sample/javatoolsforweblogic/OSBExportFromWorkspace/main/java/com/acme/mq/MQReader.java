package com.ictra.mq;

import com.ibm.mq.*;

import java.text.*;
import java.io.*;
import java.util.Hashtable;
import static com.ictra.mq.MQConstants.*;

public class MQReader {
	  
	public static void main(String[] args) throws Exception {
		MQReader mqReader = new MQReader();
		mqReader.getAllMessages();
	}

	
	public void getAllMessages() throws MQException, IOException {
		MQQueueManager qm = setup();
		int options = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_INQUIRE;
		MQQueue q = qm.accessQueue(MQ_queue, options, null, null, null);
		int depth = q.getCurrentDepth();
		DecimalFormat indexFormat = new DecimalFormat(Integer.toString(depth).replaceAll(".", "0"));
		System.out.println("found messages " + depth);
		
		for (int index = 0; index < depth; index++) {
			MQMessage msg = new MQMessage();
			q.get(msg, new MQGetMessageOptions());
			int msgLength = msg.getMessageLength();
			String text = msg.readStringOfByteLength(msgLength);
			System.out.println("message#" + index + "  text=" + text);
		}
	}


	
	public void getMessage() {
	      try
	      {
	         MQQueueManager qm = setup();
	         MQQueue q = qm.accessQueue(MQ_queue, MQC.MQOO_INPUT_AS_Q_DEF);

	         MQMessage msg = new MQMessage();

	         q.get(msg);

	         System.out.println("Message: " + msg.readLine());

	         q.close();
	         qm.disconnect();
	      }
	      catch(MQException e)
	      {
	         System.out.println("MQ Error: cc=" + e.completionCode + ", reason=" + e.reasonCode);
	      }
	      catch(java.io.IOException e)
	      {
	         System.out.println("IO Error: " + e);
	      } 		
	}
	


	public static MQQueueManager setup() throws MQException {
		MQEnvironment.hostname = MQ_hostname;
		MQEnvironment.channel = MQ_channel;
		MQEnvironment.port = MQ_port;
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES);
		MQEnvironment.properties = props;
		MQQueueManager qm = new MQQueueManager(MQ_queueManager);
		return qm;
	}
		
}
