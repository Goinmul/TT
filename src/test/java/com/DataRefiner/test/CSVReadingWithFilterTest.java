package com.DataRefiner.test;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import org.junit.Ignore;
import org.junit.Test;

import com.DataRefiner.CSVReadingWithFilter;
/**
 * in this test case, the given test data directory should be modified
 * regarding your own test cases.
 * 
 * @author HyunWoo Kwon
 */
public class CSVReadingWithFilterTest {

	// input : a csv file
	// output : a refined csv file

	//tested method
	CSVReadingWithFilter instance1 = new CSVReadingWithFilter();

	// test for "soft" refining
	@Test public void refining_test_soft() throws IOException
	{
		ArrayList<String[]> output_from_method = new ArrayList<String[]>();
		String[] temp = null;
		String bug_status = "FIXED";
		
		try {
			// ((!)) must put the right path before testing.
			output_from_method = instance1.readAndRefiner("c:/apex-core.csv", "soft"); // given test data
			
			// main data structure
			// get refined data that had gone through the testing method
			ListIterator<String[]> tester = output_from_method.listIterator(output_from_method.size());
			
			// test
			// if all the output data contains only "FIXED" at its first index, then wrong_data_check stays false.
			// when wrnog_data_check is false, that means all the output data's first string is "FIXED", where no error exists.
			// hence when we try assertFalse(), if wrong_data_check is true, which means error exists, then this test fails.
			boolean wrong_data_check = false;
			
			// iterate with checking
			while(tester.hasNext())
			{
				temp = tester.next();
				if (temp[0] != bug_status)
				{
					wrong_data_check = true;
					break;
				}
			}
			assertFalse(wrong_data_check);
			
		} catch (IOException e)
		{
			System.out.println("input file not found.");
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	//test for strict refining
	@Test public void refining_test_strict() throws IOException
	{
		ArrayList<String[]> output_from_method = new ArrayList<String[]>();
		String[] temp = null;
		String bug_status = "FIXED";
		
		try {
			// ((!)) must put the right path before testing.
			output_from_method = instance1.readAndRefiner("c:/apex-core.csv", "strict"); // given test data
			
			// main data structure
			// get refined data that had gone through the testing method
			ListIterator<String[]> tester = output_from_method.listIterator(output_from_method.size());

			// test two cases
			// 1) "FIXED" lines only check (same logic as above test)
			// 2) last index False Positive or not check
			String[] possible_pattern_group = {"= null;", "=null;", "= 0;", "=0;", "= false;", "=false;","=\"\";", "= \"\";"};
			
			// default
			boolean wrong_data_check = false; 
			boolean highly_FP = false;
			
			while(tester.hasNext()) // run loops with checking line by line
			{
				temp = tester.next(); // temp gets each single line from test data
				
				// 1) "FIXED" lines only check
				if (temp[0] != bug_status)
				{
					wrong_data_check = true;
					break;
				}
				
				// 2) last index False Positive or not check 
				int csv_row_index_size = temp.length-1;
				
				while(temp[csv_row_index_size].isEmpty()) // keep checking starting from the last index,
				{
					csv_row_index_size--; // then keep diminishing the index for checking
				}
				String last_cell = temp[csv_row_index_size]; // after finding, hand over the last cell's data
				
				// checking the highly FP cases
				for (String possible_pattern : possible_pattern_group) // keep getting each element from String[] possible_pattern.
				{
					if(last_cell.contains(possible_pattern) == true) // check if the last cell contains that pattern.
					{
						highly_FP = true; // if the last cell contains highly_FP pattern, then set the boolean value as 'true'.
					}
				}
			}
			assertFalse(wrong_data_check);
			assertFalse(highly_FP);
			
		} catch (IOException e)
		{
			System.out.println("input file not found.");
			e.printStackTrace();
		}
		
	}

}
