package org.jboss.reddeer.swt.wait;

import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Waits while condition is fulfilled
 * 
 * @author Vlado Pakan
 * @author Lucia Jelinkova
 *
 */
public class WaitWhile extends AbstractWait {

	public WaitWhile(WaitCondition condition) {
		super(condition);
	}
	
	public WaitWhile(WaitCondition condition, TimePeriod timeout) {
		super(condition, timeout);
	}
	
	@Override
	protected void wait(WaitCondition condition) {
		Bot.get().waitWhile(wrapCondition(condition), getTimeout().getSeconds() * 1000);
	}
	
	@Override
	protected String description() {
		return "waiting while: ";
	}
}
