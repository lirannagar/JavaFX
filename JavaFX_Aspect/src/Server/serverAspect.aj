package Server;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import Objects.*;

import java.io.IOException;;

public aspect serverAspect {
private Date date = new Date();
public static Logger warLogger = Logger.getLogger("warLogger");

	pointcut MissileLauncher() : execution(public void *Launcher(..) throws IOException);
	
	after() returning() : MissileLauncher() {
		WarGameServer.serverText.appendText(date + " New Missile Launcher created\n");
		//warLogger.log(Level.INFO, "Missile Launcher created");
	}
	
	pointcut MissileDestructor() : execution(public void *MissileDestructor(..) throws IOException);
	
	after() returning() : MissileDestructor() {
		WarGameServer.serverText.appendText(date + " New Missile Destructor created\n");
	}
	
	pointcut Launch() : execution(public void *launch(..) throws IOException);
	
	after() returning() : Launch() {
		WarGameServer.serverText.appendText(date + " Missile launched\n");
	}
	
	pointcut Intercept() : execution(public void intercept*(..) throws IOException);
	
	after() returning() : Intercept() {
		WarGameServer.serverText.appendText(date + " Interception launched\n");
	}

}
