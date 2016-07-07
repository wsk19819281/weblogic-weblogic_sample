package com.ictra.gu.ft;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.xml.bind.JAXBException;

import com.ictra.gu.gutypes.HGCancel;
import com.ictra.gu.gutypes.TrainType;
import com.ictra.tools.XMLLoader;

public class HGCHelper {

	private HGCancel hgc = null;
	
	HGCHelper(String content) {
		XMLLoader<HGCancel> xmlLoader = new XMLLoader<HGCancel>();
		try {
			this.hgc = xmlLoader.getObjectFromString(HGCancel.class, content);
		} catch (JAXBException e) {
			e.printStackTrace();
		}		
	}

	private HGCancel getHgc() {
		return hgc;
	}
	
	public void checkContent() {
		// ... content exists 
		assertNotNull(this.getHgc());

		// ... general data train exist and contain expected values 
		TrainType train = this.getHgc().getTrain();
		assertEquals(1530, train.getNumber());
		assertEquals("CommItinerary", train.getCirculationType());
		assertEquals("2011-06-22", train.getDepartureDate().toString());
		assertEquals("Canceled", this.getHgc().getTrainStatus());
		
	}
}
