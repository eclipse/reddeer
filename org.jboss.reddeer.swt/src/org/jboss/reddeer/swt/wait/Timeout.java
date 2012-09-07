package org.jboss.reddeer.swt.wait;

/**
 * Represents the time scale of how long the user operation might last.
 * 
 * @author Lucia Jelinkova
 *
 */
public enum Timeout {

	NORMAL(10), LONG(60), VERY_LONG(300);
	
	private long seconds;
	
	private Timeout(long seconds) {
		this.seconds = seconds;
	}
	
	public long getSeconds(){
		return seconds;
	}
}
