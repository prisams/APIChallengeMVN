package com.apiChallenge;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Class to create the different web services
 * @author Priyanka Samanta
 * Date : 9th May 2017
 *
 */
@Path("api")
public class API {

	private static final Logger LOG = Logger.getLogger(API.class.getName());
	static final String ERR_MSG_GET_RANDOM_WORD = "Invalid input.Please enter atleast 2 words seperated by spaces";
	static final String ERR_MSG_FOR_RHYMING_WORD = "No rhyming words found.";
	static final String ERR_MSG_FOR_RECKLESS_SPEED = "Invalid input. Please enter a valid speed.";
	static final String OP_MSG_FOR_SPEED_CHECK_DEFAULT = "Your driving speed is safe.";
	static final String OP_MSG_FOR_RECKLESS_SPEED = "Reckless driving speed.";

	ApplicationConfig config = new ApplicationConfig();


	/**
	 * Method to test the connection
	 * @return String 
	 */
	@GET
	@Path("/testConnection")
	@Produces(MediaType.TEXT_PLAIN)
	public String testConnection(){
		return "Pinged!";
	}
	
	/**
	 * Method to return a random word from the set of words given as input separated by space
	 * @param inputString
	 * @return random word from the set of words
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/getRandomWord/{words}")
	public String getRandomWord( @QueryParam("words") String inputString){
		LOG.info("Incoming input:" + inputString);
		Random random = new Random();
		ArrayList<String> setOfWords = new ArrayList<String>();
		setOfWords.addAll(Arrays.asList(inputString.trim()
				.replaceAll("\\s+"," ").split(" ")));
		if(setOfWords.size()<2){
			LOG.info(ERR_MSG_GET_RANDOM_WORD);
			return ERR_MSG_GET_RANDOM_WORD;
		}
		return setOfWords.get(random.nextInt(setOfWords.size()));
	}

	/**
	 * Method to return the rhyming words from CMU dictionary based on the word given as input
	 * @param word
	 * @return rhyming words
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/getRhymingWords/{word}")
	public String getRhymingWords( @PathParam ("word") String word){
		LOG.info("Incoming input:" + word);
		Process process = null;
		String output = null;
		try {
			process = Runtime.getRuntime().exec(config.getPythonScriptNameForRhymingWord() + word);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			output = reader.readLine();
			reader.close();
		}
		catch(Exception e){
			LOG.log(Level.ALL, "Exception thrown", e);
		}
		return output.equals("[]")? ERR_MSG_FOR_RHYMING_WORD : output;
	}

	/**
	 * Method to determine if the speed given as input belongs to reckless driving or not
	 * based on the computation from the data model
	 * @param speed
	 * @return message if the speed is reckless or not
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/isReckless/{speed}")
	public String isReckless(@PathParam ("speed") String speed){

		LOG.info("Incoming speed:" + speed);
		String output = OP_MSG_FOR_SPEED_CHECK_DEFAULT;
		int inputSpeed=0;
		Process process = null;
		double threshold = 0;
		try {
			inputSpeed = Integer.parseInt(speed);
			process = Runtime.getRuntime().exec(config.getPythonScriptNameForSpeed());
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			threshold = Double.parseDouble(reader.readLine());
			reader.close();
		}
		catch(NumberFormatException e){
			LOG.log(Level.SEVERE, ERR_MSG_FOR_RECKLESS_SPEED);
			output = ERR_MSG_FOR_RECKLESS_SPEED;
		}
		catch(Exception e){
			LOG.log(Level.ALL, "Exception thrown", e);
		}
		if(inputSpeed>threshold){
			output = OP_MSG_FOR_RECKLESS_SPEED;
		}
		return output;
	}
}