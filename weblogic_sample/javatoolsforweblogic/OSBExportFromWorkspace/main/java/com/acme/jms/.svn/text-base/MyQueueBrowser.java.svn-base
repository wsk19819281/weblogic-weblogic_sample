package com.ictra.jms;

import java.util.Enumeration;

import javax.naming.Context;

import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Message;
import javax.jms.QueueSession;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.TextMessage;


public class MyQueueBrowser {
	public static final String WEBLOGIC_JMS_XA_CONNECTION_FACTORY = "weblogic/jms/XAConnectionFactory";
	public static final String IN_QUEUE = "jms/GUOutputQueueTest";
	QueueBrowser queueBrowser = null;
	QueueConnection queueConn = null;
	Queue queue = null;
	QueueSession queueSession = null;
	QueueConnectionFactory queueConnFactory = null;

	private static final int WAIT_TIME_BETWEEN_QUEUE_READS = 1000;
	private static final int TIMEOUT_WAITING_FOR_MESSAGE_IN_QUEUE = 60;
	
	
	public void init(String jndiname) throws Exception {
		// get the initial context
		Context ctx = JNDITools.createInitialContext();
		// lookup the queue object
		queue = (Queue) ctx.lookup(jndiname);
		
		// lookup the queue connection factory
		queueConnFactory = (QueueConnectionFactory) ctx.lookup(WEBLOGIC_JMS_XA_CONNECTION_FACTORY);
		// create a queue connection
		queueConn = queueConnFactory.createQueueConnection();
		// create a queue session
		queueSession = queueConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		// create a queue browser
		queueBrowser = queueSession.createBrowser(queue);
		
		// start the connection
		queueConn.start();
	}

	public static void main(String[] args) throws Exception
	{
		MyQueueBrowser myQueueBrowser = new MyQueueBrowser();
		myQueueBrowser.init(IN_QUEUE);
		
		myQueueBrowser.printAll();
	}

	private void printAll() throws Exception {
		// browse the messages
		Enumeration<?> e = queueBrowser.getEnumeration();
		int numMsgs = 0;

		// count number of messages
		while (e.hasMoreElements()) {
			   TextMessage message = (TextMessage) e.nextElement();
			   System.out.println(message.getText());
			   numMsgs++;
		}

		System.out.println(queue + " has " + numMsgs + " messages");

		// close the queue connection
		queueConn.close();
		
		Message myMessage = findMessageByJMSCorrelationId("E_UPT_TIME_2011-07-04T08:36:28_408_2011-07-04", IN_QUEUE);
		System.out.println(myMessage);
	}
	
	public int countMessages() throws Exception {
		// browse the messages
		Enumeration<?> e = queueBrowser.getEnumeration();
		int numMsgs = 0;

		// count number of messages
		while (e.hasMoreElements()) {
			   e.nextElement();
			   numMsgs++;
		}

		// close the queue connection
		queueConn.close();
		return numMsgs;
	}	
	
	public Message findMessageByJMSCorrelationId(String correlationId, String jndiname) throws Exception {
		init(jndiname);
		
		Message result = null;
		Enumeration<?> e = queueBrowser.getEnumeration();
		// count number of messages
		if (e.hasMoreElements()) {
		   TextMessage message = (TextMessage) e.nextElement();
		   if (correlationId.equals(message.getJMSCorrelationID())) {
			   result = message;
		   }
		}

		// close the queue connection
		queueConn.close();
		return result;
	}
	

	public Message waitForMessageByJMSCorrelationId(String correlationId, String jndiname) throws Exception {
		Message msg = null;

		boolean found = false;
		int count = 0;
		while (!found && count < TIMEOUT_WAITING_FOR_MESSAGE_IN_QUEUE) {
			msg = this.findMessageByJMSCorrelationId(correlationId, jndiname);
			if (msg == null) {
				Thread.sleep(WAIT_TIME_BETWEEN_QUEUE_READS);
				count++;
			}
			else {
				found = true;
			}
		}
		
		return msg;
	}
	
	
	
	
	
	public void deleteAllMessagesFromQueue(String jndiname) throws Exception {
		init(jndiname);
		MessageConsumer consumer = queueSession.createConsumer(queue);
		Message message = null;
		do {
			message = consumer.receiveNoWait();
			if (message != null) message.acknowledge();
		} 
		while (message != null);
		
		consumer.close();
		queueConn.close();
	}
	
	public void deleteFirstMessageFromQueueWithJMSCorrelationId(String correlationId, String jndiname) throws Exception {
		init(jndiname);
		
		MessageConsumer consumer = queueSession.createConsumer(queue);
		
		boolean foundFirst = false;
		Message message = null;
		do {
			message = consumer.receiveNoWait();
			if (message != null) {							// message.acknowledge();
				TextMessage txtmessage = (TextMessage) message;
				if (correlationId.equals(txtmessage.getJMSCorrelationID())) {
					message.acknowledge();
					foundFirst = true;
				}
			}
		} 
		while ((message != null) && (foundFirst == false));
		
		consumer.close();
		queueConn.close();
	}

}
