package org.jboss.reddeer.junit.internal.configuration.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.core.runtime.Platform;
import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.jboss.reddeer.junit.requirement.Requirement;

/**
 * JAXB annotated class for reading the default (property based) configuration.
 * 
 * @author Lucia Jelinkova
 * @author sbunciak
 */
@XmlRootElement(name = "requirement", namespace = "http://www.jboss.org/NS/Req")
public class PropertyBasedConfiguration {

	private List<Property> properties = new ArrayList<Property>();

	private String clazz;

	private Class<? extends Requirement<?>> className;

	/**
	 * @param List of requirement properties to be set
	 */
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	/**
	 * @return List of requirement properties 
	 */
	@XmlElement(name = "property", namespace = "http://www.jboss.org/NS/Req")
	public List<Property> getProperties() {
		return properties;
	}

	/**
	 * @param Requirement class name 
	 */
	public void setRequirementClassName(String clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return Class name of the requirement 
	 */
	@XmlAttribute(name = "class")
	public String getRequirementClassName() {
		return clazz;
	}

	/**
	 * @return Requirement class looked up in running Eclipse/OSGi
	 * @since 0.2
	 */
	public Class<? extends Requirement<?>> getRequirementClass() {
		if (className == null) {
			className = createClass(getRequirementClassName());
		}
		return className;
	}

	/*
	 * Search Eclipse Platform command line arguments for test plugin (bundle)
	 * where test classes are looked up
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Class<? extends Requirement<?>> createClass(String name) {
		try {

			String testPlugin = "";
			String[] args = Platform.getCommandLineArgs();
			for (int i = 0; i < args.length; i++) {
				System.out.println(args[i]);
				if (args[i].contains("testpluginname")) {
					testPlugin = args[i + 1];
				}
			}

			Class clazz1;
			try {
				// load class from Eclipse bundle (if running as RedDeer Test)
				clazz1 = Platform.getBundle(testPlugin).loadClass(name);
			} catch (NullPointerException e) {
				// load class from junit classpath (if running as JUnit test)
				clazz1 = Class.forName(name);
			}
			
			return (Class<? extends Requirement<?>>) clazz1;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RedDeerConfigurationException(
					"The requirement class defined in the XML configuration file cannot be created. See details below",	e);
		}
	}
}
