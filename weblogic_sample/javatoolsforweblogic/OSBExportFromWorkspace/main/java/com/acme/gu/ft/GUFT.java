package com.ictra.gu.ft;

import static com.ictra.gu.conf.GUPropertyReader.getStringProperty;
import static org.junit.Assert.assertTrue;

import javax.jms.Message;
import javax.jms.TextMessage;

import org.junit.Test;

import com.ictra.gu.GUHelper;
import com.ictra.gu.GUHelper.TESTEVENT;
import com.ictra.jms.MyQueueBrowser;

/**

- delete all messages from queue
- generate ETA message
- read ETA message from Queue
- parse into a Java ETA object
- do some asserts


*/


public class GUFT {
	
	GUHelper helper = new GUHelper();
	MyQueueBrowser myQueueBrowser = new MyQueueBrowser();
	
	public String FACADE_URL = "http://iinfblockbcl003.msnet.railb.be:7009/InterfacesGU/GUAnyXMLFacade";
	public String HOST = "";
	
	public GUFT() throws Exception {
		FACADE_URL = getStringProperty("FACADE_URL");
		HOST = getStringProperty("HOST");
	}
	
	
	@Test
	public void testETA() throws Exception {
		
		// +++++ launch test         +++++
		 
		// clear receiver queue
		myQueueBrowser.deleteAllMessagesFromQueue(MyQueueBrowser.IN_QUEUE);
		// send test message
		helper.invokeGU(GUHelper.testEventETA, "http://" + HOST + FACADE_URL);
		// wait for answer with correct 'JMSCorrelationId'
		Message message = myQueueBrowser.waitForMessageByJMSCorrelationId(TESTEVENT.EVENT_ETA.toKey(), MyQueueBrowser.IN_QUEUE);

		// +++++ verify test results +++++ 
		// verify response was received
		assertTrue(message != null);
		
		// verify expected content ...
		String responseContent = ((TextMessage)message).getText();
		System.out.println(responseContent);
		
		EtaHelper etaTester = new EtaHelper(responseContent);
		etaTester.checkContent();

		// clear receiver queue
		myQueueBrowser.deleteAllMessagesFromQueue(MyQueueBrowser.IN_QUEUE);
	}
	
	@Test
	public void testTrack() throws Exception {
		
		// +++++ launch test         +++++
		 
		// clear receiver queue
		myQueueBrowser.deleteAllMessagesFromQueue(MyQueueBrowser.IN_QUEUE);
		// send test message
		helper.invokeGU(GUHelper.testEventTrack, "http://" + HOST + FACADE_URL);
		// wait for answer with correct 'JMSCorrelationId'
		Message message = myQueueBrowser.waitForMessageByJMSCorrelationId(TESTEVENT.EVENT_TRACK.toKey(), MyQueueBrowser.IN_QUEUE);
		
		// +++++ verify test results +++++
		// verify response was received
		assertTrue(message != null);
		
		// verify expected content ...
		String responseContent = ((TextMessage)message).getText();
		System.out.println(responseContent);
		
		TrackHelper trackTester = new TrackHelper(responseContent);
		trackTester.checkContent();

		// clear receiver queue
		myQueueBrowser.deleteAllMessagesFromQueue(MyQueueBrowser.IN_QUEUE);
	}
	
	
	@Test
	public void testHGCancel() throws Exception {
		
		// +++++ launch test         +++++
		 
		// clear receiver queue
		myQueueBrowser.deleteAllMessagesFromQueue(MyQueueBrowser.IN_QUEUE);
		// send test message
		helper.invokeGU(GUHelper.testEventRouteCanceled, "http://" + HOST + FACADE_URL);
		// wait for answer with correct 'JMSCorrelationId'
		Message message = myQueueBrowser.waitForMessageByJMSCorrelationId(TESTEVENT.EVENT_ROUTE_CANCELED.toKey(), MyQueueBrowser.IN_QUEUE);
		
		// +++++ verify test results +++++
		// verify response was received
		assertTrue(message != null);
		
		// verify expected content ...
		String responseContent = ((TextMessage)message).getText();
		System.out.println(responseContent);
		
		HGCHelper hgcTester = new HGCHelper(responseContent);
		hgcTester.checkContent();

		// clear receiver queue
		myQueueBrowser.deleteAllMessagesFromQueue(MyQueueBrowser.IN_QUEUE);
	}
	
	@Test
	public void testHGModified() throws Exception {
		
		// +++++ launch test         +++++
		 
		// clear receiver queue
		myQueueBrowser.deleteAllMessagesFromQueue(MyQueueBrowser.IN_QUEUE);
		// send test message
		helper.invokeGU(GUHelper.testEventRouteModified, "http://" + HOST + FACADE_URL);
		// wait for answer with correct 'JMSCorrelationId'
		Message message = myQueueBrowser.waitForMessageByJMSCorrelationId(TESTEVENT.EVENT_ROUTE_MODIFIED.toKey(), MyQueueBrowser.IN_QUEUE);
		
		// +++++ verify test results +++++
		// verify response was received
		assertTrue(message != null);
		
		// verify expected content ...
		String responseContent = ((TextMessage)message).getText();
		System.out.println(responseContent);
		
		HGMHelper hgmTester = new HGMHelper(responseContent);
		hgmTester.checkContent();

		// clear receiver queue
		myQueueBrowser.deleteAllMessagesFromQueue(MyQueueBrowser.IN_QUEUE);
	}

	@Test
	public void testRTI() throws Exception {
		
		// +++++ launch test         +++++
		 
		// clear receiver queue
		myQueueBrowser.deleteAllMessagesFromQueue(MyQueueBrowser.IN_QUEUE);
		// send test message
		helper.invokeGU(GUHelper.testEventRTI, "http://" + HOST + FACADE_URL);
		
		int nbMsg = 0;
		while (nbMsg < 2) {
			// wait for answer with correct 'JMSCorrelationId'
			Message message = myQueueBrowser.waitForMessageByJMSCorrelationId(TESTEVENT.EVENT_RTI.toKey(), MyQueueBrowser.IN_QUEUE);
			
			// +++++ verify test results +++++
			// verify response was received
			assertTrue(message != null);
			
			// verify expected content ...
			String responseContent = ((TextMessage)message).getText();
			System.out.println(responseContent);
			
			boolean isRTIIPmsg = responseContent.contains("<RTI_IP>");
			if (isRTIIPmsg) {
				RTIRouteHelper rtiRouteTester = new RTIRouteHelper(responseContent);
				rtiRouteTester.checkContent();
				//System.out.println("treated RTIRoute");
			} else {
				RTITrainHelper rtiTrainTester = new RTITrainHelper(responseContent);
				rtiTrainTester.checkContent();
				//System.out.println("treated RTITrain");
			}
			
			myQueueBrowser.deleteFirstMessageFromQueueWithJMSCorrelationId(TESTEVENT.EVENT_RTI.toKey(), MyQueueBrowser.IN_QUEUE);
			
			nbMsg++;			
		}

		// clear receiver queue
		myQueueBrowser.deleteAllMessagesFromQueue(MyQueueBrowser.IN_QUEUE);
	}
	

	
	@Test
	public void testRTP() throws Exception {
		
		// +++++ launch test         +++++
		 
		// clear receiver queue
		myQueueBrowser.deleteAllMessagesFromQueue(MyQueueBrowser.IN_QUEUE);
		// send test message
		helper.invokeGU(GUHelper.testEventRTP, "http://" + HOST + FACADE_URL);
		
		// wait for answer with correct 'JMSCorrelationId'
		Message message = myQueueBrowser.waitForMessageByJMSCorrelationId(TESTEVENT.EVENT_RTP.toKey(), MyQueueBrowser.IN_QUEUE);
		
		// +++++ verify test results +++++
		// verify response was received
		assertTrue(message != null);
		
		// verify expected content ...
		String responseContent = ((TextMessage)message).getText();
		System.out.println(responseContent);
		
		RTPHelper rtpTester = new RTPHelper(responseContent);
		rtpTester.checkContent();			
			

		// clear receiver queue
		myQueueBrowser.deleteAllMessagesFromQueue(MyQueueBrowser.IN_QUEUE);
	}
	
	
	
	
}
