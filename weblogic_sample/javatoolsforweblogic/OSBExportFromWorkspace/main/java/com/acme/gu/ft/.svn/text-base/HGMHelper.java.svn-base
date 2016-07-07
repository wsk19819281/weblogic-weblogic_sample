package com.ictra.gu.ft;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.xml.bind.JAXBException;

import com.ictra.gu.gutypes.HGModified;
import com.ictra.gu.gutypes.HGModified.Itinerary;
import com.ictra.gu.gutypes.HGModified.Itinerary.ItineraryPoint;
import com.ictra.gu.gutypes.TrainType;
import com.ictra.tools.XMLLoader;

public class HGMHelper {
	
	private HGModified hgm = null;
	
	HGMHelper(String content) {
		XMLLoader<HGModified> xmlLoader = new XMLLoader<HGModified>();
		try {
			this.hgm = xmlLoader.getObjectFromString(HGModified.class, content);
		} catch (JAXBException e) {
			e.printStackTrace();
		}		
	}

	private HGModified getHgm() {
		return hgm;
	}
	
	public void checkContent() {
		// ... content exists 
		assertNotNull(this.getHgm());

		// ... general data train exist and contain expected values 
		TrainType train = this.getHgm().getTrain();
		assertEquals(1530, train.getNumber());
		assertEquals("CommItinerary", train.getCirculationType());
		assertEquals("2011-06-22", train.getDepartureDate().toString());

		// ... train itinerary is present and contains expected values 
		// itinerary exists 
		Itinerary itinerary = this.getHgm().getItinerary();
		assertNotNull(itinerary);

		// itinerary contains 80 points 
		List<ItineraryPoint> itineraryPoints = (List<ItineraryPoint>)itinerary.getItineraryPoint();
		assertEquals(80, itineraryPoints.size());
		
		// first point has ordNo = 1 and is for ptcarNo = 1151 
		ItineraryPoint firstPoint = (ItineraryPoint)itineraryPoints.get(0);
		int firstOrderNo = firstPoint.getIdentity().getOrder();
		assertEquals(1, firstOrderNo);
		int firstPtcarNo = firstPoint.getIdentity().getId();
		assertEquals(1151, firstPtcarNo);
		int firstCountryCode = firstPoint.getIdentity().getCountryCode();
		assertEquals(88, firstCountryCode);
		
		// last point has ordNo = 80 and is for ptcarNo = 642 
		ItineraryPoint lastPoint = (ItineraryPoint)itineraryPoints.get(itineraryPoints.size()-1);
		int lastOrderNo = lastPoint.getIdentity().getOrder();
		assertEquals(80, lastOrderNo);
		int lastPtcarNo = lastPoint.getIdentity().getId();
		assertEquals(642, lastPtcarNo);
		int lastCountryCode = lastPoint.getIdentity().getCountryCode();
		assertEquals(88, lastCountryCode);
		
		for (ItineraryPoint itineraryPoint : itineraryPoints) {
			String routeStatus = itineraryPoint.getRouteStatus();
			assertNotNull(routeStatus);
		}	
	}

}
