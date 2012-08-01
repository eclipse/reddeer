package org.jboss.reddeer.junit.internal.configuration.reader;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="b", namespace="")
public class SimpleXML {

	private String attribute;

	@XmlAttribute(name="attr")
	public String getAttribute() {
		return attribute;
	}
	
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}
