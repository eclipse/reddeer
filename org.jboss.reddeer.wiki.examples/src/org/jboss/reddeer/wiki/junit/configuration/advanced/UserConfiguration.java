package org.jboss.reddeer.wiki.junit.configuration.advanced;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
