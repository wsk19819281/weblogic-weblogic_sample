package org.example.wsdl;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;

public class WSDLParser {
	public static void main(String[] args) throws Exception {
		WSDLParser wsdlParser = new WSDLParser();
		wsdlParser.parse("/path/to/mywsdl.wsdl");
	}

	@Test
	public void parse(String fileName) throws Exception {
		TDefinitions definitions = getObjectFromXMLFile(TDefinitions.class, fileName);
		System.out.println(definitions.getTargetNamespace());
	}
	
	public TDefinitions getObjectFromXMLFile(Class theClass, String fileName) throws JAXBException {
		TDefinitions result = null;
		JAXBContext jc = JAXBContext.newInstance(theClass);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		StreamSource source = new StreamSource(new File(fileName));
		JAXBElement<TDefinitions> root = unmarshaller.unmarshal(source, TDefinitions.class);
		result = root.getValue();
		return result;
	}	
}
