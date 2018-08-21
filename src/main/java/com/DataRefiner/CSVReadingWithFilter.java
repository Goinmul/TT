package com.DataRefiner;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;


public class CSVReadingWithFilter {

	public ArrayList<String[]> readAndRefiner(String FILE_PATH, String refiningOption) throws IOException
	{
		// main data structure
		ArrayList<String[]> rawData = new ArrayList<String[]>();

		String[] eachLine = null;
		String bug_status = "FIXED";

		// try-with-resources statement
		// in this form, you don't need to free the resources if the instances are in the try block.
		// ( i.e. no need for something like 'reader.close()' )
		try (
				CSVReader reader = new CSVReader(new FileReader(FILE_PATH));
				){
			// Reading in source data(without header)

			// tips)
			// if handling csv file data with header, and you want to skip header, type below line :
			// String[] dump = reader.readNext(); // just consume header line and don't use it
			// but in my logic, I will always check the first element whether it is "FIXED" or not, so this technique is not needed.

			// Reading in with a filter
			if (refiningOption == "soft")
			{
				while( (eachLine = reader.readNext()) != null ) // get each line(row) of csv file to 'String[] eachLine'. 
				{
					if (eachLine[0].equals(bug_status)) // check if the first cell of the line(row) is "FIXED" or not.
					{
						rawData.add(eachLine); // if the first cell is "FIXED", add the whole line to my data structure.
					}

				}// finished refining data. Now ArrayList 'rawData' should contain the refined data.
				
			}

			else if(refiningOption == "strict")
			{
				// logic for excluding commits end with something like "= null;"
				
				// Reason for this option :
				// PMD misunderstands some cases, that it perceives initializing with 'null' or '0' is a "meaningful" value assignment,
				// which causes it to think that later, if a really meaningful value is assigned to that variable, then PMD think of it as a
				// "dd" data flow anomaly. 
				
				/* example)
				 * 
				 * int i = 0;  // meaningless. Just a convention of initialization
				 * 
				 * ...
				 * 
				 * i = 9; // meaningful, 
				 *		  // but PMD thought that '0' was also a meaningful assigning, so it sees this as a "dd" data flow anomaly case.
				 * 
				 */
				
				// below String[] shows some highly False-Positive causing pattern.
				// if you can come up with more, you might add some elements in possible_pattern_group later.
				String[] possible_pattern_group = {"= null;", "=null;", "= 0;", "=0;", "= false;", "=false;","=\"\";", "= \"\";"};
				
				while( (eachLine = reader.readNext())!=null) // get each line(row) of csv file to 'String[] eachLine'.
				{
					if (eachLine[0].equals(bug_status)) // check if the first cell of the line(row) is "FIXED" or not.
					{                                                
						// extract a string, from the last cell of each line in the csv file, for filtering.
						
						// usually eachLine[12] ~ eachLine[15] are empty, and some lines have data at 12th or 13th index.
						// hence I should know what index is the last index that is containing valid data.
						// to do this, I made this while loop, which starts checking from the last cell of String[] eachLine(usually empty),
						// then stops checking until it finds a cell containing valid data.
						int csv_row_index_size = eachLine.length-1;
						while(eachLine[csv_row_index_size].isEmpty()) // keep checking starting from the last index,
						{
							csv_row_index_size--; // then keep diminishing the index for checking
						}
						String last_cell = eachLine[csv_row_index_size]; // after finding, hand over the last cell's data
						
						
						
						// checking the highly FP cases
						boolean highly_FP = false; // default
						for (String possible_pattern : possible_pattern_group) // keep getting each element from String[] possible_pattern.
						{
							if(last_cell.contains(possible_pattern) == true) // check if the last cell contains that pattern.
							{
								highly_FP = true; // if the last cell contains highly_FP pattern, then set the boolean value as 'true'.
							}
						}
						// if the line is judged as a highly FP line(Higly_FP == true), 
						// then do nothing(by not adding the line to my data structure).
						
						if (highly_FP == false) // if highly_FP remains false, then it is assumed not to be a False-positive case.
							rawData.add(eachLine); // only filtered lines are added to the data structure.
					}

				}

			}
			else // reaches this else statement, only when this method gets a parameter other than "soft" or "strict", which should never happen.
			{
				// should never get here!
				System.out.println("refining option null - readAndRefiner method got wrong parameter.");
				System.exit(1);
			}

			if (rawData.isEmpty())
			{
				System.out.println("Input data contains no 'FIXED' lines. Exit program.");
				System.exit(1);
			}

		}

		/* check if my ArrayList contains all the refined data - on the console.
		 *int count = 0;
		 *for (String[] temp : rawData)
		 *{
		 *	for (int i =0 ; i<temp.length;i++)
		 *	{
		 *		System.out.print(temp[i] + " ");
		 *		}
		 *		System.out.println("");
		 *		count++;
		 *	}
		 *	System.out.println("Total fixed line number : " + count);
		 */
		return rawData;

	}

}
