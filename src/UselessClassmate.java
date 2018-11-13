import java.util.Random;
import java.util.Scanner;

/***
 * A program to carry on conversations with a human user.
 * This version:
 * @author Lin Yao Pan
 * @version September 2018
 */
public class UselessClassmate
{
	//emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
	int emotion = 0;

	private String responseTemp;
	private String statementTemp;
	UselessClassmate(){
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
		System.out.println (getGreeting());


		while (!statement.equals("Bye"))
		{


			statement = in.nextLine();
			//getResponse handles the user reply
			System.out.println(getResponse(statement));
			statementTemp=statement;
			if (findKeyword(statement, "usefulclassmate",0) >= 0||findKeyword(statement, "useful classmate",0) >= 0||findKeyword(statement, "usefulclass",0) >=0 ||findKeyword(statement, "useful class",0) >= 0||findKeyword(statement, "useful",0) >= 0||findKeyword(statement, "talk to useful",0) >= 0||findKeyword(statement, "talk to useful classmate",0) >= 0||findKeyword(statement, "talk to usefulclassmate",0) >= 0)
		{
			responseTemp=getResponse(statementTemp);
			break;
		}
			else if (findKeyword(statement, "mr.levin",0) >= 0||findKeyword(statement, "levin",0) >= 0||findKeyword(statement, "mr levin",0) >= 0||findKeyword(statement, "mr. levin",0) >=0 ||findKeyword(statement, "mrlevin",0) >= 0||findKeyword(statement, "talk to levin",0) >= 0||findKeyword(statement, "talk to mrlevin",0) >= 0||findKeyword(statement, "talk to mr. levin",0) >= 0||findKeyword(statement, "talk to mr.levin",0) >= 0)
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
	public String getGreeting()
	{
		return "Ugh, why didn't you choose LevinBot?";
	}
	
	/**
	 * Gives a response to a user statement
	 * 
	 * @param statement
	 *            the user statement
	 *            emotion: the value that is calculated and then responded to at a certain level
	 * @return a response based on the rules given
	 */

	public String getResponse(String statement)
	{
		double r = Math.random();
		r = r * 7;
		String response = "";
		for (int b = 0; b < badWords.length; b++)
		{
			if (findKeyword(statement, badWords[b]) >=0 )
			{
				response = "How could you use such vulgar language?! Apologize or else!";
			}

		}
        Scanner news = new Scanner(System.in);
		while (response.equals("How could you use such vulgar language?! Apologize or else!"))
        {
			System.out.print("Say you're sorry. It'll repeat until you are." + " How could you say " + statement +
					" and not Sorry" + ".");
            statement = news.nextLine();
            if (findKeyword(statement, "Sorry") >=0)
			{
				response = "You are forgiven";
			}
		}

		if (statement.length() == 0 && response.length() == 0)
		{
			response = "Stop wasting my time. I'm trying to play League.";
			emotion--;
		}

		else if (findKeyword(statement, "no") >= 0)
		{
			response = "Yasso AFK. Why are you saying no?";
                	emotion--;
		}
		
		else if (findKeyword(statement, "levin") >= 0)
		{
			response = "I need a little levin tonight myself.";
			emotion++;
		}
		else if (findKeyword(statement, "league") >= 0)
		{
			response = "Trying to get to diamond in ranked. I'm in a league of my own here.";
			emotion--;
		}
		else if (findKeyword(statement, "useless") >= 0)
		{
			response = "You get you didn't pay for.";
			emotion --;
		}
		else if (findKeyword(statement, "help") >= 0)
		{
			response = "I told you to ask the other guy. What?";
			emotion--;
		}
		else if (findKeyword(statement, "class") >= 0)
		{
			response = "The only class I care about is assassin.";
			emotion--;
		}
		else if (findKeyword(statement, "usefulclassmate") >=0)
		{
			response = "Why can't you be more like him/her? Lol";
			emotion++;
		}
		// say almost anything and it'll make me mad :(. I'm playing League
		if ( emotion <= -3)
		{
			System.out.println("Now you've made me mad");
			response = ("You " + badWords[(int) r] + ". ");
			System.out.print(response);
			emotion = 0;
		}
		// Response transforming I want to statement
		else if (findKeyword(statement, "usefulclassmate",0) >= 0||findKeyword(statement, "useful classmate",0) >= 0||findKeyword(statement, "usefulclass",0) >=0 ||findKeyword(statement, "useful class",0) >= 0||findKeyword(statement, "useful",0) >= 0||findKeyword(statement, "talk to useful",0) >= 0||findKeyword(statement, "talk to useful classmate",0) >= 0||findKeyword(statement, "talk to usefulclassmate",0) >= 0)
		{
			response = botChangeUseful(statement);
			System.out.println("Walking up to the class's useful classmate...");
			emotion++;
		}
		else if (findKeyword(statement, "mr.levin",0) >= 0||findKeyword(statement, "levin",0) >= 0||findKeyword(statement, "mr levin",0) >= 0||findKeyword(statement, "mr. levin",0) >=0 ||findKeyword(statement, "mrlevin",0) >= 0||findKeyword(statement, "talk to levin",0) >= 0||findKeyword(statement, "talk to mrlevin",0) >= 0||findKeyword(statement, "talk to mr. levin",0) >= 0||findKeyword(statement, "talk to mr.levin",0) >= 0)
		{
			response = botChangeLevin(statement);
			System.out.println("Walking up to the class's beautiful teacher...");
			emotion++;
		}
		else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}
		else if (findKeyword(statement, "How could you",0) >= 0)
		{
			response = transformHowCouldYouStatement(statement);
		}	
		else if(response.length() == 0)
		{
			response = getRandomResponse();
		}
		else if (findKeyword(statement, "I want",0) >= 0)
		{
			response = transformIWantStatement(statement) ;
		}

		return response;
	}

	//Broadcast Bot change

	public String botChangeLevin (String statement)
	{
		int psn=0;
		statement = statement.trim();
		String lastChar = statement.substring(statement.length() - 1);
		if (lastChar.equals(".") || lastChar.equals("?") || lastChar.equals("!")) {
			statement = statement.substring(0, statement.length() - 1);
		}
		if (findKeyword(statement,"mr.levin")>=0)
		{
			psn = findKeyword(statement, "mr.levin", 0)+4;
		}
		else if(findKeyword(statement,"mr levin")>=0) {
			psn = findKeyword(statement, "levin", 0)+4;
		}
		else if(findKeyword(statement,"mr. levin")>=0) {
			psn = findKeyword(statement, "levin", 0)+5;
		}
		else if(findKeyword(statement,"mrlevin")>=0) {
			psn = findKeyword(statement, "levin", 0)+3;
		}
		else if(findKeyword(statement,"levin")>=0) {
			psn = findKeyword(statement, "levin", 0);
		}
		String restOfStatement = statement.substring(psn, psn + 5).trim();
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
		String lastChar = statement.substring(statement.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement.length() - 1);
		}
		int psn = findKeyword (statement, "I want to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "Why do you want to " + restOfStatement + "?";
	}

	/**
	 * When you say how could you something, it transforms it into "I could something as many times as I want.
	 * @param statement what is typed, which would have How could you.
	 * @return transformed statement
	 */
	private String transformHowCouldYouStatement(String statement)
	{

		statement = statement.trim();
		String Pun = statement.substring(statement.length() - 1);
		if (Pun.equals("?"))
		{
			statement = statement.substring(0, statement.length() - 1);
		}
		int psnofHow = findKeyword (statement, "How could you", 0);
		String restOfStatement = statement.substring(psnofHow+13).trim();
		return "I could " + restOfStatement + " as many times as I want.";
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
	
	private String [] randomNeutralResponses = {"Burp.. Ahhhh.",
			"Huh.",
			"Zoned out a bit there.",
			"You don't say.",
			"If only.",
			"So, would you like to go for a walk?",
			"Could you say that again?"
	};
	private String [] randomAngryResponses = {"Arrgh just died. It's your fault!", "Crowd Control?! really?! How dare you! I'm the 17 year old piece of gold.", "Die rebel scum!"};
	private String [] randomHappyResponses = {"Haha, Free kill.", "Today is a good day for Lintmaker", "You make me feel so young. You make me feel like spring has sprung."};
	private String [] badWords = {"poop", "butt", "fart", "bastard", "airhead", "turkeyface", "buttface", "evil", "Byron", "suck"};
}
