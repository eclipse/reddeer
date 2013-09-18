package org.jboss.reddeer.junit.internal.configuration.entity;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Holder class for key - value {@link String} pairs. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class Property {

	private String key;
	
	private String value;

	public Property() {
		super();
	}
	
	public Property(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	@XmlAttribute
	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	@XmlAttribute
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "Property[" + key + ", " + value + "]";
	}
}
