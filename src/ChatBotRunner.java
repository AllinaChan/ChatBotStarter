import java.util.Scanner;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * A simple class to run our chatbot teams.
 * @author Brooklyn Tech CS Department
 * @version September 2018
 */
public class ChatBotRunner
{

	/**
	 * Create instances of each chatbot, give it user input, and print its replies. Switch chatbot responses based on which chatbot the user is speaking too.
	 */
	public static void main(String[] args)
	{
		LevinBot chatbot = new LevinBot();
		UselessClassmate chatbot2 = new UselessClassmate();
		UsefulClassmate chatbot3 = new UsefulClassmate();
		

		Scanner in = new Scanner (System.in);
		System.out.println("Pick your chatbot. (LevinBot, uselessClassmate, usefulClassmate)");
		String useurimmagination = in.nextLine();
		if (useurimmagination.equals("LevinBot"))
		{
			String statement = "";
			while (!statement.equals("Bye"))
			{
				//Use Logic to control which chatbot is handling the conversation\
				//This example has only chatbot



				chatbot.chatLoop(statement);


				statement = in.nextLine();


			}
		}
		else if (useurimmagination.equals("usefulClassmate"))
		{
			String statement = "";
			while (!statement.equals("Bye")) {
				//Use Logic to control which chatbot is handling the conversation\
				//This example has only chatbot


				chatbot3.chatLoop(statement);


				statement = in.nextLine();


			}
		}
		else if (useurimmagination.equals("uselessClassmate"))
		{
			String statement = "";
			while (!statement.equals("Bye"))
			{
				//Use Logic to control which chatbot is handling the conversation\
				//This example has only chatbot



				chatbot2.chatLoop(statement);


				statement = in.nextLine();


			}
		}
	}

}
