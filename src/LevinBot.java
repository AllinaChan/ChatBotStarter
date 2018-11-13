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
	//emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
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
			statementTemp=statement;
			//getResponse handles the user reply
			System.out.println(getResponse(statement));

			//additional dialogue
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
			else if (findKeyword(statement, "usefulclassmate",0) >= 0||findKeyword(statement, "useful classmate",0) >= 0||findKeyword(statement, "usefulclass",0) >=0 ||findKeyword(statement, "useful class",0) >= 0||findKeyword(statement, "useful",0) >= 0||findKeyword(statement, "talk to useful",0) >= 0||findKeyword(statement, "talk to useful classmate",0) >= 0||findKeyword(statement, "talk to usefulclassmate",0) >= 0)
			{
				responseTemp=getResponse(statementTemp);
				break;
			}
			else if (findKeyword(statement, "uselessclassmate",0) >= 0||findKeyword(statement, "useless classmate",0) >= 0||findKeyword(statement, "uselessclass",0) >=0 ||findKeyword(statement, "useless class",0) >= 0||findKeyword(statement, "useless",0) >= 0||findKeyword(statement, "talk to useless classmate",0) >= 0||findKeyword(statement, "talk to useless",0) >= 0||findKeyword(statement, "talk to uselessclassmate",0) >= 0)
			{
				responseTemp=getResponse(statementTemp);
				break;
			}
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
		else if (findKeyword(statement, "test date") >= 0 || findKeyword(statement, "test")>=0 ||findKeyword(statement, "when is the test")>=0)
		{
			response = "The test will be whenever you want it to be ;) Just don't make it the day before the AP test";
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
		else if (findKeyword(statement, "How do you", 0) >= 0)
		{
			System.out.println("Let me just look around for the answer to your question");
			ArrayList<String> googleResults  = getDataFromGoogle(statement);
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
			System.out.println("Walking up to the class's useful classmate...");
			emotion++;
		}
		else if (findKeyword(statement, "uselessclassmate",0) >= 0||findKeyword(statement, "useless classmate",0) >= 0||findKeyword(statement, "uselessclass",0) >=0 ||findKeyword(statement, "useless class",0) >= 0||findKeyword(statement, "useless",0) >= 0||findKeyword(statement, "talk to useless classmate",0) >= 0||findKeyword(statement, "talk to useless",0) >= 0||findKeyword(statement, "talk to uselessclassmate",0) >= 0)
		{
			response = botChangeUseless(statement);
			System.out.println("Walking up to the class's useless classmate...");
			emotion++;
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

	//negative responses method
    private String[] negativeUserResponses={"I don't know", "no",
            "idk", "I dont know", "I dont need", "I don't need", "I dont really need","I dont want help", "I don't want help",
            "I don't really need", "nothing" };

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




	//"How do you" search
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



	public ArrayList<String> getDataFromGoogle(String statement)
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
	private String [] randomNeutralResponses = {"If you need help, just ask me how to do something",
			"Beep, boop, I'm Levin-n. Tell me what you need help with",
			"How are you?",
			"No Strings attached",
			"It's all boolean to me.",
			"Just a quick reminder...remember to dd a runner class to your code!",
			"Could you say that again?",
	};
	private String [] randomAngryResponses = {"WORK ON YOUR LAB...", "Harumph", "Rawr!", "Don't bother me.", "My wrath has been incurred"};
	private String [] randomHappyResponses = {"Just LevinTheDream *wink*", "Today is a good day", "AHHH, WITH THE POWER OF CODING...IM UNSTOPPABLE!", "I might just give this one a full score!"};

}
