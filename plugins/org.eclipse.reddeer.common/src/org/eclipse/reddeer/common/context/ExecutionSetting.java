/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.common.context;

import org.eclipse.reddeer.common.logging.LogLevel;
import org.eclipse.reddeer.common.logging.MessageType;
import org.eclipse.reddeer.common.properties.RedDeerProperties;

/**
 * Recognized RedDeer test execution parameters
 * 
 * @author Jiri Peterka
 *
 */
public class ExecutionSetting {
	private static ExecutionSetting instance;
	private boolean pauseFailedTest = false;
	private int logMessageFilter;
	private int logLevel = Integer.MAX_VALUE;
	private boolean filterSet = false;

	/**
	 * Provides ExecutionSetting instance.
	 *
	 * @return instance
	 */
	public static ExecutionSetting getInstance() {
		if (instance == null) {
			instance = new ExecutionSetting();
			instance.pauseFailedTest = RedDeerProperties.PAUSE_FAILED_TEST.getBooleanValue();
			String logMessageFilterText = RedDeerProperties.LOG_MESSAGE_FILTER.getValue();
			String logLevel = RedDeerProperties.LOG_LEVEL.getValue();
			instance.logLevel = LogLevel.ALL.fromString(logLevel).getValue();
			instance.parseLogMessageFilter(logMessageFilterText);
		}
		return instance;
	}

	private void parseLogMessageFilter(String logMessageTypeParam) {

		String[] parts = logMessageTypeParam.split("\\|");
		for (String p : parts) {
			if (p.equalsIgnoreCase("DUMP")) {
				addLogMessageType(MessageType.DUMP);
			} else if (p.equalsIgnoreCase("WARN")) {
				addLogMessageType(MessageType.WARN);
			} else if (p.equalsIgnoreCase("ERROR")) {
				addLogMessageType(MessageType.ERROR);
			} else if (p.equalsIgnoreCase("TRACE")) {
				addLogMessageType(MessageType.TRACE);
			} else if (p.equalsIgnoreCase("DEBUG")) {
				addLogMessageType(MessageType.DEBUG);
			} else if (p.equalsIgnoreCase("INFO")) {
				addLogMessageType(MessageType.INFO);
			} else if (p.equalsIgnoreCase("STEP")) {
				addLogMessageType(MessageType.STEP);				
			} else if (p.equalsIgnoreCase("ALL")) {
				setLogMessageFilter(MessageType.ALL);
				return;
			} else if (p.equalsIgnoreCase("NONE")) {
				setLogMessageFilter(MessageType.NONE);
				return;
			}
		}

		// ALL if not set
		if (filterSet == false) {
			setLogMessageFilter(MessageType.ALL);
		}

	}

	private void addLogMessageType(int type) {
		logMessageFilter |= type;
		filterSet = true;
	}

	private ExecutionSetting() {

	}

	/**
	 * pauseFailedTest getter.
	 *
	 * @return true if -DpauseFailingTest=true, false otherwise (default)
	 */
	public boolean isPauseFailedTest() {
		return pauseFailedTest;
	}

	/**
	 * Returns log message filter (value that determines what kind of log
	 * messages will be printed).
	 *
	 * @return log message filter value set by -DlogMessageFilter or in the code
	 */
	public int getLogMessageFilter() {
		return logMessageFilter;
	}

	/**
	 * Sets log message filter (value that determines what kind of log messages
	 * will be printed). Usually you will use -DlogMessageFilter parameter
	 * instead of call this method
	 *
	 * @param value the new log message filter
	 */
	public void setLogMessageFilter(int value) {
		logMessageFilter = value;
	}

	/**
	 * Returns log level for log messages set by users .
	 *
	 * @return log level for log messages
	 */
	public int getLogLevel() {
		return logLevel;
	}

}