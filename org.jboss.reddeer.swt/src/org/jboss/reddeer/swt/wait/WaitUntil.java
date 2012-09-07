package org.jboss.reddeer.swt.wait;

import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.util.Bot;
/**
 * Waits until condition is fulfilled
 * 
 * @author Vlado Pakan
 * @author Lucia Jelinkova
 *
 */
public class WaitUntil extends AbstractWait {

	public WaitUntil(WaitCondition condition) {
		super(condition);
	}
	
	public WaitUntil(WaitCondition condition, Timeout timeout) {
		super(condition, timeout);
	}
	
	@Override
	protected void wait(WaitCondition condition) {
		Bot.get().waitUntil(wrapCondition(condition), getTimeout().getSeconds() * 1000);
	}
}
