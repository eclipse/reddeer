package org.jboss.reddeer.core.interceptor;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.common.logging.Logger;

/**
 * SyncInterceptor provides facility to inject selected user operations (like
 * logging, diagnostic, etc.) around Display.syncExec(). Interceptor methods are
 * not called recursively from other interceptor methods (to avoid infinite
 * loop).
 * 
 * It's strongly advised to don't provide UI operation that changes UI state. Such action
 * could cause breaking state and it is required to restore original state manually.
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
	 * Registers interceptor if it doesn't exist.
	 * 
	 * @param name interceptor name 
	 * @param interceptor implementation of interceptor
	 */
	public void register(String name, ISyncInterceptor interceptor) {
		if (!syncInterceptors.containsKey(name)) {
			syncInterceptors.put(name, interceptor);
		} else {
			throw new RedDeerException("Interceptor " + name + " is already registered");
		}
	}
	
	/**
	 * Unregisters interceptor if it exists. 
	 * 
	 * @param name interceptor name 
	 */
	public void unregister(String name) {
		if (syncInterceptors.containsKey(name)) {
			syncInterceptors.remove(name);
		} else {
			throw new RedDeerException("Interceptor " + name + " doesn't exist registered");
		}
	}
	
	/**
	 * Unregister all registered interceptors.
	 */
	public void unregisterAll() {
		syncInterceptors.clear();
	}
	
	/**
	 * Checks whether interceptor with specified name is registered or not.
	 *  
	 *
	 * @param name interceptor name
	 * @return true, if is registered
	 */
	public boolean isRegistered(String name) {
		return syncInterceptors.containsKey(name);
	}
	
	/**
	 * Returns SyncInterceptorManager instance.
	 * @return SyncInterceptorManager instance
	 */
	public static SyncInterceptorManager getInstance() {
		if (instance == null) {
			instance = new SyncInterceptorManager();
		}
		return instance;
	}

	
	/**
	 * Performs registered operation(s) before sync.
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
	 * Performs registered operation(s) after sync. 
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
	 * Finds out whether intercepted code is being executed or not.
	 * 
	 * @return true if intercepted code is being executed, false otherwise
	 */
	public boolean isIntercepted() {
		return isIntercepted;
	}

}
