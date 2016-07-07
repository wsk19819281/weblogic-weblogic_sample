package com.pierre.jar;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class JarSearcher {
	public static void main(String[] args) throws Exception {
		JarSearcher jarSearcher = new JarSearcher();
		jarSearcher.findTest("C:\\bea103osb", "pipelineMonitoringLevel");
	}

	private void findTest(String dirString, String text) throws ZipException, IOException {
		File dir = new File(dirString);
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				findTest(f.getAbsolutePath(), text);
			}
			else {
				String name = f.getName();
				if (name.endsWith(".jar") || name.endsWith(".zip")) {
					System.out.println("SEARCHING " + f.getAbsolutePath());
					ZipFile zipFile = new ZipFile(f);
					Enumeration<? extends ZipEntry> e =  zipFile.entries();
					while (e.hasMoreElements()) {
						ZipEntry nextElement = e.nextElement();
						InputStream is = zipFile.getInputStream(nextElement);
						if (findInInputStream(is, text)) {
							System.out.println("FOUND ENTRY " + nextElement.getName() + " in file " + f.getAbsolutePath());
						}
					}
				}
				else {
					
				}
			}
		}
	}
	
	
	
	private boolean findInInputStream(InputStream is, String text) throws IOException {
		boolean result = false;
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		while ( (line = br.readLine()) != null && result == false) {
			if (line.contains(text)) {
				result = true;
			}
		}
		br.close();
		is.close();
		return result;
	}
}
