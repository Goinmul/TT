package com.DataRefiner.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class) // means TestSuite Class runs a number of test simultaneously(at once).
@Suite.SuiteClasses({CSVReadingWithFilterTest.class, CSVWritingTest.class})
public class TestSuiteClass {
	
	// test suite class

}
