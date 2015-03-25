package org.jboss.reddeer.core.interceptor;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.common.logging.Logger;

/***
 * SyncInterceptor provides facility to inject selected user operations (like
 * logging, diagnostic, etc.) around Display.syncExec(). Interceptor methods are
 * not called recursively from other interceptor methods (to avoid infinite
 * loop)
 * 
 * 
 * It's not expected that you provide UI operation that changes UI state. But if
 * you do, you are responsible for restoring original state.
 * 
 * Should be used from single working thread. Otherwise synchronization is up to
 * a user.
 * 
 * @author Jiri Peterka
 *
 */
public class SyncInterceptorManager {

	public static SyncInterceptorManager instance = null;;
	
	private Logger log = Logger.getLogger(SyncInterceptorManager.class);
	private Map<String, ISyncInterceptor> syncInterceptors = new LinkedHashMap<String, ISyncInterceptor>();
	private boolean isIntercepted = false;

	/**
	 * Register interceptor if it doesn't exist
	 * @name interceptor name 
	 * @param interceptor implementation
	 */
	public void register(String name, ISyncInterceptor interceptor) {
		if (!syncInterceptors.containsKey(name)) {
			syncInterceptors.put(name, interceptor);
		} else {
			throw new RedDeerException("Interceptor " + name + " is already registered");
		}
	}
	
	/**
	 * Unregister interceptor if it exist 
	 * @name interceptor name 
	 */
	public void unregister(String name) {
		if (syncInterceptors.containsKey(name)) {
			syncInterceptors.remove(name);
		} else {
			throw new RedDeerException("Interceptor " + name + " doesn't exist registered");
		}
	}
	
	/**
	 * Unregister all registrated interceptors  
	 */
	public void unregisterAll() {
		syncInterceptors.clear();
	}
	
	/**
	 * Checks if interceptor is registered 
	 * @name interceptor name 
	 */
	public boolean isRegistered(String name) {
		return syncInterceptors.containsKey(name);
	}
	
	/**
	 * Returns SyncInterceptorManager instance
	 * @return SyncInterceptorManager instance
	 */
	public static SyncInterceptorManager getInstance() {
		if (instance == null) {
			instance = new SyncInterceptorManager();
		}
		return instance;
	}

	
	/**
	 * Perform registrated operation(s) before sync 
	 */
	public void performBeforeSync() {
		for (Map.Entry<String, ISyncInterceptor> op : syncInterceptors
				.entrySet()) {
			isIntercepted = true;
			log.trace("SyncExec intercepted before by " + op.getKey());
			try {
				op.getValue().beforeSyncOp();
			} catch (Exception e) {
				log.error("BeforeSync interceptor error: " + op.getKey() + " :"
						+ e.getMessage());
				isIntercepted = false;
				throw e;
			}
			isIntercepted = false;
		}	
	}
	
	/**
	 * Perform registrated operation(s) after sync 
	 */
	public void performAfterSync() {

		for (Map.Entry<String, ISyncInterceptor> op : syncInterceptors
				.entrySet()) {
			isIntercepted = true;
			log.trace("SyncExec intercepted after by " + op.getKey());
			try {
				op.getValue().afterSyncOp();
			} catch (Exception e) {
				log.error("AfterSync interceptor error: " + op.getKey() + " :"
						+ e.getMessage());
				isIntercepted = false;
				throw e;
			}
			isIntercepted = false;
		}
	}
	
	/**
	 * Return true if intercepted code is being executed.
	 * @return true if intercepted code is being executed.
	 */
	public boolean isIntercepted() {
		return isIntercepted;
	}

}
