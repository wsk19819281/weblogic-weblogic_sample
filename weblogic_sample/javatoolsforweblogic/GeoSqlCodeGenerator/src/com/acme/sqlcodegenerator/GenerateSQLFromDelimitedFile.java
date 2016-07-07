package com.acme.sqlcodegenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Read records from a delimited file and generates a SQL Insert file
 * First column is the column names
 * @author PVE033
 *
 */
public class GenerateSQLFromDelimitedFile {

	final static String FILE_TO_PROCESS = "testfile.txt";
	final static String TABLE_NAME = "GEO_SITE";
	final static String DELIMITER = "\\|";

	public static void main(String[] args) throws IOException {
		File file = new File(FILE_TO_PROCESS);
		FileWriter fstream = new FileWriter(FILE_TO_PROCESS + ".sql");
        BufferedWriter out = new BufferedWriter(fstream);
		
		BufferedReader input = null;
		try {
			input =  new BufferedReader(new FileReader(file));
			String[] columns = null;
			
			int count = 0;
			String line = null;
			while ( (line = input.readLine()) != null) {

				// title line?
				if (count == 0) {
					System.out.println("TITLES  = " + line);
					columns = line.split(DELIMITER);
					// strip away starting and ending "
					for (int i = 0; i < columns.length; i++) {
						columns[i] = stripAway(columns[i]);
					}
				}
				// data line?
				else {
					String[] values = line.split(DELIMITER);
					StringBuffer production = new StringBuffer();
					production.append("INSERT INTO " + TABLE_NAME + "(");
					int columnCount = 0;
					for (String column : columns) {
						production.append(column);
						columnCount++;
						if (columnCount < columns.length) {
							production.append(",");
						}
					}
					production.append(") VALUES (");
					// actual data generated here
					int valuesCount = 0;
					for (String value : values) {
						// strip away ""
						value = stripAway(value);
						
						value = value.trim();

						// no empty values allowed
						if (value.length() == 0) value= " ";

						// all values in ""
						if (!value.startsWith("\"")) {
							value = "\"" + value;
						}
						if (!value.endsWith("\"")) {
							value =  value + "\"";
						}
						
						
						production.append(value);
						valuesCount++;
						if (valuesCount < values.length) {
							production.append(",");
						}
						
					}
					
					
					production.append(")");
					production.append("\n");
					out.append(production);
				}
				count++;
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

	private static String stripAway(String value) {
		if (value.startsWith("\"")) {
			value = value.substring(1);  
		}
		if (value.endsWith("\"")) {
			value = value.substring(0, value.length() - 1);  
		}
		return value;
	}
}
