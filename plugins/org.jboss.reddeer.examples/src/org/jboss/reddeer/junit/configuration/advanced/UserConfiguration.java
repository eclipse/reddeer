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
package org.jboss.reddeer.junit.configuration.advanced;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * Stores user requirement configuration loaded from custom xml file
 * @author lucia jelinkova
 *
 */
@XmlRootElement(name="user-requirement", namespace="http://www.jboss.org/NS/user-schema")
public class UserConfiguration {

	private String dbName;
	
	private String ip;
	
	private String port;

	public String getIp() {
		return ip;
	}

	@XmlElement(namespace="http://www.jboss.org/NS/user-schema")
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	@XmlElement(namespace="http://www.jboss.org/NS/user-schema")
	public void setPort(String port) {
		this.port = port;
	}

	public String getDbName() {
		return dbName;
	}

	@XmlElement(name="db-name", namespace="http://www.jboss.org/NS/user-schema")
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
}
