import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

/**
 * A program to carry on conversations with a human user.
 * This version:
 *<ul><li>
 * 		Uses advanced search for keywords 
 *</li><li>
 * 		Will transform statements as well as react to keywords
 *</li></ul>
 * @author Laurie White
 * @version April 2012
 *
 */


public class Magpie4
{
  static boolean done = false;

  //Create a string to represent the current question: Greeting, WhatState, JoeBiden, BiggestIssue, Afghanistan,SuggestingPolicies

  public static String mood = "Greeting";
  public static String currentQuestion = "how are you doing?";

  public static String[] HighlyLiberal = {"Washington", "Vermont", "New Hampshire", "New York", "Massachusetts", "Hawaii"};

  public static String[] Liberal = {"Illinois", "Oregon", "California", "Rhode Island", "Maine", "New Jersey", "Connecticut", "Maryland", "Delaware"};

  public static String[] Moderate = {"Minnesota", "Wisconsin", "Iowa", "Nevada", "Colorado", "Nebraska", "Michigan", "Pennsylvania", "Virginia", "Florida"};

  public static String[] Conservative = {"Ohio", "Kentucky", "New Mexico", "North Carolina", "Arizona", "Texas"};

  public static String[] HighlyConservative = {"Alaska", "Montana", "North Dakota", "South Dakota", "Idaho", "Wyoming", "Indiana", "Missouri", "West Virginia", "Utah", "Kansas", "Arkansas", "Tennessee", "South Carolina", "Oklahoma", "Louisiana", "Mississippi", "Alabama", "Georgia"};

  public static int democraticScore = 0; 

  public static void main(String[] args){
    Scanner scanner = new Scanner(System.in);

    String greeting = getGreeting();
    System.out.println("");
    System.out.println(greeting);

    while(!done){
      String in = scanner.nextLine();

      if (mood == "Greeting"){
        //String response = getGreetingResponse(in);
        double sentiment = totalSentiment(in);
        if (sentiment >= 0.5){
          System.out.println("Glad to hear that!");
          System.out.println("Tell me more! Which state are you from?");
          mood = "State";
        }
        else{
          System.out.println("I'm sorry to hear that!");
          System.out.println("Tell me more! Which state are you from?");
          mood = "State";
        }
      }
      else if (mood == "State"){
        String response = getStateResponse(in);
        System.out.println(response);
      }
      else if (mood == "JoeBiden"){
        //String response = getGreetingResponse(in);
        double sentiment = totalSentiment(in);
        democraticScore += sentiment;
        if (sentiment >= 0.5){
          System.out.println("Glad to hear that!");
          System.out.println("Now tell me about how you feel about our previous president, Donald Trump.");
          mood = "Trump";
        }
        else{
          System.out.println("I'm sorry to hear that!");
          System.out.println("Now tell me about how you feel about our previous president, Donald Trump.");
          mood = "Trump";
        }
      }
      else if (mood == "Trump"){
        //String response = getGreetingResponse(in);
        double sentiment = totalSentiment(in);
        democraticScore -= sentiment;
        if (sentiment >= 0.5){
          System.out.println("Glad to hear that!");
          System.out.println("Now allow me to tell you a little bit about my own policies: ");

          if(democraticScore >= 5){
            System.out.println("I plan to take serious climate action, enforce stricter COVID safety measures, and end gun violence!");
          }
          else if(democraticScore < 0){
            System.out.println("I plan to stengthen gun rights, cut funding for useless climate action, and end the mask mandate!");
          }
          else{
            System.out.println("I plan to take a decent amount of climate action, help slowly cut down COVID protocols, and keep gun rights the way they currently are");
          }

          System.out.println("Will you vote for me?");
          mood = "Will you vote?";
        }
        else{
          System.out.println("I'm sorry to hear that!");
          System.out.println("Now allow me to tell you a little bit about my own policies: ");
          
          if(democraticScore >= 5){
            System.out.println("I plan to take serious climate action, enforce stricter COVID safety measures, and end gun violence!");
          }
          else if(democraticScore < 0){
            System.out.println("I plan to strengthen gun rights, cut funding for useless climate action, and end the mask mandate!");
          }
          else{
            System.out.println("I plan to take a decent amount of climate action, help slowly cut down COVID protocols, and keep gun rights the way they currently are");
          }

          System.out.println("Will you vote for me?");
          mood = "Will you vote?";
      }
      }
      else if (mood == "Will you vote?"){
        String response = getVoteResponse(in);
        System.out.println(response);
      }
      else {
        double sentiment = totalSentiment(in);
        if(sentiment >= 1) {
          System.out.println("That sounds neat! Tell me more!");
        }
        else{
          System.out.println("That doesn't sound too great. Tell me more!");
        }
        }
      }
    }

	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */	

	public static String getGreeting()
	{
		return "Hey there! My name is Jonald Bump. I'm running for the 2024 election! How are you doing this fine day?";
	}
	
	/**
	 * Gives a response to a user statement
	 * 
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */

  public static String getVoteResponse(String statement){
    String response = "";
    if(statement.length() == 0){
      response = "What's with the silent treatment? Tell me if you're going to vote for me!";
    }
    else if (findKeyword(statement, "no") >= 0)
		{
			response = "Gosh. I hate to hear that. Tell me more about your political views.";
      mood = "Free response";
		}
    else if (findKeyword(statement, "yes") >= 0){
      response = "AWESOME CHOICE! Tell me more about your political views.";
      mood = "Free response";
    }
    else{
      response = "I don't understand. Please say 'yes' if you would like to vote for me, and 'no' otherwise.";
    }
    
    return response;
  }

	public static String getStateResponse(String statement)
	{
		String response = "";
		if (statement.length() == 0)
		{
			response = "What's with the silent treatement? Tell me what state you're from!";
		}

    int stateStanding = -10;
    for(int i=0; i < HighlyLiberal.length; i++){
      if(findKeyword(statement, HighlyLiberal[i]) >= 0){
        stateStanding = 2;
        response = "That's a great state! Now, I'd like to hear more about your political views. What is your opinion about the current president, Joe Biden?";
        democraticScore += 2;
        mood = "JoeBiden";
      }
      else{

      }
    }

    for(int j=0; j < Liberal.length; j++){
      if(findKeyword(statement, Liberal[j]) >= 0){
        stateStanding = 1;
        response = "That's a great state! Now, I'd like to hear more about your political views. What is your opinion about the current president, Joe Biden?";
        democraticScore += 1;
        mood = "JoeBiden";
      }
      else{
        
      }
    }

    for(int k=0; k < Moderate.length; k++){
      if(findKeyword(statement, Moderate[k]) >= 0){
        stateStanding = 0;
        response = "That's a great state! Now, I'd like to hear more about your political views. What is your opinion about the current president, Joe Biden?";
        democraticScore += 0;
        mood = "JoeBiden";
      }
      else{
        
      }
    }

    for(int l=0; l < Conservative.length; l++){
      if(findKeyword(statement, Conservative[l]) >= 0){
        stateStanding = -1;
        response = "That's a great state! Now, I'd like to hear more about your political views. What is your opinion about the current president, Joe Biden?";
        democraticScore -= 1;
        mood = "JoeBiden";
      }
      else{
        
      }
    }

    for(int m=0; m < Liberal.length; m++){
      if(findKeyword(statement, Liberal[m]) >= 0){
        stateStanding = -2;
        response = "That's a great state! Now, I'd like to hear more about your political views. What is your opinion about the current president, Joe Biden?";
        democraticScore -= 2;
        mood = "JoeBiden";
      }
      else{
        
      }
    }

    if(stateStanding == -10){
      response = "I don't think that's a real state. Tell me which state you're from, really.";
    }

    /*
		else if (findKeyword(statement, "no") >= 0)
		{
			response = "No is not a state, you ding dong. Try again.";
		}
    */

    /*
		else if (findKeyword(statement, "mother") >= 0
				|| findKeyword(statement, "father") >= 0
				|| findKeyword(statement, "sister") >= 0
				|| findKeyword(statement, "brother") >= 0)
		{
			response = "Tell me more about your family.";
		}
    */

    /*
    else if (findKeyword(statement, "Trump") >= 0){
      response = "Please don't confuse me with Donald Trump. I know my name sounds similar, but doesn't mean we're related in any way.";
    }
    */

		// Responses which require transformations
    /*
		else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}
    */
    
    /*
    else if (findKeyword(statement, "New York") >= 0){
      response = "If you vote for me, I will recognize Hot Dog stands as US citizens with rights. Will you vote for me, New Yorker?";
    } 
    */
    /*
		else
		{
			// Look for a two word (you <something> me)
			// pattern
			int psn = findKeyword(statement, "you", 0);

			if (psn >= 0
					&& findKeyword(statement, "me", psn) >= 0)
			{
				response = transformYouMeStatement(statement);
			}
			else
			{
				response = getRandomResponse();
			}
		}
    */
		return response;
	}
	
	/**
	 * Take a statement with "I want to <something>." and transform it into 
	 * "What would it mean to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private static String transformIWantToStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "What would it mean to " + restOfStatement + "?";
	}

	
	
	/**
	 * Take a statement with "you <something> me" and transform it into 
	 * "What makes you think that I <something> you?"
	 * @param statement the user statement, assumed to contain "you" followed by "me"
	 * @return the transformed statement
	 */
	private static String transformYouMeStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		
		int psnOfYou = findKeyword (statement, "you", 0);
		int psnOfMe = findKeyword (statement, "me", psnOfYou + 3);
		
		String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
		return "What makes you think that I " + restOfStatement + " you?";
	}
	
	

	
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @param startPos the character of the string to begin the search at
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private static int findKeyword(String statement, String goal, int startPos)
	{
		String phrase = statement.trim();
		//  The only change to incorporate the startPos is in the line below
		int psn = phrase.toLowerCase().indexOf(goal.toLowerCase(), startPos);
		
		//  Refinement--make sure the goal isn't part of a word 
		while (psn >= 0) 
		{
			//  Find the string of length 1 before and after the word
			String before = " ", after = " "; 
			if (psn > 0)
			{
				before = phrase.substring (psn - 1, psn).toLowerCase();
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(psn + goal.length(), psn + goal.length() + 1).toLowerCase();
			}
			
			//  If before and after aren't letters, we've found the word
			if (((before.compareTo ("a") < 0 ) || (before.compareTo("z") > 0))  //  before is not a letter
					&& ((after.compareTo ("a") < 0 ) || (after.compareTo("z") > 0)))
			{
				return psn;
			}
			
			//  The last position didn't work, so let's find the next, if there is one.
			psn = phrase.indexOf(goal.toLowerCase(), psn + 1);
			
		}
		
		return -1;
	}
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private static int findKeyword(String statement, String goal)
	{
		return findKeyword (statement, goal, 0);
	}
	


	/**
	 * Pick a default response to use if nothing else fits.
	 * @return a non-committal string
	 */
	private static String getRandomResponse()
	{
		final int NUMBER_OF_RESPONSES = 4;
		double r = Math.random();
		int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
		String response = "";
		
		if (whichResponse == 0)
		{
			response = "Interesting, tell me more.";
		}
		else if (whichResponse == 1)
		{
			response = "Hmmm.";
		}
		else if (whichResponse == 2)
		{
			response = "Do you really think so?";
		}
		else if (whichResponse == 3)
		{
			response = "You don't say.";
		}

		return response;
	}

  private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
  private static ArrayList<String> posAdjectives = new ArrayList<String>();
  private static ArrayList<String> negAdjectives = new ArrayList<String>();
 
  
  private static final String SPACE = " ";
  
  static{
    try {
      Scanner input = new Scanner(new File("cleanSentiment.csv"));
      while(input.hasNextLine()){
        String[] temp = input.nextLine().split(",");
        sentiment.put(temp[0],Double.parseDouble(temp[1]));
        //System.out.println("added "+ temp[0]+", "+temp[1]);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing cleanSentiment.csv");
    }
  
  
  //read in the positive adjectives in postiveAdjectives.txt
     try {
      Scanner input = new Scanner(new File("positiveAdjectives.txt"));
      while(input.hasNextLine()){
        String temp = input.nextLine().trim();
        System.out.println(temp);
        posAdjectives.add(temp);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing postitiveAdjectives.txt\n" + e);
    }   
 
  //read in the negative adjectives in negativeAdjectives.txt
     try {
      Scanner input = new Scanner(new File("negativeAdjectives.txt"));
      while(input.hasNextLine()){
        negAdjectives.add(input.nextLine().trim());
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing negativeAdjectives.txt");
    }   
  }
  
  /** 
   * returns a string containing all of the text in fileName (including punctuation), 
   * with words separated by a single space 
   */
  public static String textToString( String fileName )
  {  
    String temp = "";
    try {
      Scanner input = new Scanner(new File(fileName));
      
      //add 'words' in the file to the string, separated by a single space
      while(input.hasNext()){
        temp = temp + input.next() + " ";
      }
      //add 'words' in the file to the string, separated by a single space
      input.close();
      
    }
    catch(Exception e){
      System.out.println("Unable to locate " + fileName);
    }
    //make sure to remove any additional space that may have been added at the end of the string.
    return temp.trim();
  }
  
  /**
   * @returns the sentiment value of word as a number between -1 (very negative) to 1 (very positive sentiment) 
   */
  public static double sentimentVal( String word )
  {
    try
    {
      return sentiment.get(word.toLowerCase());
    }
    catch(Exception e)
    {
      return 0;
    }
  }
  
  /**
   * Returns the ending punctuation of a string, or the empty string if there is none 
   */
  public static String getPunctuation( String word )
  { 
    String punc = "";
    for(int i=word.length()-1; i >= 0; i--){
      if(!Character.isLetterOrDigit(word.charAt(i))){
        punc = punc + word.charAt(i);
      } else {
        return punc;
      }
    }
    return punc;
  }

      /**
   * Returns the word after removing any beginning or ending punctuation
   */
  public static String removePunctuation( String word )
  {
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(0)))
    {
      word = word.substring(1);
    }
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(word.length()-1)))
    {
      word = word.substring(0, word.length()-1);
    }
    
    return word;
  }
 
  public static String fakeReview(String review){
    int nextSpace; 
    String temporarySubstring; 
    String currentWord;
    int i=0;
    while (i <= review.length() - 1){
      if (review.charAt(i) == '*'){
        temporarySubstring = review.substring(i, review.length() - 1);
        nextSpace = temporarySubstring.indexOf(" ");
        currentWord = temporarySubstring.substring(0, nextSpace);
        review = review.replace(currentWord, randomAdjective().toLowerCase());
        i++;
      }
      else{
        i++;
      }
    }

    return review;
      

  }
  
  public static String strongerReview(String review){
    int nextSpace; 
    String temporarySubstring; 
    String currentWord;
    int i=0;
    while (i <= review.length() - 1){
      if (review.charAt(i) == '*'){
        temporarySubstring = review.substring(i, review.length() - 1);
        nextSpace = temporarySubstring.indexOf(" ");
        currentWord = temporarySubstring.substring(0, nextSpace);
        review = review.replace(currentWord, randomAdjective().toLowerCase());
        i++;
      }
      else{
        i++;
      }
    }

    return review;
      

  }

  public static double totalSentiment(String review){
    double totalSent = 0.0;
    int nextSpace;
    String currWord;
    while(review.length() != 0){
      if(review.indexOf(" ") == -1) {
        //System.out.println(review); 
        review = removePunctuation(review);
        totalSent += sentimentVal(review);
        review = "";
      } else {
        nextSpace = review.indexOf(" ");
        currWord = review.substring(0, nextSpace);
        review = review.substring(nextSpace + 1);
        currWord = removePunctuation(currWord);
        totalSent += sentimentVal(currWord);
      }
    }
    //System.out.println(totalSent);
    return totalSent;
  }

  public static int starRating(String review) {
    double sentiment = totalSentiment(review);
    if(sentiment <= -9.0){
      System.out.println("0");
      return 0;
    }
    else if(sentiment > -9.0 && sentiment <= 0.0) {
      System.out.println("1");
      return 1;
    }
    else if(sentiment > 0.0 && sentiment <= 10.0){
      System.out.println("2");
      return 2;
    }
    else if(sentiment > 10.0 && sentiment <= 20.0){
      System.out.println("3");
      return 3;
    }
    else if(sentiment > 20.0 && sentiment <= 30.0){
      System.out.println("4");
      return 4;
    }
    else{
      System.out.println("5");
      return 5;
    }
  }

  /** 
   * Randomly picks a positive adjective from the positiveAdjectives.txt file and returns it.
   */
  public static String randomPositiveAdj()
  {
    int index = (int)(Math.random() * posAdjectives.size());
    return posAdjectives.get(index);
  }
  
  /** 
   * Randomly picks a negative adjective from the negativeAdjectives.txt file and returns it.
   */
  public static String randomNegativeAdj()
  {
    int index = (int)(Math.random() * negAdjectives.size());
    return negAdjectives.get(index);
    
  }
  
  /** 
   * Randomly picks a positive or negative adjective and returns it.
   */
  public static String randomAdjective()
  {
    boolean positive = Math.random() < .5;
    if(positive){
      return randomPositiveAdj();
    } else {
      return randomNegativeAdj();
    }
  }

}
