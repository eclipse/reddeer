package org.jboss.reddeer.junit;


/**
 * Recognized RedDeer test execution parameters 
 * @author Jiri Peterka
 *
 */
public class ExecutionSetting {
	private static ExecutionSetting instance;
	private boolean debugEnabled = true;
	private boolean pauseFailedTest = false; 
	
	
	/**
	 * Provides ExecutionSetting instance
	 * @return instance
	 */
	public static ExecutionSetting getInstance() {
		if (instance == null) {
			instance = new ExecutionSetting();
			instance.debugEnabled = instance.readSystemProperty("logDebug",true);
			instance.pauseFailedTest = instance.readSystemProperty("pauseFailedTest",false);
		}
		return instance;
	}
	
	private boolean readSystemProperty(String var, boolean b) {
		String val = System.getProperty(var);
		if ((val == null) || (!val.equals("false") && (!val.equals("true")))) {
			return b;
		} else return Boolean.parseBoolean(val);		
	}

	private ExecutionSetting() {
		
	}

	/**
	 * isDebugEnabled getter
	 * @return true if -DlogDebug=true (default), false otherwise
	 */
	public boolean isDebugEnabled() {
		return debugEnabled;
	}

	/**
	 * Turn debugging on or off
	 *
	 * @param enabled
	 */
	public void setDebugEnabled(boolean enabled) {
		debugEnabled = enabled;
	}

	/**
	 * pauseFailedTest getter
	 * @return true if -DpauseFailingTest=true, false otherwise (default)
	 */	
	public boolean isPauseFailedTest() {
		return pauseFailedTest;
	}
	
}
