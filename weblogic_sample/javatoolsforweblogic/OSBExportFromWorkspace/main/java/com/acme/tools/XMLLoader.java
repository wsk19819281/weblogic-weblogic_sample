package com.ictra.tools;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

import org.w3c.dom.Node;

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
	
	public T getObjectFromString(Class theClass, String xml) throws JAXBException {
		T result = null;
		JAXBContext jc = JAXBContext.newInstance(theClass);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		
		result = (T)unmarshaller.unmarshal( new StreamSource( new StringReader(xml)));
		return result;
	}	
}