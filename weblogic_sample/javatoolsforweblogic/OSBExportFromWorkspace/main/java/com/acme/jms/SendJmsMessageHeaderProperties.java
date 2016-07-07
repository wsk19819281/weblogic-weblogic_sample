package com.ictra.jms;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;

public class SendJmsMessageHeaderProperties {
	public static void main( String[] args ) throws Exception {
		QueueConnection queueCon = null;
		try {
			// get the initial context, refer to your app server docs for this
			Context ctx = JNDITools.createInitialContext();

			// get the connection factory, and open a connection
			QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup( MyQueueBrowser.WEBLOGIC_JMS_XA_CONNECTION_FACTORY );
			queueCon = qcf.createQueueConnection();

			// create queue session off the connection
			QueueSession queueSession = queueCon.createQueueSession( false, Session.AUTO_ACKNOWLEDGE );

			// get handle on queue, create a sender and send the message
			Queue queue = (Queue) ctx.lookup( MyQueueBrowser.IN_QUEUE );
			QueueSender sender = queueSession.createSender( queue );

			Message msg = queueSession.createTextMessage( "<Hello>I am two pigs</Hello>" );

			msg.setBooleanProperty( "ACK_DEBUG", true );
			msg.setFloatProperty( "ACK_BALANCE", 24234.44f );
			sender.send( msg );

			System.out.println( "sent the message" );
		}
		finally {
			// close the queue connection
			if( queueCon != null ) {
				queueCon.close();
			}
		}
	}

}
