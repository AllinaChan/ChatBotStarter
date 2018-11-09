import java.util.Random;
import java.util.Scanner;

/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Brooklyn Tech CS Department
 * @version September 2018
 */
public class UsefulClassmate
{
	//emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
	int emotion = 0;



	/**
	 * Runs the conversation for this particular chatbot, should allow switching to other chatbots.
	 * @param statement the statement typed by the user
	 */
	public void chatLoop(String statement)
	{
		Scanner in = new Scanner (System.in);
		System.out.println (getGreeting());


		while (!statement.equals("Bye"))
		{


			statement = in.nextLine();
			//getResponse handles the user reply
			System.out.println(getResponse(statement));


		}

	}
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */	
	public String getGreeting()
	{
		return "Hi, my name is Byron, the useful classmate.";
	}
	
	/**
	 * Gives a response to a user statement
	 * 
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	public String getResponse(String statement)
	{
		String response = "";
		for (int b = 0; b < giveUp.length; b++)
		{
			if (findKeyword(statement, giveUp[b]) >=0 )
			{
				response = "I'll never let go Jack, and so shouldn't you.";
			}

		}
		
		if (statement.length() == 0)
		{
			response = "Yo, don't ignore me please.";
		}

		else if (findKeyword(statement, "no") >= 0)
		{
			response = "What happened, failed your test? It's ok, sit next to me on the next one.";
                	emotion--;
		}
		
		else if (findKeyword(statement, "Help") >= 0)
		{
			response = "Yep, I'll be always here for you.";
			emotion++;
		}

		// Response transforming I want to statement
		else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}
		else if (findKeyword(statement, "I need", 0) >=0)
		{
			response = transformINeedStatement(statement);
		}
		else if (findKeyword(statement, "I can't", 0) >=0)
		{
			response = transformICantStatement(statement);
		}
		else if (findKeyword(statement, "I don't get", 0) >=0)
		{
			response = transformIDontStatement(statement);
		}
		else if (findKeyword(statement, "homework", 0) >=0)
		{
			response = transformHomeworkStatement(statement);
		}
		else if (findKeyword(statement, "date", 0) >=0)
		{
			response = transformDateStatement(statement);
		}
		else
		{
			response = getRandomResponse();
		}
		
		return response;
	}

	/**
	 * Take a statement with "homework." and return
	 * "The homework is to finish the entire codingbat java section, good luck soldier."
	 * @return the statement
	 */

	private String transformDateStatement(String statement)
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
		int psn = findKeyword (statement, "homework", 0);
		return "The test date is at" + ;
	}

	/**
	 * Take a statement with "date." and return a test date
	 * "The test is on <a random date in november>."
	 * @return the statement
	 */

	private String transformHomeworkStatement(String statement)
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
		int psn = findKeyword (statement, "homework", 0);
		return "The homework is to finish the entire CodingBat Java section, good luck soldier.";
	}

	/**
	 * Take a statement with "I need <something>." and transform it into
	 * "Why do you need <something>?"
	 * @param statement the user statement, assumed to contain "I need"
	 * @return the transformed statement
	 */

	private String transformINeedStatement(String statement)
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
		int psn = findKeyword (statement, "I need", 0);
		String restOfStatement = statement.substring(psn + 6).trim();
		return "Why do you need " + restOfStatement + "?";
	}


	/**
	 * Take a statement with "I want to <something>." and transform it into 
	 * "Why do you want to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToStatement(String statement)
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
		return "Why do you want to " + restOfStatement + "?";
	}


	/**
	 * Take a statement with "I can't <something>." and transform it into
	 * "Wy you think you can't <something>? You got me!"
	 * @param statement the user statement, assumed to contain "I can't"
	 * @return the transformed statement
	 */
	private String transformICantStatement(String statement)
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
		int psn = findKeyword (statement, "I can't", 0);
		String restOfStatement = statement.substring(psn + 7).trim();
		return "Why you think you can't " + restOfStatement + "? You got me!";
	}

	
	/**
	 * Take a statement with "I don't <something>." and transform it into
	 * "What don't you get <something>?"
	 * @param statement the user statement, assumed to contain "I want"
	 * @return the transformed statement
	 */
	private String transformIDontStatement(String statement)
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
		int psn = findKeyword (statement, "I don't get", 0);
		String restOfStatement = statement.substring(psn + 11).trim();
		return "What is it about " + restOfStatement + " you don't get?";
	}

	

	
	
	/**
	 * Search for one word in phrase. The search is not case
	 * sensitive. This method will check that the given goal
	 * is not a substring of a longer string (so, for
	 * example, "I know" does not contain "no").
	 *
	 * @param statement
	 *            the string to search
	 * @param goal
	 *            the string to search for
	 * @param startPos
	 *            the character of the string to begin the
	 *            search at
	 * @return the index of the first occurrence of goal in
	 *         statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal,
			int startPos)
	{
		String phrase = statement.trim().toLowerCase();
		goal = goal.toLowerCase();

		// The only change to incorporate the startPos is in
		// the line below
		int psn = phrase.indexOf(goal, startPos);

		// Refinement--make sure the goal isn't part of a
		// word
		while (psn >= 0)
		{
			// Find the string of length 1 before and after
			// the word
			String before = " ", after = " ";
			if (psn > 0)
			{
				before = phrase.substring(psn - 1, psn);
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(
						psn + goal.length(),
						psn + goal.length() + 1);
			}

			// If before and after aren't letters, we've
			// found the word
			if (((before.compareTo("a") < 0) || (before
					.compareTo("z") > 0)) // before is not a
											// letter
					&& ((after.compareTo("a") < 0) || (after
							.compareTo("z") > 0)))
			{
				return psn;
			}

			// The last position didn't work, so let's find
			// the next, if there is one.
			psn = phrase.indexOf(goal, psn + 1);

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
	private int findKeyword(String statement, String goal)
	{
		return findKeyword (statement, goal, 0);
	}
	


	/**
	 * Pick a default response to use if nothing else fits.
	 * @return a non-committal string
	 */
	private String getRandomResponse ()
	{
		Random r = new Random ();
		if (emotion == 0)
		{	
			return randomNeutralResponses [r.nextInt(randomNeutralResponses.length)];
		}
		if (emotion < 0)
		{	
			return randomAngryResponses [r.nextInt(randomAngryResponses.length)];
		}	
		return randomHappyResponses [r.nextInt(randomHappyResponses.length)];
	}
	
	private String [] randomNeutralResponses = {"Keep going.",
			"Hmm...",
			"Look at it again.",
			"Write it out on paper",
			"Google it.",
			"Clarify on what you mean.",
			"This is interesting."
	};
	private String [] randomAngryResponses = {"Come on dude. I did that in 5 minutes.", "Did you really just ask me that?", "I don't usually have anger issues, but now I do."};
	private String [] randomHappyResponses = {"Whats the problem on?", "Looking good so far.", "Good job mate.", "Yep, that's right."};
	private String [] giveUp = {"yikes", "fuck", "ugh", "sigh"};
	
}
