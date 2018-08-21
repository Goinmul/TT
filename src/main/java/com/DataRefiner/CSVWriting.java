package com.DataRefiner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.opencsv.CSVWriter;

public class CSVWriting {

	public void writer(ArrayList<String[]> refinedData, String output_file_name) throws IOException
	{
		// ((!))
		// ArrayList<String[]> refined_list = new ArrayList<String[]>();
		// refined_list = refinedData; 
		// wonder if this is good habit or not. #1 directly use / #2 move data via another data structure
		
		// exception handling
		if (refinedData.isEmpty())
		{
			System.out.println("Given data empty - CSVWritting class parameter empty");
			System.exit(1);
		}
		
		try(
				CSVWriter writer = new CSVWriter(new FileWriter(output_file_name));
				){
			// 1) writing the refined data on a csv file, line by line.
			/*
			Iterator<String[]> new_Iterator = refinedData.iterator();
			while(new_Iterator.hasNext())
			{
				String[] string_3 = new_Iterator.next();
				writer.writeNext(string_3);
			}
			*/
			
			// 2) just write the refined ArrayList at the csv file at once.
			writer.writeAll(refinedData);
		}
	
	}
	
}
