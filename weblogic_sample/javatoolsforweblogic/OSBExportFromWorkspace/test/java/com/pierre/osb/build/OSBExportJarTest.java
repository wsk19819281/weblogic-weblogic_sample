package com.pierre.osb.build;

import static org.junit.Assert.*;
import static com.pierre.osb.build.ExportHelper.*;

import org.junit.Test;

import com.pierre.osb.build.OSBExportJar.EXTENSIONS;


public class OSBExportJarTest {

	@Test
	public void getLocationFromPragmatest() {
		String result = OSBExportJar.getLocationFromPragma("(:: pragma bea:global-element-parameter parameter=\"$retrieveTrainDetailsByIdResponse1\" element=\"ns0:RetrieveTrainDetailsByIdResponse\" location=\"../../InterfacesNIS/SearchByTrainService/NIS.xsd\" ::)");
		assertNotNull(result);
		assertEquals("../../InterfacesNIS/SearchByTrainService/NIS.xsd", result);
	}
	
	@Test
	public void getDependencyFromAnythingtest() {
		String result = OSBExportJar.getPropertyValueFromAnything("<xs:include schemaLocation=\"TrainType.xsd\"/>", "include schemaLocation=");
		assertNotNull(result);
		assertEquals("TrainType.xsd", result);
	}
	
	@Test
	public void testRegexpor() {
		String test = "<xsd:import namespace=\"http://www.infrabel.be/A204/Nis\" schemaLocation=\"InfraService.xsd\"/>";
		boolean cont = test.matches(".*import.*namespace.*schemaLocation.*");
		assertTrue(cont);
		cont = test.matches(".*import.*namispace.*schemaLocation.*");
		assertFalse(cont);
		
	}
	
	public void testgetPathBeforeExtension() {
		assertEquals("InterfacesNIS/InfraService/InfraService", getPathBeforeExtension("InterfacesNIS/InfraService/InfraService.xsd"));
		assertEquals("GlobalResources/_projectdata", getPathBeforeExtension("GlobalResources/_projectdata.LocationData"));
		
	}
	
	public void testreplaceExtension() throws Exception {
		assertEquals("InterfacesNIS/InfraService/InfraService.XMLSchema", replaceExtension("InterfacesNIS/InfraService/InfraService.xsd"));
	}

	public void testGetPathNewExtension() throws Exception {
		Resource res = new Resource("c:/pierre/InterfacesNIS/InfraService/InfraService.xsd", "InterfacesNIS/InfraService/InfraService.xsd", EXTENSIONS.xsd);
		assertEquals("InterfacesNIS/InfraService/InfraService.XMLSchema", res.getPathNewExtension());
	}
}
