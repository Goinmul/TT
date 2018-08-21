package com.DataRefiner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CSVRefinerLauncherWithOptions {


	public void launcher(String[] args) throws IOException, ParseException
	{
		// 1. Creating options.

		// create Options object and CommandLineParser
		Options options = new Options();

		// add options
		options.addOption("strict", false, "strict refining : get all the FIXED lines, excluding specific lines that is highly FP");

		// create parser (DefaultParser is recommended)
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);

		// HelpFormatter shows the help message. (optional)
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("CSV options", options);



		// 2. Set actions for each option respectively.

		// logic : each option gives String 'refining_option' a different value.
		// At line 68 : temporaryDataBox = myReader.readAndRefiner(input_file_path, refining_option),
		// due to the value of String refining_option, refining method works differently.

		String refining_option = "soft"; // default is soft refining - get all the FIXED lines.

		if (cmd.hasOption("strict")) // if user chooses strict refining, excludes commits end with something like "= null;"
		{
			refining_option = "strict";
		}

		else // I may not fully understand CLI, hence this line might be unnecessary.
			// if so, then you must change the 'refining_option = null' to 'refining_option = soft'.
		{
			System.out.println("No refining option being chosen. Start refining with default mode -> soft refining");
		}


		Scanner myScanner = new Scanner(System.in); 

		// get the input file from user
		CSVReadingWithFilter myReader = new CSVReadingWithFilter();
		String input_file_path = null;
		System.out.print("enter input file path with file name : ");

		input_file_path = myScanner.nextLine(); // ex) c:/jackrabbit.csv



		// Do refining, and temporarily contain the handed data(which is refined) in the 'temporaryDataBox'.
		ArrayList<String[]> temporaryDataBox = new ArrayList<String[]>();
		temporaryDataBox = myReader.readAndRefiner(input_file_path, refining_option);


		// write the output data on the user-chosen csv file.
		CSVWriting myWriter = new CSVWriting();
		String output_file_path = null;
		System.out.print("enter output file path with file name : ");
		output_file_path = myScanner.nextLine(); // ex) d:/jackrabbit_strictly_refined.csv
		
		myWriter.writer(temporaryDataBox, output_file_path); // writing data


		// free resource
		myScanner.close();
		
	}

}
