package com.acme.sqlcodegenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Search for 'RR-MM-DD HH24:MI:SS,FF'),-74,0328,40,7439,'
// put the 2 latitude and longitude within ""

public class GeoSQLCodeAdjuster {
	final static String FILE_TO_PROCESS = "C:\\pierre\\Geo\\athirdtime\\insert_geo_site.sql";
	final static String MATCHME = "'RR-MM-DD HH24:MI:SS,FF'),";

	public static void main(String[] args) throws IOException {
		File file = new File(FILE_TO_PROCESS);
		FileWriter fstream = new FileWriter(FILE_TO_PROCESS + ".fixed");
        BufferedWriter out = new BufferedWriter(fstream);
        
        out.write("set define off;\n");
        
		
		BufferedReader input = null;
		try {
			input =  new BufferedReader(new FileReader(file));
		
			int count = 0;
			String line = null;
			while ( (line = input.readLine()) != null) {
				int index = line.indexOf(MATCHME);
				if (index > 0) {
					int endIndex = index + MATCHME.length();
					String before = line.substring(0, endIndex );
					String after = line.substring(endIndex);
					// first 4 positions are lat and long
					String[] splitter = after.split(",");
					// we must consume 4 (or 2, if lat AND long had no commas, or 3 if lat OR long had no comma) commas now
					int countOfExtraCommas = 4;
					if (splitter[2].startsWith("'")) {
						countOfExtraCommas = 2;
					}
					else if (splitter[3].startsWith("'")) {
						countOfExtraCommas = 3;
					}
					for (int i = 0; i < countOfExtraCommas; i++) {
						after = after.substring(after.indexOf(",") + 1);
					}
					String newLine =  null;
					if (countOfExtraCommas == 4) {
						newLine = before + "'" + splitter[0] + "," + splitter[1] + "','" + splitter[2] + "," + splitter[3] + "'," + after;
					}
					else if (countOfExtraCommas == 3){
						newLine = before + "'" + splitter[0] + "','" + splitter[1] + "," + splitter[2]+ "'," + after;
					}
					else if (countOfExtraCommas == 2) {
						newLine = before + "'" + splitter[0] + "','" + splitter[1] + "'," + after;
					}
					out.write(newLine);
					out.write("\n");
				}
				
			}


		} 
		finally {
			if (input != null) {
				input.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
	
	
	

}
