package com.ictra.gu.ft;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.xml.bind.JAXBException;

import com.ictra.gu.gutypes.Track;
import com.ictra.gu.gutypes.Track.Itinerary;
import com.ictra.gu.gutypes.Track.Itinerary.ItineraryPoint;
import com.ictra.gu.gutypes.Track.Itinerary.ItineraryPoint.NewTrackSection;
import com.ictra.gu.gutypes.TrainType;
import com.ictra.tools.XMLLoader;

public class TrackHelper {
	
	private Track track = null;

	TrackHelper(String content) {
		XMLLoader<Track> xmlLoader = new XMLLoader<Track>();
		try {
			this.track = xmlLoader.getObjectFromString(Track.class, content);
		} catch (JAXBException e) {
			e.printStackTrace();
		}		
	}
	
	private Track getTrack() {
		return track;
	}

	public void checkContent() {
		// ... content exists 
		assertNotNull(this.getTrack());

		// ... general data train exist and contain expected values 
		TrainType train = this.getTrack().getTrain();
		assertEquals(1530, train.getNumber());
		assertEquals("CommItinerary", train.getCirculationType());
		assertEquals("2011-06-22", train.getDepartureDate().toString());

		 //... train itinerary is present and contains expected values 
		 //itinerary exists 
		Itinerary itinerary = this.getTrack().getItinerary();
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
			NewTrackSection newTrackInfo = itineraryPoint.getNewTrackSection();
			assertNotNull(newTrackInfo);
			
			if (itineraryCount == 0) {
				int trackId = newTrackInfo.getId();
				assertEquals(13888, trackId);
				
				String trackNameFr = newTrackInfo.getLongNameFr();
				assertEquals("LGV1 - Patard - voie 310", trackNameFr);
			}
		}	

	}
}
