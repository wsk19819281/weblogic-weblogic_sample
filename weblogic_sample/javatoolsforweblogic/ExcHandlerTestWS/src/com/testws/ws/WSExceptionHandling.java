package com.testws.ws;

import javax.jms.*;
import javax.jws.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;


/**
 * Service to log and store a SOAP fault occurred in the OSB  
 * 
 * @author pierre
 *
 */
@WebService
public class WSExceptionHandling {

	// Defines the Destination Connection Factory.
	public final static String JMS_FACTORY="maersk.integration.QueueConnectionFactory";
	// Defines the queue
	public final static String QUEUE="maersk.integration.osbErrorQueue";
	
	public final static transient Logger logger = Logger.getLogger(WSExceptionHandling.class);
	

  
	@WebMethod
	public void handleException(String fault, String body) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<OSBFault>\n");
		sb.append(fault);
		sb.append("\n");
		sb.append(body);
		sb.append("\n</OSBFault>");
		sendQueueMessage(false, 1, 0L, sb.toString());
	}
	
	  /**
	   * Sends a message to the queue.
	   * @param persistent	persistency setting
	   * @param priority	priority setting
	   * @param ttl		time-to-live setting
	   * @param correlate	correlation ID setting
	   * @param replyto     reply-to setting
	   * @param topicMessage	message
	   * @exception NamingException if problem with JNDI context interface
	   * @exception JMSException  if JMS fails send message due to internal error
	   */
	  private void sendQueueMessage(boolean persistent, int priority,
	                               long ttl, String messageText)
	       throws NamingException, JMSException
	  {
	    Context ctx = new InitialContext();
	    QueueConnectionFactory qconFactory;
	    QueueConnection qcon;
	    QueueSession qsession;
	    QueueSender qsender;
	    Queue queue;

	    TextMessage msg;

	    qconFactory = (QueueConnectionFactory) ctx.lookup(JMS_FACTORY);
	    qcon = qconFactory.createQueueConnection();
	    qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
	    queue = (Queue) ctx.lookup(QUEUE);
	    qsender = qsession.createSender(queue);
	    msg = qsession.createTextMessage();

	    msg.setText(messageText);
	    

	    qcon.start();
	    qsender.send(msg,
	                 persistent ? DeliveryMode.PERSISTENT
	                            : DeliveryMode.NON_PERSISTENT,
	                 priority,
	                 ttl);

	    qsender.close();
	    qsession.close();
	    qcon.close();
	  }
	
}