package AOP;


import java.util.logging.Logger;

public aspect logAspect {
	private static Logger warLogger = Logger.getLogger("warLogger");
	
	pointcut greeting() : execution(* Hello.viewWar(..));
	
	
	after() returning() : greeting() {
		System.out.println("I am printed by Aspect");
	}
}
