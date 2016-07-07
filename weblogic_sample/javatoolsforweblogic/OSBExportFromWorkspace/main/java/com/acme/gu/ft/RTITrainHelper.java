package com.ictra.gu.ft;

import static org.junit.Assert.*;

import javax.xml.bind.JAXBException;


import com.ictra.gu.gutypes.RTITrain;
import com.ictra.gu.gutypes.TrainType;
import com.ictra.tools.XMLLoader;

public class RTITrainHelper {
	
	private RTITrain rtiTrain = null;
	
	RTITrainHelper(String content) {
		XMLLoader<RTITrain> xmlLoader = new XMLLoader<RTITrain>();
		try {
			this.rtiTrain = xmlLoader.getObjectFromString(RTITrain.class, content);
		} catch (JAXBException e) {
			e.printStackTrace();
		}		
	}

	private RTITrain getRtiTrain() {
		return rtiTrain;
	}
	
	public void checkContent() {
		// ... content exists 
		assertNotNull(this.getRtiTrain());

		// ... general data train exist and contain expected values 
		TrainType train = this.getRtiTrain().getTrain();
		assertEquals(1530, train.getNumber());
		assertEquals("CommItinerary", train.getCirculationType());
		assertEquals("2011-06-22", train.getDepartureDate().toString());
		assertTrue(this.getRtiTrain().isHasRTI());
	}

}
