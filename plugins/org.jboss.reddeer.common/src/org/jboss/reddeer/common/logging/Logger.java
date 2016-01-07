/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.common.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jboss.reddeer.common.context.ExecutionSetting;
import org.jboss.reddeer.common.exception.RedDeerException;

/**
 * Simple console logger for Reddeer
 * 
 * @author Jiri Peterka
 *
 */
public class Logger {

	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");

	private static final String error = "ERROR";
	private static final String warning = "WARNING";
	private static final String debug = "DEBUG";
	private static final String trace = "TRACE";
	private static final String info = "INFO";
	private static final String dump = "DUMP";
	private static final String fatal = "FATAL";
	private static final String step = "STEP";

	private Class<? extends Object> loggerClass;

	/**
	 * Returns logger based on given class.
	 *
	 * @param c given class
	 * @return logger instance
	 */
	public static Logger getLogger(Class<? extends Object> c) {
		return new Logger(c);
	}

	/**
	 * Create Logger based on given class.
	 *
	 * @param c given class
	 */
	public Logger(Class<? extends Object> c) {
		this.loggerClass = c;
	}

	/**
	 * log debug message.
	 *
	 * @param msg message
	 */
	public void debug(String msg) {
		print(debug, msg, MessageType.DEBUG);
	}

	/**
	 * Log debug message using formatting string and arguments.
	 *
	 * @param fmtString Formatting string
	 * @param args Arguments
	 * @see java.lang.String#format(String, Object...)
	 */
	public void debug(String fmtString, Object... args) {
		debug(String.format(fmtString, args));
	}

	/**
	 * log trace message.
	 *
	 * @param msg message
	 */
	public void trace(String msg) {
		print(trace, msg, MessageType.TRACE);
	}

	/**
	 * Log trace message using formatting string and arguments.
	 *
	 * @param fmtString Formatting string
	 * @param args            Arguments
	 * @see java.lang.String#format(String, Object...)
	 */
	public void trace(String fmtString, Object... args) {
		trace(String.format(fmtString, args));
	}

	/**
	 * log warning message.
	 *
	 * @param msg message
	 */
	public void warn(String msg) {
		print(warning, msg, MessageType.WARN);
	}

	/**
	 * Log warning message using formatting string and arguments.
	 *
	 * @param fmtString Formatting string
	 * @param args Arguments
	 * @see java.lang.String#format(String, Object...)
	 */
	public void warn(String fmtString, Object... args) {
		warn(String.format(fmtString, args));
	}

	/**
	 * log error message.
	 *
	 * @param msg            message
	 */
	public void error(String msg) {
		print(error, msg, MessageType.ERROR);
	}

	/**
	 * Log error message using formatting string and arguments.
	 *
	 * @param fmtString Formatting string
	 * @param args Arguments
	 * @see java.lang.String#format(String, Object...)
	 */
	public void error(String fmtString, Object... args) {
		error(String.format(fmtString, args));
	}

	/**
	 * log error message.
	 *
	 * @param msg message
	 * @param t throwable
	 */
	public void error(String msg, Throwable t) {
		print(error, msg, MessageType.ERROR);
		printStackTraceRecursive(t);
	}

	/**
	 * Log error message using formatting string and arguments, and logs
	 * corresponding exception.
	 *
	 * @param fmtString Formatting string
	 * @param t the t
	 * @param args Arguments
	 * @see java.lang.String#format(String, Object...)
	 */
	public void error(String fmtString, Throwable t, Object... args) {
		error(String.format(fmtString, args), t);
	}

	/**
	 * log info message.
	 *
	 * @param msg message
	 */
	public void info(String msg) {
		print(info, msg, MessageType.INFO);
	}

	/**
	 * Log info message using formatting string and arguments.
	 *
	 * @param fmtString Formatting string
	 * @param args Arguments
	 * @see java.lang.String#format(String, Object...)
	 */
	public void info(String fmtString, Object... args) {
		info(String.format(fmtString, args));
	}

	/**
	 * log dump message.
	 *
	 * @param msg message
	 */
	public void dump(String msg) {
		print(dump, msg, MessageType.DUMP);
	}

	/**
	 * Log dump message using formatting string and arguments.
	 *
	 * @param fmtString Formatting string
	 * @param args Arguments
	 * @see java.lang.String#format(String, Object...)
	 */
	public void dump(String fmtString, Object... args) {
		dump(String.format(fmtString, args));
	}

	/**
	 * log fatal message.
	 *
	 * @param msg message
	 */
	public void fatal(String msg) {
		print(fatal, msg, MessageType.FATAL);
	}
	
	/**
	 * Log step message using formatting string and arguments
	 * 
	 * This should not be used directly in RedDeer API.
	 * 
	 * Step message is strictly intended to be used in tests only to describe
	 * human readable flow that can be used in issue tracker or for describing
	 * test flow.
	 * 
	 * @param fmtString
	 *            Formatting string
	 * @param args
	 *            Arguments
	 * @see java.lang.String#format(String, Object...)
	 */
	public void step(String fmtString, Object... args) {
		step(String.format(fmtString, args));
	}

	/**
	 * log step message
	 * 
	 * This should not be used directly in RedDeer API.
	 * 
	 * Step message is strictly intended to be used in tests only to describe
	 * human readable flow that can be used in issue tracker or for describing
	 * test flow
	 * 
	 * @param msg
	 *            message
	 */
	public void step(String msg) {
		print(step, msg, MessageType.STEP);
	}
	

	/**
	 * Log fatal message using formatting string and arguments.
	 *
	 * @param fmtString Formatting string
	 * @param args Arguments
	 * @see java.lang.String#format(String, Object...)
	 */
	public void fatal(String fmtString, Object... args) {
		fatal(String.format(fmtString, args));
	}

	private void print(String severity, String msg, int type) {
		if ((type & ExecutionSetting.getInstance().getLogMessageFilter()) != type)
			return;
		
		int logLevel = ExecutionSetting.getInstance().getLogLevel();
		if (logLevel < getLevelFromMsgType(type).getValue()) {
			return; 
		}

		StringBuilder sb = new StringBuilder();
		sb.append(dateFormat.format(new Date()) + " ");		

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

	private LogLevel getLevelFromMsgType(int msgType) {
		LogLevel ret = LogLevel.ALL;
		switch (msgType) {
			
			case MessageType.NONE: ret = LogLevel.OFF; break;
			case MessageType.FATAL: ret = LogLevel.FATAL; break;
			case MessageType.DUMP: ret = LogLevel.DUMP; break;
			case MessageType.ERROR: ret = LogLevel.ERROR; break;
			case MessageType.STEP: ret = LogLevel.STEP; break;
			case MessageType.INFO: ret = LogLevel.INFO; break;
			case MessageType.WARN: ret = LogLevel.WARN; break;
			case MessageType.DEBUG: ret = LogLevel.DEBUG; break;
			case MessageType.TRACE: ret = LogLevel.TRACE; break;
			case MessageType.ALL: ret = LogLevel.ALL; break;
			default: throw new RedDeerException("Unsupported log level:" + msgType);
		}
		return ret;
	}
			
	private String getThreadName() {
		return Thread.currentThread().getName();
	}

	private void printStackTraceRecursive(Throwable t) {
		if ((t != null) && (t.getStackTrace() != null)) {
			t.printStackTrace();
			printStackTraceRecursive(t.getCause());
		}
	}
}