package org.jboss.reddeer.swt.wait;

import org.apache.log4j.Logger;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Abstract class for waiting classes
 * @author Vlado Pakan
 *
 */
public class AbstractWait {
	protected final Logger log = Logger.getLogger(this.getClass());
	protected static final int DEFAULT_TIME_OUT = 1000;
	protected int timeOut = 0;
		
	public AbstractWait(){
		this(AbstractWait.DEFAULT_TIME_OUT);
	}
	
	public AbstractWait(int timeOut){
		log.debug("Sets waiting timeout to " + timeOut + " miliseconds");
		this.timeOut = timeOut;
	}
	
	public void waitWithTimeout(){
		try {
			log.debug("Wait with timeout " + timeOut);
			Bot.get().wait(timeOut);
		} catch (InterruptedException e) {
			// Do nothing
		}
	}
}
