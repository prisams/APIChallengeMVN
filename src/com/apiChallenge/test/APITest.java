package com.apiChallenge.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.apiChallenge.API;

/**
 * Test Class for API.java 
 * @author Priyanka Samanta
 * Date : 9th May 2017
 *
 */
public class APITest {

	static final String ERR_MSG_GET_RANDOM_WORD = "Invalid input.Please enter atleast 2 words seperated by spaces";
	static final String ERR_MSG_FOR_RHYMING_WORD = "No rhyming words found.";
	static final String ERR_MSG_FOR_RECKLESS_SPEED = "Invalid input. Please enter a valid speed.";
	static final String OP_MSG_FOR_SPEED_CHECK_DEFAULT = "Your driving speed is safe.";
	static final String OP_MSG_FOR_RECKLESS_SPEED = "Reckless driving speed.";
	
	
	API word;
	/**
	 * Initialization of the API object
	 */
	@Before
	public void testAPI(){
		word = new API();
	}
	
	/** 
	 * Test to check if the connection is there
	 */
	@Test
	public void testTestConnection() {
		String output = word.testConnection();
		assertEquals("Pinged!", output);
	}
	
	/**
	 * Test to check if the getRandomWord works correctly with correct input
	 */
	@Test
	public void testGetRandomWordWithTwoWords(){
		String input="Testing API";
		String output=word.getRandomWord(input);
		assertTrue(input.contains(output));
	}
	
	/**
	 * Test to check if the getRandomWord works correctly with incorrect input
	 */
	@Test
	public void testGetRandomWordWithOneWord(){
		String input="Testing";
		String output=word.getRandomWord(input);
		assertEquals(ERR_MSG_GET_RANDOM_WORD,output);
	}
	
	/**
	 * Test to check if the getRhymingWords works correctly with correct input
	 */
	@Test
	public void testGetRhymingWordsValid(){
		String output = word.getRhymingWords("kepler");
		assertTrue(!output.equals(ERR_MSG_FOR_RHYMING_WORD));
	}

	/**
	 * Test to check if the getRhymingWords works correctly with incorrect input
	 */
	@Test
	public void testGetRhymingWordsInvalid(){
		String output = word.getRhymingWords("ffffffff");
		assertEquals(ERR_MSG_FOR_RHYMING_WORD, output);
	}
	
	/**
	 * Test to check if the isReckless works correctly with correct input
	 */
	@Test
	public void testIsRecklessYes(){
		String output = word.isReckless("78");
		assertEquals(OP_MSG_FOR_RECKLESS_SPEED, output);
	}
	
	/**
	 * Test to check if the isReckless works correctly with correct input
	 */
	@Test
	public void testIsRecklessNo(){
		String output = word.isReckless("56");
		assertEquals(OP_MSG_FOR_SPEED_CHECK_DEFAULT, output);
	}
	
	/**
	 * Test to check if the isReckless works correctly with incorrect input
	 */
	@Test
	public void testIsRecklessInvalidData(){
		String output = word.isReckless("78Word");
		assertEquals(ERR_MSG_FOR_RECKLESS_SPEED, output);
	}
}
