package org.jboss.reddeer.swt.wait;

import org.jboss.reddeer.swt.condition.IConditionWithDescription;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Abstract class for classes waiting with condition
 * @author Vlado Pakan
 *
 */
public class AbstractWaitWithCondition extends AbstractWait{
	
	private IConditionWithDescription condition; 
	
	public AbstractWaitWithCondition(IConditionWithDescription condition){
		this(condition,AbstractWait.DEFAULT_TIME_OUT);
	}
	
	public AbstractWaitWithCondition(IConditionWithDescription condition, int timeOut){
		super(timeOut);
		this.condition = condition;
	}
	
	public void waitWhile(){
		log.debug("Waits while " + condition.getDescription() + " condition is fullfiled");
		Bot.get().waitWhile(condition,timeOut);
	}
	
	public void waitUntil(){
		log.debug("Wait until " + condition.getDescription() + " condition is fullfiled");
		Bot.get().waitUntil(condition,timeOut);
	}
	
}
