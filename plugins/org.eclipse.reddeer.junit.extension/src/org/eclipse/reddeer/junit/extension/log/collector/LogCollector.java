/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.extension.log.collector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.direct.platform.Platform;

/**
 * Log collector collect Eclipse workbench log and process it for a specific test class and test methods.
 * This is useful for post test run evaluation whether there were any silent errors/warnings shown in log.
 * 
 * @author mlabuda@redhat.com
 * @since 1.2.0
 */
public class LogCollector {

	private static final long timestamp = System.currentTimeMillis();
	private static final Logger log = Logger.getLogger(LogCollector.class);

	/**
	 * Gets file name for a file with collected log entries. File name contains
	 * config name and time stamp.
	 * 
	 * @param config RedDeer config
	 * @param className test class name
	 * @return file name of log file
	 */
	public String getFileName(String config, String className) {
		return "config-" + config + "_class-" + className + "_" + getID();
	}

	/**
	 * Gets log file ID in form of date in format yyyy-MM-dd_HH-mm-ss.
	 * 
	 * @return id of log file in
	 */
	private String getID() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		return dateFormat.format(new Date(timestamp)).toString();
	}

	/**
	 * Gets location of directory where file with collected log entries
	 * is/supposed to be saved. Directory is set by default to target/reddeer-log.
	 * 
	 * @return path to log directory
	 */
	public String getDirectory() {
		return System.getProperty("user.dir") + File.separator + "target" + File.separator + "reddeer-log"
				+ File.separator;
	}

	/**
	 * Gets path to RedDeer log file. Path consists of log directory and log file name.
	 * 
	 * @param config RedDeer config
	 * @param className test class name
	 * @return path to RedDeer log file
	 */
	public String getLogFilePath(String config, String className) {
		return getDirectory() + File.separator + getFileName(config, className);
	}

	/**
	 * Gets RedDeer log file for specified config. If file does not exists, it is created at first.
	 * 
	 * @param config RedDeer config
	 * @param className test class name
	 * @return log file
	 */
	public File getLogFile(String config, String className) {
		File logFile = new File(getLogFilePath(config, className));
		// First call, if file does not exists
		if (!logFile.exists()) {
			logFile.getParentFile().mkdirs();
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				// Should not happen
				e.printStackTrace();
			}
		}
		return logFile;
	}

	/**
	 * Processes workbench log. It copies log entries from Eclipse workbench log to RedDeer log file of 
	 * a specific test class.
	 * 
	 * @param config RedDeer config
	 * @param className test class name
	 * @param logDescription description related to log entries
	 */
	public void processWorkbenchLog(String config, String className, String logDescription) {
		if(eclipseLogFileExists()){
			try (BufferedReader br = new BufferedReader(new FileReader(Platform.getWorkbenchLog()));
					BufferedWriter bw = new BufferedWriter(new FileWriter(getLogFile(config, className), true))) {
				String line = br.readLine();
				if (line != null) {
					bw.write(logDescription + "\n\n");
					bw.write(line + "\n");
					while ((line = br.readLine()) != null) {
						bw.write(line + "\n");
					}
					bw.write("\n\n");
				}
			} catch (FileNotFoundException e) {
				// Should not happen
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			log.debug("Log file does not exist");
		}
	}
	
	protected boolean eclipseLogFileExists(){
		File logFile = Platform.getWorkbenchLog();
		return  logFile != null && logFile.exists();
	}
}