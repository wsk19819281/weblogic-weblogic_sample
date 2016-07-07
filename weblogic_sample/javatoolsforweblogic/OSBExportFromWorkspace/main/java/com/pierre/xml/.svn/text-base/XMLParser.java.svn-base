package com.pierre.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Node;


public class XMLParser {
	XPathReader reader ;
	
	public void initForExportInfo(String exportedContent) throws Exception {
		// do away with annoying namespace impossible to parse
		exportedContent = exportedContent.replaceAll("imp:", "");
		initWithContent(exportedContent);
		reader.initNamespaceContextForExportInfo();
	}

	public static void main(String[] args) throws Exception {
		XMLParser xmlParser = new XMLParser();
		xmlParser.init("C:/pierre/workspaceSVN/GlobalResources/a447.sa");

		//xmlParser.parse("C:/tmp/sbconfig2507_01/InterfacesGU/ETA/E_UPT_TIME_PS.ProxyService");
		//List<String> xqueries = xmlParser.parseXQueryDependencies();
		//for (String ref : xqueries) System.out.println(ref);
		System.out.println(xmlParser.parseDescription());
	}

	public void init(String fileName) throws Exception {
		reader = new XPathReader(new File(fileName));
	}

	public List<String> parseXQueryDependencies() throws Exception {
		List<String> result = new ArrayList<String>();
		// xmlns:con3="http://www.bea.com/wli/sb/stages/config"
		String expression = ".//con3:xqueryTransform/con3:resource/@ref";
		//String expression = "//foo:value[1]";
		org.w3c.dom.NodeList read = (org.w3c.dom.NodeList)reader.read(expression, XPathConstants.NODESET);
		for (int i = 0 ; i < read.getLength(); i++) {
			Node item = read.item(i);
			String nodeValue = item.getNodeValue();
			if (!result.contains(nodeValue)) {
				result.add(nodeValue);
			}
		}
		return result;
	}

	public String parseDescriptionWithExpression(String expression) {
		String result = null;
		// xmlns:con3="http://www.bea.com/wli/sb/stages/config"
		try {
			result = (String)reader.read(expression, XPathConstants.STRING);
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		return result;
	}
	
	private List<String> parseDescriptionWithExpressionReturingList(String expression) {
		List<String>  result = new ArrayList<String>();
		// xmlns:con3="http://www.bea.com/wli/sb/stages/config"
		try {
			org.w3c.dom.NodeList read = (org.w3c.dom.NodeList)reader.read(expression, XPathConstants.NODESET);
			for (int i = 0 ; i < read.getLength(); i++) {
				Node item = read.item(i);
				String nodeValue = item.getNodeValue();
				if (!result.contains(nodeValue)) {
					result.add(nodeValue);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}	
	
	private String parseDescriptionWSDL() {
		return parseDescriptionWithExpression(".//ser:description/text()");
	}
	
	private String parseDescriptionServices() {
		return parseDescriptionWithExpression(".//con:description/text()");
	}
	
	private String parseDescriptionProject() {
		return parseDescriptionWithExpression(".//proj:description/text()");
	}	
	
	private String parseDescriptionAlert() {
		return parseDescriptionWithExpression(".//aler:description/text()");
	}	
		
	
	
	public String parseDescription() {
		String result = parseDescriptionWSDL();
		if (result == null) {
			result = parseDescriptionServices();
		}
		if (result == null) {
			result = parseDescriptionProject();
		}
		if (result == null) {
			result = parseDescriptionAlert();
		}
		return result;
	}
	
		

	public void initWithContent(String exportedContent) throws Exception {
		reader = new XPathReader(exportedContent);
	}

	public String parseDescriptionFromExportInfo(String path) {
		String expression = ".//exportedItemInfo[@instanceId=\"" + path + "\"]/properties/property[@name=\"custom DESC\"]/@value";
		return parseDescriptionWithExpression(expression);
	}
	
	public List<String> parseExtrefsFromExportInfo(String path) {
		String expression = ".//exportedItemInfo[@instanceId=\"" + path + "\"]/properties/property[@name=\"extrefs\"]/@value";
		List<String> parseDescriptionWithExpressionReturingList = parseDescriptionWithExpressionReturingList(expression);
		return parseDescriptionWithExpressionReturingList;
	}
	
		
}


