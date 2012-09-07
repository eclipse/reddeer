package org.jboss.reddeer.swt.wait;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.jboss.reddeer.swt.condition.WaitCondition;

/**
 * Common ancestor for waiting classes. Contains abstract {@link #wait(WaitCondition)} method
 * that is called in the constructor. 
 * 
 * @author Vlado Pakan
 * @author Lucia Jelinkova
 *
 */
public abstract class AbstractWait {
	
	protected final Logger log = Logger.getLogger(this.getClass());
	
	private Timeout timeout;
		
	public AbstractWait(WaitCondition condition){
		this(condition, Timeout.NORMAL);
	}
	
	public AbstractWait(WaitCondition condition, Timeout timeout){
		this.timeout = timeout;
		wait(condition);
	}
	
	protected abstract void wait(WaitCondition condition);
	
	protected Timeout getTimeout() {
		return timeout;
	}
	
	protected ICondition wrapCondition(final WaitCondition condition){
		return new ICondition() {
			
			@Override
			public boolean test() throws Exception {
				return condition.test();
			}
			
			@Override
			public void init(SWTBot bot) {
			}
			
			@Override
			public String getFailureMessage() {
				return condition.getFailureMessage();
			}
		};
	}
}
