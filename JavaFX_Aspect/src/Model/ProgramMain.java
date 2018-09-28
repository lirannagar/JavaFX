package Model;

import java.util.Scanner;
import java.util.logging.Logger;

import org.json.simple.parser.JSONParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import DB.sqlDAO;
import DB.mongoDAO;


public class ProgramMain implements FinalValuesInterface{

	public static Logger warLogger = Logger.getLogger("warLogger");
	
	public static void main(String[] args) throws SecurityException, ClassNotFoundException  {
	//	ApplicationContext theContext = new ClassPathXmlApplicationContext("db.xml");
	//	mongoDAO theDB = (mongoDAO)theContext.getBean("myDB"); 
	//	ConsoleApplication console = new ConsoleApplication(theDB);
		
		ViewWar view =null;
		Scanner in = new Scanner(System.in);
		JSONParser parser = new JSONParser();
		WarModel theWar = new WarModel();
		System.out.println("Do you want to read from file? (y/n)");
		char option = in.next().charAt(0);
		switch(option) {
		case 'y':
		case 'Y':
			view = new ViewWar(parser, theWar);
			break;
		case 'n':
		case 'N':			
			view = new ViewWar(theWar);
			break;		
		}
		in.close();
	
		
	}

}
