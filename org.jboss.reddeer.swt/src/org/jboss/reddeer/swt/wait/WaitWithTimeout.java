package org.jboss.reddeer.swt.wait;

/**
 * Waits with timeout
 * @author Vlado Pakan
 *
 */
public class WaitWithTimeout extends AbstractWait {

	public WaitWithTimeout(int timeOut) {
		super(timeOut);
		waitWithTimeout();
	}
	
	public WaitWithTimeout() {
		this(AbstractWait.DEFAULT_TIME_OUT);
	}

}
