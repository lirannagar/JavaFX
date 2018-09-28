package aClient;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;

import com.sun.corba.se.impl.io.TypeMismatchException;

public class Driver {
	static DataInputStream fromNetInputStream;
	static DataInputStream consoleInput;
	static PrintStream toNetOutputStream;
	
	static final String OPTIONS_STR = "Please choose one of the below:\n"
			+ "Create Missile Destructor                 2\n"
			+ "Create Launcher                           3\n"
			+ "Launch                        			 4\n"
			+ "Intercept Missile                     	 5\n"
			+ "Exit                                      0\n";	
	
	static final String RECV = "Recieved from server ";
	
	public static void main(String[] args) {
		Socket socket = null;
		String ID = "";
		String line = "";

		try {
			socket = new Socket("localhost", 7000);
			System.out.println(new Date() + " --> Connected to server at "
					+ socket.getLocalAddress() + ":" + socket.getLocalPort());
			fromNetInputStream = new DataInputStream(socket.getInputStream());
			toNetOutputStream = new PrintStream(socket.getOutputStream());
			consoleInput = new DataInputStream(System.in);
			
			System.out.println(new Date() + " --> Recieved from server: "
					+ fromNetInputStream.readLine()); // receive the 'welcome' message
			while (!line.equals("0") && !line.equals("exit")) {
				System.out.println(OPTIONS_STR);
				line = consoleInput.readLine();
				toNetOutputStream.println(line);
				System.out.println(new Date() + " --> Recieved from server: "
						+ fromNetInputStream.readLine());
					inputINIT(line);
			}
		} catch (Exception e) {	System.err.println(e);
		} finally {
			try {
				socket.close();
				System.out.println("Client said goodbye..");
			} catch (IOException e) {}
		}
	}
	
	private static void inputINIT(String input) throws IOException {
		if (input.length() < 2) {
			switch (Integer.valueOf(input)) {
				case 1:
					break;
				case 2:
					System.out.println("Enter Missile Destructor ID");
					String mID = consoleInput.readLine();
					toNetOutputStream.println(mID);
					System.out.println("Recieved from server: addMissileDestructor() performed: " + fromNetInputStream.readLine());
					break;
				case 3:
					//createLauncher();
					System.out.println("Enter Missile Launcher ID");
					String lID = consoleInput.readLine();
					toNetOutputStream.println(lID);
					System.out.println("Recieved from server: addLauncher() performed: " + fromNetInputStream.readLine());
					break;
				case 4:
					//launch();
					try {
					System.out.println("Enter destination");
					String destination = consoleInput.readLine();
					toNetOutputStream.println(destination);
					System.out.println("Enter fly time");
					int flyTime = Integer.valueOf(consoleInput.readLine());
					toNetOutputStream.println(flyTime);
					System.out.println("Enter damage");
					int damage = Integer.valueOf(consoleInput.readLine());
					toNetOutputStream.println(damage);
					} catch (TypeMismatchException e) {
						System.out.println("Wrong value entered");
						break;
					}
					System.out.println("Received from server: launch() performed: " + fromNetInputStream.readLine());
					break;
				case 5:
					System.out.println("Enter missile ID");
					String missileID = consoleInput.readLine();
					toNetOutputStream.println(missileID);
					System.out.println("Enter time to intercept");
					int time = Integer.valueOf(consoleInput.readLine());
					toNetOutputStream.println(time);
					System.out.println("Enter missile destructor ID");
					String mdID = consoleInput.readLine();
					toNetOutputStream.println(mdID);
					System.out.println("Received from Server: Interception launched toward missile ID " + missileID);
					break;
			}
			
		}
	}
}

