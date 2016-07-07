package com.acme.geoosb.mqfeed;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Helper class to load Java Objects from an XML document
 * See test methods for usage
 * @author pierre
 *
 * @param <T>
 */
public class XMLLoader <T> {
	/**
	 * Loads an object from a XML file
	 * @param theClass
	 * @param fileName
	 * @return
	 * @throws JAXBException
	 */
	public T getObjectFromXMLFile(Class theClass, String fileName) throws JAXBException {
		T result = null;
		JAXBContext jc = JAXBContext.newInstance(theClass);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		result = (T)unmarshaller.unmarshal(new File(fileName));
		return result;
	}

	public T getObjectFromString(Class theClass, String theXML) throws JAXBException {
		T result = null;
		JAXBContext jc = JAXBContext.newInstance(theClass);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		byte[] bytes = theXML.getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		result = (T)unmarshaller.unmarshal(bais);
		return result;
	}
}
