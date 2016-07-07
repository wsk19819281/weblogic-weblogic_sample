package com.pierre.xml;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.InputSource;

import com.sun.org.apache.xerces.internal.util.NamespaceContextWrapper;



class XPathReader {

	private File xmlFile;
	private String xmlFileContent;
	//private Document xmlDocument;
	private XPath xPath;
	InputSource inputSource;

	public XPathReader(File xmlFile) throws Exception {
		this.xmlFile = xmlFile;
		initObjectsFromFile();
	}

	public XPathReader(String xmlFileContent) throws Exception {
		this.xmlFileContent = xmlFileContent;
		initObjectsFromFileContent();
	}	

	private void initObjectsFromFile() throws Exception{        
		inputSource = new InputSource(xmlFile.getAbsolutePath());
		xPath =  XPathFactory.newInstance().newXPath();
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLEventReader evtReader = factory.createXMLEventReader(new FileReader(xmlFile));
		NamespaceContext nsContext = null;
		while (evtReader.hasNext()) {
			XMLEvent event = evtReader.nextEvent();
			if (event.isStartElement()) {
				nsContext = ((StartElement) event).getNamespaceContext();
				break;
			}
		}
		xPath.setNamespaceContext(nsContext);
	}
	
	public void initNamespaceContextForExportInfo() {
		Map<String, String> prefixMappings = new HashMap<String, String>();
		prefixMappings.put("imp", "http://www.bea.com/wli/config/importexport");
		NamespaceContext nsContext = new NamespaceContextMap(prefixMappings );
		xPath.setNamespaceContext(nsContext );
	}


	private void initObjectsFromFileContent() throws Exception{        
		inputSource = new InputSource(new ByteArrayInputStream(xmlFileContent.getBytes()));
		xPath =  XPathFactory.newInstance().newXPath();
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLEventReader evtReader = factory.createXMLEventReader(new StringReader(xmlFileContent));
		NamespaceContext nsContext = null;
		Map<String, String> prefixMappings = new HashMap<String, String>();
		while (evtReader.hasNext()) {
			XMLEvent event = evtReader.nextEvent();
			if (event.isStartElement()) {
				nsContext = ((StartElement) event).getNamespaceContext();
				com.sun.org.apache.xerces.internal.util.NamespaceContextWrapper aContext = (NamespaceContextWrapper) ((StartElement) event).getNamespaceContext();
				Enumeration<?> allPrefixes = aContext.getNamespaceContext().getAllPrefixes();
				while (allPrefixes.hasMoreElements()) {
					String prefix = allPrefixes.nextElement().toString();
					String uri = aContext.getNamespaceURI(prefix);
					if (prefixMappings.get(prefix) == null) {
						prefixMappings.put(prefix, uri);
					}
				}
				break;
			}
		}
		NamespaceContextMap map = new NamespaceContextMap(prefixMappings);
		xPath.setNamespaceContext(nsContext);
	}	




	public Object read(String expression, QName returnType){
		try {
			XPathExpression xPathExpression = xPath.compile(expression);
			return xPathExpression.evaluate(inputSource, returnType);
		} catch (XPathExpressionException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}

