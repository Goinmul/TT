package com.DataRefiner;

import java.io.IOException;

import org.apache.commons.cli.ParseException;

/**
 * CSV file refining program
 * default : get all the lines having first cell equals to "FIXED"
 * option 'strict' : same as default, but excluding highly false positive case of data flow anomaly analysis.
 * 
 * @author HyunWoo Kwon
 * 
 */
public class CSVMain {

	public static void main(String[] args) throws IOException, ParseException {
		
		CSVRefinerLauncherWithOptions run = new CSVRefinerLauncherWithOptions();
		
		try {
			
		run.launcher(args);
		
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

}
