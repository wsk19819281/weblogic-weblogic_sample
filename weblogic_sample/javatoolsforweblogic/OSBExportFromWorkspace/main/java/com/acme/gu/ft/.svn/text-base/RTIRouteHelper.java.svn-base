package com.ictra.gu.ft;

import static org.junit.Assert.*;

import javax.xml.bind.JAXBException;

import com.ictra.gu.gutypes.RTIIP;
import com.ictra.gu.gutypes.RTIIP.ItineraryPoint;
import com.ictra.gu.gutypes.RTIIP.Position;
import com.ictra.gu.gutypes.TrainType;
import com.ictra.tools.XMLLoader;

public class RTIRouteHelper {
	
	private RTIIP rtiIp = null;
	
	public RTIRouteHelper(String content) {
		XMLLoader<RTIIP> xmlLoader = new XMLLoader<RTIIP>();
		try {
			this.rtiIp = xmlLoader.getObjectFromString(RTIIP.class, content);
		} catch (JAXBException e) {
			e.printStackTrace();
		}		
	}

	private RTIIP getRtiIp() {
		return rtiIp;
	}

	public void checkContent() {
		// ... content exists 
		assertNotNull(this.getRtiIp());

		// ... general data train exist and contain expected values 
		TrainType train = this.getRtiIp().getTrain();
		assertEquals(1530, train.getNumber());
		assertEquals("CommItinerary", train.getCirculationType());
		assertEquals("2011-06-22", train.getDepartureDate().toString());

		// rti point has ordNo = 2 and is for ptcarNo = 157 with countryCode = 88
		ItineraryPoint rtiPoint = this.getRtiIp().getItineraryPoint();
		int rtiOrderNo = rtiPoint.getIdentity().getOrder();
		assertEquals(2, rtiOrderNo);
		int rtiPtcarNo = rtiPoint.getIdentity().getId();
		assertEquals(157, rtiPtcarNo);
		int rtiCountryCode = rtiPoint.getIdentity().getCountryCode();
		assertEquals(88, rtiCountryCode);
		
		// rti point has rti : onArrival
		Boolean rtiOnArrival = rtiPoint.isArrivalHasRti();
		assertNotNull(rtiOnArrival);
		assertTrue(rtiOnArrival);
		Boolean rtiOnDeparture = rtiPoint.isDepartureHasRti();
		assertNull(rtiOnDeparture);
		
		// rti point has (for the moment) NO position info
		Position pos = this.getRtiIp().getPosition();
		assertNull(pos);
	}
}
