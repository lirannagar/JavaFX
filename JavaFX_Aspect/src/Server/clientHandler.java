package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class clientHandler implements Runnable {
	private Socket socket;
	DataInputStream fromClient;
	PrintStream toClient;
	
	/** Construct a thread */
	public clientHandler(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// Create data input and output streams
		try {
		fromClient = new DataInputStream(socket.getInputStream());
		toClient = new PrintStream(socket.getOutputStream());
		
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
