package com.ictra.gu;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

/**
 * Helper class to perform tests on GU
 * @author EXB866
 *
 */
public class GUHelper {
	
	private boolean VERBOSE = false;
	
	
	public boolean isVERBOSE() {
		return VERBOSE;
	}

	public void setVERBOSE(boolean vERBOSE) {
		VERBOSE = vERBOSE;
	}


	private static final String DATE = "2011-07-04";
	private static final String ID = "408";
	private static final String ID_CANCELED = "409";
	private static final String ID_RTI = "410";
	private static final String ID_RTP = "411";


	public enum TESTEVENT {
		EVENT_ETA ("E_UPT_TIME", ID), 
		EVENT_TRACK("E_UPT_TRACK", ID),
		EVENT_ROUTE_MODIFIED("E_UPT_ROUTE", ID),
		EVENT_ROUTE_CANCELED("E_UPT_ROUTE", ID_CANCELED),
		EVENT_RTI("P_UPT_RTI", ID_RTI),
		EVENT_RTP("P_UPT_RTP", ID_RTP);
		
		private final String type;
		private final String id;
		
		TESTEVENT(String p_type, String p_id) {
			this.type = p_type;
			this.id = p_id;
		};
		
		public String toString() {
			return "<NISNotification Type='" + type + "' DateTime='" + DATE + "T08:36:28' Id='" + id + "' ValidityDate='" + DATE + "'/>";
		}

		public String toKey() {
			return type + "_" + DATE + "T08:36:28" +  "_" + id + "_" + DATE;
		}
		
	}

	public static final String testEventETA = TESTEVENT.EVENT_ETA.toString();
	public static final String testEventTrack = TESTEVENT.EVENT_TRACK.toString();
	public static final String testEventRouteModified = TESTEVENT.EVENT_ROUTE_MODIFIED.toString();
	public static final String testEventRouteCanceled = TESTEVENT.EVENT_ROUTE_CANCELED.toString();
	public static final String testEventRTI = TESTEVENT.EVENT_RTI.toString();
	public static final String testEventRTP = TESTEVENT.EVENT_RTP.toString();
	


	
	
	public void invokeGU(String testEvent, String url) throws UnsupportedEncodingException, IOException, ClientProtocolException {
		HttpContext localContext = new BasicHttpContext();
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		httppost.addHeader("ORIGIN", "TEST");
		
		//NISNotification event = getNISNotification(3, "1975-01-01", "1974-01-01 00:00:00", NISConstants.TYPES.TrainItinerary.name());
		String eventAsString = testEvent; //eventToXML(event );
		StringEntity entity = new StringEntity(eventAsString);
		httppost.setEntity(entity );
		log("Sending message " + eventAsString);
		long time = System.currentTimeMillis();
		HttpResponse response = httpclient.execute(httppost, localContext);
		log("time elapsed " + (System.currentTimeMillis() - time) );
		log("getReasonPhrase " + response.getStatusLine().getReasonPhrase());
		//log("getStatusCode " + response.getStatusLine().getStatusCode());
		httpclient.getConnectionManager().closeExpiredConnections();
	}

	public void log(String message) {
		if (isVERBOSE()) {
			System.out.println(Thread.currentThread().getName() + " " + message);
		}
	}
	
	
	public String getTypeFromNisNotificationEvent(String event) {
		String TYPE = "Type='";
		int beginIndex = event.indexOf(TYPE);
		int endIndex = event.indexOf("' DateTime=");
		String type = event.substring(beginIndex + TYPE.length(), endIndex);
		return type;
	}

}
