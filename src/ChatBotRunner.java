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
import java.util.concurrent.TimeUnit;


/**
 * A simple class to run our chatbot teams.
 * @author Allen Chen, Lin Yao Pan, Byron
 * @version September 2018
 */
public class ChatBotRunner
{

	/**
	 * Create instances of each chatbot, give it user input, and print its replies. Switch chatbot responses based on which chatbot the user is speaking too.
	 */
	public static void main(String[] args) {
		LevinBot chatbot = new LevinBot();
		UselessClassmate chatbot2 = new UselessClassmate();
		UsefulClassmate chatbot3 = new UsefulClassmate();


		Scanner in = new Scanner(System.in);
		System.out.println("Welcome to period 9 AP COMPUTER SCIENCE A!");
		//1 second delay. Need to catch InterruptedException error
		try {
			Thread.sleep(1000);
			System.out.println("Now tell me, who would you like to talk to? Mr.Levin? A Useful Classmate? or A Useless Classmate?");
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
		//makes sure user picked a bot
		/**
		 * @param pickedBot is the boolean for if the user has selected a bot
		 * @param  pickedLevin is the boolean for if the user has selected LevinBot
		 * @param pickedUseful is the boolean for if the user has selected Useful Classmate
		 * @param pickedUseless is the boolean for if the user has selected Useless Classmate
		 * @param botSelector is the user input for the bot they want to select
		 * @param botChanger is the user inputted statement trimmed to what bot the user want to switch to while in LevinBot
		 * @param botChanger2 is the user inputted statement trimmed to what bot the user want to switch to while in UselessClassmate Bot
		 * @param botChanger3 is the user inputted statement trimmed to what bot the user want to switch to while in UsefulClassmate Bot
		 */
		boolean pickedBot=false;
		boolean pickedLevin=false;
		boolean pickedUseful=false;
		boolean pickedUseless=false;
		String botSelector="";
		String botChanger = "";
		String botChanger2="";
		String botChanger3="";
		while (pickedBot == false) {
			botSelector = in.nextLine();
			botSelector = botSelector.toLowerCase();
			if (botSelector.indexOf("levin")>=0 || botSelector.indexOf("mr.levin")>=0 || botSelector.indexOf("mrlevin")>=0 || botSelector.indexOf("mr levin")>=0) {

				String statement = "";

				//Use Logic to control which chatbot is handling the conversation\
				//This example has only chatbot

				System.out.println("H-E-L-L-O! It's me M-R. L-E-V-I-N");
				statement = in.nextLine();
				chatbot.chatLoop(statement);
				pickedBot = true;
				pickedLevin=true;
				pickedUseful=false;
				pickedUseless=false;
				
			} else if (botSelector.indexOf("usefulclassmate")>=0 || botSelector.indexOf("a useful classmate")>=0 || botSelector.indexOf("useful classmate")>=0 || botSelector.indexOf("a usefulclassmate")>=0 || botSelector.indexOf("a usefulclassmate")>=0 || botSelector.indexOf("useful")>=0) {
				String statement = "";

				//Use Logic to control which chatbot is handling the conversation\
				//This example has only chatbot


				chatbot3.chatLoop(statement);

				pickedBot = true;
				pickedUseful=true;
				pickedLevin=false;
				pickedUseless=false;


			} else if (botSelector.indexOf("uselessclassmate")>=0 || botSelector.indexOf("a useless classmate")>=0 || botSelector.indexOf("useless classmate")>=0 || botSelector.indexOf("a uselessclassmate")>=0 || botSelector.indexOf("useless")>=0) {
				String statement = "";

				//Use Logic to control which chatbot is handling the conversation\
				//This example has only chatbot


				chatbot2.chatLoop(statement);
				pickedUseless = true;
				pickedBot=true;
				pickedUseless=true;
				pickedLevin=false;
				pickedUseful=false;


			} else {
				String statement = "";
				System.out.println("Please choose who to talk to!");
			}
		}

		//If user wants to switch bots
		while (pickedBot==true)
		{
			//getResponse methods gets the trimmed response of each statement in the respective bots
			while (pickedLevin==true)
			{
				botChanger=chatbot.getResponse().toLowerCase();
				if (botChanger.equals("useless")||botChanger.equals("useful")) {
					break;
				}
			}
			while (pickedUseless==true)
			{
				botChanger2=chatbot2.getResponse().toLowerCase();
				if (botChanger2.equals("levin")||botChanger2.equals("useful")) {
				break;
				}
			}
			while (pickedUseful==true)
			{
				botChanger3=chatbot3.getResponse().toLowerCase();
				if (botChanger3.equals("levin")||botChanger3.equals("useless")) {
					break;
				}
			}



			//LevinBotChanges
			if (botChanger2.equals("levin")||botChanger3.equals("levin")) {
					String statement="";
					//Use Logic to control which chatbot is handling the conversation\
					//This example has only chatbot


					System.out.println("H-E-L-L-O! It's me M-R. L-E-V-I-N");
					statement = in.nextLine();
					chatbot.chatLoop(statement);


					statement = in.nextLine();

					pickedBot = true;
					pickedLevin=true;
					pickedUseful=false;
					pickedUseless=false;

				//UsefulBotChanges
			}else if (botChanger.equals("useful")||botChanger2.equals("useful")) {
				String statement="";
					//Use Logic to control which chatbot is handling the conversation\
					//This example has only chatbot


					chatbot3.chatLoop(statement);


					statement = in.nextLine();

					pickedBot = true;
					pickedUseful=true;
					pickedLevin=false;
					pickedUseless=false;


				//UselessBotChanges
			}else if (botChanger.equals("useless")||botChanger3.equals("useless")) {
				String statement="";
					//Use Logic to control which chatbot is handling the conversation\
					//This example has only chatbot


					chatbot2.chatLoop(statement);


					statement = in.nextLine();
					pickedBot=true;
					pickedUseless=true;
					pickedLevin=false;
					pickedUseful=false;

			}
		}
	}
}
