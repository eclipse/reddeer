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

	/**
	 * Instantiates a new property.
	 */
	public Property() {
		super();
	}
	
	/**
	 * Instantiates a new property.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public Property(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets the key.
	 *
	 * @param key the new key
	 */
	@XmlAttribute
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	@XmlAttribute
	public void setValue(String value) {
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Property[" + key + ", " + value + "]";
	}
}
