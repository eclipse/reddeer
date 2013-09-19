package org.jboss.reddeer.junit.logging;

import org.jboss.reddeer.junit.ExecutionSetting;

/**
 * Simple console logger for Reddeer
 * @author Jiri Peterka
 *
 */
public class Logger {

	
	private static final String error = "ERROR";
	private static final String warning = "WARNING";
	private static final String debug = "DEBUG";
	private static final String trace = "TRACE";
	private static final String info = "INFO";
	private static final String dump = "DUMP";
	private static final String fatal = "FATAL";
	
	private Class<? extends Object> loggerClass;
	
	/**
	 * Returns logger based on given class
	 * @param c given class
	 * @return logger instance
	 */
	public static Logger getLogger(Class<? extends Object> c) { 
		return new Logger(c);
	}
	
	/**
	 * Create Logger based on given class
	 * @param c given class
	 */
	public Logger(Class<? extends Object> c) {
		this.loggerClass = c;
	}

	/**
	 * log debug message
	 * @param msg message
	 */
	public void debug(String msg) {
		if (ExecutionSetting.getInstance().isDebugEnabled()) {
			print(debug,msg);
		}		
	}
	
	/**
	 * log tracemessage
	 * @param msg message
	 */
	public void trace(String msg) {
		if (ExecutionSetting.getInstance().isDebugEnabled()) {
			print(trace,msg);
		}		
	}
	
	/**
	 * log warning message
	 * @param msg message
	 */
	public void warn(String msg) {
		print(warning,msg);
	}

	/**
	 * log error message
	 * @param msg message
	 */
	public void error(String msg) {
		print(error,msg);
	}

	/**
	 * log error message
	 * @param msg message
	 * @param t throwable
	 */
	public void error(String msg, Throwable t) {
		print(error,msg + t.getStackTrace());
	}
	
	/**
	 * log info message
	 * @param msg message
	 */
	public void info(String msg) {
		print(info,msg);
	}

	/**
	 * log dump message
	 * @param msg message
	 */
	public void dump(String msg) {
		print(dump,msg);
	}
	
	/**
	 * log fatal message
	 * @param msg message
	 */
	public void fatal(String msg) {
		print(fatal,msg);
	}
	
	private void print(String severity, String msg) {
		StringBuilder sb = new StringBuilder();
		sb.append(severity);
		sb.append(" [");
		sb.append(getThreadName());
		sb.append("]");
		sb.append("[");		
		sb.append(loggerClass.getSimpleName());
		sb.append("] ");
		sb.append(msg);
		System.out.println(sb.toString());
	}
	
	private String getThreadName() {
		return Thread.currentThread().getName();
	}

	public boolean isDebugEnabled() {
		return ExecutionSetting.getInstance().isDebugEnabled();
	}
}
