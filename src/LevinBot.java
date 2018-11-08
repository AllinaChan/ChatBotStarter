import java.util.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Brooklyn Tech CS Department
 * @version September 2018
 */
public class LevinBot
{
	//emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
	int emotion = 0;

	/**
	 * Runs the conversation for this particular chatbot, should allow switching to other chatbots.
	 * @param statement the statement typed by the user
	 */
	public void chatLoop(String statement)
	{
		System.out.println("Hey Levin Bot here!");
		Scanner in = new Scanner (System.in);
		String greeting="";
		while (!greeting.equals("Hey, what's up? Do you need help with anything?")) {
			greeting=getGreeting(statement);
			if(greeting.equals("Hey, what's up? Do you need help with anything?")) //Hey, what's up return greeting repeats twice. This is to ensure that that doesn't happen
			{
				continue;
			}
			System.out.println(greeting);
			statement=in.nextLine();

		}
		System.out.println(greeting);


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
	public String getGreeting(String statement) {
		boolean noGreeting=false;
		statement=statement.toLowerCase();
		for (String val: humanGreetings)
		{
			if (findKeyword(statement, val) == -1) {
				noGreeting = true;
			}
			if (findKeyword(statement, val) >= 0){
				noGreeting=false;
				break;
			}
		}

		if  (noGreeting==true) {
			return "You need to say hi back";
		} else {
			return "Hey, what's up? Do you need help with anything?";
		}
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

		if (statement.length() == 0)
		{
			response = "Say something, please.";
			emotion--;
		}

		else if (findKeyword(statement, "no") >= 0)
		{
			response = "Then why are you bothering me...I have to grade your class's poorly written codes. Zeroes all around... *sigh*";
			emotion--;
		}

		else if (findKeyword(statement, "help") >= 0)
		{
			response = "Sure I can help, just tell me when";
			emotion++;
		}
		else if (findKeyword(statement, "folwell") >= 0)
		{
			response = "Watch your backpacks, Mr. Folwell doesn't fall well.";
			emotion++;
		}
		else if (findKeyword(statement, "bye") >= 0)
		{
			response = "Bye bye!";
			emotion++;
		}

		// Response transforming I want to statement
		else if (findKeyword(statement, "How do you", 0) >= 0)
		{
			 ArrayList<String> googleResults  = getDataFromGoogle(statement);
                     for (String val : googleResults)
					 {
					 	if (val.indexOf("https://")>=0 && val.indexOf("google")==-1) {
							response += val + " ";
						}
					 }
		}
		else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}
		else if (findKeyword(statement, "I want",0) >= 0)
		{
			response = transformIWantStatement(statement);
		}
		else
		{
			response = getRandomResponse();
		}

		return response;
	}



	//"How do you" search



	public ArrayList<String> getDataFromGoogle(String statement)
    {
		statement = statement.trim();
		String lastChar = statement.substring(statement.length() - 1);
		if (lastChar.equals(".") || lastChar.equals("?") || lastChar.equals("!")) {
			statement = statement.substring(0, statement.length() - 1);
		}
		int psn = findKeyword(statement, "How do you", 0);
		String restOfStatement = statement.substring(psn + 10).trim();

		System.out.println("Let me just look around for the answer to your question");
    	Document doc = null;
        ArrayList<String> titleAndUrl= new ArrayList<>();

        try {
            doc = Jsoup.connect("https://www.google.com/search?q=" + restOfStatement).get();

            String title = doc.title();
            titleAndUrl.add("Title: " + title);
            Elements links = doc.select("a[href]");

            for (Element link:links) {

                titleAndUrl.add("\nLink: " + link.attr("href"));
            }
        } catch (IOException e)
        {
            //TODO Auto-generated catch
            e.printStackTrace();
        }
        return titleAndUrl;
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
	 * Take a statement with "I want <something>." and transform it into
	 * "Would you really be happy if you had <something>?"
	 * @param statement the user statement, assumed to contain "I want"
	 * @return the transformed statement
	 */
	private String transformIWantStatement(String statement)
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
		int psn = findKeyword (statement, "I want", 0);
		String restOfStatement = statement.substring(psn + 6).trim();
		return "Would you really be happy if you had " + restOfStatement + "?";
	}


	/**
	 * Take a statement with "I <something> you" and transform it into
	 * "Why do you <something> me?"
	 * @param statement the user statement, assumed to contain "I" followed by "you"
	 * @return the transformed statement
	 */
	private String transformIYouStatement(String statement)
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

		int psnOfI = findKeyword (statement, "I", 0);
		int psnOfYou = findKeyword (statement, "you", psnOfI);

		String restOfStatement = statement.substring(psnOfI + 1, psnOfYou).trim();
		return "Why do you " + restOfStatement + " me?";
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
	private String getRandomResponse () {
		Random r = new Random();
		if (emotion == 0) {
			return randomNeutralResponses[r.nextInt(randomNeutralResponses.length)];
		}
		if (emotion < 5) {
			return randomAngryResponses[r.nextInt(randomAngryResponses.length)];
		}
		return randomHappyResponses[r.nextInt(randomHappyResponses.length)];
	}

	private String [] humanGreetings={"hello", "hi","sup","hola", "bonjour", "yo", "hey"};
	private String [] randomNeutralResponses = {"Interesting, tell me more",
			"Beep, boop, I'm Levinn",
			"Do you really think so?",
			"No Strings attached",
			"It's all boolean to me.",
			"Remember to add a runner class to your code!",
			"Could you say that again?",
	};
	private String [] randomAngryResponses = {"WORK ON YOUR LAB...", "Harumph", "Rawr!", "Don't bother me.", "My wrath has been incurred"};
	private String [] randomHappyResponses = {"Just LevinTheDream *wink*", "Today is a good day", "AHHH, WITH THE POWER OF CODING...IM UNSTOPPABLE!", "I might just give this one a full score!"};

}
