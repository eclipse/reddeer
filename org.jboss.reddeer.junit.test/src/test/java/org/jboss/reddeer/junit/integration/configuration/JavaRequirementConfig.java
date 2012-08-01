package org.jboss.reddeer.junit.integration.configuration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="java-requirement", namespace="http://www.jboss.org/NS/JavaReq")
public class JavaRequirementConfig {

	private String version;
	
	private String runtime;

	public String getVersion() {
		return version;
	}

	@XmlElement(namespace="http://www.jboss.org/NS/JavaReq")
	public void setVersion(String version) {
		this.version = version;
	}

	public String getRuntime() {
		return runtime;
	}

	@XmlElement(namespace="http://www.jboss.org/NS/JavaReq")
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
}
