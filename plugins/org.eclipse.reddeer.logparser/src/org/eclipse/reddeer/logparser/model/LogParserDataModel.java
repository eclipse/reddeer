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
package org.eclipse.reddeer.logparser.model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;
import org.eclipse.reddeer.logparser.LogParserActivator;
import org.eclipse.reddeer.logparser.LogParserLog;
import org.eclipse.reddeer.logparser.preferences.LogParserPreferencesPageModel;

public class LogParserDataModel {
	private static final String TAG_LOGS = "logs";
	private static final String TAG_LOG_DATA = "log_data";
	private static final String TAG_LOCATION = "location";
	private static final String TAG_RULE_NAME = "rule_name";

	public static ArrayList<LogData> getLogParserData() {
		return loadLogParserData();
	}

	public static void saveLogParserData(List<LogData> logs) {
		XMLMemento memento = XMLMemento.createWriteRoot(TAG_LOGS);
		FileWriter fileWriter = null;
		try {
			for (LogData logData : logs) {
				IMemento logDataNode = memento.createChild(TAG_LOG_DATA);
				logDataNode.putString(TAG_LOCATION, logData.getLocation());
				for (ParseRule parseRule : logData.getParseRules()){
					logDataNode.createChild(TAG_RULE_NAME).putTextData(parseRule.getName());
				}
			}
			fileWriter = new FileWriter(getLogParserFile());
			memento.save(fileWriter);
		} catch (IOException ioe) {
			LogParserLog.logError(ioe);
		} finally {
			try {
				if (fileWriter != null) {
					fileWriter.close();
				}
			} catch (IOException ioe) {
				LogParserLog.logError(ioe);
			}
		}
	}

	private static ArrayList<LogData> loadLogParserData() {
		ArrayList<LogData> logs = new ArrayList<LogData>();
		
		FileReader fileReader = null;
		File logParserFile = getLogParserFile();
		List<ParseRule> definedParseRules = LogParserPreferencesPageModel.getParseRules();
		if (logParserFile.exists()){
			try {
				fileReader = new FileReader(logParserFile);
				IMemento[] logDataNodes = XMLMemento.createReadRoot(fileReader).getChildren(TAG_LOG_DATA);
				for (IMemento logDataNode : logDataNodes) {
					LogData logData = new LogData();
					logData.setLocation(logDataNode.getString(TAG_LOCATION));
					ArrayList<ParseRule> parseRules = new ArrayList<ParseRule>();
					IMemento[] parseRuleNodes = logDataNode.getChildren(TAG_RULE_NAME);
					for (IMemento parseRuleNode : parseRuleNodes){
						ParseRule parseRule = LogParserDataModel.getRuleByName(parseRuleNode.getTextData(),
								definedParseRules);
						if (parseRule != null){
							parseRules.add(parseRule);
						}
					}
					logData.setParseRules(parseRules);
					logs.add(logData);
				}
			} catch (WorkbenchException|IOException ioe ) {
				LogParserLog.logError(ioe);
			} finally {
				try {
					if (fileReader != null) {
						fileReader.close();
					}
				} catch (IOException ioe) {
					LogParserLog.logError(ioe);
				}
			}
		}
		
		return logs;
	}
	
	private static File getLogParserFile() {
		return LogParserActivator.getDefault().getStateLocation().append("logparserdata.xml").toFile();
	}

	public static ParseRule getRuleByName (String name , List<ParseRule> parseRules){
		ParseRule parseRule = null;
		
		if (parseRules != null && name != null){
			Iterator<ParseRule> itParseRule = parseRules.iterator();
			while (parseRule == null && itParseRule.hasNext()){
				ParseRule currentParseRule = itParseRule.next();
				if (name.equalsIgnoreCase(currentParseRule.getName())){
					parseRule = currentParseRule;
				}
			}
		}
		
		return parseRule;
	}
	
}
