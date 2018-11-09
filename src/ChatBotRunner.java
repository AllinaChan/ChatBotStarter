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

// Author: Allen Chen, Lin Yao Pan, byron
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
		System.out.println("Welcome to period 9 AP COMPUTER SCIENCE A!");
		System.out.println("Now tell me, who would you like to talk to? Mr.Levin? A Useful Classmate? or A Useless Classmate?");
		String useUrImagination = in.nextLine();
		useUrImagination=useUrImagination.toLowerCase();
		if (useUrImagination.equals("levin")||useUrImagination.equals("mr.levin")||useUrImagination.equals("mrlevin")||useUrImagination.equals("mr levin"))
		{
			String statement = "";
			while (!statement.equals("Bye"))
			{
				//Use Logic to control which chatbot is handling the conversation\
				//This example has only chatbot


				System.out.println("H-E-L-L-O! It's me M-R. L-E-V-I-N");
				statement = in.nextLine();
				chatbot.chatLoop(statement);


				statement = in.nextLine();


			}
		}
		else if (useUrImagination.equals("usefulclassmate")||useUrImagination.equals("a useful classmate")||useUrImagination.equals("useful classmate")||useUrImagination.equals("a usefulclassmate")||useUrImagination.equals("a usefulclassmate")||useUrImagination.equals("useful"))
		{
			String statement = "";
			while (!statement.equals("Bye")) {
				//Use Logic to control which chatbot is handling the conversation\
				//This example has only chatbot


				chatbot3.chatLoop(statement);


				statement = in.nextLine();


			}
		}
		else if (useUrImagination.equals("uselessclassmate")||useUrImagination.equals("a useless classmate")||useUrImagination.equals("useless classmate")||useUrImagination.equals("a uselessclassmate")||useUrImagination.equals("useless"))
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
