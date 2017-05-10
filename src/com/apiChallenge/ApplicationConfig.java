package com.apiChallenge;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to get the values from the settings.properies file
 * @author Priyanka Samanta
 * Date : 9th May 2017
 *
 */
public class ApplicationConfig {

	private static final Logger LOG = Logger.getLogger(ApplicationConfig.class.getName());
	File configFile = new File(getClass().
			getResource("/com/apiChallenge/resources/settings.properties").getFile());
	
	Properties props = new Properties();
	public ApplicationConfig(){
		FileReader reader;
		try {
			reader = new FileReader(configFile);
			props.load(reader);
		} 
		catch (IOException e) {
			
			LOG.log(Level.SEVERE, "Exception thrown", e);
		}
	}
	/**
	 * Method to return the path of the Python executible
	 * @return
	 */
	public String getPythonexepath() {
		return props.getProperty("pythonexe") + " ";	
	}

	/**
	 * Method to return the script that calls the CMU dictionary
	 * @return script path
	 */
	public String getPythonScriptNameForRhymingWord(){
		return getPythonexepath()+ 
				props.getProperty("pythonscriptrhyme") + " ";
	}

	/**
	 * Method to return the Script that runs the dataModel to determine  the threshold 
	 * of reckless speed
	 * @return script path
	 */
	public String getPythonScriptNameForSpeed(){
		return getPythonexepath() +
				props.getProperty("pythonscriptthreshold") + " " + 
				getFileNameToCreateSpeedModel();
	}

	/**
	 * Method to return the path of the data file
	 * @return Data file path
	 */
	public String getFileNameToCreateSpeedModel(){
		return props.getProperty("datafile");
	}
}
