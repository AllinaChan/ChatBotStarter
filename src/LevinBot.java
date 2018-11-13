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
 * @author Allen Chen
 * @version September 2018
 */
public class LevinBot
{
	/**
	 * @param emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
	 * @param responseTemp just a additional storage for getResponse(statement) so I can call it in the runner
	 * @param statementTemp Can't put fields in parameters so I used a different named variable to store statement
	 */

	int emotion = 0;
	private String responseTemp;
	private String statementTemp;
	LevinBot(){
		this.responseTemp=responseTemp;
		this.statementTemp=statementTemp;
	}
	public String getResponse()
	{
		return responseTemp;
	}
	public String getStatement()
	{
		return statementTemp;
	}

	/**
	 * Runs the conversation for this particular chatbot, should allow switching to other chatbots.
	 * @param statement the statement typed by the user
	 */
	public void chatLoop(String statement)
	{
		//MAKES SURE THE USER SAYS HI!
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
			statementTemp=statement;  //Can't put fields in parameters so I used a different named variable to store statement

			//getResponse handles the user reply
			System.out.println(getResponse(statement));

			//Additional Dialogue
			if(findKeyword(statement, "I need help") >= 0 && !(findKeyword(statement, "homework")>= 0))
            {
                System.out.println("Ask me the question");
            }
            else if (findKeyword(statement, "How do you", 0) >= 0)
            {
                System.out.println(" ");
                System.out.println("As a teacher, I won't give you the answers...Pfft, figure it out yourself.");
            }
            else if(ifContainNegative(statement) && !(findKeyword(statement, "no i didnt")>=0))
            {
                System.out.println("Zeroes all around...*sigh*");
            }
			else if (findKeyword(statement, "test prep") >= 0||findKeyword(statement, "test questions") >= 0||findKeyword(statement, "Ap test questions") >= 0||findKeyword(statement, "Ap questions") >= 0)
			{
				System.out.println("I love studious students");
			}
			else if (findKeyword(statement, "usefulclassmate",0) >= 0||findKeyword(statement, "useful classmate",0) >= 0||findKeyword(statement, "usefulclass",0) >=0 ||findKeyword(statement, "useful class",0) >= 0||findKeyword(statement, "useful",0) >= 0||findKeyword(statement, "talk to useful",0) >= 0||findKeyword(statement, "talk to useful classmate",0) >= 0||findKeyword(statement, "talk to usefulclassmate",0) >= 0)
			{
				System.out.println("Walking up to the class's useful classmate...");
				responseTemp=getResponse(statementTemp);
				break;
			}
			else if (findKeyword(statement, "uselessclassmate",0) >= 0||findKeyword(statement, "useless classmate",0) >= 0||findKeyword(statement, "uselessclass",0) >=0 ||findKeyword(statement, "useless class",0) >= 0||findKeyword(statement, "useless",0) >= 0||findKeyword(statement, "talk to useless classmate",0) >= 0||findKeyword(statement, "talk to useless",0) >= 0||findKeyword(statement, "talk to uselessclassmate",0) >= 0)
			{
				System.out.println("Walking up to the class's useless classmate...");
				responseTemp=getResponse(statementTemp);
				break;
			}
		}

	}

	//Greetings that the user might use
	private String [] humanGreetings={"hello", "hi","sup","hola", "bonjour", "yo", "hey"};

	/**
	 * Get a default greeting
	 * @return a greeting that doesn't stop looping until the user says hi or a variant of hi
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
		String response="";
		if (statement.length() == 0)
		{
			response = "Say something, please.";
			emotion--;
		}

		else if(ifContainNegative(statement) && !(findKeyword(statement, "no i didnt")>=0))
        {
            response="Then stop bothering me...I need to grade all of these poorly written code.";
            emotion--;
        }
		else if (findKeyword(statement, "homework") >= 0 && !(findKeyword(statement,"how do you")>=0))
		{
			response = "What do you need help with on the homework?";
			emotion++;
		}
		else if(findKeyword(statement, "fine")>=0||findKeyword(statement, "well")>=0|findKeyword(statement, "good")>=0)
		{
			response="That's good! How can be of service";
			emotion--;
		}
        else if(findKeyword(statement, "code")>=0)
        {
            response="I'm pretty sure I taught you that today...";
            emotion--;
        }
		else if(findKeyword(statement, "what")>=0)
		{
			response="I don't want to repeat myself...Don't say what. Ask me questions in the form of 'how do you'";
			emotion--;
		}
		else if (findKeyword(statement, "test date") >= 0 ||findKeyword(statement, "when is the test")>=0)
		{
			response = "The test will be whenever you want it to be ;) Just don't make it the day before the AP test";
			emotion++;
		}
		else if (findKeyword(statement, "test prep") >= 0||findKeyword(statement, "test questions") >= 0||findKeyword(statement, "Ap test questions") >= 0||findKeyword(statement, "Ap test prep questions") >= 0||findKeyword(statement, "Ap questions") >= 0)
		{
			response = "Try going to this site for some 2018 questions: https://secure-media.collegeboard.org/ap/pdf/ap18-frq-computer-science-a.pdf";
			emotion++;
		}
		else if (findKeyword(statement, "bye") >= 0)
		{
			response = "See you tomorrow. DO NOT be absent. My classes are fun uWu";
			emotion++;
		}
		// Response transforming I want to statement
		else if (findKeyword(statement, "I need help") >= 0 && !(findKeyword(statement, "homework")>= 0))
		{
			response = transformINeedHelpWithStatement(statement);
			emotion++;
		}
		else if (findKeyword(statement, "dont give me") >= 0||findKeyword(statement, "don't give me") >= 0)
		{
			response = transformDontGiveMeStatement(statement);
			emotion++;
		}
		else if (findKeyword(statement, "How do you", 0) >= 0)
		{
			//ONly takes the top 5 result from the arraylist of all the search results

			System.out.println("Let me just look around for the answer to your question");
			ArrayList<String> googleResults  = getDataFromGoogleHowDoYou(statement);
			int count =0;
			 for (String val : googleResults)
					 {
					     if (count<5) {
                             if (val.indexOf("https://") >= 0 && val.indexOf("google") == -1) {
                                 response += val + " ";
                                 count++;
                             }
                         }
					 }
					 System.out.println("");
            System.out.println("Here are the top 5 search results I got from my secret source.");

		}
		//putting change to useful classmate makes it bypass the "I want to" else if statements. Users could say "I want to talk to useful classmate" which creates a discrepancy
		else if (findKeyword(statement, "usefulclassmate",0) >= 0||findKeyword(statement, "useful classmate",0) >= 0||findKeyword(statement, "usefulclass",0) >=0 ||findKeyword(statement, "useful class",0) >= 0||findKeyword(statement, "useful",0) >= 0||findKeyword(statement, "talk to useful",0) >= 0||findKeyword(statement, "talk to useful classmate",0) >= 0||findKeyword(statement, "talk to usefulclassmate",0) >= 0)
		{
			response = botChangeUseful(statement);
			emotion++;
		}
		else if (findKeyword(statement, "uselessclassmate",0) >= 0||findKeyword(statement, "useless classmate",0) >= 0||findKeyword(statement, "uselessclass",0) >=0 ||findKeyword(statement, "useless class",0) >= 0||findKeyword(statement, "useless",0) >= 0||findKeyword(statement, "talk to useless classmate",0) >= 0||findKeyword(statement, "talk to useless",0) >= 0||findKeyword(statement, "talk to uselessclassmate",0) >= 0)
		{
			response = botChangeUseless(statement);
			emotion++;
		}
		else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}
		else if (findKeyword(statement, "I want to know",0) >= 0)
		{
			System.out.println("Since you want to know, let me just search my sources");
			ArrayList<String> googleResults  = getDataFromGoogleIWantToKnow(statement);
			int count =0;
			for (String val : googleResults)
			{
				if (count<5) {
					if (val.indexOf("https://") >= 0 && val.indexOf("google") == -1) {
						response += val + " ";
						count++;
					}
				}
			}
			System.out.println("");
			System.out.println("Here are the top 5 search results I got from google");

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

	//negative responses method
    private String[] negativeUserResponses={"I don't know", "no",
            "idk", "I dont know", "I dont need", "I don't need", "I dont really need","I dont want help", "I don't want help",
            "I don't really need", "nothing" };

	/**
	 *
	 * @param statement the inputted statement
	 * @return result if the statement contains an element from the arraylist negativeUserResponses
	 */
    private boolean ifContainNegative(String statement)
    {
        boolean result=false;
        for (String val: negativeUserResponses)
        {
            if(findKeyword(statement, val) >=0)
            {
                result=true;
            }
        }
        return result;
    }


    //Broadcast Bot change

	/**
	 *
	 * @param statement the inputted user statement
	 * @return the trimmed statement with only "useless" left
	 */
	public String botChangeUseless (String statement)
	{
		int psn=0;
		statement = statement.trim();
		String lastChar = statement.substring(statement.length() - 1);
		if (lastChar.equals(".") || lastChar.equals("?") || lastChar.equals("!")) {
			statement = statement.substring(0, statement.length() - 1);
		}
		if (findKeyword(statement,"uselessclassmate")>=0)
		{
			psn = findKeyword(statement, "uselessclassmate", 0);
		}
		else if(findKeyword(statement,"useless classmate")>=0) {
			psn = findKeyword(statement, "useless", 0);
		}
		else if (findKeyword(statement,"useless")>=0)
		{
			psn = findKeyword(statement, "useless", 0);
		}
		String restOfStatement = statement.substring(psn, psn + 7).trim();
		return restOfStatement;
	}

	/**
	 *
	 * @param statement the inputted user statement
	 * @return the trimmed statement with only "useful" left
	 */

	public String botChangeUseful (String statement)
	{
		int psn=0;
		statement = statement.trim();
		String lastChar = statement.substring(statement.length() - 1);
		if (lastChar.equals(".") || lastChar.equals("?") || lastChar.equals("!")) {
			statement = statement.substring(0, statement.length() - 1);
		}
		if (findKeyword(statement,"usefulclassmate")>=0)
		{
			psn = findKeyword(statement, "usefulclassmate", 0);
		}
		else if(findKeyword(statement,"useful classmate")>=0) {
			psn = findKeyword(statement, "useful", 0);
		}
		else if (findKeyword(statement,"useful")>=0)
		{
			psn = findKeyword(statement, "useful", 0);
		}
		String restOfStatement = statement.substring(psn, psn + 6).trim();
		return restOfStatement;
	}




	//Google search

	/**
	 *
	 * @param statement the inputted user statement
	 * @return cuts the "How do you" part of the statement out for a google search
	 */
	private String transformHowDoYouStatement(String statement)
	{
		statement = statement.trim();
		String lastChar = statement.substring(statement.length() - 1);
		if (lastChar.equals(".") || lastChar.equals("?") || lastChar.equals("!")) {
			statement = statement.substring(0, statement.length() - 1);
		}
		int psn = findKeyword(statement, "How do you", 0);
		String restOfStatement = statement.substring(psn + 10).trim();
		return restOfStatement;
	}


	/**
	 *
	 * @param statement the inputted user statement
	 * @return uses jsoup the parsing the html of a google search and then uses it to create an arraylist of search results
	 */
	public ArrayList<String> getDataFromGoogleHowDoYou(String statement)
    {

    	Document doc = null;
        ArrayList<String> titleAndUrl= new ArrayList<>();

        try {
            doc = Jsoup.connect("https://www.google.com/search?q=" + transformHowDoYouStatement(statement)).get();

            String title = doc.title();
            titleAndUrl.add("Title: " + title);
            Elements links = doc.select("a[href]");
            int count=0;
            for (Element link:links) {

                titleAndUrl.add("\n" +link.text()+":" + link.attr("href"));
            }
        } catch (IOException e)
        {
            //TODO Auto-generated catch
            e.printStackTrace();
        }
        return titleAndUrl;
    }

	public ArrayList<String> getDataFromGoogleIWantToKnow(String statement)
	{

		Document doc = null;
		ArrayList<String> titleAndUrl= new ArrayList<>();

		try {
			doc = Jsoup.connect("https://www.google.com/search?q=" + transformIWantToKnowStatement(statement)).get();

			String title = doc.title();
			titleAndUrl.add("Title: " + title);
			Elements links = doc.select("a[href]");
			int count=0;
			for (Element link:links) {

				titleAndUrl.add("\n" +link.text()+":" + link.attr("href"));
			}
		} catch (IOException e)
		{
			//TODO Auto-generated catch
			e.printStackTrace();
		}
		return titleAndUrl;
	}

	/**
	 * Take a statement with "Why Are you <something>." and transform it into
	 *  just <something>
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformWhyAreYouStatement(String statement)
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
		int psn = findKeyword (statement, "why are you", 0);
		String restOfStatement = statement.substring(psn + 11).trim();
		return restOfStatement+ "I don't know what you're talking about... Just ask me a question";
	}

	/**
	 * Take a statement with "I want to <something>." and transform it into
	 * "Don't give you what? <something>? Say that again little kid!"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformDontGiveMeStatement(String statement)
	{
		int psn=0;
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		if (findKeyword (statement, "Dont give me", 0)>=0) {
			psn = findKeyword(statement, "Dont give me", 0);
		}
		if(findKeyword (statement, "Don't give me", 0)>=0) {
			psn = findKeyword(statement, "Don't give me", 0)+1;
		}
		String restOfStatement = statement.substring(psn + 12).trim();
		return "Don't give you what? " + restOfStatement + "? Say that again little kid!";
	}
	/**
	 * Take a statement with "I need help <something>." and transform it into
	 * "Uhh sure, I can help you with <something>?"
	 * @param statement the inputted user statement
	 * @return the transformed statement
	 */
	private String transformINeedHelpWithStatement(String statement)
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
		int psn = findKeyword (statement, "I need help");
		String restOfStatement = statement.substring(psn + 11).trim().toLowerCase();
		if(restOfStatement.indexOf("my")>=0)
		{
			restOfStatement = statement.substring(psn + 14).trim().toLowerCase();
		}
		if(restOfStatement.indexOf("with")>=0)
        {
            restOfStatement=statement.substring(psn + 16).trim().toLowerCase();
        }
		return "Uhh sure, I can help you with " + restOfStatement;
	}

	/**
	 * Take a statement with "I want to know <something>." and transform it into
	 *  just <something>
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToKnowStatement(String statement)
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
		int psn = findKeyword (statement, "I want to know", 0);
		String restOfStatement = statement.substring(psn + 14).trim();
		return restOfStatement;
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
		if (emotion <= 7) {
			return randomAngryResponses[r.nextInt(randomAngryResponses.length)];
		}
		return randomHappyResponses[r.nextInt(randomHappyResponses.length)];
	}

	private String [] randomNeutralResponses = {"If you need help, just ask me how to do something",
			"Beep, boop, I'm Levin-n. Tell me what you need help with",
			"How are you?",
			"IF you want to talk to someone else, they just say who you want to talk to",
			"Just a quick reminder...remember to dd a runner class to your code!",
			"Could you say that again?",
	};
	private String [] randomAngryResponses = {"WORK ON YOUR LAB...", "IF you want to talk to someone else, they just say who you want to talk to", "Rawr!", "Don't bother me.", "My wrath has been incurred"};
	private String [] randomHappyResponses = {"Just LevinTheDream *wink*", "IF you want to talk to someone else, they just say who you want to talk to", "AHHH, WITH THE POWER OF CODING...IM UNSTOPPABLE!", "I might just give this one a full score!"};

}
