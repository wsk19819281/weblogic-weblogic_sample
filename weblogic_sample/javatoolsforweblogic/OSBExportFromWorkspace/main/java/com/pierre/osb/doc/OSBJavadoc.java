package com.pierre.osb.doc;

import static com.pierre.osb.build.ExportHelper.getContent;
import static com.pierre.osb.build.ExportHelper.getExtensionFromFilenameFromLong;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.pierre.osb.build.ExportHelper;
import com.pierre.osb.build.ListResource;
import com.pierre.osb.build.OSBExportJar.EXTENSIONS;
import com.pierre.osb.build.Resource;
import com.pierre.xml.XMLParser;

/**
 * Scans a sbconfig.jar and prints the javadoc for the project
 * @author EXB866
 *
 */

public class OSBJavadoc {

	public static void main(String[] args)  throws Exception {
		OSBJavadoc osbJavadoc = new OSBJavadoc();
		osbJavadoc.produceJavadoc("C:/pierre/downloads/sbconfig08August.jar", "c:/temp/osbjavadoc");
	}

	public void produceJavadoc(String jarfile, String destinationDir) throws Exception {
		ListResource resources = new ListResource();
		ZipInputStream in = new ZipInputStream(new FileInputStream(jarfile));
		ZipEntry entry = null;
		while ( (entry = in.getNextEntry()) != null ) {
			String name = entry.getName();
			Resource resource = new Resource("", name, getExtensionFromFilenameFromLong(name));
			resource.setExportedContent(getContent(in, entry));
			resources.add(resource);
		}

		Resource exportInfo = resources.getExportInfo();

		// populate resources with info from ExportInfo
		for (Resource resource: resources) {
			if (!resource.isExportInfo()) {
				XMLParser xmlParser = new XMLParser();
				xmlParser.initWithContent(resource.getExportedContent());
				String parseDescription = xmlParser.parseDescription();
				if (parseDescription == null) {
					parseDescription = getExportInfoParser(exportInfo).parseDescriptionFromExportInfo(resource.getPathNoExtension());
				}
				resource.setDescription(parseDescription);

				List<String> parseExtrefsFromExportInfo = getExportInfoParser(exportInfo).parseExtrefsFromExportInfo(resource.getPathNoExtension());
				resource.setDependenciesRemapping(parseExtrefsFromExportInfo);
			}
		}

		// generate report
		log("<html><head></head><body>");
		for (Resource res : resources) {
			log("<a id=\"" + noFunnyCharacters(res.getPathReverseExtension()) + "\">");
			log("<b>" + res.getPath() + "</b>");
			log("</a>");
			log("<i>" + res.getDescription() + "</i>");
			log("<br/>Uses:<br/>");
			for (String ref : res.getDependencies()) {
				log("&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#" + noFunnyCharacters(ref) + "\">" + ref + "</a>");
				log("<br/>");
			}
			log("<p/>");
			log("Is used by:<br/>");
			for (Resource usingRes : resources.findResourcesUsing(res)) {
				log("&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#" + noFunnyCharacters(usingRes.getPathReverseExtension()) + "\">" + usingRes.getPath() + "</a>");
				log("<br/>");
			}
			log("<p/><hr/>");
		}
		log("</body></html>");

		//System.out.println(resources.toStringShort());

	}

	private String noFunnyCharacters(String ref) {
		String result = ref.replaceAll("/", "");
		result = result.replaceAll("\\.", "");
		return result;
	}

	private void log(String message) {
		System.out.println(message);

	}



	private XMLParser getExportInfoParser(Resource exportInfo) throws Exception {
		XMLParser exportInfoXmlParser = new XMLParser();
		exportInfoXmlParser.initForExportInfo(exportInfo.getExportedContent());
		return exportInfoXmlParser;
	}

}
