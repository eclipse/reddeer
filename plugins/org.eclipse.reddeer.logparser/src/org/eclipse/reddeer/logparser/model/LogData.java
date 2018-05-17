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
package org.eclipse.reddeer.logparser.model;

import java.util.List;

public class LogData {
	private String location = "";
	private List<ParseRule> parseRules;
		
	public LogData() {
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<ParseRule> getParseRules() {
		return parseRules;
	}

	public void setParseRules(List<ParseRule> parseRules) {
		this.parseRules = parseRules;
	}
	
	@Override
	public String toString() {
		return "LogData [location=" + location + ", parseRules.size=" 
			+ (parseRules != null ? parseRules.size() : 0) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogData other = (LogData) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}
	public LogData clone () {
		LogData clone = new LogData();
		
		clone.location = this.location;
		clone.parseRules = this.parseRules;
		
		return clone;
	}
	
	public static void copyFields (LogData fromLogData, LogData toLogData){
		toLogData.setLocation(fromLogData.getLocation());
		toLogData.setParseRules(fromLogData.getParseRules());
	}
}
