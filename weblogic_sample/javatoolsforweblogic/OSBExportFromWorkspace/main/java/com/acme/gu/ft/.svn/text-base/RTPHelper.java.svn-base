package com.ictra.gu.ft;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;

import com.ictra.gu.gutypes.RTP;
import com.ictra.gu.gutypes.TrainType;
import com.ictra.gu.gutypes.RTP.ItineraryPoint;
import com.ictra.gu.gutypes.RTP.Position;
import com.ictra.tools.XMLLoader;

public class RTPHelper {
	
	private RTP rtp = null;
	
	RTPHelper(String content) {
		XMLLoader<RTP> xmlLoader = new XMLLoader<RTP>();
		try {
			this.rtp = xmlLoader.getObjectFromString(RTP.class, content);
		} catch (JAXBException e) {
			e.printStackTrace();
		}		
	}

	private RTP getRtp() {
		return rtp;
	}
	
	public void checkContent() {
		// ... content exists 
		assertNotNull(this.getRtp());

		// ... general data train exist and contain expected values 
		TrainType train = this.getRtp().getTrain();
		assertEquals(1530, train.getNumber());
		assertEquals("CommItinerary", train.getCirculationType());
		assertEquals("2011-06-22", train.getDepartureDate().toString());

		// rtp point has ordNo = 2 and is for ptcarNo = 157 with countryCode = 88
		ItineraryPoint rtpPoint = this.getRtp().getItineraryPoint();
		int rtpOrderNo = rtpPoint.getIdentity().getOrder();
		assertEquals(57, rtpOrderNo);
		int rtpPtcarNo = rtpPoint.getIdentity().getId();
		assertEquals(1575, rtpPtcarNo);
		int rtpCountryCode = rtpPoint.getIdentity().getCountryCode();
		assertEquals(88, rtpCountryCode);
		
		// rtp point has rtp : onArrival
		XMLGregorianCalendar rtpOnArrival = rtpPoint.getArrivalRtpDelay();
		assertNull(rtpOnArrival);

		XMLGregorianCalendar rtpOnDeparture = rtpPoint.getDepartureRtpDelay();
		assertNotNull(rtpOnDeparture);
		assertEquals(5,rtpOnDeparture.getMinute());

		// rti point has (for the moment) NO position info
		Position pos = this.getRtp().getPosition();
		assertNull(pos);
		
	}

}
