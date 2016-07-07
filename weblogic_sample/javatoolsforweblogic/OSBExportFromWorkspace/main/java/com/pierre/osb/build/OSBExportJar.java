package com.pierre.osb.build;

import static com.pierre.osb.build.ExportHelper.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.pierre.xml.XMLParser;


public class OSBExportJar {

	Comparator<Include> comparatorInclude = new Comparator<Include>() {

		@Override
		public int compare(Include o1, Include o2) {
			return o1.schemaLocation.compareTo(o2.schemaLocation);
		}
	};

	Comparator<Import> comparatorImport = new Comparator<Import>() {

		@Override
		public int compare(Import o1, Import o2) {
			return o1.schemaLocation.compareTo(o2.schemaLocation);
		}
	};





	public enum EXTENSIONS {
		sa("ServiceAccount", "com.bea.wli.sb.svcacct.StaticServiceAccountConfig", "25"), 
		mqc("MQConnection", "com.bea.wli.sb.resources.custom.ResourceDataObject", "30"), 
		biz("BusinessService", "com.bea.wli.sb.services.impl.ServiceDefinitionImpl", "3000"), 
		proxy("ProxyService", "com.bea.wli.sb.services.impl.ServiceDefinitionImpl", "3000"), 
		xq("Xquery", "com.bea.wli.sb.resources.config.impl.XqueryEntryDocumentImpl", "0"), 
		alert("AlertDestination", "com.bea.wli.monitoring.alert.impl.AlertDestinationImpl", "30"), 
		xsd("XMLSchema", "com.bea.wli.sb.resources.config.impl.SchemaEntryDocumentImpl", "0"), 
		wsdl("WSDL", "com.bea.wli.sb.resources.config.impl.WsdlEntryDocumentImpl", "0"),
		ExportInfo("ExportInfo", "", ""),
		LocationData("LocationData", "com.bea.wli.config.project.impl.LocationDataImpl", "0");
		
		
		private final String longName;
		private final String implementation;
		private final String representationversion;

		EXTENSIONS(String longName, String implementation, String representationversion) {
			this.longName = longName;
			this.implementation = implementation;
			this.representationversion = representationversion;
		}

		public String getLongName() {
			return longName;
		}

		public String getImplementation() {
			return implementation;
		}

		public String getRepresentationversion() {
			return representationversion;
		}
	}

	/* list of files to be processed */
	ListResource resources = new ListResource();

	String theWorkspace = null; 

	public static void main(String[] args) throws Exception {
		String previous = System.getProperty("line.separator");
		System.setProperty("line.separator", "\n");
		OSBExportJar exportJar = new OSBExportJar();
		exportJar.export("C:/pierre/workspaceSVN/", "GlobalResources,GUTools,InterfacesGU,InterfacesNIS,MockNIS", "c:/tmp/", "pvexport.jar");
		System.setProperty("line.separator", previous);
	}

	public void export(String workspace, String projectList, String destinationFolder, String destinationJar) throws Exception {
		theWorkspace = workspace;
		// check for existence of workspace
		File workspaceDir = new File(workspace);
		if (!workspaceDir.exists() || !workspaceDir.isDirectory()) {
			throw new IllegalArgumentException("invalid dir " + workspace);
		}
		// create destination folder
		File destinationFolderDir = new File(destinationFolder);
		if (!destinationFolderDir.exists() || !destinationFolderDir.isDirectory()) {
			throw new IllegalArgumentException("invalid dir " + destinationFolder);
		}
		// remove existing garbage in destination folder
		File temporaryOutDir1 = new File(destinationFolder + "osbjarexport");
		if (temporaryOutDir1.exists()) {
			deleteDir(temporaryOutDir1);
		}
		//create brand new destination folder
		File temporaryOutDir2 = new File(destinationFolder + "osbjarexport");
		temporaryOutDir2.mkdir();

		// build project list
		String[] list = projectList.split(",");
		ArrayList<String> projectsToExport = new ArrayList<String>(Arrays.asList(list));

		// build resources list
		for (File dir : workspaceDir.listFiles()) {
			String projectName = dir.getName();
			if (dir.isDirectory() && projectsToExport.contains(projectName)) {
				System.out.println("scanning for resources " + dir.getAbsolutePath());
				resources.add(createLocationDataResource(dir, false, projectName));
				scanAllFilesRecursively(dir, projectName);
			}
		}

		processResources(resources);
		System.out.println(resources.toStringShort());
		Resource exportInfo = new Resource(EXPORT_INFO, EXPORT_INFO, null);
		exportInfo.setExportedContent(this.buildExportInfo());
		exportInfo.setExportInfo(true);
		resources.add(exportInfo);
		createZip(resources, destinationJar);

	}


	private void processResources(ListResource resources2) throws Exception {
		for (Resource res : resources2) {
			process(res);
		}
	}

	/*
	 * Add all files to resource collection
	 */
	private void scanAllFilesRecursively(File dir, String projectName) throws Exception {

		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				String dirName = file.getName();
				if (!dirName.startsWith(".")) {
					resources.add(createLocationDataResource(file, true, projectName));
					scanAllFilesRecursively(file, projectName);
				}
			}
			else {
				String fileName = file.getName();
				// make sure you exclude .project 
				if (!fileName.startsWith(".") && (!fileName.equals(EXPORT_INFO))) {
					String absolutePath = file.getAbsolutePath().replaceAll("\\\\", "/");
					Resource resource = new Resource(file.getAbsolutePath(), absolutePath.substring(theWorkspace.length()), getExtensionFromFilename(fileName));
					resource.setOriginalContent(readFileContent(file));
					resource.setProjectName(projectName);
					resources.add(resource);
				}
			}
		}

	}


	private Resource createLocationDataResource(File outDir, boolean isFolderData, String projectName) throws Exception {
		String filename = isFolderData? "_folderdata.LocationData" : "_projectdata.LocationData";
		String fileContent = XML_PREAMBLE + "\n<proj:isImmutable xmlns:proj=\"http://www.bea.com/wli/config/project\">false</proj:isImmutable>";
		// String fullAbsolutePath, String path, EXTENSIONS type
		String absolutePathLocationData = outDir.getAbsolutePath() + "/" + filename;
		String absolutePath = absolutePathLocationData.replaceAll("\\\\", "/");
		Resource resource = new Resource(absolutePathLocationData, absolutePath.substring(theWorkspace.length()),EXTENSIONS.LocationData);
		resource.setOriginalContent(fileContent);
		resource.setExportedContent(fileContent);
		resource.setProjectName(projectName);
		resource.setPhysical(false);
		return resource;
	}	


	// Deletes all files and subdirectories under dir.
	// Returns true if all deletions were successful.
	// If a deletion fails, the method stops attempting to delete and returns false.
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}

	
	
	

	
	// Copies src file to dst file.
	// If the dst file does not exist, it is created
	void process(Resource res) throws Exception {
		EXTENSIONS ext = res.getType();
		List<String> xqLocations = new ArrayList<String>();
		
		List<Include> xsdIncludes = new ArrayList<Include>();
		List<Import> xsdImports = new ArrayList<Import>();
		List<Import> wsdlXsdImports = new ArrayList<Import>();
		List<Import> wsdlImports = new ArrayList<Import>();
		List<String> bizWSDL = new ArrayList<String>();
		
		String wsdlNamespace = null;
		String xsdNamespace = null;
		StringBuilder writer = new StringBuilder();

		switch (ext) {
		case xq:
			writer.append(XML_PREAMBLE + "\n<con:xqueryEntry xmlns:con=\"http://www.bea.com/wli/sb/resources/config\">\n<con:xquery><![CDATA[");
			break;
		case xsd:
			writer.append(XML_PREAMBLE + "\n<con:schemaEntry xmlns:con=\"http://www.bea.com/wli/sb/resources/config\">\n    <con:schema><![CDATA[");
			break;
		case wsdl:
			writer.append(XML_PREAMBLE + "\n<con:wsdlEntry xmlns:con=\"http://www.bea.com/wli/sb/resources/config\">\n<con:wsdl><![CDATA[");
		}

		// check for "pragma bea:global-element-parameter"

		String line = null;
		boolean writeNewLine = false;
		BufferedReader reader = new BufferedReader(new StringReader(res.getOriginalContent()));
		while ((line = reader.readLine()) != null) {
			switch (ext) {
			case xq:
				String location = getLocationFromPragma(line);
				if (location != null && !xqLocations.contains(location)) xqLocations.add(location);

				break;

			case xsd:
				Include dependencyInclude = getDependencyFromInclude(line);
				if (dependencyInclude != null) xsdIncludes.add(dependencyInclude);
				Import dependencyImport = getDependencyFromImport(line);
				if (dependencyImport != null) xsdImports.add(dependencyImport);

				String xsdGuessNamespace = findNamespace(line);
				if (xsdGuessNamespace != null && xsdNamespace == null) {
					xsdNamespace = xsdGuessNamespace;
				}					
				break;

			case wsdl:
				String wsdlGuessNamespace = findNamespace(line);
				if (wsdlGuessNamespace != null && wsdlNamespace == null) {
					wsdlNamespace = wsdlGuessNamespace;
				}
				//  <wsdl:import namespace="http://www.infrabel.be/A204/Nis" location="InfraServices0.wsdl"/>
				Import wsdlXsdDependencyImport = getXSDWSDLDependencyFromImport(line);
				if (wsdlXsdDependencyImport != null) wsdlXsdImports.add(wsdlXsdDependencyImport);
				Import wsdlDependencyImport = getWSDLDependencyFromImport(line);
				if (wsdlDependencyImport != null) wsdlImports.add(wsdlDependencyImport);
				break;
			case biz:
				String wsdlRef = findBizWSDL(line);
				if (wsdlRef != null) {
					bizWSDL.add(wsdlRef);
				}
				
				break;				
			}
			if (writeNewLine) {
				writer.append("\n");   // Write system dependent end of line.
			}
			writer.append(line);
			writeNewLine = true;
		}


		Collections.sort(xsdIncludes, comparatorInclude);
		//Collections.sort(xsdImports, comparatorImport);


		switch (ext) {
		case xq:
			writer.append("]]></con:xquery>\n");
			for (String loc : xqLocations) {
				writer.append("\n<con:dependency location=\"" + loc + "\"/>");
			}
			writer.append("\n</con:xqueryEntry>");
			break;
		case xsd:
			writer.append("]]></con:schema>\n");
			boolean hasDependencies = (xsdImports.size() > 0) || (xsdIncludes.size() > 0);
			if (hasDependencies) {
				writer.append("    <con:dependencies>");
			}
			for (Import myImport : xsdImports) {
				writer.append("\n        <con:import namespace=\"" + myImport.namespace + "\" schemaLocation=\"" + myImport.schemaLocation + "\" ref=\"" + myImport.ref + "\"/>");
			}
			for (Include myInclude : xsdIncludes) {
				writer.append("\n        <con:include schemaLocation=\"" + myInclude.schemaLocation + "\" ref=\"" + myInclude.ref + "\"/>");
			}
			if (hasDependencies) {
				writer.append("\n    </con:dependencies>");
			}
			if (xsdNamespace != null) {
				writer.append("\n    <con:targetNamespace>" + xsdNamespace + "</con:targetNamespace>");
			}

			//writer.write("<con:targetNamespace>http://schemas.microsoft.com/2003/10/Serialization/Arrays</con:targetNamespace>\n");
			writer.append("\n</con:schemaEntry>");
			break;
		case wsdl:
			writer.append("]]></con:wsdl>");
			if ( (wsdlXsdImports.size() > 0) || (wsdlImports.size() > 0) ) {
				writer.append("\n    <con:dependencies>");
				for (Import imp : wsdlXsdImports) {
					writer.append("\n        <con:schemaRef isInclude=\"false\" schemaLocation=\"" + imp.schemaLocation + "\" namespace=\"" + imp.namespace + "\" ref=\"" + imp.ref + "\"/>");
				}
				for (Import imp : wsdlImports) {
					writer.append("\n        <con:import location=\"" + imp.schemaLocation + "\" namespace=\"" + imp.namespace + "\">");
					writer.append("\n        <con:wsdl ref=\"" + imp.ref + "\"/>");
					writer.append( "\n</con:import>");

				}				
				writer.append("\n    </con:dependencies>");
				if (wsdlNamespace != null) {
					writer.append("\n<con:targetNamespace>" + wsdlNamespace + "</con:targetNamespace>");
				}
			}

			writer.append("\n</con:wsdlEntry>");
		}
		res.setExportedContent(writer.toString());
		
		for (Import imp : xsdImports) res.addDependency("XMLSchema$" + imp.ref.replaceAll("//", "$"));
		for (Include inc : xsdIncludes) res.addDependency("XMLSchema$" + inc.ref.replaceAll("//", "$"));
		for (Import imp : wsdlImports) res.addDependency("WSDL$" + imp.ref.replaceAll("//", "$"));
		for (Import imp : wsdlXsdImports) res.addDependency("WSDL$" + imp.ref.replaceAll("//", "$"));
		for (String ref : bizWSDL) res.addDependency(ref);

		// read extra info from the XML
		if (res.isPhysical()) {
			XMLParser xmlParser = new XMLParser();
			xmlParser.init(res.getFullAbsolutePath());
			res.setDescription(xmlParser.parseDescription());
		}
		

	}
	
	
	//  <xsd:schema targetNamespace="http://www.infrabel.be/A204/Nis/Imports">
	private String findNamespace(String line) {
		String result = null;
		if (line.contains("schema") && line.contains("targetNamespace=")) {
			result = getPropertyValueFromAnything(line, "targetNamespace=");
		}
		return result;
	}
	
	//  <con1:wsdl ref="InterfacesNIS/InfraService/InfraServices"/>
	private String findBizWSDL(String line) {
		String result = null;
		if (line.contains("con1:wsdl") && line.contains("ref=")) {
			result = getPropertyValueFromAnything(line, "ref=");
		}
		return result;
	}
		
	

	private Import getXSDWSDLDependencyFromImport(String maybeImport) {
		Import result = null;
		if ( maybeImport.contains("xsd:import") && maybeImport.contains("namespace") && maybeImport.contains("schemaLocation") ) {
			String namespace = getPropertyValueFromAnything(maybeImport, "namespace=");
			if (namespace != null) {
				result = new Import();
				result.namespace = namespace;
				result.schemaLocation = getPropertyValueFromAnything(maybeImport, "schemaLocation=");
				result.ref = getRefForSchema(result.schemaLocation);
			}
		}
		return result;		
	}

	private Import getWSDLDependencyFromImport(String maybeImport) {
		Import result = null;
		if ( maybeImport.contains("wsdl:import") && maybeImport.contains("namespace") && maybeImport.contains("location") ) {
			String namespace = getPropertyValueFromAnything(maybeImport, "namespace=");
			if (namespace != null) {
				result = new Import();
				result.namespace = namespace;
				result.schemaLocation = getPropertyValueFromAnything(maybeImport, "location=");
				result.ref = getRefForSchema(result.schemaLocation);
			}
		}
		return result;		
	}	

	// <xs:include schemaLocation="TrainType.xsd"/>
	private Include getDependencyFromInclude(String maybeInclude) {
		Include result = null;
		if ( maybeInclude.contains("include") && maybeInclude.contains("schemaLocation=") ) {
			String schemaLocation = getPropertyValueFromAnything(maybeInclude, "schemaLocation=");
			if (schemaLocation != null) {
				result = new Include();
				result.schemaLocation = schemaLocation;
				result.ref = getRefForSchema(schemaLocation);
			}
		}
		return result;		
	}

	// input is ../CommonSchemas/Serialization.xsd, output is InterfacesNIS/CommonSchemas/Serialization
	public String getRefForSchema(String schemaLocation) {
		int beginIndex = schemaLocation.lastIndexOf("/");

		String refName = schemaLocation.substring(beginIndex + 1);
		String resource = lookupResource(refName);
		return resource;
	}

	private String lookupResource(String refName) {
		String result = null;
		if (refName.endsWith("Serialization.xsd")) {
			result = null;
		}
		try {
			for (String res : resources.getAllPaths()) {
				if (res.endsWith("/" + refName)) {
					result = res;
					break;
				}
			}
			if (result.endsWith(".xsd")) {
				result = result.substring(0, result.length() - ".xsd".length());
			}
			if (result.endsWith(".wsdl")) {
				result = result.substring(0, result.length() - ".wsdl".length());
			}
		}
		catch (Exception e) {
			System.out.println(refName + " could not be found");
		}
		return result;
	}

	// <xs:import schemaLocation="../CommonSchemas/Serialization.xsd" namespace="http://schemas.microsoft.com/2003/10/Serialization/"/>
	private Import getDependencyFromImport(String maybeInclude) {
		Import result = null;
		if ( maybeInclude.contains("import") && maybeInclude.contains("schemaLocation=") ) {

			String schemaLocation = getPropertyValueFromAnything(maybeInclude, "schemaLocation=");
			if (schemaLocation != null) {
				result = new Import();
				result.schemaLocation = schemaLocation;
				result.namespace = getPropertyValueFromAnything(maybeInclude, "namespace=");
				result.ref = getRefForSchema(schemaLocation);
			}
		}
		return result;		
	}

	public static String getPropertyValueFromAnything(String maybeInclude, String anything) {
		String result = null;
		maybeInclude = maybeInclude.replaceAll("\"", "'");
		if (maybeInclude.contains(anything)) {
			int index1 = maybeInclude.indexOf(anything);
			String afterString = maybeInclude.substring(index1 + anything.length() + 1);
			int index2 = afterString.indexOf("'");
			result = afterString.substring(0, index2);
		}
		return result;		
	}	

	// (:: pragma bea:global-element-parameter parameter="$retrieveTrainDetailsByIdResponse1" element="ns0:RetrieveTrainDetailsByIdResponse" location="../../InterfacesNIS/SearchByTrainService/NIS.xsd" ::)

	public static String getLocationFromPragma(String maybePragma) {
		String result = null;
		if (maybePragma.contains("pragma bea:global-element")) {
			String locationConstant = " location=\"";
			int index1 = maybePragma.indexOf(locationConstant);
			String afterString = maybePragma.substring(index1 + locationConstant.length());
			int index2 = afterString.indexOf("\"");
			result = afterString.substring(0, index2);
		}
		return result;
	}


	class Import {
		String namespace;
		String schemaLocation;
		String ref;
	}

	class Include {
		String schemaLocation;
		String ref;
	}


	private String buildExportInfo() throws Exception {
		StringBuilder result = new StringBuilder();
		result.append(XML_PREAMBLE + "\n");
		result.append("<xml-fragment name=\"\" version=\"v2\">");
		result.append("\n      <imp:properties xmlns:imp=\"http://www.bea.com/wli/config/importexport\">");
		result.append("\n            <imp:property name=\"username\" value=\"pierluigi\"/>");
		result.append("\n            <imp:property name=\"description\" value=\"\"/>");
		result.append("\n            <imp:property name=\"exporttime\" value=\"Tue Jul 26 10:31:07 CEST 2011\"/>");
		result.append("\n            <imp:property name=\"productname\" value=\"Oracle Service Bus\"/>");
		result.append("\n            <imp:property name=\"productversion\" value=\"11.1.1.4\"/>");
		result.append("\n            <imp:property name=\"projectLevelExport\" value=\"true\"/>");
		result.append("\n      </imp:properties>");

		for (Resource res: resources) {
			result.append("\n<imp:exportedItemInfo instanceId=\"" + res.getPathNoExtension() + "\" typeId=\"" + res.getType().getLongName() +  "\" xmlns:imp=\"http://www.bea.com/wli/config/importexport\">");

			result.append("\n<imp:properties>");
			result.append("\n<imp:property name=\"representationversion\" value=\"" + res.getType().getRepresentationversion() + "\"/>");
			result.append("\n<imp:property name=\"dataclass\" value=\"" + res.getType().getImplementation() + "\"/>");
			result.append("\n<imp:property name=\"isencrypted\" value=\"false\"/>");
			result.append("\n<imp:property name=\"jarentryname\" value=\"" + res.getPathNewExtension() + "\"/>");
			
			for (String dep : res.getDependencies()) {
				result.append("\n<imp:property name=\"extrefs\" value=\"" + dep + "\"/>");
			}
			
			result.append("\n</imp:properties>");
			result.append("\n</imp:exportedItemInfo>");

		}
		result.append("\n</xml-fragment>");

		return result.toString();
	}



}
