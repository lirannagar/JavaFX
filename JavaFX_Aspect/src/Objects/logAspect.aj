package Objects;

import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public aspect logAspect {
	private Date date = new Date();
	private Logger theLogger;
	private FileHandler fileHandler;
	
	pointcut log() : execution(private void writeToHandler* (..));
	
	after() returning() : log() {
		System.out.println(date + " " + this.getClass().getName());
		try {
		fileHandler = new FileHandler("log.txt");
		theLogger = Logger.getLogger("warAspectLogger");
		theLogger.log(Level.INFO, "Event occured");
		} catch (IOException e) {
			
		}
	}
}
