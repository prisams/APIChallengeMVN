package com.apiChallenge.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.apiChallenge.ApplicationConfig;

/**
 * Test Class for Application Config
 * @author Priyanka Samanta
 * Date : 9th May 2017
 *
 */
public class ApplicationConfigTest {
	static final String BASE_PATH = "C://Users//Priyanka//";
	static final String PYTHON_EXE = "C://Users//Priyanka//Anaconda3//python";
	static final String SCRIPT1 = BASE_PATH+"pronounce.py";
	static final String SCRIPT2 = BASE_PATH+"classification.py";
	static final String DATAFILE = BASE_PATH+"Data.csv";
		
	
	
	ApplicationConfig config;
	@Before
	/**
	 * initialization of the ApplicationConfig object
	 */
	public void testAplicationConfig(){
		config = new ApplicationConfig();
	}
	
	/**
	 * Test if the path od the Python exe is correct
	 */
	@Test
	public void testGetPythonexepath(){
		String path = config.getPythonexepath();
		assertEquals(PYTHON_EXE+" ",path);
	}

	/**
	 * Test if the path of the Script1 to run the check against CMU dict is correct
	 */
	@Test
	public void testGetPythonScriptNameForRhymingWord(){
		String path = config.getPythonScriptNameForRhymingWord();
		assertEquals(PYTHON_EXE + " " + SCRIPT1 + " ",path);
	}
	
	/**
	 * Test if the path of the Script2 to run the data model is correct
	 */
	@Test
	public void testGetPythonScriptNameForSpeed(){
		String path = config.getPythonScriptNameForSpeed();
		assertEquals(PYTHON_EXE + " " + SCRIPT2 + " " + DATAFILE,path);
	}
	
	/**
	 * Test if the path of the Data File used in the data modelling is correct
	 */
	@Test
	public void testGetFileNameToCreateSpeedModel(){
		String path = config.getFileNameToCreateSpeedModel();
		assertEquals(DATAFILE,path);
	}
}
