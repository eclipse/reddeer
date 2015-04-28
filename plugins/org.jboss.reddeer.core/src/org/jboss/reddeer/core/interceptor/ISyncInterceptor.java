package org.jboss.reddeer.core.interceptor;

/**
 * Display SyncExec interceptor. Serves to intercept user operation before and after each
 * call of Display.syncExec.
 *  
 * @author Jiri Peterka
 *
 */
public interface ISyncInterceptor {

	/**
	 * Method to be executed before syncExec is performed.
	 */
	void beforeSyncOp();
	
	/**
	 * Method to be executed after syncExec is performed.
	 */
	void afterSyncOp();
}
