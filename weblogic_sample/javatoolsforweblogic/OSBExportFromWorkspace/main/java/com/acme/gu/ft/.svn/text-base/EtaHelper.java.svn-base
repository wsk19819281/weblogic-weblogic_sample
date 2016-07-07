package com.ictra.gu.ft;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.xml.bind.JAXBException;

import com.ictra.gu.gutypes.ETA;
import com.ictra.gu.gutypes.ETA.Itinerary;
import com.ictra.gu.gutypes.ETA.Itinerary.ItineraryPoint;
import com.ictra.gu.gutypes.ETA.Itinerary.ItineraryPoint.ArrivalInfo;
import com.ictra.gu.gutypes.ETA.Itinerary.ItineraryPoint.DepartureInfo;
import com.ictra.gu.gutypes.TrainType;
import com.ictra.tools.XMLLoader;

public class EtaHelper {

	private ETA eta = null;
	
	EtaHelper(String content) {
		XMLLoader<ETA> xmlLoader = new XMLLoader<ETA>();
		try {
			this.eta = xmlLoader.getObjectFromString(ETA.class, content);
		} catch (JAXBException e) {
			e.printStackTrace();
		}		
	}
	
	private ETA getEta() {
		return eta;
	}

	public void checkContent() {
		// ... content exists 
		assertNotNull(this.getEta());

		// ... general data train exist and contain expected values 
		TrainType train = this.getEta().getTrain();
		assertEquals(1530, train.getNumber());
		assertEquals("CommItinerary", train.getCirculationType());
		assertEquals("2011-06-22", train.getDepartureDate().toString());

		// ... train itinerary is present and contains expected values 
		// itinerary exists 
		Itinerary itinerary = this.getEta().getItinerary();
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
		
		int itineraryCount = 0;
		for (ItineraryPoint itineraryPoint : itineraryPoints) {
			
			ArrivalInfo arrivalInfo = itineraryPoint.getArrivalInfo();
			if (itineraryCount > 0) assertNotNull(arrivalInfo);
			
			DepartureInfo departureInfo = itineraryPoint.getDepartureInfo();
			if (itineraryCount < itineraryPoints.size()-1) assertNotNull(departureInfo);
			
			itineraryCount++;
		}	
		
	}
	
}
