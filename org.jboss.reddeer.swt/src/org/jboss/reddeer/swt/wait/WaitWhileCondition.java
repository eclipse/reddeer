package org.jboss.reddeer.swt.wait;

import org.jboss.reddeer.swt.condition.IConditionWithDescription;

/**
 * Waits while condition is fulfilled
 * @author Vlado Pakan
 *
 */
public class WaitWhileCondition extends AbstractWaitWithCondition {

	public WaitWhileCondition(IConditionWithDescription condition, int timeOut) {
		super(condition, timeOut);
		waitWhile();
	}
	
	public WaitWhileCondition(IConditionWithDescription condition) {
		this(condition,AbstractWaitWithCondition.DEFAULT_TIME_OUT);
	}

}
