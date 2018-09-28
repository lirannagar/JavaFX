package Server;

import java.io.IOException;

public interface serverProtocol {

	public enum actionsType {
		CREATE_LAUNCHER(3), CREATE_MISSILE_DESTRUCTOR(2), LAUNCH(4), INTERCEPT_MISSILE(5), EXIT(0);
		
		private int numVal;
		
		actionsType(int numVal) {
			this.numVal = numVal;
		}
	}
	
	public final int EXIT = 0;
	
	public final int SOCKET_IP_LOCATION = 13;
	
	public final int SOCKET_PORT_END = 33;
	
	public void createLauncher() throws IOException;
	
	public void launch() throws IOException;
	
	public void interceptMissile() throws IOException;
	
}
