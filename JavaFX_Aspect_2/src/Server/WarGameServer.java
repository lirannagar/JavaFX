package Server;

import java.io.*;
import java.net.*;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mongodb.bulk.WriteRequest.Type;

import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import DB.*;
import Listeners.DBactions;
import Model.WarModel;
import View.*;
import Objects.*;

public class WarGameServer extends Application implements serverProtocol {
	public static DBselect DAOtype;
	private WarModel war = new WarModel();
	private WarView view = new WarView();
	protected static TextArea serverText = new TextArea();
	private ServerSocket serverSocket;
	private final int PORT_NUMBER = 7000;
	private actionsType a;
	private boolean flag = true;
	private static int idGen = 100;
	DataInputStream fromClient;
	PrintStream toClient;
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		Scene scene = new Scene(serverText, 600, 300);
		stage.setScene(scene);
		stage.setTitle("Server Exc1");
		stage.show();
		
		 ApplicationContext theContext = 
	                new ClassPathXmlApplicationContext("db.xml");
		 
		 DBselect DAOtype = (DBselect)theContext.getBean("DAO");
		
		new Thread(() -> {
			try {
				serverSocket = new ServerSocket(PORT_NUMBER);
				Platform.runLater(() -> {
					serverText.appendText("MultiThreadServer for WarGame started at " + new Date() + '\n');
					serverText.appendText(DAOtype.toString());
				});
				while (true) {
					Socket socket = serverSocket.accept();
					fromClient = new DataInputStream(socket.getInputStream());
					toClient = new PrintStream(socket.getOutputStream());
					Platform.runLater(() -> { 
						serverText.appendText("Connection from " + socket + " at " + new Date() + '\n');
					});
					toClient.println(" Welcome to WarGameServer! "); //Notify Client
					String line = "";
					String clientIPPort = socket.toString().substring(SOCKET_IP_LOCATION, SOCKET_PORT_END);
					Platform.runLater(() -> {
						serverText.appendText("Client Thread -> " + Thread.currentThread().getName() + '\n');
					});
					while (flag) { //--> to keep user options menu running and server listening
						line = fromClient.readLine();
						serverText.appendText("Client connected from " + socket + " sent: " + line + '\n');
						try {	
							if (line.length() > 1) {	
									if (checkInputString(line)) {
										serverText.appendText("Sever received a legal request from client at (" + clientIPPort + "). Performing Task...\n");
										inputINITString(line);
									} else
										System.out.println("Server received an unknown request \n");
							}
							if (Integer.valueOf(line) > 0 && Integer.valueOf(line) <= 6) {
								serverText.appendText("Server received a legal request from client at (" + clientIPPort + "). Performing task...\n");
								toClient.println(" Server received : " + line);
								inputINIT(Integer.valueOf(line));
							} else if (Integer.valueOf(line) == EXIT) { //ZERO
								serverText.appendText("Server received an EXIT request from client at (" + clientIPPort + "). Goodbye!");
								toClient.println(" Server received an EXIT request \n");
								flag = false;
								} else {
							serverText.appendText("Server received an unknown request \n");
							toClient.println(" Server to client : illegal request - try again ");
							}
					} catch (NumberFormatException e) {
						toClient.println(" Server received : " + line);
					}
					
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	public static void main(String[] args) {
		//ApplicationContext theContext = new ClassPathXmlApplicationContext("db.xml");
		//	mongoDAO theDB = (mongoDAO)theContext.getBean("myDB"); 
		//	ConsoleApplication console = new ConsoleApplication(theDB);
		launch(args);
	}
	
	private void inputINITString(String input) throws IOException {
		//TODO handle user input format 
		String formattedInput = input.toUpperCase().trim().replaceAll(" ", "_");
		System.out.println(formattedInput);
		switch (formattedInput) {
		case "CREATE_LAUNCHER":
			createLauncher();
			break;
		case "CREATE_MISSILE_DESTRUCTOR":
			createLauncherDestructor();
			break;
		case "LAUNCH":
			launch();
			break;
		case "INTERCEPT_MISSILE":
			interceptMissile();
			break;
		default:
			break;
		}
	}
	
	private void inputINIT(int n) throws IOException {
		switch (n) {
		case 1:
			createLauncherDestructor();
			break;
		case 2:
			createMissileDestructor();
			break;
		case 3:
			createLauncher();
			break;
		case 4:
			launch();
			break;
		case 5:
			interceptMissile();
			break;
		case 6:
			
			break;
		}
	}
	
	private boolean checkInputString(String input) {
		//line.toUpperCase().equals(actionsType.values()) || line.toUpperCase().replaceAll(" ","_").equals(actionsType.values())
		if (input.toUpperCase().equals("EXIT")) {
			this.flag = false;
			return false;
		}
		actionsType[] arr = actionsType.values();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].toString().equals(input.toUpperCase()) || arr[i].toString().equals(input.toUpperCase().replaceAll(" ", "_"))) {
				return true;
			}
		}
		return false;
	}
	
	protected void append(String s) {
		serverText.appendText(s);
	}

	@Override
	public void createLauncher() throws IOException {
		// TODO Auto-generated method stub
		String id = fromClient.readLine();
		Launcher l = war.addLauncher(id, false);
		view.addLauncher(l);
		toClient.println("Launcher ID " + id + " created");
		return;
	}


	public void createMissileDestructor() throws IOException {
		// TODO Auto-generated method stub
		String id = fromClient.readLine();
		MissileDestructor md = war.addMissileDestructor(id);
		toClient.println("Missile Destructor ID " + id + " created");
		return;
	}

	public void createLauncherDestructor() throws IOException {
		// TODO Auto-generated method stub
		String type = "";
		while (!type.equals("ship") | !type.equals("plane")) {
			type = fromClient.readLine();
		}
		LauncherDestructor mld = war.addLauncherDestructor(type);
		toClient.println("Missile Launcher Destructor ID " + mld.getId() + " created");
		return;
	}

	@Override
	public void launch() throws IOException {
		// TODO Auto-generated method stub
		String destination = fromClient.readLine();
		// generate missile ID
		int flyTime = Integer.valueOf(fromClient.readLine());
		int damage = Integer.valueOf(fromClient.readLine());
		Object o = new Missile("M" + idGen, destination, 0, flyTime, damage, null);
		war.onLaunchingEvent(o);
		toClient.println("Missile ID " + "M" + idGen + " created and launched towards " + destination + " /n");
		++idGen;
		return;
	}

	@Override
	public void interceptMissile() throws IOException {
		// TODO Auto-generated method stub
		String missileID = fromClient.readLine();
		int fireTime = Integer.valueOf(fromClient.readLine());
		String mdID = fromClient.readLine();
		Object o = new DestructedMissile(missileID, fireTime , mdID);
		war.onInterceptEvent(o);
		toClient.println("Interception launched towards Missile ID " + missileID);
		return;
	}


}
