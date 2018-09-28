package Objects;

import java.util.Date;

public aspect logAspect {
	private Date date = new Date();
	
	pointcut log() : execution(private void writeToHandler* (..));
	
	after() returning() : log() {
		System.out.println(date + " " + this.getClass().getName());
	}
}
