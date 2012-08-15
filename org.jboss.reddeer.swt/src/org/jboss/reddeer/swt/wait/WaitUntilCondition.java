package org.jboss.reddeer.swt.wait;

import org.jboss.reddeer.swt.condition.IConditionWithDescription;
/**
 * Waits until condition is fulfilled
 * @author Vlado Pakan
 *
 */
public class WaitUntilCondition extends AbstractWaitWithCondition {

	public WaitUntilCondition(IConditionWithDescription condition, int timeOut) {
		super(condition, timeOut);
		waitUntil();
	}
	
	public WaitUntilCondition(IConditionWithDescription condition) {
		this(condition,AbstractWaitWithCondition.DEFAULT_TIME_OUT);
	}

}
