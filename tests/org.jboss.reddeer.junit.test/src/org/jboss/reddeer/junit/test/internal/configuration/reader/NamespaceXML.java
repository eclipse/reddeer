package org.jboss.reddeer.junit.test.internal.configuration.reader;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="user-requirement", namespace="http://www.jboss.org/NS/user-schema")
public class NamespaceXML {

	private String attribute;

	@XmlAttribute(name="name")
	public String getAttribute() {
		return attribute;
	}
	
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}
