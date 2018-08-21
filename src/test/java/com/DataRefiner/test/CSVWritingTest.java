package com.DataRefiner.test;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.DataRefiner.CSVWriting;
/**
 * a dump file will be created after testing.
 * You might want to delete that file. (file directory : line 44)
 * 
 * @author HyunWoo Kwon
 *
 */
public class CSVWritingTest {
	
	// input : a single ArrayList<String[]>
	// output : a csv file
	
	// in the file directory, a test_file will be created. So you have to delete it later.
	File file = new File("d:/writing_test_output.csv");
	
	//tested method
	CSVWriting instance1 = new CSVWriting();
	
	//test data
	ArrayList<String[]> sample_csv_source = new ArrayList<String[]>();
	
	@Test
	public void writing_test() throws IOException {
		
		String[] element1 = {"FIXED", "1"};
		String[] element2 = {"FIXED","2","apple"};
		
		sample_csv_source.add(element1);
		sample_csv_source.add(element2);
		// #1 when empty file is put - handled in the method
		
		// #2 when a normal csv file is put
		instance1.writer(sample_csv_source, "d:/writing_test_output.csv");
		
		//check if the written file exists or not
		assertNotNull(file.exists());
		
	}

}
